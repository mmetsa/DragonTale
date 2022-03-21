package com.ruthlessps.world.content.mbox.impl;

import java.util.ArrayList;

import com.ruthlessps.model.Item;
import com.ruthlessps.world.content.mbox.MysteryBoxRewards;
import com.ruthlessps.world.content.mbox.MysteryBoxes;
import com.ruthlessps.world.content.mbox.Rarity;
import com.ruthlessps.world.entity.impl.player.Player;

public class OmegaBox extends MysteryBoxes {

	@Override
	public int getBox() {
		return 916;
	}

	@Override
	public String getName() {
		return "Omega PVM Box";
	}
	
	@Override
	public ArrayList<Item> getArray(Player player) {
		return player.omegaBoxRewards;
	}

	@Override
	public ArrayList<MysteryBoxRewards> getRewards() {
		return Rewards.getRewards();
	}

	public enum Rewards {
		
		RAINBOW1(new Item(3626), Rarity.COMMON),
		RAINBOW2(new Item(3627), Rarity.COMMON),
		RAINBOW3(new Item(3628), Rarity.COMMON),
		RAINBOW4(new Item(3629), Rarity.COMMON),
		RAINBOW5(new Item(3630), Rarity.COMMON),
		RAINBOW6(new Item(3631), Rarity.COMMON),
		RAINBOW7(new Item(3632), Rarity.COMMON),
		
		BURST1(new Item(1840), Rarity.COMMON),
		BURST2(new Item(1841), Rarity.COMMON),
		BURST3(new Item(1842), Rarity.COMMON),
		BURST4(new Item(2912), Rarity.COMMON),
		BURST5(new Item(3298), Rarity.COMMON),
		BURST6(new Item(8675), Rarity.COMMON),
		BURST7(new Item(18490), Rarity.COMMON),
		
		CRYPTIC1(new Item(1843), Rarity.UNCOMMON),
		CRYPTIC2(new Item(1845), Rarity.UNCOMMON),
		CRYPTIC3(new Item(1846), Rarity.UNCOMMON),
		CRYPTIC4(new Item(1848), Rarity.UNCOMMON),
		CRYPTIC5(new Item(1849), Rarity.UNCOMMON),
		CRYPTIC6(new Item(1850), Rarity.UNCOMMON),
		CRYPTIC7(new Item(20652), Rarity.UNCOMMON),
		
		ZIVA1(new Item(3820), Rarity.UNCOMMON),
		ZIVA2(new Item(3821), Rarity.UNCOMMON),
		ZIVA3(new Item(3850), Rarity.UNCOMMON),
		ZIVA4(new Item(6450), Rarity.UNCOMMON),
		ZIVA5(new Item(6451), Rarity.UNCOMMON),
		ZIVA6(new Item(6452), Rarity.UNCOMMON),
		ZIVA7(new Item(17855), Rarity.UNCOMMON),
		
		TYRANT1(new Item(8816), Rarity.RARE),
		TYRANT2(new Item(8817), Rarity.RARE),
		TYRANT3(new Item(8818), Rarity.RARE),
		TYRANT4(new Item(8820), Rarity.RARE),
		TYRANT5(new Item(8821), Rarity.RARE),
		TYRANT6(new Item(8822), Rarity.RARE),
		TYRANT7(new Item(3290), Rarity.RARE),
		
		EVIL1(new Item(3961), Rarity.VERY_RARE),
		EVIL2(new Item(3963), Rarity.VERY_RARE),
		EVIL3(new Item(3965), Rarity.VERY_RARE),
		EVIL4(new Item(3967), Rarity.VERY_RARE),
		EVIL5(new Item(4036), Rarity.VERY_RARE),
		EVIL6(new Item(18378), Rarity.VERY_RARE),
		EVIL7(new Item(17848), Rarity.VERY_RARE),
		
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
