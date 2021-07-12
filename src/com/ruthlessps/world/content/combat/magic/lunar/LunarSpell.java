package com.ruthlessps.world.content.combat.magic.lunar;

import com.ruthlessps.model.Item;
import com.ruthlessps.world.entity.impl.player.Player;

public abstract class LunarSpell {
	public abstract void execute(Player player);

	public abstract Item[] getItemRequirements();

	public abstract int getLevelRequirement();
}
