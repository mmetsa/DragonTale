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

public class Legio implements CombatStrategy {
	int counter = 0;
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
		int random = Misc.random(100);
		NPC legio = (NPC) entity;
		Player player = (Player) victim;
		if (legio.isChargingAttack() || victim.getConstitution() <= 0) {
			return true;
		}
		if(random < 75) {
			if (Locations.goodDistance(legio.getPosition().copy(), victim.getPosition().copy(), 8)) {
				legio.setChargingAttack(true);
				legio.getCombatBuilder().setContainer(new CombatContainer(legio, victim, 1, 4, CombatType.MAGIC,
				Misc.getRandom(10) <= 3 ? false : true));
				TaskManager.submit(new Task(1, legio, false) {
					int tick = 0;
					@Override
					protected void execute() {
						switch (tick) {
							case 0:
								legio.performAnimation(new Animation(8996));
								new Projectile(legio, player, 1185, 100, 3, 43, 31, 10).sendProjectile();
							break;
							case 4:
								player.performGraphic(new Graphic(1186));
								legio.setChargingAttack(false);
								stop();
							break;
						}
						tick++;
					}
				});
				return true;
			}
		} else if(random < 84){
			if (Locations.goodDistance(legio.getPosition().copy(), player.getPosition().copy(), 8)) {
				legio.setChargingAttack(true);
				legio.forceChat("Feel the Power of the Fire Bomb!");
				TaskManager.submit(new Task(1, legio, false) {
					int tick = 0;
					@Override
					protected void execute() {
						switch (tick) {
							case 0:
								legio.performAnimation(new Animation(8996));
								new Projectile(new Position(legio.getPosition().getX(), legio.getPosition().getY()),
										new Position(x = player.getPosition().getX(), y = player.getPosition().getY()),
										0, 1213, 100, 3, 43, 31, 10).sendProjectile();
							break;
							case 4:
								if(player.getPosition().getX() == x && player.getPosition().getY() == y) {
									player.dealFakeDamage(new Hit(99999));
									player.dealDamage(new Hit(player.getConstitution()));
									player.getPA().sendMessage("You are killed by the flames!");
								}
								GameObject c = new GameObject(0, new Position(x,y));
								World.register(c);
								c.performGraphic(new Graphic(1618));
								legio.setChargingAttack(false);
								stop();
								World.deregister(c);
							break;
						}
						tick++;
					}
				});
				return true;
			}
		} else if(random < 93){
			if (Locations.goodDistance(legio.getPosition().copy(), player.getPosition().copy(), 8)) {
				legio.setChargingAttack(true);
				legio.forceChat("Shall it rain bombs!");
				TaskManager.submit(new Task(1, legio, false) {
					int tick = 0;
					int p=0,q=0;
					@Override
					protected void execute() {
						if(tick < 19) {
							if(tick == 0) {
								legio.performAnimation(new Animation(8996));
								new Projectile(new Position(player.getPosition().getX()-1, player.getPosition().getY()),
										new Position(p = player.getPosition().getX(), q = player.getPosition().getY()),
										0, 1213, 50, 2, 250, 30, 0).sendProjectile();
								System.out.println("First");
							} else {
								if(tick != 18) {
									if(player.getPosition().getX() == p && player.getPosition().getY() == q) {
										if(tick%2 == 0) {
											player.dealDamage(new Hit(990));
											player.getPA().sendMessage("You are killed by the bomb!");
											legio.setChargingAttack(false);
											stop();
										}
									}
									GameObject c = new GameObject(0, new Position(p,q));
									World.register(c);
									c.performGraphic(new Graphic(1618));
									World.deregister(c);
									legio.performAnimation(new Animation(8996));
									new Projectile(new Position(player.getPosition().getX()-1, player.getPosition().getY()),
											new Position(p = player.getPosition().getX(), q = player.getPosition().getY()),
											0, 1213, 50, 2, 250, 30, 0).sendProjectile();
								} else {
									if(player.getPosition().getX() == p && player.getPosition().getY() == q) {
										player.getPA().sendMessage("You are killed by the bomb!");
									}
									GameObject c = new GameObject(0, new Position(x,y));
									World.register(c);
									c.performGraphic(new Graphic(1618));
									legio.setChargingAttack(false);
									stop();
									World.deregister(c);
								}
							}
						}
						tick++;
					}
				});
				return true;
			}
		} else {
			if (Locations.goodDistance(legio.getPosition().copy(), victim.getPosition().copy(), 8)) {
				legio.setChargingAttack(true);
				legio.getCombatBuilder().setContainer(new CombatContainer(legio, victim, 1, 3, CombatType.RANGED,
				Misc.getRandom(10) <= 3 ? false : true));
				TaskManager.submit(new Task(1, legio, false) {
					int tick = 0;
					@Override
					protected void execute() {
						switch (tick) {
							case 0:
								legio.forceChat("Shall it rain arrows!");
								legio.performAnimation(new Animation(427));
								new Projectile(legio, player, 995, 100, 3, 43, 31, 10).sendProjectile();
							break;
							case 3:
								if(player.getPrayerbook() == Prayerbook.NORMAL) {
									PrayerHandler.deactivateAll(player);
									player.getPA().sendMessage("Legio has disabled your prayers!");
								} else {
									CurseHandler.deactivateAll(player);
									player.getPA().sendMessage("Legio has disabled your prayers!");
								}
								player.performGraphic(new Graphic(1186));
								legio.setChargingAttack(false);
								stop();
							break;
						}
						tick++;
					}
				});
				return true;
			}
		}
		return false;
	}

	@Override
	public CombatType getCombatType() {
		return CombatType.MIXED;
	}
}
