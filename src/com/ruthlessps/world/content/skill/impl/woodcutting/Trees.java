package com.ruthlessps.world.content.skill.impl.woodcutting;

import java.util.HashMap;
import java.util.Map;

public enum Trees {
	NORMAL(1, 3655, 1511,new int[] { 1276, 1277, 1278, 1279, 1280, 1282, 1283, 1284, 1285, 1286, 1289, 1290, 1291, 1315, 1316, 1318,1319, 1330, 1331, 1332, 1365, 1383, 1384, 3033, 3034, 3035, 3036, 3881, 3882, 3883, 5902, 5903,5904 },4, false), 
	ACHEY(1, 3655, 2862, new int[] { 2023 }, 4, false), OAK(15, 4684, 1521, new int[] { 1281, 3037 },5, true), 
	WILLOW(30, 6346, 1519, new int[] { 1308, 5551, 5552, 5553 }, 6, true), 
	TEAK(35, 6544,6333, new int[] { 9036 }, 7, true), 
	DRAMEN(36, 6581, 771, new int[] { 1292 }, 7,true), 
	MAPLE(45, 7935, 1517, new int[] { 1307, 4677 }, 7, true), 
	MAHOGANY(50, 8112,6332, new int[] { 9034 }, 7, true), 
	YEW(60, 8417, 1515, new int[] { 1309 },8, true), 
	MAGIC(75, 9127, 1513, new int[] { 1306 }, 9, true), 
	BLOOD(99, 15000, 993, new int[] { 990000 }, 10, true),
	DREAM(50, 100, 7406, new int[] {16604}, 15, true);

	private static final Map<Integer, Trees> tree = new HashMap<Integer, Trees>();
	static {
		for (Trees t : Trees.values()) {
			for (int obj : t.objects) {
				tree.put(obj, t);
			}
		}
	}

	public static Trees forId(int id) {
		return tree.get(id);
	}

	private int[] objects;

	private int req, xp, log, ticks;

	private boolean multi;

	private Trees(int req, int xp, int log, int[] obj, int ticks, boolean multi) {
		this.req = req;
		this.xp = xp;
		this.log = log;
		this.objects = obj;
		this.ticks = ticks;
		this.multi = multi;
	}

	public int getReq() {
		return req;
	}

	public int getReward() {
		return log;
	}

	public int getTicks() {
		return ticks;
	}

	public int getXp() {
		return xp;
	}

	public boolean isMulti() {
		return multi;
	}
}