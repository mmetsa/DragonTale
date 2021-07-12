package com.ruthlessps.world.content;

import java.util.concurrent.CopyOnWriteArrayList;

import com.ruthlessps.engine.task.Task;
import com.ruthlessps.engine.task.TaskManager;
import com.ruthlessps.model.GameObject;
import com.ruthlessps.model.GroundItem;
import com.ruthlessps.model.Item;
import com.ruthlessps.model.Position;
import com.ruthlessps.world.World;
import com.ruthlessps.world.clip.region.RegionClipping;
import com.ruthlessps.world.entity.impl.GroundItemManager;
import com.ruthlessps.world.entity.impl.player.Player;

/**
 * Handles customly spawned objects (mostly global but also privately for
 * players)
 * 
 * @author Gabriel Hannason
 */
public class CustomObjects {

	private static final int DISTANCE_SPAWN = 70; // Spawn if within 70 squares
	// of distance
	private static final CopyOnWriteArrayList<GameObject> CUSTOM_OBJECTS = new CopyOnWriteArrayList<GameObject>();

	/**
	 * Contains
	 * 
	 * @param ObjectId
	 *            - The object ID to spawn
	 * @param absX
	 *            - The X position of the object to spawn
	 * @param absY
	 *            - The Y position of the object to spawn
	 * @param Z
	 *            - The Z position of the object to spawn
	 * @param face
	 *            - The position the object will face
	 */

