package com.ruthlessps.model.input.impl;

import com.ruthlessps.model.input.Input;
import com.ruthlessps.world.content.clan.ClanChatManager;
import com.ruthlessps.world.entity.impl.player.Player;

public class EnterClanChatToJoin extends Input {

	@Override
	public void handleSyntax(Player player, String syntax) {
		if (syntax.length() <= 1) {
			player.getPacketSender().sendMessage("Invalid syntax entered.");
			return;
		}
		ClanChatManager.join(player, syntax);
	}
}
