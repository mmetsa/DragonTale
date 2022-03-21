package com.ruthlessps.world.content.skill.impl.herblore;

import com.ruthlessps.engine.task.Task;
import com.ruthlessps.engine.task.TaskManager;
import com.ruthlessps.engine.task.impl.QuestAssigner;
import com.ruthlessps.model.Animation;
import com.ruthlessps.model.Item;
import com.ruthlessps.model.Skill;
import com.ruthlessps.model.definitions.ItemDefinition;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.content.Achievements;
import com.ruthlessps.world.content.Achievements.AchievementData;
import com.ruthlessps.world.entity.impl.player.Player;

public class Herblore {

	enum SpecialPotion {
		EXTREME_ATTACK(new Item[] { new Item(145), new Item(261) }, new Item(15309), 88, 4430), EXTREME_STRENGTH(
				new Item[] { new Item(157), new Item(267) }, new Item(15313), 88,
				4753), EXTREME_DEFENCE(new Item[] { new Item(163), new Item(2481) }, new Item(15317), 90,
						5002), EXTREME_MAGIC(new Item[] { new Item(3042), new Item(9594) }, new Item(15321), 91,
								5408), EXTREME_RANGED(new Item[] { new Item(169), new Item(12539, 5) }, new Item(15325),
										92, 5924), OVERLOAD(new Item[] { new Item(15309), new Item(15313),
												new Item(15317), new Item(15321), new Item(15325) }, new Item(15333),
												96, 13103);

		public static SpecialPotion forItems(int item1, int item2) {
			for (SpecialPotion potData : SpecialPotion.values()) {
				int found = 0;
				for (Item it : potData.getIngridients()) {
					if (it.getId() == item1 || it.getId() == item2)
						found++;
				}
				if (found >= 2)
					return potData;
			}
			return null;
		}

		private Item[] ingridients;

		private Item product;

		private int lvlReq, exp;

		SpecialPotion(Item[] ingridients, Item product, int lvlReq, int exp) {
			this.ingridients = ingridients;
			this.product = product;
			this.lvlReq = lvlReq;
			this.exp = exp;
		}

		public int getExperience() {
			return exp;
		}

		public Item[] getIngridients() {
			return ingridients;
		}

		public int getLevelReq() {
			return lvlReq;
		}

		public Item getProduct() {
			return product;
		}
	}

	public static final int VIAL = 227;

	private static final Animation ANIMATION = new Animation(363);

	public static boolean cleanHerb(final Player player, final int herbId) {
		Herbs herb = Herbs.forId(herbId);
		if (herb == null) {
			return false;
		}
		if (player.getInventory().contains(herb.getGrimyHerb())) {
			if (player.getSkillManager().getCurrentLevel(Skill.HERBLORE) < herb.getLevelReq()) {
				player.getPacketSender().sendMessage(
						"You need a Herblore level of at least " + herb.getLevelReq() + " to clean this leaf.");
				return false;
			}
			player.getInventory().delete(herb.getGrimyHerb(), 1);
			player.getInventory().add(herb.getCleanHerb(), 1);
			Achievements.doProgress(player, AchievementData.CLEAN_HERB);
			Achievements.doProgress(player, AchievementData.CLEAN_50_HERBS);
			Achievements.doProgress(player, AchievementData.CLEAN_500_HERBS);
			player.getSkillManager().addExperience(Skill.HERBLORE, herb.getExp());
			player.getPacketSender().sendMessage("You clean the dirt off the leaf.");
			if(herb == herb.GUAM) {
				QuestAssigner.handleQuest(player, 73);
			} else if(herb == herb.MARRENTILL) {
				QuestAssigner.handleQuest(player, 74);
			} else if(herb == herb.TARROMIN) {
				QuestAssigner.handleQuest(player, 75);
			} else if(herb == herb.HARRALANDER) {
				QuestAssigner.handleQuest(player, 76);
			} else if(herb == herb.RANARR) {
				QuestAssigner.handleQuest(player, 77);
			} else if(herb == herb.IRIT) {
				QuestAssigner.handleQuest(player, 78);
			} else if(herb == herb.AVANTOE) {
				QuestAssigner.handleQuest(player, 79);
			} else if(herb == herb.KWUARM) {
				QuestAssigner.handleQuest(player, 80);
			} else if(herb == herb.CADANTINE) {
				QuestAssigner.handleQuest(player, 81);
			} else if(herb == herb.DWARFWEED) {
				QuestAssigner.handleQuest(player, 82);
			} else if(herb == herb.TORSTOL) {
				QuestAssigner.handleQuest(player, 83);
			}
			return true;
		}
		return false;
	}

