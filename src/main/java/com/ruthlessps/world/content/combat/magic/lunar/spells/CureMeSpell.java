package com.ruthlessps.world.content.combat.magic.lunar.spells;

import com.ruthlessps.model.Item;
import com.ruthlessps.world.content.combat.magic.lunar.LunarSpell;
import com.ruthlessps.world.entity.impl.player.Player;

public class CureMeSpell extends LunarSpell {

	@Override
	public void execute(Player player) {
		if (player.isPoisoned()) {
			player.setPoisonDamage(0);
			player.sendMessage("You have been cursed of poison.");
		} else {
			player.sendMessage("You are not poisoned.");
		}
	}

	@Override
	public Item[] getItemRequirements() {
		return new Item[] {

		};
	}

	@Override
	public int getLevelRequirement() {
		return 71;
	}

}
