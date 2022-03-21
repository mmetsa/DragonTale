package com.ruthlessps.world.content.combat.strategy.impl;

import com.ruthlessps.model.Animation;
import com.ruthlessps.model.Locations;
import com.ruthlessps.model.Position;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.World;
import com.ruthlessps.world.content.combat.CombatContainer;
import com.ruthlessps.world.content.combat.CombatType;
import com.ruthlessps.world.content.combat.strategy.CombatStrategy;
import com.ruthlessps.world.entity.impl.Character;
import com.ruthlessps.world.entity.impl.npc.NPC;
import com.ruthlessps.world.entity.impl.player.Player;

public class TorvaBoss implements CombatStrategy {
	public static int counter = 0;

	public static int getAnimation(int npc) {
		int anim = 427;
		return anim;
	}

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
		NPC torva = (NPC) entity;
		Player victi = (Player) victim;
		if (torva.isChargingAttack() || torva.getConstitution() <= 0) {
			torva.getCombatBuilder().setAttackTimer(4);
			return true;
		}
		NPC minion;
		if (Locations.goodDistance(torva.getPosition().copy(), victim.getPosition().copy(), 1)) {
			torva.performAnimation(new Animation(torva.getDefinition().getAttackAnimation()));
			torva.getCombatBuilder().setContainer(new CombatContainer(torva, victim, 1, 1, CombatType.MELEE, true));
		}
		return true;
	}

	@Override
	public CombatType getCombatType() {
		return CombatType.MELEE;
	}
}
