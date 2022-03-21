package com.ruthlessps.world.content.skill.impl.prayer;

public enum BonesData {

	TIER_6_BONES(20249, 4750),
	TIER_5_BONES(20254, 4250),
	TIER_4_BONES(20260, 3750),
	TIER_3_BONES(20255, 3250),
	TIER_2_BONES(3879, 2750),
	TIER_1_BONES(3086, 2250),
	CAMOUFLAGE_BONE(980,10000), WINTER_CAMO_BONE(17642, 12500), BLOODSHOT_CAMO_BONE(17644, 15000), 
	BOSS_BONES(931, 19000), TRAINING_BONES(20259, 1250);

	public static BonesData forId(int bone) {
		for (BonesData prayerData : BonesData.values()) {
			if (prayerData.getBoneID() == bone) {
				return prayerData;
			}
		}
		return null;
	}

	private int boneId;
	private int buryXP;

	BonesData(int boneId, int buryXP) {
		this.boneId = boneId;
		this.buryXP = buryXP;
	}

	public int getBoneID() {
		return this.boneId;
	}

	public int getBuryingXP() {
		return this.buryXP;
	}

}
