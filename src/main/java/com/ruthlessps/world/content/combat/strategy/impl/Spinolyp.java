package com.ruthlessps.world.content.combat.strategy.impl;

import com.ruthlessps.model.Animation;
import com.ruthlessps.model.Locations.Location;
import com.ruthlessps.model.Projectile;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.content.combat.CombatContainer;
import com.ruthlessps.world.content.combat.CombatType;
import com.ruthlessps.world.content.combat.strategy.CombatStrategy;
import com.ruthlessps.world.entity.impl.Character;
import com.ruthlessps.world.entity.impl.npc.NPC;

public class Spinolyp implements CombatStrategy {

	@Override
	public CombatContainer attack(Character entity, Character victim) {
		return null;
	}

	@Override
	public int attackDelay(Character entity) {
		return entity.getAttackSpeed();
	}

	@Override
	public int attackDistance(Character entity) {
		return entity.getLocation() == Location.DUNGEONEERING ? 6 : 50;
	}

	@Override
	public boolean canAttack(Character entity, Character victim) {
		return true;
	}

	@Override
	public boolean customContainerAttack(Character entity, Character victim) {
		NPC spinolyp = (NPC) entity;
		if (spinolyp.getConstitution() <= 0 || victim.getConstitution() <= 0) {
			return true;
		}
		spinolyp.performAnimation(new Animation(spinolyp.getDefinition().getAttackAnimation()));
		boolean mage = Misc.getRandom(10) <= 7;
		new Projectile(spinolyp, victim, mage ? 1658 : 1017, 44, 3, 43, 43, 0).sendProjectile();
		spinolyp.getCombatBuilder().setContainer(new CombatContainer(spinolyp, victim, 1, mage ? 3 : 2,
				mage ? CombatType.MAGIC : CombatType.RANGED, true));
		return true;
	}

	@Override
	public CombatType getCombatType() {
		return CombatType.MIXED;
	}
}
