package com.ruthlessps.world.content;

import com.ruthlessps.model.GroundItem;
import com.ruthlessps.model.Item;
import com.ruthlessps.model.Position;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.content.LoyaltyProgram.LoyaltyTitles;
import com.ruthlessps.world.entity.impl.GroundItemManager;
import com.ruthlessps.world.entity.impl.player.Player;

public class Achievements {

	public enum AchievementData {

		REACH_COMBAT_LVL_50(Difficulty.EASY, "Reach combat level 50", null),

		CHOP_A_TREE(Difficulty.EASY, "Chop a tree", null),

		CATCH_SOME_FISH(Difficulty.EASY, "Catch some fish", null),

		COOK_SOME_FISH(Difficulty.EASY, "Cook some fish", null),

		MINE_SOME_ORE(Difficulty.EASY, "Mine some ore", null),

		MAKE_SOME_BARS(Difficulty.EASY, "Make some bars", null),

		CRAFT_SOME_RUNES(Difficulty.EASY, "Craft some runes", null),

		BURY_SOME_BONES(Difficulty.EASY, "Bury some bones", null),

		FLETCH_SOME_ARROWS(Difficulty.EASY, "Fletch some arrows", null),

		HARVEST_A_CROP(Difficulty.EASY, "Harvest a crop", null),

		KILL_PLAYER(Difficulty.EASY, "Kill another player", null),

		CLEAN_HERB(Difficulty.EASY, "Clean a herb", null),

		STEAl_STALL(Difficulty.EASY, "Steal from a stall", null),

		CLIMB_OBSTACLE(Difficulty.EASY, "Climb an obstacle", null),

		ENTER_HOME(Difficulty.EASY, "Enter your home", null),

		MAKE_ARMOUR(Difficulty.EASY, "Make some armor", null),

		SUMMON_FAMILIAR(Difficulty.EASY, "Summon a familiar", null),

		COMPLETE_SLAYER_TASK(Difficulty.EASY, "Complete a slayer task", null),

		DUEL_PLAYER(Difficulty.EASY, "Win a duel", null),

		COMPLETE_QUEST(Difficulty.EASY, "Complete a Quest", null),

		SET_EMAIL(Difficulty.EASY, "Set an email", null),

		TELEPORT_VARROCK(Difficulty.EASY, "Teleport to varrock", null),
		
		TELEPORT_CAMELOT(Difficulty.EASY, "Teleport to camelot", null),

		DEAL_1000_MELEE_DMG(Difficulty.EASY, "Deal 1000 melee damage", new int[] { 0, 1000 }),

		DEAL_1000_RANGE_DMG(Difficulty.EASY, "Deal 1000 range damage", new int[] { 1, 1000 }),

		DEAL_1000_MAGIC_DMG(Difficulty.EASY, "Deal 1000 magic damage", new int[] { 2, 1000 }),

		KILL_25_MORTYS(Difficulty.EASY, "Kill 25 Mortys", new int[] { 3, 25 }),

		KILL_25_MEWTWOS(Difficulty.EASY, "Kill 25 MewTwos", new int[] { 4, 25 }),

		KILL_25_CHARMELEONS(Difficulty.EASY, "Kill 25 Charmeleons", new int[] { 5, 25 }),
		
		KILL_1_AMERICAN(Difficulty.EASY, "Kill an American Warrior", null),
		
		KILL_1_CLOBE(Difficulty.EASY, "Kill a Clobe Warrior", null),
		
		KILL_1_ARCHUS(Difficulty.EASY, "Kill an Archus Warrior", null),

		PLAY_THE_ZOMBIE_MINIGAME(Difficulty.EASY, "Play the zombie minigame", null),

		
		

		COMPLETE_ALL_EASY(Difficulty.MEDIUM, "Compelete all easy tasks", new int[] { 62, 33 }, new String[]{"A cape with +1000 prayer bonus"}),
		
		GET_99_PRAYER(Difficulty.MEDIUM, "Get 99 Prayer", null),

		COMPLETE_3_QUESTS(Difficulty.MEDIUM, "Complete 3 Quests", new int[] { 6, 3 }),

		KILL_10_PLAYERS(Difficulty.MEDIUM, "Kill 10 players", new int[] { 7, 10 }),

