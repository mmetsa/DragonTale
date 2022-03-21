package com.ruthlessps.world.content.skill.impl.slayer;

import com.ruthlessps.model.Position;
import com.ruthlessps.util.Misc;

/**
 * @author Gabriel Hannason
 */

public enum SlayerTasks {

	NO_TASK(null, -1, null, -1, null),

	/**
	 * Easy tasks
	 */
	
	MORTY(SlayerMaster.BEGINNER_MASTER, 3711, " ", 3000, new Position(3306, 4975)), 
	RICK(SlayerMaster.BEGINNER_MASTER, 3000, " ", 3000, new Position(3298, 4969)),
	MEWTWO(SlayerMaster.BEGINNER_MASTER, 508, "", 1500, new Position(1952, 5050)),
	CHARMELEON(SlayerMaster.BEGINNER_MASTER, 509, "", 4000, new Position(1951, 5040)),
	BISHARP(SlayerMaster.BEGINNER_MASTER, 1860, "", 5000, new Position(1979, 5022)),
	ALIEN_DEMON(SlayerMaster.BEGINNER_MASTER, 7235, "", 6500, new Position(1970, 5022)),
	

	/**
	 * Medium tasks
	 */
	CELESTIAL_DRAGON(SlayerMaster.INTERMEDIATE_MASTER, 3712, "", 8000, new Position(1929, 5023)),
	SKELETAL_DRAGON(SlayerMaster.INTERMEDIATE_MASTER, 3712, "", 10000, new Position(1929, 5023)),
	WATER_BEAST(SlayerMaster.INTERMEDIATE_MASTER, 1388, "", 13000, new Position(1950, 5004)),
	SUN_BEAST(SlayerMaster.INTERMEDIATE_MASTER, 1871, "", 13000, new Position(1950, 5004)),
	/**
	 * Hard tasks
	 */
	
	
	/**
	 * Elite
	 */
	
	CAMO_TORVA_BOSS(SlayerMaster.ELITE_MASTER, 515, "", 25000, new Position(2541, 5779)),
	WINTER_TORVA_BOSS(SlayerMaster.ELITE_MASTER, 517, "", 30000, new Position(2541, 5779)),
	BLOODSHOT_TORVA_BOSS(SlayerMaster.ELITE_MASTER, 518, "", 35000, new Position(2541, 5779)),
	RAINBOW_TORVA_BOSS(SlayerMaster.ELITE_MASTER, 799, "", 40000, new Position(2541, 5779)),
	VORTEX_BOSS(SlayerMaster.ELITE_MASTER, 3705, "", 25000, new Position(2834, 9560)),
	LEFOSH(SlayerMaster.ELITE_MASTER, 6309, "", 55000, new Position(2721, 4905)),
	IKTOMI(SlayerMaster.ELITE_MASTER, 6307, "", 75000, new Position(2642, 4917, 2)),
	ZIVA(SlayerMaster.ELITE_MASTER, 4999, "", 75000, new Position(3417, 2983)),
	VORAGO(SlayerMaster.ELITE_MASTER, 514, "", 75000, new Position(2785, 10024)),
	MELEE_SHIELD_BOSS(SlayerMaster.ELITE_MASTER, 6770, "", 75000, new Position(2270, 4053)),
	RANGED_SHIELD_BOSS(SlayerMaster.ELITE_MASTER, 6771, "", 75000, new Position(2270, 4053)),
	MAGIC_SHIELD_BOSS(SlayerMaster.ELITE_MASTER, 6772, "", 75000, new Position(2270, 4053)),
	LEGIO(SlayerMaster.ELITE_MASTER, 7164, "", 75000, new Position(2903, 2708)),
	
	
	
	/**
	 * GOD MASTER TASKS
	 */
	DIABLO(SlayerMaster.GOD_MASTER, 11248, " ", 150000, new Position(2971, 9509)), 
	HEARTWRENCHER(SlayerMaster.GOD_MASTER, 6313, " ", 150000, new Position(1866, 4940, 2)),
	BANDOS_WARLORD(SlayerMaster.GOD_MASTER, 256, " ", 200000, new Position(3423, 9621)), 
	ZULRAH(SlayerMaster.GOD_MASTER, 2047, " ", 150000, new Position(2793, 3321)), 
	
	
	
