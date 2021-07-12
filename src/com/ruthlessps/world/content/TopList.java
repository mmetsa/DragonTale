package com.ruthlessps.world.content;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.ruthlessps.util.NameUtils;
import com.ruthlessps.world.entity.impl.player.Player;

public class TopList {

	
	public static void showChoice(Player player) {
		clear(player);
		player.getPacketSender().sendString(58004, "@blu@Top 10 Donators");
		player.getPacketSender().sendString(58006, "@blu@Top 10 Prestigers");
		player.getPacketSender().sendString(58008, "@blu@Top 10 Damage Dealers");
		player.getPacketSender().sendString(58010, "@blu@Top 10 Voters");
		player.getPacketSender().sendString(58012, "@blu@Top 10 NPC Kills");
		player.getPacketSender().sendString(58014, "@blu@Top 10 Questers");
		player.getPacketSender().sendString(58016, "@blu@Top 10 Soon");
		player.getPacketSender().sendString(58018, "@blu@Top 10 Soon");
		player.getPacketSender().sendInterface(58000);
	}
	
	public static void clear(Player player) {
		int step = 0;
		for(int i = 0; i< 10; i++) {
			player.getPacketSender().sendString(58021+step, " ").sendString(58022+step, " ");
			step+=4;
		}
	}
	public static List<Entry<Player, Long>> donorList;
	public static List<Entry<Player, Long>> prestigeList;
	public static List<Entry<Player, Long>> dmgList;
	public static List<Entry<Player, Long>> voteList;
	public static List<Entry<Player, Long>> killsList;
	public static List<Entry<Player, Long>> questList;
	
	public static void showList(Player player, List<Entry<Player, Long>> list) {
		String s = "";
		if(list == donorList) {
			s = "$";
		} else if(list==prestigeList) {
			s="prestiges";
		} else if(list==dmgList) {
			s="dmg";
		} else if(list==voteList) {
			s="votes";
		} else if(list==killsList) {
			s="kills";
		} else if(list==questList) {
			s="quests";
		}
		showChoice(player);
		int step = 0;
		for(int i = 0; i< 10; i++) {
			player.getPacketSender().sendString(58021+step, list.get(i).getKey().getUsername()+": "+format(list.get(i).getValue())+" "+s);
			step+=4;
		}
			player.getPacketSender().sendInterface(58000);
	}
	
	private static String format(Long dmg) {
		if(dmg < 1000000) {
			if(dmg < 10000) {
				return Long.toString(dmg);
			} else if(dmg < 1000000) {
				return ""+Math.round(dmg/1000)+"k ";
			}
		} else if(dmg < 1000000000) {
			if(dmg < 10000000) {
				return ""+Math.round(dmg/1000000)+"M";
			} else if(dmg < 100000000) {
				return ""+Math.round(dmg/1000000)+"M";
			} else if(dmg < 1000000000) {
				return ""+Math.round(dmg/1000000)+"M";
			}
		} else if(dmg < 1000000000000L) {
			if(dmg < 10000000000L) {
				return ""+Math.round(dmg/1000000000)+"T "+Long.toString(dmg).substring(1,4)+"M";
			} else if(dmg < 100000000000L) {
				return ""+Math.round(dmg/1000000000)+"T "+Long.toString(dmg).substring(2,5)+"M";
			} else if(dmg < 1000000000000L) {
				return ""+Math.round(dmg/1000000000)+"T "+Long.toString(dmg).substring(3,6)+"M";
			}
		} else {
			return "0";
		}
		return "0";
	}

	public static void updateLists() {
		new Thread(new Runnable() {
		     public void run() {
					File dir = new File("./data/playerdata/");
					FileReader fr = null;
					BufferedReader br = null;
					long time = System.currentTimeMillis();
					Map<Player, Long> myMap = new HashMap<>();
					Map<Player, Long> myMap2 = new HashMap<>();
					Map<Player, Long> myMap3 = new HashMap<>();
					Map<Player, Long> myMap4 = new HashMap<>();
					Map<Player, Long> myMap5 = new HashMap<>();
					Map<Player, Long> myMap6 = new HashMap<>();
					File[] directoryListing = dir.listFiles();
					if (directoryListing != null) {
					    for (File child : directoryListing) {
					    	if (child.getName().endsWith(".gitignore")) {
					    		continue;
							}
					    	try {
								fr = new FileReader(child);
								br = new BufferedReader(fr);
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					    	Player player1 = new Player(null);
							player1.setUsername(child.getName().substring(0, child.getName().length()-4));
							player1.setLongUsername(NameUtils.stringToLong(child.getName().substring(0, child.getName().length()-4)));
							try {
									long amt = Long.parseLong(br.readLine());
									myMap.put(player1, amt);
									//br.readLine();
									amt = Long.parseLong(br.readLine());
									myMap2.put(player1, amt);
									//br.readLine();
									amt = Long.parseLong(br.readLine());
									myMap3.put(player1, amt);
									//br.readLine();
									amt = Long.parseLong(br.readLine());
									myMap4.put(player1, amt);
									//br.readLine();
									amt = Long.parseLong(br.readLine());
									myMap5.put(player1, amt);
									//br.readLine();
									amt = Long.parseLong(br.readLine());
									myMap6.put(player1, amt);
									fr.close();
									br.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					      }
						donorList = WildyTorva.sortEntries(myMap);
						prestigeList = WildyTorva.sortEntries(myMap2);
						dmgList = WildyTorva.sortEntries(myMap3);
						voteList = WildyTorva.sortEntries(myMap4);
						killsList = WildyTorva.sortEntries(myMap5);
						questList = WildyTorva.sortEntries(myMap6);
					  } 
		     }
		}).start();
	}
	

}