	// NOTE: You must add to the client's customobjects array to make them
	// spawn, this is just clipping!
	private static final int[][] CLIENT_OBJECTS = { { 32015, 3005, 10363, 0, 0 }, { 401, 3503, 3567, 0, 0 },
			{ 401, 3504, 3567, 0, 0 }, { 1309, 2715, 3465, 0, 0 }, { 1309, 2709, 3465, 0, 0 },
			{ 1309, 2709, 3458, 0, 0 }, { 1306, 2705, 3465, 0, 0 }, { 1306, 2705, 3458, 0, 0 },
			{ -1, 2715, 3466, 0, 0 }, { -1, 2712, 3466, 0, 0 }, { -1, 2713, 3464, 0, 0 }, { -1, 2711, 3467, 0, 0 },
			{ -1, 2710, 3465, 0, 0 }, { -1, 2709, 3464, 0, 0 }, { -1, 2708, 3466, 0, 0 }, { -1, 2707, 3467, 0, 0 },
			{ -1, 2704, 3465, 0, 0 }, { -1, 2714, 3466, 0, 0 }, { -1, 2705, 3457, 0, 0 }, { -1, 2709, 3461, 0, 0 },
			{ -1, 2709, 3459, 0, 0 }, { -1, 2708, 3458, 0, 0 }, { -1, 2710, 3457, 0, 0 }, { -1, 2711, 3461, 0, 0 },
			{ -1, 2713, 3461, 0, 0 }, { -1, 2712, 3459, 0, 0 }, { -1, 2712, 3457, 0, 0 }, { -1, 2714, 3458, 0, 0 },
			{ -1, 2705, 3459, 0, 0 }, { -1, 2705, 3464, 0, 0 }, { 2274, 2912, 5300, 2, 0 }, { 2274, 2914, 5300, 1, 0 },
			{ 2274, 2919, 5276, 1, 0 }, { 2274, 2918, 5274, 0, 0 }, { 2274, 3001, 3931, 0, 0 },
			{ -1, 2884, 9797, 0, 2 }, { 4875, 3064, 2873, 0, 0 }, { 4874, 3063, 2873, 0, 0 },
			{ 4876, 3062, 2873, 0, 0 }, { 4877, 3061, 2873, 0, 0 }, { 4878, 3060, 2873, 0, 0 },
			{ 4483, 2909, 4832, 0, 3 }, { 4483, 2901, 5201, 0, 2 }, { 4483, 2902, 5201, 0, 2 },
			{ 9326, 3001, 3960, 0, 0 }, { 1662, 3112, 9677, 0, 2 }, { 1661, 3114, 9677, 0, 2 },
			{ 1661, 3122, 9664, 0, 1 }, { 1662, 3123, 9664, 0, 2 }, { 1661, 3124, 9664, 0, 3 },
			{ 4483, 2918, 2885, 0, 3 }, { 12356, 3016, 2826, 0, 0 }, { 2182, 3018, 2824, 0, 0 },
			{ 1746, 3090, 3492, 0, 1 }, { 2644, 2737, 3444, 0, 0 }, { -1, 2608, 4777, 0, 0 }, { -1, 2601, 4774, 0, 0 },
			{ -1, 2611, 4776, 0, 0 },
			/** home bank **/
			{ 11758, 3031, 2871, 0, 0 }, { 11758, 3032, 2871, 0, 0 }, { 11758, 3033, 2871, 0, 0 },
			{ 11758, 3034, 2871, 0, 0 }, { 11758, 3035, 2871, 0, 0 }, { 11758, 3036, 2871, 0, 0 },
			{ 11758, 3037, 2871, 0, 0 }, { 11758, 3038, 2871, 0, 0 }, { 11758, 3039, 2871, 0, 0 },
			{ 11758, 3040, 2871, 0, 0 }, { 11758, 3041, 2871, 0, 0 }, { 11758, 3042, 2871, 0, 0 },
			{ 11758, 3043, 2871, 0, 0 }, { 11758, 3044, 2871, 0, 0 }, { 11758, 3045, 2871, 0, 0 },
			{ 11758, 3046, 2871, 0, 0 }, { 11758, 3047, 2871, 0, 0 }, { 11758, 3048, 2871, 0, 0 },
			/** New Member Zone */
			{ 2344, 3421, 2908, 0, 0 }, // Rock blocking
			{ 2345, 3438, 2909, 0, 0 }, { 2344, 3435, 2909, 0, 0 }, { 2344, 3432, 2909, 0, 0 },
			{ 2345, 3431, 2909, 0, 0 }, { 2344, 3428, 2921, 0, 1 }, { 2344, 3428, 2918, 0, 1 },
			{ 2344, 3428, 2915, 0, 1 }, { 2344, 3428, 2912, 0, 1 }, { 2345, 3428, 2911, 0, 1 },
			{ 2344, 3417, 2913, 0, 1 }, { 2344, 3417, 2916, 0, 1 }, { 2344, 3417, 2919, 0, 1 },
			{ 2344, 3417, 2922, 0, 1 }, { 2345, 3417, 2912, 0, 0 }, { 2346, 3418, 2925, 0, 0 },
			{ 10378, 3426, 2907, 0, 0 }, { 8749, 3426, 2923, 0, 2 }, // Altar
			{ -1, 3420, 2909, 0, 10 }, // Remove crate by mining
			{ -1, 3420, 2923, 0, 10 }, // Remove Rockslide by Woodcutting
			{ -1, 3091, 3495, 0, 10 }, // Remove table from edgeville bank
			{ 14859, 3421, 2909, 0, 0 }, // Mining
			{ -1, 2366, 4959, 0, 0 }, { 14859, 3419, 2909, 0, 0 }, { 14859, 3418, 2910, 0, 0 },
			{ 14859, 3418, 2911, 0, 0 }, { 14859, 3422, 2909, 0, 0 }, { 1306, 3418, 2921, 0, 0 }, // Woodcutting
			{ 1306, 3421, 2924, 0, 0 }, { 1306, 3420, 2924, 0, 0 }, { 1306, 3419, 2923, 0, 0 },
			{ 1306, 3418, 2922, 0, 0 }, { 27663, 3381, 3269, 0, 0 }, { 27663, 3382, 3270, 0, 0 },
			{ -1, 3430, 2912, 0, 2 }, { 13493, 3424, 2916, 0, 1 }, // Armour
			// stall
			/** New Member Zone end */
			{ -1, 3098, 3496, 0, 1 }, { -1, 3095, 3498, 0, 1 }, { -1, 3088, 3509, 0, 1 }, { -1, 3095, 3499, 0, 1 },
			{ -1, 3085, 3506, 0, 1 }, { 30205, 3038, 2823, 0, 2 }, { -1, 3206, 3263, 0, 0 }, { -1, 2794, 2773, 0, 0 },
			{ 2, 2692, 3712, 0, 3 }, { 2, 2688, 3712, 0, 1 }, { 4483, 3081, 3496, 0, 1 }, { 4483, 3081, 3495, 0, 1 },
			{ 409, 3244, 9521, 0, 2 }, { 6552, 2930, 2715, 0, 3 }, { 410, 2925, 2720, 0, 3 },
			{ 29942, 3066, 2866, 0, 2 }, { 411, 2930, 2725, 0, 3 }, { 11758, 3019, 9740, 0, 0 },
			{ 11758, 3020, 9739, 0, 1 }, { 11758, 3019, 9738, 0, 2 }, { 11758, 3018, 9739, 0, 3 },
			{ 11933, 3028, 9739, 0, 1 }, { 11933, 3032, 9737, 0, 2 }, { 11933, 3032, 9735, 0, 0 },
			{ 11933, 3035, 9742, 0, 3 }, { 11933, 3034, 9739, 0, 0 }, { 11936, 3028, 9737, 0, 1 },
			{ 11936, 3029, 9734, 0, 2 }, { 11936, 3031, 9739, 0, 0 }, { 11936, 3032, 9741, 0, 3 },
			{ 11936, 3035, 9734, 0, 0 }, { 11954, 3037, 9739, 0, 1 }, { 11954, 3037, 9735, 0, 2 },
			{ 11954, 3037, 9733, 0, 0 }, { 11954, 3039, 9741, 0, 3 }, { 11954, 3039, 9738, 0, 0 },
			{ 11963, 3039, 9733, 0, 1 }, { 11964, 3040, 9732, 0, 2 }, { 11965, 3042, 9734, 0, 0 },
			{ 11965, 3044, 9737, 0, 3 }, { 11963, 3042, 9739, 0, 0 }, { 11963, 3045, 9740, 0, 1 },
			{ 11965, 3043, 9742, 0, 2 }, { 11964, 3045, 9744, 0, 0 }, { 11965, 3048, 9747, 0, 3 },
			{ 11951, 3048, 9743, 0, 0 }, { 11951, 3049, 9740, 0, 1 }, { 11951, 3047, 9737, 0, 2 },
			{ 11951, 3050, 9738, 0, 0 }, { 11951, 3052, 9739, 0, 3 }, { 11951, 3051, 9735, 0, 0 },
			{ 11947, 3049, 9735, 0, 1 }, { 11947, 3049, 9734, 0, 2 }, { 11947, 3047, 9733, 0, 0 },
			{ 11947, 3046, 9733, 0, 3 }, { 11947, 3046, 9735, 0, 0 }, { 11941, 3053, 9737, 0, 1 },
			{ 11939, 3054, 9739, 0, 2 }, { 11941, 3053, 9742, 0, 0 }, { 14859, 3038, 9748, 0, 3 },
			{ 14859, 3044, 9753, 0, 0 }, { 14859, 3048, 9754, 0, 1 }, { 14859, 3054, 9746, 0, 2 },
			{ 4306, 3026, 9741, 0, 0 }, { 6189, 3022, 9742, 0, 1 }, { 172, 3234, 9509, 0, 2 }, { 75, 2914, 3452, 0, 2 },
			{ 75, 2914, 3452, 0, 2 },
			{ 10091, 2352, 3703, 0, 2 }, { 11758, 3449, 3722, 0, 0 }, { 11758, 3450, 3722, 0, 0 },
			{ 50547, 3445, 3717, 0, 3 }, { 2465, 3035, 2844, 0, 0 }, { -1, 3090, 3496, 0, 0 }, { -1, 3090, 3494, 0, 0 },
			{ -1, 3092, 3496, 0, 0 }, { 35970, 2473, 3422, 1, 0 }, { 2302, 2535, 3547, 1, 0 }, { -1, 3659, 3508, 0, 0 },
			{ 4053, 3660, 3508, 0, 0 }, { 20211, 2538, 3545, 0, 0 }, { 4051, 3659, 3508, 0, 0 },
			{ 1, 3649, 3506, 0, 0 }, { 1, 3650, 3506, 0, 0 }, { 1, 3651, 3506, 0, 0 }, { 1, 3652, 3506, 0, 0 },
			{ 8702, 3423, 2911, 0, 0 }, { 47180, 3422, 2918, 0, 0 }, { 11356, 3418, 2917, 0, 1 },
			{ -1, 2860, 9734, 0, 1 }, { -1, 2857, 9736, 0, 1 }, { 664, 2859, 9742, 0, 1 }, { 1167, 2860, 9742, 0, 1 },
			{ 5277, 3691, 3465, 0, 2 }, { 5277, 3690, 3465, 0, 2 }, { 5277, 3688, 3465, 0, 2 },
			{ 5277, 3687, 3465, 0, 2 }, { 114, 3093, 3933, 0, 0 }, { 5276, 2025, 4747, 0, 0 },
			{ 5276, 2024, 4747, 0, 0 }, { 5276, 2022, 4747, 0, 0 }, { 5276, 2020, 4747, 0, 0 },
			{ 5276, 2019, 4747, 0, 0 },
			{2156, 2655, 10020, 0, 1},{2156, 2660, 10015, 0, 2},{2156, 2655, 10011, 0, 3},{2156, 2651, 10015, 0, 0},
			/*
			 * Edgeville
			 */
			{ 26972, 3097, 3495, 0, 0 }, { 26972, 3095, 3493, 0, 0 }, { 26972, 3095, 3491, 0, 0 },
			{ 26814, 3108, 3500, 0, 0 }, { 26969, 3091, 3499, 0, 0 },
			/*
			 * Donor Zone
			 */
			{ 409, 2019, 4753, 0, 2 }, { 411, 2016, 4749, 0, 1 }, { 6552, 2028, 4749, 0, 3 }, { 410, 2025, 4752, 0, 0 },
			{ -1, 2023, 4758, 0, 0 }, { -1, 2021, 4758, 0, 0 }, { -1, 2022, 4758, 0, 0 }, { 1306, 2018, 4760, 0, 0 },
			{ 1306, 2018, 4763, 0, 0 }, { 14859, 2026, 4763, 0, 0 }, { 14859, 2026, 4762, 0, 0 },
			{ 14859, 2026, 4761, 0, 0 }, { 14859, 2026, 4760, 0, 0 }, { 817, 2024, 4758, 0, 0 },
			{ 817, 2020, 4758, 0, 0 },
			/*
			 * Deluxe Donor Zone
			 */
			{ 8749, 2341, 9630, 0, 2 }, { 411, 2348, 9622, 0, 3 }, { 409, 2340, 9617, 0, 0 },
			{ 6552, 2314, 9623, 0, 1 }, { 7100, 2321, 9622, 0, 2 }, { 410, 2346, 9618, 0, 0 }, { -1, 2321, 9629, 0, 0 },
			{ -1, 2323, 9629, 0, 0 }, { -1, 2322, 9632, 0, 0 },
			/*
			 * Sponsor Zone
			 */
			{ 13493, 3333, 3333, 0, 1 },
			{2471, 2340, 4748, 0, 1},

			/*PORTALS */
			{2274, 3174, 3030, 0, 0}, {2274, 3168, 3031, 0, 0}, {2274, 1888, 4822, 0, 0},
			{2274, 1894, 4822, 0, 0}, {2274, 1952, 4823, 0, 0},

			{2274, 2016, 4822, 0, 0}, {2274, 2022, 4825, 0, 0}, {2274, 2592, 4631, 0, 0},
			{2274, 2598, 4633, 0, 0}, {2274, 2016, 4503, 0, 0},

			{2274, 2784, 3863, 0, 0}, {2274, 2790, 3865, 0, 0},{2274, 2272, 5017, 0, 0},
			{2274, 2278, 5018, 0, 0},{2274, 2400, 5017, 0, 0},

			{13389, 3174, 3028, 0, 1},{13389, 3168, 3033, 0, 1},{13389, 1888, 4820, 0, 1},
			{13389, 1894, 4825, 0, 1},{13389, 1952, 4825, 0, 1},

			{13389, 2016, 4824, 0, 1},{13389, 2022, 4822, 0, 1},{13389, 2592, 4629, 0, 1},
			{13389, 2598, 4630, 0, 1},{13389, 2016, 4501, 0, 1},

			{13389, 2784, 3861, 0, 1},{13389, 2790, 3862, 0, 1},{13389, 2272, 5015, 0, 1},
			{13389, 2278, 5014, 0, 1},{13389, 2400, 5015, 0, 1},

			{13389, 2406, 5017, 0, 1}, {13389, 2022, 4505, 0, 1}, {13389, 1958, 4825, 0, 1},

			// WILLOW TREE FIX
			{ 5553, 2719, 3505, 0, 0 }, { 5552, 2712, 3508, 0, 0 }, { 5553, 2711, 3511, 0, 0 },
			{ 5552, 2709, 3510, 0, 0 }, { 5551, 2708, 3513, 0, 0 }, { 5551, 2704, 3506, 0, 0 },
			
			//NEW POINTZONE
			{ 2213, 1951, 5024, 0, 1 },{ 2213, 1951, 5023, 0, 1 },{ 2213, 1951, 5022, 0, 1 },
			{ 2213, 1953, 5024, 0, 1 },{ 2213, 1953, 5023, 0, 1 },{ 2213, 1953, 5022, 0, 1 },
			{ 2213, 1952, 5024, 0, 0 },{ 2213, 1952, 5022, 0, 0 },
			
			//AFK SKILL ZONE
			{57164, 3231, 9524, 0, 0},
			
			{16604, 3227, 9524, 0, 0},{2026, 3236, 9524, 0, 0},

	};

