package com.ruthlessps.world.content.combat.strategy.impl;

import com.ruthlessps.model.Animation;
import com.ruthlessps.model.Graphic;
import com.ruthlessps.model.Locations.Location;
import com.ruthlessps.model.Projectile;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.content.combat.CombatContainer;
import com.ruthlessps.world.content.combat.CombatType;
import com.ruthlessps.world.content.combat.strategy.CombatStrategy;
import com.ruthlessps.world.entity.impl.Character;
import com.ruthlessps.world.entity.impl.npc.NPC;

public class MagicSpell implements CombatStrategy {

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
		return 50;
	}

	@Override
	public boolean canAttack(Character entity, Character victim) {
		return true;
	}

	@Override
	public boolean customContainerAttack(Character entity, Character victim) {
		NPC npc = (NPC) entity;
		if (npc.getConstitution() <= 0 || victim.getConstitution() <= 0) {
			return true;
		}
		npc.performAnimation(new Animation(npc.getDefinition().getAttackAnimation()));
		if(npc.getId() == 11250) {
			new Projectile(npc, victim, 970, 44, 3, 43, 43, 0).sendProjectile();
			new Projectile(npc, victim, 1000, 44, 3, 43, 43, 0).sendProjectile();
			victim.performGraphic(new Graphic(971));
		}else {
			new Projectile(npc, victim, 395, 44, 3, 43, 43, 0).sendProjectile();
			victim.performGraphic(new Graphic(363));
		}
		npc.getCombatBuilder().setContainer(new CombatContainer(npc, victim, 1, 3, CombatType.MAGIC, true));
		return true;
	}

	@Override
	public CombatType getCombatType() {
		return CombatType.MAGIC;
	}
}
