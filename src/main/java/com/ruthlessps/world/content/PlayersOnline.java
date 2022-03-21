package com.ruthlessps.world.content;

import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

import com.ruthlessps.model.GameMode;
import com.ruthlessps.model.PlayerRanks.DonorRights;
import com.ruthlessps.model.PlayerRanks.PlayerRights;
import com.ruthlessps.util.Misc;
import com.ruthlessps.util.NameUtils;
import com.ruthlessps.util.Stopwatch;
import com.ruthlessps.world.World;
import com.ruthlessps.world.entity.impl.player.Player;

public class PlayersOnline {

	private static Stopwatch lastResort = new Stopwatch();
	private final static CopyOnWriteArrayList<Player> PLAYERS_ONLINE_LIST = new CopyOnWriteArrayList<Player>();

	public static void add(Player player) {
		PLAYERS_ONLINE_LIST.add(player);
		updatePlayers();
	}

	private static void clearInterface(Player player) {
		for (int i = 57041; i < 57141; i++) {
			player.getPacketSender().sendString(i, "");
		}
		sendDetails(player, player);
		player.getPacketSender().sendConfig(1110, -1);
		player.setPlayerViewingIndex(-1);
	}

	public static void sendDetails(Player player, Player other) {
		String rank = "";

		int rankId = getValue(other); // other.getRights().ordinal();

		int mode = -1;
		if (other.getGameMode() == GameMode.IRONMAN) {
			mode = 444;
		} else if (other.getGameMode() == GameMode.HARDCORE) {
			mode = 711;
		}

		int donor = -1;
		switch (other.getDonor()) {
		case DELUXE_DONOR:
			donor = 443;
			break;
		case SPONSOR:
			donor = 440;
			break;
		case DONOR:
			donor = 441;
			break;
		}

		String preset = "Rank(s):";

		if (mode != -1) {
			rank += "<img=" + mode + ">  ";
		}
		if (donor > 0) {
			rank += "<img=" + donor + ">  ";
		}
		if (rankId > 0) {
			rank += "<img=" + rankId + "> ";
		}

		if (other.isModeler()) {
			rank += " <img=430> ";
		}
		if (other.isGambler()) {
			rank += " <img=429> ";
		}
		if (other.isGfxDesigner()) {
			rank += " <img=432> ";
		}
		if (other.isYoutuber()) {
			rank += " <img=433> ";
		}
		player.getPacketSender().sendString(55040, "Name: @red@" + other.getUsername())
				.sendString(55041, rank.equals("") ? "Ranks: Player" : "Ranks:")
				.sendString(55005,
						"" + Misc.getHoursPlayed((other.getTotalPlayTime() + other.getRecordedLogin().elapsed())))
				.sendString(55011, "$" + other.getAmountDonated())
				.sendString(55008, "" + Misc.formatText(other.getGameMode().name().toLowerCase()))
				.sendString(55017, "" + other.getSkillManager().getCombatLevel())
				.sendString(55014, " " + other.getSkillManager().getTotalLevel())
				.sendString(55023, "" + other.getPointsManager().getPoints("slayer"))
				.sendString(55020, "" + other.getPointsManager().getPoints("voting"))
				.sendString(57017, "" + other.getPointsManager().getPoints("pc"))
				.sendString(55026, "" + other.getPointsManager().getPoints("dung"))
				.sendString(55029, "" + other.getPointsManager().getPoints("pvp"))
				.sendString(55032, "" + other.getPlayerKillingAttributes().getPlayerKillStreak())
				.sendString(55035, "" + other.getPlayerKillingAttributes().getPlayerKills())
				.sendString(55038, "" + other.getPlayerKillingAttributes().getPlayerDeaths());
	}

	private static int getValue(Player p) {
		return p.getRights().ordinal();
	}

