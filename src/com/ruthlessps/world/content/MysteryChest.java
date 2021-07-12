package com.ruthlessps.world.content;

import static com.ruthlessps.model.ItemRarity.COMMON;
import static com.ruthlessps.model.ItemRarity.LEGENDARY;
import static com.ruthlessps.model.ItemRarity.RARE;
import static com.ruthlessps.model.ItemRarity.UNCOMMON;
import static com.ruthlessps.model.ItemRarity.VERY_RARE;

import java.util.*;
import java.util.stream.Collectors;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.ruthlessps.model.Animation;
import com.ruthlessps.model.Item;
import com.ruthlessps.model.ItemRarity;
import com.ruthlessps.model.Position;
import com.ruthlessps.model.definitions.ItemDefinition;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.content.Achievements.AchievementData;
import com.ruthlessps.world.entity.impl.player.Player;

// Author: Chris AKA Hacker.

public class MysteryChest {

	public static int[][] americanRewards = {{13664, 5, 1},{14049, 1, 100}, {14047, 1, 100}, {14048, 1, 100}, {14050, 1, 100}, {14051, 1, 100}, {3078, 1, 100}};
	public static int[][] oreoRewards = {{13664, 10, 1},{3953, 1, 120}, {3955, 1, 120}, {10858, 1, 120}, {2623, 1, 120}, {2617, 1, 12}, {7020, 1, 120}, {4706, 1, 120}, {19957, 1, 120}};
	public static int[][] skyRewards = {{13664, 15, 1},{10071, 1, 140}, {3071, 1, 140}, {3244, 1, 140}, {4202, 1, 140}, {3079, 1, 140}};
	public static int[][] darthRewards = {{13664, 25, 1},{16600, 1, 160}, {3904, 1, 160}, {4744, 1, 160}, {3084, 1, 160}};
	public static int[][] cashRewards = {{13664, 50, 1}};
	public static int[][] silverRewards = {{13664, 100, 1}};

	public static int[][] clobeRewards = {{13664, 7, 1}};
	public static int[][] prostexRewards = {{13664, 12, 1}};
	public static int[][] redonexRewards = {{13664, 20, 1}};
	public static int[][] legionRewards = {{13664, 30, 1}};
	public static int[][] zarthyxRewards = {{13664, 60, 1}};
	public static int[][] rucordRewards = {{13664, 110, 1}};

	public static int[][] archusRewards = {{13664, 7, 1}};
	public static int[][] razielRewards = {{13664, 12, 1}};
	public static int[][] gorgRewards = {{13664, 20, 1}};
	public static int[][] harnanRewards = {{13664, 30, 1}};
	public static int[][] landazarRewards = {{13664, 60, 1}};
	public static int[][] xintorRewards = {{13664, 110, 1}};
	private static final int KEY = 989;

	/**
	 * Handles the action of clicking the chest.
	 * 
	 * @param player
	 *            The player in this action.
	 */
	public static void handleChest(Player player) {
		if (player.getInventory().getAmount(KEY)>=100 ) {

			double numGen = Math.random();

			ItemRarity rarity = numGen >= 0.25 ? ItemRarity.COMMON
					: numGen >= 0.15 ? ItemRarity.UNCOMMON
							: numGen >= 0.04 ? ItemRarity.RARE
									: numGen >= 0.02 ? ItemRarity.VERY_RARE : ItemRarity.LEGENDARY;

			Optional<MysteryChestReward> reward = MysteryChestReward.get(rarity);
			if (reward.isPresent()) {
				player.performAnimation(new Animation(827));
				player.getInventory().delete(KEY, 100);
				player.getPacketSender().sendMessage("You open the chest..");
				player.getInventory().add(reward.get().getItemId(), reward.get().getAmount()).refreshItems();
				Achievements.doProgress(player, AchievementData.OPEN_CHEST);
			} else {
				player.sendMessage("Critical error: Report to staff");
			}
		} else {
			// if no key:
			player.sendMessage("You need at least 100 mystery keys to open chest!");
		}
	}

