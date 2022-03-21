package com.ruthlessps.net.packet.impl.commands.owner;

import com.ruthlessps.world.entity.impl.player.Player;

public class OwnerCommands {

	public static void execute(Player player, String command, String[] parts) {
		Teleports.checkTeleports(player, command, parts);
		Misc.checkCommands(player, command, parts);
	}

}
