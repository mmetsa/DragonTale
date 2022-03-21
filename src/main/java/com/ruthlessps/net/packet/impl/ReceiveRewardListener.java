package com.ruthlessps.net.packet.impl;

import com.ruthlessps.net.packet.Packet;
import com.ruthlessps.net.packet.PacketListener;
import com.ruthlessps.world.entity.impl.player.Player;

public class ReceiveRewardListener implements PacketListener {

	@Override
	public void handleMessage(Player player, Packet packet) {
		if(player.boxOpen.equalsIgnoreCase("Mega mystery box"))
			player.megaBox.receive(player);
		else if(player.boxOpen.equalsIgnoreCase("Pet mystery box"))
			player.petBox.receive(player);
		else if(player.boxOpen.equalsIgnoreCase("Torva mystery box"))
			player.torvaBox.receive(player);
		else if(player.boxOpen.equalsIgnoreCase("Fancy mystery box"))
			player.fancyBox.receive(player);
		else if(player.boxOpen.equalsIgnoreCase("Mystery box"))
			player.regularBox.receive(player);
		else if(player.boxOpen.equalsIgnoreCase("Donator box"))
			player.donatorBox.receive(player);
		else if(player.boxOpen.equalsIgnoreCase("PVM Box"))
			player.pvmBox.receive(player);
		else if(player.boxOpen.equalsIgnoreCase("Omega PVM Box"))
			player.omegaBox.receive(player);
		else if(player.boxOpen.equalsIgnoreCase("Sharpy Box"))
			player.sharpyBox.receive(player);
	}

}
