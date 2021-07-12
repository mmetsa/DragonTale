package com.ruthlessps.world.content;

import com.ruthlessps.world.content.dialogue.DialogueManager;
import com.ruthlessps.world.entity.impl.player.Player;

public class Morphing {

	public static void rub(Player player, int item) {
		if (player.getInterfaceId() > 0)
			player.getPacketSender().sendInterfaceRemoval();
		player.setDialogueActionId(15000);
		DialogueManager.start(player, 15000);
		player.setSelectedSkillingItem(item);
	}

	public static void Next(Player player, int item) {
		if (player.getInterfaceId() > 0)
			player.getPacketSender().sendInterfaceRemoval();
		player.setDialogueActionId(15001);
		DialogueManager.start(player, 15001);
		player.setSelectedSkillingItem(item);
	}

	public static void Next2(Player player, int item) {
		if (player.getInterfaceId() > 0)
			player.getPacketSender().sendInterfaceRemoval();
		player.setDialogueActionId(15001);
		DialogueManager.start(player, 15002);
		player.setSelectedSkillingItem(item);
	}

	public static void Next3(Player player, int item) {
		if (player.getInterfaceId() > 0)
			player.getPacketSender().sendInterfaceRemoval();
		player.setDialogueActionId(15003);
		DialogueManager.start(player, 15003);
		player.setSelectedSkillingItem(item);
	}

}
