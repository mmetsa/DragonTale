package com.ruthlessps.util;

import java.util.logging.Logger;

import com.ruthlessps.GameServer;
import com.ruthlessps.world.World;
import com.ruthlessps.world.content.WellOfGoodwill;
import com.ruthlessps.world.content.DoubleDropWell;
import com.ruthlessps.world.content.PointsWell;
import com.ruthlessps.world.content.HourlyNpc;
import com.ruthlessps.world.content.clan.ClanChatManager;
import com.ruthlessps.world.entity.impl.player.Player;
import com.ruthlessps.world.entity.impl.player.PlayerHandler;

public class ShutdownHook extends Thread {

	/**
	 * The ShutdownHook logger to print out information.
	 */
	private static final Logger logger = Logger.getLogger(ShutdownHook.class.getName());

	@Override
	public void run() {
		logger.info("The shutdown hook is processing all required actions...");
		// World.savePlayers();
		GameServer.setUpdating(true);
		for (Player player : World.getPlayers()) {
			if (player != null) {
				// World.deregister(player);
				PlayerHandler.handleLogout(player);
			}
		}
		WellOfGoodwill.save();
		DoubleDropWell.save();
		PointsWell.save();
		HourlyNpc.save();
		ClanChatManager.save();
		logger.info("The shudown hook actions have been completed, shutting the server down...");
	}
}
