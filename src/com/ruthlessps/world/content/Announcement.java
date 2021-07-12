package com.ruthlessps.world.content;

import com.ruthlessps.util.Misc;
import com.ruthlessps.util.Stopwatch;
import com.ruthlessps.world.World;

/*
 * @author FaladorPS
 * www.FaladorPS.com
 */

public class Announcement {
	
	
    private static final int TIME = 1200000; //10 minutes
	private static Stopwatch timer = new Stopwatch().reset();
	public static String currentMessage;
	
	/*
	 * Random Message Data
	 */
	private static final String[][] MESSAGE_DATA = { 
			{"@blu@[SERVER]@bla@ Remember to ::vote for rewards every 12 hours!"},
			{"@blu@[SERVER]@bla@ We do updates weekly, stay informed on our ::forums!"},
			{"@blu@[SERVER]@bla@ Use the ::help command to ask staff for help"},
			{"@blu@[SERVER]@bla@ Make sure to read the discord for latest information"},
			{"@blu@[SERVER]@bla@ You get a PvM Lootbox after every 100 NPC kills"},
			{"@blu@[SERVER]@bla@ Do ::thread 16 to see the benefits of donating"},
			{"@blu@[SERVER]@bla@ Donate to help the server grow! ::store to see what we offer"},
			{"@blu@[SERVER]@bla@ Do ::thread 40 to post more suggestions"},
			{"@blu@[SERVER]@bla@ Register and post on the forums to keep them active! ::Forums"},
			
		
	};

	/*
	 * Sequence called in world.java
	 * Handles the main method
	 * Grabs random message and announces it
	 */
	public static void sequence() {
		if(timer.elapsed(TIME)) {
			timer.reset();
			{
				
			currentMessage = MESSAGE_DATA[Misc.getRandom(MESSAGE_DATA.length - 1)][0];
			World.sendMessage(currentMessage);
			World.savePlayers();
					
					
				}
				
			}
		

          }
  }