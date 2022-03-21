package com.ruthlessps.world.content.mbox.impl;

import java.util.ArrayList;

import com.ruthlessps.model.Item;
import com.ruthlessps.world.content.mbox.MysteryBoxRewards;
import com.ruthlessps.world.content.mbox.MysteryBoxes;
import com.ruthlessps.world.content.mbox.Rarity;
import com.ruthlessps.world.entity.impl.player.Player;

public class PetBox extends MysteryBoxes {

	@Override
	public int getBox() {
		return 2800;
	}

	@Override
	public String getName() {
		return "Pet mystery box";
	}

	@Override
	public ArrayList<Item> getArray(Player player) {
		return player.petBoxRewards;
	}

	@Override
	public ArrayList<MysteryBoxRewards> getRewards() {
		return Rewards.getRewards();
	}
	
	public enum Rewards {
		RICK(new Item(15251), Rarity.COMMON),
		AMERICAN_T(new Item(1647), Rarity.COMMON),
		SKY_T(new Item(9411), Rarity.COMMON),
		OREO_T(new Item(9412), Rarity.COMMON),
		CASH_T(new Item(9414), Rarity.COMMON),
		CLOBE(new Item(9385), Rarity.COMMON),
		DARTH_T(new Item(9413), Rarity.COMMON),

		CAMOUFLAGE_T(new Item(9491), Rarity.UNCOMMON),
		WINTER_T(new Item(9492), Rarity.UNCOMMON),
		PROSTEX(new Item(9386), Rarity.UNCOMMON),
		REDONEX(new Item(9387), Rarity.UNCOMMON),
		SILVER_T(new Item(9402), Rarity.UNCOMMON),
		LEGION(new Item(13732), Rarity.UNCOMMON),
		ZARTHYX(new Item(13733), Rarity.UNCOMMON),

		RAINBOW_UNI(new Item(5195), Rarity.RARE),
		CRASH_BANDICOOT(new Item(5196), Rarity.RARE),
		PZ_DRAGON(new Item(13731), Rarity.RARE),
		
		BLOODSHOT_T(new Item(9493), Rarity.VERY_RARE),
		RAINBOW_T(new Item(13730), Rarity.VERY_RARE),
		CHARMELEON(new Item(9401), Rarity.VERY_RARE),
		MEWTWO(new Item(9403), Rarity.VERY_RARE),
		SQUIRTLE(new Item(9409), Rarity.VERY_RARE),
		HOMER(new Item(9404), Rarity.VERY_RARE),
		SONIC(new Item(9405), Rarity.VERY_RARE),
		MR_KRABBS(new Item(9406), Rarity.VERY_RARE),
		LUCARIO(new Item(9407), Rarity.VERY_RARE),
		PIKACHU(new Item(9410), Rarity.VERY_RARE),
		LEFOSH(new Item(15253), Rarity.VERY_RARE),
		IKTOMI(new Item(15252), Rarity.VERY_RARE),
		VORAGO(new Item(9400), Rarity.VERY_RARE),
		
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
