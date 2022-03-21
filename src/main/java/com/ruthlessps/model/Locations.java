package com.ruthlessps.model;

import com.ruthlessps.model.PlayerRanks.PlayerRights;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.World;
import com.ruthlessps.world.content.clan.ClanChatManager;
import com.ruthlessps.world.content.combat.CombatFactory;
import com.ruthlessps.world.content.combat.pvp.BountyHunter;
import com.ruthlessps.world.content.gambling.GamblingManager;
import com.ruthlessps.world.content.gambling.GamblingManager.GambleStage;
import com.ruthlessps.world.content.minigames.impl.Graveyard;
import com.ruthlessps.world.content.minigames.impl.KillGame;
import com.ruthlessps.world.content.minigames.impl.Nomad;
import com.ruthlessps.world.content.minigames.impl.RecipeForDisaster;
import com.ruthlessps.world.content.skill.impl.dungeoneering.Dungeoneering;
import com.ruthlessps.world.entity.Entity;
import com.ruthlessps.world.entity.impl.Character;
import com.ruthlessps.world.entity.impl.npc.NPC;
import com.ruthlessps.world.entity.impl.player.Player;

public class Locations {

	public enum Location {

		HUNGER_GAMES_LOBBY(new int[] { 2450, 2483, 2608, 2877, }, new int[] { 4759, 4792, 3222, 3318 }, false, false,
				false, false, false, false) {
			@Override
			public void process(Player player) {
				if (player.getWalkableInterfaceId() != 48300)
					player.getPacketSender().sendWalkableInterface(48300);
			}
		},