	public static void handleChest(Player player, int[][] rewards, int keyId) {
		if(!player.getInventory().contains(keyId)) {
			player.getPA().sendMessage("You need a key to open this chest.");
			return;
		}
		if(player.getInventory().isFull()) {
			player.getPA().sendMessage("You need at least 1 free inventory space to open this chest.");
			return;
		}
		List<int[]> possibleLoot = new ArrayList<>();
		for(int[] reward : rewards) {
			if(Misc.random(reward[2]) == 0) {
				possibleLoot.add(reward);
			}
		}
		if (!possibleLoot.isEmpty()) {
			int[] reward = possibleLoot.get(Misc.random(possibleLoot.size() - 1));
			Item rewardItem = new Item(reward[0], reward[1]);
			player.getInventory().delete(keyId, 1);
			player.getInventory().add(rewardItem);
			player.getPA().sendMessage("You received "+ reward[1]+"x " + ItemDefinition.forId(reward[0]).getName());
		}


	}

	public static void clearInterface(Player player) {
		for(int i = 24100; i < 24191; i++) {
			player.getPA().sendItemOnInterface(i, 0, 1);
		}
	}

	public static void showInterface(Player player, int objectId, Position pos) {
		int[][] items = new int[][] {};
		clearInterface(player);
		player.getPA().sendInterface(31000);
		if(pos.getX() == 3174 && pos.getY() == 3028) {
			items = americanRewards;
		} else if(pos.getX() == 3168 && pos.getY() == 3033) {
			items = oreoRewards;
		} else if(pos.getX() == 1888 && pos.getY() == 4820) {
			items = skyRewards;
		} else if(pos.getX() == 1894 && pos.getY() == 4825) {
			items = darthRewards;
		} else if(pos.getX() == 1952 && pos.getY() == 4825) {
			items = cashRewards;
		} else if(pos.getX() == 1958 && pos.getY() == 4825) {
			items = silverRewards;
		} else if(pos.getX() == 2016 && pos.getY() == 4824) {
			items = clobeRewards;
		} else if(pos.getX() == 2022 && pos.getY() == 4822) {
			items = prostexRewards;
		} else if(pos.getX() == 2592 && pos.getY() == 4629) {
			items = redonexRewards;
		} else if(pos.getX() == 2598 && pos.getY() == 4630) {
			items = legionRewards;
		} else if(pos.getX() == 2016 && pos.getY() == 4501) {
			items = zarthyxRewards;
		} else if(pos.getX() == 2022 && pos.getY() == 4505) {
			items = rucordRewards;
		} else if(pos.getX() == 2784 && pos.getY() == 3861) {
			items = archusRewards;
		} else if(pos.getX() == 2790 && pos.getY() == 3862) {
			items = razielRewards;
		} else if(pos.getX() == 2272 && pos.getY() == 5015) {
			items = gorgRewards;
		} else if(pos.getX() == 2278 && pos.getY() == 5014) {
			items = harnanRewards;
		} else if(pos.getX() == 2400 && pos.getY() == 5015) {
			items = landazarRewards;
		} else if(pos.getX() == 2406 && pos.getY() == 5017) {
			items = xintorRewards;
		}
		int id = 24100;
		for(int[] item : items) {
			player.getPA().sendItemOnInterface(id, item[0], item[1]);
			id++;
		}

	}

