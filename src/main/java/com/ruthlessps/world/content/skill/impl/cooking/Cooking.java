package com.ruthlessps.world.content.skill.impl.cooking;

import com.ruthlessps.engine.task.Task;
import com.ruthlessps.engine.task.TaskManager;
import com.ruthlessps.model.Animation;
import com.ruthlessps.model.Skill;
import com.ruthlessps.model.definitions.ItemDefinition;
import com.ruthlessps.model.input.impl.EnterAmountToCook;
import com.ruthlessps.world.content.Achievements;
import com.ruthlessps.world.content.Achievements.AchievementData;
import com.ruthlessps.world.entity.impl.player.Player;

public class Cooking {

	public static void cook(final Player player, final int rawFish, final int amount) {
		final CookingData fish = CookingData.forFish(rawFish);
		if (fish == null)
			return;
		player.getSkillManager().stopSkilling();
		player.getPacketSender().sendInterfaceRemoval();
		if (!CookingData.canCook(player, rawFish))
			return;
		player.performAnimation(new Animation(896));
		player.setCurrentTask(new Task(2, player, false) {
			int amountCooked = 0;

			@Override
			public void execute() {
				if (!CookingData.canCook(player, rawFish)) {
					stop();
					return;
				}
				player.performAnimation(new Animation(896));
				player.getInventory().delete(rawFish, 1);
				if (!CookingData.success(player, 3, fish.getLevelReq(), fish.getStopBurn())) {
					player.getInventory().add(fish.getBurntItem(), 1);
					player.getPacketSender().sendMessage("You accidently burn the " + fish.getName() + ".");
				} else {
					player.getInventory().add(fish.getCookedItem(), 1);
					player.getSkillManager().addExperience(Skill.COOKING, fish.getXp());
					Achievements.doProgress(player, AchievementData.COOK_SOME_FISH);
					Achievements.doProgress(player, AchievementData.COOK_50_FISH);
				}
				amountCooked++;
				if (amountCooked >= amount)
					stop();
			}

			@Override
			public void stop() {
				setEventRunning(false);
				player.setSelectedSkillingItem(-1);
				player.performAnimation(new Animation(65535));
			}
		});
		TaskManager.submit(player.getCurrentTask());
	}

	public static void selectionInterface(Player player, CookingData cookingData) {
		if (cookingData == null)
			return;
		player.setSelectedSkillingItem(cookingData.getRawItem());
		player.setInputHandling(new EnterAmountToCook());
		player.getPacketSender().sendString(2799, ItemDefinition.forId(cookingData.getCookedItem()).getName())
				.sendInterfaceModel(1746, cookingData.getCookedItem(), 150).sendChatboxInterface(4429);
		player.getPacketSender().sendString(2800, "How many would you like to cook?");
	}
}