	public static boolean finishPotion(final Player player, final int itemUsed, final int usedWith) {
		final FinishedPotions pot = FinishedPotions.forId(itemUsed, usedWith);
		if (pot == FinishedPotions.MISSING_INGRIDIENTS) {
			player.getPacketSender().sendMessage("You don't have the required items to make this potion.");
			return false;
		}
		if (pot == null) {
			handleSpecialPotion(player, itemUsed, usedWith);
			return false;
		}
		if (player.getSkillManager().getCurrentLevel(Skill.HERBLORE) < pot.getLevelReq()) {
			player.getPacketSender().sendMessage(
					"You need a Herblore level of at least " + pot.getLevelReq() + " to make this potion.");
			return false;
		}
		if (player.getInventory().contains(pot.getUnfinishedPotion())
				&& player.getInventory().contains(pot.getItemNeeded())) {
			player.getSkillManager().stopSkilling();
			player.performAnimation(ANIMATION);
			TaskManager.submit(new Task(1, player, false) {
				@Override
				public void execute() {
					player.getInventory().delete(pot.getUnfinishedPotion(), 1).delete(pot.getItemNeeded(), 1)
							.add(pot.getFinishedPotion(), 1);
					player.getSkillManager().addExperience(Skill.HERBLORE, (pot.getExpGained() * 80));
					String name = ItemDefinition.forId(pot.getFinishedPotion()).getName();
					player.getPacketSender()
							.sendMessage("You combine the ingredients to make " + Misc.anOrA(name) + " " + name + ".");
					this.stop();
				}
			});
			return true;
		} else
			player.getPacketSender().sendMessage("You don't have the required items to make this potion.");
		return false;
	}

	public static void handleSpecialPotion(Player p, int item1, int item2) {
		if (item1 == item2)
			return;
		if (!p.getInventory().contains(item1) || !p.getInventory().contains(item2))
			return;
		SpecialPotion specialPotData = SpecialPotion.forItems(item1, item2);
		if (specialPotData == null)
			return;
		if (p.getSkillManager().getCurrentLevel(Skill.HERBLORE) < specialPotData.getLevelReq()) {
			p.getPacketSender().sendMessage(
					"You need a Herblore level of at least " + specialPotData.getLevelReq() + " to make this potion.");
			return;
		}
		if (!p.getClickDelay().elapsed(500))
			return;
		for (Item ingridients : specialPotData.getIngridients()) {
			if (!p.getInventory().contains(ingridients.getId())
					|| p.getInventory().getAmount(ingridients.getId()) < ingridients.getAmount()) {
				p.getPacketSender().sendMessage("You do not have all ingridients for this potion.");
				p.getPacketSender()
						.sendMessage("Remember: You can purchase an Ingridient's book from the Druid Spirit.");
				return;
			}
		}
		for (Item ingridients : specialPotData.getIngridients())
			p.getInventory().delete(ingridients);
		p.getInventory().add(specialPotData.getProduct());
		p.performAnimation(new Animation(363));
		p.getSkillManager().addExperience(Skill.HERBLORE, specialPotData.getExperience());
		String name = specialPotData.getProduct().getDefinition().getName();
		p.getPacketSender().sendMessage("You make " + Misc.anOrA(name) + " " + name + ".");
		p.getClickDelay().reset();
	}

	public static boolean makeUnfinishedPotion(final Player player, final int herbId) {
		final UnfinishedPotions unf = UnfinishedPotions.forId(herbId);
		if (unf == null)
			return false;
		if (player.getSkillManager().getCurrentLevel(Skill.HERBLORE) < unf.getLevelReq()) {
			player.getPacketSender().sendMessage(
					"You need a Herblore level of at least " + unf.getLevelReq() + " to make this potion.");
			return false;
		}
		if (player.getInventory().contains(VIAL) && player.getInventory().contains(unf.getHerbNeeded())) {
			player.getSkillManager().stopSkilling();
			player.performAnimation(ANIMATION);
			TaskManager.submit(new Task(1, player, false) {
				@Override
				public void execute() {
					player.getInventory().delete(VIAL, 1).delete(unf.getHerbNeeded(), 1).add(unf.getUnfPotion(), 1);
					player.getPacketSender().sendMessage("You put the "
							+ ItemDefinition.forId(unf.getHerbNeeded()).getName() + " into the vial of water.");
					player.getSkillManager().addExperience(Skill.HERBLORE, 1);
					this.stop();
				}
			});
			return true;
		}
		return false;
	}
}
