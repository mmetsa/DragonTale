package com.ruthlessps.model;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

/**
 * Handles all the player ranks
 * 
 * @author 2012 <http://www.rune-server.org/members/dexter+morgan/>
 *
 */
public class PlayerRanks {

	/*
	 * A member who has donated to the server.
	 */
	public enum DonorRights {
		NONE(1, 0),
		DONOR(1, 0.05),
		DELUXE_DONOR(1.25, 0.1),
		SPONSOR(1.5, 0.15),
		SUPER_SPONSOR(1.75, 0.25),
		KING(1.75, 0.5),
		DRAGON(1.75, 0.5),
		VIP(1.75, 1.0),
		VETERAN(2, 0.75),
		HEARTH(2, 1.0),
		GOLDBAG(2, 1.0),
		GEM(2, 1.0);

		private static final ImmutableSet<DonorRights> MEMBERS = Sets.immutableEnumSet(DONOR, DELUXE_DONOR, SPONSOR, SUPER_SPONSOR, KING, DRAGON, VIP, VETERAN, HEARTH, GOLDBAG, GEM);

		/**
		 * The experience modifier
		 */
		private double modifier;
		private double dropRateBoost;

		/**
		 * New donator right
		 * 
		 * @param modifier
		 *            the experience modifier
		 */
		DonorRights(double modifier, double dropRateBoost) {
			this.setModifier(modifier);
			this.dropRateBoost = dropRateBoost;
		}

		/**
		 * Gets the modifier
		 *
		 * @return the modifier
		 */
		public double getModifier() {
			return modifier;
		}

		public double getDropRateBoost() {
			return dropRateBoost;
		}

		/**
		 * Sets the modifier
		 *
		 * @param modifier
		 *            the modifier
		 */
		public void setModifier(double modifier) {
			this.modifier = modifier;
		}

		public boolean isMember() {
			return MEMBERS.contains(this);
		}
	}

	/**
	 * Represents a player's privilege rights.
	 */
	public enum PlayerRights {
		PLAYER(-1, null, 1, 1), OWNER(-1, "<col=B40404>", 1, 1), DEVELOPER(-1, "<shad=B40404>", 1, 1), WEB_DEVELOPER(-1,
				null, 1, 1), GLOBAL_ADMINISTRATOR(-1, null, 1, 1), ADMINISTRATOR(-1, "<col=FFFF64><shad=0>", 1,
						1), FORUM_ADMINISTRATOR(-1, null, 1, 1), GLOBAL_MODERATOR(-1, null, 1, 1), TRIAL_MODERATOR(-1,
								null, 1, 1), MODERATOR(-1, "<col=20B2AA><shad=0>", 1, 1), FORUM_MODERATOR(-1, null, 1,
										1), TRIAL_FORUM_MODERATOR(-1, null, 1, 1), ITEM_MODELLER(-1, null, 1,
												1), GFX_ARTIST(-1, null, 1,
														1), YOUTUBER(-1, null, 1, 1), EX_STAFF(-1, null, 1, 1), MANAGER(-1, "<col=20B2AA><shad=0>", 1, 1), HELPER(-1, "<col=20B2AA><shad=0>", 1, 1);

		private static final ImmutableSet<PlayerRights> STAFF = Sets.immutableEnumSet(OWNER, DEVELOPER,
				GLOBAL_ADMINISTRATOR, ADMINISTRATOR, GLOBAL_MODERATOR, MODERATOR, HELPER, MANAGER);

		/**
		 * Gets the rank for a certain id.
		 * 
		 * @param id
		 *            The id (ordinal()) of the rank.
		 * @return rights.
		 */
		public static PlayerRights forId(int id) {
			for (PlayerRights rights : PlayerRights.values()) {
				if (rights.ordinal() == id) {
					return rights;
				}
			}
			return null;
		}

		private int yellDelay;
		private String yellHexColorPrefix;
		private double loyaltyPointsGainModifier;
		private double experienceGainModifier;

		PlayerRights(int yellDelaySeconds, String yellHexColorPrefix, double loyaltyPointsGainModifier,
				double experienceGainModifier) {
			this.yellDelay = yellDelaySeconds;
			this.yellHexColorPrefix = yellHexColorPrefix;
			this.loyaltyPointsGainModifier = loyaltyPointsGainModifier;
			this.experienceGainModifier = experienceGainModifier;
		}

		public double getExperienceGainModifier() {
			return experienceGainModifier;
		}

		public double getLoyaltyPointsGainModifier() {
			return loyaltyPointsGainModifier;
		}

		public int getYellDelay() {
			return yellDelay;
		}

		public String getYellPrefix() {
			return yellHexColorPrefix;
		}

		public boolean isStaff() {
			return STAFF.contains(this);
		}

		public boolean isDeveloper() {
			return this == PlayerRights.DEVELOPER;
		}

		public static PlayerRights forName(String s) {
			for (PlayerRights rights : PlayerRights.values()) {
				if (rights.name().equalsIgnoreCase(s)) {
					return rights;
				}
			}
			return PlayerRights.PLAYER;
		}
	}
}
