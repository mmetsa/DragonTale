package com.ruthlessps.model;

public enum ItemRarity {

	COMMON(0), UNCOMMON(1), RARE(2), VERY_RARE(3), LEGENDARY(4);

	public int rarity;

	ItemRarity(int rarity) {
		this.rarity = rarity;
	}

	public int getRarity() {
		return this.rarity;
	}

	public void setRarity(ItemRarity rarity) {
		this.rarity = rarity.rarity;
	}
}
