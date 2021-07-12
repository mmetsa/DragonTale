package com.ruthlessps.world.content.teleportation;

import com.ruthlessps.model.Skill;
import com.ruthlessps.world.content.Achievements;
import com.ruthlessps.world.content.Achievements.AchievementData;
import com.ruthlessps.world.entity.impl.player.Player;

public class TeleportSpellHandler {

	public static boolean handleSpellTeleport(Player player, int id) {
		if (player.getMovementQueue().isLockMovement())
			return false;
		TeleportSpells spell = TeleportSpells.get(id);
		if (spell == null)
			return false;
		if (!player.getClickDelay().elapsed(4500))
			return false;
		if (player.getSkillManager().getCurrentLevel(Skill.MAGIC) < spell.level) {
			player.sendMessage("You need a Magic level of " + spell.level + " to cast this spell.");
			return false;
		}
		if (!player.getInventory().containsAll(spell.runes)) {
			player.sendMessage("You do not have enough runes to cast this spell.");
			return false;
		}
		switch (spell) {
		case VARROCK:
			Achievements.doProgress(player, AchievementData.TELEPORT_VARROCK);
			break;
		case CAMELOT:
			Achievements.doProgress(player, AchievementData.TELEPORT_CAMELOT);
			break;
		case APE_ATOLL:
			Achievements.doProgress(player, AchievementData.TELEPORT_APE_ATOL);
			break;
		default:
			break;
		}
		player.getInventory().deleteItemSet(spell.runes);
		player.getSkillManager().addExperience(Skill.MAGIC, spell.experience);
		TeleportHandler.teleportPlayer(player, spell.loc, player.getSpellbook().getTeleportType());
		return true;
	}
}
