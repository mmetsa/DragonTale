package com.ruthlessps.world.content.minigames.impl;

import java.util.ArrayList;

import com.ruthlessps.model.Position;
import com.ruthlessps.model.definitions.WeaponAnimations;
import com.ruthlessps.GameSettings;
import com.ruthlessps.model.Flag;
import com.ruthlessps.model.Item;
import com.ruthlessps.model.Locations.Location;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.content.teleportation.TeleportHandler;
import com.ruthlessps.world.entity.impl.player.Player;
import com.ruthlessps.world.entity.impl.player.PlayerProcess;

public class Bunnyhop {
	private Player player;
	public Bunnyhop(Player p) {
		this.player = p;
	}
	static int x;
	static int y;
	static int l = 24;
	static int n;
	
	static long START_TIME, END_TIME, elapsed;
	
	GoodCoords coords[] = new GoodCoords[100];
	private static int[] tiles = {4757, 4758, 4759, 4760};
	private static int[] temp = new int[2];
	public void createBadTiles() {
		clearTiles();
		coords[0] = new GoodCoords(2509, 4760);
		coords[1] = new GoodCoords(2510, 4760);
		coords[2] = new GoodCoords(2511, 4760);
		
		coords[3] = new GoodCoords(2509, 4759);
		coords[4] = new GoodCoords(2510, 4759);
		coords[5] = new GoodCoords(2511, 4759);
		
		coords[6] = new GoodCoords(2509, 4758);
		coords[7] = new GoodCoords(2510, 4758);
		coords[8] = new GoodCoords(2511, 4758);
		
		coords[9] = new GoodCoords(2509, 4757);
		coords[10] = new GoodCoords(2510, 4757);
		coords[11] = new GoodCoords(2511, 4757);
		
		coords[12] = new GoodCoords(2535, 4757);
		coords[13] = new GoodCoords(2535, 4758);
		coords[14] = new GoodCoords(2535, 4759);
		coords[15] = new GoodCoords(2535, 4760);
		
		coords[16] = new GoodCoords(2536, 4757);
		coords[17] = new GoodCoords(2536, 4758);
		coords[18] = new GoodCoords(2536, 4759);
		coords[19] = new GoodCoords(2536, 4760);
		
		coords[20] = new GoodCoords(2537, 4757);
		coords[21] = new GoodCoords(2537, 4758);
		coords[22] = new GoodCoords(2537, 4759);
		coords[23] = new GoodCoords(2537, 4760);
		for(int i = 2512; i < 2535; i++) {
			x = i;
			if(i==2512) {
				makeOneTile(i);
			} else {
				if(Misc.random(1) == 0) {
					makeOneTile(i);
				} else {
					makeTwoTiles();
				}
			}
		}
	}
	public void makeOneTile(int i) {
		int rand;
		if(i == 2512) {
			coords[l] = new GoodCoords(x, tiles[Misc.random(3)]);
			System.out.println(coords[l].x + " "+ coords[l].y);
			l+=1;
		} else {
			y = coords[l-1].y;
			if(y == 4757) {
				rand = Misc.random(1);
				if(rand==0) {
					coords[l] = new GoodCoords(x, 4757);
					System.out.println(coords[l].x + " "+ coords[l].y);
					l+=1;
				} else {
					coords[l] = new GoodCoords(x, 4758);
					System.out.println(coords[l].x + " "+ coords[l].y);
					l+=1;
				}
			} else if(y == 4758) {
				rand = Misc.random(2);
				if(rand == 0) {
					coords[l] = new GoodCoords(x, 4757);
					System.out.println(coords[l].x + " "+ coords[l].y);
					l+=1;
				} else if(rand == 1){
					coords[l] = new GoodCoords(x, 4758);
					System.out.println(coords[l].x + " "+ coords[l].y);
					l+=1;
				} else {
					coords[l] = new GoodCoords(x, 4759);
					System.out.println(coords[l].x + " "+ coords[l].y);
					l+=1;
				}
			} else if(y == 4759) {
				rand = Misc.random(2);
				if(rand == 0) {
					coords[l] = new GoodCoords(x, 4758);
					System.out.println(coords[l].x + " "+ coords[l].y);
					l+=1;
				} else if(rand == 1){
					coords[l] = new GoodCoords(x, 4759);
					System.out.println(coords[l].x + " "+ coords[l].y);
					l+=1;
				} else {
					coords[l] = new GoodCoords(x, 4760);
					System.out.println(coords[l].x + " "+ coords[l].y);
					l+=1;
				}
			} else if(y == 4760) {
				rand = Misc.random(1);
				if(rand==0) {
					coords[l] = new GoodCoords(x, 4760);
					System.out.println(coords[l].x + " "+ coords[l].y);
					l+=1;
				} else {
					coords[l] = new GoodCoords(x, 4759);
					System.out.println(coords[l].x + " "+ coords[l].y);
					l+=1;
				}
			}
		}
	}
	
	
	public void makeTwoTiles() {
		int rand;
		y = coords[l-1].y;
		if(y == 4757) {
			coords[l] = new GoodCoords(x, 4757);
			System.out.println(coords[l].x + " "+ coords[l].y);
			l+=1;
			coords[l] = new GoodCoords(x, 4758);
			System.out.println(coords[l].x + " "+ coords[l].y);
			l+=1;
		} else if(y == 4758) {
			rand = Misc.random(1);
			if(rand == 0) {
				coords[l] = new GoodCoords(x, 4757);
				System.out.println(coords[l].x + " "+ coords[l].y);
				l+=1;
				coords[l] = new GoodCoords(x, 4758);
				System.out.println(coords[l].x + " "+ coords[l].y);
				l+=1;
			} else {
				coords[l] = new GoodCoords(x, 4758);
				System.out.println(coords[l].x + " "+ coords[l].y);
				l+=1;
				coords[l] = new GoodCoords(x, 4759);
				System.out.println(coords[l].x + " "+ coords[l].y);
				l+=1;
			}
		} else if(y == 4759) {
			rand = Misc.random(1);
			if(rand == 0) {
				coords[l] = new GoodCoords(x, 4758);
				System.out.println(coords[l].x + " "+ coords[l].y);
				l+=1;
				coords[l] = new GoodCoords(x, 4759);
				System.out.println(coords[l].x + " "+ coords[l].y);
				l+=1;
			} else {
				coords[l] = new GoodCoords(x, 4759);
				System.out.println(coords[l].x + " "+ coords[l].y);
				l+=1;
				coords[l] = new GoodCoords(x, 4760);
				System.out.println(coords[l].x + " "+ coords[l].y);
				l+=1;
			}
		} else if(y == 4760) {
			coords[l] = new GoodCoords(x, 4759);
			System.out.println(coords[l].x + " "+ coords[l].y);
			l+=1;
			coords[l] = new GoodCoords(x, 4760);
			System.out.println(coords[l].x + " "+ coords[l].y);
			l+=1;
		}
	}
	
	
	
