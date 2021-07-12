package com.ruthlessps.world.content.mbox.impl;

import java.util.ArrayList;

import com.ruthlessps.model.Item;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.content.mbox.MysteryBoxRewards;
import com.ruthlessps.world.content.mbox.MysteryBoxes;
import com.ruthlessps.world.content.mbox.Rarity;
import com.ruthlessps.world.entity.impl.player.Player;

public class TorvaBox extends MysteryBoxes {

	@Override
	public int getBox() {
		return 2801;
	}

	@Override
	public String getName() {
		return "Torva mystery box";
	}

	@Override
	public ArrayList<MysteryBoxRewards> getRewards() {
		return Rewards.getRewards();
	}
	
	@Override
	public ArrayList<Item> getArray(Player player) {
		return player.torvaBoxRewards;
	}

	public enum Rewards {
		AMERICAN(new Item(14558), Rarity.COMMON),
		AMERICAN1(new Item(20252), Rarity.COMMON),
		AMERICAN2(new Item(20253), Rarity.COMMON),
		AMERICAN3(new Item(20251), Rarity.COMMON),
		AMERICAN4(new Item(14556), Rarity.COMMON),
		AMERICAN5(new Item(14557), Rarity.COMMON),
		AMERICAN6(new Item(20250), Rarity.COMMON),
		OREO(new Item(14565), Rarity.COMMON),
		OREO1(new Item(14561), Rarity.COMMON),
		OREO2(new Item(14562), Rarity.COMMON),
		OREO3(new Item(14560), Rarity.COMMON),
		OREO4(new Item(14563), Rarity.COMMON),
		OREO5(new Item(14564), Rarity.COMMON),
		OREO6(new Item(14559), Rarity.COMMON),
		SKY(new Item(14572), Rarity.UNCOMMON),
		SKY1(new Item(14568), Rarity.UNCOMMON),
		SKY2(new Item(14569), Rarity.UNCOMMON),
		SKY3(new Item(14567), Rarity.UNCOMMON),
		SKY4(new Item(14570), Rarity.UNCOMMON),
		SKY5(new Item(14571), Rarity.UNCOMMON),
		SKY6(new Item(14566), Rarity.UNCOMMON),
		
		DARTH(new Item(14586), Rarity.UNCOMMON),
		DARTH1(new Item(14582), Rarity.UNCOMMON),
		DARTH2(new Item(14583), Rarity.UNCOMMON),
		DARTH3(new Item(14581), Rarity.UNCOMMON),
		DARTH4(new Item(14584), Rarity.UNCOMMON),
		DARTH5(new Item(14585), Rarity.UNCOMMON),
		DARTH6(new Item(20690), Rarity.UNCOMMON),
		CASH(new Item(80), Rarity.RARE),
		CASH1(new Item(3314), Rarity.RARE),
		CASH12(new Item(14590), Rarity.RARE),
		CASH13(new Item(14588), Rarity.RARE),
		CASH14(new Item(14591), Rarity.RARE),
		CASH15(new Item(79), Rarity.RARE),
		CASH16(new Item(14587), Rarity.RARE),
		SILVER(new Item(18742), Rarity.RARE),
		SILVER1(new Item(14596), Rarity.RARE),
		SILVER2(new Item(14597), Rarity.RARE),
		SILVER3(new Item(14619), Rarity.RARE),
		SILVER4(new Item(14598), Rarity.RARE),
		SILVER5(new Item(14599), Rarity.RARE),
		SILVER6(new Item(81), Rarity.RARE),
		
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