		GAMBLING_ZONE(new int[] { 3207, 3222 }, new int[] { 9495, 9509 }, false, false, false, false, false, false) {
			@Override
			public void enter(Player player) {
				if (player.getPlayerInteractingOption() != PlayerInteractingOption.GAMBLE)
					player.getPacketSender().sendInteractionOption("Gamble with", 2, false);
			}
			
			@Override
			public void leave(Player player) {
				player.getPacketSender().sendInteractionOption("null", 2, false);
			}
		},
		DUNGEONEERING(new int[] { 3433, 3459, 2421, 2499 }, new int[] { 3694, 3729, 4915, 4990 }, true, false, true,
				false, true, false) {

			@Override
			public boolean handleKilledNPC(Player killer, NPC npc) {
				if (Dungeoneering.doingDungeoneering(killer)) {
					Dungeoneering.handleNpcDeath(killer, npc);
					return true;
				}
				return false;
			}

			@Override
			public void leave(Player player) {
				Dungeoneering.leave(player, true, true);
			}

			@Override
			public void onDeath(Player player) {
				if (Dungeoneering.doingDungeoneering(player)) {
					Dungeoneering.handlePlayerDeath(player);
				}
			}

			@Override
			public void process(Player player) {
				if (Dungeoneering.doingDungeoneering(player)) {
					if (player.getWalkableInterfaceId() != 37500) {
						player.getPacketSender().sendWalkableInterface(37500);
					}
				} else if (player.getWalkableInterfaceId() == 37500) {
					player.getPacketSender().sendWalkableInterface(-1);
				}
			}
		},
		MEMBER_ZONE(new int[] { 3415, 3435 }, new int[] { 2900, 2926 }, false, true, true, false, false, true) {
		},
		FUN_PK(new int[] { 1811, 1835 }, new int[] { 5076, 5099 }, true, true, true, false, false, true) {
			@Override
			public boolean canAttack(Player player, Player target) {
				int combatDifference = CombatFactory.combatLevelDifference(player.getSkillManager().getCombatLevel(),
						target.getSkillManager().getCombatLevel());
				if (combatDifference > 500) {
					player.getPacketSender()
							.sendMessage("Your combat level difference is too great to attack that player here.");
					player.getMovementQueue().reset();
					return false;
				}
				if (target.getLocation() != Location.FUN_PK) {
					player.getPacketSender()
							.sendMessage("That player cannot be attacked, because they are not in the Wilderness.");
					player.getMovementQueue().reset();
					return false;
				}

				if (Misc.getMinutesPlayed(player) < 20) {
					player.getPacketSender()
							.sendMessage("You must have played for at least 20 minutes in order to attack someone.");
					return false;
				}
				if (Misc.getMinutesPlayed(target) < 20) {
					player.getPacketSender()
							.sendMessage("This player is a new player and can therefore not be attacked yet.");
					return false;
				}

				return true;
			}

			@Override
			public boolean canTeleport(Player player) {
				return true;
			}

			@Override
			public void enter(Player player) {
				player.getPacketSender().sendInteractionOption("Attack", 2, true);
				player.getPacketSender().sendWalkableInterface(25347);
				player.getPacketSender().sendString(19000,
						"Combat level: " + player.getSkillManager().getCombatLevel());
				player.getUpdateFlag().flag(Flag.APPEARANCE);
			}

			@Override
			public void leave(Player player) {
				if (player.getLocation() != this) {
					player.getPacketSender().sendString(19000,
							"Combat level: " + player.getSkillManager().getCombatLevel());
					player.getUpdateFlag().flag(Flag.APPEARANCE);
				}
			}

			@Override
			public void login(Player player) {
				player.performGraphic(new Graphic(2000, 8));
			}

			@Override
			public void process(Player player) {
				int x = player.getPosition().getX();
				int y = player.getPosition().getY();
				boolean ghostTown = x >= 3650 && y <= 3538;
				if (ghostTown) {
					player.setWildernessLevel(60);
				} else {
					player.setWildernessLevel(100);
				}
				player.getPacketSender().sendString(25352, "" + player.getWildernessLevel());
			}
		},
		VARROCK(new int[] { 3167, 3272 }, new int[] { 3263, 3504 }, false, true, true, true, true, true) {
		},
		LEGIO(new int[] { 2387, 2398 }, new int[] { 4696, 4716 }, false, true, true, true, true, true) {
		},
		BUNNYHOP(new int[] { 2508, 2538 }, new int[] { 4756, 4761 }, false, true, true, true, true, true) {
		},
		BANK(new int[] { 3090, 3099, 3089, 3090, 3248, 3258, 3179, 3191, 2944, 2948, 2942, 2948, 2944, 2950, 3008, 3019,
				3017, 3022, 3203, 3213, 3212, 3215, 3215, 3220, 3220, 3227, 3227, 3230, 3226, 3228, 3227, 3229 },
				new int[] { 3487, 3500, 3492, 3498, 3413, 3428, 3432, 3448, 3365, 3374, 3367, 3374, 3365, 3370, 3352,
						3359, 3352, 3357, 3200, 3237, 3200, 3235, 3202, 3235, 3202, 3229, 3208, 3226, 3230, 3211, 3208,
						3226 },
				false, true, true, false, false, true) {
		},
		EDGEVILLE(new int[] { 3073, 3134 }, new int[] { 3457, 3518 }, false, true, true, false, false, true) {
		},
		LUMBRIDGE(new int[] { 3175, 3238 }, new int[] { 3179, 3302 }, false, true, true, true, true, true) {
		},
		/*
		 * KING_BLACK_DRAGON(new int[] { 2251, 2292 }, new int[] { 4673, 4717 }, true,
		 * true, true, true, true, true) { },
		 */
		SLASH_BASH(new int[] { 2504, 2561 }, new int[] { 9401, 9473 }, true, true, true, true, true, true) {
		},
		BANDOS_AVATAR(new int[] { 2877, 2928 }, new int[] { 4734, 4787 }, true, true, true, true, true, true) {
		},
		KALPHITE_QUEEN(new int[] { 3464, 3500 }, new int[] { 9478, 9523 }, true, true, true, true, true, true) {
		},
		PHOENIX(new int[] { 2824, 2862 }, new int[] { 9545, 9594 }, true, true, true, true, true, true) {
		},
		BANDIT_CAMP(new int[] { 3020, 3150, 3055, 3195 }, new int[] { 3684, 3711, 2958, 3003 }, true, true, true, true,
				true, true) {
		},
		ROCK_CRABS(new int[] { 2689, 2727 }, new int[] { 3691, 3730 }, true, true, true, true, true, true) {
		},
		ARMOURED_ZOMBIES(new int[] { 3077, 3132 }, new int[] { 9657, 9680 }, true, true, true, true, true, true) {
		},
		CORPOREAL_BEAST(new int[] { 2879, 2962 }, new int[] { 4368, 4406 }, true, true, true, false, true, true) {
		},
		DAGANNOTH_DUNGEON(new int[] { 2886, 2938 }, new int[] { 4431, 4477 }, true, true, true, false, true, true) {
		},
		WILDERNESS(new int[] { 2941, 3392, 2986, 3012, 3653, 3706, 3650, 3653 },
				new int[] { 3523, 3968, 10338, 10366, 3441, 3538, 3457, 3472 }, false, true, true, true, true, true) {
			@Override
			public boolean canAttack(Player player, Player target) {
				int combatDifference = CombatFactory.combatLevelDifference(player.getSkillManager().getCombatLevel(),
						target.getSkillManager().getCombatLevel());
				if (combatDifference > player.getWildernessLevel() || combatDifference > target.getWildernessLevel()) {
					player.getPacketSender()
							.sendMessage("Your combat level difference is too great to attack that player here.");
					player.getMovementQueue().reset();
					return false;
				}
				if (target.getLocation() != Location.WILDERNESS) {
					player.getPacketSender()
							.sendMessage("That player cannot be attacked, because they are not in the Wilderness.");
					player.getMovementQueue().reset();
					return false;
				}

				if (Misc.getMinutesPlayed(player) < 20) {
					player.getPacketSender()
							.sendMessage("You must have played for at least 20 minutes in order to attack someone.");
					return false;
				}
				if (Misc.getMinutesPlayed(target) < 20) {
					player.getPacketSender()
							.sendMessage("This player is a new player and can therefore not be attacked yet.");
					return false;
				}

				return true;
			}

			@Override
			public boolean canTeleport(Player player) {
				if (player.getWildernessLevel() > 20) {
					if (player.getRights() == PlayerRights.MODERATOR || player.getRights() == PlayerRights.OWNER
							|| player.getRights() == PlayerRights.DEVELOPER) {
						player.getPacketSender()
								.sendMessage("@red@You've teleported out of deep Wilderness, logs have been written.");
						return true;
					}
					player.getPacketSender().sendMessage("Teleport spells are blocked in this level of Wilderness.");
					player.getPacketSender()
							.sendMessage("You must be below level 20 of Wilderness to use teleportation spells.");
					return false;
				}
				return true;
			}

			@Override
			public void enter(Player player) {
				player.getPacketSender().sendInteractionOption("Attack", 2, true);
				player.getPacketSender().sendWalkableInterface(25347);
				player.getPacketSender().sendString(19000,
						"Combat level: " + player.getSkillManager().getCombatLevel());
				player.getUpdateFlag().flag(Flag.APPEARANCE);
				PLAYERS_IN_WILD++;
			}

			@Override
			public void leave(Player player) {
				if (player.getLocation() != this) {
					player.getPacketSender().sendString(19000,
							"Combat level: " + player.getSkillManager().getCombatLevel());
					player.getUpdateFlag().flag(Flag.APPEARANCE);
				}
				PLAYERS_IN_WILD--;
			}

			@Override
			public void login(Player player) {
				player.performGraphic(new Graphic(2000, 8));
			}

			@Override
			public void process(Player player) {
				int x = player.getPosition().getX();
				int y = player.getPosition().getY();
				boolean ghostTown = x >= 3650 && y <= 3538;
				if (ghostTown) {
					player.setWildernessLevel(60);
				} else {
					player.setWildernessLevel(((((y > 6400 ? y - 6400 : y) - 3525) / 8) + 1));
				}
				player.getPacketSender().sendString(25352, "" + player.getWildernessLevel());
				player.getPacketSender().sendString(25355, "Levels: " + CombatFactory.getLevelDifference(player, false)
						+ " - " + CombatFactory.getLevelDifference(player, true));
				BountyHunter.process(player);
			}
		},
		SOULWARS(new int[] { -1, -1 }, new int[] { -1, -1 }, true, true, true, false, true, true) {
			@Override
			public boolean canTeleport(Player player) {
				player.getPacketSender()
						.sendMessage("If you wish to leave, you must use the portal in your team's lobby.");
				return false;
			}

			@Override
			public boolean handleKilledNPC(Player killer, NPC npc) {

				return false;
			}

			@Override
			public void logout(Player player) {

			}

			@Override
			public void onDeath(Player player) {

			}

			@Override
			public void process(Player player) {

			}

		},
		SOULWARS_WAIT(new int[] { -1, -1 }, new int[] { -1, -1 }, false, false, false, false, false, true) {
			@Override
			public boolean canTeleport(Player player) {
				player.getPacketSender().sendMessage("You must leave the waiting room before being able to teleport.");
				return false;
			}

			@Override
			public void logout(Player player) {
			}

			@Override
			public void process(Player player) {
			}
		},
		GRAVEYARD(new int[] { 3485, 3517 }, new int[] { 3559, 3580 }, true, false, false, true, false, false) {
			@Override
			public boolean canTeleport(Player player) {
				if (player.getMinigameAttributes().getGraveyardAttributes().hasEntered()) {
					player.getPacketSender().sendInterfaceRemoval()
							.sendMessage("A spell teleports you out of the graveyard.");
					Graveyard.leave(player);
					return false;
				}
				return true;
			}

			@Override
			public boolean handleKilledNPC(Player killer, NPC npc) {
				return killer.getMinigameAttributes().getGraveyardAttributes().hasEntered()
						&& Graveyard.handleDeath(killer, npc);
			}

			@Override
			public void logout(Player player) {
				if (player.getMinigameAttributes().getGraveyardAttributes().hasEntered()) {
					Graveyard.leave(player);
				}
			}

			@Override
			public void onDeath(Player player) {
				Graveyard.leave(player);
			}

			@Override
			public void process(Player player) {
			}
		},
		DUEL_ARENA(new int[] { 3322, 3394, 3311, 3323, 3331, 3391 }, new int[] { 3195, 3291, 3223, 3248, 3242, 3260 },
				false, false, false, false, false, false) {
			@Override
			public boolean canAttack(Player player, Player target) {
				if (target.getIndex() != player.getDueling().duelingWith) {
					player.getPacketSender().sendMessage("That player is not your opponent!");
					return false;
				}
				if (player.getDueling().timer != -1) {
					player.getPacketSender().sendMessage("You cannot attack yet!");
					return false;
				}
				return player.getDueling().duelingStatus == 5 && target.getDueling().duelingStatus == 5;
			}

			@Override
			public boolean canTeleport(Player player) {
				if (player.getDueling().duelingStatus == 5) {
					player.getPacketSender().sendMessage("To forfiet a duel, run to the west and use the trapdoor.");
					return false;
				}
				return true;
			}

			@Override
			public void enter(Player player) {
				PLAYERS_IN_DUEL_ARENA++;
				player.getPacketSender()
						.sendMessage("<col=996633>Warning! Do not stake items which you are not willing to lose.");
			}

			@Override
			public void leave(Player player) {
				if (player.getDueling().duelingStatus == 5) {
					onDeath(player);
				}
				PLAYERS_IN_DUEL_ARENA--;
			}

			@Override
			public void logout(Player player) {
				boolean dc = false;
				if (player.getDueling().inDuelScreen && player.getDueling().duelingStatus != 5) {
					player.getDueling().declineDuel(player.getDueling().duelingWith > 0 ? true : false);
				} else if (player.getDueling().duelingStatus == 5) {
					if (player.getDueling().duelingWith > -1) {
						Player duelEnemy = World.getPlayers().get(player.getDueling().duelingWith);
						if (duelEnemy != null) {
							duelEnemy.getDueling().duelVictory();
						} else {
							dc = true;
						}
					}
				}
				player.moveTo(new Position(3368, 3268));
				if (dc) {
					World.getPlayers().remove(player);
				}
			}

			@Override
			public void onDeath(Player player) {
				if (player.getDueling().duelingStatus == 5) {
					if (player.getDueling().duelingWith > -1) {
						Player duelEnemy = World.getPlayers().get(player.getDueling().duelingWith);
						if (duelEnemy != null) {
							duelEnemy.getDueling().duelVictory();
							duelEnemy.getPacketSender().sendMessage("You won the duel! Congratulations!");
						}
					}
					player.getPacketSender().sendMessage("You've lost the duel.");
					player.getDueling().arenaStats[1]++;
					player.getDueling().reset();
				}
				player.moveTo(new Position(3368 + Misc.getRandom(5), 3267 + Misc.getRandom(3)));
				player.getDueling().reset();
			}

			@Override
			public void process(Player player) {
				if (player.getWalkableInterfaceId() != 201)
					player.getPacketSender().sendWalkableInterface(201);
				if (player.getDueling().duelingStatus == 0) {
					if (player.getPlayerInteractingOption() != PlayerInteractingOption.CHALLENGE)
						player.getPacketSender().sendInteractionOption("Challenge", 2, false);
				} else if (player.getPlayerInteractingOption() != PlayerInteractingOption.ATTACK)
					player.getPacketSender().sendInteractionOption("Attack", 2, true);
			}
		},
		NOMAD(new int[] { 3342, 3377 }, new int[] { 5839, 5877 }, true, true, false, true, false, true) {
			@Override
			public boolean canTeleport(Player player) {
				player.getPacketSender().sendMessage(
						"Teleport spells are blocked here. If you'd like to leave, use the southern exit.");
				return false;
			}

			@Override
			public boolean handleKilledNPC(Player killer, NPC npc) {
				if (npc.getId() == 8528) {
					Nomad.endFight(killer, true);
					return true;
				}
				return false;
			}

			@Override
			public void leave(Player player) {
				if (player.getRegionInstance() != null)
					player.getRegionInstance().destruct();
				player.moveTo(new Position(1890, 3177));
				player.restart();
			}
		},
		ELEMENTAL(new int[] { 3805, 3820 }, new int[] { 3561, 3581 }, true, true, true, true, true, true) {
		},
		KILLSTART(new int[] { 2435, 2448 }, new int[] { 3080, 3100 }, true, true, true, true, true, true) {
		},
		SCORPION(new int[] { 2200, 2240 }, new int[] { 3270, 3320 }, true, true, true, true, true, true) {
		},
		GAMBLES(new int[] { 3035, 3056 }, new int[] { 3371, 3385 }, true, true, true, true, true, true) {
		},
		FREEZER(new int[] { 2510, 2550 }, new int[] { 3720, 3760 }, true, true, true, true, true, true) {
		},
		CAMO_TORVA(new int[] { 2527, 2552 }, new int[] { 5755, 5788 }, true, true, true, true, true, true) {
		},
		WINTER_TORVA(new int[] { 2853, 2880 }, new int[] { 9932, 9968 }, true, true, true, true, true, true) {
		},
		BLOODSHOT_TORVA(new int[] { 3223, 3243 }, new int[] { 3936, 3952 }, true, true, true, true, true, true) {
		},
		RECIPE_FOR_DISASTER(new int[] { 1885, 1913 }, new int[] { 5340, 5369 }, true, true, false, false, false,
				false) {

			@Override
			public boolean canTeleport(Player player) {
				player.getPacketSender()
						.sendMessage("Teleport spells are blocked here. If you'd like to leave, use a portal.");
				return false;
			}

			@Override
			public boolean handleKilledNPC(Player killer, NPC npc) {
				RecipeForDisaster.handleNPCDeath(killer, npc);
				return true;
			}

			@Override
			public void leave(Player player) {
				if (player.getRegionInstance() != null)
					player.getRegionInstance().destruct();
				player.moveTo(new Position(3041, 2855));
			}

			@Override
			public void onDeath(Player player) {
				leave(player);
			}
		},
		KILLGAME(new int[] { 2252, 2290 }, new int[] { 4678, 4712 }, true, true, true, false, false, true) {
			@Override
			public boolean canAttack(Player player, Player target) {
				if (target.getLocation() != KILLGAME) {
					player.getPacketSender().sendMessage("That player is not in the minigame!");
					player.getMovementQueue().reset();
					return false;
				}
				return true;
			}

			@Override
			public boolean canTeleport(Player player) {
				player.getPacketSender().sendMessage("Teleport spells are blocked here!");
				return false;
			}

			@Override
			public void enter(Player player) {
				if (player.getPlayerInteractingOption() != PlayerInteractingOption.ATTACK) {
					player.getPacketSender().sendInteractionOption("Attack", 2, true);
				}
			}

			@Override
			public void onDeath(Player player) {
				KillGame.removePlayerGame(player);
			}
		},
		FREE_FOR_ALL_ARENA(new int[] { 2755, 2876 }, new int[] { 5512, 5627 }, true, true, true, false, false, true) {
			@Override
			public boolean canAttack(Player player, Player target) {
				if (target.getLocation() != FREE_FOR_ALL_ARENA) {
					player.getPacketSender().sendMessage("That player has not entered the dangerous zone yet.");
					player.getMovementQueue().reset();
					return false;
				}
				return true;
			}

			@Override
			public boolean canTeleport(Player player) {
				player.getPacketSender()
						.sendMessage("Teleport spells are blocked here, if you wish to teleport, use the portal.");
				return false;
			}

			@Override
			public void enter(Player player) {
				if (player.getPlayerInteractingOption() != PlayerInteractingOption.ATTACK) {
					player.getPacketSender().sendInteractionOption("Attack", 2, true);
				}
			}

			@Override
			public void onDeath(Player player) {
				player.moveTo(new Position(2815, 5511));
			}

		},
		FREE_FOR_ALL_WAIT(new int[] { 2755, 2876 }, new int[] { 5507, 5627 }, false, false, true, false, false, true) {
			@Override
			public boolean canTeleport(Player player) {
				player.getPacketSender()
						.sendMessage("Teleport spells are blocked here, if you wish to teleport, use the portal.");
				return false;
			}

			@Override
			public void onDeath(Player player) {
				player.moveTo(new Position(2815, 5511));
			}
		},
		PURO_PURO(new int[] { 2556, 2630 }, new int[] { 4281, 4354 }, false, true, true, false, false, true) {
		},
		FLESH_CRAWLERS(new int[] { 2033, 2049 }, new int[] { 5178, 5197 }, false, true, true, false, true, true) {
		},
		DEFAULT(null, null, false, true, true, true, true, true) {
		};

