package com.ruthlessps.world.content.combat.strategy.impl;

import com.ruthlessps.engine.task.Task;
import com.ruthlessps.engine.task.TaskManager;
import com.ruthlessps.model.Animation;
import com.ruthlessps.model.Graphic;
import com.ruthlessps.model.Locations;
import com.ruthlessps.model.Projectile;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.content.combat.CombatContainer;
import com.ruthlessps.world.content.combat.CombatType;
import com.ruthlessps.world.content.combat.strategy.CombatStrategy;
import com.ruthlessps.world.entity.impl.Character;
import com.ruthlessps.world.entity.impl.npc.NPC;

public class Demon implements CombatStrategy {

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
		return 1;
	}

	@Override
	public boolean canAttack(Character entity, Character victim) {
		return true;
	}

	@Override
	public boolean customContainerAttack(Character entity, Character victim) {
		NPC diablo = (NPC) entity;
		if (diablo.isChargingAttack() || victim.getConstitution() <= 0) {
			return true;
		}
		if (Locations.goodDistance(diablo.getPosition().copy(), victim.getPosition().copy(), 1)) {
			if(diablo.getConstitution() > diablo.getDefaultConstitution()*0.8) {
				diablo.performAnimation(new Animation(diablo.getDefinition().getAttackAnimation()));
				diablo.getCombatBuilder().setContainer(new CombatContainer(diablo, victim, 1, 1, CombatType.MAGIC, false));
			} else if(diablo.getConstitution() > diablo.getDefaultConstitution()*0.4) {
				diablo.performAnimation(new Animation(diablo.getDefinition().getAttackAnimation()));
				diablo.getCombatBuilder().setContainer(new CombatContainer(diablo, victim, 2, 1, CombatType.MAGIC, false));
			} else {
				diablo.performAnimation(new Animation(diablo.getDefinition().getAttackAnimation()));
				diablo.getCombatBuilder().setContainer(new CombatContainer(diablo, victim, 3, 1, CombatType.MAGIC, false));
			}
		}
		return true;
	}

	@Override
	public CombatType getCombatType() {
		return CombatType.MAGIC;
	}
}
