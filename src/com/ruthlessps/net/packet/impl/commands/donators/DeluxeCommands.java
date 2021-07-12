package com.ruthlessps.net.packet.impl.commands.donators;

import com.ruthlessps.model.Locations.Location;
import com.ruthlessps.model.Position;
import com.ruthlessps.world.content.teleportation.TeleportHandler;
import com.ruthlessps.world.entity.impl.player.Player;

public class DeluxeCommands {

	public static void execute(Player player, String command, String[] parts) {
		if (command.equalsIgnoreCase("ddzone")) {
			TeleportHandler.teleportPlayer(player, new Position(2319, 9624, 0),
					player.getSpellbook().getTeleportType());
		}
		if (command.equalsIgnoreCase("bank")) {
			if (player.getLocation() == Location.WILDERNESS || player.getLocation() == Location.KILLGAME || player.getLocation() == Location.KILLSTART) {
				player.getPacketSender().sendMessage("You can not bank here.");
			} else {
				player.getBank(player.getCurrentBankTab()).open();
			}
		}
		if(command.equalsIgnoreCase("buff")) {
			player.buffCombatStats(11);
		}
	}
}