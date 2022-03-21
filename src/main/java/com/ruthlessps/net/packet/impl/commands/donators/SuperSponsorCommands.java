package com.ruthlessps.net.packet.impl.commands.donators;

import com.ruthlessps.model.Position;
import com.ruthlessps.world.content.teleportation.TeleportHandler;
import com.ruthlessps.world.entity.impl.player.Player;

public class SuperSponsorCommands {

	public static void execute(Player player, String command, String[] parts) {
		if (command.equalsIgnoreCase("sszone")) {
			TeleportHandler.teleportPlayer(player, new Position(1762, 5088, 2),
					player.getSpellbook().getTeleportType());
		}
		if(command.equalsIgnoreCase("buff")) {
			player.buffCombatStats(36);
		}
	}

}