		public static Location getLocation(Entity gc) {
			for (Location location : Location.values()) {
				if (location != Location.DEFAULT)
					if (inLocation(gc, location))
						return location;
			}
			return Location.DEFAULT;
		}

		/**
		 * SHOULD AN ENTITY FOLLOW ANOTHER ENTITY NO MATTER THE DISTANCE BETWEEN THEM?
		 **/
		public static boolean ignoreFollowDistance(Character character) {
			Location location = character.getLocation();
			return location == Location.RECIPE_FOR_DISASTER || location == Location.NOMAD;
		}

		public static boolean inLocation(Entity gc, Location location) {
			if (location == Location.DEFAULT) {
				if (getLocation(gc) == Location.DEFAULT)
					return true;
				else
					return false;
			}
			/*
			 * if(gc instanceof Player) { Player p = (Player)gc; if(location ==
			 * Location.TRAWLER_GAME) { String state = FishingTrawler.getState(p); return
			 * (state != null && state.equals("PLAYING")); } else if(location ==
			 * FIGHT_PITS_WAIT_ROOM || location == FIGHT_PITS) { String state =
			 * FightPit.getState(p), needed = (location == FIGHT_PITS_WAIT_ROOM) ? "WAITING"
			 * : "PLAYING"; return (state != null && state.equals(needed)); } else
			 * if(location == Location.SOULWARS) { return (SoulWars.redTeam.contains(p) ||
			 * SoulWars.blueTeam.contains(p) && SoulWars.gameRunning); } else if(location ==
			 * Location.SOULWARS_WAIT) { return SoulWars.isWithin(SoulWars.BLUE_LOBBY, p) ||
			 * SoulWars.isWithin(SoulWars.RED_LOBBY, p); } }
			 */
			return inLocation(gc.getPosition().getX(), gc.getPosition().getY(), location);
		}

