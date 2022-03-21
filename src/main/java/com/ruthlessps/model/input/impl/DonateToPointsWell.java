package com.ruthlessps.model.input.impl;

import com.ruthlessps.model.input.EnterAmount;
import com.ruthlessps.world.content.PointsWell;
import com.ruthlessps.world.entity.impl.player.Player;

public class DonateToPointsWell extends EnterAmount {

	@Override
	public void handleAmount(Player player, int amount) {
		PointsWell.donate(player, amount);
	}

}
