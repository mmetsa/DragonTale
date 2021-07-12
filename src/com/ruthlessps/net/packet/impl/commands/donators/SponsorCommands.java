package com.ruthlessps.net.packet.impl.commands.donators;

import com.ruthlessps.model.Graphic;
import com.ruthlessps.model.Position;
import com.ruthlessps.model.Skill;
import com.ruthlessps.world.content.teleportation.TeleportHandler;
import com.ruthlessps.world.entity.impl.player.Player;

public class SponsorCommands {

	public static void execute(Player player, String command, String[] parts) {
		if (command.equalsIgnoreCase("szone")) {
			TeleportHandler.teleportPlayer(player, new Position(3216, 3117, 0),
					player.getSpellbook().getTeleportType());
		}
		if(command.equalsIgnoreCase("buff")) {
			player.buffCombatStats(21);

		}
	}

}
