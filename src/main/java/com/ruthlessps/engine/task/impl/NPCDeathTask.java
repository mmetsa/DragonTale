package com.ruthlessps.engine.task.impl;

import com.ruthlessps.GameSettings;
import com.ruthlessps.drops.NPCDrops;
import com.ruthlessps.engine.task.Task;
import com.ruthlessps.engine.task.TaskManager;
import com.ruthlessps.model.Animation;
import com.ruthlessps.model.GroundItem;
import com.ruthlessps.model.Item;
import com.ruthlessps.model.Locations.Location;
import com.ruthlessps.model.Position;
import com.ruthlessps.model.Skill;
import com.ruthlessps.model.definitions.NpcDefinition;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.World;
import com.ruthlessps.world.content.Achievements;
import com.ruthlessps.world.content.HourlyNpc;
import com.ruthlessps.world.content.MoneyPouch;
import com.ruthlessps.world.content.Achievements.AchievementData;
import com.ruthlessps.world.content.ZoneTasks.ZoneData;
import com.ruthlessps.world.content.PlayerPanel;
import com.ruthlessps.world.content.PointsManager;
import com.ruthlessps.world.content.WildyTorva;
import com.ruthlessps.world.content.ZoneTasks;
import com.ruthlessps.world.content.combat.strategy.impl.TorvaBoss;
import com.ruthlessps.world.content.skill.impl.slayer.SlayerMaster;
import com.ruthlessps.world.entity.impl.GroundItemManager;
import com.ruthlessps.world.entity.impl.npc.NPC;
import com.ruthlessps.world.entity.impl.npc.Pet;
import com.ruthlessps.world.entity.impl.player.Player;
import com.ruthlessps.world.entity.impl.player.PlayerProcess;

/**
 * Represents an npc's death task, which handles everything an npc does before
 * and after their death animation (including it), such as dropping their drop
 * table items.
 * 
 * @author relex lawl
 */

public class NPCDeathTask extends Task {

	/**
	 * The npc setting off the death task.
	 */
	private final NPC npc;

	/**
	 * The amount of ticks on the task.
	 */
	private int ticks = 2;

	/**
	 * The player who killed the NPC
	 */
	private Player killer = null;

	/**
	 * The NPCDeathTask constructor.
	 * 
	 * @param npc
	 *            The npc being killed.
	 */

	public NPCDeathTask(NPC npc) {
		super(2);
		this.npc = npc;
		this.ticks = 2;
	}
	public int addFrags(int npcId) {
		NpcDefinition npcDef = NpcDefinition.forId(npcId);
		return Misc.random(npcDef.getCombatLevel());
	}
	
	public int[] bossIds = {515, 517, 518, 513, 6309, 6307, 3705, 11250, 2259, 2261, 3004, 3006, 514, 4999, 6770, 6771, 6772, 3587, 3589, 4222, 3588};