	public static boolean handleButton(Player player, int button) {

		if (button >= 8165 && button <= 8237) {
			int index = (button - (8165));
			updateInterface(player, index);
			return true;
		}
		switch (button) {
		case -10494:
			if (player.getPlayerViewingIndex() == -1) {
				return true;
			}
			Player player2 = PLAYERS_ONLINE_LIST.get(player.getPlayerViewingIndex());
			if (player2 == null) {
				return true;
			}
			player.getRelations().addFriend(NameUtils.stringToLong(player2.getUsername()));
			return true;
		case -10490:
			if (player.getPlayerViewingIndex() == -1) {
				return true;
			}
			player2 = PLAYERS_ONLINE_LIST.get(player.getPlayerViewingIndex());
			if (player2 == null) {
				return true;
			}
			player.getRelations().addIgnore(NameUtils.stringToLong(player2.getUsername()));
			return true;
		case -10489:
			if (player.getPlayerViewingIndex() == -1) {
				return true;
			}
			player2 = PLAYERS_ONLINE_LIST.get(player.getPlayerViewingIndex());
			if (player2 == null) {
				return true;
			}
			player.getPacketSender().sendMessage(":pm:_" + player2.getUsername());
			return true;
		}
		if (button == -8511 || button == -8508) {
			Player p = player.getPlayerViewingIndex() < PLAYERS_ONLINE_LIST.size()
					? PLAYERS_ONLINE_LIST.get(player.getPlayerViewingIndex())
					: null;
			if (p == null) {
				player.getPacketSender().sendMessage("Please select an active player.");
				return true;
			}
			player.getPacketSender().sendString(button == -8511 ? 57025 : 57028, p.getUsername());
			return true;
		}
		return false;
	}

	public static void remove(Player player) {
		PLAYERS_ONLINE_LIST.remove(player);
		updatePlayers();
	}

	private static void resort() {
		if (!lastResort.elapsed(1000)) {
			return;
		}
		lastResort.reset();
		Collections.sort(PLAYERS_ONLINE_LIST, new Comparator<Player>() {
			@Override
			public int compare(Player arg0, Player arg1) {
				int value1 = getValue(arg0);
				int value2 = getValue(arg1);
				if (value1 == value2) {
					return 0;
				} else if (value1 > value2) {
					return -1;
				} else {
					return 1;
				}
			}
		});
	}

