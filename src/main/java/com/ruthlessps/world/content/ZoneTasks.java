package com.ruthlessps.world.content;

import com.ruthlessps.model.GroundItem;
import com.ruthlessps.model.Item;
import com.ruthlessps.model.Position;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.entity.impl.GroundItemManager;
import com.ruthlessps.world.entity.impl.player.Player;

public class ZoneTasks {

	
	public enum ZoneData {

		TRAINING_ZONE("Training Zone", new String[] {"Kill 25 Ricks", "Bury 50 Train Bones", "Get a Morty Pet"}, new Item[] {new Item(13664, 25), new Item(3638), new Item(15251)}, new int[] {0, 25, 50, 1}),
		POINTZONE("Point Zone", new String[] {"Buy an Air Whip", "Kill 100 Aliens", "Get a Charmeleon Pet"}, new Item[] {new Item(15501, 3), new Item(6199, 50), new Item(17654, 2)}, new int[] {1, 1, 100, 1}),
		MYSTERY_BOX_ZONE("Mystery Box Zone", new String[] {"Collect 200 mystery boxes", "Collect 200 mystery keys", "Get a Unicorn Pet"}, new Item[] {new Item(17662, 10), new Item(17660, 10), new Item(17656, 10)}, new int[] {2, 200, 200, 1}),
		MINECRAFT_ZONE("Minecraft Zone", new String[] {"Get a Fire Stone", "Get an Earth Stone", "Get a Minecraft Player"}, new Item[] {new Item(6853), new Item(1009), new Item(6800)}, new int[] {3, 1, 1, 1}),
		MONEYBAG_ZONE("Money Bags", new String[] {"Enter 2nd Moneybag area", "Get a 5$ Scroll drop", "Kill a Giant Moneybag"}, new Item[] {new Item(6799), new Item(6800), new Item(6802)}, new int[] {4, 1, 1, 1}),
		YODA_ZONE("Yoda Zone", new String[] {"", "", ""}, new Item[] {new Item(13664, 25), new Item(3638), new Item(15251)}, new int[] {5, 25, 50, 1}),
		MARIO_ZONE("Mario Zone", new String[] {"", "", ""}, new Item[] {new Item(13664, 25), new Item(3638), new Item(15251)}, new int[] {6, 25, 50, 1}),
		KNUCKLES_ZONE("Knuckles Zone", new String[] {"", "", ""}, new Item[] {new Item(13664, 25), new Item(3638), new Item(15251)}, new int[] {7, 25, 50, 1}),
		DONKEY_KONG_ZONE("Donkey Kong Zone", new String[] {"", "", ""}, new Item[] {new Item(13664, 25), new Item(3638), new Item(15251)}, new int[] {8, 25, 50, 1})

		;
		
		
		
		ZoneData(String zoneName, String[] tasks, Item[] rewards, int[] progressData) {
			this.zoneName = zoneName;
			this.tasks = tasks;
			this.rewards = rewards;
			this.progressData = progressData;	
		}


		private String zoneName;
		private String[] tasks;
		private Item[] rewards;
		private int[] progressData;
	
	
	}	
	
	
	public static void doTaskProgress(Player player, ZoneData zone, int task, int amt) {
		if (player.getZoneTaskAttributes().getCompletion()[zone.ordinal()][task]) 
			return;
		int progressIndex = zone.progressData[0];
		int amountNeeded = task == 0 ? zone.progressData[1] : task == 1 ? zone.progressData[2] : zone.progressData[3];
		player.getZoneTaskAttributes().setProgress(progressIndex, task, player.getZoneTaskAttributes().getProgress()[progressIndex][task] + amt);
		if (player.getZoneTaskAttributes().getProgress()[progressIndex][task] >= amountNeeded) {
			finishZoneTask(player, zone, task);
		}
		//updateInterface(player);//TODO
	}
	
	private static void doZoneProgress(Player player, ZoneData zone) {
		doZoneProgress(player, zone, 1);
	}
	
	
	
