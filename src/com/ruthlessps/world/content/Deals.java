package com.ruthlessps.world.content;

import java.io.*;

import com.ruthlessps.engine.task.Task;
import com.ruthlessps.engine.task.TaskManager;
import com.ruthlessps.model.Item;
import com.ruthlessps.world.World;
import com.ruthlessps.world.entity.impl.player.Player;

public class Deals {

	static boolean deal1;
	static boolean deal2;
	static boolean deal3;
	static boolean deal4;
	public static Item item1,item2,item3,item4,item5,item6,item7,item8,item9,item10,item11,item12;
	public static int time1=0,time2=0,time3=0,time4=0;
	static int counter=0;
	static final File DIR = new File("./data/def/txt/deals.txt");

	
	

	public static void init() {
		TaskManager.submit(new Task(1, false) {
			@Override
			public void execute() {
				if(counter >= 50) {
					if(time1 > 0) {
						time1--;
						if(time1%60 == 0 && time1 != 0) {
							World.sendMessage("<img=10><col=FF7400><shad=0>There's <col=255>"+time1/60+"<col=FF7400> hours left on the 10$ Deal!");
						}
						if(time1 == 0) {
							World.sendMessage("<img=10><col=FF7400><shad=0>The 10$ Deal has run out");
							deal1=false;
						}
					}
					if(time2 > 0) {
						time2--;
						if(time2%60 == 0 && time2 != 0) {
							World.sendMessage("<img=10><col=FF7400><shad=0>There's <col=255>"+time2/60+"<col=FF7400> hours left on the 50$ Deal!");
						}
						if(time2 == 0) {
							World.sendMessage("<img=10><col=FF7400><shad=0>The 50$ Deal has run out");
							deal2=false;
						}
					}
					if(time3 > 0) {
						time3--;
						if(time3%60 == 0 && time3 != 0) {
							World.sendMessage("<img=10><col=FF7400><shad=0>There's <col=255>"+time3/60+"<col=FF7400> hours left on the 100$ Deal!");
						}
						if(time3 == 0) {
							World.sendMessage("<img=10><col=FF7400><shad=0>The 100$ Deal has run out");
							deal3=false;
						}
					}
					if(time4 > 0) {
						time4--;
						if(time4%60 == 0 && time4 != 0) {
							World.sendMessage("<img=10><col=FF7400><shad=0>There's <col=255>"+time4/60+"<col=FF7400> hours left on the 200$ Deal!");
						}
						if(time4 == 0) {
							World.sendMessage("<img=10><col=FF7400><shad=0>The 200$ Deal has run out");
							deal4=false;
						}
					}
					counter=0;
				} else {
					counter++;
				}

			}
		});
	}
	
	
	public static int deal1(int i,boolean isAmt) {
		if(!isAmt) {
			if(i == 1) {
				return item1.getId();
			} else if(i==2) {
				return item2.getId();
			} else {
				return item3.getId();
			}
		} else {
			if(i == 1) {
				return item1.getAmount();
			} else if(i==2) {
				return item2.getAmount();
			} else {
				return item3.getAmount();
			}
		}
	}
	public static int deal2(int i, boolean isAmt) {
		if(!isAmt) {
			if(i == 1) {
				return item4.getId();
			} else if(i==2) {
				return item5.getId();
			} else {
				return item6.getId();
			}
		} else {
			if(i == 1) {
				return item4.getAmount();
			} else if(i==2) {
				return item5.getAmount();
			} else {
				return item6.getAmount();
			}
		}
	}
	public static int deal3(int i, boolean isAmt) {
		if(!isAmt) {
			if(i == 1) {
				return item7.getId();
			} else if(i==2) {
				return item8.getId();
			} else {
				return item9.getId();
			}
		} else {
			if(i == 1) {
				return item7.getAmount();
			} else if(i==2) {
				return item8.getAmount();
			} else {
				return item9.getAmount();
			}
		}
	}
	public static int deal4(int i,boolean isAmt) {
		if(!isAmt) {
			if(i == 1) {
				return item10.getId();
			} else if(i==2) {
				return item11.getId();
			} else {
				return item12.getId();
			}
		} else {
			if(i == 1) {
				return item10.getAmount();
			} else if(i==2) {
				return item11.getAmount();
			} else {
				return item12.getAmount();
			}
		}
	}
	
