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

public class Bandos implements CombatStrategy {

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
		int rand = Misc.getRandom(4);
		if (Locations.goodDistance(diablo.getPosition().copy(), victim.getPosition().copy(), 1)) {
			if(rand == 0) {
				diablo.performAnimation(new Animation(diablo.getDefinition().getAttackAnimation()));
				diablo.getCombatBuilder().setContainer(new CombatContainer(diablo, victim, 1, 0, CombatType.MELEE, true));
			} else if(rand == 1) {
				diablo.performAnimation(new Animation(413));
				diablo.getCombatBuilder().setContainer(new CombatContainer(diablo, victim, 2, 0, CombatType.MELEE, true));
			} else if(rand == 2) {
				diablo.performAnimation(new Animation(414));
				diablo.getCombatBuilder().setContainer(new CombatContainer(diablo, victim, 3, 0, CombatType.MELEE, true));
			} else if(rand == 3) {
				diablo.performAnimation(new Animation(451));
				diablo.getCombatBuilder().setContainer(new CombatContainer(diablo, victim, 4, 0, CombatType.MELEE, true));
			} else if(rand == 4) {
				diablo.performAnimation(new Animation(440));
				diablo.getCombatBuilder().setContainer(new CombatContainer(diablo, victim, 3, 0, CombatType.MELEE, true));
			}
		}
		return true;
	}

	@Override
	public CombatType getCombatType() {
		return CombatType.MELEE;
	}
}
