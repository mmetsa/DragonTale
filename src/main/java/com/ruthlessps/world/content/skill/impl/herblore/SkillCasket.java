package com.ruthlessps.world.content.skill.impl.herblore;

import java.util.List;

import com.ruthlessps.model.GroundItem;
import com.ruthlessps.model.Item;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.entity.impl.GroundItemManager;
import com.ruthlessps.world.entity.impl.player.Player;

public class SkillCasket {
	
	static int rewards[] = {};
	static int herbs[] = {200, 202, 204, 206, 208, 3050, 210, 212, 214, 3052, 216, 2486, 218, 220, 250, 252, 254, 256, 258, 2999, 260, 262, 264, 3001, 266, 2482, 268, 270};
	static int seeds[] = {5291, 5292, 5293, 5294, 5295, 5296, 5297, 5298, 5299, 5300, 5301, 5302, 5303, 5304};
	static int ores[] = {437, 439, 441, 443, 445, 448, 450, 452};
	static int bars[] = {2350, 2352, 2354, 2356, 2358, 2360, 2362, 2364};
	static int thieve[] = {2, 18200, 15010, 17402, 1390, 11999};
	static int gems[] = {1626, 1628, 1630, 1624, 1622, 1620, 1618, 1632, 6572, 1610, 1612, 1614, 1608, 1606, 1604, 1602, 1616, 6574};
	static int logs[] = {1512, 1514, 1516, 1518, 1520, 1522};
	static int jars[] = {11239, 11241, 11243, 11245, 11247, 11249, 11251, 11253, 11255, 11257, 15518, 15516, 15514};
	static int fish[] = {318, 322, 328, 332, 336, 342, 346, 360, 364, 372, 378, 384, 390, 396, 15273};
	static int ess[] = {1437, 7937};
	
	
	public static int getAmt(int[] list) {
		if(list.equals(herbs)) {
			return 100;
		} else if(list.equals(seeds)) {
			return 50;
		} else if(list.equals(ores)) {
			return 100;
		} else if(list.equals(bars)) {
			return 50;
		} else if(list.equals(thieve)) {
			return 500;
		} else if(list.equals(gems)) {
			return 200;
		} else if(list.equals(logs)) {
			return 300;
		} else if(list.equals(jars)) {
			return 50;
		} else if(list.equals(fish)) {
			return 300;
		} else if(list.equals(ess)) {
			return 5000;
		}
		return 100;
	}
	public static int[] getRewards(int s) {
		switch(s) {
		case 0:
			return herbs;
		case 1:
			return seeds;
		case 2:
			return ores;
		case 3:
			return bars;
		case 4:
			return thieve;
		case 5:
			return gems;
		case 6:
			return logs;
		case 7:
			return jars;
		case 8:
			return fish;
		case 9:
			return ess;
		}
		return thieve;
	}
	public static void openCasket(Player player, int times) {
		int s = 0;
		if(player.getInventory().getFreeSlots() < 4) {
			player.getPA().sendMessage("You need at least 4 empty inventory slots!");
			return;
		}
		for(int i = 0; i < times; i++) {
			s = Misc.random(9);
			player.getInventory().add(getRewards(s)[Misc.random(getRewards(s).length-1)],Misc.random(getAmt(getRewards(s))));
		}
		player.getInventory().delete(casket, 1);
		player.getPA().sendMessage("You open the casket and receive a reward!");
		return;
	}
	
	
	
	static int casket = 7956;
	public static void giveCasket(Player player, int chance) {
		int ch = Misc.random(chance);
		if(ch == 1) {
			if(player.getInventory().getFreeSlots() >= 1) {
				player.getInventory().add(casket, 1);
				player.getPA().sendMessage("You receive a casket!");
			} else {
				GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(7956, 1),
						player.getPosition().copy(), player.getUsername(), false, 150, false, 200));
				player.getPA().sendMessage("You receive a casket, it has been dropped under you!");
			}
		}
		
		
	}
	
	
	
	
	
	
	
}
