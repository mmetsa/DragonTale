package com.ruthlessps.world.content.combat.strategy.impl;

import com.ruthlessps.model.Animation;
import com.ruthlessps.model.Locations;
import com.ruthlessps.model.Skill;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.content.combat.CombatContainer;
import com.ruthlessps.world.content.combat.CombatType;
import com.ruthlessps.world.content.combat.prayer.CurseHandler;
import com.ruthlessps.world.content.combat.prayer.PrayerHandler;
import com.ruthlessps.world.content.combat.strategy.CombatStrategy;
import com.ruthlessps.world.entity.impl.Character;
import com.ruthlessps.world.entity.impl.npc.NPC;
import com.ruthlessps.world.entity.impl.player.Player;

public class Vortex implements CombatStrategy {

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
		return 6;
	}

	@Override
	public boolean canAttack(Character entity, Character victim) {
		return true;
	}

	@Override
	public boolean customContainerAttack(Character entity, Character victim) {
		NPC vortex = (NPC) entity;
		Player victi = (Player) victim;
		if (vortex.isChargingAttack() || vortex.getConstitution() <= 0) {
			vortex.getCombatBuilder().setAttackTimer(4);
			return true;
		}
		if (Locations.goodDistance(vortex.getPosition().copy(), victim.getPosition().copy(), 1)
				&& Misc.getRandom(9) <= 6) {
			vortex.performAnimation(new Animation(vortex.getDefinition().getAttackAnimation()));
			vortex.getCombatBuilder().setContainer(new CombatContainer(vortex, victim, 1, 1, CombatType.RANGED, true));
		} else {
			vortex.performAnimation(new Animation(vortex.getDefinition().getAttackAnimation()));
			vortex.getCombatBuilder().setContainer(new CombatContainer(vortex, victim, 1, 1, CombatType.RANGED, true));
			victi.getSkillManager().setCurrentLevel(Skill.PRAYER, (int) (victi.getSkillManager().getCurrentLevel(Skill.PRAYER)-(victi.getSkillManager().getCurrentLevel(Skill.PRAYER)/5)), true);
			if(victi.getSkillManager().getCurrentLevel(Skill.PRAYER) <= 100) {
				CurseHandler.deactivateAll(victi);
				PrayerHandler.deactivateAll(victi);
			}
		}
		return true;
	}

	@Override
	public CombatType getCombatType() {
		return CombatType.RANGED;
	}
}