	 class GoodCoords {
		private int x;
		private int y;
		
		public GoodCoords(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	 
	 public void startGame() {
		 
		 if(player.getLocation() == Location.BUNNYHOP) {
			 player.getPA().sendMessage("End the current round first!");
			 return;
		 }
		 /*Create the walkable tiles*/
		 createBadTiles();
		 
		 /*Move the player there*/
		 TeleportHandler.teleportPlayer(player, new Position(2509, 4759, 0),
					player.getSpellbook().getTeleportType());
		 player.getPA().sendMessage("You start the Bunnyhop minigame. Get to other end as fast as you can.");
		 player.setRunEnergy(0);
		 player.setRunning(false);
		 WeaponAnimations.assign(player, new Item(12508));
			player.getUpdateFlag().flag(Flag.APPEARANCE);
		 /*Start the timer*/
		 START_TIME = System.currentTimeMillis();
		 
	 }
	 
	 public void endGame(boolean won) {
		 
		 
		 END_TIME = System.currentTimeMillis();
		 elapsed = END_TIME-START_TIME;
		 elapsed /= 1000;
		 if(won) {
			 addPoints(elapsed);
			 player.getPA().sendMessage("It took you "+elapsed+" seconds to complete the minigame!");
			 if(player.getBunnyhopTime() > elapsed) {
				 player.setBunnyhopTime(elapsed);
				 player.getPA().sendMessage("That's a new Personal best!");
			 }
		 } else {
			 player.getPA().sendMessage("You failed to get to the other side!");
		 }
		 PlayerProcess.bunnyhop = 0;
		 clearTiles();
		 /*Move the player away*/
		 player.moveTo(GameSettings.DEFAULT_POSITION);
		 player.setRunEnergy(100);
		 player.setRunning(true);
		 
		 
		 
	 }
	 
	 
	 public void addPoints(long time) {
		 int amount = 10;
		 if(time < 300) {
			 amount += 300-time;
		 }
		 player.getInventory().add(3706, amount);
		 //player.getPointsManager().increasePoints("imbue", amount);
		 //player.getPointsManager().refreshPanel();
		 player.getPA().sendMessage("You earned "+amount+" imbue tokens!");
	 }
	 
	 public void clearTiles() {
		l = 24; 
	 }
	 
	 public boolean checkBad() {
		Position playerPos = player.getPosition();
		Position otherPos = null;
		for(int i = 0; i < l; i++) {
			otherPos = new Position(coords[i].x, coords[i].y);
			if(playerPos.equals(otherPos)) {
				return false;
			}
		}
		return true;

	 }
	
	
	
}
