package com.ruthlessps.world.content;

import com.ruthlessps.GameSettings;
import com.ruthlessps.model.Flag;
import com.ruthlessps.model.GameMode;
import com.ruthlessps.model.Item;
import com.ruthlessps.world.World;
import com.ruthlessps.world.content.Achievements.AchievementData;
import com.ruthlessps.world.content.pos.PlayerOwnedShop;
import com.ruthlessps.world.entity.impl.player.Player;

public class StartScreen {
	public enum GameModes {
		NORMAL("Normal", -18533, -18533, 1, 0,
				new Item[] { new Item(1856, 1), new Item(20696, 1), new Item(20693, 1), new Item(4646, 1),
						new Item(4647, 1), (new Item(4648, 1)), new Item(4649, 1), new Item(4650, 1), new Item(4651, 1), (new Item(3083, 1)), (new Item(892, 1000)),
						(new Item(995, 1000000000)), (new Item(4049, 1)), (new Item(2437, 100)), (new Item(2441, 100)), (new Item(2443, 100)), (new Item(3025, 100)),
						(new Item(554, 1000)), (new Item(555, 1000)), (new Item(556, 1000)), (new Item(557, 1000)) },
				"", "Play " + GameSettings.SERVER_NAME + " on normal mode.",
				"As a normal player you will be able to play the game without any restrictions,", "nor any boosts.", "",
				"", ""),

		IRONMAN("  Ironman", -18532, -18532, 1, 1,
				new Item[] { new Item(1856, 1), new Item(20696, 1), new Item(20693, 1), new Item(4646, 1),
						new Item(4647, 1), (new Item(4648, 1)), new Item(4649, 1), new Item(4650, 1), new Item(4651, 1), (new Item(3083, 1)), (new Item(892, 1000)),
						(new Item(995, 100000000)), (new Item(4049, 1)), (new Item(2437, 100)), (new Item(2441, 100)), (new Item(2443, 100)), (new Item(3025, 100)),
						(new Item(554, 1000)), (new Item(555, 1000)), (new Item(556, 1000)), (new Item(557, 1000))},
				"Play " + GameSettings.SERVER_NAME + " as an Iron man.",
				"You will be restricted from trading, staking and looting items from killed players. You will not",
				"get a npc drop if another player has done more damage. You will have to rely on your starter,",
				"skilling, pvming, and shops. This game mode is for players that love a challenge.", " All points 1.25x", "", ""),

		HARDCORE("  Hardcore", -18531, -18531, 1, 2,
				new Item[] { new Item(1856, 1), new Item(20696, 1), new Item(20693, 1), new Item(4646, 1),
						new Item(4647, 1), (new Item(4648, 1)), new Item(4649, 1), new Item(4650, 1), new Item(4651, 1), (new Item(3083, 1)), (new Item(892, 1000)),
						(new Item(995, 10000000)), (new Item(4049, 1)), (new Item(2437, 100)), (new Item(2441, 100)), (new Item(2443, 100)), (new Item(3025, 100)),
						(new Item(554, 1000)), (new Item(555, 1000)), (new Item(556, 1000)), (new Item(557, 1000)) },
				"Play " + GameSettings.SERVER_NAME + " on Hardcore mode.",
				"Hardcore mode has 8x slower xp rate than normal players w/ 5% increased drop rate,",
				"All in-game points are multiplied by x1.4, items are LOST ON DEATH. These will",
				"stack with other ranks & items such as donator ranks & ring of wealth.", "You also get 2 Mega Mystery Boxes upon reaching a 99 in a non-combat skill.", "", "");

		private String name;
		private int stringId;
		private int checkClick;
		private int textClick;
		private int configId;
		protected Item[] starterPackItems;
		private String line1;
		private String line2;
		private String line3;
		private String line4;
		private String line5;
		private String line6;
		private String line7;

