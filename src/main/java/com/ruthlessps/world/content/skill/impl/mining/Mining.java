package com.ruthlessps.world.content.skill.impl.mining;

import com.ruthlessps.engine.task.Task;
import com.ruthlessps.engine.task.TaskManager;
import com.ruthlessps.engine.task.impl.QuestAssigner;
import com.ruthlessps.model.Animation;
import com.ruthlessps.model.GameMode;
import com.ruthlessps.model.GameObject;
import com.ruthlessps.model.Locations;
import com.ruthlessps.model.Skill;
import com.ruthlessps.model.container.impl.Equipment;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.World;
import com.ruthlessps.world.content.Achievements;
import com.ruthlessps.world.content.Achievements.AchievementData;
import com.ruthlessps.world.content.CustomObjects;
import com.ruthlessps.world.content.Sounds;
import com.ruthlessps.world.content.Sounds.Sound;
import com.ruthlessps.world.content.skill.impl.mining.MiningData.Ores;
import com.ruthlessps.world.entity.impl.player.Player;

public class Mining {

	public static void oreRespawn(final Player player, final GameObject oldOre, Ores o) {
		if (oldOre == null || oldOre.getPickAmount() >= 1)
			return;
		oldOre.setPickAmount(1);
		for (Player players : player.getLocalPlayers()) {
			if (players == null)
				continue;
			if (players.getInteractingObject() != null && players.getInteractingObject().getPosition()
					.equals(player.getInteractingObject().getPosition().copy())) {
				players.getPacketSender().sendClientRightClickRemoval();
				players.getSkillManager().stopSkilling();
			}
		}
		player.getPacketSender().sendClientRightClickRemoval();
		player.getSkillManager().stopSkilling();
		CustomObjects.globalObjectRespawnTask(new GameObject(452, oldOre.getPosition().copy(), 10, 0), oldOre,
				o.getRespawn());
	}

	private static void handleAfkCrystal(Player player, int pickaxe) {
		switch (pickaxe) {
			case 16307: //Water
				if(Misc.random(250) == 1) {
					player.getInventory().add(17662, 1);
				}
				break;
			case 16299: // Earth
				if(Misc.random(250) == 1) {
					player.getInventory().add(17660, 1);
				}
				break;
			case 16305: // Fire
				if(Misc.random(250) == 1) {
					player.getInventory().add(17654, 1);
				}
				break;
			case 16303: // Air
				if(Misc.random(250) == 1) {
					player.getInventory().add(17656, 1);
				}
				break;
			case 16142: // Melee
				player.getAttributes().incrementMeleeBoost();
				break;
			case 16143: // Magic
				player.getAttributes().incrementMagicBoost();
				break;
			case 16144: // Ranged
				player.getAttributes().incrementRangedBoost();
				break;
		}
	}

