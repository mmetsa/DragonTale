package com.ruthlessps.world.content.mbox.impl;

import java.util.ArrayList;

import com.ruthlessps.model.Item;
import com.ruthlessps.world.content.mbox.MysteryBoxRewards;
import com.ruthlessps.world.content.mbox.MysteryBoxes;
import com.ruthlessps.world.content.mbox.Rarity;
import com.ruthlessps.world.entity.impl.player.Player;

public class RegularBox extends MysteryBoxes {

	@Override
	public int getBox() {
		return 6199;
	}

	@Override
	public String getName() {
		return "Mystery box";
	}
	
	@Override
	public ArrayList<Item> getArray(Player player) {
		return player.regularBoxRewards;
	}

	@Override
	public ArrayList<MysteryBoxRewards> getRewards() {
		return Rewards.getRewards();
	}

	public enum Rewards {
		POT1(new Item(2443, 3), Rarity.COMMON),
		POT2(new Item(2437, 3), Rarity.COMMON),
		POT3(new Item(2441, 3), Rarity.COMMON),
		POT4(new Item(2435, 3), Rarity.COMMON),
		POT5(new Item(3025, 3), Rarity.COMMON),
		COINS(new Item(13664, 1), Rarity.COMMON),
		COINS1(new Item(13664, 2), Rarity.COMMON),
		COINS2(new Item(13664, 3), Rarity.COMMON),
		COINS3(new Item(13664, 4), Rarity.COMMON),
		COINS4(new Item(13664, 5), Rarity.COMMON),
		FRAGS(new Item(14639, 1000), Rarity.COMMON),
		FRAGS1(new Item(14639, 2000), Rarity.COMMON),
		COINS5(new Item(13664, 10), Rarity.UNCOMMON),
		COINS6(new Item(13664, 15), Rarity.RARE),
		COINS7(new Item(13664, 20), Rarity.RARE),
		
		COINS8(new Item(13664, 25), Rarity.VERY_RARE),
		HARNAN(new Item(20973), Rarity.VERY_RARE),
		HARNAN1(new Item(20969), Rarity.VERY_RARE),
		HARNAN2(new Item(21058), Rarity.VERY_RARE),
		HARNAN3(new Item(20971), Rarity.VERY_RARE),
		HARNAN4(new Item(21057), Rarity.VERY_RARE),
		BOX(new Item(2800), Rarity.VERY_RARE),
		BOX1(new Item(2801), Rarity.VERY_RARE),
		BOX3(new Item(4032), Rarity.VERY_RARE),
		WHIP1(new Item(895), Rarity.VERY_RARE),
		WHIP2(new Item(82), Rarity.VERY_RARE),
		WHIP3(new Item(894), Rarity.VERY_RARE),
		
		;
		
		public Item reward;
		public Rarity rarity;
		
		Rewards(Item reward, Rarity rarity) {
			this.reward = reward;
			this.rarity = rarity;
		}
		
		static ArrayList<MysteryBoxRewards> getRewards() {
			ArrayList<MysteryBoxRewards> rewards = new ArrayList<MysteryBoxRewards>();
			for(Rewards reward : Rewards.values())
				rewards.add(new MysteryBoxRewards(reward.reward, reward.rarity));
			return rewards;
		}
	}

}
