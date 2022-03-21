package com.ruthlessps.world.content;

import java.util.HashMap;

import com.ruthlessps.GameSettings;
import com.ruthlessps.world.entity.impl.player.Player;

public class PointsManager {

	private Player player;

	public static final String[] POINTS = { "pvp", "DragonTale", "prestige", "loyalty", "voting", "slayer", "dung",
			"donation", "pc", "achievement", "skill", "imbue" };

	public PointsManager(Player player) {
		this.player = player;
	}

	private HashMap<String, Integer> points = new HashMap<String, Integer>();

	public void refreshPanel() {
		final int[] total = {0};
		player.getDropKillCount().forEach((k, v) -> total[0] += v);
		String tril = "";
		if(player.getNpcDamageT() > 0) {
			tril = Integer.toString(player.getNpcDamageT())+"@gre@T@whi@ ";
		}
		// TODO player.getPacketSender().sendString(39174, "@red@Donation Points: @bla@"
		// + getPoints("donation"));
		player.getPacketSender().sendString(39174, "@red@" + GameSettings.SERVER_NAME + " Points: @whi@" + getPoints(GameSettings.SERVER_NAME));
		player.getPacketSender().sendString(39175, "@red@Prestige Points: @whi@" + getPoints("prestige"));
		player.getPacketSender().sendString(39176, "@red@Loyalty Points: @whi@" + getPoints("loyalty"));
		player.getPacketSender().sendString(39177, "@red@Total Prestiged times: @whi@ " + player.getPrestigeAmount());
		player.getPacketSender().sendString(39178, "@red@Voting Points: @whi@ " + getPoints("voting"));
		player.getPacketSender().sendString(39179, "@red@Slayer Points: @whi@" + getPoints("slayer"));
		player.getPacketSender().sendString(39180, "@red@Skill points: @whi@" + getPoints("skill"));
		player.getPacketSender().sendString(39181, "@red@Total NPC Kills: @whi@" + total[0]);
		player.getPacketSender().sendString(39182, "@red@Quests completed: @whi@" + player.getQuestsDone());
		player.getPacketSender().sendString(39183, "@red@Damage Dealt: @whi@" +tril+player.getDmg());
		player.getPacketSender().sendString(39184, "@red@RESERVED");
		player.getPacketSender().sendString(39185, "@red@Arena Losses: @whi@" + player.getDueling().arenaStats[1]);
	}

	public double getModifier() {
		//TODO pets modifier
		double more = 1.0;
		switch (player.getDonor()) {
		case DELUXE_DONOR:
			more = 1.5;
			break;
		case DONOR:
			more = 1.25;
			break;
		case SPONSOR:
			more = 1.75;
			break;
		case SUPER_SPONSOR:
		case KING:
		case VIP:
		case DRAGON:
		case HEARTH:
		case GOLDBAG:
		case GEM:
		case VETERAN:
			more = 2;
			break;
		default:
			break;
		}
		switch (player.getGameMode()) {
		case HARDCORE:
			more += 0.40;
			break;
		case IRONMAN:
			more += 0.25;
			break;
		default:
			break;

		}
		if(PointsWell.isActive()) {
			more += 0.5;
		}
		more += player.pointsMultiplier;
		return more;
	}

	public void create(String key) {
		points.put(key, 0);
	}

	public int getPoints(String key) {
		return points.get(key) == null ? 0 : points.get(key);
	}

	public void setPoints(String key, int value) {
		if (points.get(key) == null) {
			create(key);
		}
		points.put(key, value);
	}

	public void setWithIncrease(String key, int value) {
		if (points.get(key) == null) {
			create(key);
		}
		if (getModifier() > 1.0 && (!key.equals("donation"))) {
			value *= getModifier();
		}
		points.put(key, points.get(key) + value);
	}

	public boolean decreasePoints(String key) {
		if (points.get(key) == null) {
			create(key);
		}
		if (points.get(key) < 1) {
			return false;
		}
		setPoints(key, points.get(key) - 1);
		return true;
	}
	public boolean increasePoints(String key) {
		if (points.get(key) == null) {
			create(key);
		}
		if (points.get(key) < 0) {
			return false;
		}
		setPoints(key, points.get(key) + 1);
		return true;
	}
	public void increasePoints(String key, int amount) {
		for (int i = 1; i <= amount; i++) {
			if (!increasePoints(key)) {
				break;
			}
		}
	}

	public void decreasePoints(String key, int amount) {
		for (int i = 1; i <= amount; i++) {
			if (!decreasePoints(key)) {
				break;
			}
		}
	}

	public void clearPoints(String[] keys) {
		for (String key : keys) {
			points.remove(key);
		}
	}

	public void clearAll() {
		clearPoints(POINTS);
	}
}