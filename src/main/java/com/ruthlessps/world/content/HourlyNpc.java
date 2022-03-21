package com.ruthlessps.world.content;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;



import com.ruthlessps.engine.task.Task;
import com.ruthlessps.engine.task.TaskManager;
import com.ruthlessps.model.Position;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.World;
import com.ruthlessps.world.entity.impl.npc.NPC;
import com.ruthlessps.world.entity.impl.player.Player;

public class HourlyNpc {
	
	public static final int[] HOURLY_NPCS = {6307,6770,6771,6772,514,3705,515,513,517,518,4999,11248,7164,256,2047,6313,8211,502,503,504,505,506,507,508,509,1860,7235,1388,1871,3712,4500,699,698,700,3700,3699,3701,4000,401,402,404,582,580,6309};
	public static int CURRENT_NPC;
	public static int COUNTER = 6000; //1 hour - 6000 ticks
	public static NPC n = new NPC(loadNpc(), new Position(0,0,0));
	
	
	public static String getCurrentNpc() {
		return n.getDefinition().getName();
	}
	
	
	
	public static int loadNpc() {
		int a = 0;
		String s = "";
		try {
			File file = new File("data/saves/hourly-npc.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			s = reader.readLine();
			a = Integer.parseInt(s);
			return a;
		} catch(Exception e) {
			
		}
		return 0;
	}
	public static int loadTime() {
		int a = 0;
		String s = "";
		try {
			File file = new File("data/saves/hourly-npc.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			s = reader.readLine();
			s = reader.readLine();
			a = Integer.parseInt(s);
			return a;
		} catch(Exception e) {
			
		}
		return 0;
	}
	
	public static void save() {
		try {
			String s = Integer.toString(CURRENT_NPC);
			String t = Integer.toString(COUNTER);
			File file = new File("data/saves/hourly-npc.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(s);
			writer.newLine();
			writer.write(t);
			writer.close();
		} catch(Exception e) {
			
		}
	}
	
	
	public static void createNpc() {
		CURRENT_NPC = HOURLY_NPCS[new Random().nextInt(HOURLY_NPCS.length)];
		n = new NPC(CURRENT_NPC, new Position(0,0,0));
		World.sendMessage("<img=10><col=FF7400><shad=0>The Hourly NPC is "+n.getDefinition().getName()+"  [20% dr boost + 30% dmg boost]");
    	//Discord.api.getChannelById(DiscordConstant.HOURLY_NPC).get().asTextChannel().get().sendMessage("**The new Hourly NPC is:  **"+HourlyNpc.getCurrentNpc().toString());
		for(Player p: World.getPlayers()) {
			if(p != null) {
				PlayerPanel.refreshPanel(p);
			}
		}
		
	}
	
	
	public static void init() {
		CURRENT_NPC = loadNpc();
		COUNTER = loadTime();
		TaskManager.submit(new Task(1, false) {
			@Override
			public void execute() {
				if(COUNTER > 0) {
					COUNTER--;
					if(COUNTER == 0) {
						COUNTER = 6000;
						createNpc();
					}
				}
			}
		});
		
	}
	
	
	
}