		public static boolean inLocation(int absX, int absY, Location location) {
			int checks = location.getX().length - 1;
			for (int i = 0; i <= checks; i += 2) {
				if (absX >= location.getX()[i] && absX <= location.getX()[i + 1]) {
					if (absY >= location.getY()[i] && absY <= location.getY()[i + 1]) {
						return true;
					}
				}
			}
			return false;
		}

		public static boolean inMinigame(Player player) {
			int x = player.getPosition().getX();
			int y = player.getPosition().getY();

			if(x >= 3174 && x <= 3181 && y >= 3022 && y <= 3042
					||x >= 3161 && x <= 3168 && y >= 3022 && y <= 3042
					||x >= 1881 && x <= 1888 && y >= 4814 && y <= 4834
					||x >= 1894 && x <= 1901 && y >= 4814 && y <= 4834
					||x >= 1945 && x <= 1952 && y >= 4814 && y <= 4834
					||x >= 1958 && x <= 1965 && y >= 4814 && y <= 4834

					||x >= 2009 && x <= 2016 && y >= 4814 && y <= 4834
					||x >= 2022 && x <= 2029 && y >= 4814 && y <= 4834
					||x >= 2585 && x <= 2592 && y >= 4622 && y <= 4642
					||x >= 2009 && x <= 2016 && y >= 4494 && y <= 4514
					||x >= 2598 && x <= 2605 && y >= 4622 && y <= 4642
					||x >= 2022 && x <= 2029 && y >= 4494 && y <= 4514

					||x >= 2777 && x <= 2784 && y >= 3854 && y <= 3874
					||x >= 2790 && x <= 2797 && y >= 3854 && y <= 3874
					||x >= 2265 && x <= 2272 && y >= 5006 && y <= 5026
					||x >= 2278 && x <= 2285 && y >= 5006 && y <= 5026
					||x >= 2393 && x <= 2400 && y >= 5006 && y <= 5026
					||x >= 2406 && x <= 2413 && y >= 5006 && y <= 5026) {
				return true;
			} else {
				return false;
			}
		}