	private static void doZoneProgress(Player player, ZoneData zone, int amt) {
		if (player.getZoneAttributes().getCompletion()[zone.ordinal()])
			return;
		int progressIndex = zone.progressData[0];
		int amountNeeded = 3;
		player.getZoneAttributes().setProgress(progressIndex, player.getZoneAttributes().getProgress()[progressIndex]+amt);
		if (player.getZoneAttributes().getProgress()[progressIndex] >= amountNeeded) {
			finishZone(player, zone);
		}
		//updateInterface(player);//TODO
		
	}
	
	private static void finishZone(Player player, ZoneData zone) {
		if (player.getZoneAttributes().getCompletion()[zone.ordinal()])
			return;
		player.getZoneAttributes().setCompletion(zone.ordinal(), true);
		player.getPacketSender().sendMessage("<col=339900>You have fully completed the " + Misc.formatText(zone.name().toLowerCase() + "."));
		player.getPA().sendMessage("Do ::zones to claim the reward.");

		switch (zone.name().toLowerCase()) {
			case "training_zone":
				if (player.getInventory().getFreeSlots() >= 1) {
					player.getInventory().add(new Item(13665, 10));
				} else {
					GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(13664, 10), new Position(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ()), player.getUsername(), false, 0, false, 0));
					player.getPacketSender().sendMessage("Your reward for completing the Zone has been placed on the floor.");
				}
				break;
		case "moneybag_zone":
			if (player.getInventory().getFreeSlots() >= 1) {
				player.getInventory().add(new Item(13664, 10));
			} else {
				GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(13664, 10), new Position(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ()), player.getUsername(), false, 0, false, 0));
				player.getPacketSender().sendMessage("Your reward for completing the Zone has been placed on the floor.");
			}
		break;
		}
	}
	
	private static void finishZoneTask(Player player, ZoneData zone, int task) {
		if (player.getZoneTaskAttributes().getCompletion()[zone.ordinal()][task])
			return;
		player.getZoneTaskAttributes().getCompletion()[zone.ordinal()][task] = true;
		player.getZoneTaskAttributes().getRewards()[zone.ordinal()][task] = true;
		player.getPacketSender().sendMessage("<col=339900>You have completed the task @red@" + Misc.formatText(zone.tasks[task].toLowerCase() + "."));
		player.getPA().sendMessage("Do ::zones to claim the reward.");
		doZoneProgress(player, zone);
	}

	private static void clearInterface(Player player) {
		int zoneAmt = 10;
		int d = 23007;
		int s=0,g=0;
		for(int i = 0; i < zoneAmt; i++) {
			for(int f = 0; f < 3; f++) {
				d++;
				player.getPA().sendItemOnInterface(d, 0, 1);
				g++;
			}
			s++;
			d+=16;
			g=0;
		}
		s=g=0;
		d = 23010;
		for(int i = 0; i < zoneAmt; i++) {
			for(int f = 0; f < 3; f++) {
				d++;
				player.getPA().sendString(d, "");
				g++;
			}
			g=0;
			d+=16;
			s++;
		}
		d = 23014;
		s=0;g=0;
		for(int i = 0; i < zoneAmt; i++) {
			player.getPA().sendString(d, "");
			s++;
			d+=19;
		}
		s=0;
		d=23014;
		for(int i = 0; i < zoneAmt; i++) {
			for(int h = 0; h < 3; h++) {
				d++;
				player.getPA().sendString(d, "");
				g++;
			}
			s++;
			d+=16;
			g=0;
		}
	}

	private static void showInterface(Player player) {
		int zoneAmt = ZoneData.values().length;
		int d = 23007;
		int s=0,g=0;
		for(int i = 0; i < zoneAmt; i++) {
			for(int f = 0; f < 3; f++) {
				d++;
				player.getPA().sendItemOnInterface(d, ZoneData.values()[s].rewards[g].getId(), ZoneData.values()[s].rewards[g].getAmount());
				g++;
			}
			s++;
			d+=16;
			g=0;
		}
		s=g=0;
		d = 23010;
		for(int i = 0; i < zoneAmt; i++) {
			for(int f = 0; f < 3; f++) {
				d++;
				player.getPA().sendString(d, ""+getPrecent(player, s, g)+"@gre@%");
				g++;
			}
			g=0;
			d+=16;
			s++;
		}
		d = 23014;
		s=0;g=0;
		for(int i = 0; i < zoneAmt; i++) {
			player.getPA().sendString(d, ""+ZoneData.values()[s].zoneName.toString());
			s++;
			d+=19;
		}
		s=0;
		d=23014;
		for(int i = 0; i < zoneAmt; i++) {
			for(int h = 0; h < 3; h++) {
				d++;
				player.getPA().sendString(d, ""+ZoneData.values()[s].tasks[g]);
				g++;
			}
			s++;
			d+=16;
			g=0;
		}
		player.getPA().sendInterface(23000);
	}

	public static void openInterface(Player player) {
		clearInterface(player);
		showInterface(player);
		
	}
	
	private static int getPrecent(Player player, int s, int g) {
		float current = player.getZoneTaskAttributes().progress[s][g];
		float total = ZoneData.values()[s].progressData[g+1];
		float precent = current/total*100;
		int p = Math.round(precent);
		if(p > 100) {
			p = 100;
		}
		return p;
	}







	public static void claimPrize(Player player, int button) {

		switch(button) {
			case 23021:
				if(player.getZoneTaskAttributes().getRewards()[0][0]) {
					player.getInventory().add(ZoneData.values()[0].rewards[0].getId(), ZoneData.values()[0].rewards[0].getAmount());
					player.getPA().sendMessage("You receive your reward!");
					player.getZoneTaskAttributes().setRewards(0, 0, false);
				}
				if(player.getZoneTaskAttributes().getRewards()[0][1]) {
					player.getInventory().add(ZoneData.values()[0].rewards[1].getId(), ZoneData.values()[0].rewards[1].getAmount());
					player.getPA().sendMessage("You receive your reward!");
					player.getZoneTaskAttributes().setRewards(0, 1, false);
				}
				if(player.getZoneTaskAttributes().getRewards()[0][2]) {
					player.getInventory().add(ZoneData.values()[0].rewards[2].getId(), ZoneData.values()[0].rewards[2].getAmount());
					player.getPA().sendMessage("You receive your reward!");
					player.getZoneTaskAttributes().setRewards(0, 2, false);
				}
				break;
			case 23040:
				if(player.getZoneTaskAttributes().getRewards()[1][0]) {
					System.out.println("Reach");
					player.getInventory().add(ZoneData.values()[1].rewards[0].getId(), ZoneData.values()[1].rewards[0].getAmount());
					player.getPA().sendMessage("You receive your reward!");
					player.getZoneTaskAttributes().setRewards(1, 0, false);
				}
				if(player.getZoneTaskAttributes().getRewards()[1][1]) {
					player.getInventory().add(ZoneData.values()[1].rewards[1].getId(), ZoneData.values()[1].rewards[1].getAmount());
					player.getPA().sendMessage("You receive your reward!");
					player.getZoneTaskAttributes().setRewards(1, 1, false);
				}
				if(player.getZoneTaskAttributes().getRewards()[1][2]) {
					player.getInventory().add(ZoneData.values()[1].rewards[2].getId(), ZoneData.values()[1].rewards[2].getAmount());
					player.getPA().sendMessage("You receive your reward!");
					player.getZoneTaskAttributes().setRewards(1, 2, false);
				}
				break;
			case 23059:
				if(player.getZoneTaskAttributes().getRewards()[2][0]) {
					player.getInventory().add(ZoneData.values()[2].rewards[0].getId(), ZoneData.values()[2].rewards[0].getAmount());
					player.getPA().sendMessage("You receive your reward!");
					player.getZoneTaskAttributes().setRewards(2, 0, false);
				}
				if(player.getZoneTaskAttributes().getRewards()[2][1]) {
					player.getInventory().add(ZoneData.values()[2].rewards[1].getId(), ZoneData.values()[2].rewards[1].getAmount());
					player.getPA().sendMessage("You receive your reward!");
					player.getZoneTaskAttributes().setRewards(2, 1, false);
				}
				if(player.getZoneTaskAttributes().getRewards()[2][2]) {
					player.getInventory().add(ZoneData.values()[2].rewards[2].getId(), ZoneData.values()[2].rewards[2].getAmount());
					player.getPA().sendMessage("You receive your reward!");
					player.getZoneTaskAttributes().setRewards(2, 2, false);
				}
				break;
			case 23078:
				if(player.getZoneTaskAttributes().getRewards()[3][0]) {
					player.getInventory().add(ZoneData.values()[3].rewards[0].getId(), ZoneData.values()[3].rewards[0].getAmount());
					player.getPA().sendMessage("You receive your reward!");
					player.getZoneTaskAttributes().setRewards(3, 0, false);
				}
				if(player.getZoneTaskAttributes().getRewards()[3][1]) {
					player.getInventory().add(ZoneData.values()[3].rewards[1].getId(), ZoneData.values()[3].rewards[1].getAmount());
					player.getPA().sendMessage("You receive your reward!");
					player.getZoneTaskAttributes().setRewards(3, 1, false);
				}
				if(player.getZoneTaskAttributes().getRewards()[3][2]) {
					player.getInventory().add(ZoneData.values()[3].rewards[2].getId(), ZoneData.values()[3].rewards[2].getAmount());
					player.getPA().sendMessage("You receive your reward!");
					player.getZoneTaskAttributes().setRewards(3, 2, false);
				}
				break;
			case 23097:
				if(player.getZoneTaskAttributes().getRewards()[4][0]) {
					player.getInventory().add(ZoneData.values()[4].rewards[0].getId(), ZoneData.values()[4].rewards[0].getAmount());
					player.getPA().sendMessage("You receive your reward!");
					player.getZoneTaskAttributes().setRewards(4, 0, false);
				}
				if(player.getZoneTaskAttributes().getRewards()[4][1]) {
					player.getInventory().add(ZoneData.values()[4].rewards[1].getId(), ZoneData.values()[4].rewards[1].getAmount());
					player.getPA().sendMessage("You receive your reward!");
					player.getZoneTaskAttributes().setRewards(4, 1, false);
				}
				if(player.getZoneTaskAttributes().getRewards()[4][2]) {
					player.getInventory().add(ZoneData.values()[4].rewards[2].getId(), ZoneData.values()[4].rewards[2].getAmount());
					player.getPA().sendMessage("You receive your reward!");
					player.getZoneTaskAttributes().setRewards(4, 2, false);
				}
				break;
		}
		
	}







	public static class ZoneAttributes { //ZONE 1/3 and 2/3 and 3/3

		public ZoneAttributes() {
		}

		/** ZONE TASKS **/
		private boolean[] completed = new boolean[100];
		private int[] progress = new int[100];
		public boolean[] getCompletion() {
			return this.completed;
		}
		void setCompletion(int index, boolean value) {
			this.completed[index] = value;
		}
		public int[] getProgress() {
			return this.progress;
		}
		void setProgress(int index, int value) {
			this.progress[index] = value;
		}
		public void setProgress(int[] progress) {
			this.progress = progress;
		}
		public void setCompletion(boolean[] completed) {
			this.completed = completed;
		}
	}
	
	public static class ZoneTaskAttributes { //ZONE TASK 1/1000 and 16/2500 etc

		public ZoneTaskAttributes() {
		}

		/** ZONE TASKS **/
		private boolean[][] completed = new boolean[100][3];
		private int[][] progress = new int[100][3];
		private boolean[][] reward = new boolean[100][3];

		public boolean[][] getCompletion() {
			return completed;
		}

		public boolean getTwoOfThreeCompletion(int f) {
			return ((completed[f][0] && completed[f][1])||(completed[f][0] && completed[f][2])
					||(completed[f][1] && completed[f][2]));
		}
	

		public void setCompletion(int index, int task, boolean value) {
			this.completed[index][task] = value;
		}
		
		void setRewards(int index, int task, boolean value) {
			this.reward[index][task] = value;
		}
		
		public void setRewards(boolean[][] completed) {
			this.reward = completed;
		}

		public void setCompletion(boolean[][] completed) {
			this.completed = completed;
		}

		public int[][] getProgress() {
			return progress;
		}
		
		public boolean[][] getRewards() {
			return reward;
		}

		void setProgress(int index, int task, int value) {
			this.progress[index][task] = value;
		}

		public void setProgress(int[][] progress) {
			this.progress = progress;
		}
	}
}
