package com.ruthlessps.net.packet.impl;

import com.ruthlessps.model.Skill;
import com.ruthlessps.net.packet.Packet;
import com.ruthlessps.net.packet.PacketListener;
import com.ruthlessps.world.entity.impl.player.Player;

public class PrestigeSkillPacketListener implements PacketListener {

	@Override
	public void handleMessage(Player player, Packet packet) {
		int prestigeId = packet.readShort();
		Skill skill = Skill.forPrestigeId(prestigeId);
		if (skill == null) {
			return;
		}
		if (player.getInterfaceId() > 0) {
			player.getPacketSender().sendMessage("Please close all interfaces before doing this.");
			return;
		}
		player.getSkillManager().resetSkill(skill, true);
	}

}