		public static boolean inMulti(Character gc) {
			int xx = gc.getPosition().getX(), yy = gc.getPosition().getY();
			if (xx >= 2708 && xx <= 2737 && yy >= 4884 && yy <= 4918
					|| xx >= 2625 && xx <= 2642 && yy >= 4905 && yy <= 4930
					|| xx >= 2769 && xx <= 2800 && yy >= 10013 && yy <= 10035
					|| xx >= 2570 && xx <= 2590 && yy >= 4516 && yy <= 4540
					|| xx >= 3820 && xx <= 3840 && yy >= 3045 && yy <= 3065
					|| xx >= 2610 && xx <= 2720 && yy >= 3950 && yy <= 4030
					|| xx >= 2785 && xx <= 2800 && yy >= 9320 && yy <= 9340
					|| xx >= 2450 && xx <= 2480 && yy >= 4770 && yy <= 4795
					|| xx >= 2348 && xx <= 2356 && yy >= 5064 && yy <= 5074
					|| xx >= 3414 && xx <= 3430 && yy >= 2978 && yy <= 2988
					|| xx >= 1860 && xx <= 1885 && yy >= 4936 && yy <= 4958
					|| xx >= 3411 && xx <= 3433 && yy >= 9618 && yy <= 9640
					|| xx >= 2787 && xx <= 2800 && yy >= 3319 && yy <= 3329
					|| xx >= 2968 && xx <= 2984 && yy >= 9499 && yy <= 9515
					|| xx >= 2202 && xx <= 2214 && yy >= 4952 && yy <= 4964
					|| xx >= 2850 && xx <= 2910 && yy >= 5890 && yy <= 5950) {
				return true;
			}
			if (gc.getLocation() == WILDERNESS) {
				int x = gc.getPosition().getX(), y = gc.getPosition().getY();
				if (x >= 3250 && x <= 3302 && y >= 3905 && y <= 3925 || x >= 3020 && x <= 3055 && y >= 3684 && y <= 3711
						|| x >= 3150 && x <= 3195 && y >= 2958 && y <= 3003
						|| x >= 3303 && x <= 3315 && y >= 3924 && y <= 3950
						|| x >= 3220 && x <= 3255 && y >= 3750 && y <= 3778
						|| x >= 3129 && x <= 3160 && y >= 3876 && y <= 3893
						|| x >= 3170 && x <= 3198 && y >= 3670 && y <= 3710) {
					return true;
				}
			}
			return gc.getLocation().multi;
		}

