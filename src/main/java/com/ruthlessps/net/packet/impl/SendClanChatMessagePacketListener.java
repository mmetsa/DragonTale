package com.ruthlessps.net.packet.impl;

import org.jboss.netty.buffer.ChannelBuffer;

import com.ruthlessps.net.packet.Packet;
import com.ruthlessps.net.packet.PacketListener;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.content.PlayerPunishments;
import com.ruthlessps.world.content.clan.ClanChatManager;
import com.ruthlessps.world.content.dialogue.DialogueManager;
import com.ruthlessps.world.entity.impl.player.Player;

public class SendClanChatMessagePacketListener implements PacketListener {

	@Override
	public void handleMessage(Player player, Packet packet) {
		/** Get method for the channel buffer. **/
		ChannelBuffer opcode = packet.getBuffer();
		/** Gets requested bytes from the buffer client > server **/
		int size = opcode.readableBytes();
		/** Check to flood **/
		if (size < 1 || size > 255) {
			System.err.println("blocked packet from sending from clan chat. Requested size="+size);
			return;
		}
		
		String clanMessage = packet.readString();
		/** Checks for null, invalid messages **/
		if(clanMessage == null || clanMessage.length() < 1 || clanMessage.length() > 255)
			return;
		
		if(PlayerPunishments.isMuted(player.getUsername()) || PlayerPunishments.isIpMuted(player.getHostAddress())) {
			player.getPacketSender().sendMessage("You are muted and cannot chat.");
			return;
		}
		ClanChatManager.sendMessage(player, Misc.filterMessage(player, clanMessage));
	}

}