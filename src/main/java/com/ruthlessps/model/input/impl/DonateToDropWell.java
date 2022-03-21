package com.ruthlessps.model.input.impl;

import com.ruthlessps.model.input.EnterAmount;
import com.ruthlessps.world.content.DoubleDropWell;
import com.ruthlessps.world.entity.impl.player.Player;

public class DonateToDropWell extends EnterAmount {

	@Override
	public void handleAmount(Player player, int amount) {
		DoubleDropWell.donate(player, amount);
	}

}
