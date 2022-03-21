package com.ruthlessps.world.content;

import com.ruthlessps.model.Animation;
import com.ruthlessps.model.GameMode;
import com.ruthlessps.model.Locations.Location;
import com.ruthlessps.model.PlayerRanks.DonorRights;
import com.ruthlessps.model.Skill;
import com.ruthlessps.world.entity.impl.player.Player;

/**
 * Handles a player's run energy
 * 
 * @author Gabriel Hannason Thanks to Russian for formula!
 */
public class EnergyHandler {

	public static void processPlayerEnergy(Player p) {
		if (p.getGameMode().equals(GameMode.HARDCORE) && p.getDonor() != DonorRights.SPONSOR && p.getDonor() != DonorRights.SUPER_SPONSOR) {
			if (p.isRunning() && p.getMovementQueue().isMoving()) {
				int energy = p.getRunEnergy();
				if (energy > 0) {
					energy = (energy - 1);
					p.setRunEnergy(energy);
					p.getPacketSender().sendRunEnergy(energy);
				} else {
					p.setRunning(false);
					p.getPacketSender().sendRunStatus();
					p.getPacketSender().sendRunEnergy(0);
				}
			} else {
				if (p.getRunEnergy() < 100) {
					if (System.currentTimeMillis() >= (restoreEnergyFormula(p) + p.getLastRunRecovery().getTime())) {
						int energy = p.getRunEnergy() + 1;
						p.setRunEnergy(energy);
						p.getPacketSender().sendRunEnergy(energy);
						p.getLastRunRecovery().reset();
					}
				}
			}
		} else {
			
		}
	}

	public static void rest(Player player) {
		if (player.busy() || player.getCombatBuilder().isBeingAttacked() || player.getCombatBuilder().isAttacking() || player.getLocation().equals(Location.WILDERNESS)) {
			player.getPacketSender().sendMessage("You cannot do this right now.");
			return;
		}
		player.setResting(true);
		player.performAnimation(new Animation(11786));
		player.getPacketSender().sendMessage("You begin resting..");
	}

	public static double restoreEnergyFormula(Player p) {
		return 2260 - (p.getSkillManager().getCurrentLevel(Skill.AGILITY) * (p.isResting() ? 13000 : 10));
	}
}