	public static void loadDeals() {
		try {
			deal1=deal2=deal3=deal4=false;
			String line;
			BufferedReader reader = new BufferedReader(new FileReader(DIR));
			while((line=reader.readLine()) != null) {
				if(line.contains("DEAL1: Active")) {
					deal1 = true;
				}
				if(line.contains("ITEM1")) {
					int end = line.indexOf(",");
					int itemId = Integer.parseInt(line.substring(7, end));
					int amt = Integer.parseInt(line.substring(end+1));
					item1 = new Item(itemId,amt);
				}
				if(line.contains("ITEM2")) {
					int end = line.indexOf(",");
					int itemId = Integer.parseInt(line.substring(7, end));
					int amt = Integer.parseInt(line.substring(end+1));
					item2 = new Item(itemId,amt);
				}
				if(line.contains("ITEM3")) {
					int end = line.indexOf(",");
					int itemId = Integer.parseInt(line.substring(7, end));
					int amt = Integer.parseInt(line.substring(end+1));
					item3 = new Item(itemId,amt);
				}
				if(line.contains("TIME1")) {
					time1 = Integer.parseInt(line.substring(7));
				}
				if(line.contains("DEAL2: Active")) {
					deal2 = true;
				}
				if(line.contains("ITEM4")) {
					int end = line.indexOf(",");
					int itemId = Integer.parseInt(line.substring(7, end));
					int amt = Integer.parseInt(line.substring(end+1));
					item4 = new Item(itemId,amt);
				}
				if(line.contains("ITEM5")) {
					int end = line.indexOf(",");
					int itemId = Integer.parseInt(line.substring(7, end));
					int amt = Integer.parseInt(line.substring(end+1));
					item5 = new Item(itemId,amt);
				}
				if(line.contains("ITEM6")) {
					int end = line.indexOf(",");
					int itemId = Integer.parseInt(line.substring(7, end));
					int amt = Integer.parseInt(line.substring(end+1));
					item6 = new Item(itemId,amt);
				}
				if(line.contains("TIME2")) {
					time2 = Integer.parseInt(line.substring(7));
				}
				if(line.contains("DEAL3: Active")) {
					deal3 = true;
				}
				if(line.contains("ITEM7")) {
					int end = line.indexOf(",");
					int itemId = Integer.parseInt(line.substring(7, end));
					int amt = Integer.parseInt(line.substring(end+1));
					item7 = new Item(itemId,amt);
				}
				if(line.contains("ITEM8")) {
					int end = line.indexOf(",");
					int itemId = Integer.parseInt(line.substring(7, end));
					int amt = Integer.parseInt(line.substring(end+1));
					item8 = new Item(itemId,amt);
				}
				if(line.contains("ITEM9")) {
					int end = line.indexOf(",");
					int itemId = Integer.parseInt(line.substring(7, end));
					int amt = Integer.parseInt(line.substring(end+1));
					item9 = new Item(itemId,amt);
				}
				if(line.contains("TIME3")) {
					time3 = Integer.parseInt(line.substring(7));
				}
				if(line.contains("DEAL4: Active")) {
					deal4 = true;
				}
				if(line.contains("ITEM10")) {
					int end = line.indexOf(",");
					int itemId = Integer.parseInt(line.substring(8, end));
					int amt = Integer.parseInt(line.substring(end+1));
					item10 = new Item(itemId,amt);
				}
				if(line.contains("ITEM11")) {
					int end = line.indexOf(",");
					int itemId = Integer.parseInt(line.substring(8, end));
					int amt = Integer.parseInt(line.substring(end+1));
					item11 = new Item(itemId,amt);
				}
				if(line.contains("ITEM12")) {
					int end = line.indexOf(",");
					int itemId = Integer.parseInt(line.substring(8, end));
					int amt = Integer.parseInt(line.substring(end+1));
					item12 = new Item(itemId,amt);
				}
				if(line.contains("TIME4")) {
					time4 = Integer.parseInt(line.substring(7));
				}
			}
			reader.close();
		} catch(Exception e) {
		}
	}
	
	
	public static void give(int i, Player player) {
		if(player.getInventory().getFreeSlots() < 4) {
			player.getPA().sendMessage("You need at least 4 empty inventory slots to use this!");
			return;
		}
		if(i == 1) {
			if(!dealActive(1)) {
				player.getPacketSender().sendMessage("The Deal is currently not active!");
				return;
			}
			player.getInventory().add(item1.getId(), item1.getAmount());
			player.getInventory().add(item2.getId(), item2.getAmount());
			player.getInventory().add(item3.getId(), item3.getAmount());
			player.getInventory().delete(10936,1);
			return;
		}
		if(i == 2) {
			if(!dealActive(2)) {
				player.getPacketSender().sendMessage("The Deal is currently not active!");
				return;
			}
			player.getInventory().add(item4.getId(), item4.getAmount());
			player.getInventory().add(item5.getId(), item5.getAmount());
			player.getInventory().add(item6.getId(), item6.getAmount());
			player.getInventory().delete(10944,1);
			return;
		}
		if(i == 3) {
			if(!dealActive(3)) {
				player.getPacketSender().sendMessage("The Deal is currently not active!");
				return;
			}
			player.getInventory().add(item7.getId(), item7.getAmount());
			player.getInventory().add(item8.getId(), item8.getAmount());
			player.getInventory().add(item9.getId(), item9.getAmount());
			player.getInventory().delete(12421,1);
			return;
		}
		if(i == 4) {
			if(!dealActive(4)) {
				player.getPacketSender().sendMessage("The Deal is currently not active!");
				return;
			}
			player.getInventory().add(item10.getId(), item10.getAmount());
			player.getInventory().add(item11.getId(), item11.getAmount());
			player.getInventory().add(item12.getId(), item12.getAmount());
			player.getInventory().delete(15356,1);
			return;
		}
		
		
	}
	
