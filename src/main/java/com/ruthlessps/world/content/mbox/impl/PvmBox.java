package com.ruthlessps.world.content.mbox.impl;

import java.util.ArrayList;

import com.ruthlessps.model.Item;
import com.ruthlessps.world.content.mbox.MysteryBoxRewards;
import com.ruthlessps.world.content.mbox.MysteryBoxes;
import com.ruthlessps.world.content.mbox.Rarity;
import com.ruthlessps.world.entity.impl.player.Player;

public class PvmBox extends MysteryBoxes {

	@Override
	public int getBox() {
		return 18768;
	}

	@Override
	public String getName() {
		return "PVM Box";
	}
	
	@Override
	public ArrayList<Item> getArray(Player player) {
		return player.pvmBoxRewards;
	}

	@Override
	public ArrayList<MysteryBoxRewards> getRewards() {
		return Rewards.getRewards();
	}

	public enum Rewards {
		//Food
		Monkfish(new Item(7947, 100), Rarity.COMMON),
		Seaturtles(new Item(398, 100), Rarity.COMMON),
		Sharks(new Item(386, 100), Rarity.COMMON),
		Mantarays(new Item(392, 100), Rarity.COMMON),
		Rocktails(new Item(15273, 100), Rarity.COMMON),
		
		//Potions
		Super_attacks(new Item(2437, 20), Rarity.COMMON),
		Super_strenghts(new Item(2441, 20), Rarity.COMMON),
		Super_defences(new Item(2443, 20), Rarity.COMMON),
		Prayer_potions(new Item(2435, 20), Rarity.UNCOMMON),
		Super_restores(new Item(3025, 20), Rarity.UNCOMMON),
		
		//Bandages
		
		//Quest Crystal
		Quest_crystal(new Item(611, 1), Rarity.UNCOMMON),
		
		//XP Lamps
		XP_lamp(new Item(11137, 1), Rarity.UNCOMMON),
		Elite_xp_lamp(new Item(2528, 1), Rarity.RARE),
		
		//Misc Potions
		Combat_potion(new Item(195, 1), Rarity.RARE),
		Double_drop_pot(new Item(4061, 1), Rarity.VERY_RARE),
		Lifesteal_pot(new Item(4062, 1), Rarity.VERY_RARE),
		DR_pot(new Item(4063, 1), Rarity.VERY_RARE),
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
