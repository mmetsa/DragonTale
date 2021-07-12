package com.ruthlessps.world.content.minigames.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.ruthlessps.model.Flag;
import com.ruthlessps.model.GroundItem;
import com.ruthlessps.model.Item;
import com.ruthlessps.model.Locations.Location;
import com.ruthlessps.model.Position;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.World;
import com.ruthlessps.world.content.Achievements;
import com.ruthlessps.world.content.Achievements.AchievementData;
import com.ruthlessps.world.content.teleportation.TeleportHandler;
import com.ruthlessps.world.entity.impl.GroundItemManager;
import com.ruthlessps.world.entity.impl.player.Player;

public class KillGame {
	private static HashMap<Player, Integer> waitingArea = new HashMap<Player, Integer>();
	private static ArrayList<Player> players = new ArrayList<Player>();
	private static HashMap<Player, Integer> gamePlayers = new HashMap<Player, Integer>();
	private static ArrayList<Player> gamerPlayers = new ArrayList<Player>();
	public static final int playersNeeded = 2;
	private static final int WAIT_TIMER = 300;
	

	private static int waitTimer = 120;
	private static boolean gameStarted = false;
	private static int playeramount = 0;
	
	
	private static void cleanUp() {
		waitTimer = WAIT_TIMER;
		gameStarted = false;
		gamePlayers.clear();
		gamerPlayers.clear();
		playeramount = 0;
	}
	
	public static void addToWaitArea(Player player) {
		if(player.isBanking() || player.isTeleporting() || player.isDying() || player.isFrozen()) {
			return;
		}
		if (player != null) {
			for(int i = 0; i < 28; i++) {
				if(!player.getInventory().isSlotFree(i)) {
					player.getPacketSender().sendMessage("@red@Please remove any items you have in inventory!");
					return;
				}
			}
			for(int i = 0; i < 11; i++) {
				if(!player.getEquipment().isSlotFree(i)) {
					player.getPacketSender().sendMessage("@red@Please remove any items you have equipped!");
					return;
				}
			}
			if(player.getLocation() == Location.KILLSTART) {
				player.sendMessage("You are already waiting!");
				return;
			}
			player.sendMessage("You have joined the Hunger Games waiting area.");
			player.moveTo(new Position(2442, 3090, 0));
			waitingArea.put(player, 1);
			players.add(player);
		}
	}
	
	public static boolean isInGame(Player player) {
		return gamePlayers.containsKey(player);
	}
	
