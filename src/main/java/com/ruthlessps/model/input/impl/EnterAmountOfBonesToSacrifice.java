package com.ruthlessps.model.input.impl;

import com.ruthlessps.model.input.EnterAmount;
import com.ruthlessps.world.content.skill.impl.prayer.BonesOnAltar;
import com.ruthlessps.world.entity.impl.player.Player;

public class EnterAmountOfBonesToSacrifice extends EnterAmount {

	@Override
	public void handleAmount(Player player, int amount) {
		BonesOnAltar.offerBones(player, amount);
	}

}
