package com.ruthlessps.world.content.mbox.impl;

import java.util.ArrayList;

import com.ruthlessps.model.Item;
import com.ruthlessps.world.content.mbox.MysteryBoxRewards;
import com.ruthlessps.world.content.mbox.MysteryBoxes;
import com.ruthlessps.world.content.mbox.Rarity;
import com.ruthlessps.world.entity.impl.player.Player;

public class SharpyBox extends MysteryBoxes {

	@Override
	public int getBox() {
		return 917;
	}

	@Override
	public String getName() {
		return "Sharpy Box";
	}
	
	@Override
	public ArrayList<Item> getArray(Player player) {
		return player.sharpyBoxRewards;
	}

	@Override
	public ArrayList<MysteryBoxRewards> getRewards() {
		return Rewards.getRewards();
	}

	public enum Rewards {
		
		RAINBOW_WHIP(new Item(3626), Rarity.COMMON),
		RAINBOW_WHIP_I(new Item(9122), Rarity.COMMON),
		BURST_SWORD(new Item(8675), Rarity.COMMON),
		CRYPTIC_SWORD(new Item(1849), Rarity.UNCOMMON),
		CRYPTIC_SWORD_U(new Item(3508), Rarity.RARE),
		ZIVA_SWORD(new Item(17855), Rarity.VERY_RARE),
		TYRANT_DAGGER(new Item(3290), Rarity.VERY_RARE),
		EVIL_SWORD(new Item(17848), Rarity.VERY_RARE),
		OBLIT_MAIN(new Item(5234), Rarity.VERY_RARE),
		OBLIT_OFF(new Item(19919), Rarity.VERY_RARE)
		
		
		
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