		FORGE_KEYS1(Difficulty.MEDIUM, "Forge 10 keys", new int[] { 8, 10 }),

		KILL50SKYS(Difficulty.MEDIUM, "Kill 50 Sky Torvas", new int[] { 9, 50 }),

		KILL50REDONEX(Difficulty.MEDIUM, "Kill 50 Redonex's", new int[] { 10, 50 }),

		KILL50GORGS(Difficulty.MEDIUM, "Kill 50 Gorgs", new int[] { 11, 50 }),

		DEFEATNOMAD(Difficulty.MEDIUM, "Defeat Nomad", null),

		DEFEAT_CULINAROMANCER(Difficulty.MEDIUM, "Defeat Culinaromancer", null),

		DEAL_100_000_MELEE_DMG(Difficulty.MEDIUM, "Deal 100k melee damage", new int[] { 12, 100_000 }),

		DEAL_100_000_RANGE_DMG(Difficulty.MEDIUM, "Deal 100k range damage", new int[] { 13, 100_000 }),

		DEAL_100_000_MAGIC_DMG(Difficulty.MEDIUM, "Deal 100k magic damage", new int[] { 14, 100_000 }),

		COMBAT_LVL_75(Difficulty.MEDIUM, "Reach 75 combat level", null),

		CHOP_50_TREES(Difficulty.MEDIUM, "Chop 100 trees", new int[] { 15, 100 }),

		CATCH_50_FISH(Difficulty.MEDIUM, "Catch 100 fish", new int[] { 16, 100 }),

		COOK_50_FISH(Difficulty.MEDIUM, "Cook 100 fish", new int[] { 17, 100 }),

		MINE_50_ORES(Difficulty.MEDIUM, "Mine 100 ores", new int[] { 18, 100 }),

		MAKE_50_BARS(Difficulty.MEDIUM, "Make 100 bars", new int[] { 19, 100 }),

		CRAFT_50_RUNES(Difficulty.MEDIUM, "Craft 100 runes", new int[] { 20, 100 }),

		BURY_50_BONES(Difficulty.MEDIUM, "Bury 100 bones", new int[] { 21, 100 }),

		FLETCH_50_ARROWS(Difficulty.MEDIUM, "Fletch 100 sets arrows", new int[] { 22, 100 }),

		HARVEST_50_CROPS(Difficulty.MEDIUM, "Harvest 100 crops", new int[] { 23, 100 }),

		CLEAN_50_HERBS(Difficulty.MEDIUM, "Clean 100 herbs", new int[] { 24, 100 }),

		STEAL_50_STALLS(Difficulty.MEDIUM, "Steal from 100 stalls", new int[] { 25, 100 }),

		CLIMB_50_OBSTACLES(Difficulty.MEDIUM, "Climb 100 obstacles", new int[] { 26, 100 }),

		SMITH_50_ITEMS(Difficulty.MEDIUM, "Smith 100 items", new int[] { 27, 100 }),
		
		SUMMON_25_FAMILIARS(Difficulty.MEDIUM, "Summon 25 familiars", new int[] { 28, 25 }),
		
		FOUR_GODS(Difficulty.MEDIUM, "Teleport to God Wars", null),

		COMPLETE_15_SLAYER(Difficulty.MEDIUM, "Complete 25 slayer tasks", new int[] { 29, 25 }),

		TELEPORT_APE_ATOL(Difficulty.MEDIUM, "Teleport to Ape Atoll", null),

		
		
		
		COMPLETE_ALL_MEDIUM(Difficulty.HARD, "Complete all medium tasks", new int[] { 63, 31 }, new String[] {"Double XP ring", "Access to skill point shop"}),

		COMPLETE_10_QUESTS(Difficulty.HARD, "Complete 10 Quests", new int[] { 31, 10 }),

		OPEN_CHEST(Difficulty.HARD, "Open Mystery Chest 100x", new int[] { 32, 100 }),

		FORGE_KEYS(Difficulty.HARD, "Forge 100 keys", new int[] { 33, 100 }),

		KILL_50_IKTOMIS(Difficulty.HARD, "Kill 50 Iktomis", new int[] { 34, 50 }),

