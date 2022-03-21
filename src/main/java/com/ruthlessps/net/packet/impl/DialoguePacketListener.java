package com.ruthlessps.net.packet.impl;

import com.ruthlessps.net.packet.Packet;
import com.ruthlessps.net.packet.PacketListener;
import com.ruthlessps.world.content.dialogue.DialogueManager;
import com.ruthlessps.world.entity.impl.player.Player;

/**
 * This packet listener handles player's mouse click on the "Click here to
 * continue" option, etc.
 * 
 * @author relex lawl
 */

public class DialoguePacketListener implements PacketListener {

	public static final int DIALOGUE_OPCODE = 40;

	@Override
	public void handleMessage(Player player, Packet packet) {
		switch (packet.getOpcode()) {
		case DIALOGUE_OPCODE:
			DialogueManager.next(player);
			break;
		}
	}
}
