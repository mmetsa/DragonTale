package com.ruthlessps.engine.task.impl;

import com.ruthlessps.world.entity.impl.player.Player;

public class SetEffects {

	public static boolean burst(Player player) {
		return player.getEquipment().containsAll(1840, 1841, 1842, 18490, 2912, 3298);
	}
	
	public static boolean cryptic(Player player) {
		return player.getEquipment().containsAll(1843,1845,1846,1848,1850,20652);
	}
	
	public static boolean tyrant(Player player) {
		return player.getEquipment().containsAll(8816,8817,8818,8820,8821,8822);
	}
	
	public static boolean insidious(Player player) {
		return player.getEquipment().containsAll(7020,7021,7022,7023,7024);
	}
	
	public static boolean subjugation(Player player) {
		return player.getEquipment().containsAll(3307,3309,3310,3311,3312,3313);
	}
	
	public static boolean skeletal(Player player) {
		return player.getEquipment().containsAll(9921, 9922,9923,9924,9925, 15352);
	}
	public static boolean fooo(Player player) {
		return player.getEquipment().containsAll(6485, 6486, 6487, 10069, 8820, 8821);
	}
	
	public static boolean battlemage(Player player) {
		return (player.getEquipment().containsAll(14114, 14115, 14116, 15065, 17171));
	}
}