	/**
	 * Reward Table.
	 */
	public enum MysteryChestReward {
		/*
		 * COMMON
		 */
		BUCKS_1(13664, 1, COMMON),
		BUCKS_2(13664, 3, COMMON),
		BUCKS_5(13664, 5, COMMON),
		BUCKS_10(13664, 7, COMMON),
		BUCKS_15(13664, 9, COMMON),
		BUCKS_30(13664, 11, COMMON),
		BUCKS_40(13664, 13, COMMON),
		BUCKS_50(13664, 15, COMMON),
		BUCKS_70(13664, 17, COMMON),
		BUCKS_90(13664, 19, COMMON),
		BUCKS_125(13664, 21, COMMON),
		AMERICAN(14558, COMMON),
		AMERICAN1(20252, COMMON),
		AMERICAN2(20253, COMMON),
		AMERICAN3(20251, COMMON),
		AMERICAN4(14556, COMMON),
		AMERICAN5(14557, COMMON),
		AMERICAN6(20250, COMMON),
		OREO(14565, COMMON),
		OREO1(14561, COMMON),
		OREO2(14562, COMMON),
		OREO3(14560, COMMON),
		OREO4(14563, COMMON),
		OREO5(14564, COMMON),
		OREO6(14559, COMMON),
		SKY(14572, COMMON),
		SKY1(14568, COMMON),
		SKY2(14569, COMMON),
		SKY3(14567, COMMON),
		SKY4(14570, COMMON),
		SKY5(14571, COMMON),
		SKY6(14566, COMMON),
		CLOBE(4454, COMMON),
		CLOBE1(4453, COMMON),
		CLOBE2(4452, COMMON),
		CLOBE3(4451, COMMON),
		CLOBE4(4450, COMMON),
		PROSTEX(4557, COMMON),
		PROSTEX1(4556, COMMON),
		PROSTEX2(4555, COMMON),
		PROSTEX3(4554, COMMON),
		PROSTEX4(4553, COMMON),
		REDONEX(4003, COMMON),
		REDONEX1(4002, COMMON),
		REDONEX2(4001, COMMON),
		REDONEX3(4000, COMMON),
		REDONEX4(3999, COMMON),
		ARCHUS(7619, COMMON),
		ARCHUSN1(7152, COMMON),
		ARCHUSN2(7046, COMMON),
		ARCHUSN3(7040, COMMON),
		ARCHUSAN4(20462, COMMON),
		ARCHUSAN5(20460, COMMON),
		ARCHUSN6(20458, COMMON),
		RAZIEL(7047, COMMON),
		RAZIEL1(7041, COMMON),
		RAZIEL2(20300, COMMON),
		RAZIEL3(20528, COMMON),
		RAZIEL4(20526, COMMON),
		RAZIEL5(7621, COMMON),
		RAZIEL6(7153, COMMON),
		GORG(7042, COMMON),
		SGORG1(19730, COMMON),
		GORG2(20836, COMMON),
		GORGY3(20832, COMMON),
		GORG4(20838, COMMON),
		GORG5(20840, COMMON),
		GORG6(20834, COMMON),
		
		BUCKS_71(13664, 25, UNCOMMON),
		BUCKS_91(13664, 30, UNCOMMON),
		BUCKS_121(13664, 35, UNCOMMON),
		DARTH(14586, UNCOMMON),
		DARTH1(14582, UNCOMMON),
		DARTH2(14583, UNCOMMON),
		DARTH3(14581, UNCOMMON),
		DARTH4(14584, UNCOMMON),
		DARTH5(14585, UNCOMMON),
		DARTH6(20690, UNCOMMON),
		CASH(80, UNCOMMON),
		CASH1(3314, UNCOMMON),
		CASH12(14590, UNCOMMON),
		CASH13(14588, UNCOMMON),
		CASH14(14591, UNCOMMON),
		CASH15(79, UNCOMMON),
		CASH16(14587, UNCOMMON),
		SILVER(18742, UNCOMMON),
		SILVER1(14596, UNCOMMON),
		SILVER2(14597, UNCOMMON),
		SILVER3(14619, UNCOMMON),
		SILVER4(14598, UNCOMMON),
		SILVER5(14599, UNCOMMON),
		SILVER6(81, UNCOMMON),
		LEGION(934, UNCOMMON),
		LEGION1(935, UNCOMMON),
		LEGION2(937, UNCOMMON),
		LEGION33(940, UNCOMMON),
		LEGION4(2884, UNCOMMON),
		ZARTHYX(14453, UNCOMMON),
		ZARTHYX1(933, UNCOMMON),
		ZARTHYXX2(938, UNCOMMON),
		ZARTHYXR3(979, UNCOMMON),
		ZARTHYXRX4(3246, UNCOMMON),
		RUCORD(932, UNCOMMON),
		RUCORD1(936, UNCOMMON),
		RUCORDX2(939, UNCOMMON),
		RUCORDX3(996, UNCOMMON),
		RUCORDX4(3248, UNCOMMON),
		HARNAN(7043, UNCOMMON),
		HARNANN1(7048, UNCOMMON),
		HARNANN2(7155, UNCOMMON),
		HARNANN3(7670, UNCOMMON),
		HARNANN4(20732, UNCOMMON),
		HARNANAN5(20733, UNCOMMON),
		HARNAN6(20786, UNCOMMON),
		LANDAZAR(20301, UNCOMMON),
		LANDAZARL1(20302, UNCOMMON),
		LANDAZAREL2(20310, UNCOMMON),
		LANDAZARL3(7044, UNCOMMON),
		LANDAZARL4(7049, UNCOMMON),
		LANDAZARL5(7154, UNCOMMON),
		LANDAZAR6(7672, UNCOMMON),
		XINTOR(20924, UNCOMMON),
		XINTOR1(20934, UNCOMMON),
		XINTOR2(20932, UNCOMMON),
		XINTOR3(20922, UNCOMMON),
		XINTOR4(20926, UNCOMMON),
		XINTOR5(7045, UNCOMMON),
		XINTOR6(7151, UNCOMMON),
		