		private int[] x, y;
		private boolean multi;
		private boolean summonAllowed;

		private boolean followingAllowed;

		private boolean cannonAllowed;

		private boolean firemakingAllowed;

		private boolean aidingAllowed;

		Location(int[] x, int[] y, boolean multi, boolean summonAllowed, boolean followingAllowed,
				boolean cannonAllowed, boolean firemakingAllowed, boolean aidingAllowed) {
			this.x = x;
			this.y = y;
			this.multi = multi;
			this.summonAllowed = summonAllowed;
			this.followingAllowed = followingAllowed;
			this.cannonAllowed = cannonAllowed;
			this.firemakingAllowed = firemakingAllowed;
			this.aidingAllowed = aidingAllowed;
		}

		public boolean canAttack(Player player, Player target) {
			return false;
		}

		public boolean canTeleport(Player player) {
			return true;
		}

		public void enter(Player player) {

		}

		public int[] getX() {
			return x;
		}

		public int[] getY() {
			return y;
		}

		public boolean handleKilledNPC(Player killer, NPC npc) {
			return false;
		}

		public boolean isAidingAllowed() {
			return aidingAllowed;
		}

		public boolean isCannonAllowed() {
			return cannonAllowed;
		}

		public boolean isFiremakingAllowed() {
			return firemakingAllowed;
		}

