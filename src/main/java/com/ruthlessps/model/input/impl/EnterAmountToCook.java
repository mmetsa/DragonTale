package com.ruthlessps.model.input.impl;

import com.ruthlessps.model.input.EnterAmount;
import com.ruthlessps.world.content.skill.impl.cooking.Cooking;
import com.ruthlessps.world.entity.impl.player.Player;

public class EnterAmountToCook extends EnterAmount {

	@Override
	public void handleAmount(Player player, int amount) {
		if (player.getSelectedSkillingItem() > 0)
			Cooking.cook(player, player.getSelectedSkillingItem(), amount);
	}

}
