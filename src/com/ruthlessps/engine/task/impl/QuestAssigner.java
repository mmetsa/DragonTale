package com.ruthlessps.engine.task.impl;

import com.ruthlessps.model.GroundItem;
import com.ruthlessps.model.Item;
import com.ruthlessps.model.definitions.ItemDefinition;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.World;
import com.ruthlessps.world.content.Achievements;
import com.ruthlessps.world.content.Achievements.AchievementAttributes;
import com.ruthlessps.world.content.Achievements.AchievementData;
import com.ruthlessps.world.content.dialogue.Dialogue;
import com.ruthlessps.world.content.dialogue.DialogueExpression;
import com.ruthlessps.world.content.dialogue.DialogueManager;
import com.ruthlessps.world.content.dialogue.DialogueType;
import com.ruthlessps.world.entity.impl.GroundItemManager;
import com.ruthlessps.world.entity.impl.player.Player;

public class QuestAssigner {

	
	public static void assign(Player player) {
		int rand = Misc.random(91);
		int amount = 0;
		if(player.getQuestType() < 7) {
			amount = Misc.random(800);
		} else if(player.getQuestType() < 18) {
			amount = 1+Misc.random(750);
		} else if(player.getQuestType() < 24) {
			amount = 1+Misc.random(1200);
		} else if(player.getQuestType() < 34) {
			amount = 1+Misc.random(1100);
		} else if(player.getQuestType() < 47) {
			amount = 1+Misc.random(1000);
		} else if(player.getQuestType() < 60) {
			amount = 1+Misc.random(700);
		} else if(player.getQuestType() < 61) {
			amount = 1+Misc.random(600);
		} else if(player.getQuestType() < 66) {
			amount = 1+Misc.random(1200);
		} else if(player.getQuestType() < 73) {
			amount = 1+Misc.random(800);
		} else if(player.getQuestType() < 84) {
			amount = 1+Misc.random(1100);
		} else if(player.getQuestType() < 90) {
			amount = 1+Misc.random(1000);
		} else if(player.getQuestType() < 91) {
			amount = 1+Misc.random(1100);
		} else if(player.getQuestType() < 92) {
			amount = 1+Misc.random(900);
		}
		
		
		
		if(player.getQuestType() != -1) {
			tellQuest(player);
			return;
		}
		player.setQuestType(rand);
		player.setCollectAmt(amount);
		tellQuest(player);
		player.getInventory().delete(611, 1);
		player.getInventory().add(612, 1);
		
	}
	/*public static void levelUpPet(Player player) {
		player.setQuestsDone(0);
		int level = player.getQuestLevel();
		if(level == 1) {
			if(player.getInventory().contains(9406)) {
				player.getInventory().delete(9406, 1);
				player.getInventory().add(3815, 1);
			}
		} else if(level == 2) {
			if(player.getInventory().contains(3815)) {
				player.getInventory().delete(3815, 1);
				player.getInventory().add(3816, 1);
			}
		}
		
		
		
		
		
		
		
		player.setQuestLevel(player.getQuestLevel()+1);
		
	}*/
	
