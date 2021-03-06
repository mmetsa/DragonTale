package com.ruthlessps.world.content.combat.pvp;

import java.util.ArrayList;
import java.util.List;

import com.ruthlessps.model.Locations.Location;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.content.Achievements;
import com.ruthlessps.world.content.Achievements.AchievementData;
import com.ruthlessps.world.content.Artifacts;
import com.ruthlessps.world.content.LoyaltyProgram;
import com.ruthlessps.world.content.LoyaltyProgram.LoyaltyTitles;
import com.ruthlessps.world.entity.impl.player.Player;

public class PlayerKillingAttributes {

	/**
	 * Gets a random message after killing a player
	 * 
	 * @param killedPlayer
	 *            The player that was killed
	 */
	public static String getRandomKillMessage(String killedPlayer) {
		int deathMsgs = Misc.getRandom(8);
		switch (deathMsgs) {
		case 0:
			return "With a crushing blow, you defeat " + killedPlayer + ".";
		case 1:
			return "It's humiliating defeat for " + killedPlayer + ".";
		case 2:
			return "" + killedPlayer + " didn't stand a chance against you.";
		case 3:
			return "You've defeated " + killedPlayer + ".";
		case 4:
			return "" + killedPlayer + " regrets the day they met you in combat.";
		case 5:
			return "It's all over for " + killedPlayer + ".";
		case 6:
			return "" + killedPlayer + " falls before you might.";
		case 7:
			return "Can anyone defeat you? Certainly not " + killedPlayer + ".";
		case 8:
			return "You were clearly a better fighter than " + killedPlayer + ".";
		}
		return null;
	}

	private final Player player;
	private Player target;
	private int playerKills;
	private int playerKillStreak;
	private int playerDeaths;
	private int targetPercentage;
	private long lastPercentageIncrease;

	private int safeTimer;
	private final int WAIT_LIMIT = 2;

	private List<String> killedPlayers = new ArrayList<String>();

	public PlayerKillingAttributes(Player player) {
		this.player = player;
	}

	public void add(Player other) {

		if (other.getAppearance().getBountyHunterSkull() >= 0)
			other.getAppearance().setBountyHunterSkull(-1);

		boolean target = player.getPlayerKillingAttributes().getTarget() != null
				&& player.getPlayerKillingAttributes().getTarget().getIndex() == other.getIndex()
				|| other.getPlayerKillingAttributes().getTarget() != null
						&& other.getPlayerKillingAttributes().getTarget().getIndex() == player.getIndex();
		if (target)
			killedPlayers.clear();

		if (killedPlayers.size() >= WAIT_LIMIT) {
			killedPlayers.clear();
		}

		if (!killedPlayers.contains(other.getUsername()))
			handleReward(other, target);
		else
			player.getPacketSender().sendMessage(
					"You were not given points because you have recently defeated " + other.getUsername() + ".");

		if (target)
			BountyHunter.resetTargets(player, other, true, "You have defeated your target!");
	}

	public List<String> getKilledPlayers() {
		return killedPlayers;
	}

	public long getLastTargetPercentageIncrease() {
		return lastPercentageIncrease;
	}

	public int getPlayerDeaths() {
		return playerDeaths;
	}

	public int getPlayerKills() {
		return playerKills;
	}

	public int getPlayerKillStreak() {
		return playerKillStreak;
	}

	public int getSafeTimer() {
		return safeTimer;
	}

	public Player getTarget() {
		return target;
	}

	public int getTargetPercentage() {
		return targetPercentage;
	}

	/**
	 * Gives the player a reward for defeating his opponent
	 * 
	 * @param other
	 */
	private void handleReward(Player o, boolean targetKilled) {
		if (!o.getSerialNumber().equals(player.getSerialNumber())
				&& !player.getHostAddress().equalsIgnoreCase(o.getHostAddress())
				&& player.getLocation() == Location.WILDERNESS) {
			if (!killedPlayers.contains(o.getUsername()))
				killedPlayers.add(o.getUsername());
			player.getPacketSender().sendMessage(getRandomKillMessage(o.getUsername()));

			player.getPointsManager().setWithIncrease("pvp", 1);
			this.playerKills += 1;
			this.playerKillStreak += 1;
			player.getPacketSender().sendMessage("You've received a Pk point.");
			Artifacts.handleDrops(player, o, targetKilled);
			if (player.getAppearance().getBountyHunterSkull() < 4)
				player.getAppearance().setBountyHunterSkull(player.getAppearance().getBountyHunterSkull() + 1);
			player.getPointsManager().refreshPanel();

			/** ACHIEVEMENTS AND LOYALTY TITLES **/
			LoyaltyProgram.unlock(player, LoyaltyTitles.KILLER);
			if (this.playerKills >= 15) {
				LoyaltyProgram.unlock(player, LoyaltyTitles.SLAUGHTERER);
			} else if (this.playerKills >= 50) {
				LoyaltyProgram.unlock(player, LoyaltyTitles.GENOCIDAL);
			}
			if (this.playerKillStreak >= 15) {
				LoyaltyProgram.unlock(player, LoyaltyTitles.IMMORTAL);
			}
		}
	}

	public void setKilledPlayers(List<String> list) {
		killedPlayers = list;
	}

	public void setLastTargetPercentageIncrease(long lastPercentageIncrease) {
		this.lastPercentageIncrease = lastPercentageIncrease;
	}

	public void setPlayerDeaths(int playerDeaths) {
		this.playerDeaths = playerDeaths;
	}

	public void setPlayerKills(int playerKills) {
		this.playerKills = playerKills;
	}

	public void setPlayerKillStreak(int playerKillStreak) {
		this.playerKillStreak = playerKillStreak;
	}

	public void setSafeTimer(int safeTimer) {
		this.safeTimer = safeTimer;
	}

	public void setTarget(Player target) {
		this.target = target;
	}

	public void setTargetPercentage(int targetPercentage) {
		this.targetPercentage = targetPercentage;
	}
}
