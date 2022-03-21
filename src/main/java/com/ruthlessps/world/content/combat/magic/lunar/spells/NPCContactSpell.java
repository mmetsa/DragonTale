package com.ruthlessps.world.content.combat.magic.lunar.spells;

import com.ruthlessps.model.Animation;
import com.ruthlessps.model.Graphic;
import com.ruthlessps.model.Item;
import com.ruthlessps.world.content.combat.magic.lunar.LunarSpell;
import com.ruthlessps.world.entity.impl.player.Player;

public class NPCContactSpell extends LunarSpell {

	@Override
	public void execute(Player player) {
		// TODO: Open dialogue of target npc

		player.performAnimation(new Animation(4412));
		player.performGraphic(new Graphic(729));
	}

	@Override
	public Item[] getItemRequirements() {
		return new Item[] {

		};
	}

	@Override
	public int getLevelRequirement() {
		return 67;
	}

}