	/**
	 * Contains
	 * 
	 * @param ObjectId
	 *            - The object ID to spawn
	 * @param absX
	 *            - The X position of the object to spawn
	 * @param absY
	 *            - The Y position of the object to spawn
	 * @param Z
	 *            - The Z position of the object to spawn
	 * @param face
	 *            - The position the object will face
	 */

	// Objects that are handled by the server on regionchange
	private static final int[][] CUSTOM_OBJECTS_SPAWNS = { { 2274, 3652, 3488, 0, 0 },
			/** Jail Start */
			{ 12269, 3093, 3933, 0, 0 }, { 1864, 3093, 3932, 0, 1 }, // Cell 1
			{ 1864, 3094, 3932, 0, 1 }, { 1864, 3095, 3932, 0, 1 }, { 1864, 3096, 3932, 0, 1 },
			{ 1864, 3097, 3932, 0, 1 }, { 1864, 3097, 3931, 0, 2 }, { 1864, 3097, 3930, 0, 2 },
			{ 1864, 3097, 3929, 0, 2 }, { 1864, 3097, 3928, 0, 3 }, { 1864, 3096, 3928, 0, 3 },
			{ 1864, 3095, 3928, 0, 3 }, { 1864, 3094, 3928, 0, 3 }, { 1864, 3093, 3928, 0, 3 },
			{ 1864, 3093, 3929, 0, 4 }, { 1864, 3093, 3930, 0, 4 }, { 1864, 3093, 3931, 0, 4 },
			{ 1864, 3098, 3932, 0, 1 }, // Cell 2
			{ 1864, 3099, 3932, 0, 1 }, { 1864, 3100, 3932, 0, 1 }, { 1864, 3101, 3932, 0, 1 },
			{ 1864, 3102, 3932, 0, 1 }, { 1864, 3102, 3931, 0, 2 }, { 1864, 3102, 3930, 0, 2 },
			{ 1864, 3102, 3929, 0, 2 }, { 1864, 3102, 3928, 0, 3 }, { 1864, 3101, 3928, 0, 3 },
			{ 1864, 3100, 3928, 0, 3 }, { 1864, 3099, 3928, 0, 3 }, { 1864, 3098, 3928, 0, 3 },
			{ 1864, 3098, 3929, 0, 4 }, { 1864, 3098, 3930, 0, 4 }, { 1864, 3098, 3931, 0, 4 },
			{ 1864, 3093, 3939, 0, 1 }, // Cell 3
			{ 1864, 3094, 3939, 0, 1 }, { 1864, 3095, 3939, 0, 1 }, { 1864, 3096, 3939, 0, 1 },
			{ 1864, 3097, 3939, 0, 1 }, { 1864, 3097, 3938, 0, 2 }, { 1864, 3097, 3937, 0, 2 },
			{ 1864, 3097, 3936, 0, 2 }, { 1864, 3097, 3935, 0, 3 }, { 1864, 3096, 3935, 0, 3 },
			{ 1864, 3095, 3935, 0, 3 }, { 1864, 3094, 3935, 0, 3 }, { 1864, 3093, 3935, 0, 3 },
			{ 1864, 3093, 3936, 0, 4 }, { 1864, 3093, 3937, 0, 4 }, { 1864, 3093, 3938, 0, 4 },
			{ 1864, 3098, 3939, 0, 1 }, // Cell 4
			{ 1864, 3099, 3939, 0, 1 }, { 1864, 3100, 3939, 0, 1 }, { 1864, 3101, 3939, 0, 1 },
			{ 1864, 3102, 3939, 0, 1 }, { 1864, 3102, 3938, 0, 2 }, { 1864, 3102, 3937, 0, 2 },
			{ 1864, 3102, 3936, 0, 2 }, { 1864, 3102, 3935, 0, 3 }, { 1864, 3101, 3935, 0, 3 },
			{ 1864, 3100, 3935, 0, 3 }, { 1864, 3099, 3935, 0, 3 }, { 1864, 3098, 3935, 0, 3 },
			{ 1864, 3098, 3936, 0, 4 }, { 1864, 3098, 3937, 0, 4 }, { 1864, 3098, 3938, 0, 4 },
			{ 1864, 3103, 3932, 0, 1 }, // Cell 5
			{ 1864, 3104, 3932, 0, 1 }, { 1864, 3105, 3932, 0, 1 }, { 1864, 3106, 3932, 0, 1 },
			{ 1864, 3107, 3932, 0, 1 }, { 1864, 3107, 3931, 0, 2 }, { 1864, 3107, 3930, 0, 2 },
			{ 1864, 3107, 3929, 0, 2 }, { 1864, 3107, 3928, 0, 3 }, { 1864, 3106, 3928, 0, 3 },
			{ 1864, 3105, 3928, 0, 3 }, { 1864, 3104, 3928, 0, 3 }, { 1864, 3103, 3928, 0, 3 },
			{ 1864, 3103, 3929, 0, 4 }, { 1864, 3103, 3930, 0, 4 }, { 1864, 3103, 3931, 0, 4 },
			{ 1864, 3108, 3932, 0, 1 }, // Cell 6
			{ 1864, 3109, 3932, 0, 1 }, { 1864, 3110, 3932, 0, 1 }, { 1864, 3111, 3932, 0, 1 },
			{ 1864, 3112, 3932, 0, 1 }, { 1864, 3112, 3931, 0, 2 }, { 1864, 3112, 3930, 0, 2 },
			{ 1864, 3112, 3929, 0, 2 }, { 1864, 3112, 3928, 0, 3 }, { 1864, 3111, 3928, 0, 3 },
			{ 1864, 3110, 3928, 0, 3 }, { 1864, 3109, 3928, 0, 3 }, { 1864, 3108, 3928, 0, 3 },
			{ 1864, 3108, 3929, 0, 4 }, { 1864, 3108, 3930, 0, 4 }, { 1864, 3108, 3931, 0, 4 },
			{ 1864, 3103, 3939, 0, 1 }, // Cell 7
			{ 1864, 3104, 3939, 0, 1 }, { 1864, 3105, 3939, 0, 1 }, { 1864, 3106, 3939, 0, 1 },
			{ 1864, 3107, 3939, 0, 1 }, { 1864, 3107, 3938, 0, 2 }, { 1864, 3107, 3937, 0, 2 },
			{ 1864, 3107, 3936, 0, 2 }, { 1864, 3107, 3935, 0, 3 }, { 1864, 3106, 3935, 0, 3 },
			{ 1864, 3105, 3935, 0, 3 }, { 1864, 3104, 3935, 0, 3 }, { 1864, 3103, 3935, 0, 3 },
			{ 1864, 3103, 3936, 0, 4 }, { 1864, 3103, 3937, 0, 4 }, { 1864, 3103, 3938, 0, 4 },
			{ 1864, 3108, 3939, 0, 1 }, // Cell 8
			{ 1864, 3109, 3939, 0, 1 }, { 1864, 3110, 3939, 0, 1 }, { 1864, 3111, 3939, 0, 1 },
			{ 1864, 3112, 3939, 0, 1 }, { 1864, 3112, 3938, 0, 2 }, { 1864, 3112, 3937, 0, 2 },
			{ 1864, 3112, 3936, 0, 2 }, { 1864, 3112, 3935, 0, 3 }, { 1864, 3111, 3935, 0, 3 },
			{ 1864, 3110, 3935, 0, 3 }, { 1864, 3109, 3935, 0, 3 }, { 1864, 3108, 3935, 0, 3 },
			{ 1864, 3108, 3936, 0, 4 }, { 1864, 3108, 3937, 0, 4 }, { 1864, 3108, 3938, 0, 4 },
			{ 1864, 3113, 3932, 0, 1 }, // Cell 9
			{ 1864, 3114, 3932, 0, 1 }, { 1864, 3115, 3932, 0, 1 }, { 1864, 3116, 3932, 0, 1 },
			{ 1864, 3117, 3932, 0, 1 }, { 1864, 3117, 3931, 0, 2 }, { 1864, 3117, 3930, 0, 2 },
			{ 1864, 3117, 3929, 0, 2 }, { 1864, 3117, 3928, 0, 3 }, { 1864, 3116, 3928, 0, 3 },
			{ 1864, 3115, 3928, 0, 3 }, { 1864, 3114, 3928, 0, 3 }, { 1864, 3113, 3928, 0, 3 },
			{ 1864, 3113, 3929, 0, 4 }, { 1864, 3113, 3930, 0, 4 }, { 1864, 3113, 3931, 0, 4 },
			{ 1864, 3113, 3939, 0, 1 }, // Cell 10
			{ 1864, 3114, 3939, 0, 1 }, { 1864, 3115, 3939, 0, 1 }, { 1864, 3116, 3939, 0, 1 },
			{ 1864, 3117, 3939, 0, 1 }, { 1864, 3117, 3938, 0, 2 }, { 1864, 3117, 3937, 0, 2 },
			{ 1864, 3117, 3936, 0, 2 }, { 1864, 3117, 3935, 0, 3 }, { 1864, 3116, 3935, 0, 3 },
			{ 1864, 3115, 3935, 0, 3 }, { 1864, 3114, 3935, 0, 3 }, { 1864, 3113, 3935, 0, 3 },
			{ 1864, 3113, 3936, 0, 4 }, { 1864, 3113, 3937, 0, 4 }, { 1864, 3113, 3938, 0, 4 },
			// bank at ::gamble
			{ 26193, 3054, 3376, 0, 1 },
			//SZONE
			{ 6552, 3211, 3119, 0, 2},
			{ 8792, 3205, 3119, 0, 1 },
			{ 8749, 3224, 3116, 0, 3 },
			{ 411, 3223, 3121, 0, 2 },
			{ 410, 3220, 3127, 0, 1 },
			{ 409, 3214, 3130, 0, 1 },
			{ 7100, 3214, 3119, 0, 1 },
			{ 26193, 3214, 3117, 0, 1 }, 
			{ -1, 2369, 4959, 0, 0 },
			{ 1864, 3046, 3372, 0, 1 },
			{ 1864, 3045, 3372, 0, 1 },
			{2471, 2340, 4748, 0, 1},
			{2472, 2344, 4762, 0, 1},
			{2469, 2358, 4757, 0, 1},
			{2470, 2358, 4739, 0, 1},
			{11232, 2537, 4759, 0, 1},
			{11339, 2537, 4758, 0, 3},
	
	
	
	};
	public static void deleteGlobalObject(GameObject object) {
		handleList(object, "delete");
		World.deregister(object);
	}

