package com.ruthlessps.net.packet.impl;

import com.ruthlessps.GameSettings;
import com.ruthlessps.engine.task.Task;
import com.ruthlessps.engine.task.TaskManager;
import com.ruthlessps.engine.task.impl.ForceMovementTask;
import com.ruthlessps.engine.task.impl.QuestAssigner;
import com.ruthlessps.engine.task.impl.WalkToTask;
import com.ruthlessps.engine.task.impl.WalkToTask.FinalizedMovementTask;
import com.ruthlessps.model.Animation;
import com.ruthlessps.model.Direction;
import com.ruthlessps.model.DwarfCannon;
import com.ruthlessps.model.Flag;
import com.ruthlessps.model.ForceMovement;
import com.ruthlessps.model.GameObject;
import com.ruthlessps.model.Graphic;
import com.ruthlessps.model.Locations.Location;
import com.ruthlessps.model.MagicSpellbook;
import com.ruthlessps.model.PlayerRanks.DonorRights;
import com.ruthlessps.model.PlayerRanks.PlayerRights;
import com.ruthlessps.model.Position;
import com.ruthlessps.model.Prayerbook;
import com.ruthlessps.model.Skill;
import com.ruthlessps.model.definitions.GameObjectDefinition;
import com.ruthlessps.model.input.impl.DonateToDropWell;
import com.ruthlessps.model.input.impl.DonateToPointsWell;
import com.ruthlessps.model.input.impl.DonateToWell;
import com.ruthlessps.model.input.impl.EnterAmountOfLogsToAdd;
import com.ruthlessps.net.packet.Packet;
import com.ruthlessps.net.packet.PacketListener;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.World;
import com.ruthlessps.world.clip.region.RegionClipping;
import com.ruthlessps.world.content.CustomObjects;
import com.ruthlessps.world.content.MysteryChest;
import com.ruthlessps.world.content.WildernessObelisks;
import com.ruthlessps.world.content.ZoneTasks;
import com.ruthlessps.world.content.combat.magic.Autocasting;
import com.ruthlessps.world.content.combat.prayer.CurseHandler;
import com.ruthlessps.world.content.combat.prayer.PrayerHandler;
import com.ruthlessps.world.content.combat.range.DwarfMultiCannon;
import com.ruthlessps.world.content.combat.weapon.CombatSpecial;
import com.ruthlessps.world.content.dialogue.DialogueManager;
import com.ruthlessps.world.content.minigames.impl.Dueling;
import com.ruthlessps.world.content.minigames.impl.Dueling.DuelRule;
import com.ruthlessps.world.content.minigames.impl.Nomad;
import com.ruthlessps.world.content.minigames.impl.OffhandMinigame;
import com.ruthlessps.world.content.minigames.impl.RecipeForDisaster;
import com.ruthlessps.world.content.skill.impl.agility.Agility;
import com.ruthlessps.world.content.skill.impl.construction.Construction;
import com.ruthlessps.world.content.skill.impl.crafting.Flax;
import com.ruthlessps.world.content.skill.impl.dungeoneering.Dungeoneering;
import com.ruthlessps.world.content.skill.impl.fishing.Fishing;
import com.ruthlessps.world.content.skill.impl.fishing.Fishing.Spot;
import com.ruthlessps.world.content.skill.impl.hunter.Hunter;
import com.ruthlessps.world.content.skill.impl.hunter.PuroPuro;
import com.ruthlessps.world.content.skill.impl.mining.Mining;
import com.ruthlessps.world.content.skill.impl.mining.MiningData;
import com.ruthlessps.world.content.skill.impl.mining.Prospecting;
import com.ruthlessps.world.content.skill.impl.runecrafting.Runecrafting;
import com.ruthlessps.world.content.skill.impl.runecrafting.RunecraftingData;
import com.ruthlessps.world.content.skill.impl.smithing.EquipmentMaking;
import com.ruthlessps.world.content.skill.impl.smithing.Smelting;
import com.ruthlessps.world.content.skill.impl.thieving.Stalls;
import com.ruthlessps.world.content.skill.impl.woodcutting.Hatchet;
import com.ruthlessps.world.content.skill.impl.woodcutting.Trees;
import com.ruthlessps.world.content.skill.impl.woodcutting.Woodcutting;
import com.ruthlessps.world.content.teleportation.TeleportHandler;
import com.ruthlessps.world.content.teleportation.TeleportManager;
import com.ruthlessps.world.content.teleportation.TeleportType;
import com.ruthlessps.world.entity.impl.player.Player;

import java.util.EnumSet;

/**
 * This packet listener is called when a player clicked on a game object.
 *
 * @author relex lawl
 */

public class ObjectActionPacketListener implements PacketListener {

	public static final int FIRST_CLICK = 132, SECOND_CLICK = 252, THIRD_CLICK = 70, FOURTH_CLICK = 234,
			FIFTH_CLICK = 228;

	private static void fifthClick(final Player player, Packet packet) {
		final int id = packet.readUnsignedShortA();
		final int y = packet.readUnsignedShortA();
		final int x = packet.readShort();
		final Position position = new Position(x, y, player.getPosition().getZ());
		final GameObject gameObject = new GameObject(id, position);
		if (!Construction.buildingHouse(player)) {
			if (id > 0 && !RegionClipping.objectExists(gameObject)) {
				return;
			}
		}
		player.setPositionToFace(gameObject.getPosition());
		int distanceX = player.getPosition().getX() - position.getX();
		int distanceY = player.getPosition().getY() - position.getY();
		if (distanceX < 0) {
			distanceX = -distanceX;
		}
		if (distanceY < 0) {
			distanceY = -distanceY;
		}
		int size = distanceX > distanceY ? distanceX : distanceY;
		gameObject.setSize(size);
		player.setInteractingObject(gameObject);
		player.setWalkToTask(new WalkToTask(player, position, gameObject.getSize(), new FinalizedMovementTask() {
			@Override
			public void execute() {
				switch (id) {
				}
				Construction.handleFifthObjectClick(x, y, id, player);
			}
		}));
	}

