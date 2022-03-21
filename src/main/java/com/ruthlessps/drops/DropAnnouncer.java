package com.ruthlessps.drops;

import com.ruthlessps.drops.NPCDrops.NpcDrop;
import com.ruthlessps.model.definitions.ItemDefinition;
import com.ruthlessps.world.World;
import com.ruthlessps.world.entity.impl.player.Player;

class DropAnnouncer {

	static boolean shouldAnnounce(NpcDrop drop) {
		if(drop.getId() == 6199 || drop.getId() == 989) {
			return false;
		}
		return drop.getChance() >= 512;
	}

	static void announce(Player player, int itemId, int npcId, int amount, int value) {
		String rare_text = value > 1024 ? "mega rare" : "very rare";
		String itemName = ItemDefinition.forId(itemId).getName();
		for(Player player1:World.getPlayers()) {
			if(player1 != null) {
				if(player1.getQuestLevel() == 1) {
					int amt = player.getDropKillCount(npcId)+1;
					player1.getPacketSender().sendMessage("<img=10><col=97FF00><shad=0>"+
						player.getUsername() + "<col=4></shad> has received <col=FF0000>" + amount + "x " + itemName + "<col=4> as a <col=FF0000>" + rare_text + "<col=4> drop! (kill #<col=FF0000>"+amt+"<col=4>)");
				}
				}
		}
		//World.sendMessage("<img=10><col=97FF00><shad=0>"+
			//	player.getUsername() + "<col=7800FF><shad=0> has received <col=FF0000><shad=0>" + amount + "x " + itemName + "<col=7800FF><shad=0> as a <col=FF0000><shad=0>" + rare_text + "<col=7800FF><shad=0> drop!");
	}
}