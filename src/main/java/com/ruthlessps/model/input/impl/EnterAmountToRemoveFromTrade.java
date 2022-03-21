package com.ruthlessps.model.input.impl;

import com.ruthlessps.model.input.EnterAmount;
import com.ruthlessps.world.entity.impl.player.Player;

public class EnterAmountToRemoveFromTrade extends EnterAmount {

	public EnterAmountToRemoveFromTrade(int item) {
		super(item);
	}

	@Override
	public void handleAmount(Player player, int amount) {
		if (player.getTrading().inTrade() && getItem() > 0) {
			player.getTrading().removeTradedItem(getItem(), amount, getSlot(), true);
		} else {
			player.getPacketSender().sendInterfaceRemoval();
		}
	}

}
