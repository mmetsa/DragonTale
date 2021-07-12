package com.ruthlessps.world.content.mbox.impl;

import java.util.ArrayList;

import com.ruthlessps.model.Item;
import com.ruthlessps.world.content.mbox.MysteryBoxRewards;
import com.ruthlessps.world.content.mbox.MysteryBoxes;
import com.ruthlessps.world.content.mbox.Rarity;
import com.ruthlessps.world.entity.impl.player.Player;

public class FancyBox extends MysteryBoxes {

	@Override
	public int getBox() {
		return 4032;
	}

	@Override
	public String getName() {
		return "Fancy mystery box";
	}
	
	@Override
	public ArrayList<Item> getArray(Player player) {
		return player.fancyBoxRewards;
	}

	@Override
	public ArrayList<MysteryBoxRewards> getRewards() {
		return Rewards.getRewards();
	}

	public enum Rewards {
		
		LIME_SLED(new Item(4177), Rarity.COMMON),
		CYAN_SLED(new Item(4083), Rarity.COMMON),
		GOLD_SANTA(new Item(3090), Rarity.UNCOMMON),
		CYAN_SANTA(new Item(896), Rarity.UNCOMMON),
		RAINBOW_SANTA(new Item(924), Rarity.UNCOMMON),
		STRIPED_SANTA(new Item(2380), Rarity.UNCOMMON),
		ORANGE_SANTA(new Item(909), Rarity.UNCOMMON),
		BLUE_SANTA(new Item(898), Rarity.UNCOMMON),
		PINK_SANTA(new Item(899), Rarity.UNCOMMON),
		YELLOW_SANTA(new Item(900), Rarity.UNCOMMON),
		GREEN_SANTA(new Item(901), Rarity.UNCOMMON),
		LIME_SANTA(new Item(902), Rarity.UNCOMMON),
		PURPLE_SANTA(new Item(903), Rarity.UNCOMMON),
		//GOLD_SANTA(3090, COMMON),
		PURPLE_PHAT(new Item(904), Rarity.UNCOMMON),
		ORANGE_PHAT(new Item(3088), Rarity.UNCOMMON),
		LIME_PHAT(new Item(906), Rarity.UNCOMMON),
		PINK_PHAT(new Item(907), Rarity.UNCOMMON),
		CYAN_PHAT(new Item(908), Rarity.UNCOMMON),
		RAINBOW_PHAT(new Item(926), Rarity.UNCOMMON),
		STRIPED_PHAT(new Item(3878), Rarity.UNCOMMON),
		GOLD_PHAT(new Item(3811), Rarity.UNCOMMON),
		WHITE_PHAT_SPECS(new Item(11586), Rarity.RARE),
		ORANGE_PHAT_SPECS(new Item(11587), Rarity.RARE),
		CYAN_PHAT_SPECS(new Item(11588), Rarity.RARE),
		PURPLE_PHAT_SPECS(new Item(11589), Rarity.RARE),
		LIME_PHAT_SPECS(new Item(11590), Rarity.RARE),
		PINK_PHAT_SPECS(new Item(11591), Rarity.RARE),
		MYSTICAL_SPIRIT(new Item(910), Rarity.RARE),
		DEATHLY_SPIRIT(new Item(911), Rarity.RARE),
		LIME_SPIRIT(new Item(912), Rarity.RARE),
		PINK_SPIRIT(new Item(3091), Rarity.RARE),
		GREEN_SPIRIT(new Item(914), Rarity.RARE),
		PURPLE_SPIRIT(new Item(3089), Rarity.RARE),
		PATRIOT_SPIRIT(new Item(11530), Rarity.RARE),
		COLORFUL_SPIRIT(new Item(11531), Rarity.RARE),
		RASTA_SPIRIT(new Item(11532), Rarity.RARE),
		RAGE_HELM(new Item(14049), Rarity.VERY_RARE),
		RAGE_BODY(new Item(14047), Rarity.VERY_RARE),
		RAGE_LEGS(new Item(14048), Rarity.VERY_RARE),
		RAGE_GLOVES(new Item(14050), Rarity.VERY_RARE),
		RAGE_BOOTS(new Item(14051), Rarity.VERY_RARE);
		
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