package com.ruthlessps.net.packet.impl.commands.player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.ruthlessps.drops.DropLog;
import com.ruthlessps.drops.NPCDrops;
import com.ruthlessps.model.Flag;
import com.ruthlessps.model.Item;
import com.ruthlessps.model.Locations.Location;
import com.ruthlessps.model.PlayerRanks.DonorRights;
import com.ruthlessps.model.PlayerRanks.PlayerRights;
import com.ruthlessps.model.Position;
import com.ruthlessps.model.Skill;
import com.ruthlessps.util.NameUtils;
import com.ruthlessps.world.World;
import com.ruthlessps.world.content.*;
import com.ruthlessps.world.content.clan.ClanChatManager;
import com.ruthlessps.world.content.combat.CombatFactory;
import com.ruthlessps.world.content.combat.DesolaceFormulas;
import com.ruthlessps.world.content.dialogue.Dialogue;
import com.ruthlessps.world.content.dialogue.DialogueExpression;
import com.ruthlessps.world.content.dialogue.DialogueManager;
import com.ruthlessps.world.content.dialogue.DialogueType;
import com.ruthlessps.world.content.minigames.impl.KillGame;
import com.ruthlessps.world.content.skill.SkillManager;
import com.ruthlessps.world.content.skill.impl.slayer.SlayerTasks;
import com.ruthlessps.world.content.teleportation.TeleportHandler;
import com.ruthlessps.world.entity.impl.npc.NPC;
import com.ruthlessps.world.entity.impl.player.Player;
import com.ruthlessps.world.entity.impl.player.PlayerLoading;

import mysql.impl.Donation;

//import mysql.impl.Store;

public class Misc {

