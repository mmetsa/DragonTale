package com.ruthlessps.world.content.combat.magic.lunar.spells;

import java.util.concurrent.ThreadLocalRandom;

import com.ruthlessps.model.Animation;
import com.ruthlessps.model.Graphic;
import com.ruthlessps.model.Item;
import com.ruthlessps.model.MagicSpellbook;
import com.ruthlessps.world.content.combat.magic.lunar.LunarSpell;
import com.ruthlessps.world.entity.impl.player.Player;

//TODO: Add this code for spell execution in other spell books
/*if (player.spellBookSwap) {
	player.setSpellbook(MagicSpellbook.LUNAR);
}*/
public class SpellbookSwapSpell extends LunarSpell {

	@Override
	public void execute(Player player) {
		// TODO: Open some dialogue giving options for either ancients or lunar

		int rand = ThreadLocalRandom.current().nextInt(0, 1);

		if (rand == 0)
			player.setSpellbook(MagicSpellbook.ANCIENT);
		else
			player.setSpellbook(MagicSpellbook.NORMAL);

		player.spellBookSwap = true;
		player.performAnimation(new Animation(6299));
		player.performGraphic(new Graphic(1062));
	}

	@Override
	public Item[] getItemRequirements() {
		return new Item[] {

		};
	}

	@Override
	public int getLevelRequirement() {
		return 96;
	}

}