	/**
	 * The PacketListener logger to debug information and print out errors.
	 */
	// private final static Logger logger =
	// Logger.getLogger(ObjectActionPacketListener.class);
	private static void firstClick(final Player player, Packet packet) {
		final int x = packet.readLEShortA();
		final int id = packet.readUnsignedShort();
		final int y = packet.readUnsignedShortA();
		final Position position = new Position(x, y, player.getPosition().getZ());
		final GameObject gameObject = new GameObject(id, position);
		if (id > 0 && id != 15480 && id != 6 && !RegionClipping.objectExists(gameObject)) {
			return;
		}
		int distanceX = player.getPosition().getX() - position.getX();
		int distanceY = player.getPosition().getY() - position.getY();
		if (distanceX < 0) {
			distanceX = -distanceX;
		}
		if (distanceY < 0) {
			distanceY = -distanceY;
		}
		int size = distanceX > distanceY ? GameObjectDefinition.forId(id).getSizeX()
				: GameObjectDefinition.forId(id).getSizeY();
		if (size <= 0) {
			size = 1;
		}
		gameObject.setSize(size);
		if (player.getMovementQueue().isLockMovement()) {
			return;
		}
		if (player.getRights() == PlayerRights.DEVELOPER) {
			player.getPacketSender()
					.sendMessage("First click object id; [id, position] : [" + id + ", " + position.toString() + "]");
		}
		player.setInteractingObject(gameObject)
				.setWalkToTask(new WalkToTask(player, position, gameObject.getSize(), new FinalizedMovementTask() {
					@Override
					public void execute() {
						player.setPositionToFace(gameObject.getPosition());
						if (Trees.forId(id) != null) {
							Woodcutting.cutWood(player, gameObject, false);
							return;
						}
						if (MiningData.forRock(gameObject.getId()) != null) {
							Mining.startMining(player, gameObject);
							return;
						}
						if (player.getFarming().click(player, x, y, 1)) {
							return;
						}
						if (Runecrafting.runecraftingAltar(player, gameObject.getId())) {
							RunecraftingData.RuneData rune = RunecraftingData.RuneData.forId(gameObject.getId());
							if (rune == null) {
								return;
							}
							Runecrafting.craftRunes(player, rune);
							return;
						}
						if (Agility.handleObject(player, gameObject)) {
							return;
						}
						if (player.getLocation() == Location.WILDERNESS
								&& WildernessObelisks.handleObelisk(gameObject.getId())) {
							return;
						}
						switch (id) {
							case 8987:
								TeleportHandler.teleportPlayer(player, GameSettings.DEFAULT_POSITION.copy(), player.getSpellbook().getTeleportType());
								break;
							case 2468:
							case 20718:
								OffhandMinigame.moveToNextRoom(player, player.getOffhandAttributes().getStage());
								break;
							case 15480:
								Construction.newHouse(player);
								Construction.enterHouse(player, player, true);
								break;
							case 2073:
								if(player.getInventory().getFreeSlots() < 1) {
									return;
								} else {
									player.performAnimation(new Animation(7268));
									player.getInventory().add(1963, 1);
									player.getPacketSender().sendMessage("You pick a banana!");
								}
								break;
							case 2156:
								if(gameObject.getX() == 2655 && gameObject.getY() == 10020) {
									TeleportHandler.teleportPlayer(player, new Position(2655, 10023, 0),
											player.getSpellbook().getTeleportType());
								} else if(gameObject.getX() == 2655 && gameObject.getY() == 10020) {
									if(player.getDropKillCount(1618) < 500) {
										player.getPA().sendMessage("You need 500 Small Moneybag kills to enter this zone.");
										return;
									}
									TeleportHandler.teleportPlayer(player, new Position(2662, 10016, 0),
											player.getSpellbook().getTeleportType());
								} else if(gameObject.getX() == 2655 && gameObject.getY() == 10011) {
									if(player.getDropKillCount(8060) < 1500) {
										player.getPA().sendMessage("You need 1500 Medium Moneybag kills to enter this zone.");
										return;
									}
                                    ZoneTasks.doTaskProgress(player, ZoneTasks.ZoneData.MONEYBAG_ZONE, 0, 1);
									TeleportHandler.teleportPlayer(player, new Position(2656, 10009, 0), player.getSpellbook().getTeleportType());
								} else if(gameObject.getX() == 2651 && gameObject.getY() == 10015) {
									if(player.getDropKillCount(8060) < 3000||player.getDropKillCount(8063) < 3000||player.getDropKillCount(1618) < 3000) {
										player.getPA().sendMessage("You need 3000 kills in Small, Medium and Large Moneybags to Enter.");
										return;
									}
									TeleportHandler.teleportPlayer(player, new Position(2649, 10016, 0),
											player.getSpellbook().getTeleportType());
								}
							case 13389:
								if(gameObject.getX() == 3174 && gameObject.getY() == 3028) {
									MysteryChest.handleChest(player, MysteryChest.americanRewards, 991);
								} else if(gameObject.getX() == 3168 && gameObject.getY() == 3033) {
									MysteryChest.handleChest(player, MysteryChest.oreoRewards, 993);
								} else if(gameObject.getX() == 1888 && gameObject.getY() == 4820) {
									MysteryChest.handleChest(player, MysteryChest.skyRewards, 1507);
								} else if(gameObject.getX() == 1894 && gameObject.getY() == 4825) {
									MysteryChest.handleChest(player, MysteryChest.darthRewards, 1542);
								} else if(gameObject.getX() == 1952 && gameObject.getY() == 4825) {
									MysteryChest.handleChest(player, MysteryChest.cashRewards, 1545);
								} else if(gameObject.getX() == 1958 && gameObject.getY() == 4825) {
									MysteryChest.handleChest(player, MysteryChest.silverRewards, 1546);
								} else if(gameObject.getX() == 2016 && gameObject.getY() == 4824) {
									MysteryChest.handleChest(player, MysteryChest.clobeRewards, 1547);
								} else if(gameObject.getX() == 2022 && gameObject.getY() == 4822) {
									MysteryChest.handleChest(player, MysteryChest.prostexRewards, 1548);
								} else if(gameObject.getX() == 2592 && gameObject.getY() == 4629) {
									MysteryChest.handleChest(player, MysteryChest.redonexRewards, 1586);
								} else if(gameObject.getX() == 2598 && gameObject.getY() == 4630) {
									MysteryChest.handleChest(player, MysteryChest.legionRewards, 2401);
								} else if(gameObject.getX() == 2016 && gameObject.getY() == 4501) {
									MysteryChest.handleChest(player, MysteryChest.zarthyxRewards, 2404);
								} else if(gameObject.getX() == 2022 && gameObject.getY() == 4505) {
									MysteryChest.handleChest(player, MysteryChest.rucordRewards, 2409);
								} else if(gameObject.getX() == 2784 && gameObject.getY() == 3861) {
									MysteryChest.handleChest(player, MysteryChest.archusRewards, 927);
								} else if(gameObject.getX() == 2790 && gameObject.getY() == 3862) {
									MysteryChest.handleChest(player, MysteryChest.razielRewards, 3813);
								} else if(gameObject.getX() == 2272 && gameObject.getY() == 5015) {
									MysteryChest.handleChest(player, MysteryChest.gorgRewards, 3814);
								} else if(gameObject.getX() == 2278 && gameObject.getY() == 5014) {
									MysteryChest.handleChest(player, MysteryChest.harnanRewards, 1588);
								} else if(gameObject.getX() == 2400 && gameObject.getY() == 5015) {
									MysteryChest.handleChest(player, MysteryChest.landazarRewards, 1590);
								} else if(gameObject.getX() == 2406 && gameObject.getY() == 5017) {
									MysteryChest.handleChest(player, MysteryChest.xintorRewards, 1591);
								}
								break;
						case 21175:
							if(player.getDonor().ordinal() > DonorRights.SPONSOR.ordinal()) {
								DialogueManager.start(player, 15004);
								player.setDialogueActionId(15005);
								return;
							}
							if(player.getInventory().contains(13260)) {
								DialogueManager.start(player, 15004);
								player.setDialogueActionId(15005);
								return;
							}
							if((player.getDropKillCount(3160) < 20 || player.getDropKillCount(3020) < 20 || player.getDropKillCount(3003) < 20 || player.getDropKillCount(3120) < 20 )) {
								player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to have killed 20 of each miniboss to travel to the Boss lair!");
								return;
							} else {
								DialogueManager.start(player, 15004);
								player.setDialogueActionId(15005);
							}
							break;
						case 2471:
							if(player.getDonor().ordinal() > DonorRights.DONOR.ordinal()) {
								TeleportHandler.teleportPlayer(player, new Position(2340, 4757, 0),
										player.getSpellbook().getTeleportType());
							} else {
								if(player.getDropKillCount(998) < 300) {
									player.getPA().sendMessage("<img=10><col=FF7400><shad=0>You need to have 300 Camo Torva kills to proceed!");
									return;
								} else {
									TeleportHandler.teleportPlayer(player, new Position(2340, 4757, 0),
											player.getSpellbook().getTeleportType());
								}
							}
							break;
						case 2472:
							if(player.getDonor().ordinal() > DonorRights.DELUXE_DONOR.ordinal()) {
								TeleportHandler.teleportPlayer(player, new Position(2353, 4762, 0),
										player.getSpellbook().getTeleportType());
							} else {
								if(player.getDropKillCount(999) < 300) {
									player.getPA().sendMessage("<img=10><col=FF7400><shad=0>You need to have 300 Winter Camo Torva kills to proceed!");
									return;
								} else {
									TeleportHandler.teleportPlayer(player, new Position(2353, 4762, 0),
											player.getSpellbook().getTeleportType());
								}
							}
							break;
						case 11232://IMBUE
							player.getBunnyhop().endGame(true);
							break;
						case 113339://CASH
							player.getBunnyhop().endGame(true);
							break;
						case 2469:
							if(player.getDonor().ordinal() > DonorRights.SPONSOR.ordinal()) {
								TeleportHandler.teleportPlayer(player, new Position(2358, 4748, 0),
										player.getSpellbook().getTeleportType());
							} else {
								if(player.getDropKillCount(1000) < 300) {
									player.getPA().sendMessage("<img=10><col=FF7400><shad=0>You need to have 300 BS Camo Torva kills to proceed!");
									return;
								} else {
									TeleportHandler.teleportPlayer(player, new Position(2358, 4748, 0),
											player.getSpellbook().getTeleportType());
								}
							}
							break;
						case 2470:
							TeleportHandler.teleportPlayer(player, new Position(2903, 2708, 0),
									player.getSpellbook().getTeleportType());
							player.getPacketSender().sendMessage("You teleport home.");
							break;
						case 4788:
						case 4787:
							if(player.getSkillManager().getCurrentLevel(Skill.AGILITY)<75) {
								player.getPacketSender().sendMessage("@red@You must have at least 75 agility to slip through here!");
							} else if(player.getSkillManager().getCurrentLevel(Skill.SLAYER)<92) {
								player.getPacketSender().sendMessage("@red@You must have at least 92 slayer to enter this!");
							} else {
								player.moveTo(new Position(2721, 2765, 0));
								player.getPacketSender().sendMessage("@blu@You manage to slip through the hole!");
							}
							break;
						case 23271:
							if (player.getInventory().containsAny(GameSettings.BANNEDWILD_ITEMS)) {
								player.sendMessage("You have items that are not allowed in the wilderness.");
							} else if (player.getEquipment().containsAny(GameSettings.BANNEDWILD_ITEMS)) {
								player.sendMessage("You're wearing an item that is now allowed in the wilderness.");
							} else if (player.getPosition().getY() <= 3520) {
								player.getPacketSender().sendInterface(35_000);
							} else if (player.getPosition().getY() > 3520 && player.getForceMovement() == null) {
								player.getPacketSender().sendInterfaceRemoval();
								player.getMovementQueue().reset();
								player.performAnimation(new Animation(6132));
								final Position crossDitch = new Position(0,
										player.getPosition().getY() < 3522 ? 3 : -3);
								TaskManager.submit(
										new ForceMovementTask(player, 3, new ForceMovement(player.getPosition().copy(),
												crossDitch, 33, 60, crossDitch.getY() == 3 ? 0 : 2, 6312)));
							}
							break;
						case 5259:
							if (player.getPosition().getY() == 4757)
								player.moveTo(player.getPosition().translate(0, 2));
							else
								player.moveTo(player.getPosition().translate(0, -2)); // okay try this

							break;
						case 38700:
							player.moveTo(new Position(3041, 2855));
							break;
						case 2465:
							player.getPacketSender().sendMessage(
									"@blu@Welcome to the free-for-all arena! You will not lose any items on death here.");
							player.moveTo(new Position(2815, 5511));
							break;
						case 45803:
						case 1767:
							DialogueManager.start(player, 114);
							player.setDialogueActionId(72);
							break;
						case 7352:
							if (Dungeoneering.doingDungeoneering(player) && player.getMinigameAttributes()
									.getDungeoneeringAttributes().getParty().getGatestonePosition() != null) {
								player.moveTo(player.getMinigameAttributes().getDungeoneeringAttributes().getParty()
										.getGatestonePosition());
								player.setEntityInteraction(null);
								player.getPacketSender().sendMessage("You are teleported to your party's gatestone.");
								player.performGraphic(new Graphic(1310));
							} else {
								player.getPacketSender().sendMessage(
										"Your party must drop a Gatestone somewhere in the dungeon to use this portal.");
							}
							break;
						case 7353:
							player.moveTo(new Position(2439, 4956, player.getPosition().getZ()));
							break;
						case 7321:
							player.moveTo(new Position(2452, 4944, player.getPosition().getZ()));
							break;
						case 7322:
							player.moveTo(new Position(2455, 4964, player.getPosition().getZ()));
							break;
						case 7315:
							player.moveTo(new Position(2447, 4956, player.getPosition().getZ()));
							break;
						case 7316:
							player.moveTo(new Position(2471, 4956, player.getPosition().getZ()));
							break;
						case 7318:
							player.moveTo(new Position(2464, 4963, player.getPosition().getZ()));
							break;
						case 7319:
							player.moveTo(new Position(2467, 4940, player.getPosition().getZ()));
							break;
						case 7324:
							player.moveTo(new Position(2481, 4956, player.getPosition().getZ()));
							break;
						case 11356:
							player.moveTo(new Position(2860, 9741));
							player.getPacketSender().sendMessage("You step through the portal..");
							break;
						case 47180:
							if (player.getDonor() != DonorRights.SPONSOR) {
								player.getPacketSender().sendMessage("You must be at least a Sponsor to use this.");
								return;
							}
							player.getPacketSender().sendMessage("You activate the device..");
							player.moveTo(new Position(2586, 3912));
							break;
						case 10091:
						case 8702:
							if (gameObject.getId() == 8702) {
								if (player.getDonor() != DonorRights.SPONSOR
										&& player.getDonor() != DonorRights.DELUXE_DONOR) {
									player.getPacketSender()
											.sendMessage("You must be at least a deluxe donor to use this.");
									return;
								}
							}
							Fishing.setupFishing(player, Spot.ROCKTAIL);
							break;
						case 2026:
							player.setEntityInteraction(gameObject);
							Fishing.setupFishing(player, Fishing.forSpot(gameObject.getId(), false));
							break;
						case 9319:
							if (player.getSkillManager().getCurrentLevel(Skill.AGILITY) < 61) {
								player.getPacketSender().sendMessage(
										"You need an Agility level of at least 61 or higher to climb this");
								return;
							}
							if (player.getPosition().getZ() == 0) {
								player.moveTo(new Position(3422, 3549, 1));
							} else if (player.getPosition().getZ() == 1) {
								if (gameObject.getPosition().getX() == 3447) {
									player.moveTo(new Position(3447, 3575, 2));
								} else {
									player.moveTo(new Position(3447, 3575, 0));
								}
							}
							break;

						case 9320:
							if (player.getSkillManager().getCurrentLevel(Skill.AGILITY) < 61) {
								player.getPacketSender().sendMessage(
										"You need an Agility level of at least 61 or higher to climb this");
								return;
							}
							if (player.getPosition().getZ() == 1) {
								player.moveTo(new Position(3422, 3549, 0));
							} else if (player.getPosition().getZ() == 0) {
								player.moveTo(new Position(3447, 3575, 1));
							} else if (player.getPosition().getZ() == 2) {
								player.moveTo(new Position(3447, 3575, 1));
							}
							player.performAnimation(new Animation(828));
							break;
						case 2274:
							if (gameObject.getPosition().getX() == 2912 && gameObject.getPosition().getY() == 5300) {
								player.moveTo(new Position(2914, 5300, 1));
							} else if (gameObject.getPosition().getX() == 2914
									&& gameObject.getPosition().getY() == 5300) {
								player.moveTo(new Position(2912, 5300, 2));
							} else if (gameObject.getPosition().getX() == 2919
									&& gameObject.getPosition().getY() == 5276) {
								player.moveTo(new Position(2918, 5274));
							} else if (gameObject.getPosition().getX() == 2918
									&& gameObject.getPosition().getY() == 5274) {
								player.moveTo(new Position(2919, 5276, 1));
							} else if (gameObject.getPosition().getX() == 3001
									&& gameObject.getPosition().getY() == 3931
									|| gameObject.getPosition().getX() == 3652
											&& gameObject.getPosition().getY() == 3488) {
								player.moveTo(GameSettings.DEFAULT_POSITION.copy());
								player.getPacketSender().sendMessage("The portal teleports you to Edgeville.");
							} else if (gameObject.getX() == 3174 && gameObject.getY() == 3030) {
								if(player.meleeKills >= 25) {
									player.moveTo(new Position(3165, 3031, 0));
									player.getPA().sendMessage("You advance to the next level.");
									player.meleeKills = 0;
								} else {
									player.getPA().sendMessage("You need 25 kills to advance. You have " + player.meleeKills);
								}
							} else if (gameObject.getX() == 3168 && gameObject.getY() == 3031) {
								if(player.meleeKills >= 75) {
									player.moveTo(new Position(1885, 4824, 0));
									player.getPA().sendMessage("You advance to the next level.");
									player.meleeKills = 0;
								} else {
									player.getPA().sendMessage("You need 75 kills to advance. You have " + player.meleeKills);
								}
							} else if (gameObject.getX() == 1888 && gameObject.getY() == 4822) {
								if(player.meleeKills >= 150) {
									player.moveTo(new Position(1896, 4824, 0));
									player.getPA().sendMessage("You advance to the next level.");
									player.meleeKills = 0;
								} else {
									player.getPA().sendMessage("You need 150 kills to advance. You have " + player.meleeKills);
								}
							} else if (gameObject.getX() == 1894 && gameObject.getY() == 4822) {
								if(player.meleeKills >= 400) {
									player.moveTo(new Position(1949, 4825, 0));
									player.getPA().sendMessage("You advance to the next level.");
									player.meleeKills = 0;
								} else {
									player.getPA().sendMessage("You need 400 kills to advance. You have " + player.meleeKills);
								}
							} else if (gameObject.getX() == 1952 && gameObject.getY() == 4823) {
								if(player.meleeKills >= 1000) {
									player.moveTo(new Position(1960, 4824, 0));
									player.getPA().sendMessage("You advance to the next level.");
									player.meleeKills = 0;
								} else {
									player.getPA().sendMessage("You need 1000 kills to advance. You have " + player.meleeKills);
								}
							} else if (gameObject.getX() == 2016 && gameObject.getY() == 4822) {
								if(player.meleeKills >= 25) {
									player.moveTo(new Position(2025, 4823, 0));
									player.getPA().sendMessage("You advance to the next level.");
									player.meleeKills = 0;
								} else {
									player.getPA().sendMessage("You need 25 kills to advance. You have " + player.meleeKills);
								}
							} else if (gameObject.getX() == 2022 && gameObject.getY() == 4825) {
								if(player.meleeKills >= 75) {
									player.moveTo(new Position(2589, 4632, 0));
									player.getPA().sendMessage("You advance to the next level.");
									player.meleeKills = 0;
								} else {
									player.getPA().sendMessage("You need 75 kills to advance. You have " + player.meleeKills);
								}
							} else if (gameObject.getX() == 2592 && gameObject.getY() == 4631) {
								if(player.meleeKills >= 150) {
									player.moveTo(new Position(2600, 4631, 0));
									player.getPA().sendMessage("You advance to the next level.");
									player.meleeKills = 0;
								} else {
									player.getPA().sendMessage("You need 150 kills to advance. You have " + player.meleeKills);
								}
							} else if (gameObject.getX() == 2598 && gameObject.getY() == 4633) {
								if(player.meleeKills >= 400) {
									player.moveTo(new Position(2013, 4504, 0));
									player.getPA().sendMessage("You advance to the next level.");
									player.meleeKills = 0;
								} else {
									player.getPA().sendMessage("You need 400 kills to advance. You have " + player.meleeKills);
								}
							} else if (gameObject.getX() == 2016 && gameObject.getY() == 4503) {
								if(player.meleeKills >= 1000) {
									player.moveTo(new Position(2025, 4504, 0));
									player.getPA().sendMessage("You advance to the next level.");
									player.meleeKills = 0;
								} else {
									player.getPA().sendMessage("You need 1000 kills to advance. You have " + player.meleeKills);
								}
							} else if (gameObject.getX() == 2784 && gameObject.getY() == 3863) {
								if(player.meleeKills >= 25) {
									player.moveTo(new Position(2793, 3864, 0));
									player.getPA().sendMessage("You advance to the next level.");
									player.meleeKills = 0;
								} else {
									player.getPA().sendMessage("You need 25 kills to advance. You have " + player.meleeKills);
								}
							} else if (gameObject.getX() == 2790 && gameObject.getY() == 3865) {
								if(player.meleeKills >= 75) {
									player.moveTo(new Position(2269, 5016, 0));
									player.getPA().sendMessage("You advance to the next level.");
									player.meleeKills = 0;
								} else {
									player.getPA().sendMessage("You need 75 kills to advance. You have " + player.meleeKills);
								}
							} else if (gameObject.getX() == 2272 && gameObject.getY() == 5017) {
								if(player.meleeKills >= 150) {
									player.moveTo(new Position(2281, 5016, 0));
									player.getPA().sendMessage("You advance to the next level.");
									player.meleeKills = 0;
								} else {
									player.getPA().sendMessage("You need 150 kills to advance. You have " + player.meleeKills);
								}
							} else if (gameObject.getX() == 2278 && gameObject.getY() == 5018) {
								if(player.meleeKills >= 400) {
									player.moveTo(new Position(2397, 5016, 0));
									player.getPA().sendMessage("You advance to the next level.");
									player.meleeKills = 0;
								} else {
									player.getPA().sendMessage("You need 400 kills to advance. You have " + player.meleeKills);
								}
							} else if (gameObject.getX() == 2400 && gameObject.getY() == 5017) {
								if(player.meleeKills >= 1000) {
									player.moveTo(new Position(2409, 5016, 0));
									player.getPA().sendMessage("You advance to the next level.");
									player.meleeKills = 0;
								} else {
									player.getPA().sendMessage("You need 1000 kills to advance. You have " + player.meleeKills);
								}
							}
							break;
						case 7836:
						case 7808:
							int amt = player.getInventory().getAmount(6055);
							if (amt > 0) {
								player.getInventory().delete(6055, amt);
								player.getPacketSender().sendMessage("You put the weed in the compost bin.");
								player.getSkillManager().addExperience(Skill.FARMING, 20 * amt);
							} else {
								player.getPacketSender().sendMessage("You do not have any weeds in your inventory.");
							}
							break;
						case 5960: // Levers
						case 5959:
							player.setDirection(Direction.WEST);
							TeleportHandler.teleportPlayer(player, new Position(3090, 3475), TeleportType.LEVER);
							break;
						case 31990:
							if (gameObject.getPosition().getX() == 2272 && gameObject.getPosition().getY() == 4053) {
							player.moveTo(new Position(2272, 4054));
							}
							if (gameObject.getPosition().getX() == 2656 && gameObject.getPosition().getY() == 9877) {
								player.moveTo(new Position(2656, 9878));
								}
							if (gameObject.getPosition().getX() == 2976 && gameObject.getPosition().getY() == 9877) {
								player.moveTo(new Position(2976, 9878));
								}
							break;
						case 5096:
							if (gameObject.getPosition().getX() == 2644 && gameObject.getPosition().getY() == 9593) {
								player.moveTo(new Position(2649, 9591));
							}
							break;

						case 5094:
							if (gameObject.getPosition().getX() == 2648 && gameObject.getPosition().getY() == 9592) {
								player.moveTo(new Position(2643, 9594, 2));
							}
							break;

						case 5098:
							if (gameObject.getPosition().getX() == 2635 && gameObject.getPosition().getY() == 9511) {
								player.moveTo(new Position(2637, 9517));
							}
							break;

						case 5097:
							if (gameObject.getPosition().getX() == 2635 && gameObject.getPosition().getY() == 9514) {
								player.moveTo(new Position(2636, 9510, 2));
							}
							break;
						case 23093:
							if (player.getSkillManager().getCurrentLevel(Skill.AGILITY) < 70) {
								player.getPacketSender().sendMessage(
										"You need an Agility level of at least 70 to go through this portal.");
								return;
							}
							if (!player.getClickDelay().elapsed(2000)) {
								return;
							}
							int plrHeight = player.getPosition().getZ();
							if (plrHeight == 2) {
								player.moveTo(new Position(2914, 5300, 1));
							} else if (plrHeight == 1) {
								int x = gameObject.getPosition().getX();
								int y = gameObject.getPosition().getY();
								if (x == 2914 && y == 5300) {
									player.moveTo(new Position(2912, 5299, 2));
								} else if (x == 2920 && y == 5276) {
									player.moveTo(new Position(2920, 5274, 0));
								}
							} else if (plrHeight == 0) {
								player.moveTo(new Position(2920, 5276, 1));
							}
							player.getClickDelay().reset();
							break;
						case 26439:
							if (player.getSkillManager().getMaxLevel(Skill.CONSTITUTION) <= 700) {
								player.getPacketSender()
										.sendMessage("You need a Constitution level of at least 70 to swim across.");
								return;
							}
							if (!player.getClickDelay().elapsed(1000)) {
								return;
							}
							if (player.isCrossingObstacle()) {
								return;
							}
							final String startMessage = "You jump into the icy cold water..";
							final String endMessage = "You climb out of the water safely.";
							final int jumpGFX = 68;
							final int jumpAnimation = 772;
							player.setSkillAnimation(773);
							player.setCrossingObstacle(true);
							player.getUpdateFlag().flag(Flag.APPEARANCE);
							player.performAnimation(new Animation(3067));
							final boolean goBack2 = player.getPosition().getY() >= 5344;
							player.getPacketSender().sendMessage(startMessage);
							player.moveTo(new Position(2885, !goBack2 ? 5335 : 5342, 2));
							player.setDirection(goBack2 ? Direction.SOUTH : Direction.NORTH);
							player.performGraphic(new Graphic(jumpGFX));
							player.performAnimation(new Animation(jumpAnimation));
							TaskManager.submit(new Task(1, player, false) {
								int ticks = 0;

								@Override
								public void execute() {
									ticks++;
									player.getMovementQueue().walkStep(0, goBack2 ? -1 : 1);
									if (ticks >= 10) {
										stop();
									}
								}

								@Override
								public void stop() {
									player.setSkillAnimation(-1);
									player.setCrossingObstacle(false);
									player.getUpdateFlag().flag(Flag.APPEARANCE);
									player.getPacketSender().sendMessage(endMessage);
									player.moveTo(
											new Position(2885, player.getPosition().getY() < 5340 ? 5333 : 5345, 2));
									setEventRunning(false);
								}
							});
							player.getClickDelay().reset(System.currentTimeMillis() + 9000);
							break;
						case 26384:
							if (player.isCrossingObstacle()) {
								return;
							}
							if (!player.getInventory().contains(2347)) {
								player.getPacketSender()
										.sendMessage("You need to have a hammer to bang on the door with.");
								return;
							}
							player.setCrossingObstacle(true);
							final boolean goBack = player.getPosition().getX() <= 2850;
							player.performAnimation(new Animation(377));
							TaskManager.submit(new Task(2, player, false) {
								@Override
								public void execute() {
									player.moveTo(new Position(goBack ? 2851 : 2850, 5333, 2));
									player.setCrossingObstacle(false);
									stop();
								}
							});
							break;
						case 26303:
							if (!player.getClickDelay().elapsed(1200)) {
								return;
							}
							if (player.getSkillManager().getCurrentLevel(Skill.RANGED) < 70) {
								player.getPacketSender()
										.sendMessage("You need a Ranged level of at least 70 to swing across here.");
							} else if (!player.getInventory().contains(9418)) {
								player.getPacketSender().sendMessage(
										"You need a Mithril grapple to swing across here. Explorer Jack might have one.");
								return;
							} else {
								player.performAnimation(new Animation(789));
								TaskManager.submit(new Task(2, player, false) {
									@Override
									public void execute() {
										player.getPacketSender().sendMessage(
												"You throw your Mithril grapple over the pillar and move across.");
										player.moveTo(new Position(2871,
												player.getPosition().getY() <= 5270 ? 5279 : 5269, 2));
										stop();
									}
								});
								player.getClickDelay().reset();
							}
							break;
						case 4493:
							if (player.getPosition().getX() >= 3432) {
								player.moveTo(new Position(3433, 3538, 1));
							}
							break;
						case 4494:
							player.moveTo(new Position(3438, 3538, 0));
							break;
						case 4495:
							player.moveTo(new Position(3417, 3541, 2));
							break;
						case 4496:
							player.moveTo(new Position(3412, 3541, 1));
							break;
						case 2491:
							player.setDialogueActionId(48);
							DialogueManager.start(player, 87);
							break;
						case 25339:
						case 25340:
							player.moveTo(new Position(1778, 5346, player.getPosition().getZ() == 0 ? 1 : 0));
							break;
						case 10229:
						case 10230:
							boolean up = id == 10229;
							player.performAnimation(new Animation(up ? 828 : 827));
							player.getPacketSender().sendMessage("You climb " + (up ? "up" : "down") + " the ladder..");
							TaskManager.submit(new Task(1, player, false) {
								@Override
								protected void execute() {
									player.moveTo(up ? new Position(1912, 4367) : new Position(2900, 4449));
									stop();
								}
							});
							break;
						case 1568:
							player.moveTo(new Position(3097, 9868));
							break;
						case 5103: // Brimhaven vines
						case 5104:
						case 5105:
						case 5106:
						case 5107:
							if (!player.getClickDelay().elapsed(4000)) {
								return;
							}
							if (player.getSkillManager().getCurrentLevel(Skill.WOODCUTTING) < 30) {
								player.getPacketSender()
										.sendMessage("You need a Woodcutting level of at least 30 to do this.");
								return;
							}
							if (com.ruthlessps.world.content.skill.impl.woodcutting.Hatchet.getHatchet(player) < 0) {
								player.getPacketSender().sendMessage(
										"You do not have a hatchet which you have the required Woodcutting level to use.");
								return;
							}
							final Hatchet axe = Hatchet.forId(
									com.ruthlessps.world.content.skill.impl.woodcutting.Hatchet.getHatchet(player));
							player.performAnimation(new Animation(axe.getAnim()));
							gameObject.setFace(-1);
							TaskManager.submit(new Task(3 + Misc.getRandom(4), player, false) {
								@Override
								protected void execute() {
									if (player.getMovementQueue().isMoving()) {
										stop();
										return;
									}
									int x = 0;
									int y = 0;
									if (player.getPosition().getX() == 2689 && player.getPosition().getY() == 9564) {
										x = 2;
										y = 0;
									} else if (player.getPosition().getX() == 2691
											&& player.getPosition().getY() == 9564) {
										x = -2;
										y = 0;
									} else if (player.getPosition().getX() == 2683
											&& player.getPosition().getY() == 9568) {
										x = 0;
										y = 2;
									} else if (player.getPosition().getX() == 2683
											&& player.getPosition().getY() == 9570) {
										x = 0;
										y = -2;
									} else if (player.getPosition().getX() == 2674
											&& player.getPosition().getY() == 9479) {
										x = 2;
										y = 0;
									} else if (player.getPosition().getX() == 2676
											&& player.getPosition().getY() == 9479) {
										x = -2;
										y = 0;
									} else if (player.getPosition().getX() == 2693
											&& player.getPosition().getY() == 9482) {
										x = 2;
										y = 0;
									} else if (player.getPosition().getX() == 2672
											&& player.getPosition().getY() == 9499) {
										x = 2;
										y = 0;
									} else if (player.getPosition().getX() == 2674
											&& player.getPosition().getY() == 9499) {
										x = -2;
										y = 0;
									}
									CustomObjects.objectRespawnTask(player,
											new GameObject(-1, gameObject.getPosition().copy()), gameObject, 10);
									player.getPacketSender().sendMessage("You chop down the vines..");
									player.getSkillManager().addExperience(Skill.WOODCUTTING, 45);
									player.performAnimation(new Animation(65535));
									player.getMovementQueue().walkStep(x, y);
									stop();
								}
							});
							player.getClickDelay().reset();
							break;
						case 29942:
							if (player.getSkillManager().getCurrentLevel(Skill.SUMMONING) == player.getSkillManager()
									.getMaxLevel(Skill.SUMMONING)) {
								player.getPacketSender()
										.sendMessage("You do not need to recharge your Summoning points right now.");
								return;
							}
							player.performGraphic(new Graphic(1517));
							player.getSkillManager().setCurrentLevel(Skill.SUMMONING,
									player.getSkillManager().getMaxLevel(Skill.SUMMONING), true);
							player.getPacketSender().sendString(18045,
									" " + player.getSkillManager().getCurrentLevel(Skill.SUMMONING) + "/"
											+ player.getSkillManager().getMaxLevel(Skill.SUMMONING));
							player.getPacketSender().sendMessage("You recharge your Summoning points.");
							break;
						case 884:
							player.setDialogueActionId(41);
							DialogueManager.start(player, 75);
							break;
						case 12201:
							player.setDialogueActionId(130);
							DialogueManager.start(player, 130);
							break;
						case 11793:
							player.setDialogueActionId(131);
							DialogueManager.start(player, 131);
							break;
						case 9294:
							if (player.getSkillManager().getCurrentLevel(Skill.AGILITY) < 80) {
								player.getPacketSender()
										.sendMessage("You need an Agility level of at least 80 to use this shortcut.");
								return;
							}
							player.performAnimation(new Animation(769));
							TaskManager.submit(new Task(1, player, false) {
								@Override
								protected void execute() {
									player.moveTo(
											new Position(player.getPosition().getX() >= 2880 ? 2878 : 2880, 9813));
									stop();
								}
							});
							break;
						case 9293:
							boolean back = player.getPosition().getX() > 2888;
							player.moveTo(back ? new Position(2886, 9799) : new Position(2891, 9799));
							break;
						case 2320:
							back = player.getPosition().getY() == 9969 || player.getPosition().getY() == 9970;
							player.moveTo(back ? new Position(3120, 9963) : new Position(3120, 9969));
							break;
						case 29745:
							player.moveTo(new Position(2636, 4917, 2));
							break;
						case 32015:
							player.performAnimation(new Animation(828));
							player.getPacketSender().sendMessage("You climb the ladder..");
							TaskManager.submit(new Task(1, player, false) {
								@Override
								protected void execute() {
									if (gameObject.getPosition().getX() == 3005
											&& gameObject.getPosition().getY() == 10363) {
										player.moveTo(new Position(3005, 3964));
									}
									stop();
								}
							});
							break;
						case 1755:
							player.performAnimation(new Animation(828));
							player.getPacketSender().sendMessage("You climb the stairs..");
							TaskManager.submit(new Task(1, player, false) {
								@Override
								protected void execute() {
									if (gameObject.getPosition().getX() == 2547
											&& gameObject.getPosition().getY() == 9951) {
										player.moveTo(new Position(2548, 3551));
									} else if (gameObject.getPosition().getX() == 3005
											&& gameObject.getPosition().getY() == 10363) {
										player.moveTo(new Position(3005, 3962));
									} else if (gameObject.getPosition().getX() == 3084
											&& gameObject.getPosition().getY() == 9672) {
										player.moveTo(new Position(3117, 3244));
									} else if (gameObject.getPosition().getX() == 3097
											&& gameObject.getPosition().getY() == 9867) {
										player.moveTo(new Position(3096, 3468));
									}
									stop();
								}
							});
							break;
						case 5110:
							player.moveTo(new Position(2647, 9557));
							player.getPacketSender().sendMessage("You pass the stones..");
							break;
						case 5111:
							player.moveTo(new Position(2649, 9562));
							player.getPacketSender().sendMessage("You pass the stones..");
							break;
						case 6434:
							player.performAnimation(new Animation(827));
							player.getPacketSender().sendMessage("You enter the trapdoor..");
							TaskManager.submit(new Task(1, player, false) {
								@Override
								protected void execute() {
									player.moveTo(new Position(3085, 9672));
									stop();
								}
							});
							break;
						case 19187:
						case 19175:
							Hunter.dismantle(player, gameObject);
							break;
						case 25029:
							PuroPuro.goThroughWheat(player, gameObject);
							break;
						case 47976:
							Nomad.endFight(player, false);
							break;
						case 2182:
							if (!player.getMinigameAttributes().getRecipeForDisasterAttributes().hasFinishedPart(0)) {
								player.getPacketSender()
										.sendMessage("You have no business with this chest. Talk to the Gypsy first!");
								return;
							}
							RecipeForDisaster.openRFDShop(player);
							break;
						case 12356:
							if (!player.getMinigameAttributes().getRecipeForDisasterAttributes().hasFinishedPart(0)) {
								player.getPacketSender()
										.sendMessage("You have no business with this portal. Talk to the Gypsy first!");
								return;
							}
							if (player.getPosition().getZ() > 0) {
								RecipeForDisaster.leave(player);
							} else {
								player.getMinigameAttributes().getRecipeForDisasterAttributes().setPartFinished(1,
										true);
								RecipeForDisaster.enter(player);
							}
							break;
						case 6704:
							player.moveTo(new Position(3577, 3282, 0));
							break;
						case 6706:
							player.moveTo(new Position(3554, 3283, 0));
							break;
						case 6705:
							player.moveTo(new Position(3566, 3275, 0));
							break;
						case 6702:
							player.moveTo(new Position(3564, 3289, 0));
							break;
						case 6703:
							player.moveTo(new Position(3574, 3298, 0));
							break;
						case 6707:
							player.moveTo(new Position(3556, 3298, 0));
							break;
						case 3203:
							if (player.getLocation() == Location.DUEL_ARENA && player.getDueling().duelingStatus == 5) {
								if (Dueling.checkRule(player, DuelRule.NO_FORFEIT)) {
									player.getPacketSender().sendMessage("Forfeiting has been disabled in this duel.");
									return;
								}
								player.getCombatBuilder().reset(true);
								if (player.getDueling().duelingWith > -1) {
									Player duelEnemy = World.getPlayers().get(player.getDueling().duelingWith);
									if (duelEnemy == null) {
										return;
									}
									duelEnemy.getCombatBuilder().reset(true);
									duelEnemy.getMovementQueue().reset();
									duelEnemy.getDueling().duelVictory();
								}
								player.moveTo(new Position(3368 + Misc.getRandom(5), 3267 + Misc.getRandom(3), 0));
								player.getDueling().reset();
								player.getCombatBuilder().reset(true);
								player.restart();
							}
							break;
						case 1738:
							if (player.getLocation() == Location.LUMBRIDGE && player.getPosition().getZ() == 0) {
								player.moveTo(
										new Position(player.getPosition().getX(), player.getPosition().getY(), 1));
							} else {
								player.moveTo(new Position(2840, 3539, 2));
							}
							break;
						case 15638:
							player.moveTo(new Position(2840, 3539, 0));
							break;
						case 15644:
						case 15641:
							switch (player.getPosition().getZ()) {
							case 0:
								player.moveTo(new Position(2855, player.getPosition().getY() >= 3546 ? 3545 : 3546));
								break;
							}
							break;
						case 28714:
							player.performAnimation(new Animation(828));
							player.delayedMoveTo(new Position(3089, 3492), 2);
							break;
						case 1746:
							player.performAnimation(new Animation(827));
							player.delayedMoveTo(new Position(2209, 5348), 2);
							break;
						case 19191:
						case 19189:
						case 19180:
						case 19184:
						case 19182:
						case 19178:
							Hunter.lootTrap(player, gameObject);
							break;
						case 817:
							if (player.getDonor() != DonorRights.DONOR && player.getDonor() != DonorRights.DELUXE_DONOR
									&& player.getDonor() != DonorRights.SPONSOR && player.getDonor() != DonorRights.SUPER_SPONSOR) {
								player.getPacketSender()
										.sendMessage("You must be at least a deluxe donor to use this.");
								return;
							}
							int loot = 1127;
							Stalls.stealFromStall(player, 1, 15000, loot, "You stole some morune equipment.", 1);
							break;
						case 7100:
							if (player.getDonor().ordinal() < DonorRights.DELUXE_DONOR.ordinal()) {
								player.getPacketSender()
										.sendMessage("You must be at least a deluxe donor to use this.");
							}
							if(player.getDonor() == DonorRights.DELUXE_DONOR) {
								Stalls.stealFromStall(player, 1, 20000, 1187, "You stole some blue dragon equipment.", 1);
							} else if (player.getDonor() == DonorRights.SPONSOR) {
								Stalls.stealFromStall(player, 1, 20000, 2, "You stole a cannonball.", 1);
							} else if (player.getDonor().ordinal() >= DonorRights.SUPER_SPONSOR.ordinal()) {
								Stalls.stealFromStall(player, 1, 25000, 2, "You stole a cannonball", 1);
							}
							break;
						case 30205:
							player.setDialogueActionId(11);
							DialogueManager.start(player, 20);
							break;
						case 28716:
							if (!player.busy()) {
								player.getSkillManager().updateSkill(Skill.SUMMONING);
								player.getPacketSender().sendInterface(63471);
							} else {
								player.getPacketSender()
										.sendMessage("Please finish what you're doing before opening this.");
							}
							break;
						case 6:
							DwarfCannon cannon = player.getCannon();
							if (cannon == null || cannon.getOwnerIndex() != player.getIndex()) {
								player.getPacketSender().sendMessage("This is not your cannon!");
							} else {
								DwarfMultiCannon.startFiringCannon(player, cannon);
							}
							break;
						case 2:
							player.moveTo(new Position(player.getPosition().getX() > 2690 ? 2687 : 2694, 3714));
							player.getPacketSender().sendMessage("You walk through the entrance..");
							break;
						case 2028:
						case 2029:
						case 2030:
						case 2031:
							player.setEntityInteraction(gameObject);
							Fishing.setupFishing(player, Fishing.forSpot(gameObject.getId(), false));
							return;
						case 12692:
						case 2783:
						case 4306:
							player.setInteractingObject(gameObject);
							EquipmentMaking.handleAnvil(player);
							break;
						case 2732:
							EnterAmountOfLogsToAdd.openInterface(player);
							break;
						case 409:
						case 27661:
						case 2640:
						case 36972:
							if (player.getSkillManager().getCurrentLevel(Skill.PRAYER) >= player.getSkillManager()
									.getMaxLevel(Skill.PRAYER)) {
								player.sendMessage("You already have full prayer points!");
								return;
							}
							player.performAnimation(new Animation(645));
							if (player.getSkillManager().getCurrentLevel(Skill.PRAYER) < player.getSkillManager()
									.getMaxLevel(Skill.PRAYER)) {
								player.getSkillManager().setCurrentLevel(Skill.PRAYER,
										player.getSkillManager().getMaxLevel(Skill.PRAYER), true);
								player.getPacketSender().sendMessage("You recharge your Prayer points.");
							}
							break;
						case 8749:
							boolean restore = player.getSpecialPercentage() < 100;
							if (restore) {
								player.setSpecialPercentage(100);
								CombatSpecial.updateBar(player);
								player.getPacketSender().sendMessage("Your special attack energy has been restored.");
							}
							for (Skill skill : Skill.values()) {
								int increase = skill != Skill.PRAYER && skill != Skill.CONSTITUTION
										&& skill != Skill.SUMMONING ? 19 : 0;
								if (player.getSkillManager().getCurrentLevel(
										skill) < player.getSkillManager().getMaxLevel(skill) + increase) {
									player.getSkillManager().setCurrentLevel(skill,
											player.getSkillManager().getMaxLevel(skill) + increase);
								}
							}
							player.performGraphic(new Graphic(1302));
							player.getPacketSender().sendMessage("Your stats have received a major buff.");
							break;
						case 4859:
							player.performAnimation(new Animation(645));
							if (player.getSkillManager().getCurrentLevel(Skill.PRAYER) < player.getSkillManager()
									.getMaxLevel(Skill.PRAYER)) {
								player.getSkillManager().setCurrentLevel(Skill.PRAYER,
										player.getSkillManager().getMaxLevel(Skill.PRAYER), true);
								player.getPacketSender().sendMessage("You recharge your Prayer points.");
							}
							break;
						case 411:
							if (player.getSkillManager().getMaxLevel(Skill.DEFENCE) < 30) {
								player.getPacketSender()
										.sendMessage("You need a Defence level of at least 30 to use this altar.");
								return;
							}
							player.performAnimation(new Animation(645));
							if (player.getPrayerbook() == Prayerbook.NORMAL) {
								player.getPacketSender()
										.sendMessage("You sense a surge of power flow through your body!");
								player.setPrayerbook(Prayerbook.CURSES);
							} else {
								player.getPacketSender()
										.sendMessage("You sense a surge of purity flow through your body!");
								player.setPrayerbook(Prayerbook.NORMAL);
							}
							player.getPacketSender().sendTabInterface(GameSettings.PRAYER_TAB,
									player.getPrayerbook().getInterfaceId());
							PrayerHandler.deactivateAll(player);
							CurseHandler.deactivateAll(player);
							break;
						case 6552:
							player.performAnimation(new Animation(645));
							player.setSpellbook(player.getSpellbook() == MagicSpellbook.ANCIENT ? MagicSpellbook.NORMAL
									: MagicSpellbook.ANCIENT);
							player.getPacketSender()
									.sendTabInterface(GameSettings.MAGIC_TAB, player.getSpellbook().getInterfaceId())
									.sendMessage("Your magic spellbook is changed..");
							Autocasting.resetAutocast(player, true);
							break;
						case 410:
							if (player.getSkillManager().getMaxLevel(Skill.DEFENCE) < 40) {
								player.getPacketSender()
										.sendMessage("You need a Defence level of at least 40 to use this altar.");
								return;
							}
							player.performAnimation(new Animation(645));
							player.setSpellbook(player.getSpellbook() == MagicSpellbook.LUNAR ? MagicSpellbook.NORMAL
									: MagicSpellbook.LUNAR);
							player.getPacketSender()
									.sendTabInterface(GameSettings.MAGIC_TAB, player.getSpellbook().getInterfaceId())
									.sendMessage("Your magic spellbook is changed..");
							;
							Autocasting.resetAutocast(player, true);
							break;
						case 2191:
							openMbox(player);
							break;
						case 172:
							if (player.getInventory().getFreeSlots() <= 0) {
								player.sendMessage("You need 1 open space to receive your reward.");
								return;
							}
							MysteryChest.handleChest(player);
							break;
						case 6910:
						case 4483:
						case 3193:
						case 2213:
						case 11758:
						case 5276:
						case 14367:
						case 27663:
						case 42192:
						case 75:
						case 26969:
						case 26972:
						case 26193:
						case 25808:
							player.getBank(player.getCurrentBankTab()).open();
							break;
						}
					}
				}));
	}

