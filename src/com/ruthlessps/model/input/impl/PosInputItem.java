package com.ruthlessps.model.input.impl;

import com.ruthlessps.model.input.Input;
import com.ruthlessps.world.entity.impl.player.Player;

public class PosInputItem extends Input {

	@Override
	public void handleSyntax(Player player, String syntax) {
		player.getPacketSender().sendClientRightClickRemoval();
		player.getPlayerOwnedShopManager().updateFilterItem(syntax);
	}
}