		BUCKS_72(13664, 45, RARE),
		BUCKS_92(13664, 65, RARE),
		BUCKS_122(13664, 85, RARE),
		CAMO(3625, RARE),
		CAMO1(3621, RARE),
		CAMO2(3622, RARE),
		CAMO3(3620, RARE),
		CAMO4(3623, RARE),
		CAMO5(3624, RARE),
		CAMO6(3619, RARE),
		WINTER(618, RARE),
		WINTER1(623, RARE),
		WINTER2(621, RARE),
		WINTER3(624, RARE),
		WINTER4(620, RARE),
		WINTER5(619, RARE),
		WINTER6(625, RARE),
		BLOODSHOT(673, RARE),
		BLOODSHOT1(669, RARE),
		BLOODSHOT2(670, RARE),
		BLOODSHOT3(666, RARE),
		BLOODSHOT4(671, RARE),
		BLOODSHOT5(672, RARE),
		BLOODSHOT6(665, RARE),
		VORTEX(3272, RARE),
		VORTEXT1(3273, RARE),
		VORTEXT2(3274, RARE),
		VORTEX2(3275, RARE),
		VORTEX4(3276, RARE),
		VORTEXT5(3271, RARE),
		
		BUCKS_73(13664, 100, VERY_RARE),
		BUCKS_93(13664, 150, VERY_RARE),
		BUCKS_123(13664, 200, VERY_RARE),
		
		BUCKS_723(13664, 400, LEGENDARY),
		BUCKS_923(13664, 600, LEGENDARY),
		BUCKS_1223(13664, 800, LEGENDARY),
;

		
		private static final ImmutableSet<MysteryChestReward> REWARDS = Sets
				.immutableEnumSet(EnumSet.allOf(MysteryChestReward.class));
		private final int itemId;
		private final int amount;
		private final ItemRarity rarity;

		MysteryChestReward(int itemId, ItemRarity rarity) {
			this.itemId = itemId;
			this.amount = 1;
			this.rarity = rarity;
		}

		MysteryChestReward(int itemId, int amount, ItemRarity rarity) {
			this.itemId = itemId;
			this.amount = amount;
			this.rarity = rarity;
		}

		public static Optional<MysteryChestReward> get(ItemRarity rarity) {
			long count = REWARDS.stream().filter(reward -> reward.getRarity() == rarity).count();
			int randomIndex = Misc.RANDOM.nextInt((int) ((count - 0))); // count
			return Optional.of(REWARDS.stream().filter(reward -> reward.getRarity() == rarity)
					.collect(Collectors.toList()).get(randomIndex));
		}

		public int getItemId() {
			return itemId;
		}

		public int getAmount() {
			return amount;
		}

		public ItemRarity getRarity() {
			return rarity;
		}
	}
}