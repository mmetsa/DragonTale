package com.ruthlessps.world.content.combat;

import java.util.List;

import com.ruthlessps.engine.task.Task;
import com.ruthlessps.model.Animation;
import com.ruthlessps.model.Graphic;
import com.ruthlessps.model.GraphicHeight;
import com.ruthlessps.model.Locations.Location;
import com.ruthlessps.model.container.impl.Equipment;
import com.ruthlessps.model.definitions.WeaponAnimations;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.content.Achievements;
import com.ruthlessps.world.content.Achievements.AchievementData;
import com.ruthlessps.world.content.Sounds;
import com.ruthlessps.world.content.combat.CombatContainer.CombatHit;
import com.ruthlessps.world.entity.impl.Character;
import com.ruthlessps.world.entity.impl.npc.NPC;
import com.ruthlessps.world.entity.impl.npc.NPCMovementCoordinator.CoordinateState;
import com.ruthlessps.world.entity.impl.player.Player;

/**
 * A {@link Task} implementation that deals a series of hits to an entity after
 * a delay.
 * 
 * @author lare96
 */
public class CombatHitTask extends Task {

	/** The attacker instance. */
	private Character attacker;

	/** The victim instance. */
	private Character victim;

	/** The attacker's combat builder attached to this task. */
	private CombatBuilder builder;

	/** The attacker's combat container that will be used. */
	private CombatContainer container;

	/** The total damage dealt during this hit. */
	private int damage;

	public CombatHitTask(CombatBuilder builder, CombatContainer container) { // Instant
																				// attack,
																				// no
																				// task
		this.builder = builder;
		this.container = container;
		this.attacker = builder.getCharacter();
		this.victim = builder.getVictim();
	}

	/**
	 * Create a new {@link CombatHit}.
	 * 
	 * @param builder
	 *            the combat builder attached to this task.
	 * @param container
	 *            the combat hit that will be used.
	 * @param delay
	 *            the delay in ticks before the hit will be dealt.
	 * @param initialRun
	 *            if the task should be ran right away.
	 */
	public CombatHitTask(CombatBuilder builder, CombatContainer container, int delay, boolean initialRun) {
		super(delay, builder.getCharacter(), initialRun);
		this.builder = builder;
		this.container = container;
		this.attacker = builder.getCharacter();
		this.victim = builder.getVictim();
	
	}

	@Override
	public void execute() {

		handleAttack();

		this.stop();
	}