	CAMO__TORVA(SlayerMaster.SUMONA, 515, "You can find them somewhere on the Slayer island.", 39500, new Position(2540, 5785)),
	PHOENIX(SlayerMaster.SUMONA, 8549, "You can find the Phoenix boss in Ape atoll!", 25000, new Position(2540, 5785)),
	DRYGORE_WARRIOR(SlayerMaster.SUMONA, 3706, "", 20000, new Position(2524, 4776)),
	WINTER__CAMO_TORVA(SlayerMaster.SUMONA, 517, "You can find them somewhere on the Slayer island.", 44500, new Position(2792, 3796)),
	GIANT_MOLE(SlayerMaster.SUMONA, 517, "You can find them somewhere on the Slayer island.", 44500, new Position(2792, 3796));
	;
	/*
	 * @param taskMaster
	 * 
	 * @param npcId
	 * 
	 * @param npcLocation
	 * 
	 * @param XP
	 * 
	 * @param taskPosition
	 */

	public static SlayerTasks forId(int id) {
		for (SlayerTasks tasks : SlayerTasks.values()) {
			if (tasks.ordinal() == id) {
				return tasks;
			}
		}
		return null;
	}

	public static int[] getNewTaskData(SlayerMaster master) {
		int slayerTaskId = 1, slayerTaskAmount = 20;
		int easyTasks = 0, mediumTasks = 0, hardTasks = 0, eliteTasks = 0, godTasks = 0;

		/*
		 * Calculating amount of tasks
		 */
		for (SlayerTasks task : SlayerTasks.values()) {
			if (task.getTaskMaster() == SlayerMaster.BEGINNER_MASTER)
				easyTasks++;
			else if (task.getTaskMaster() == SlayerMaster.INTERMEDIATE_MASTER)
				mediumTasks++;
			else if (task.getTaskMaster() == SlayerMaster.HEROIC_MASTER)
				hardTasks++;
			else if (task.getTaskMaster() == SlayerMaster.ELITE_MASTER)
				eliteTasks++;
			else if (task.getTaskMaster() == SlayerMaster.GOD_MASTER)
				godTasks++;
		}

		/*
		 * Getting a task
		 */
		if (master == SlayerMaster.BEGINNER_MASTER) {
			slayerTaskId = 1 + Misc.getRandom(easyTasks);
			if (slayerTaskId > easyTasks)
				slayerTaskId = easyTasks;
			slayerTaskAmount = 15 + Misc.getRandom(15);
		} else if (master == SlayerMaster.INTERMEDIATE_MASTER) {
			slayerTaskId = easyTasks + Misc.getRandom(mediumTasks);
			slayerTaskAmount = 12 + Misc.getRandom(13);
		} else if (master == SlayerMaster.HEROIC_MASTER) {
			slayerTaskId = 1 + easyTasks + mediumTasks + Misc.getRandom(hardTasks - 1);
			slayerTaskAmount = 10 + Misc.getRandom(15);
		} else if (master == SlayerMaster.ELITE_MASTER) {
			slayerTaskId = 1 + easyTasks + mediumTasks + hardTasks + Misc.getRandom(eliteTasks - 1);
			slayerTaskAmount = 2 + Misc.getRandom(7);
		} else if (master == SlayerMaster.GOD_MASTER) {
			slayerTaskId = 1 + easyTasks + mediumTasks + hardTasks + eliteTasks+ Misc.getRandom(godTasks - 1);
			slayerTaskAmount = 2 + Misc.getRandom(7);
			//31 enne godtaske
			
		}
		return new int[] { slayerTaskId, slayerTaskAmount };
	}

	private SlayerMaster taskMaster;
	private int npcId;
	private String npcLocation;
	private int XP;

	private Position taskPosition;

	private SlayerTasks(SlayerMaster taskMaster, int npcId, String npcLocation, int XP, Position taskPosition) {
		this.taskMaster = taskMaster;
		this.npcId = npcId;
		this.npcLocation = npcLocation;
		this.XP = XP;
		this.taskPosition = taskPosition;
	}

	public int getNpcId() {
		return this.npcId;
	}

	public String getNpcLocation() {
		return this.npcLocation;
	}

	public SlayerMaster getTaskMaster() {
		return this.taskMaster;
	}

	public Position getTaskPosition() {
		return this.taskPosition;
	}

	public int getXP() {
		return this.XP;
	}
}
