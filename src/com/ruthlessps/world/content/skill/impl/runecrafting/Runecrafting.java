package com.ruthlessps.world.content.skill.impl.runecrafting;

import com.ruthlessps.model.Animation;
import com.ruthlessps.model.GameMode;
import com.ruthlessps.model.Graphic;
import com.ruthlessps.model.Position;
import com.ruthlessps.model.Skill;
import com.ruthlessps.world.content.Achievements;
import com.ruthlessps.world.content.Achievements.AchievementData;
import com.ruthlessps.world.content.skill.impl.runecrafting.RunecraftingData.TalismanData;
import com.ruthlessps.world.content.teleportation.TeleportHandler;
import com.ruthlessps.world.entity.impl.player.Player;

/**
 * Handles the Runecrafting skill
 * 
 * @author Gabriel Hannason
 */
public class Runecrafting {

	public static boolean canRuneCraft(Player player, RunecraftingData.RuneData rune) {
		if (rune == null)
			return false;
		if (player.getSkillManager().getMaxLevel(Skill.RUNECRAFTING) < rune.getLevelRequirement()) {
			player.getPacketSender().sendMessage(
					"You need a Runecrafting level of at least " + rune.getLevelRequirement() + " to craft this.");
			return false;
		}
		if (rune.pureRequired() && !player.getInventory().contains(7936) && !player.getInventory().contains(1436)) {
			player.getPacketSender().sendMessage("You do not have any Pure essence in your inventory.");
			return false;
		} else if (rune.pureRequired() && !player.getInventory().contains(7936)
				&& player.getInventory().contains(1436)) {
			player.getPacketSender().sendMessage("Only Pure essence has the power to bind this altar's energy.");
			return false;
		}
		if (!player.getInventory().contains(7936) && !player.getInventory().contains(1436)) {
			player.getPacketSender().sendMessage("You do not have any Rune or Pure essence in your inventory.");
			return false;
		}
		if (!player.getClickDelay().elapsed(4500))
			return false;
		return true;
	}

	public static void craftRunes(final Player player, RunecraftingData.RuneData rune) {
		if (!canRuneCraft(player, rune))
			return;
		int essence = -1;
		if (player.getInventory().contains(1436) && !rune.pureRequired())
			essence = 1436;
		if (player.getInventory().contains(7936) && essence < 0)
			essence = 7936;
		if (essence == -1)
			return;
		player.performGraphic(new Graphic(186));
		player.performAnimation(new Animation(791));
		int amountToMake = RunecraftingData.getMakeAmount(rune, player);
		int amountMade = 0;
		for (int i = 28; i > 0; i--) {
			if (!player.getInventory().contains(essence))
				break;
			player.getInventory().delete(essence, 1);
			if(player.getGameMode() == GameMode.HARDCORE && player.getEquipment().contains(9765)) {
				player.getInventory().add(rune.getRuneID(), amountToMake*2);
			} else {
				player.getInventory().add(rune.getRuneID(), amountToMake);
			}
			amountMade += amountToMake;
			player.getSkillManager().addExperience(Skill.RUNECRAFTING, rune.getXP());
		}
		Achievements.doProgress(player, AchievementData.CRAFT_SOME_RUNES, amountMade);
		Achievements.doProgress(player, AchievementData.CRAFT_50_RUNES, amountMade);
		Achievements.doProgress(player, AchievementData.CRAFT_500_RUNES,amountMade);
		player.performGraphic(new Graphic(129));
		player.getSkillManager().addExperience(Skill.RUNECRAFTING, rune.getXP());
		player.getPacketSender().sendMessage("You bind the altar's power into " + rune.getName() + "s..");
		player.getClickDelay().reset();
	}

	public static void handleTalisman(Player player, int ID) {
		TalismanData talisman = RunecraftingData.TalismanData.forId(ID);
		if (talisman == null)
			return;
		if (player.getSkillManager().getMaxLevel(Skill.RUNECRAFTING) < talisman.getLevelRequirement()) {
			player.getPacketSender().sendMessage("You need a Runecrafting level of at least "
					+ talisman.getLevelRequirement() + " to use this Talisman's teleport function.");
			return;
		}
		Position targetLocation = talisman.getLocation();
		TeleportHandler.teleportPlayer(player, targetLocation, player.getSpellbook().getTeleportType());
	}

	public static boolean runecraftingAltar(Player player, int ID) {
		return ID >= 2478 && ID < 2489 || ID == 17010 || ID == 30624 || ID == 47120;
	}

}
