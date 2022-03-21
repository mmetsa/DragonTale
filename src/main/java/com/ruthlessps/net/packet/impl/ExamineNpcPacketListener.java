package com.ruthlessps.net.packet.impl;

import com.ruthlessps.drops.NPCDrops;
import com.ruthlessps.model.Position;
import com.ruthlessps.model.definitions.NpcDefinition;
import com.ruthlessps.net.packet.Packet;
import com.ruthlessps.net.packet.PacketListener;
import com.ruthlessps.world.content.KillsTracker;
import com.ruthlessps.world.entity.impl.npc.NPC;
import com.ruthlessps.world.entity.impl.player.Player;

public class ExamineNpcPacketListener implements PacketListener {

	@Override
	public void handleMessage(Player player, Packet packet) {
		int npc = packet.readShort();
		if (npc <= 0) {
			return;
		}
		NpcDefinition npcDef = NpcDefinition.forId(npc);
		if (npcDef != null) {
			player.getPacketSender().sendMessage("Your " + npcDef.getName() + " kc is "+player.getDropKillCount(npc)+"");
			NPCDrops.loadDropTable(player, new NPC(npc, new Position(0,0,0)));
		}
	}

}