	private static void sendInterfaceData(Player player) {
		int child = 57042;
		for (int i = 0; i < World.getPlayers().size(); i++) {
			if (i >= PLAYERS_ONLINE_LIST.size()) {
				player.getPacketSender().sendString(child, i + "");
				child++;
				continue;
			}
			Player p = PLAYERS_ONLINE_LIST.get(i);
			if (p == null)
				continue;
			int rankId = p.getRights().ordinal();
			int img = 0;
			int d = 0;
			if (player.getGameMode() == GameMode.IRONMAN) {
				img = 33;
			} else if (player.getGameMode() == GameMode.HARDCORE) {
				img = 32;
			}

			if (player.getRights() == PlayerRights.OWNER) {
				img = 3;
			}
			if (player.getRights() == PlayerRights.DEVELOPER) { // 679 main img 0
				img = 4;
			}
			if (player.getRights() == PlayerRights.MODERATOR) {
				img = 300;
			}
			if (player.getRights() == PlayerRights.ADMINISTRATOR) {
				img = 356;
			}
			if (player.getRights() == PlayerRights.EX_STAFF) {
				img = 366;
			}

			if (player.getRights() == PlayerRights.FORUM_ADMINISTRATOR) {
				img = 357;
			}
			if (player.getRights() == PlayerRights.TRIAL_MODERATOR) {
				img = 361;
			}
			if (player.getRights() == PlayerRights.WEB_DEVELOPER) {
				img = 354;
			}
			if (player.getRights() == PlayerRights.GLOBAL_ADMINISTRATOR) {
				img = 355;
			}
			if (player.getRights() == PlayerRights.GLOBAL_MODERATOR) {
				img = 358;
			}
			if (player.getRights() == PlayerRights.FORUM_MODERATOR) {
				img = 360;
			}
			if (player.getRights() == PlayerRights.TRIAL_FORUM_MODERATOR) {
				img = 362;
			}
			if (player.getRights() == PlayerRights.MANAGER) {
				img = 658;
			}
			if (player.getRights() == PlayerRights.HELPER) {
				img = 10;
			}
			int dimg = 0;
			if (player.getDonor() == DonorRights.DONOR) {
				dimg = 5;
			}
			if (player.getDonor() == DonorRights.DELUXE_DONOR) {
				dimg = 6;
			}
			if (player.getDonor() == DonorRights.SPONSOR) {
				dimg = 7;
			}
			if (player.getDonor() == DonorRights.KING) {
				dimg = 657;
			}
			if (player.getDonor() == DonorRights.SUPER_SPONSOR) {
				dimg = 656;
			}
			if (player.getDonor() == DonorRights.VETERAN) {
				dimg = 661;
			}
			if (player.getDonor() == DonorRights.VIP) {
				dimg = 660;
			}
			if (player.getDonor() == DonorRights.DRAGON) {
				dimg = 659;
			}
			if (player.getDonor() == DonorRights.HEARTH) {
				dimg = 665;
			}
			if (player.getDonor() == DonorRights.GOLDBAG) {
				dimg = 666;
			}
			if (player.getDonor() == DonorRights.GEM) {
				dimg = 664;
			}
			/*if (player.getDonor() == DonorRights.RASTA_KING) {
				dimg = 665;
			}
			if (player.getDonor() == DonorRights.SUPER_SPONSOR) {
				dimg = 664;
			}
			if (player.getDonor() == DonorRights.VETERAN) {
				dimg = 675;
			}
			if (player.getDonor() == DonorRights.INSIDIOUS) {
				dimg = 674;
			}
			if (player.getDonor() == DonorRights.DRAGON) {
				dimg = 673;
			}*/
			if (player.getDonor() == DonorRights.HEARTH) {
				dimg = 665;
			}
			if (player.getDonor() == DonorRights.GOLDBAG) {
				dimg = 666;
			}
			if (player.getDonor() == DonorRights.GEM) {
				dimg = 664;
			}

			if (player.isModeler()) {
				dimg = 296;
			}
			if (player.isGfxDesigner()) {
				dimg = 298;
			}
			if (player.isYoutuber()) {
				dimg = 299;
			}
			if (player.isGambler()) {
				dimg = 297;
			}

			player.getPacketSender().sendString(child,
					"" + (rankId > 0 ? "<img=" + img + "><img=" + dimg + ">   " : "") + p.getUsername());
			child++;
		}
	}

	public static void showInterface(Player player) {
		resort();
		clearInterface(player);
		sendInterfaceData(player);
		int amount = World.getPlayers().size();

		player.getPacketSender().setScrollBar(57040, amount <= 11 ? 250 : amount * 35);
		player.getPacketSender().sendString(55045, "" + PLAYERS_ONLINE_LIST.size()).sendInterface(55000);
	}

	public static void updateInterface(Player player, int index) {
		if (index >= PLAYERS_ONLINE_LIST.size() || PLAYERS_ONLINE_LIST.get(index) == null) {
			showInterface(player);
			player.getPacketSender().sendMessage("That player is currently unavailable.");
			player.getPacketSender().sendConfig(1110, -1);
			return;
		}
		Player player2 = PLAYERS_ONLINE_LIST.get(index);
		player.setPlayerViewingIndex(index);
		player.getPacketSender().sendMessage("index: " + index + " - " + player2.getUsername());
		sendDetails(player, player2);
	}

	public static void updatePlayers() {
		for (Player player : World.getPlayers()) {
			if (player != null && player.getInterfaceId() == 55000) {
				showInterface(player);
			}
		}
	}
}
