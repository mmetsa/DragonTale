package com.ruthlessps.model.input.impl;

import com.ruthlessps.model.input.EnterAmount;
import com.ruthlessps.world.content.skill.impl.summoning.PouchMaking;
import com.ruthlessps.world.entity.impl.player.Player;

public class EnterAmountToInfuse extends EnterAmount {

	@Override
	public void handleAmount(Player player, int amount) {
		if (player.getInterfaceId() != 63471) {
			player.getPacketSender().sendInterfaceRemoval();
			return;
		}
		if (amount >= 29) {
			amount = 28;
		}
		PouchMaking.infusePouches(player, amount);
	}

}
