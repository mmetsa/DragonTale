package com.ruthlessps.world.content.mbox.impl;

import java.util.ArrayList;

import com.ruthlessps.model.Item;
import com.ruthlessps.world.content.mbox.MysteryBoxRewards;
import com.ruthlessps.world.content.mbox.MysteryBoxes;
import com.ruthlessps.world.content.mbox.Rarity;
import com.ruthlessps.world.entity.impl.player.Player;

public class DonatorBox extends MysteryBoxes {

	@Override
	public int getBox() {
		return 6930;
	}

	@Override
	public String getName() {
		return "Donator box";
	}
	
	@Override
	public ArrayList<Item> getArray(Player player) {
		return player.donatorBoxRewards;
	}

	@Override
	public ArrayList<MysteryBoxRewards> getRewards() {
		return Rewards.getRewards();
	}

	public enum Rewards {
		RUTHLESSBUCKS1(new Item(13664, 250), Rarity.COMMON),
		RUTHLESSBUCKS2(new Item(13664, 500), Rarity.COMMON),
		RUTHLESSBUCKS3(new Item(13664, 750), Rarity.COMMON),
		RUTHLESSBUCKS4(new Item(13664, 1000), Rarity.COMMON),
		RUTHLESSBUCKS9(new Item(6199, 5), Rarity.COMMON),
		RUTHLESSBUCKS71(new Item(6199, 10), Rarity.COMMON),
		RUTHLESSBUCKS81(new Item(6199, 15), Rarity.COMMON),
		
		
		VOTES(new Item(2800, 2), Rarity.UNCOMMON),
		DART1(new Item(2800, 5), Rarity.UNCOMMON),
		DART2(new Item(2800, 8), Rarity.UNCOMMON),
		DART3(new Item(2801, 3), Rarity.UNCOMMON),
		DART4(new Item(2801, 6), Rarity.UNCOMMON),
		DART5(new Item(2801, 10), Rarity.UNCOMMON),
		DART6(new Item(4032, 5), Rarity.UNCOMMON),
		DART7(new Item(4032, 10), Rarity.UNCOMMON),
		DART216(new Item(15501, 10), Rarity.UNCOMMON),
		DART217(new Item(15501, 20), Rarity.UNCOMMON),
		BLOODSHOT(new Item(673), Rarity.UNCOMMON),
		BLOODSHOT1(new Item(669), Rarity.UNCOMMON),
		BLOODSHOT2(new Item(670), Rarity.UNCOMMON),
		BLOODSHOT3(new Item(666), Rarity.UNCOMMON),
		BLOODSHOT4(new Item(671), Rarity.UNCOMMON),
		BLOODSHOT5(new Item(672), Rarity.UNCOMMON),
		BLOODSHOT6(new Item(665), Rarity.UNCOMMON),
		
		GALARS1(new Item(4741), Rarity.RARE),
		GALARS2(new Item(5249), Rarity.RARE),
		GALARS3(new Item(13253), Rarity.RARE),
		GALARS4(new Item(15658), Rarity.RARE),
		GALARS5(new Item(15659), Rarity.RARE),
		GALARS6(new Item(16618), Rarity.RARE),
		GALARS7(new Item(16619), Rarity.RARE),
		GALARS8(new Item(16620), Rarity.RARE),
		
		ESTPURE1(new Item(4764), Rarity.RARE),
		ESTPURE2(new Item(13254), Rarity.RARE),
		ESTPURE3(new Item(13256), Rarity.RARE),
		ESTPURE4(new Item(13257), Rarity.RARE),
		ESTPURE5(new Item(18376), Rarity.RARE),
		ESTPURE6(new Item(19714), Rarity.RARE),
		ESTPURE7(new Item(19941), Rarity.RARE),
		
		ARCHIE1(new Item(3915), Rarity.RARE),
		ARCHIE2(new Item(3948), Rarity.RARE),
		ARCHIE3(new Item(12521), Rarity.RARE),
		ARCHIE4(new Item(17892), Rarity.RARE),
		ARCHIE5(new Item(18457), Rarity.RARE),
		ARCHIE6(new Item(18458), Rarity.RARE),
		ARCHIE7(new Item(18487), Rarity.RARE),
		ARCHIE8(new Item(18491), Rarity.RARE),
		ARCHIE9(new Item(20027), Rarity.RARE),
	
		ZANYTE1(new Item(16624), Rarity.RARE),
		ZANYTE2(new Item(16625), Rarity.RARE),
		ZANYTE3(new Item(16626), Rarity.RARE),
		ZANYTE4(new Item(17846), Rarity.RARE),
		ZANYTE5(new Item(9281), Rarity.RARE),
		ZANYTE6(new Item(11311), Rarity.RARE),
		
		TYRANT1(new Item(8816), Rarity.RARE),
		TYRANT2(new Item(8817), Rarity.RARE),
		TYRANT3(new Item(8818), Rarity.RARE),
		TYRANT4(new Item(8820), Rarity.RARE),
		TYRANT5(new Item(8821), Rarity.RARE),
		TYRANT6(new Item(8822), Rarity.RARE),
		TYRANT7(new Item(3290), Rarity.RARE)
		
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
