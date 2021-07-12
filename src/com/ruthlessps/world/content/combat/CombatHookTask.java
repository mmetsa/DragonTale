package com.ruthlessps.world.content.combat;

import com.ruthlessps.engine.task.Task;
import com.ruthlessps.engine.task.TaskManager;
import com.ruthlessps.model.container.impl.Equipment;
import com.ruthlessps.world.content.Sounds;
import com.ruthlessps.world.content.combat.range.CombatRangedAmmo.RangedWeaponData;
import com.ruthlessps.world.content.combat.strategy.impl.DefaultRangedCombatStrategy;
import com.ruthlessps.world.content.combat.weapon.CombatSpecial;
import com.ruthlessps.world.entity.impl.npc.NPC;
import com.ruthlessps.world.entity.impl.player.Player;

/**
 * A {@link Task} implementation that handles every combat 'hook' or 'turn'
 * during a combat session.
 * 
 * @author lare96
 */
public class CombatHookTask extends Task {

	/** The builder assigned to this task. */
	private CombatBuilder builder;

	/**
	 * Create a new {@link CombatHookTask}.
	 * 
	 * @param builder
	 *            the builder assigned to this task.
	 */
	CombatHookTask(CombatBuilder builder) {
		super(1, builder, false);
		this.builder = builder;
	}

	@Override
	public void execute() {
		if (builder.isCooldown()) {
			builder.cooldown--;
			builder.attackTimer--;

			if (builder.cooldown == 0) {
				builder.reset(true);
			}
			return;
		}

		if (!CombatFactory.checkHook(builder.getCharacter(), builder.getVictim())) {
			return;
		}

		// If the entity is an player we redetermine the combat strategy before
		// attacking.
		if (builder.getCharacter().isPlayer()) {
			builder.determineStrategy();
		}

		// Decrement the attack timer.
		builder.attackTimer--;

		// The attack timer is below 1, we can attack.
		if (builder.attackTimer < 1) {
			// Check if the attacker is close enough to attack.
			if (!CombatFactory.checkAttackDistance(builder)) {
				if (builder.getCharacter().isNpc() && builder.getVictim().isPlayer()) {
					if (builder.getLastAttack().elapsed(4500)) {
						((NPC) builder.getCharacter()).setFindNewTarget(true);
					}
				}
				return;
			}

			// Check if the attack can be made on this hook
			if (!builder.getStrategy().canAttack(builder.getCharacter(), builder.getVictim())) {
				builder.getCharacter().getCombatBuilder().reset(builder.getCharacter().isNpc());
				return;
			}

			// Do all combat calculations here, we create the combat containers
			// using the attacking entity's combat strategy.

			// boolean customContainer =
			// builder.getStrategy().customContainerAttack(builder.getCharacter(),
			// builder.getVictim());

			builder.getStrategy().customContainerAttack(builder.getCharacter(), builder.getVictim());

			CombatContainer container = builder.getContainer();

			builder.getCharacter().setEntityInteraction(builder.getVictim());

			if (builder.getCharacter().isPlayer()) {
				Player player = (Player) builder.getCharacter();
				player.getPacketSender().sendInterfaceRemoval();

				if (player.isSpecialActivated() && player.getCastSpell() == null) {
					container = player.getCombatSpecial().container(player, builder.getVictim());
					boolean magicShortbowSpec = player.getCombatSpecial() != null
							&& player.getCombatSpecial() == CombatSpecial.MAGIC_SHORTBOW;
					CombatSpecial.drain(player, player.getCombatSpecial().getDrainAmount());

					Sounds.sendSound(player,
							Sounds.specialSounds(player.getEquipment().get(Equipment.WEAPON_SLOT).getId()));

					if (player.getCombatSpecial().getCombatType() == CombatType.RANGED) {
						DefaultRangedCombatStrategy.decrementAmmo(player, builder.getVictim().getPosition());
						if (CombatFactory.darkBow(player)
								|| player.getRangedWeaponData() == RangedWeaponData.MAGIC_SHORTBOW
										&& magicShortbowSpec) {
							DefaultRangedCombatStrategy.decrementAmmo(player, builder.getVictim().getPosition());
						}
					}
				}
			}

			// If there is no hit type the combat turn is ignored.
			if (container != null && container.getCombatType() != null) {
				// If we have hit splats to deal, we filter them through combat
				// prayer effects now. If not then we still send the hit tasks
				// next to handle any effects.

				// An attack is going to be made for sure, set the last attacker
				// for this victim.
				builder.getVictim().getCombatBuilder().setLastAttacker(builder.getCharacter());
				builder.getVictim().getLastCombat().reset();

				// Start cooldown if we're using magic and not autocasting.
				if (container.getCombatType() == CombatType.MAGIC && builder.getCharacter().isPlayer()) {
					Player player = (Player) builder.getCharacter();

					if (!player.isAutocast() && !player.getEquipment().containsAny(Player.magicStaves)) {
						if (!player.isSpecialActivated())
							player.getCombatBuilder().cooldown = 10;
						player.setCastSpell(null);
						player.getMovementQueue().setFollowCharacter(null);
						builder.determineStrategy();
					}
				}
				// Custom weapon hit amounts.
				if (builder.getCharacter().isPlayer()) {
					Player player = (Player) builder.getCharacter();

					if (player.getEquipment().get(3) != null) {
						switch (player.getEquipment().get(3).getId()) {
							case 20250:
							case 3078:
							case 19810:
							case 14559:
							case 10858:
							case 21077:
							case 14566:
							case 3071:
							case 20690:
							case 4744:
							case 14587:
							case 81:
							case 601:
							case 423:
							case 20691:
							case 20697:
							case 20693:
							case 3289:
							case 19661:
							case 7046:
							case 7047:
							case 3083:
							case 19730:
							case 17293:
							case 3619:
							case 625:
							case 665:
							case 17855: //ZIVA SWORD
								container.setHitAmount(2);
								break;
							/*
							 * Hit three times
							 */
							case 3277:
							case 3278:
							case 3626:
							case 8675:
							case 3290: //TYRANT DAGGER
							case 9122:
							case 7048:
							case 3279:
							case 3081:
							case 3092:
							case 7049:
							case 7151:
								container.setHitAmount(3);
								break;
							/*
							 * Hit four times
							 */
							case 4204:
							case 17848: //EVIL SWORD
							case 3280:
							case 3135:
							case 1849: //CRYPTIC SWORD
							case 9121:
							case 5234:
							case 17926:
							case 4762:
							case 3287:
							case 3508: //UPGRADED CRYPTIC
							case 14015:
							case 5180:
							case 20202:
							case 13254:
							case 6818:
							case 17892:
							case 3082:
							case 14038:
							case 17846:
								container.setHitAmount(4);
								break;
						}
					}
				}
				if (container.getHitDelay() == 0) { // An instant attack
					new CombatHitTask(builder, container).handleAttack();
				} else {
					TaskManager.submit(new CombatHitTask(builder, container, container.getHitDelay(), false));
				}

				builder.setContainer(null); // Fetch a brand new container on
											// next attack
			}

			// Reset the attacking entity.
			builder.attackTimer = builder.getStrategy() != null
					? builder.getStrategy().attackDelay(builder.getCharacter())
					: builder.getCharacter().getAttackSpeed();
			builder.getLastAttack().reset();
			builder.getCharacter().setEntityInteraction(builder.getVictim());
		}
	}
}
