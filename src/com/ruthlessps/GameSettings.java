package com.ruthlessps;

import java.math.BigInteger;

import com.ruthlessps.model.Position;

public class GameSettings {
	
	/**
     * Added by Logan: MySQL Connection Constants:
     */
    static String DBHost = "68.66.216.19";
    public static String SERVER_NAME = "DragonTale";
	/**
	 * The game port
	 */
	static final int GAME_PORT = 43594;

	/**
	 * The game version
	 */
	public static final int GAME_VERSION = 13;
	

	/**
	 * The maximum amount of players that can be logged in on a single game
	 * sequence.
	 */
	public static final int LOGIN_THRESHOLD = 50;

	/**
	 * The maximum amount of players that can be logged in on a single game
	 * sequence.
	 */
	public static final int LOGOUT_THRESHOLD = 50;

	/**
	 * The maximum amount of connections that can be active at a time, or in other
	 * words how many clients can be logged in at once per connection. (0 is counted
	 * too)
	 */
	public static final int CONNECTION_AMOUNT = 4;

	/**
	 * The number of seconds before a connection becomes idle.
	 */
	public static final int IDLE_TIME = 15;

	/**
	 * The keys used for encryption on login
	 */
	public static final BigInteger RSA_MODULUS = new BigInteger(
			"111377950644322995055878946588973476276098595576679689347341120908104097498761997539542948722679721423776173366821666620220475565214617570037867168184868229657733599896865610380594769839440841988231150852671825777088384133829464416134057822619306301747013033047468420325277248535353421185505580095222412942231");
	public static final BigInteger RSA_EXPONENT = new BigInteger(
			"90635959286556204469538367326596173714953237091955402173923076434243369818636416875641310119138118970548405908102798788311921553382455471645933317265596400232330465594893569159382079209885781000921423454191172803243766192103927869910078345568377070943310797348467211730079031109362410362608297963700780337985");

	/**
	 * The maximum amount of messages that can be decoded in one sequence.
	 */
	public static final int DECODE_LIMIT = 30;

