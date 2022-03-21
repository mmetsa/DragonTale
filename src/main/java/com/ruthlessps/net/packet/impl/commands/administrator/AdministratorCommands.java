package com.ruthlessps.net.packet.impl.commands.administrator;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.ruthlessps.world.entity.impl.player.Player;

public class AdministratorCommands {

	public static void execute(Player player, String command, String[] parts) {
		Teleports.checkTeleports(player, command, parts);
		try {
			Misc.checkCommands(player, command, parts);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
