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

public class HeartWrencher implements CombatStrategy {

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
		if (Locations.goodDistance(diablo.getPosition().copy(), victim.getPosition().copy(), 1) && Misc.getRandom(5) <= 3) {
				diablo.performAnimation(new Animation(diablo.getDefinition().getAttackAnimation()));
				diablo.getCombatBuilder().setContainer(new CombatContainer(diablo, victim, 3, 0, CombatType.MELEE, true));
		} else {
			diablo.performAnimation(new Animation(69));
			diablo.setChargingAttack(true);
			diablo.getCombatBuilder().setContainer(new CombatContainer(diablo, victim, 1, 2, CombatType.MAGIC,
					Misc.getRandom(10) <= 3 ? false : true));
			TaskManager.submit(new Task(1, diablo, false) {
				int tick = 0;

				@Override
				protected void execute() {
					switch (tick) {
					case 0:
						new Projectile(diablo, victim, 135, 44, 3, 43, 31, 0).sendProjectile();
						break;
					case 1:
						victim.performGraphic(new Graphic(129));
						diablo.setChargingAttack(false);
						stop();
						break;
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
