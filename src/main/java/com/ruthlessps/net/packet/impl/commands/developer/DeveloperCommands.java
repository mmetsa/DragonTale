package com.ruthlessps.net.packet.impl.commands.developer;

import com.ruthlessps.model.container.impl.Shop.ShopManager;
import com.ruthlessps.world.entity.impl.player.Player;

public class DeveloperCommands {

	public static void execute(Player player, String command, String[] parts) {
		Teleports.checkTeleports(player, command, parts);
		Misc.checkCommands(player, command, parts);
		if(parts[0].equalsIgnoreCase("shop")) {
			int shopid = Integer.parseInt(parts[1]);
			ShopManager.getShops().get(shopid).open(player);
			
		}
	}

}