	@SuppressWarnings("unused")
	private static void fourthClick(Player player, Packet packet) {
	}
	
	public static void openMbox(Player player) {
		if(player.getInventory().contains(991)) {
			int[] torvaIds =  {20250, 20251, 20252, 20253, 14556, 14557};
			openThis(player, 991, torvaIds);
		} else if (player.getInventory().contains(993)) {
			int[] torvaIds =  {14559, 14560, 14561, 14562, 14563, 14564};
			openThis(player, 993, torvaIds);
		} else if (player.getInventory().contains(1507)) {
			int[] torvaIds =  {14572, 14567, 14568, 14569, 14570, 14571};
			openThis(player, 1507, torvaIds);
		} else if (player.getInventory().contains(1542)) {
			int[] torvaIds =  {14586, 14581, 14582, 14583, 14584, 14585};
			openThis(player, 1542, torvaIds);
		} else if (player.getInventory().contains(1545)) {
			int[] torvaIds =  {14588, 3314, 14590, 14591, 79, 80};
			openThis(player, 1545, torvaIds);
		} else if (player.getInventory().contains(1546)) {
			int[] torvaIds =  {14619, 14596, 14597, 14598, 14599, 18742};
			openThis(player, 1546, torvaIds);
		} else if (player.getInventory().contains(1547)) {
			int[] torvaIds =  {4450, 4451, 4452, 4453, 4454, 4453};
			openThis(player, 1547, torvaIds);
		} else if (player.getInventory().contains(1548)) {
			int[] torvaIds =  {4553, 4554, 4555, 4556, 4557, 4558};
			openThis(player, 1548, torvaIds);
		} else if (player.getInventory().contains(1586)) {
			int[] torvaIds =  {3999, 4000, 4001, 4002, 4003, 3999};
			openThis(player, 1586, torvaIds);
		} else if (player.getInventory().contains(1588)) {
			int[] torvaIds =  {3620, 3621, 3622, 3623, 3624, 3625};
			openThis(player, 1588, torvaIds);
		} else if (player.getInventory().contains(1590)) {
			int[] torvaIds =  {624, 623, 621, 620, 619, 618};
			openThis(player, 1590, torvaIds);
		} else if (player.getInventory().contains(1591)) {
			int[] torvaIds =  {666, 669, 670, 671, 672, 673};
			openThis(player, 1591, torvaIds);
		} else if (player.getInventory().contains(2400)) {
			int[] torvaIds =  {19658, 19659, 19660, 19661, 19662, 19663};
			openThis(player, 2400, torvaIds);
		} else if (player.getInventory().contains(1547)) {
			int[] torvaIds =  {4450, 4451, 4452, 4453, 4454};
			openThis(player, 1547, torvaIds);
		} else if (player.getInventory().contains(1548)) {
			int[] torvaIds =  {4553, 4554, 4555, 4556, 4557};
			openThis(player, 1548, torvaIds);
		} else if (player.getInventory().contains(1586)) {
			int[] torvaIds =  {3999, 4000, 4001, 4002, 4003};
			openThis(player, 1586, torvaIds);
		} else if (player.getInventory().contains(2401)) {
			int[] torvaIds =  {935, 937, 940, 934, 2884};
			openThis(player, 2401, torvaIds);
		} else if (player.getInventory().contains(2404)) {
			int[] torvaIds =  {933, 938, 979, 3246, 14453};
			openThis(player, 2404, torvaIds);
		} else if (player.getInventory().contains(2409)) {
			int[] torvaIds =  {932, 936, 939, 996, 3248};
			openThis(player, 2409, torvaIds);
		} else if (player.getInventory().contains(2411)) {
			int[] torvaIds =  {20732, 20733, 20786, 7043, 7048, 7155, 7670};
			openThis(player, 2411, torvaIds);
		} else if (player.getInventory().contains(3813)) {
			int[] torvaIds =  {20526, 20528, 20300, 7041, 7047, 7153, 7621};
			openThis(player, 3813, torvaIds);
		} else if (player.getInventory().contains(3814)) {
			int[] torvaIds =  {20834, 20840, 20838, 20832, 20836, 19730, 7042};
			openThis(player, 3814, torvaIds);
		} else if (player.getInventory().contains(2420)) {
			int[] torvaIds =  {20301, 20302, 20310, 7044, 7049, 7154, 7672};
			openThis(player, 2420, torvaIds);
		} else if (player.getInventory().contains(2421)) {
			int[] torvaIds =  {7045, 7151, 20924, 20934, 20932, 20922, 20926};
			openThis(player, 2421, torvaIds);
		} else if (player.getInventory().contains(927)) {
			int[] torvaIds =  {20458, 20460, 20462, 7040, 7046, 7152, 7619};
			openThis(player, 927, torvaIds);
		} else if (player.getInventory().contains(3320)) {
			int[] torvaIds =  {2603, 3627, 3628, 3629, 3630, 3631, 3632};
			openThis(player, 3320, torvaIds);
		} else if (player.getInventory().contains(2421)) {
			int[] torvaIds =  {7045, 7151, 20924, 20934, 20932, 20922, 20926};
			openThis(player, 2421, torvaIds);
		} else if (player.getInventory().contains(3321)) {
			int[] torvaIds =  {3271, 3272, 3273, 3274, 3275, 3276};
			openThis(player, 3321, torvaIds);
		} else if (player.getInventory().contains(3322)) {
			int[] torvaIds =  {17293, 3309, 3307, 3310, 3311, 3312, 3313};
			openThis(player, 3322, torvaIds);
		} else if (player.getInventory().contains(3323)) {
			int[] torvaIds =  {3948, 12521, 18457, 18458, 17892, 3915, 18487, 20027, 18491, 9281, 16624, 16625,17846,3972,16626,4766,18376,4764,13256,13257,13254,19941,19714,4766,5249,16618,16619,16620,4741,15659,15658,13253};
			openThis(player, 3323, torvaIds);
		} else if (player.getInventory().contains(3324)) {
			int[] torvaIds =  {1840,15,7671,1841,1842,7673};
			openThis(player, 3324, torvaIds);
		} else if (player.getInventory().contains(3687)) {
			int[] torvaIds =  {1843,1845,1846,1850,20652,1848};
			openThis(player, 3687, torvaIds);
		} else if (player.getInventory().contains(3717)) {
			int[] torvaIds =  {14094,14095,14096,14097,14118,14119,14120,14319,14331,14341};
			openThis(player, 3717, torvaIds);
		} else if (player.getInventory().contains(3810)) {
			int[] torvaIds =  {3647,3648,3649,3650,3651,3652,3653};
			openThis(player, 3810, torvaIds);
		}
		
	}
	
