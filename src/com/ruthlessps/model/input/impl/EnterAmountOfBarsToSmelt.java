package com.ruthlessps.model.input.impl;

import com.ruthlessps.model.input.EnterAmount;
import com.ruthlessps.world.content.skill.impl.smithing.Smelting;
import com.ruthlessps.world.content.skill.impl.smithing.SmithingData;
import com.ruthlessps.world.entity.impl.player.Player;

public class EnterAmountOfBarsToSmelt extends EnterAmount {

	private int bar;

	public EnterAmountOfBarsToSmelt(int bar) {
		this.bar = bar;
	}

	public int getBar() {
		return bar;
	}

	@Override
	public void handleAmount(Player player, int amount) {
		for (int barId : SmithingData.SMELT_BARS) {
			if (barId == bar) {
				Smelting.smeltBar(player, barId, amount);
				break;
			}
		}
	}

}
