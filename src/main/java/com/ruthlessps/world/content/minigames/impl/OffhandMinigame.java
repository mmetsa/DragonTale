package com.ruthlessps.world.content.minigames.impl;

import java.util.ArrayList;

import com.ruthlessps.model.Position;
import com.ruthlessps.model.definitions.WeaponAnimations;
import com.ruthlessps.GameSettings;
import com.ruthlessps.model.Flag;
import com.ruthlessps.model.Item;
import com.ruthlessps.model.Locations.Location;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.content.Achievements.AchievementData;
import com.ruthlessps.world.content.teleportation.TeleportHandler;
import com.ruthlessps.world.content.teleportation.TeleportType;
import com.ruthlessps.world.entity.impl.player.Player;
import com.ruthlessps.world.entity.impl.player.PlayerProcess;

public class OffhandMinigame {
	
	static private int[] STAGE_REQS = new int[] {50,50,50,50,0,50,50,50,50,0,50,50,50,50,0,50,50,50,50}; //16 of them
	
	public static void moveToNextRoom(Player player, int stage) {
		if(player.getOffhandAttributes().getStage() == -1) {
			moveTo(player, stage);
		} else {
			if(player.getOffhandAttributes().getStageCompletion()[stage] == true) {
				moveTo(player, stage);
			} else {
				player.sendMessage("You need a kill count of "+STAGE_REQS[stage]+" to advance to the next room.");
			}
		}
	}
	
	public static void moveTo(Player player, int stage) {
		switch(stage) {
		case -1:
			TeleportHandler.teleportPlayer(player, new Position(1862, 4939), TeleportType.NORMAL);
			break;
		case 0:
			TeleportHandler.teleportPlayer(player, new Position(1876, 4939), TeleportType.NORMAL);
			break;
		case 1:
			TeleportHandler.teleportPlayer(player, new Position(1890, 4939), TeleportType.NORMAL);
			break;
		case 2:
			TeleportHandler.teleportPlayer(player, new Position(1904, 4939), TeleportType.NORMAL);
			break;
		case 3:
			TeleportHandler.teleportPlayer(player, new Position(1917, 4939), TeleportType.NORMAL);
			break;
		case 4:
			TeleportHandler.teleportPlayer(player, new Position(1915, 4953), TeleportType.NORMAL);
			break;
		case 5:
			TeleportHandler.teleportPlayer(player, new Position(1901, 4953), TeleportType.NORMAL);
			break;
		case 6:
			TeleportHandler.teleportPlayer(player, new Position(1887, 4953), TeleportType.NORMAL);
			break;
		case 7:
			TeleportHandler.teleportPlayer(player, new Position(1873, 4953), TeleportType.NORMAL);
			break;
		case 8:
			TeleportHandler.teleportPlayer(player, new Position(1858, 4953), TeleportType.NORMAL);
			break;
		case 9:
			TeleportHandler.teleportPlayer(player, new Position(1862, 4967), TeleportType.NORMAL);
			break;
		case 10:
			TeleportHandler.teleportPlayer(player, new Position(1876, 4967), TeleportType.NORMAL);
			break;
		case 11:
			TeleportHandler.teleportPlayer(player, new Position(1890, 4967), TeleportType.NORMAL);
			break;
		case 12:
			TeleportHandler.teleportPlayer(player, new Position(1904, 4967), TeleportType.NORMAL);
			break;
		case 13:
			TeleportHandler.teleportPlayer(player, new Position(1917, 4967), TeleportType.NORMAL);
			break;
		case 14:
			TeleportHandler.teleportPlayer(player, new Position(1915, 4981), TeleportType.NORMAL);
			break;
		case 15:
			TeleportHandler.teleportPlayer(player, new Position(1901, 4981), TeleportType.NORMAL);
			break;
		case 16:
			TeleportHandler.teleportPlayer(player, new Position(1887, 4981), TeleportType.NORMAL);
			break;
		case 17:
			TeleportHandler.teleportPlayer(player, new Position(1873, 4981), TeleportType.NORMAL);
			break;
		case 18:
			break;
		}
		player.getOffhandAttributes().setStage(stage+1);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static class OffhandAttributes {
		private int kills;
		private int stage = -1;
		private int stageNPC = 1618;
		private boolean[] stageCompletion = new boolean[16];
	
		public boolean[] getStageCompletion() {
			return stageCompletion;
		}
	
		public void setCompletion(int index, boolean value) {
			this.stageCompletion[index] = value;
		}
	
		public void setCompletion(boolean[] completed) {
			this.stageCompletion = completed;
		}
		public int getStage() {
			return this.stage;
		}
		public void setStage(int stage) {
			this.stage = stage;
		}
		
		public int getStageNPC() {
			return this.stageNPC;
		}
		public void setStageNPC(int stage) {
			this.stageNPC = stage;
		}
		public void increment() {
			this.kills++;
			if(this.kills == 50) {
				setCompletion(this.stage, true);
				kills = 0;
			}
		}
	}
	
	
	
}
