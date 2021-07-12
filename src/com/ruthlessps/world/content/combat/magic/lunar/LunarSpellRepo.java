package com.ruthlessps.world.content.combat.magic.lunar;

import java.util.HashMap;
import java.util.Map;

import com.ruthlessps.model.Skill;
import com.ruthlessps.world.content.combat.magic.lunar.spells.CureMeSpell;
import com.ruthlessps.world.content.combat.magic.lunar.spells.MagicImbueSpell;
import com.ruthlessps.world.content.combat.magic.lunar.spells.NPCContactSpell;
import com.ruthlessps.world.content.combat.magic.lunar.spells.PlankMakeSpell;
import com.ruthlessps.world.content.combat.magic.lunar.spells.SpellbookSwapSpell;
import com.ruthlessps.world.content.combat.magic.lunar.spells.StringJewlarySpell;
import com.ruthlessps.world.content.combat.magic.lunar.spells.SuperGlassMakeSpell;
import com.ruthlessps.world.entity.impl.player.Player;

public class LunarSpellRepo {
	private static Map<Integer, LunarSpell> spells = new HashMap<>();
	static {
		bind(30186, new StringJewlarySpell());
		bind(30242, new PlankMakeSpell());
		bind(30322, new SpellbookSwapSpell());
		bind(30091, new CureMeSpell());
		bind(30202, new MagicImbueSpell());

		bind(-1, new NPCContactSpell()); // TODO: FIX
		bind(30154, new SuperGlassMakeSpell()); // TODO: 30154 prints as "spellId" not buttonid?
	}

	public static boolean spellExists(int buttonId) {
		return spells.get(buttonId) != null;
	}

	public static void execute(Player player, int buttonId) {
		LunarSpell spell = spells.get(buttonId);
		if (spell != null) {

			if (player.getSkillManager().getCurrentLevel(Skill.MAGIC) < spell.getLevelRequirement()) {
				player.sendMessage("You do not have the required magic level to cast this spell.");
				return;
			}

			if (player.getInventory().contains(spell.getItemRequirements())) {
				player.sendMessage("You do not have enough runes to cast this spell.");
				return;
			}

			spell.execute(player);

		} else {
			System.err.println(buttonId + " has not been binded.");
		}
	}

	private static void bind(int buttonId, LunarSpell spell) {
		spells.put(buttonId, spell);
	}
}