	public static boolean dealActive(int deal) {
		switch(deal) {
		case 1:
			return deal1;
		case 2:
			return deal2;
		case 3:
			return deal3;
		case 4:
			return deal4;
		}
		return false;
	}
	
	public static void clear(Player player) {
		player.getPacketSender().sendInterface(62000);
		player.getPacketSender().sendItemOnInterface(62005, 20260, 1);
		player.getPacketSender().sendItemOnInterface(62006, 20260, 1);
		player.getPacketSender().sendItemOnInterface(62007, 20260, 1);
		player.getPacketSender().sendItemOnInterface(62016, 20260, 1);
		player.getPacketSender().sendItemOnInterface(62017, 20260, 1);
		player.getPacketSender().sendItemOnInterface(62018, 20260, 1);
		player.getPacketSender().sendItemOnInterface(62026, 20260, 1);
		player.getPacketSender().sendItemOnInterface(62027, 20260, 1);
		player.getPacketSender().sendItemOnInterface(62028, 20260, 1);
		player.getPacketSender().sendItemOnInterface(62036, 20260, 1);
		player.getPacketSender().sendItemOnInterface(62037, 20260, 1);
		player.getPacketSender().sendItemOnInterface(62038, 20260, 1);
		
	}
	
	public static void getDeals(Player player) {
		clear(player);
		if(dealActive(1)) {
			player.getPacketSender().sendItemOnInterface(62005, deal1(1, false), deal1(1, true));
			player.getPacketSender().sendItemOnInterface(62006, deal1(2, false), deal1(2, true));
			player.getPacketSender().sendItemOnInterface(62007, deal1(3, false), deal1(3, true));
			player.getPacketSender().sendString(62010, "Time Left: "+time1+" min");
		} else {
			player.getPacketSender().sendString(62010, "@red@Deal not active");
		}
		if(dealActive(2)) {
			player.getPacketSender().sendItemOnInterface(62016, deal2(1, false), deal2(1, true));
			player.getPacketSender().sendItemOnInterface(62017, deal2(2, false), deal2(2, true));
			player.getPacketSender().sendItemOnInterface(62018, deal2(3, false), deal2(3, true));
			player.getPacketSender().sendString(62020, "Time Left: "+time2+" min");
		} else {
			player.getPacketSender().sendString(62020, "@red@Deal not active");
		}
		if(dealActive(3)) {
			player.getPacketSender().sendItemOnInterface(62026, deal3(1, false), deal3(1, true));
			player.getPacketSender().sendItemOnInterface(62027, deal3(2, false), deal3(2, true));
			player.getPacketSender().sendItemOnInterface(62028, deal3(3, false), deal3(3, true));
			player.getPacketSender().sendString(62030, "Time Left: "+time3+" min");
		} else {
			player.getPacketSender().sendString(62030, "@red@Deal not active");
		}
		if(dealActive(4)) {
			player.getPacketSender().sendItemOnInterface(62036, deal4(1, false), deal4(1, true));
			player.getPacketSender().sendItemOnInterface(62037, deal4(2, false), deal4(2, true));
			player.getPacketSender().sendItemOnInterface(62038, deal4(3, false), deal4(3, true));
			player.getPacketSender().sendString(62040, "Time Left: "+time4+" min");
		} else {
			player.getPacketSender().sendString(62040, "@red@Deal not active");
		}
	}
	
	
	
}
