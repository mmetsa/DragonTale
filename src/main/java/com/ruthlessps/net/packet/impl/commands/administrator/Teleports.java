package com.ruthlessps.net.packet.impl.commands.administrator;

import com.ruthlessps.world.World;
import com.ruthlessps.world.entity.impl.player.Player;

public class Teleports {

	public static void checkTeleports(Player player, String wholeCommand, String[] command) {
		if (command[0].equalsIgnoreCase("teletome")) {
			String targetName = wholeCommand.substring(command[0].length() + 1);
			Player target = World.getPlayerByName(targetName);
			if (target == null) {
				player.getPacketSender().sendMessage("No such player exists.");
				return;
			}
			target.moveTo(player.getPosition());
			target.getSkillManager().stopSkilling();
			player.getPacketSender().sendMessage("You teleport " + target.getUsername() + " to you.");
			target.sendMessage("You have been teleported to " + player.getUsername() + ".");
		}
	}

}