	public static void openThis(Player player, int itemId, int[] torvaIds) {
		if (player.getInventory().getFreeSlots() <= 1) {
			player.sendMessage("You need more free inventory slots");
			return;
		}
		if(player.getInventory().getAmount(itemId) >= 1) {
			player.getInventory().delete(itemId, 1);
			int rng = Misc.random(100);
			if(rng == 100) {
					player.getInventory().add(2800, 1);
					World.sendMessage("<img=10><col=FF7400><shad=0>"+player.getUsername()+" got a Pet Mystery Box from the chest!");
			} else if (rng >= 95) {
				player.getInventory().add(2801, 1);
				World.sendMessage("<img=10><col=FF7400><shad=0>"+player.getUsername()+" got a Torva Mystery Box from the chest!");
			} else if (rng >= 94) {
				player.getInventory().add(15501, 1);
				World.sendMessage("<img=10><col=FF7400><shad=0>"+player.getUsername()+" got a Mega Mystery Box from the chest!");
			} else if(rng >= 89) {
				player.getInventory().add(torvaIds[Misc.random(torvaIds.length-1)], 1);
				player.sendMessage("You got an armor piece!");
			} else if (rng >= 79) {
				player.getInventory().add(6199, 1);
				player.sendMessage("You got a mystery box!");
			} else if (rng >= 69) {
				player.getInventory().add(13664, 30);
				player.sendMessage("You got 30 " + GameSettings.SERVER_NAME + " bucks!");
			} else if (rng >= 54) {
				player.getInventory().add(13664, 15);
				player.sendMessage("You got 15 " + GameSettings.SERVER_NAME + " bucks!");
			} else if (rng >= 44) {
				player.getInventory().add(2435, 20);
				player.sendMessage("You got 20 Prayer potions!");
			} else if (rng >= 34) {
				player.getInventory().add(2437, 20);
				player.sendMessage("You got 20 Super Attack potions!");
			} else if (rng >= 24) {
				player.getInventory().add(2441, 20);
				player.sendMessage("You got 20 Super Strenght potions!");
			} else if (rng >= 14) {
				player.getInventory().add(2443, 20);
				player.sendMessage("You got 20 Super Defence potions!");
			} else if (rng >= 9) {
				player.getInventory().add(931, 20);
				player.sendMessage("You got 20 Boss Bones!");
			} else {
				player.getInventory().add(15273, 200);
				player.sendMessage("You got 200 Rocktails!");
			}
		} else {
			player.sendMessage("You need a key to open this chest!");
		}
	}