	private static void startGame() {
		//if we dont have
		if(gameStarted) {
			for(Player player : players) {
				player.getPacketSender().sendMessage("@blu@A game is still going on!");
			}
			waitTimer = WAIT_TIMER;
			return;
		}
		if (playersWaiting() < playersNeeded) {
			if(playersWaiting() == 0) {
				return;
			}
			waitTimer = WAIT_TIMER;
			World.sendMessage("@red@There are "+playersWaiting()+" players waiting in the Hunger Games area!");
			return;
		}
		waitTimer = -1;
		for(Player player : players) {
			//Player player = iterator.next();
			if (player == null) {
				continue;
			}
			playeramount++;
			if(waitingArea.containsKey(player)) {
				waitingArea.remove(player);
			}
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(1347, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(1215, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(1321, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(4587, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(1333, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(4747, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(13899, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(11730, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(11696, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(11037, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(6739, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(1319, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(1079, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(1127, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(1163, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(10551, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(10828, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(1073, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(1123, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(3085, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(1201, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(910, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(2550, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(6737, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(2760, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
//FOOD
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(385, 3), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(373, 3), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(373, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(373, 2), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(385, 2), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(385, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(315, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(315, 2), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(315, 3), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(379, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(379, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(379, 2), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(379, 3), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(379, 2), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(391, 2), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(391, 2), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(391, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(157, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));

//RANGED ITEMS
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(1065, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(1099, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(1135, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(6733, 1), new Position(2256+Misc.random(30), 4681+Misc.random(29)), "everyone", true, 2000, true, 10000));

			int i = Misc.random(3);
			if(i == 0) {
				TeleportHandler.teleportPlayer(player, new Position(2279, 4702, 0),
						player.getSpellbook().getTeleportType());
			} else if (i == 1) {
				TeleportHandler.teleportPlayer(player, new Position(2275, 4689, 0),
						player.getSpellbook().getTeleportType());
			} else if (i == 2) {
				TeleportHandler.teleportPlayer(player, new Position(2263, 4694, 0),
						player.getSpellbook().getTeleportType());
			} else if (i == 3) {
				TeleportHandler.teleportPlayer(player, new Position(2259, 4701, 0),
						player.getSpellbook().getTeleportType());
			}
			gamerPlayers.add(player);
			gamePlayers.put(player, 1);
			player.getPacketSender().sendMessage("@red@The Game has begun!");
			gameStarted = true;
		}
		waitingArea.clear();
		players.clear();
	}
	
	public static void process() {
		try {
			/**
			 * handling the wait time in lobby, if timer is done then attempt to start game
			 */
			if (waitTimer > 0)
				waitTimer--;
			else if (waitTimer == 0)
				startGame();
			if (gameStarted) {
				if (playeramount == 1) {
					endGame();
				}
			}
		} catch (RuntimeException e) {
			System.out.println("Failed to set process");
			e.printStackTrace();
		}
	}
	private static void endGame() {
		System.out.println("Endgame called");
		Iterator<Player> inGamePlayers = gamePlayers.keySet().iterator();
		while (inGamePlayers.hasNext()) {
			Player winner = inGamePlayers.next();
			if (winner != null) {
				winner.getCombatBuilder().reset(true);
				if (winner.getRegionInstance() != null)
					winner.getRegionInstance().destruct();
				winner.restart();
				winner.getInventory().resetItems().refreshItems();
				winner.getEquipment().resetItems().refreshItems();
				winner.getPacketSender().sendInterfaceRemoval();
				winner.setEntityInteraction(null);
				winner.getMovementQueue().setFollowCharacter(null);
				winner.getCombatBuilder().cooldown(false);
				winner.setTeleporting(false);
				winner.setWalkToTask(null);
				winner.getSkillManager().stopSkilling();
				winner.getPacketSender().sendMessage("@blu@Congratulations, you have won the Hunger Games!");
				winner.getPacketSender().sendMessage("@blu@You are rewarded with 10b!");
				winner.getInventory().add(13664, 10);
				winner.getUpdateFlag().flag(Flag.APPEARANCE);
				winner.moveTo(new Position(3041, 2855));
				//Achievements.doProgress(winner, AchievementData.WIN_HUNGER_GAMES);
				World.sendMessage("@blu@"+winner.getUsername()+" has won the Hunger Games!");
			}
		}
		cleanUp();
	}
	public static void removePlayerGame(Player player) {
		if (gamerPlayers.contains(player)) {
			player.moveTo(new Position(3041, 2855));
			gamePlayers.remove(player);
			gamerPlayers.remove(player);
			player.getPacketSender().sendMessage("@red@You've failed to survive!");
			player.getCombatBuilder().reset(true);
			if (player.getRegionInstance() != null)
				player.getRegionInstance().destruct();
			player.restart();
			player.getInventory().resetItems().refreshItems();
			player.getEquipment().resetItems().refreshItems();
			player.getPacketSender().sendInterfaceRemoval();
			player.setEntityInteraction(null);
			player.getMovementQueue().setFollowCharacter(null);
			player.getCombatBuilder().cooldown(false);
			player.setTeleporting(false);
			player.setWalkToTask(null);
			player.getSkillManager().stopSkilling();
			player.getUpdateFlag().flag(Flag.APPEARANCE);
			playeramount--;
			for(Player player2: gamerPlayers) {
				if(!Location.inLocation(player2, Location.KILLGAME)) {
					player2.moveTo(new Position(2279, 4702, 0));
					player2.getPacketSender().sendMessage("You need to go in the game!");
				}
			}
			
		}
	}
	
	
	public static void leaveWaitingArea(Player p) {
		if (waitingArea.containsKey(p)) {
			waitingArea.remove(p);
		}
	}
	
	public static boolean isInWaitArea(Player player) {
		return waitingArea.containsKey(player);
	}
	
	
	private static int playersWaiting() {
		int players = 0;
		Iterator<Player> iterator = waitingArea.keySet().iterator();
		while (iterator.hasNext()) {
			Player player = iterator.next();
			if (player != null) {
				players++;
			}
		}
		return players;
	}
	
	private static int playersInGame() {
		int players = 0;
		Iterator<Player> inGamePlayers = gamePlayers.keySet().iterator();
		while (inGamePlayers.hasNext()) {
			Player player = inGamePlayers.next();
			if (player != null) {
				players++;
			}
		}
		return players;
	}
}
