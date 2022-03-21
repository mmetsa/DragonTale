package com.ruthlessps.world.content.skill.impl.woodcutting;

import com.ruthlessps.engine.task.Task;
import com.ruthlessps.engine.task.TaskManager;
import com.ruthlessps.engine.task.impl.QuestAssigner;
import com.ruthlessps.model.Animation;
import com.ruthlessps.model.GameMode;
import com.ruthlessps.model.GameObject;
import com.ruthlessps.model.Skill;
import com.ruthlessps.model.container.impl.Equipment;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.content.Achievements;
import com.ruthlessps.world.content.Achievements.AchievementData;
import com.ruthlessps.world.content.CustomObjects;
import com.ruthlessps.world.content.Sounds;
import com.ruthlessps.world.content.Sounds.Sound;
import com.ruthlessps.world.content.skill.impl.firemaking.Logdata;
import com.ruthlessps.world.content.skill.impl.firemaking.Logdata.logData;
import com.ruthlessps.world.entity.impl.player.Player;

public class Woodcutting {

	public static void cutWood(final Player player, final GameObject object, boolean restarting) {
		if (!restarting) {
			player.getSkillManager().stopSkilling();
		}
		if (player.getInventory().getFreeSlots() == 0) {
			player.getPacketSender().sendMessage("You don't have enough free inventory space.");
			return;
		}
		player.setPositionToFace(object.getPosition());
		final int objId = object.getId();
		final Hatchet h = Hatchet.forId(com.ruthlessps.world.content.skill.impl.woodcutting.Hatchet.getHatchet(player));
		if (h != null) {
			if (player.getSkillManager().getCurrentLevel(Skill.WOODCUTTING) >= h.getRequiredLevel()) {
				final Trees t = Trees.forId(objId);
				if (t != null) {
					player.setEntityInteraction(object);
					if (player.getSkillManager().getCurrentLevel(Skill.WOODCUTTING) >= t.getReq()) {
						if(t == Trees.NORMAL) {
							QuestAssigner.handleQuest(player, 1);
						} else if(t == Trees.OAK) {
							QuestAssigner.handleQuest(player, 2);
						} else if(t == Trees.MAPLE) {
							QuestAssigner.handleQuest(player, 3);
						} else if(t == Trees.WILLOW) {
							QuestAssigner.handleQuest(player, 4);
						} else if(t == Trees.YEW) {
							QuestAssigner.handleQuest(player, 5);
						} else if(t == Trees.MAGIC) {
							QuestAssigner.handleQuest(player, 6);
						}
						player.performAnimation(new Animation(h.getAnim()));
						int delay = Misc.getRandom(t.getTicks()
								- com.ruthlessps.world.content.skill.impl.woodcutting.Hatchet.getChopTimer(player, h))
								+ 1;
						player.setCurrentTask(new Task(1, player, false) {
							int cycle = 0, reqCycle = delay >= 2 ? delay : Misc.getRandom(1) + 1;

							@Override
							public void execute() {
								if (player.getInventory().getFreeSlots() == 0) {
									player.performAnimation(new Animation(65535));
									player.getPacketSender().sendMessage("You don't have enough free inventory space.");
									this.stop();
									return;
								}
								if (cycle != reqCycle) {
									cycle++;
									player.performAnimation(new Animation(h.getAnim()));
								} else if (cycle >= reqCycle) {
									int xp = t.getXp();
									if (lumberJack(player)) {
										xp *= 1.5;
									}
									player.getSkillManager().addExperience(Skill.WOODCUTTING, xp);
									cycle = 0;
									BirdNests.dropNest(player);
									QuestAssigner.handleQuest(player, 0);
									this.stop();
									if (!t.isMulti() || Misc.getRandom(10) == 2 && t != Trees.DREAM) {
										treeRespawn(player, object);
										player.getPacketSender().sendMessage("You've chopped the tree down.");
										player.performAnimation(new Animation(65535));
									} else {
										cutWood(player, object, true);
										player.getPacketSender().sendMessage("You get some logs..");
									}
									Achievements.doProgress(player, AchievementData.CHOP_A_TREE);
									Achievements.doProgress(player, AchievementData.CHOP_50_TREES);
									Achievements.doProgress(player, AchievementData.CHOP_500_TREES);
									Sounds.sendSound(player, Sound.WOODCUT);
									if (!(infernoAdze(player) && Misc.getRandom(5) <= 2)) {
										int amt = 1;
										if(player.getGameMode() == GameMode.HARDCORE && player.getEquipment().contains(9807)) {
											amt += 1;
										}
										if(t == Trees.DREAM) {
											if(player.getEquipment().contains(16361)) {
												amt = 2;
											} else if(player.getEquipment().contains(16363)) {
												amt = 3;
											} else if(player.getEquipment().contains(16365)) {
												amt = 4;
											} else if(player.getEquipment().contains(16367)) {
												amt = 5;
											}
										}
										player.getInventory().add(t.getReward(), amt);
									} else if (Misc.getRandom(5) <= 2) {
										logData fmLog = Logdata.getLogData(player, t.getReward());
										if (fmLog != null) {
											player.getSkillManager().addExperience(Skill.FIREMAKING, fmLog.getXp());
											player.getPacketSender().sendMessage(
													"Your Inferno Adze burns the log, granting you Firemaking experience.");
										}
									}
								}
							}
						});
						TaskManager.submit(player.getCurrentTask());
					} else {
						player.getPacketSender().sendMessage(
								"You need a Woodcutting level of at least " + t.getReq() + " to cut this tree.");
					}
				}
			} else {
				player.getPacketSender()
						.sendMessage("You do not have a hatchet which you have the required Woodcutting level to use.");
			}
		} else {
			player.getPacketSender().sendMessage("You do not have a hatchet that you can use.");
		}
	}

	public static boolean infernoAdze(Player player) {
		return player.getEquipment().get(Equipment.WEAPON_SLOT).getId() == 13661;
	}

	public static boolean lumberJack(Player player) {
		return player.getEquipment().get(Equipment.HEAD_SLOT).getId() == 10941
				&& player.getEquipment().get(Equipment.BODY_SLOT).getId() == 10939
				&& player.getEquipment().get(Equipment.LEG_SLOT).getId() == 10940
				&& player.getEquipment().get(Equipment.FEET_SLOT).getId() == 10933;
	}

	public static void treeRespawn(final Player player, final GameObject oldTree) {
		if (oldTree == null || oldTree.getPickAmount() >= 1) {
			return;
		}
		oldTree.setPickAmount(1);
		for (Player players : player.getLocalPlayers()) {
			if (players == null) {
				continue;
			}
			if (players.getInteractingObject() != null && players.getInteractingObject().getPosition()
					.equals(player.getInteractingObject().getPosition().copy())) {
				players.getSkillManager().stopSkilling();
				players.getPacketSender().sendClientRightClickRemoval();
			}
		}
		player.getPacketSender().sendClientRightClickRemoval();
		player.getSkillManager().stopSkilling();
		CustomObjects.globalObjectRespawnTask(new GameObject(1343, oldTree.getPosition().copy(), 10, 0), oldTree,
				20 + Misc.getRandom(10));
	}

}