	public static String currentQuest(Player player) {
		switch(player.getQuestType()) {
		case 0:
			return "cut "+player.getCollectAmt()+" logs of any type.";
		case 1:
			return "cut "+player.getCollectAmt()+" regular logs.";
		case 2:
			return "cut "+player.getCollectAmt()+" oak logs.";
		case 3:
			return "cut "+player.getCollectAmt()+" maple logs.";
		case 4:
			return "cut "+player.getCollectAmt()+" willow logs.";
		case 5:
			return "cut "+player.getCollectAmt()+" yew logs.";
		case 6:
			return "cut "+player.getCollectAmt()+" magic logs.";
		case 7:
			return "mine "+player.getCollectAmt()+" ore of any type.";
		case 8:
			return "mine "+player.getCollectAmt()+" tin ore.";
		case 9:
			return "mine "+player.getCollectAmt()+" copper ore.";
		case 10:
			return "mine "+player.getCollectAmt()+" iron (ice) ore.";
		case 11:
			return "mine "+player.getCollectAmt()+" coal.";
		case 12:
			return "mine "+player.getCollectAmt()+" pure essence.";
		case 13:
			return "mine "+player.getCollectAmt()+" rune essence.";
		case 14:
			return "mine "+player.getCollectAmt()+" gold ore.";
		case 15:
			return "mine "+player.getCollectAmt()+" mithril ore.";
		case 16:
			return "mine "+player.getCollectAmt()+" adamantite ore.";
		case 17:
			return "mine "+player.getCollectAmt()+" runite ore.";
		case 18:
			return "deliver "+player.getCollectAmt()+" regular noted logs to Quest Collector at home";
		case 19:
			return "deliver "+player.getCollectAmt()+" noted oak logs to Quest Collector at home";
		case 20:
			return "deliver "+player.getCollectAmt()+" noted maple logs to Quest Collector at home";
		case 21:
			return "deliver "+player.getCollectAmt()+" noted willow logs to Quest Collector at home";
		case 22:
			return "deliver "+player.getCollectAmt()+" noted yew logs to Quest Collector at home";
		case 23:
			return "deliver "+player.getCollectAmt()+" noted magic logs to Quest Collector at home";
		case 24:
			return "deliver "+player.getCollectAmt()+" noted tin ore to Quest Collector at home";
		case 25:
			return "deliver "+player.getCollectAmt()+" noted copper ore to Quest Collector at home";
		case 26:
			return "deliver "+player.getCollectAmt()+" noted iron (ice) ore to Quest Collector at home";
		case 27:
			return "deliver "+player.getCollectAmt()+" noted coal ore to Quest Collector at home";
		case 28:
			return "deliver "+player.getCollectAmt()+" noted pure essence to Quest Collector at home";
		case 29:
			return "deliver "+player.getCollectAmt()+" noted rune essence to Quest Collector at home";
		case 30:
			return "deliver "+player.getCollectAmt()+" noted gold ore to Quest Collector at home";
		case 31:
			return "deliver "+player.getCollectAmt()+" noted mithril ore to Quest Collector at home";
		case 32:
			return "deliver "+player.getCollectAmt()+" noted adamant ore to Quest Collector at home";
		case 33:
			return "deliver "+player.getCollectAmt()+" noted runite ore to Quest Collector at home";
		case 34:
			return "deliver "+player.getCollectAmt()+" noted Raw Shrimps to Quest Collector at home";
		case 35:
			return "deliver "+player.getCollectAmt()+" noted Raw Trout to Quest Collector at home";
		case 36:
			return "deliver "+player.getCollectAmt()+" noted Raw Salmon to Quest Collector at home";
		case 37:
			return "deliver "+player.getCollectAmt()+" noted Raw Lobster to Quest Collector at home";
		case 38:
			return "deliver "+player.getCollectAmt()+" noted Raw Cod to Quest Collector at home";
		case 39:
			return "deliver "+player.getCollectAmt()+" noted Raw Bass to Quest Collector at home";
		case 40:
			return "deliver "+player.getCollectAmt()+" noted Raw Anchovies to Quest Collector at home";
		case 41:
			return "deliver "+player.getCollectAmt()+" noted Raw Monkfish to Quest Collector at home";
		case 42:
			return "deliver "+player.getCollectAmt()+" noted Raw Manta rays to Quest Collector at home";
		case 43:
			return "deliver "+player.getCollectAmt()+" noted Raw Tunas to Quest Collector at home";
		case 44:
			return "deliver "+player.getCollectAmt()+" noted Raw Swordfish to Quest Collector at home";
		case 45:
			return "deliver "+player.getCollectAmt()+" noted Raw Sharks to Quest Collector at home";
		case 46:
			return "deliver "+player.getCollectAmt()+" noted Raw Rocktails to Quest Collector at home";
		case 47:
			return "deliver "+player.getCollectAmt()+" noted Cooked Shrimps to Quest Collector at home";
		case 48:
			return "deliver "+player.getCollectAmt()+" noted Cooked Trout to Quest Collector at home";
		case 49:
			return "deliver "+player.getCollectAmt()+" noted Cooked Salmon to Quest Collector at home";
		case 50:
			return "deliver "+player.getCollectAmt()+" noted Cooked Lobster to Quest Collector at home";
		case 51:
			return "deliver "+player.getCollectAmt()+" noted Cooked Cod to Quest Collector at home";
		case 52:
			return "deliver "+player.getCollectAmt()+" noted Cooked Bass to Quest Collector at home";
		case 53:
			return "deliver "+player.getCollectAmt()+" noted Cooked Anchovies to Quest Collector at home";
		case 54:
			return "deliver "+player.getCollectAmt()+" noted Cooked Monkfish to Quest Collector at home";
		case 55:
			return "deliver "+player.getCollectAmt()+" noted Cooked Manta rays to Quest Collector at home";
		case 56:
			return "deliver "+player.getCollectAmt()+" noted Cooked Tunas to Quest Collector at home";
		case 57:
			return "deliver "+player.getCollectAmt()+" noted Cooked Swordfish to Quest Collector at home";
		case 58:
			return "deliver "+player.getCollectAmt()+" noted Cooked Sharks to Quest Collector at home";
		case 59:
			return "deliver "+player.getCollectAmt()+" noted Cooked Rocktails to Quest Collector at home";
		case 60:
			return "Deliver "+player.getCollectAmt()+" Agility Tickets to Quest Collector at home";
		case 61:
			return "Deliver "+player.getCollectAmt()+" Noted Bananas (thiev.) to Quest Collector at home";
		case 62:
			return "Deliver "+player.getCollectAmt()+" Noted Gold Rings (thiev.) to Quest Collector at home";
		case 63:
			return "Deliver "+player.getCollectAmt()+" Noted Damaged Hammers (thiev.) to Quest Collector at home";
		case 64:
			return "Deliver "+player.getCollectAmt()+" Noted Staves (thiev.) to Quest Collector at home";
		case 65:
			return "Deliver "+player.getCollectAmt()+" Noted Scimitars (thiev.) to Quest Collector at home";
		case 66:
			return "Deliver "+player.getCollectAmt()+" Noted Bronze Bars to Quest Collector at home";
		case 67:
			return "Deliver "+player.getCollectAmt()+" Noted Ice Bars to Quest Collector at home";
		case 68:
			return "Deliver "+player.getCollectAmt()+" Noted Steel Bars to Quest Collector at home";
		case 69:
			return "Deliver "+player.getCollectAmt()+" Noted Gold Bars to Quest Collector at home";
		case 70:
			return "Deliver "+player.getCollectAmt()+" Noted Mithril Bars to Quest Collector at home";
		case 71:
			return "Deliver "+player.getCollectAmt()+" Noted Adamant Bars to Quest Collector at home";
		case 72:
			return "Deliver "+player.getCollectAmt()+" Noted Rune Bars to Quest Collector at home";
		case 73:
			return "Clean "+player.getCollectAmt()+" Grimy Guams";
		case 74:
			return "Clean "+player.getCollectAmt()+" Grimy Marrentills";
		case 75:
			return "Clean "+player.getCollectAmt()+" Grimy Tarromins";
		case 76:
			return "Clean "+player.getCollectAmt()+" Grimy Harralanders";
		case 77:
			return "Clean "+player.getCollectAmt()+" Grimy Ranarrs";
		case 78:
			return "Clean "+player.getCollectAmt()+" Grimy Irits";
		case 79:
			return "Clean "+player.getCollectAmt()+" Grimy Avantoes";
		case 80:
			return "Clean "+player.getCollectAmt()+" Grimy Kwuarms";
		case 81:
			return "Clean "+player.getCollectAmt()+" Grimy Cadantines";
		case 82:
			return "Clean "+player.getCollectAmt()+" Grimy Dwarf Weeds";
		case 83:
			return "Clean "+player.getCollectAmt()+" Grimy Torstols";
		case 84:
			return "Light "+player.getCollectAmt()+" Regular Logs";
		case 85:
			return "Light "+player.getCollectAmt()+" Oak Logs";
		case 86:
			return "Light "+player.getCollectAmt()+" Maple Logs";
		case 87:
			return "Light "+player.getCollectAmt()+" Willow Logs";
		case 88:
			return "Light "+player.getCollectAmt()+" Yew Logs";
		case 89:
			return "Light "+player.getCollectAmt()+" Magic Logs";
		case 90:
			return "Pick "+player.getCollectAmt()+" Flax";
		case 91:
			return "Cut "+player.getCollectAmt()+" Gems";
			
			default:
				return "";
		}
	}
	public static void tellQuest(Player player) {
		if(player.getQuestType() == -1) {
			player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You don't have a quest!");
			return;
		}
		DialogueManager.start(player, new Dialogue() {

			@Override
			public DialogueExpression animation() {
				return DialogueExpression.NORMAL;
			}

			@Override
			public String[] dialogue() {
				return new String[] { "Your current quest is to",
						" "+currentQuest(player) };
			}

			@Override
			public Dialogue nextDialogue() {
				return DialogueManager.getDialogues().get(-1);
			}

			@Override
			public int npcId() {
				return 1;
			}

			@Override
			public DialogueType type() {
				return DialogueType.NPC_STATEMENT;
			}

		});
	}
	