		KILL_50_VORTEX(Difficulty.HARD, "Kill 50 Vortex Kings", new int[] { 35, 50 }),

		KILL_50_ZEUS(Difficulty.HARD, "Kill 50 Zeus'", new int[] { 36, 50 }),

		DEAL_10M_MELEE_DMG(Difficulty.HARD, "Deal 10,000,000 melee damage", new int[] { 37, 10_000_000 }),

		DEAL_10M_RANGE_DMG(Difficulty.HARD, "Deal 10,000,000 range damage", new int[] { 38, 10_000_000 }),

		DEAL_10M_MAGIC_DMG(Difficulty.HARD, "Deal 10,000,000 magic damage", new int[] { 39, 10_000_000 }),

		MAX_COMBAT(Difficulty.HARD, "Reach max combat level", null),

		CREATE_SLAYER_GROUP(Difficulty.HARD, "Create a Slayer Group", null),

		CHOP_500_TREES(Difficulty.HARD, "Chop 2.5k trees", new int[] { 40, 2500 }),

		CATCH_500_FISH(Difficulty.HARD, "Catch 2.5k fish", new int[] { 41, 2500 }),

		MINE_500_ORES(Difficulty.HARD, "Mine 2.5k ores", new int[] { 42, 2500 }),

		MAKE_500_BARS(Difficulty.HARD, "Make 2.5k bars", new int[] { 43, 2500 }),

		CRAFT_500_RUNES(Difficulty.HARD, "Craft 2.5k runes", new int[] { 44, 2500 }),

		BURY_500_BONES(Difficulty.HARD, "Bury 2.5k bones", new int[] { 45, 2500 }),

		FLETCH_500_ARROWS(Difficulty.HARD, "Fletch 2.5k arrows", new int[] { 46, 2500 }),

		HARVEST_500_CROPS(Difficulty.HARD, "Harvest 2.5k crops", new int[] { 47, 2500 }),

		CLEAN_500_HERBS(Difficulty.HARD, "Clean 2.5k herbs", new int[] { 48, 2500 }),

		STEAL_500_STALLS(Difficulty.HARD, "Steal 1k stalls", new int[] { 49, 1000 }),

		CLIMB_500_OBSTACLES(Difficulty.HARD, "Climb 2.5k obstacles", new int[] { 50, 2500 }),

		MAXHIT_5000(Difficulty.HARD, "Get a Melee Max hit of over 5k", null),

		SMITH_500_ITEMS(Difficulty.HARD, "Smith 1k items", new int[] { 52, 1000 }),

		SUMMON_100_FAMILIARS(Difficulty.HARD, "Summon 500 familiars", new int[] { 53, 500 }),

		COMPLETE_50_TASKS(Difficulty.HARD, "Complete 50 achievements", new int[] { 54, 50 }),

		PRESTIGE_ZONE(Difficulty.HARD, "Enter the Prestige zone", null),
		
		

		COMPLETE_ALL_HARD(Difficulty.ELITE, "Complete all hard tasks", new int[] { 64, 28 }, new String[] {"Custom Pet that banks skill loot", "Golden Minigun", "Best Range Amulet"}),

		COMPLETE_50_QUESTS(Difficulty.ELITE, "Complete 50 Quests", new int[] { 56, 50 }),

		COMPLETE_50_GROUP(Difficulty.ELITE, "Finish 50 Group Slay tasks", new int[] { 57, 50 }),

		WEAR_FULL_KM(Difficulty.ELITE, "Wear full Knightmare", null),

		KILL_10K_NPCS(Difficulty.ELITE, "Kill 10k npcs", new int[] { 59, 10_000 }),

		KILL_500_BOSSES(Difficulty.ELITE, "Kill 1k bosses", new int[] { 60, 1000 }),

		WEAR_SLAYER_HELM_i5(Difficulty.ELITE, "Wear a Slayer Helmet (i5)", null),

		MAX_SKILL(Difficulty.ELITE, "Get 99 in all skills", null),

		LOYALTY_TITLES(Difficulty.ELITE, "Unlock all loyalty titles", new int[] { 62, LoyaltyTitles.values().length }),

		COMPLETE_ALL_ELITE(Difficulty.ELITE, "Complete all Achievements", new int[] { 61, 101 }, new String[] {"Access to custom boss", "Custom Epic cape", "Custom Pet" });
		
		
		
