package com.ruthlessps.world.content.combat.strategy.impl;

import com.ruthlessps.engine.task.Task;
import com.ruthlessps.engine.task.TaskManager;
import com.ruthlessps.model.Animation;
import com.ruthlessps.model.Locations;
import com.ruthlessps.model.Position;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.content.combat.CombatContainer;
import com.ruthlessps.world.content.combat.CombatType;
import com.ruthlessps.world.content.combat.strategy.CombatStrategy;
import com.ruthlessps.world.entity.impl.Character;
import com.ruthlessps.world.entity.impl.npc.NPC;
import com.ruthlessps.world.entity.impl.player.Player;

public class BandosWarlord implements CombatStrategy {
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
		return 10;
	}

	@Override
	public boolean canAttack(Character entity, Character victim) {
		return true;
	}

	@Override
	public boolean customContainerAttack(Character entity, Character victim) {
		NPC bandos = (NPC) entity;
		Player player = (Player) victim;
		if (bandos.isChargingAttack() || victim.getConstitution() <= 0) {
			return true;
		}
		bandos.setChargingAttack(true);
		if (!(Locations.goodDistance(bandos.getPosition().copy(), victim.getPosition().copy(), 1))) {
			TaskManager.submit(new Task(1, bandos, false) {
				int tick = 0;
				@Override
				protected void execute() {
					switch(tick) {
					case 0:
						x=player.getPosition().getX();y=player.getPosition().getY();
						bandos.performAnimation(new Animation(1979));
						bandos.forceChat("Come here you fool!");
						player.moveTo(new Position(bandos.getPosition().getX()+1, bandos.getPosition().getY()));
					break;
					case 1:
						bandos.getCombatBuilder().setContainer(new CombatContainer(bandos, victim, 2, 0, CombatType.MELEE,
								Misc.getRandom(10) <= 1 ? false : true));
						break;
					case 3:
						bandos.forceChat("Leave you fool!");
						player.moveTo(new Position(x,y));
						player.getCombatBuilder().cooldown(true);
						bandos.setChargingAttack(false);
					break;
					}
					tick++;
				}
			});
			return true;
		}
		return true;
	}

	@Override
	public CombatType getCombatType() {
		return CombatType.MELEE;
	}
}
