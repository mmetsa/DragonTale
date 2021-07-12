package com.ruthlessps.world.content;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import com.dropbox.core.v2.teamlog.TimeUnit;
import com.ruthlessps.engine.task.Task;
import com.ruthlessps.engine.task.TaskManager;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.World;
import com.ruthlessps.world.content.dialogue.Dialogue;
import com.ruthlessps.world.content.dialogue.DialogueExpression;
import com.ruthlessps.world.content.dialogue.DialogueManager;
import com.ruthlessps.world.content.dialogue.DialogueType;
import com.ruthlessps.world.entity.impl.player.Player;

import mysql.impl.Donation;

public class PointsWell {

	public static final int LEAST = 20;
	public static int WELL_TIMER = 0;
	
	
	public static void save() {
		try {
			String s = Integer.toString(WELL_TIMER);
			File file = new File("data/saves/points-well.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(s);
			writer.close();
		} catch(Exception e) {
			
		}
	}
	
	public static int getWell() {
		int a = 0;
		String s = "";
		try {
			File file = new File("data/saves/points-well.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			s = reader.readLine();
			a = Integer.parseInt(s);
			return a;
		} catch(Exception e) {
			
		}
		return 0;
	}
	
	public static void checkWell(Player player) {
		player.getPA().sendInterfaceRemoval();
		if(WELL_TIMER > 0) {
			player.getPA().sendMessage("It looks like the Well is active for "+WELL_TIMER/100+" minutes");
		} else {
			player.getPA().sendMessage("The Well is inactive!");
		}
		return;
	}
	
	public static void donate(Player player, int amount) {
		if(amount < LEAST) {
			player.getPA().sendInterfaceRemoval();
			player.getPA().sendMessage("You need to donate at least "+LEAST+"x 1B bucks!");
			return;
		}
		if(player.getInventory().getAmount(13664) >= amount) {
			WELL_TIMER += amount*5;
			player.getInventory().delete(13664, amount);
		} else {
			player.getPA().sendInterfaceRemoval();
			player.getPA().sendMessage("You don't have enough 1b Bucks in your inventory!");
			return;
		}
		if(amount >= 100) {
			World.sendMessage("@blu@[SERVER]@or2@ "+player.getUsername()+"@blu@ has contributed "+amount/20+" minutes to Loyalty Well");
		}
		for(Player p: World.getPlayers()) {
			if(p != null) {
				PlayerPanel.refreshPanel(p);
			}
		}
		
		return;
		
	}
	
	
	public static boolean isActive() {
		if(WELL_TIMER > 0) {
			return true;
		}
		return false;
	}

	

	public static void init() {
		WELL_TIMER = getWell();
		TaskManager.submit(new Task(1, false) {
			@Override
			public void execute() {
				if(PointsWell.WELL_TIMER > 0) {
					PointsWell.WELL_TIMER--;
					if(	PointsWell.WELL_TIMER%100 == 0) {
						for(Player p: World.getPlayers()) {
							if(p != null) {
								PlayerPanel.refreshPanel(p);
							}
						}
					}
					if(PointsWell.WELL_TIMER == 0) {
						World.sendMessage("<img=10><col=FF7400><shad=0>The Loyalty Well has run out!");
					}
				}

			}
		});
		
	}
	
	
}
