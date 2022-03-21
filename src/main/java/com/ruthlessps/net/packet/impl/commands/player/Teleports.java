package com.ruthlessps.net.packet.impl.commands.player;

import com.ruthlessps.GameSettings;
import com.ruthlessps.model.GameMode;
import com.ruthlessps.model.PlayerRanks.PlayerRights;
import com.ruthlessps.model.Position;
import com.ruthlessps.world.World;
import com.ruthlessps.world.content.Achievements;
import com.ruthlessps.world.content.Achievements.AchievementData;
import com.ruthlessps.world.content.teleportation.TeleportHandler;
import com.ruthlessps.world.entity.impl.player.Player;

public class Teleports {

	public static void checkTeleports(Player player, String wholeCommand, String[] command) {
		if (wholeCommand.equalsIgnoreCase("home")) {
			TeleportHandler.teleportPlayer(player, GameSettings.DEFAULT_POSITION,
					player.getSpellbook().getTeleportType());
			Achievements.doProgress(player, AchievementData.ENTER_HOME);
		}
		if (wholeCommand.equalsIgnoreCase("funpk")) {
			TeleportHandler.teleportPlayer(player, new Position(1837, 5087, 2),
					player.getSpellbook().getTeleportType());
		}
		if (wholeCommand.equalsIgnoreCase("bunnyhop")) {
			player.getBunnyhop().startGame();
		}

		if(command[0].equalsIgnoreCase("totask")) {
			player.getSlayer().handleSlayerToTask();
		}

		if (wholeCommand.equalsIgnoreCase("prestigezone")) {
			if(player.getGameMode() == GameMode.HARDCORE) {
				if(player.getPrestigeAmount() >= 150) {
					TeleportHandler.teleportPlayer(player, new Position(3101, 5536, 0),
							player.getSpellbook().getTeleportType());
					Achievements.doProgress(player, AchievementData.PRESTIGE_ZONE);
				} else {
					player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You must have prestiged 150 times to enter this zone!");
				}
			} else {
					if(player.getPrestigeAmount() >= 300) {
						TeleportHandler.teleportPlayer(player, new Position(3101, 5536, 0),
								player.getSpellbook().getTeleportType());
					} else {
						player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You must have prestiged 300 times to enter this zone!");
					}
			}
		}
		if (wholeCommand.equalsIgnoreCase("train")) {
			TeleportHandler.teleportPlayer(player, new Position(3305, 4975, 0),
					player.getSpellbook().getTeleportType());
		}
		if (wholeCommand.equalsIgnoreCase("well")) {
			TeleportHandler.teleportPlayer(player, new Position(3215, 9489, 0),
					player.getSpellbook().getTeleportType());
		}

		if (wholeCommand.equalsIgnoreCase("zeus")) {
			TeleportHandler.teleportPlayer(player, new Position(3813, 3562, 0),
					player.getSpellbook().getTeleportType());
		}
		if (wholeCommand.equalsIgnoreCase("meleetier")) {
			TeleportHandler.teleportPlayer(player, new Position(3177, 3030, 0),
					player.getSpellbook().getTeleportType());
			player.meleeKills = 0;
		}
		if (wholeCommand.equalsIgnoreCase("magictier")) {
			TeleportHandler.teleportPlayer(player, new Position(2781, 3864, 0),
					player.getSpellbook().getTeleportType());
			player.meleeKills = 0;
		}
		if (wholeCommand.equalsIgnoreCase("rangetier")) {
			TeleportHandler.teleportPlayer(player, new Position(2013, 4822, 0),
					player.getSpellbook().getTeleportType());
			player.meleeKills = 0;
		}
		if (wholeCommand.equalsIgnoreCase("lefosh")) {
			TeleportHandler.teleportPlayer(player, new Position(2721, 4905, 0),
			player.getSpellbook().getTeleportType());
		}
		if (wholeCommand.equalsIgnoreCase("iktomi")) {
			TeleportHandler.teleportPlayer(player, new Position(2642, 4917, 2),
			player.getSpellbook().getTeleportType());
		}
		if (wholeCommand.equalsIgnoreCase("duel")) {
			if(player.getAttributes().getPet() != null) {
				player.getPacketSender().sendMessage("@red@Please dismiss your familiar first");
			} else {
			TeleportHandler.teleportPlayer(player, new Position(3364, 3267, 0),
					player.getSpellbook().getTeleportType());
			}
		}
		if (wholeCommand.equalsIgnoreCase("zombies")) {
			TeleportHandler.teleportPlayer(player, new Position(3504, 3565, 0),
					player.getSpellbook().getTeleportType());
		}
		if (wholeCommand.equalsIgnoreCase("nomad")) {
			TeleportHandler.teleportPlayer(player, new Position(1891, 3177, 0),
					player.getSpellbook().getTeleportType());
		}
		if (wholeCommand.equalsIgnoreCase("1v1")) {
			TeleportHandler.teleportPlayer(player, new Position(3087, 3510, 0),
					player.getSpellbook().getTeleportType());
		}
		if (wholeCommand.equalsIgnoreCase("multi")) {
			TeleportHandler.teleportPlayer(player, new Position(3246, 3501, 0),
					player.getSpellbook().getTeleportType());
		}
		if (wholeCommand.equalsIgnoreCase("rfd")) {
			TeleportHandler.teleportPlayer(player, new Position(3017, 2827, 0),
					player.getSpellbook().getTeleportType());
		}
	}
}