	public void handleAttack() {
		if (attacker.getConstitution() <= 0 || !attacker.isRegistered()) {
			return;
		}

		// Do any hit modifications to the container here first.

		if (container.getModifiedDamage() > 0) {
			container.allHits(context -> {
				context.getHit().setDamage(container.getModifiedDamage());
				context.setAccurate(true);
			});
		}

		// Now we send the hitsplats if needed! We can't send the hitsplats
		// there are none to send, or if we're using magic and it splashed.
		if (container.getHits().length != 0 && container.getCombatType() != CombatType.MAGIC
				|| container.isAccurate()) {

			/** PRAYERS **/
			CombatFactory.applyPrayerProtection(container, builder);

			this.damage = container.getDamage();
			victim.getCombatBuilder().addDamage(attacker, damage);
			container.dealDamage();

			/** MISC **/
			if (attacker.isPlayer()) {
				Player p = (Player) attacker;
				if (damage > 0) {
					if(p.getEquipment().contains(4204)) {
							if(p.getConstitution() < 990) {
								p.setConstitution(p.getConstitution()+damage);
							}
					}
					if(p.getAttributes().getLifestealTimer() > 0) {
						if(p.getConstitution() < 990) {
							p.setConstitution(p.getConstitution()+damage/10);
							if(p.getConstitution() > 990) {
								p.setConstitution(990);
							}
						}
					}
					if (p.getLocation() == Location.DUNGEONEERING) {
						p.getMinigameAttributes().getDungeoneeringAttributes().incrementDamageDealt(damage);
					}
					/** ACHIEVEMENTS **/
					if (container.getCombatType() == CombatType.MELEE) {

						Achievements.doProgress(p, AchievementData.DEAL_1000_MELEE_DMG, damage);
						Achievements.doProgress(p, AchievementData.DEAL_100_000_MELEE_DMG, damage);
						Achievements.doProgress(p, AchievementData.DEAL_10M_MELEE_DMG, damage);
					} else if (container.getCombatType() == CombatType.RANGED) {
						Achievements.doProgress(p, AchievementData.DEAL_1000_RANGE_DMG, damage);
						Achievements.doProgress(p, AchievementData.DEAL_100_000_RANGE_DMG, damage);
						Achievements.doProgress(p, AchievementData.DEAL_10M_RANGE_DMG, damage);
					} else if (container.getCombatType() == CombatType.MAGIC) {
						Achievements.doProgress(p, AchievementData.DEAL_1000_MAGIC_DMG, damage);
						Achievements.doProgress(p, AchievementData.DEAL_100_000_MAGIC_DMG, damage);
						Achievements.doProgress(p, AchievementData.DEAL_10M_MAGIC_DMG, damage);
					}
				}
			} else {
				if (victim.isPlayer() && container.getCombatType() == CombatType.DRAGON_FIRE) {
					Player p = (Player) victim;
					if (Misc.getRandom(20) <= 15
							&& p.getEquipment().getItems()[Equipment.SHIELD_SLOT].getId() == 11283) {
						p.setPositionToFace(attacker.getPosition().copy());
						CombatFactory.chargeDragonFireShield(p);
					}
					if (damage >= 160) {
						((Player) victim).getPacketSender().sendMessage("You are badly burnt by the dragon's fire!");
					}
				}
			}
		}

		// Give experience based on the hits.
		CombatFactory.giveExperience(builder, container, damage);

		if (!container.isAccurate()) {
			if (container.getCombatType() == CombatType.MAGIC && attacker.getCurrentlyCasting() != null) {
				victim.performGraphic(new Graphic(85, GraphicHeight.MIDDLE));
				attacker.getCurrentlyCasting().finishCast(attacker, victim, false, 0);
				attacker.setCurrentlyCasting(null);
			}
		} else if (container.isAccurate()) {

			CombatFactory.handleArmorEffects(attacker, victim, damage, container.getCombatType());
			CombatFactory.handlePrayerEffects(attacker, victim, damage, container.getCombatType());
			CombatFactory.handleSpellEffects(attacker, victim, damage, container.getCombatType());

			attacker.poisonVictim(victim, container.getCombatType());

			// Finish the magic spell with the correct end graphic.
			if (container.getCombatType() == CombatType.MAGIC && attacker.getCurrentlyCasting() != null) {
				attacker.getCurrentlyCasting().endGraphic().ifPresent(victim::performGraphic);
				attacker.getCurrentlyCasting().finishCast(attacker, victim, true, damage);
				attacker.setCurrentlyCasting(null);
			}
		}

		// Send the defensive animations.
		if (victim.getCombatBuilder().getAttackTimer() <= 2) {
			if (victim.isPlayer()) {
				victim.performAnimation(new Animation(WeaponAnimations.getBlockAnimation(((Player) victim))));
				if (((Player) victim).getInterfaceId() > 0)
					((Player) victim).getPacketSender().sendInterfaceRemoval();
			} else if (victim.isNpc()) {
				if (!(((NPC) victim).getId() >= 6142 && ((NPC) victim).getId() <= 6145) && ((NPC) victim).getId() != 514)
					victim.performAnimation(new Animation(((NPC) victim).getDefinition().getDefenceAnimation()));
			}
		}

		// Fire the container's dynamic hit method.
		container.onHit(damage, container.isAccurate());

		// And finally auto-retaliate if needed.
		if (!victim.getCombatBuilder().isAttacking() || victim.getCombatBuilder().isCooldown() || victim.isNpc()) {
			if (victim.isPlayer() && ((Player) victim).isAutoRetaliate() && !victim.getMovementQueue().isMoving()	&& ((Player) victim).getWalkToTask() == null) {
				victim.getCombatBuilder().setDidAutoRetaliate(true);
				victim.getCombatBuilder().attack(attacker);
			} else if (victim.isNpc()) {
				 if (!(attacker.isNpc() 
						&& ((NPC) attacker).isSummoningNpc())) {
					NPC npc = (NPC) victim;

					if ((npc.getCombatBuilder().getVictim() == null || (npc.canChangeTarget() && Misc.random(5) > 2))
							&& npc.getMovementCoordinator().getCoordinateState() == CoordinateState.HOME) {
						victim.getCombatBuilder().attack(attacker);
						if (npc.getCombatBuilder().getVictim() == null
								|| npc.getCombatBuilder().getVictim().getIndex() != attacker.getIndex()) {
							npc.setChangeTargetTime(System.currentTimeMillis());
						}
					}
				}
			}
		}

		if (attacker.isNpc() && victim.isPlayer()) {
			NPC npc = (NPC) attacker;
			Player p = (Player) victim;
			if (npc.switchesVictim() && Misc.getRandom(6) <= 1) {
				if (npc.getDefinition().isAggressive()) {
					if (p.getSummoning().getFamiliar() != null) {
						npc.setFindNewTarget(true);
					}
					npc.setFindNewTarget(true);
				} else {
					if (p.getLocalPlayers().size() >= 1) {
						List<Player> list = p.getLocalPlayers();
						Player c = list.get(Misc.getRandom(list.size() - 1));
						npc.getCombatBuilder().attack(c);
					}
				}
			}

			Sounds.sendSound(p, Sounds.getPlayerBlockSounds(p.getEquipment().get(Equipment.WEAPON_SLOT).getId()));
			/** CUSTOM ON DAMAGE STUFF **/
			if (attacker.isPlayer()) {
				Player player = (Player) attacker;

				/** SKULLS **/
				if (player.getLocation() == Location.WILDERNESS && victim.isPlayer()) {
					if (!player.getCombatBuilder().isBeingAttacked() && !player.getCombatBuilder().didAutoRetaliate()
							|| player.getCombatBuilder().isBeingAttacked()
									&& player.getCombatBuilder().getLastAttacker() != victim
									&& Location.inMulti(player)) {
						CombatFactory.skullPlayer(player);
					}
				}

				player.setLastCombatType(container.getCombatType());
				Sounds.sendSound(player, Sounds.getPlayerAttackSound(player));

				/** CUSTOM ON DAMAGE STUFF **/
				if (victim.isPlayer()) {
					Sounds.sendSound((Player) victim, Sounds
							.getPlayerBlockSounds(((Player) victim).getEquipment().get(Equipment.WEAPON_SLOT).getId()));
				}
			}
		}
	}
}