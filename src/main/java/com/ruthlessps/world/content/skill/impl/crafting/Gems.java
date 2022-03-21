package com.ruthlessps.world.content.skill.impl.crafting;

import com.ruthlessps.engine.task.Task;
import com.ruthlessps.engine.task.TaskManager;
import com.ruthlessps.engine.task.impl.QuestAssigner;
import com.ruthlessps.model.Animation;
import com.ruthlessps.model.Skill;
import com.ruthlessps.model.definitions.ItemDefinition;
import com.ruthlessps.model.input.impl.EnterAmountOfGemsToCut;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.entity.impl.player.Player;

public class Gems {

	enum GEM_DATA {

		OPAL(1625, 1609, 8, 347, new Animation(886)), JADE(1627, 1611, 13, 680, new Animation(886)), RED_TOPAZ(1629,
				1613, 16, 790, new Animation(887)), SAPPHIRE(1623, 1607, 20, 1200, new Animation(888)), EMERALD(1621,
						1605, 27, 1800, new Animation(889)), RUBY(1619, 1603, 34, 2600, new Animation(892)), DIAMOND(
								1617, 1601, 43, 3500, new Animation(886)), DRAGONSTONE(1631, 1615, 55, 4400,
										new Animation(885)), ONYX(6571, 6573, 99, 8513, new Animation(885));

		public static GEM_DATA forUncutGem(int uncutGem) {
			for (GEM_DATA data : GEM_DATA.values()) {
				if (data.getUncutGem() == uncutGem)
					return data;
			}
			return null;
		}

		private int uncutGem, cutGem, levelReq, xpReward;
		private Animation animation;

		GEM_DATA(int uncutGem, int cutGem, int levelReq, int xpReward, Animation animation) {
			this.uncutGem = uncutGem;
			this.cutGem = cutGem;
			this.levelReq = levelReq;
			this.xpReward = xpReward;
			this.animation = animation;
		}

		public Animation getAnimation() {
			return animation;
		}

		public int getCutGem() {
			return cutGem;
		}

		public int getLevelReq() {
			return levelReq;
		}

		public int getUncutGem() {
			return uncutGem;
		}

		public int getXpReward() {
			return xpReward;
		}
	}

	public static void cutGem(final Player player, final int amount, final int uncutGem) {
		player.getPacketSender().sendInterfaceRemoval();
		player.getSkillManager().stopSkilling();
		final GEM_DATA data = GEM_DATA.forUncutGem(uncutGem);
		if (data == null)
			return;
		player.setCurrentTask(new Task(2, player, true) {
			int amountCut = 0;

			@Override
			public void execute() {
				if (!player.getInventory().contains(uncutGem)) {
					stop();
					return;
				}
				player.performAnimation(data.getAnimation());
				player.getInventory().delete(uncutGem, 1);
				if(Misc.random(14) == 1) {
					player.getInventory().add(6573, 1);
					player.getPacketSender().sendMessage("That gem turned out to be a Onyx instead!");
				} else {
					player.getInventory().add(data.getCutGem(), 1);
				}
				QuestAssigner.handleQuest(player, 91);
				player.getSkillManager().addExperience(Skill.CRAFTING, data.getXpReward());
				amountCut++;
				if (amountCut >= amount)
					stop();
			}
		});
		TaskManager.submit(player.getCurrentTask());
	}

	public static void selectionInterface(Player player, int gem) {
		player.getPacketSender().sendInterfaceRemoval();
		GEM_DATA data = GEM_DATA.forUncutGem(gem);
		if (data == null)
			return;
		if (player.getSkillManager().getMaxLevel(Skill.CRAFTING) < data.getLevelReq()) {
			player.getPacketSender()
					.sendMessage("You need a Crafting level of atleast " + data.getLevelReq() + " to craft this gem.");
			return;
		}
		player.setSelectedSkillingItem(gem);
		player.setInputHandling(new EnterAmountOfGemsToCut());
		player.getPacketSender().sendString(2799, ItemDefinition.forId(gem).getName())
				.sendInterfaceModel(1746, gem, 150).sendChatboxInterface(4429);
		player.getPacketSender().sendString(2800, "How many would you like to craft?");
	}

}