	public static void deleteGlobalObjectWithinDistance(GameObject object) {
		for (Player player : World.getPlayers()) {
			if (player == null)
				continue;
			if (object.getPosition().isWithinDistance(player.getPosition(), DISTANCE_SPAWN)) {
				deleteObject(player, object);
			}
		}
	}

	public static void deleteObject(Player p, GameObject object) {
		p.getPacketSender().sendObjectRemoval(object);
		if (RegionClipping.objectExists(object)) {
			RegionClipping.removeObject(object);
		}
	}

	public static GameObject getGameObject(Position pos) {
		for (GameObject objects : CUSTOM_OBJECTS) {
			if (objects != null && objects.getPosition().equals(pos)) {
				return objects;
			}
		}
		return null;
	}

	public static void globalFiremakingTask(final GameObject fire, final Player player, final int cycles) {
		spawnGlobalObject(fire);
		TaskManager.submit(new Task(cycles) {
			@Override
			public void execute() {
				deleteGlobalObject(fire);
				if (player.getInteractingObject() != null && player.getInteractingObject().getId() == 2732) {
					player.setInteractingObject(null);
				}
				this.stop();
			}

			@Override
			public void stop() {
				setEventRunning(false);
				GroundItemManager.spawnGroundItem(player,
						new GroundItem(new Item(592), fire.getPosition(), player.getUsername(), false, 150, true, 100));
			}
		});
	}

