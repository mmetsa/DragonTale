package com.ruthlessps.world.entity.impl.npc;

import com.ruthlessps.model.Locations.Location;
import com.ruthlessps.world.content.combat.CombatFactory;
import com.ruthlessps.world.content.skill.impl.dungeoneering.Dungeoneering;
import com.ruthlessps.world.entity.impl.player.Player;

/**
 * Handles the behavior of aggressive {@link Npc}s around players within the
 * <code>NPC_TARGET_DISTANCE</code> radius.
 * 
 * @author lare96
 */
public final class NpcAggression {

	/**
	 * Time that has to be spent in a region before npcs stop acting aggressive
	 * toward a specific player.
	 */
	public static final int NPC_TOLERANCE_SECONDS = 300; // 5 mins

	public static void target(Player player) {

		if (player.isPlayerLocked())
			return;

		final boolean dung = Dungeoneering.doingDungeoneering(player);

		// Loop through all of the aggressive npcs.
		for (NPC npc : player.getLocalNpcs()) {
			NPCFacing.updateFacing(player, npc);
			if (npc == null || npc.getConstitution() <= 0
					|| !(dung && npc.getId() != 11226) && !npc.getDefinition().isAggressive()) {
				continue;
			}

			if (!npc.findNewTarget()) {
				if (npc.getCombatBuilder().isAttacking() || npc.getCombatBuilder().isBeingAttacked()) {
					continue;
				}
			}

			boolean multi = Location.inMulti(player);

			if (player.isTargeted()) {
				if (!player.getCombatBuilder().isBeingAttacked()) {
					player.setTargeted(false);
				} else if (!multi) {
					break;
				}
			}

			if (player.getSkillManager().getCombatLevel() > (npc.getDefinition().getCombatLevel() * 2)
					&& player.getLocation() != Location.WILDERNESS && !dung) {
				continue;
			}

			if (Location.ignoreFollowDistance(npc) || npc.getDefaultPosition().getDistance(player.getPosition()) < 12
					+ npc.getMovementCoordinator().getCoordinator().getRadius() || dung) {
				if (CombatFactory.checkHook(npc, player)) {
					player.setTargeted(true);
					npc.getCombatBuilder().attack(player);
					npc.setFindNewTarget(false);
					break;
				}
			}
		}
	}
}