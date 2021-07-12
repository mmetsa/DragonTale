package com.ruthlessps.net.packet.impl.commands.player;

import com.ruthlessps.world.entity.impl.player.Player;

public class PlayerCommands {

	public static void execute(Player player, String wholeCommand, String[] command) {
		Teleports.checkTeleports(player, wholeCommand, command);
		Misc.checkCommands(player, wholeCommand, command);
	}

}
