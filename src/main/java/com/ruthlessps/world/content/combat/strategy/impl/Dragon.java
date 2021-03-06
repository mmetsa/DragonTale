package com.ruthlessps.world.content.combat.strategy.impl;

import com.ruthlessps.engine.task.Task;
import com.ruthlessps.engine.task.TaskManager;
import com.ruthlessps.model.Animation;
import com.ruthlessps.model.Graphic;
import com.ruthlessps.model.Locations;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.content.combat.CombatContainer;
import com.ruthlessps.world.content.combat.CombatType;
import com.ruthlessps.world.content.combat.strategy.CombatStrategy;
import com.ruthlessps.world.entity.impl.Character;
import com.ruthlessps.world.entity.impl.npc.NPC;

public class Dragon implements CombatStrategy {

	public static int getAnimation(int npc) {
		int anim = 12259;
		if (npc == 50)
			anim = 81;
		else if (npc == 5363 || npc == 1590 || npc == 1591 || npc == 1592)
			anim = 14246;
		else if (npc == 51)
			anim = 13152;
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
		return 5;
	}

	@Override
	public boolean canAttack(Character entity, Character victim) {
		return true;
	}

	@Override
	public boolean customContainerAttack(Character entity, Character victim) {
		NPC dragon = (NPC) entity;
		if (dragon.isChargingAttack() || dragon.getConstitution() <= 0) {
			dragon.getCombatBuilder().setAttackTimer(4);
			return true;
		}
		if (Locations.goodDistance(dragon.getPosition().copy(), victim.getPosition().copy(), 1)
				&& Misc.getRandom(5) <= 3) {
			dragon.performAnimation(new Animation(dragon.getDefinition().getAttackAnimation()));
			dragon.getCombatBuilder().setContainer(new CombatContainer(dragon, victim, 1, 1, CombatType.MELEE, true));
		} else {
			dragon.setChargingAttack(true);
			dragon.performAnimation(new Animation(getAnimation(dragon.getId())));
			dragon.getCombatBuilder()
					.setContainer(new CombatContainer(dragon, victim, 1, 3, CombatType.DRAGON_FIRE, true));
			TaskManager.submit(new Task(1, dragon, false) {
				int tick = 0;

				@Override
				public void execute() {
					/*
					 * if(tick == 1 && dragon.getId() == 50) { new Projectile(dragon, victim, 393 +
					 * Misc.getRandom(3), 44, 3, 43, 43, 0).sendProjectile(); } else
					 */
					if (tick == 2) {
						victim.performGraphic(new Graphic(5));
					} else if (tick == 3) {
						victim.performGraphic(new Graphic(5));
						dragon.setChargingAttack(false).getCombatBuilder().setAttackTimer(6);
						stop();
					}
					tick++;
				}
			});

		}
		return true;
	}

	@Override
	public CombatType getCombatType() {
		return CombatType.MIXED;
	}
}
