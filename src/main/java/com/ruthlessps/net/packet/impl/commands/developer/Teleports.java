package com.ruthlessps.net.packet.impl.commands.developer;

import com.ruthlessps.GameSettings;
import com.ruthlessps.model.Position;
import com.ruthlessps.world.content.Achievements;
import com.ruthlessps.world.content.teleportation.TeleportHandler;
import com.ruthlessps.world.entity.impl.player.Player;

public class Teleports {

	public static void checkTeleports(Player player, String wholeCommand, String[] command) {
		if (wholeCommand.equalsIgnoreCase("newmap")) {
			TeleportHandler.teleportPlayer(player, new Position(2350, 4956, 0),
					player.getSpellbook().getTeleportType());
			Achievements.doProgress(player, Achievements.AchievementData.ENTER_HOME);
		}
	}

}