	public static void checkCommands(Player player, String wholeCommand, String[] command) {
		if(command[0].equalsIgnoreCase("help")) {
			World.sendStaffMessage("@red@"+player.getUsername()+" is requesting for help.");
			player.getPacketSender().sendMessage("A help request has been sent to staff! Please do not spam this command!");
		}
		if(command[0].equalsIgnoreCase("thread")) {
			String thread = wholeCommand.substring(7);
			player.getPacketSender().sendString(1, "www.rivalps.eu/showthread.php?tid="+thread);
			player.getPA().sendMessage("<img=10><col=FF0000><shad=0>Opening thread "+thread+"...");
		}
		if(command[0].equalsIgnoreCase("pc")) {
			String name = wholeCommand.substring(3).toLowerCase().replaceAll("_", " ");
			File file = new File("./data/def/txt/prices.txt");
			if(name.length() < 3) {
				player.getPacketSender().sendMessage("<img=10><col=FF0000><shad=0>Please enter at least 3 letters!");
				return;
			}
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line;
				int counter = 0;
				while ((line = reader.readLine()) != null) {
					if(line.contains(name)) {
						String args[] = line.split(": ");
						if (args.length <= 1)
							continue;
						String value = args[1];
						String output = args[0].substring(0, 1).toUpperCase() + args[0].substring(1);
						player.getPacketSender().sendMessage("<img=10><col=FF0000><shad=0>"+output+" costs "+value+"@blu@B");
						counter++;
					}
				}
				if(counter == 0) {
					player.getPacketSender().sendMessage("<img=10><col=FF0000><shad=0>No results were found! Check for spelling errors!");
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (command[0].equalsIgnoreCase("top10")) {
			TopList.showChoice(player);
		}
		if (command[0].equalsIgnoreCase("deals")) {
			Deals.getDeals(player);
		}
		if (command[0].equalsIgnoreCase("togglekc")) {
			player.setToggleKC(!player.getToggleKC());
			if(player.getToggleKC()) {
				player.getPacketSender().sendMessage("<img=10><col=00ff00><shad=0>You have disabled your kill count text.");
			} else {
				player.getPacketSender().sendMessage("<img=10><col=00ff00><shad=0>You have enabled your kill count text.");
			}
		}
		if(wholeCommand.equalsIgnoreCase("boosts")) {
			BoostManager.showBoosts(player);
		}

		if(wholeCommand.equalsIgnoreCase("maxhit")) {
			player.getPacketSender().sendMessage("@red@Melee max hit: @blu@"+DesolaceFormulas.calculateMaxMeleeHit(player, player));
			player.getPacketSender().sendMessage("@red@Ranged max hit: @blu@"+CombatFactory.calculateMaxRangedHit(player, player));
			player.getPacketSender().sendMessage("@red@Magic max hit: @blu@"+DesolaceFormulas.getMagicMaxhit(player, player));
		}

		if(wholeCommand.equalsIgnoreCase("zones")) {
			ZoneTasks.openInterface(player);
		}
		if(wholeCommand.equalsIgnoreCase("combine")) {
			ItemUpgrade.clearInterface(player);
		}

		if(wholeCommand.equalsIgnoreCase("prestiges")) {
			player.getPacketSender().sendMessage("<img=10><col=00ff00><shad=0>You have prestiged "+player.getPrestigeAmount()+ " times");
		}
		if(wholeCommand.equalsIgnoreCase("clearfl")) {
			player.getRelations().getFriendList().clear();
			player.getPacketSender().sendMessage("<img=10><col=00ff00><shad=0>You have cleared your friends list!");
		}
		if(wholeCommand.equalsIgnoreCase("videos")) {
			player.getPacketSender().sendString(1, "https://rivalps.eu/forumdisplay.php?fid=15");
		}
		if(wholeCommand.toLowerCase().equals("disableglobal")) {
			player.setQuestLevel(0);
			player.getPacketSender().sendMessage("<img=10><col=00ff00><shad=0>Your have disabled your Global chats!");
		}
		if(wholeCommand.toLowerCase().equals("enableglobal")) {
			player.setQuestLevel(1);
			player.getPacketSender().sendMessage("<img=10><col=00ff00><shad=0>You have enabled your Global chats!");
		}

		if (wholeCommand.toLowerCase().startsWith("yell")) {
			if (PlayerPunishments.isMuted(player.getUsername()) || PlayerPunishments.isIpMuted(player.getHostAddress())
					|| PlayerPunishments.isMacMuted(player.getMac())) {
				player.getPacketSender().sendMessage("You are muted and cannot yell.");
				return;
			}
			if (player.getRights() == PlayerRights.PLAYER && player.getDonor() == DonorRights.NONE) {
				player.getPacketSender().sendMessage("You must be a staff member or Donator to use yell.");
				return;
			}
			int delay = player.getRights().getYellDelay();
			if (!player.getLastYell().elapsed((delay * 1000))) {
				player.getPacketSender().sendMessage(
						"You must wait at least " + delay + " seconds between every yell-message you send.");
				return;
			}
			String yellMessage = com.ruthlessps.util.Misc.ucFirst(wholeCommand.substring(5, wholeCommand.length()));
			String yell = "";
			yell += "_" + player.getRights().ordinal();
			yell += "_" + player.getGameMode().ordinal();
			yell += "_" + player.getDonor().ordinal();
			yell += "_" + player.isModeler();
			yell += "_" + player.isGambler();
			yell += "_" + player.isGfxDesigner();
			yell += "_" + player.isYoutuber();
			yell += "_" + player.getUsername();
			yell += "_" + yellMessage;
			yell += "_" + player.yellColours.set.titleColour;
			yell += "_" + player.yellColours.set.textColour;
			yell += "_" + player.yellColours.set.titleShadowColour;
			yell += "_" + player.yellColours.set.textShadowColour;
			yell += "_" + player.yellColours.set.titleAlpha;
			yell += "_" + player.yellColours.set.textAlpha;
			yell += "_" + player.yellColours.set.titleShadowAlpha;
			yell += "_" + player.yellColours.set.textShadowAlpha;
			yell += "_" + player.yellTitle;
			
			System.out.print("message:"+yellMessage+" color: "+player.yellColours.set+",Title: "+player.yellTitle);
			World.sendMessage(":yell: " + yell);
			player.getLastYell().reset();
			return;
		}

		if (command[0].equalsIgnoreCase("totalnpckills")) {
			final int[] total = {0};
			player.getDropKillCount().forEach((k, v) -> total[0] += v);
			player.setNpcKills(total[0]);
			player.getPacketSender().sendMessage("<img=10><col=FF0000><shad=0>You have killed "+total[0]+" NPC's");
				
		}

		if (wholeCommand.toLowerCase().startsWith("yell") && player.getRights() == PlayerRights.PLAYER) {
			player.getPacketSender().sendMessage("@red@You must be at least a donor to use this feature.");
		}

		if (command[0].equals("empty")) {
			DialogueManager.start(player, new Dialogue() {

				@Override
				public DialogueExpression animation() {
					return null;
				}

				@Override
				public String[] dialogue() {
					return new String[] { "Yes, empty my inventory", "No, don't empty my inventory" };
				}

				@Override
				public void specialAction() {
					player.setDialogueActionId(420);
				}

				@Override
				public DialogueType type() {
					return DialogueType.OPTION;
				}

			});
		}
		if (command[0].equals("emptybank")) {
			player.getPacketSender().sendInterfaceRemoval().sendMessage("You empty your bank.");
			player.getSkillManager().stopSkilling();
			for (int i = 0; i < player.getBanks().length; i++) {
				player.getBank(i).resetItems().refreshItems();
			}
		}
		if (command[0].equals("players")) {
			player.getPacketSender().sendInterfaceRemoval();
			PlayersOnline.showInterface(player);
		}
		
		if (command[0].equalsIgnoreCase("claim")) {
			new Thread(new Donation(player)).start();
		}
		
		if(command[0].equalsIgnoreCase("changepassword")) {
			player.getPacketSender().sendMessage("@red@Please contact Staff to change your password!");
		}

		if (command[0].startsWith("reward")) {
			if (command.length == 1) {
				player.getPacketSender().sendMessage("Please use [::reward id] or [::reward id amount]");
				return;
			}
			final String playerName = player.getUsername();
			final String id = "1";
			final String amount = command[1];

			com.everythingrs.vote.Vote.service.execute(() -> {
				try {
					com.everythingrs.vote.Vote[] reward = com.everythingrs.vote.Vote.reward("qpxi3atu4zqw10b3apnxc4n29f1r93qlcvo4g2ecm8us8dj9k94o1gpzaubolswteh6kxebnvcxr",
							playerName, id, amount);
					if (reward[0].message != null) {
						player.getPacketSender().sendMessage(reward[0].message);
						return;
					}
					player.getInventory().add(19670, reward[0].give_amount);
					player.setVoteAmt(player.getVoteAmt()+reward[0].give_amount);
					player.getPacketSender().sendMessage("Thank you for voting! You now have " + reward[0].vote_points + " vote points.");
				} catch (Exception e) {
					player.getPacketSender().sendMessage("Api Services are currently offline. Please check back shortly");
					e.printStackTrace();
				}
			});
		}
		if (wholeCommand.equalsIgnoreCase("vote")) {
			player.getPacketSender().sendString(1, "http://rivalv2.everythingrs.com/services/vote");
		}
		if (wholeCommand.equalsIgnoreCase("donate") || wholeCommand.equalsIgnoreCase("store")) {
			player.getPacketSender().sendString(1, "http://rivalps.tech/store");
		}
		if (wholeCommand.equalsIgnoreCase("skull")) {
			if (player.getSkullTimer() > 0) {
				player.getPacketSender().sendMessage("You are already skulled.");
			} else {
				CombatFactory.skullPlayer(player);
			}
		}
	}
}