	public static void handleBob(Player player, int type) {
		int logId = 0;
		switch(type) {
		case 18:
			logId = 1512;
			break;
		case 19:
			logId = 1522;
			break;
		case 20:
			logId = 1518;
			break;
		case 21:
			logId = 1520;
			break;
		case 22:
			logId = 1516;
			break;
		case 23:
			logId = 1514;
			break;
		case 24:
			logId = 439;
			break;
		case 25:
			logId = 437;
			break;
		case 26:
			logId = 441;
			break;
		case 27:
			logId = 454;
			break;
		case 28:
			logId = 7937;
			break;
		case 29:
			logId = 1437;
			break;
		case 30:
			logId = 445;
			break;
		case 31:
			logId = 448;
			break;
		case 32:
			logId = 450;
			break;
		case 33:
			logId = 452;
			break;
		case 34:
			logId = 320;
			break;
		case 35:
			logId = 336;
			break;
		case 36:
			logId = 332;
			break;
		case 37:
			logId = 378;
			break;
		case 38:
			logId = 342;
			break;
		case 39:
			logId = 364;
			break;
		case 40:
			logId = 322;
			break;
		case 41:
			logId = 7945;
			break;
		case 42:
			logId = 390;
			break;
		case 43:
			logId = 360;
			break;
		case 44:
			logId = 372;
			break;
		case 45:
			logId = 384;
			break;
		case 46:
			logId = 15271;
			break;
		case 47:
			logId = 316;
			break;
		case 48:
			logId = 334;
			break;
		case 49:
			logId = 332;
			break;
		case 50:
			logId = 380;
			break;
		case 51:
			logId = 340;
			break;
		case 52:
			logId = 366;
			break;
		case 53:
			logId = 320;
			break;
		case 54:
			logId = 7947;
			break;
		case 55:
			logId = 392;
			break;
		case 56:
			logId = 362;
			break;
		case 57:
			logId = 374;
			break;
		case 58:
			logId = 386;
			break;
		case 59:
			logId = 15273;
			break;
		case 60:
			logId = 2996;
			break;
		case 61:
			logId = 18200;
			break;
		case 62:
			logId = 15010;
			break;
		case 63:
			logId = 17402;
			break;
		case 64:
			logId = 1390;
			break;
		case 65:
			logId = 11999;
			break;
		case 66:
			logId = 2350;
			break;
		case 67:
			logId = 2352;
			break;
		case 68:
			logId = 2354;
			break;
		case 69:
			logId = 2358;
			break;
		case 70:
			logId = 2360;
			break;
		case 71:
			logId = 2362;
			break;
		case 72:
			logId = 2364;
			break;
			
			
			default:
				logId = 0;
		}
		final int logs = logId;
		if(type < 18 || type > 72) {
			player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to have a Quest to talk to Quest Collector!");
			return;
		}
		DialogueManager.start(player, new Dialogue() {
			

			@Override
			public DialogueExpression animation() {
				return DialogueExpression.NORMAL;
			}

			@Override
			public String[] dialogue() {
				return new String[] { "You had "+player.getInventory().getAmount(logs)+" "+ItemDefinition.forId(logs).getName().replaceAll("_", " ")+" in your inventory",
						"Thank you for handing them over!" };
			}

			@Override
			public Dialogue nextDialogue() {
				return DialogueManager.getDialogues().get(-1);
			}

			@Override
			public int npcId() {
				return 1;
			}

			@Override
			public DialogueType type() {
				return DialogueType.NPC_STATEMENT;
			}

		});
		if(player.getCollectAmt()-player.getInventory().getAmount(logId) < 0) {
			player.getInventory().delete(logId, player.getCollectAmt());
			player.setCollectAmt(0);
		} else if(player.getCollectAmt()-player.getInventory().getAmount(logId) >= 0) {
			player.setCollectAmt(player.getCollectAmt()-player.getInventory().getAmount(logId));
			player.getInventory().delete(logId, player.getInventory().getAmount(logId));
		}
		if(player.getCollectAmt() == 0) {
			player.setQuestsDone(player.getQuestsDone()+1);
			player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You have completed your quest!");
			player.setQuestType(-1);
			Achievements.doProgress(player, AchievementData.COMPLETE_QUEST);
			Achievements.doProgress(player, AchievementData.COMPLETE_3_QUESTS, 1);
			Achievements.doProgress(player, AchievementData.COMPLETE_10_QUESTS, 1);
			Achievements.doProgress(player, AchievementData.COMPLETE_50_QUESTS, 1);
			if(player.getQuestsDone()%10 == 0) {
				World.sendMessage("<col=FF7400><shad=0> <img=10>[Global]<img=10> "+player.getUsername()+" has completed "+player.getQuestsDone()+" quests!");
			}
			if(player.getQuestsDone()%10 == 0) {
				World.sendMessage("<col=FF7400><shad=0> <img=10>[Global]<img=10> "+player.getUsername()+" has completed "+player.getQuestsDone()+" quests!");
			}
			handleReward(player);
		}

		
	}
	public static void handleReward(Player player) {
		int rand = Misc.random(9)+1;
		int amt = 0;
		int boxes[] = {4032, 15501, 6199, 2801};
		if(rand<7) {
			amt = 1;
		} else if (rand < 10) {
			amt = 2;
		} else {
			amt = 3;
		}
		if(player.getInventory().getFreeSlots() > 2+amt) {
			for(int i = 1; i<=amt; i++) {
				player.getInventory().add(boxes[Misc.random(boxes.length-1)], 1);
			}
			player.getInventory().add(14639, Misc.random(10000)+1);
			player.getInventory().add(19670, 1+Misc.random(8));
			player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You have received your reward!");
		} else {
			for(int i = 1; i<=amt; i++) {
				GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(boxes[Misc.random(boxes.length-1)], 1),
						player.getPosition().copy(), player.getUsername(), false, 150, false, 200));
			}
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(14639, Misc.random(10000)+1),
					player.getPosition().copy(), player.getUsername(), false, 150, false, 200));
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(19670, 1+Misc.random(8)),
					player.getPosition().copy(), player.getUsername(), false, 150, false, 200));
			player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>Your reward has been dropped on the ground! Pick it up!");
		}
		
		
	}
	public static void handleQuest(Player player, int type) {
		if(player.getQuestType() == type) {
			if(player.getCollectAmt() >= 1) {
				player.setCollectAmt(player.getCollectAmt()-1);
				if(player.getCollectAmt() == 0) {
					player.setQuestsDone(player.getQuestsDone()+1);
					player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You have completed your quest!");
					Achievements.doProgress(player, AchievementData.COMPLETE_QUEST);
					Achievements.doProgress(player, AchievementData.COMPLETE_3_QUESTS, 1);
					Achievements.doProgress(player, AchievementData.COMPLETE_10_QUESTS, 1);
					Achievements.doProgress(player, AchievementData.COMPLETE_50_QUESTS, 1);
					if(player.getQuestsDone()%10 == 0) {
						World.sendMessage("<col=FF7400><shad=0> <img=10>[Global]<img=10> "+player.getUsername()+" has completed "+player.getQuestsDone()+" quests!");
					}
					handleReward(player);
					player.setQuestType(-1);
				}
			}
		}
		
		
		
		
		
	}
	
	
	
}
