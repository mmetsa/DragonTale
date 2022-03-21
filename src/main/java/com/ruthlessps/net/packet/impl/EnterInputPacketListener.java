package com.ruthlessps.net.packet.impl;

import com.ruthlessps.net.packet.Packet;
import com.ruthlessps.net.packet.PacketListener;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.entity.impl.player.Player;

/**
 * This packet manages the input taken from chat box interfaces that allow
 * input, such as withdraw x, bank x, enter name of friend, etc.
 * 
 * @author Gabriel Hannason
 */

public class EnterInputPacketListener implements PacketListener {

	public static final int ENTER_AMOUNT_OPCODE = 208, ENTER_SYNTAX_OPCODE = 60;

	@Override
	public void handleMessage(Player player, Packet packet) {
		switch (packet.getOpcode()) {
		case ENTER_SYNTAX_OPCODE:
			String name = Misc.readString(packet.getBuffer());
			if (name == null)
				return;
			if (player.getInputHandling() != null)
				player.getInputHandling().handleSyntax(player, name);
			player.setInputHandling(null);
			break;
		case ENTER_AMOUNT_OPCODE:
			int amount = packet.readInt();
			if (amount <= 0)
				return;
			if (player.getInputHandling() != null)
				player.getInputHandling().handleAmount(player, amount);
			player.setInputHandling(null);
			break;
		}
	}
}