		AchievementData(Difficulty difficulty, String interfaceLine, int[] progressData) {
			this.difficulty = difficulty;
			this.interfaceLine = interfaceLine;
			this.progressData = progressData;
		}
		AchievementData(Difficulty difficulty, String interfaceLine, int[] progressData, String[] reward) {
			this.difficulty = difficulty;
			this.interfaceLine = interfaceLine;
			this.progressData = progressData;
			this.reward = reward;
		}

		private Difficulty difficulty;
		private String interfaceLine;
		private int[] progressData;
		private String[] reward;

		public Difficulty getDifficulty() {
			return difficulty;
		}
	}

	public enum Difficulty {
		BEGINNER, EASY, MEDIUM, HARD, ELITE, TEMPORARY;
	}

	public static boolean allComplete(Player player, Difficulty diff) {
		for (AchievementData data : AchievementData.values()) {
			if (data.getDifficulty().equals(diff)) {
				if (!player.getAchievementAttributes().getCompletion()[data.ordinal()]) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean handleButton(Player player, int button) {
		if (!(button >= -28531 && button <= -28425)) {
			return false;
		}
		int index = -1;
		if (button >= -28531 && button <= -28499) {
			index = 28531 + button;
		} else if (button >= -28497 && button <= -28467) {
			index = 33 + 28497 + button;
		} else if (button >= -28465 && button <= -28438) {
			index = 64 + 28465 + button;
		} else if (button >= -28436 && button <= -28427) {
			index = 92 + 28436 + button;
		}
		if (index >= 0 && index < AchievementData.values().length) {
			AchievementData achievement = AchievementData.values()[index];

			if (achievement == AchievementData.REACH_COMBAT_LVL_50 && player.getSkillManager().getCombatLevel() < 50) {
				player.getPacketSender().sendMessage("<col=FFFF00>Your progress for this achievement is currently at: "
						+ player.getSkillManager().getCombatLevel() + "/50.");
				return true;
			}
			if (player.getAchievementAttributes().getCompletion()[achievement.ordinal()]) {
				player.getPacketSender().sendMessage(
						"<col=339900>You have completed the achievement: " + achievement.interfaceLine + ".");
			} else if (achievement.progressData == null) {
				player.getPacketSender().sendMessage(
						"<col=660000>You have not started the achievement: " + achievement.interfaceLine + ".");
				if(achievement.reward != null) {
					player.getPacketSender().sendMessage("<img=10><col=660000>Rewards: "+achievement.reward);
				}
			} else {
				int progress = player.getAchievementAttributes().getProgress()[achievement.progressData[0]];
				int requiredProgress = achievement.progressData[1];
				if (progress == 0) {
					player.getPacketSender().sendMessage(
							"<col=660000>You have not started the achievement: " + achievement.interfaceLine + ".");
				} else if (progress != requiredProgress) {
					player.getPacketSender()
							.sendMessage("<col=FFFF00>Your progress for this achievement is currently at: "
									+ Misc.insertCommasToNumber("" + progress) + "/"
									+ Misc.insertCommasToNumber("" + requiredProgress) + ".");
					for(int i = 0; i<achievement.reward.length; i++) {
						player.getPacketSender().sendMessage("<img=10><col=660000>Reward: "+achievement.reward[i]);
					}
				}
			}
		}
		return true;
	}

	public static void updateInterface(Player player) {

		int easy = 37005;
		int med = 37039;
		int hard = 37071;
		int elite = 37100;
		for (AchievementData achievement : AchievementData.values()) {
			boolean completed = player.getAchievementAttributes().getCompletion()[achievement.ordinal()];
			boolean progress = achievement.progressData != null
					&& player.getAchievementAttributes().getProgress()[achievement.progressData[0]] > 0;
			switch (achievement.difficulty) {
			case EASY:
				player.getPacketSender().sendString(easy,
						(completed ? "@gre@<img=11> " : progress ? "@yel@" : "@red@") + achievement.interfaceLine);
				easy++;
				break;
			case MEDIUM:
				player.getPacketSender().sendString(med,
						(completed ? "@gre@<img=11> " : progress ? "@yel@" : "@red@") + achievement.interfaceLine);
				med++;
				break;
			case HARD:
				player.getPacketSender().sendString(hard,
						(completed ? "@gre@<img=11> " : progress ? "@yel@" : "@red@") + achievement.interfaceLine);
				hard++;
				break;
			case ELITE:
				player.getPacketSender().sendString(elite,
						(completed ? "@gre@<img=11> " : progress ? "@yel@" : "@red@") + achievement.interfaceLine);
				elite++;
				break;
			}
		}
		player.getPacketSender().sendString(37001, "Achievements: " + player.getPointsManager().getPoints("achievement")
				+ "/" + AchievementData.values().length);
	}

	public static void setPoints(Player player) {
		int points = 0;
		for (AchievementData achievement : AchievementData.values()) {
			if (player.getAchievementAttributes().getCompletion()[achievement.ordinal()]) {
				points++;
			}
		}
		player.getPointsManager().setPoints("achievement", points);
	}

	public static void doProgress(Player player, AchievementData achievement) {
		doProgress(player, achievement, 1);
	}

	public static void doProgress(Player player, AchievementData achievement, int amt) {
		if (player.getAchievementAttributes().getCompletion()[achievement.ordinal()])
			return;
		if (achievement.progressData != null) {
			int progressIndex = achievement.progressData[0];
			int amountNeeded = achievement.progressData[1];
			player.getAchievementAttributes().getProgress()[progressIndex] += amt;
			if (player.getAchievementAttributes().getProgress()[progressIndex] >= amountNeeded) {
				finishAchievement(player, achievement);
			}
			updateInterface(player);
		} else {
			finishAchievement(player, achievement);
		}
	}

	public static void finishAchievement(Player player, AchievementData achievement) {
		if (player.getAchievementAttributes().getCompletion()[achievement.ordinal()])
			return;
		player.getAchievementAttributes().getCompletion()[achievement.ordinal()] = true;
		player.getPacketSender()
				.sendMessage("<col=339900>You have completed the achievement "
						+ Misc.formatText(achievement.interfaceLine.toString().toLowerCase() + "."))
				.sendString(37001, "Achievements: " + player.getPointsManager().getPoints("achievement") + "/"
						+ AchievementData.values().length);
		if(achievement.equals(AchievementData.COMPLETE_ALL_EASY)) {
			player.getPacketSender().sendMessage("<img=10><col=0055FF><shad=0>You are rewarded a Tier 1 Achievement Cape for completing all Easy achievements!");
			if (player.getInventory().getFreeSlots() >= 1) {
				player.getInventory().add(new Item(4767, 1));
			} else {
				GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(4767, 1), new Position(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ()), player.getUsername(), false, 0, false, 0));
				player.getPacketSender()
						.sendMessage("Your reward for completing the achievement has been placed on the floor.");
			}
		} else if(achievement.equals(AchievementData.COMPLETE_ALL_MEDIUM)) {
			if (player.getInventory().getFreeSlots() >= 1) {
				player.getInventory().add(new Item(18405, 1));
			} else {
				GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(18405, 1), new Position(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ()), player.getUsername(), false, 0, false, 0));
				player.getPacketSender()
						.sendMessage("Your reward for completing the achievement has been placed on the floor.");
			}
		} else if(achievement.equals(AchievementData.COMPLETE_ALL_HARD)) {
			if (player.getInventory().getFreeSlots() >= 2) {
				player.getInventory().add(new Item(9404, 1));
				player.getInventory().add(new Item(20202, 1));
			} else {
				GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(9404, 1), new Position(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ()), player.getUsername(), false, 0, false, 0));
				GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(20202, 1), new Position(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ()), player.getUsername(), false, 0, false, 0));
				player.getPacketSender()
						.sendMessage("Your reward for completing the achievement has been placed on the floor.");
			}
		} else if(achievement.equals(AchievementData.COMPLETE_ALL_ELITE)) {
			if (player.getInventory().getFreeSlots() >= 1) {
				player.getInventory().add(new Item(13664, 2000));
			} else {
				GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(3626, 1), new Position(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ()), player.getUsername(), false, 0, false, 0));
				player.getPacketSender()
						.sendMessage("Your reward for completing the achievement has been placed on the floor.");
			}
		}
		switch (achievement.difficulty) {
		case EASY:
			if (player.getInventory().getFreeSlots() >= 1) {
				player.getInventory().add(new Item(13664, 10));
			} else {
				GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(13664, 10), new Position(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ()), player.getUsername(), false, 0, false, 0));
				player.getPacketSender()
						.sendMessage("Your reward for completing the achievement has been placed on the floor.");
			}
			Achievements.doProgress(player, AchievementData.COMPLETE_ALL_EASY);
			Achievements.doProgress(player, AchievementData.COMPLETE_ALL_ELITE);
			break;
		case MEDIUM:
			if (player.getInventory().getFreeSlots() > 1) {
				player.getInventory().add(new Item(13664, 25));
			} else {
				GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(13664, 25), player.getPosition(), player.getUsername(), false, 0, false, 0));
				player.getPacketSender()
						.sendMessage("Your reward for completing the achievement has been placed on the floor.");
			}
			Achievements.doProgress(player, AchievementData.COMPLETE_ALL_MEDIUM);
			Achievements.doProgress(player, AchievementData.COMPLETE_ALL_ELITE);
			break;
		case HARD:
			if (player.getInventory().getFreeSlots() > 0) {
				player.getInventory().add(new Item(13664, 250));
			} else {
				GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(13664, 250), player.getPosition(), player.getUsername(), false, 0, false, 0));
				player.getPacketSender()
						.sendMessage("Your reward for completing the achievement has been placed on the floor.");
			}
			Achievements.doProgress(player, AchievementData.COMPLETE_ALL_HARD);
			Achievements.doProgress(player, AchievementData.COMPLETE_ALL_ELITE);
			break;
		case ELITE:
			if (player.getInventory().getFreeSlots() > 0) {
				player.getInventory().add(new Item(13664, 2000));
			} else {
				GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(13664, 2000), player.getPosition(), player.getUsername(), false, 0, false, 0));
				player.getPacketSender()
						.sendMessage("Your reward for completing the achievement has been placed on the floor.");
			}
			Achievements.doProgress(player, AchievementData.COMPLETE_ALL_ELITE);
			break;
		}

		boolean completed = true;
		for (int i = 0; i < player.getAchievementAttributes().getCompletion().length; i++) {
			if (!player.getAchievementAttributes().getCompletion()[i]) {
				completed = false;
			}
		}
		Achievements.doProgress(player, AchievementData.COMPLETE_50_TASKS);
		if (completed) {
			player.getBank(0).add(13654, 1);
		}
		updateInterface(player);
		player.getPointsManager().setWithIncrease("achievement", 1);
	}

	public static class AchievementAttributes {

		public AchievementAttributes() {
		}

		/** ACHIEVEMENTS **/
		private boolean[] completed = new boolean[AchievementData.values().length];
		private int[] progress = new int[100];

		public boolean[] getCompletion() {
			return completed;
		}

		public void setCompletion(int index, boolean value) {
			this.completed[index] = value;
		}

		public void setCompletion(boolean[] completed) {
			this.completed = completed;
		}

		public int[] getProgress() {
			return progress;
		}

		public void setProgress(int index, int value) {
			this.progress[index] = value;
		}

		public void setProgress(int[] progress) {
			this.progress = progress;
		}

		/** MISC **/
		private int coinsGambled;
		private double totalLoyaltyPointsEarned;
		private boolean[] godsKilled = new boolean[5];

		public int getCoinsGambled() {
			return coinsGambled;
		}

		public void setCoinsGambled(int coinsGambled) {
			this.coinsGambled = coinsGambled;
		}

		public double getTotalLoyaltyPointsEarned() {
			return totalLoyaltyPointsEarned;
		}

		public void incrementTotalLoyaltyPointsEarned(double totalLoyaltyPointsEarned) {
			this.totalLoyaltyPointsEarned += totalLoyaltyPointsEarned;
		}

		public boolean[] getGodsKilled() {
			return godsKilled;
		}

		public void setGodKilled(int index, boolean godKilled) {
			this.godsKilled[index] = godKilled;
		}

		public void setGodsKilled(boolean[] b) {
			this.godsKilled = b;
		}
	}
}
