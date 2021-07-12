package com.ruthlessps.net.packet.impl;

import com.ruthlessps.model.Skill;
import com.ruthlessps.model.definitions.ItemDefinition;
import com.ruthlessps.net.packet.Packet;
import com.ruthlessps.net.packet.PacketListener;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.entity.impl.player.Player;

public class ExamineItemPacketListener implements PacketListener {

	@Override
	public void handleMessage(Player player, Packet packet) {
		int item = packet.readShort();
		if (item == 995 || item == 18201) {
			player.getPacketSender().sendMessage("Mhmm... Shining coins...");
			return;
		}
		ItemDefinition itemDef = ItemDefinition.forId(item);
		if (itemDef != null) {
			player.getPacketSender().sendMessage(itemDef.getDescription());
			for (Skill skill : Skill.values()) {
				if (itemDef.getRequirement()[skill.ordinal()] > player.getSkillManager().getMaxLevel(skill)) {
					player.getPacketSender().sendMessage("@red@WARNING: You need "
							+ new StringBuilder()
									.append(skill.getName().startsWith("a") || skill.getName().startsWith("e")
											|| skill.getName().startsWith("i") || skill.getName().startsWith("o")
											|| skill.getName().startsWith("u") ? "an " : "a ")
									.toString()
							+ Misc.formatText(skill.getName()) + " level of at least "
							+ itemDef.getRequirement()[skill.ordinal()] + " to wear this.");
				}
			}
		}
	}

}