	@Override
	public void execute() {
		try {
			npc.setEntityInteraction(null);
			switch (ticks) {
			case 2:
				npc.getMovementQueue().setLockMovement(true).reset();
				killer = npc.getCombatBuilder().getKiller(npc.getId() != 4999 && npc.getId() != 585 && npc.getId() != 515 && npc.getId() != 514 &&npc.getId() != 517&&npc.getId() != 518&&npc.getId() != 3706&&npc.getId() != 3340&&npc.getId() != 799&&npc.getId() != 3705&&npc.getId() != 6307&&npc.getId() != 6309&&npc.getId() != 11250);
				//killer = npc.getCombatBuilder().getKiller(true);
				killer.getCombatBuilder().setLastAttacker(null);
				if (!(npc.getId() >= 6142 && npc.getId() <= 6145) && !(npc.getId() > 5070 && npc.getId() < 5081))
					npc.performAnimation(new Animation(npc.getDefinition().getDeathAnimation()));

				/** CUSTOM NPC DEATHS **/

				break;
			case 0:
				if (killer != null) {
					int total = 1;
					for(int i = 1; i <= 10000; i++) {
						total += killer.getDropKillCount(i);
					}
					if(Location.inMinigame(killer)) {
						killer.meleeKills++;
					}
					if(killer.getAttributes().getPet() != null) {
						Pet.addExperience(killer, Math.round(NpcDefinition.forId(npc.getId()).getCombatLevel()/10));
					}
					if(total%100 == 0) {
						if(npc.getId() == 76 ||npc.getId() == 5664||npc.getId() == 5400||npc.getId() == 8162||npc.getId() == 5407) {
							return;
						}
						if(killer.getInventory().getFreeSlots() > 0) {
							killer.getInventory().add(18768, 1);
							killer.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You received a PvM Loot Box");
						} else {
							GroundItemManager.spawnGroundItem(killer, new GroundItem(new Item(18768, 1),
									killer.getPosition().copy(), killer.getUsername(), false, 150, false, 200));
							killer.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You received a PvM Loot Box! It is dropped on the ground!");
						}
					}
					if(total%1000 == 0) {
						World.sendMessage("<col=FF7400>[DragonTale] <col=255>"+killer.getUsername()+" has killed a total of "+total+" NPCs");
					}
					if(total%2500 == 0) {
						if(npc.getId() == 76 ||npc.getId() == 5664||npc.getId() == 5400||npc.getId() == 8162||npc.getId() == 5407) {
							return;
						}
						if(killer.getInventory().getFreeSlots() > 0) {
							killer.getInventory().add(916, 1);
							killer.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You received a Omega PvM Box");
						} else {
							GroundItemManager.spawnGroundItem(killer, new GroundItem(new Item(916, 1),
									killer.getPosition().copy(), killer.getUsername(), false, 150, false, 200));
							killer.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You received a Omega PVM Box! It is dropped on the ground!");
						}
					}

					if(npc.getId() == 1231) {
						int pts = 1+Misc.random(3);
						killer.getPointsManager().setWithIncrease("prestige", pts);
						killer.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You received "+pts+" prestige points.");
						killer.getPointsManager().refreshPanel();
						
					}
					for(int i: bossIds) {
						if(npc.getId() == i) {
							Achievements.doProgress(killer, AchievementData.KILL_500_BOSSES);
						}
					}
					int points = 0;
					if (npc.getId() == 508) {
						points = 10;
					} else if (npc.getId() == 509) {
						points = 30;
					} else if (npc.getId() == 4500) {
						points = 180;
					} else if (npc.getId() == 1860) {
						points = 150;
					} else if (npc.getId() == 7235) {
						points = 165;
					} else if (npc.getId() == 3712) {
						points = 200;
					} else if (npc.getId() == 1871) {
						points = 225;
					} else if (npc.getId() == 1388) {
						points = 255;
					}
					if(killer.billperkill != 0) {
						MoneyPouch.addMoney(killer, killer.billperkill, true);
					}
					if(npc.getId() == 1618) {
						killer.getOffhandAttributes().increment();
					}
					if(npc.getId() == 6770) {
						NPC n = new NPC(6771, new Position(npc.getPosition().getX(), npc.getPosition().getY(), npc.getPosition().getZ()));
						World.register(n);
					}
					if(npc.getId() == 6771) {
						NPC n = new NPC(6772, new Position(npc.getPosition().getX(), npc.getPosition().getY(), npc.getPosition().getZ()));
						World.register(n);
					}
					if(npc.getId() == 6772) {
						NPC n = new NPC(6770, new Position(npc.getPosition().getX(), npc.getPosition().getY(), npc.getPosition().getZ()));
						World.register(n);
						NPCDrops.handleDrops(killer, new NPC(6770, npc.getPosition()));
						NPCDrops.handleDrops(killer, new NPC(6771, npc.getPosition()));
					}
					if(npc.getId() == 515) {
						if(Misc.random(1) == 1) {
							NPC n = new NPC(517, new Position(npc.getPosition().getX(), npc.getPosition().getY(), npc.getPosition().getZ()));
							World.register(n);
						} else {
							NPC n = new NPC(515, new Position(npc.getPosition().getX(), npc.getPosition().getY(), npc.getPosition().getZ()));
							World.register(n);
						}
					}
					if(npc.getId() == 517) {
						if(Misc.random(1) == 1) {
							NPC n = new NPC(518, new Position(npc.getPosition().getX(), npc.getPosition().getY(), npc.getPosition().getZ()));
							World.register(n);
						} else {
							NPC n = new NPC(517, new Position(npc.getPosition().getX(), npc.getPosition().getY(), npc.getPosition().getZ()));
							World.register(n);
						}
					}
					if(npc.getId() == 518) {
						if(Misc.random(1) == 1) {
							NPC n = new NPC(799, new Position(npc.getPosition().getX(), npc.getPosition().getY(), npc.getPosition().getZ()));
							World.register(n);
						} else {
							NPC n = new NPC(518, new Position(npc.getPosition().getX(), npc.getPosition().getY(), npc.getPosition().getZ()));
							World.register(n);
						}
					}
					if(npc.getId() == 799) {
						NPC n = new NPC(515, new Position(npc.getPosition().getX(), npc.getPosition().getY(), npc.getPosition().getZ()));
						World.register(n);
						NPCDrops.handleDrops(killer, new NPC(515, npc.getPosition()));
						NPCDrops.handleDrops(killer, new NPC(517, npc.getPosition()));
						NPCDrops.handleDrops(killer, new NPC(518, npc.getPosition()));
					}
					if(npc.getDefinition().getSuperior()) {
						if(Misc.getRandom(400) == 0) {
							killer.setSuperior(true);
							killer.getSlayer().handleSuperior(killer, npc.getDefinition().getSuperiorId(), true);
							killer.getPacketSender().sendMessage("A Superior Slayer Monster has spawned for you!");
							killer.getPacketSender().sendMessage("You have 10 minutes to kill it!");
						}
					}
					if(npc.getId() == 8057) {
						ZoneTasks.doTaskProgress(killer, ZoneData.MONEYBAG_ZONE, 2, 1);
					}
					if(npc.getId() == 3000) {
						ZoneTasks.doTaskProgress(killer, ZoneData.TRAINING_ZONE, 0, 1);
					}
					if(npc.getId() == 7235) {
						ZoneTasks.doTaskProgress(killer, ZoneData.POINTZONE, 1, 1);
					}
					if(npc.getDefinition().getIsSuperior()) {
						killer.setSuperior(false);
						killer.getSkillManager().addExperience(Skill.SLAYER, 10000);
						killer.getPointsManager().increasePoints("slayer", 100);
						killer.getPacketSender().sendMessage("You kill the Superior monster and receive 100 Slayer Points!");
						for(int i = 3; i > 0; i--) {
							NPCDrops.handleDrops(killer, new NPC(killer.getSlayer().getSlayerTask().getNpcId(), npc.getPosition()));
						}
						PlayerProcess.superiorCounter = 0;
						World.deregister(killer.getSuperiorId());
					}
					if(npc.getId() > 1 && npc.getId() != 508 && npc.getId() != 509) {
						PlayerPanel.refreshPanel(killer);
						if(Misc.random(700) == 1) {
							killer.getInventory().add(11137, 1);
							killer.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You received an Experience lamp.");
						}
							if(Misc.random(1400) == 1) {
								killer.getInventory().add(2528, 1);
								killer.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You received an Elite Experience lamp.");
							}
							if(Misc.random(250) == 0) {
								if(killer.getInventory().getFreeSlots() > 0) {
									killer.getInventory().add(611, 1);
									killer.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You received a Quest Crystal");
								} else {
									GroundItemManager.spawnGroundItem(killer, new GroundItem(new Item(611, 1),
											killer.getPosition().copy(), killer.getUsername(), false, 150, false, 200));
									killer.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You received a Quest Crystal! It is dropped on the ground!");
								}
							}
					}
					if(!killer.getToggleKC()) {
						int count = 0;
						count = 1+killer.getDropKillCount(npc.getId());
						killer.getPacketSender().sendMessage("@blu@Your "+npc.getDefinition().getName()+" kill count is: @or2@"+count);
					}
					if (points > 0) {
						killer.getPointsManager().setWithIncrease(GameSettings.SERVER_NAME, points);
						killer.getPointsManager().refreshPanel();
					}
					Achievements.doProgress(killer, AchievementData.KILL_10K_NPCS);
					if(npc.getId() == 3160 || npc.getId() == 3120 || npc.getId() == 3003 || npc.getId() == 3020) {
						if(killer.getDropKillCount(npc.getId())>= 20) {
							killer.getDropKillCount().put(npc.getId(), 20);
						}
					}
					if (npc.getId() == 3707 || npc.getId() == 3708 || npc.getId() == 3709) {
						TorvaBoss.counter--;
					}
					if (npc.getId() == 3491) {
						Achievements.doProgress(killer, AchievementData.DEFEAT_CULINAROMANCER);
					} else if (npc.getId() == 8528) {
						Achievements.doProgress(killer, AchievementData.DEFEATNOMAD);
					} else if (npc.getId() == 3711) {
						Achievements.doProgress(killer, AchievementData.KILL_25_MORTYS);
						
					} else if (npc.getId() == 508) {
						Achievements.doProgress(killer, AchievementData.KILL_25_MEWTWOS);
					} else if (npc.getId() == 509) {
						Achievements.doProgress(killer, AchievementData.KILL_25_CHARMELEONS);
					} else if (npc.getId() == 502) {
						Achievements.doProgress(killer, AchievementData.KILL_1_AMERICAN);
					} else if (npc.getId() == 699) {
						Achievements.doProgress(killer, AchievementData.KILL_1_CLOBE);
					} else if (npc.getId() == 4000) {
						Achievements.doProgress(killer, AchievementData.KILL_1_ARCHUS);
					}

					switch (npc.getId()) {
					case 504:
						Achievements.doProgress(killer, AchievementData.KILL50SKYS);
						break;
					case 698:
						Achievements.doProgress(killer, AchievementData.KILL50REDONEX);
						break;
					case 402:
						Achievements.doProgress(killer, AchievementData.KILL50GORGS);
						break;
					case 6307:
						Achievements.doProgress(killer, AchievementData.KILL_50_IKTOMIS);
						break;
					case 3705:
						Achievements.doProgress(killer, AchievementData.KILL_50_VORTEX);
						break;
					case 8211:
						Achievements.doProgress(killer, AchievementData.KILL_50_ZEUS);
						break;
					}
					killer.setNpcKills(killer.getNpcKills()+1);

					/** LOCATION KILLS **/
					if (npc.getLocation().handleKilledNPC(killer, npc)) {
						stop();
						return;
					}

					/** PARSE DROPS **/
					//BOSSES DROP TOP 3
					if(npc.getId() == 4999||npc.getId() == 514||npc.getId() == 515||npc.getId() == 517||npc.getId() == 518||npc.getId() == 3706||npc.getId() == 3340||npc.getId() == 799||npc.getId() == 3705||npc.getId() == 6307||npc.getId() == 6309||npc.getId() == 11250) {
						WildyTorva.handleDrops(npc);
					} else {
						com.ruthlessps.drops.NPCDrops.handleDrops(killer, npc);
						killer.increaseDKC(npc.getId());
					}
					if(killer.getInventory().getFreeSlots() >= 1||killer.getInventory().contains(14639)) {
						killer.getInventory().add(14639, addFrags(npc.getId()));
					}
					if(npc.getId() == 585) {
						WildyTorva.handleDrop(npc);
						killer.getInventory().add(13664, 200);
					}
					if(npc.getId() == killer.getSlayer().getSlayerTask().getNpcId()) {
						if(killer.getSlayer().getSlayerMaster() == SlayerMaster.BEGINNER_MASTER) {
							killer.getInventory().add(13664, Misc.random(1));
						}else if(killer.getSlayer().getSlayerMaster() == SlayerMaster.INTERMEDIATE_MASTER) {
							killer.getInventory().add(13664, 1+Misc.random(1));
						} else if(killer.getSlayer().getSlayerMaster() == SlayerMaster.HEROIC_MASTER) {
							killer.getInventory().add(13664, 2+Misc.random(2));
						} else if(killer.getSlayer().getSlayerMaster() == SlayerMaster.ELITE_MASTER) {
							killer.getInventory().add(13664, 4+Misc.random(2));
						} else if(killer.getSlayer().getSlayerMaster() == SlayerMaster.GOD_MASTER) {
							killer.getInventory().add(13664, Misc.random(8));
						}
						if(killer.getSlayer().getSlayerMaster() != SlayerMaster.GOD_MASTER) {
							killer.getPointsManager().setWithIncrease("slayer", 1);
						}
					}
					/** SLAYER **/
					killer.getSlayer().killedNpc(npc);
				}
				stop();
				break;
			}
			ticks--;
		} catch (Exception e) {
			e.printStackTrace();
			stop();
		}
	}

	@Override
	public void stop() {
		setEventRunning(false);
		npc.setDying(false);

		// respawn
		if (npc.getDefinition().getRespawnTime() > 0 && npc.getLocation() != Location.GRAVEYARD
				&& npc.getLocation() != Location.DUNGEONEERING) {
			TaskManager.submit(new NPCRespawnTask(npc, npc.getDefinition().getRespawnTime()));
		}

		World.deregister(npc);
	}
}