	private static void secondClick(final Player player, Packet packet) {
		final int id = packet.readLEShortA();
		final int y = packet.readLEShort();
		final int x = packet.readUnsignedShortA();
		final Position position = new Position(x, y, player.getPosition().getZ());
		final GameObject gameObject = new GameObject(id, position);
		if (id > 0 && id != 6 && !RegionClipping.objectExists(gameObject)) {
			// player.getPacketSender().sendMessage("An error occured. Error
			// code: "+id).sendMessage("Please report the error to a staff
			// member.");
			return;
		}
		player.setPositionToFace(gameObject.getPosition());
		int distanceX = player.getPosition().getX() - position.getX();
		int distanceY = player.getPosition().getY() - position.getY();
		if (distanceX < 0) {
			distanceX = -distanceX;
		}
		if (distanceY < 0) {
			distanceY = -distanceY;
		}
		int size = distanceX > distanceY ? distanceX : distanceY;
		gameObject.setSize(size);
		player.setInteractingObject(gameObject)
				.setWalkToTask(new WalkToTask(player, position, gameObject.getSize(), new FinalizedMovementTask() {
					@Override
					public void execute() {
						if (MiningData.forRock(gameObject.getId()) != null) {
							Prospecting.prospectOre(player, id);
							return;
						}
						if (player.getFarming().click(player, x, y, 1)) {
							return;
						}
						switch (gameObject.getId()) {
						case 28716:
							if (!player.busy()) {
								if (player.getSkillManager().getCurrentLevel(Skill.SUMMONING) == player
										.getSkillManager().getMaxLevel(Skill.SUMMONING)) {
									player.getPacketSender().sendMessage("You do not need to recharge right now.");
									return;
								}
								player.performGraphic(new Graphic(1517));
								player.performAnimation(new Animation(8502));
								player.getSkillManager().setCurrentLevel(Skill.SUMMONING,
										player.getSkillManager().getMaxLevel(Skill.SUMMONING), true);
								player.getPacketSender().sendString(18045,
										" " + player.getSkillManager().getCurrentLevel(Skill.SUMMONING) + "/"
												+ player.getSkillManager().getMaxLevel(Skill.SUMMONING));
								player.getPacketSender().sendMessage("You recharge your Summoning points.");
							} else {
								player.getPacketSender()
										.sendMessage("Please finish what you're doing before doing this.");
							}
							break;
						case 884:
							player.setDialogueActionId(41);
							player.setInputHandling(new DonateToWell());
							player.getPacketSender().sendInterfaceRemoval()
									.sendEnterAmountPrompt("How much money would you like to contribute with?");
							break;
						case 12201:
							player.setDialogueActionId(130);
							player.setInputHandling(new DonateToDropWell());
							player.getPacketSender().sendInterfaceRemoval()
									.sendEnterAmountPrompt("How much money would you like to contribute with?");
							break;
						case 11793:
							player.setDialogueActionId(131);
							player.setInputHandling(new DonateToPointsWell());
							player.getPacketSender().sendInterfaceRemoval()
									.sendEnterAmountPrompt("How much money would you like to contribute with?");
							break;
						case 2646:
						case 312:
							if (!player.getClickDelay().elapsed(1200)) {
								return;
							}
							if (player.getInventory().isFull()) {
								player.getPacketSender().sendMessage("You don't have enough free inventory space.");
								return;
							}
							String type = gameObject.getId() == 312 ? "Potato" : "Flax";
							player.performAnimation(new Animation(827));
							player.getInventory().add(gameObject.getId() == 312 ? 1942 : 1779, 1);
							player.getPacketSender().sendMessage("You pick some " + type + "..");
							QuestAssigner.handleQuest(player, 90);
							gameObject.setPickAmount(gameObject.getPickAmount() + 1);
							if (Misc.getRandom(3) == 1 && gameObject.getPickAmount() >= 1
									|| gameObject.getPickAmount() >= 6) {
								player.getPacketSender().sendClientRightClickRemoval();
								gameObject.setPickAmount(0);
								CustomObjects.globalObjectRespawnTask(new GameObject(-1, gameObject.getPosition()),
										gameObject, 10);
							}
							player.getClickDelay().reset();
							break;
						case 2644:
							Flax.showSpinInterface(player);
							break;
						case 6:
							DwarfCannon cannon = player.getCannon();
							if (cannon == null || cannon.getOwnerIndex() != player.getIndex()) {
								player.getPacketSender().sendMessage("This is not your cannon!");
							} else {
								DwarfMultiCannon.pickupCannon(player, cannon, false);
							}
							break;
							case 13389:
								MysteryChest.showInterface(player, gameObject.getId(), gameObject.getPosition());
								break;
						case 10679:
							Stalls.stealFromStall(player, 1, 1500, 18199, "You steal a banana.", 1);
							break;
						case 10680:
							Stalls.stealFromStall(player, 20, 4330, 15009, "You steal a golden ring.", 1);
							break;
						case 10681:
							Stalls.stealFromStall(player, 50, 5370, 17401, "You steal a damaged hammer.", 1);
							break;
						case 10682:
							Stalls.stealFromStall(player, 75, 6590, 1389, "You steal a staff.", 1);
							break;
						case 10683:
							Stalls.stealFromStall(player, 90, 7930, 11998, "You steal a scimitar.", 1);
							break;
						case 10685:
						case 19098:
							Stalls.stealFromStall(player, 95, 8530, 11998, "You steal a scimitar.", 1);
							break;
						case 6189:
						case 26814:
						case 11666:
							Smelting.openInterface(player);
							break;
						case 2152:
							player.performAnimation(new Animation(8502));
							player.performGraphic(new Graphic(1308));
							player.getSkillManager().setCurrentLevel(Skill.SUMMONING,
									player.getSkillManager().getMaxLevel(Skill.SUMMONING));
							player.getPacketSender().sendMessage("You renew your Summoning points.");
							break;
						}
					}
				}));
	}

	@SuppressWarnings("unused")
	private static void thirdClick(Player player, Packet packet) {
	}

	@Override
	public void handleMessage(Player player, Packet packet) {
		if (player.isTeleporting() || player.isPlayerLocked() || player.getMovementQueue().isLockMovement()) {
			return;
		}
		switch (packet.getOpcode()) {
			case FIRST_CLICK:
				firstClick(player, packet);
				break;
			case SECOND_CLICK:
				secondClick(player, packet);
				break;
			case THIRD_CLICK:
				// thirdClick(player, packet);
				break;
			case FOURTH_CLICK:
				// fourthClick(player, packet);
				break;
			case FIFTH_CLICK:
				fifthClick(player, packet);
				break;
		}
	}
}