		public boolean isFollowingAllowed() {
			return followingAllowed;
		}

		public boolean isSummoningAllowed() {
			return summonAllowed;
		}

		public void leave(Player player) {

		}

		public void login(Player player) {

		}

		public void logout(Player player) {

		}

		public void onDeath(Player player) {

		}

		public void process(Player player) {

		}
	}

	public static int PLAYERS_IN_WILD;

	public static int PLAYERS_IN_DUEL_ARENA;

	public static int distanceTo(Position position, Position destination, int size) {
		final int x = position.getX();
		final int y = position.getY();
		final int otherX = destination.getX();
		final int otherY = destination.getY();
		int distX, distY;
		if (x < otherX)
			distX = otherX - x;
		else if (x > otherX + size)
			distX = x - (otherX + size);
		else
			distX = 0;
		if (y < otherY)
			distY = otherY - y;
		else if (y > otherY + size)
			distY = y - (otherY + size);
		else
			distY = 0;
		if (distX == distY)
			return distX + 1;
		return distX > distY ? distX : distY;
	}

	public static boolean goodDistance(int objectX, int objectY, int playerX, int playerY, int distance) {
		if (playerX == objectX && playerY == objectY)
			return true;
		for (int i = 0; i <= distance; i++) {
			for (int j = 0; j <= distance; j++) {
				if ((objectX + i) == playerX
						&& ((objectY + j) == playerY || (objectY - j) == playerY || objectY == playerY)) {
					return true;
				} else if ((objectX - i) == playerX
						&& ((objectY + j) == playerY || (objectY - j) == playerY || objectY == playerY)) {
					return true;
				} else if (objectX == playerX
						&& ((objectY + j) == playerY || (objectY - j) == playerY || objectY == playerY)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean goodDistance(Position pos1, Position pos2, int distanceReq) {
		if (pos1.getZ() != pos2.getZ())
			return false;
		return goodDistance(pos1.getX(), pos1.getY(), pos2.getX(), pos2.getY(), distanceReq);
	}

	public static void login(Player player) {
		player.setLocation(Location.getLocation(player));
		player.getLocation().login(player);
		player.getLocation().enter(player);
	}

	public static void logout(Player player) {
		player.getLocation().logout(player);
		ClanChatManager.leave(player, false);
		if (player.getRegionInstance() != null)
			player.getRegionInstance().destruct();
		if (player.getLocation() != Location.NOMAD) {
			player.getLocation().leave(player);
		}
	}

	public static void process(Character gc) {
		Location newLocation = Location.getLocation(gc);
		if (gc.getLocation() == newLocation) {
			if (gc.isPlayer()) {
				Player player = (Player) gc;
				gc.getLocation().process(player);
				if (Location.inMulti(player)) {
					if (player.getMultiIcon() != 1)
						player.getPacketSender().sendMultiIcon(1);
				} else if (player.getMultiIcon() == 1)
					player.getPacketSender().sendMultiIcon(0);
			}
		} else {
			Location prev = gc.getLocation();
			if (gc.isPlayer()) {
				Player player = (Player) gc;
				if (player.getMultiIcon() > 0)
					player.getPacketSender().sendMultiIcon(0);
				if (player.getWalkableInterfaceId() > 0 && player.getWalkableInterfaceId() != 37400
						&& player.getWalkableInterfaceId() != 50000)
					player.getPacketSender().sendWalkableInterface(-1);
				if (player.getPlayerInteractingOption() != PlayerInteractingOption.NONE)
					player.getPacketSender().sendInteractionOption("null", 2, true);
			}
			gc.setLocation(newLocation);
			if (gc.isPlayer()) {
				prev.leave(((Player) gc));
				gc.getLocation().enter(((Player) gc));
			}
		}
	}
}
