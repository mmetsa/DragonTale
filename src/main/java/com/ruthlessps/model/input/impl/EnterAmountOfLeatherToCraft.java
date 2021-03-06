package com.ruthlessps.model.input.impl;

import com.ruthlessps.model.input.EnterAmount;
import com.ruthlessps.world.content.skill.impl.crafting.LeatherMaking;
import com.ruthlessps.world.content.skill.impl.crafting.leatherData;
import com.ruthlessps.world.entity.impl.player.Player;

public class EnterAmountOfLeatherToCraft extends EnterAmount {

	@Override
	public void handleAmount(Player player, int amount) {
		for (final leatherData l : leatherData.values()) {
			if (player.getSelectedSkillingItem() == l.getLeather()) {
				LeatherMaking.craftLeather(player, l, amount);
				break;
			}
		}
	}
}