		private GameModes(String name, int stringId, int checkClick, int textClick, int configId,
				Item[] starterPackItems, String line1, String line2, String line3, String line4, String line5,
				String line6, String line7) {
			this.name = name;
			this.stringId = stringId;
			this.checkClick = checkClick;
			this.textClick = textClick;
			this.configId = configId;
			this.starterPackItems = starterPackItems;
			this.line1 = line1;
			this.line2 = line2;
			this.line3 = line3;
			this.line4 = line4;
			this.line5 = line5;
			this.line6 = line6;
			this.line7 = line7;
		}
	}

	public static void addStarterToInv(Player player) {
		for (Item item : player.selectedGameMode.starterPackItems) {
			player.getInventory().add(item);
		}
		BankPin.init(player, false);
	}

	public static void resetStarter(Player player) {
		for (GameModes g : GameModes.values()) {
			if (g.toString().equalsIgnoreCase(player.getGameMode().toString()))
				for (Item item : g.starterPackItems) {
					player.getInventory().add(item);
				}
		}
	}

	public static void check(Player player, GameModes mode) {
		for (GameModes gameMode : GameModes.values()) {
			if (player.selectedGameMode == gameMode) {
				player.getPacketSender().sendConfig(gameMode.configId, 1);
				continue;
			}
			player.getPacketSender().sendConfig(gameMode.configId, 0);
		}
	}

	public static boolean handleButton(Player player, int buttonId) {
		final int CONFIRM = -18530;
		if (buttonId == CONFIRM) {
			handleConfirm(player);
			return true;
		}
		for (GameModes mode : GameModes.values()) {
			if (mode.checkClick == buttonId || mode.textClick == buttonId) {
				selectMode(player, mode);
				return true;
			}
		}
		return false;
	}

	public static void handleConfirm(Player player) {
		if (!player.getClickDelay().elapsed(10000)) {
			return;
		}
		player.getClickDelay().reset();
		player.getPacketSender().sendInterfaceRemoval();
		if(player.getGameMode() == GameMode.HARDCORE || player.getGameMode() == GameMode.IRONMAN) {
			World.sendStaffMessage("@red@Player @blu@"+player.getUsername()+"@red@ has just tried to dupe items in Cheat engine.");
			return;
		}
		if (player.selectedGameMode == GameModes.HARDCORE) {
			player.setGameMode(GameMode.HARDCORE);
		} else if (player.selectedGameMode == GameModes.IRONMAN) {
			player.setGameMode(GameMode.IRONMAN);
		} else {
			player.setGameMode(GameMode.NORMAL);
		}
		addStarterToInv(player);
		player.setPlayerLocked(false);
		player.getPacketSender().sendIronmanMode(player.getGameMode().ordinal());
		player.getUpdateFlag().flag(Flag.APPEARANCE);
	}

	public static void open(Player player) {
		sendNames(player);
		player.getPacketSender().sendInterface(47000);
		player.selectedGameMode = GameModes.NORMAL;
		check(player, GameModes.NORMAL);
		sendStartPackItems(player, GameModes.NORMAL);
		sendDescription(player, GameModes.NORMAL);
	}

	public static void selectMode(Player player, GameModes mode) {
		player.selectedGameMode = mode;
		check(player, mode);
		sendStartPackItems(player, mode);
		sendDescription(player, mode);
	}

	public static void sendDescription(Player player, GameModes mode) {
		int s = 47002;
		String text = mode.line1 + "\\n" + mode.line2 + "\\n" + mode.line3 + "\\n" + mode.line4 + "\\n" + mode.line5
				+ "\\n" + mode.line6 + "\\n" + mode.line7;

		player.getPacketSender().sendString(s, text);
	}

	public static void sendNames(Player player) {
		for (GameModes mode : GameModes.values()) {
			player.getPacketSender().sendString(mode.stringId, mode.name);
		}
	}

	public static void sendStartPackItems(Player player, GameModes mode) {
		final int START_ITEM_INTERFACE = 59025;
		for (int i = 0; i < 28; i++) {
			int id = -1;
			int amount = 0;
			try {
				id = mode.starterPackItems[i].getId();
				amount = mode.starterPackItems[i].getAmount();
			} catch (Exception e) {

			}
			player.getPacketSender().sendItemOnInterface(START_ITEM_INTERFACE + i, id, amount);
		}
	}
}