	public static void startMining(final Player player, final GameObject oreObject) {
		player.getSkillManager().stopSkilling();
		player.getPacketSender().sendInterfaceRemoval();
		if (!Locations.goodDistance(player.getPosition().copy(), oreObject.getPosition(), 1)
				&& oreObject.getId() != 24444 && oreObject.getId() != 24445 && oreObject.getId() != 38660 && oreObject.getId() !=57164) {
			return;
		}
		if (player.busy() || player.getCombatBuilder().isBeingAttacked() || player.getCombatBuilder().isAttacking()) {
			player.getPacketSender().sendMessage("You cannot do that right now.");
			return;
		}
		if (player.getInventory().getFreeSlots() == 0) {
			player.getPacketSender().sendMessage("You do not have any free inventory space left.");
			return;
		}
		player.setInteractingObject(oreObject);
		player.setPositionToFace(oreObject.getPosition());
		final Ores o = MiningData.forRock(oreObject.getId());
		final boolean giveGem = o != Ores.Rune_essence && o != Ores.Pure_essence && o != Ores.CRYSTALS;
		final int reqCycle = o == Ores.Runite ? 6 + Misc.getRandom(2) : Misc.getRandom(o.getTicks() - 1);
		final int pickaxe = MiningData.getPickaxe(player);
		final int miningLevel = player.getSkillManager().getCurrentLevel(Skill.MINING);
		if (pickaxe > 0) {
			if (miningLevel >= o.getLevelReq()) {
				final MiningData.Pickaxe p = MiningData.forPick(pickaxe);
				if (miningLevel >= p.getReq()) {
					player.performAnimation(new Animation(p.getAnim()));
					final int delay = o.getTicks() - MiningData.getReducedTimer(player, p);
					player.setCurrentTask(new Task(delay >= 2 ? delay : 1, player, false) {
						int cycle = 0;

						@Override
						public void execute() {
							if (player.getInteractingObject() == null
									|| player.getInteractingObject().getId() != oreObject.getId()) {
								player.getSkillManager().stopSkilling();
								player.performAnimation(new Animation(65535));
								stop();
								return;
							}
							if (player.getInventory().getFreeSlots() == 0) {
								player.performAnimation(new Animation(65535));
								stop();
								player.getPacketSender()
										.sendMessage("You do not have any free inventory space left.");
								return;
							}
							if (cycle != reqCycle) {
								cycle++;
								player.performAnimation(new Animation(p.getAnim()));
							}
							if (giveGem) {
								boolean onyx = (o == Ores.Runite || o == Ores.CRASHED_STAR)
										&& Misc.getRandom(o == Ores.CRASHED_STAR ? 20000 : 5000) == 1;
								if (onyx || Misc.getRandom(o == Ores.CRASHED_STAR ? 35 : 50) == 15) {
									int gemId = onyx ? 6571
											: MiningData.RANDOM_GEMS[(int) (MiningData.RANDOM_GEMS.length
													* Math.random())];
									player.getInventory().add(gemId, 1);
									player.getPacketSender().sendMessage("You've found a gem!");
									if (gemId == 6571) {
										String s = o == Ores.Runite ? "Runite ore" : "Crashed star";
										World.sendMessage("<col=009966> " + player.getUsername()
												+ " has just received an Uncut Onyx from mining a " + s + "!");
									}
								}
							}
							if (cycle == reqCycle) {
								if(o == Ores.Tin) {
									QuestAssigner.handleQuest(player, 8);
								} else if(o == Ores.Copper) {
									QuestAssigner.handleQuest(player, 9);
								} else if(o == Ores.Iron) {
									QuestAssigner.handleQuest(player, 10);
								} else if(o == Ores.Coal) {
									QuestAssigner.handleQuest(player, 11);
								} else if(o == Ores.Pure_essence) {
									QuestAssigner.handleQuest(player, 12);
								} else if(o == Ores.Rune_essence) {
									QuestAssigner.handleQuest(player, 13);
								} else if(o == Ores.Gold) {
									QuestAssigner.handleQuest(player, 14);
								} else if(o == Ores.Mithril) {
									QuestAssigner.handleQuest(player, 15);
								} else if(o == Ores.Adamantite) {
									QuestAssigner.handleQuest(player, 16);
								} else if(o == Ores.Runite) {
									QuestAssigner.handleQuest(player, 17);
								}
								QuestAssigner.handleQuest(player, 7);
								Achievements.doProgress(player, AchievementData.MINE_SOME_ORE);
								Achievements.doProgress(player, AchievementData.MINE_50_ORES);
								Achievements.doProgress(player, AchievementData.MINE_500_ORES);
								if(o == Ores.CRYSTALS) {
									handleAfkCrystal(player, pickaxe);
								}
								if (o.getItemId() != -1) {
									if(player.getGameMode() == GameMode.HARDCORE && player.getEquipment().contains(9792)) {
										player.getInventory().add(o.getItemId(), 2);
									} else {
										player.getInventory().add(o.getItemId(), 1);
									}
								}
								player.getSkillManager().addExperience(Skill.MINING, (int) (o.getXpAmount() * 1.4));
								if (o == Ores.CRASHED_STAR) {
									player.getPacketSender().sendMessage("You mine the crashed star..");
								} else {
									player.getPacketSender().sendMessage("You mine some ore.");
								}
								Sounds.sendSound(player, Sound.MINE_ITEM);
								cycle = 0;
								this.stop();
								if (o.getRespawn() > 0) {
									player.performAnimation(new Animation(65535));
									oreRespawn(player, oreObject, o);
								} else {
									if (oreObject.getId() == 38660) {
										player.performAnimation(new Animation(65535));
									}
									startMining(player, oreObject);
								}
							}
						}
					});
					TaskManager.submit(player.getCurrentTask());
				} else {
					player.getPacketSender().sendMessage(
							"You need a Mining level of at least " + p.getReq() + " to use this pickaxe.");
				}
			} else {
				player.getPacketSender().sendMessage(
						"You need a Mining level of at least " + o.getLevelReq() + " to mine this rock.");
			}
		} else {
			player.getPacketSender().sendMessage("You don't have a pickaxe to mine this rock with.");
		}
	}
}
