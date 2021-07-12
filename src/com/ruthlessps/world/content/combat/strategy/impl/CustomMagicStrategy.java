package com.ruthlessps.world.content.combat.strategy.impl;

import com.ruthlessps.model.Animation;
import com.ruthlessps.model.Graphic;
import com.ruthlessps.model.Projectile;
import com.ruthlessps.model.Skill;
import com.ruthlessps.model.definitions.WeaponAnimations;
import com.ruthlessps.model.definitions.WeaponInterfaces.WeaponInterface;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.content.combat.CombatContainer;
import com.ruthlessps.world.content.combat.CombatType;
import com.ruthlessps.world.content.combat.prayer.CurseHandler;
import com.ruthlessps.world.content.combat.prayer.PrayerHandler;
import com.ruthlessps.world.content.combat.strategy.CombatStrategy;
import com.ruthlessps.world.content.minigames.impl.Dueling;
import com.ruthlessps.world.content.minigames.impl.Dueling.DuelRule;
import com.ruthlessps.world.entity.impl.Character;
import com.ruthlessps.world.entity.impl.npc.NPC;
import com.ruthlessps.world.entity.impl.player.Player;

/**
 * The default combat strategy assigned to an {@link Character} during a magic
 * based combat session. This is the combat strategy used by all {@link Npc}s by
 * 
 * @author Archie
 */
public class CustomMagicStrategy implements CombatStrategy {

	@Override
	public CombatContainer attack(Character entity, Character victim) {

		// Start the performAnimation for this attack.
		startAnimation(entity);

		// Create the combat container for this hook.
		return new CombatContainer(entity, victim, 1, 1, CombatType.MAGIC, true);
	}

	@Override
	public int attackDelay(Character entity) {
		// The attack speed for the weapon being used.
		return entity.getAttackSpeed();
	}

	@Override
	public int attackDistance(Character entity) {

		// The default distance for all npcs using magic is 8.
		return 8;
	}

	@Override
	public boolean canAttack(Character entity, Character victim) {

		if (entity.isPlayer()) {
			Player player = (Player) entity;
			if (Dueling.checkRule(player, DuelRule.NO_MAGIC)) {
				player.getPacketSender().sendMessage("Magic-attacks have been turned off in this duel!");
				player.getCombatBuilder().reset(true);
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean customContainerAttack(Character entity, Character victim) {
		Player player = (Player) entity;
		if (player.getConstitution() <= 0 || victim.getConstitution() <= 0) {
			return true;
		}
		player.performAnimation(new Animation(WeaponAnimations.getAttackAnimation(player)));
		new Projectile(player, victim, 551, 44, 3, 43, 43, 0).sendProjectile();
		victim.performGraphic(new Graphic(196));
		player.getCombatBuilder().setContainer(new CombatContainer(player, victim, 1, 1, CombatType.MAGIC, true));
		return true;
	}

	@Override
	public CombatType getCombatType() {
		return CombatType.MAGIC;
	}

	/**
	 * Starts the performAnimation for the argued entity in the current combat hook.
	 * 
	 * @param entity
	 *            the entity to start the performAnimation for.
	 */
	private void startAnimation(Character entity) {
		if (entity.isNpc()) {
			NPC npc = (NPC) entity;
			npc.performAnimation(new Animation(npc.getDefinition().getAttackAnimation()));
		} else if (entity.isPlayer()) {
			Player player = (Player) entity;
			if (!player.isSpecialActivated()) {
				player.performAnimation(new Animation(WeaponAnimations.getAttackAnimation(player)));
			} else {
				player.performAnimation(new Animation(player.getFightType().getAnimation()));
			}
		}
	}
}
