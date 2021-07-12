package com.ruthlessps.world.content.skill.impl.thieving;

import com.ruthlessps.model.Animation;
import com.ruthlessps.model.GameMode;
import com.ruthlessps.model.Skill;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.content.Achievements;
import com.ruthlessps.world.content.Achievements.AchievementData;
import com.ruthlessps.world.entity.impl.player.Player;

public class Stalls {

	public static void stealFromStall(Player player, int lvlreq, int xp, int reward, String message, int shardAmount) {
		if (player.getInventory().getFreeSlots() < 1) {
			player.getPacketSender().sendMessage("You need some more inventory space to do this.");
			return;
		}
		if (player.getCombatBuilder().isBeingAttacked()) {
			player.getPacketSender()
					.sendMessage("You must wait a few seconds after being out of combat before doing this.");
			return;
		}
		if (!player.getClickDelay().elapsed(2000))
			return;
		if (player.getSkillManager().getMaxLevel(Skill.THIEVING) < lvlreq) {
			player.getPacketSender()
					.sendMessage("You need a Thieving level of at least " + lvlreq + " to steal from this stall.");
			return;
		}
		player.performAnimation(new Animation(881));
		player.getPacketSender().sendInterfaceRemoval();
		player.getSkillManager().addExperience(Skill.THIEVING, xp);
		player.getClickDelay().reset();
		if(Misc.random(3999) == 0) {
			player.getInventory().add(12487, 1);
			player.getPA().sendMessage("You found a raccoon. You decide to keep it as your pet!");
			return;
		}
		int chance = 0;
		if(player.getEquipment().containsAll(3933,3934,3935,3936,3937,3938)) {
			shardAmount *= 2;
		} else {
			if(player.getEquipment().contains(3933) && Misc.random(9) == 0) {
				chance += 10;
			}
			if(player.getEquipment().contains(3934) && Misc.random(9) == 0) {
				chance += 10;
			}
			if(player.getEquipment().contains(3935) && Misc.random(9) == 0) {
				chance += 10;
			}
			if(player.getEquipment().contains(3936) && Misc.random(9) == 0) {
				chance += 10;
			}
			if(player.getEquipment().contains(3937) && Misc.random(9) == 0) {
				chance += 10;
			}
			if(player.getEquipment().contains(3938) && Misc.random(9) == 0) {
				chance += 10;
			}
			if(chance != 0) {
				if(Misc.random(100/chance) == 0) {
					shardAmount *=2;
				}
			}
		}
		if(player.getAttributes().getPet().getId() == 3286 && Misc.random(4) == 0) {
			shardAmount *=2;
		}
		
		player.getInventory().add(604, shardAmount);
		player.getPA().sendMessage("You gain "+shardAmount+" skilling shards.");
		Achievements.doProgress(player, AchievementData.STEAl_STALL);
		Achievements.doProgress(player, AchievementData.STEAL_50_STALLS);
		Achievements.doProgress(player, AchievementData.STEAL_500_STALLS);
		if(player.getInventory().isFull()) {
			return;
		}
		
		player.getPacketSender().sendMessage(message);
		if(player.getGameMode() == GameMode.HARDCORE && player.getEquipment().contains(9777)) {
			player.getInventory().add(reward, 2);
		} else {
			player.getInventory().add(reward, 1);
		}
		player.getSkillManager().stopSkilling();
	}

}