	public static void globalObjectRemovalTask(final GameObject object, final int cycles) {
		spawnGlobalObject(object);
		TaskManager.submit(new Task(cycles) {
			@Override
			public void execute() {
				deleteGlobalObject(object);
				this.stop();
			}
		});
	}

	public static void globalObjectRespawnTask(final GameObject tempObject, final GameObject mainObject,
			final int cycles) {
		deleteGlobalObject(mainObject);
		spawnGlobalObject(tempObject);
		TaskManager.submit(new Task(cycles) {
			@Override
			public void execute() {
				deleteGlobalObject(tempObject);
				spawnGlobalObject(mainObject);
				this.stop();
			}
		});
	}

	private static void handleList(GameObject object, String handleType) {
		switch (handleType.toUpperCase()) {
		case "DELETE":
			for (GameObject objects : CUSTOM_OBJECTS) {
				if (objects.getId() == object.getId() && object.getPosition().equals(objects.getPosition())) {
					CUSTOM_OBJECTS.remove(objects);
				}
			}
			break;
		case "ADD":
			if (!CUSTOM_OBJECTS.contains(object)) {
				CUSTOM_OBJECTS.add(object);
			}
			break;
		case "EMPTY":
			CUSTOM_OBJECTS.clear();
			break;
		}
	}