	public static int[] BANNEDWILD_ITEMS = { 3307, 3309, 3310, 3311, 3312, 3313, 17293, 1009,  295, 1647, 14667, 5510, 5512, 5509, 11999, 1389, 1390, 17401, 17402,
			15078, 4202, 15009, 15010, 15262, 4155, 13663, 292, 989, 20084, 6199, 15501, 14018, 20250, 20251, 20252,
			20253, 14556, 14557, 14558, 14559, 14560, 14561, 14562, 14563, 14564, 14565, 14566, 14567, 14568, 14569,
			14570, 14571, 14572, 20690, 14581, 14582, 14583, 14584, 14585, 14586, 14587, 14588, 14589, 14590, 14591, 79,
			80, 81, 14619, 14596, 14597, 14598, 14599, 18742, 82, 894, 895, 896, 924, 2380, 909, 898, 899, 900, 901,
			902, 903, 2548, 2547, 904, 3088, 906, 907, 908, 926, 3878, 910, 911, 912, 3091, 914, 3089, 20259, 20260,
			20256, 20249, 20254, 20255, 3879, 3072, 3073, 3074, 3075, 3076, 6862, 667, 1247, 805, 1373, 1289, 3637,
			3638, 3639, 3640, 3641, 3642, 4083, 4177, 3643, 3644, 3645, 3646, 941, 2749, 2750, 2751, 2752, 2753, 2754,
			1666, 1667, 3647, 3648, 3649, 3650, 3651, 3652, 3653, 3654, 3655, 3656, 3657, 3658, 3659, 3660, 3661, 8675,
			8677, 3619, 3620, 3621, 3622, 980, 17642, 17644, 3623, 3624, 3625, 3626, 3627, 3628, 3629, 3630, 3631, 3632,
			3271, 3272, 3273, 3274, 3275, 3276, 3807, 3808, 3809, 3090, 3811, 3812, 3080, 3081, 3082, 3083, 3086,
			3087, 4150, 3092, 3135, 3242, 3277, 3278, 3279, 3280, 3281, 3282, 3283, 3284, 3285, 3286, 3287, 3288, 3289,
			3290, 3291, 3292, 3293, 3294, 3295, 3296, 3297, 3298, 3299, 11292, 3869, 3308, 3244, 3078, 3079, 3084,
			20080, 2870, 3301, 3302, 84, 4204, 3300, 3303, 3304, 3305, 2633, 421, 85, 596, 275, 293, 298, 423,
			432, 601, 605, 709, 758, 759, 788, 983, 20079, 5, 20075, 20073, 20074, 20082, 1543, 1544, 625, 624, 623,
			8822, 8821, 8820, 8818, 8817, 8816, 8810, 8811, 8812, 5180, 5181, 10073, 14094, 14095, 14096, 14097, 14107, 14108, 14109, 14110, 14117, 14118, 14119, 14120, 14121, 14192, 14193, 14194, 14195, 14196, 14197, 14198, 14199, 14200, 14201, 14287, 14288, 14289, 14290, 14291, 14292, 14293, 14294, 14295, 14296, 14297, 14298, 14299, 14300, 14301, 14302, 14303, 14304, 14305, 14306, 14307, 14308, 14309, 14310, 14311, 14312, 14313, 14314, 14315, 14316, 14317, 14318, 14319, 14320, 14321, 14322, 14323, 14324, 14325, 14326, 14327, 14328, 14329, 14330, 14331, 14332, 14333, 14334, 14335, 14336, 14337, 14338, 14339, 14340, 14341, 14342, 14343, 14344, 14345, 14346, 14347, 14348, 14349, 14350, 14351, 14352, 14353, 14354, 14355, 14356, 14357, 14358, 14359, 14360, 14361, 14362, 14363, 14364, 14365, 14366, 14367, 14368, 14369, 14370, 14371, 14372, 14373, 14374, 14375, 14376, 14377, 14378, 14379, 14380, 14381, 14382, 14383, 14384, 14385, 14386, 14391, 14392, 14393, 14394, 14395, 14396, 14397, 14398, 14399, 14400, 14401, 14402, 14403, 14404, 14405, 14406, 14407, 14408, 14409, 14410, 14411, 14412, 14413, 14414, 14415, 144116, 14417, 14418, 14419, 14420, 12543, 20693, 20696, 4646, 4647, 4648, 4649, 4650, 4651, 3948, 18457, 18458, 17892, 3915, 18487, 20027, 18491, 11311, 9281, 16624, 16625, 17846, 3972, 16626, 4766, 18376, 4764, 13256, 13257, 13254, 19941, 19714, 4766, 5249, 16618, 16619, 16620, 4741, 15659, 15658, 13253, 4766,
			621, 620, 619, 20690, 618, 11609, 665, 666, 669, 670, 671, 672, 673, 21636, 695, 11530, 11531, 11532, 1849, 1843, 1845, 1846, 1848, 1850, 20652, 1840, 1841, 1842, 15, 3314, 3307, 3309, 3310, 3311, 3312, 3313, 7671, 7673 };

	/* GAME */

	/**
	 * Processing the engine
	 */
	static final int ENGINE_PROCESSING_CYCLE_RATE = 200;

	/**
	 * Is it currently bonus xp?
	 */
	public static final boolean BONUS_EXP = true;
	 
	/**
	 * 
	 * The default position
	 */
	public static final Position DEFAULT_POSITION = new Position(3232, 9505, 0);

	public static final int MAX_STARTERS_PER_IP = 2;

	/**
	 * Untradeable items Items which cannot be traded or staked
	 */
	public static final int[] UNTRADEABLE_ITEMS = { 962, 963, 11593, 11596, 11597, 11598, 5040, 6204, 13147, 7406 };

	/**
	 * Unsellable items Items which cannot be sold to shops
	 */
	public static int[] UNSELLABLE_ITEMS = new int[] { 962, 963 };

	public static final int ATTACK_TAB = 0, SKILLS_TAB = 1, QUESTS_TAB = 2, INVENTORY_TAB = 3, EQUIPMENT_TAB = 4,
			PRAYER_TAB = 5, MAGIC_TAB = 6, CLAN_CHAT_TAB = 7, FRIEND_TAB = 8, IGNORE_TAB = 9, LOGOUT = 10,
			OPTIONS_TAB = 11, EMOTES_TAB = 12, SUMMONING_TAB = 13, ACHIEVEMENT_TAB = 14, PLAYER_PANAL = 16;
}
