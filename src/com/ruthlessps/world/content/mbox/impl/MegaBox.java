package com.ruthlessps.world.content.mbox.impl;

import java.util.ArrayList;

import com.ruthlessps.model.Item;
import com.ruthlessps.world.content.mbox.MysteryBoxRewards;
import com.ruthlessps.world.content.mbox.MysteryBoxes;
import com.ruthlessps.world.content.mbox.Rarity;
import com.ruthlessps.world.entity.impl.player.Player;

public class MegaBox extends MysteryBoxes {

	@Override
	public int getBox() {
		return 15501;
	}

	@Override
	public String getName() {
		return "Mega mystery box";
	}
	
	@Override
	public ArrayList<Item> getArray(Player player) {
		return player.megaBoxRewards;
	}

	@Override
	public ArrayList<MysteryBoxRewards> getRewards() {
		return Rewards.getRewards();
	}

	public enum Rewards {
		OREO1(new Item(14559), Rarity.COMMON),
		OREO2(new Item(14560), Rarity.COMMON),
		OREO3(new Item(14561), Rarity.COMMON),
		OREO4(new Item(14562), Rarity.COMMON),
		OREO5(new Item(14563), Rarity.COMMON),
		OREO6(new Item(14564), Rarity.COMMON),
		OREO7(new Item(14565), Rarity.COMMON),
		RUTHLESSBUCKS1(new Item(13664, 1), Rarity.COMMON),
		RUTHLESSBUCKS2(new Item(13664, 5), Rarity.COMMON),
		RUTHLESSBUCKS3(new Item(13664, 10), Rarity.COMMON),
		RUTHLESSBUCKS4(new Item(13664, 15), Rarity.COMMON),
		RUTHLESSBUCKS5(new Item(13664, 25), Rarity.COMMON),
		RUTHLESSBUCKS6(new Item(13664, 35), Rarity.COMMON),
		RUTHLESSBUCKS7(new Item(13664, 45), Rarity.COMMON),
		RUTHLESSBUCKS8(new Item(13664, 55), Rarity.COMMON),
		DEAGLE(new Item(3092), Rarity.COMMON),
		MBOX(new Item(6199, 1), Rarity.COMMON),
		MBOX1(new Item(6199, 3), Rarity.COMMON),
		MBOX2(new Item(6199, 6), Rarity.COMMON),
		MBOX3(new Item(6199, 9), Rarity.COMMON),
		//MBOX4(new Item(6199, 15), Rarity.COMMON),
		
		//GRANITE_MAUL(new Item(4153), Rarity.UNCOMMON),
		MBOX4(new Item(6199, 15), Rarity.UNCOMMON),
		RUTHLESSBUCKS9(new Item(13664, 75), Rarity.UNCOMMON),
		RUTHLESSBUCKS10(new Item(13664, 100), Rarity.UNCOMMON),
		RUTHLESSBUCKS11(new Item(13664, 125), Rarity.UNCOMMON),
		RUTHLESSBUCKS12(new Item(13664, 150), Rarity.UNCOMMON),
		UZI(new Item(3279), Rarity.UNCOMMON),
		ROCKS(new Item(15273, 5000), Rarity.UNCOMMON),
		VOTES(new Item(19670, 10), Rarity.UNCOMMON),
		DART1(new Item(14581), Rarity.UNCOMMON),
		DART2(new Item(14582), Rarity.UNCOMMON),
		DART3(new Item(14583), Rarity.UNCOMMON),
		DART4(new Item(14584), Rarity.UNCOMMON),
		DART5(new Item(14585), Rarity.UNCOMMON),
		DART6(new Item(14586), Rarity.UNCOMMON),
		DART7(new Item(20690), Rarity.UNCOMMON),
		
		ROC_4(new Item(15273, 10000), Rarity.RARE),
		VOTE_4(new Item(19670, 25), Rarity.RARE),
		SILVER1(new Item(81), Rarity.RARE),
		SILVER2(new Item(14596), Rarity.RARE),
		SILVER3(new Item(14597), Rarity.RARE),
		SILVER4(new Item(14598), Rarity.RARE),
		SILVER5(new Item(14599), Rarity.RARE),
		SILVER6(new Item(18742), Rarity.RARE),
		SILVER7(new Item(14619), Rarity.RARE),
		MBOXX(new Item(6199, 25), Rarity.RARE),
		AK(new Item(3081), Rarity.RARE),
		
		EARTH(new Item(895), Rarity.VERY_RARE),
		BS_BONES4(new Item(17644, 100), Rarity.VERY_RARE),
		WINTER_BONES4(new Item(17642, 100), Rarity.VERY_RARE),
		CAMO_BONES4(new Item(980, 100), Rarity.VERY_RARE),
		RUTHLESS_BUCKS7(new Item(13664, 250), Rarity.VERY_RARE),
		RUTHLESS_BUCKS6(new Item(13664, 500), Rarity.VERY_RARE),
		RUTHLESS_BUCKS5(new Item(13664, 750), Rarity.VERY_RARE),
		RUTHLESS_BUCKS4(new Item(13664, 1000), Rarity.VERY_RARE),
		RAINBOW_WHIP(new Item(3626), Rarity.VERY_RARE),
		RAINBOW_WINGS(new Item(3632), Rarity.VERY_RARE),
		RAINBOW_HELM(new Item(3628), Rarity.VERY_RARE),
		RAINBOW_BODY(new Item(3629), Rarity.VERY_RARE),
		RAINBOW_LEGS(new Item(3627), Rarity.VERY_RARE),
		RAINBOW_BOOTS(new Item(3631), Rarity.VERY_RARE),
		RAINBOW_GLOVES(new Item(3630), Rarity.VERY_RARE),
		DRAGUNOV(new Item(3277), Rarity.VERY_RARE),
		AIR(new Item(894), Rarity.VERY_RARE),
		ROR(new Item(596), Rarity.VERY_RARE),
		WATER(new Item(82), Rarity.VERY_RARE),
		RUC_5(new Item(3248), Rarity.VERY_RARE),
		RUC_4(new Item(996), Rarity.VERY_RARE),
		RUC_3(new Item(939), Rarity.VERY_RARE),
		RUC_2(new Item(936), Rarity.VERY_RARE),
		RUC_1(new Item(932), Rarity.VERY_RARE),
		BOX_1(new Item(7673), Rarity.VERY_RARE),
		BOX_2(new Item(7671), Rarity.VERY_RARE),
		CRY_6(new Item(20652), Rarity.VERY_RARE),
		CRY_5(new Item(1850), Rarity.VERY_RARE),
		CRY_4(new Item(1848), Rarity.VERY_RARE),
		CRY_3(new Item(1846), Rarity.VERY_RARE),
		CRY_2(new Item(1845), Rarity.VERY_RARE),
		CRY_1(new Item(1843), Rarity.VERY_RARE),
		
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
