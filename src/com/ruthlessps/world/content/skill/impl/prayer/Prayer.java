package com.ruthlessps.world.content.skill.impl.prayer;

import com.ruthlessps.engine.task.Task;
import com.ruthlessps.engine.task.TaskManager;
import com.ruthlessps.model.Animation;
import com.ruthlessps.model.Item;
import com.ruthlessps.model.Skill;
import com.ruthlessps.world.content.Achievements;
import com.ruthlessps.world.content.Achievements.AchievementData;
import com.ruthlessps.world.content.Sounds;
import com.ruthlessps.world.content.Sounds.Sound;
import com.ruthlessps.world.content.ZoneTasks;
import com.ruthlessps.world.entity.impl.player.Player;

/**
 * The prayer skill is based upon burying the corpses of enemies. Obtaining a
 * higher level means more prayer abilities being unlocked, which help out in
 * combat.
 * 
 * @author Gabriel Hannason
 */

public class Prayer {

	public static boolean buryBone(Player player, int itemId) {
		if (!player.getClickDelay().elapsed(2000)) {
			return true;
		}

		final BonesData currentBone = BonesData.forId(itemId);
		if (currentBone == null) {
			return false;
		}

		player.getSkillManager().stopSkilling();
		player.getPacketSender().sendInterfaceRemoval();
		player.performAnimation(new Animation(827));
		player.getPacketSender().sendMessage("You dig a hole in the ground..");
		Item bone = new Item(itemId);
		player.getInventory().delete(bone);
		if(itemId == 20259)
			ZoneTasks.doTaskProgress(player, ZoneTasks.ZoneData.TRAINING_ZONE, 1, 1);
		Achievements.doProgress(player, AchievementData.BURY_SOME_BONES);
		Achievements.doProgress(player, AchievementData.BURY_50_BONES);
		Achievements.doProgress(player, AchievementData.BURY_500_BONES);
		TaskManager.submit(new Task(3, player, false) {
			@Override
			public void execute() {
				player.getPacketSender().sendMessage("..and bury the " + bone.getDefinition().getName() + ".");
				player.getSkillManager().addExperience(Skill.PRAYER, currentBone.getBuryingXP());
				Sounds.sendSound(player, Sound.BURY_BONE);
				this.stop();
			}
		});

		player.getClickDelay().reset();
		return true;
	}

	public static boolean isBone(int bone) {
		return BonesData.forId(bone) != null;
	}

}
