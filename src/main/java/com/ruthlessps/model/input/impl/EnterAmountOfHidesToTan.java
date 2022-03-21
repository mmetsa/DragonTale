package com.ruthlessps.model.input.impl;

import com.ruthlessps.model.input.EnterAmount;
import com.ruthlessps.world.content.skill.impl.crafting.Tanning;
import com.ruthlessps.world.entity.impl.player.Player;

public class EnterAmountOfHidesToTan extends EnterAmount {

	private int button;

	public EnterAmountOfHidesToTan(int button) {
		this.button = button;
	}

	@Override
	public void handleAmount(Player player, int amount) {
		Tanning.tanHide(player, button, amount);
	}

}