	public static void handleRegionChange(Player p) {
		for (GameObject object : CUSTOM_OBJECTS) {
			if (object == null)
				continue;
			if (object.getPosition().isWithinDistance(p.getPosition(), DISTANCE_SPAWN)) {
				spawnObject(p, object);
			}
		}
	}

	public static void init() {
		for (int i = 0; i < CLIENT_OBJECTS.length; i++) {
			int id = CLIENT_OBJECTS[i][0];
			int x = CLIENT_OBJECTS[i][1];
			int y = CLIENT_OBJECTS[i][2];
			int z = CLIENT_OBJECTS[i][3];
			int face = CLIENT_OBJECTS[i][4];
			GameObject object = new GameObject(id, new Position(x, y, z));
			object.setFace(face);
			RegionClipping.addObject(object);
		}
		for (int i = 0; i < CUSTOM_OBJECTS_SPAWNS.length; i++) {
			int id = CUSTOM_OBJECTS_SPAWNS[i][0];
			int x = CUSTOM_OBJECTS_SPAWNS[i][1];
			int y = CUSTOM_OBJECTS_SPAWNS[i][2];
			int z = CUSTOM_OBJECTS_SPAWNS[i][3];
			int face = CUSTOM_OBJECTS_SPAWNS[i][4];
			GameObject object = new GameObject(id, new Position(x, y, z));
			object.setFace(face);
			CUSTOM_OBJECTS.add(object);
			World.register(object);
		}
	}

	public static boolean objectExists(Position pos) {
		return getGameObject(pos) != null;
	}

	public static void objectRespawnTask(Player p, final GameObject tempObject, final GameObject mainObject,
			final int cycles) {
		deleteObject(p, mainObject);
		spawnObject(p, tempObject);
		TaskManager.submit(new Task(cycles) {
			@Override
			public void execute() {
				deleteObject(p, tempObject);
				spawnObject(p, mainObject);
				this.stop();
			}
		});
	}

	public static void spawnGlobalObject(GameObject object) {
		handleList(object, "add");
		World.register(object);
	}

	public static void spawnGlobalObjectWithinDistance(GameObject object) {
		for (Player player : World.getPlayers()) {
			if (player == null)
				continue;
			if (object.getPosition().isWithinDistance(player.getPosition(), DISTANCE_SPAWN)) {
				spawnObject(player, object);
			}
		}
	}

	public static void spawnObject(Player p, GameObject object) {
		if (object.getId() != -1) {
			p.getPacketSender().sendObject(object);
			if (!RegionClipping.objectExists(object)) {
				RegionClipping.addObject(object);
			}
		} else {
			deleteObject(p, object);
		}
	}
}
