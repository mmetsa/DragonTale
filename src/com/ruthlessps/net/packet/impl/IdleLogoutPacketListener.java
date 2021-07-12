/*package com.ruthlessps.net.packet.impl;

import com.ruthlessps.model.PlayerRanks.PlayerRights;
import com.ruthlessps.net.packet.Packet;
import com.ruthlessps.net.packet.PacketListener;
import com.ruthlessps.world.World;
import com.ruthlessps.world.entity.impl.player.Player;

//CALLED EVERY 3 MINUTES OF INACTIVITY

public class IdleLogoutPacketListener implements PacketListener {

	@Override
	public void handleMessage(Player player, Packet packet) {
		if (player.getRights() == PlayerRights.MODERATOR || player.getRights() == PlayerRights.ADMINISTRATOR
				|| player.getRights() == PlayerRights.OWNER || player.getRights() == PlayerRights.DEVELOPER)
			return;

		if (player.logout() && (player.getCurrentTask() == null || !player.isRunning())) {
			World.getPlayers().remove(player);
		}

		player.setInactive(true);
	}
}
*/