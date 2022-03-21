package com.ruthlessps.world.content.combat.strategy.impl;

import com.ruthlessps.engine.task.Task;
import com.ruthlessps.engine.task.TaskManager;
import com.ruthlessps.model.Animation;
import com.ruthlessps.model.Graphic;
import com.ruthlessps.model.Hit;
import com.ruthlessps.model.Locations;
import com.ruthlessps.model.Prayerbook;
import com.ruthlessps.model.Projectile;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.content.combat.CombatContainer;
import com.ruthlessps.world.content.combat.CombatType;
import com.ruthlessps.world.content.combat.strategy.CombatStrategy;
import com.ruthlessps.world.entity.impl.Character;
import com.ruthlessps.world.entity.impl.npc.NPC;
import com.ruthlessps.world.entity.impl.player.Player;

public class Diablo implements CombatStrategy {
	int counter = 0;
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
		Player player = (Player) victim;
		if (diablo.isChargingAttack() || victim.getConstitution() <= 0) {
			return true;
		}
		if (Locations.goodDistance(diablo.getPosition().copy(), victim.getPosition().copy(), 1)) {
			diablo.performAnimation(new Animation(diablo.getDefinition().getAttackAnimation()));
			diablo.getCombatBuilder().setContainer(new CombatContainer(diablo, victim, 1, 0, CombatType.MELEE, true));
			counter++;
			if(counter == 10) {
				diablo.forceChat("You shall perish!");
				if(player.getPrayerbook() == Prayerbook.CURSES && !player.getCurseActive()[9]||player.getPrayerbook() == Prayerbook.NORMAL && !player.getPrayerActive()[18]) {
					player.dealDamage(new Hit(player.getConstitution()));
					player.getPA().sendMessage("The Diablo perishes you for not having protection!");
				}
				counter = 0;
			}
		}
		return true;
	}

	@Override
	public CombatType getCombatType() {
		return CombatType.MELEE;
	}
}
