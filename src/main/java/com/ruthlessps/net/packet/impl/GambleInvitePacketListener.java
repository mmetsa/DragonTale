package com.ruthlessps.net.packet.impl;

import com.ruthlessps.net.packet.Packet;
import com.ruthlessps.net.packet.PacketListener;
import com.ruthlessps.world.World;
import com.ruthlessps.world.content.gambling.GamblingManager;
import com.ruthlessps.world.entity.impl.player.Player;

public class GambleInvitePacketListener implements PacketListener {

	@Override
	public void handleMessage(Player player, Packet packet) {
		if (player.getConstitution() <= 0)
			return;
		player.getSkillManager().stopSkilling();
		int index = packet.readShort() & 0xFF;
		if (index > World.getPlayers().capacity()) {
			return;
		}
		Player otherPlayer = World.getPlayers().get(index);
		if (otherPlayer == null) {
			return;
		}
		if (otherPlayer.getIndex() != player.getIndex()) {
			GamblingManager.acceptInvitation(player, otherPlayer);
		}
	}
}
