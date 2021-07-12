package com.ruthlessps.world.content.combat.strategy.impl;

import com.ruthlessps.engine.task.Task;
import com.ruthlessps.engine.task.TaskManager;
import com.ruthlessps.model.Animation;
import com.ruthlessps.model.GameObject;
import com.ruthlessps.model.Graphic;
import com.ruthlessps.model.Hit;
import com.ruthlessps.model.Locations;
import com.ruthlessps.model.Position;
import com.ruthlessps.model.Prayerbook;
import com.ruthlessps.model.Projectile;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.World;
import com.ruthlessps.world.content.combat.CombatContainer;
import com.ruthlessps.world.content.combat.CombatType;
import com.ruthlessps.world.content.combat.prayer.CurseHandler;
import com.ruthlessps.world.content.combat.prayer.PrayerHandler;
import com.ruthlessps.world.content.combat.strategy.CombatStrategy;
import com.ruthlessps.world.entity.Entity;
import com.ruthlessps.world.entity.impl.Character;
import com.ruthlessps.world.entity.impl.npc.NPC;
import com.ruthlessps.world.entity.impl.player.Player;
import com.ruthlessps.world.entity.impl.player.PlayerLoading;

public class Zulrah implements CombatStrategy {
	int counter = 1;
	int x, y, z;
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
		NPC zulrah = (NPC) entity;
		Player player = (Player) victim;
		if (zulrah.isChargingAttack() || victim.getConstitution() <= 0) {
			return true;
		}
		zulrah.setChargingAttack(true);
		TaskManager.submit(new Task(1, zulrah, false) {
			int tick = 0;
			@Override
			protected void execute() {
				switch(tick) {
				case 0:
					zulrah.performAnimation(new Animation(5072));
					player.getCombatBuilder().cooldown(true);
					break;
				case 1:
					zulrah.setVisible(false);
					break;
				case 4:
					zulrah.setVisible(true);
					zulrah.performAnimation(new Animation(5073));
					break;
				case 8:
					zulrah.getCombatBuilder().setContainer(new CombatContainer(zulrah, victim, 3, 1,counter==1? CombatType.RANGED:CombatType.MAGIC,
							Misc.getRandom(10) <= 1 ? false : true));
					zulrah.performAnimation(new Animation(5069));
					new Projectile(zulrah, player, counter==1?1044:1046, 100, 3, 43, 31, 10).sendProjectile();
					if(counter==1) {
						counter = 0;
					}else {
						counter = 1;
					}
					break;
				case 10:
					zulrah.setChargingAttack(false);
					stop();
					break;
				}
				tick++;
			}
		});
		return true;
	}

	@Override
	public CombatType getCombatType() {
		return CombatType.MIXED;
	}
}
