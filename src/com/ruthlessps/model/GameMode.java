
package com.ruthlessps.model;

import com.ruthlessps.model.container.impl.Bank;
import com.ruthlessps.world.content.Achievements.AchievementData;
import com.ruthlessps.world.content.PlayerPanel;
import com.ruthlessps.world.content.StartScreen;
import com.ruthlessps.world.content.StartScreen.GameModes;
import com.ruthlessps.world.content.skill.impl.slayer.SlayerMaster;
import com.ruthlessps.world.content.skill.impl.slayer.SlayerTasks;
import com.ruthlessps.world.entity.impl.player.Player;

public enum GameMode {

	NORMAL(0.00), HARDCORE(0.05), IRONMAN(0.05);

	GameMode(double dropRateBoost) {
		this.dropRateBoost = dropRateBoost;
	}

	private double dropRateBoost;

	public double getDropRateBoost() {
		return dropRateBoost;
	}

	public static void set(Player player, GameMode newMode) {
		player.getPacketSender().sendInterfaceRemoval();
		//if (!player.newPlayer()) {
			if (player.getGameMode().equals(GameModes.HARDCORE) && newMode.equals(GameModes.NORMAL)) {
				player.getPacketSender().sendMessage("You can't go from hardcore to normal mode.");
				player.getPacketSender().sendMessage("Please select another mode.");
				return;
			}
			if (player.getGameMode().equals(newMode)) {
				player.getPacketSender().sendMessage("You are already on this game mode.");
				return;
			}
			player.getEquipment().resetItems().refreshItems();
			player.getInventory().resetItems().refreshItems();
			for (Bank b : player.getBanks()) {
				b.resetItems();
			}
			player.getSlayer().resetSlayerTask();
			player.getSlayer().setSlayerTask(SlayerTasks.NO_TASK).setAmountToSlay(0).setTaskStreak(0)
					.setSlayerMaster(SlayerMaster.VANNAKA);
			player.getSkillManager().newSkillManager();
			for (Skill skill : Skill.values()) {
				player.getSkillManager().updateSkill(skill);
			}
			for (AchievementData d : AchievementData.values()) {
				player.getAchievementAttributes().setCompletion(d.ordinal(), false);
			}
			player.getMinigameAttributes().getRecipeForDisasterAttributes().reset();
			player.getMinigameAttributes().getNomadAttributes().reset();
			player.getKillsTracker().clear();
			player.getDropLog().clear();
			player.getPointsManager().clearAll();
			PlayerPanel.refreshPanel(player);
		//}
		StartScreen.resetStarter(player);
		player.setGameMode(newMode);
		player.getPacketSender().sendIronmanMode(newMode.ordinal());
		player.getPacketSender().sendMessage("Your account progress has been reset.");
		player.setPlayerLocked(false);
		player.getUpdateFlag().flag(Flag.APPEARANCE);
	}
}