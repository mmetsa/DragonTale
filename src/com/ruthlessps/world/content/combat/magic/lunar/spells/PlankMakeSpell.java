package com.ruthlessps.world.content.combat.magic.lunar.spells;

import java.util.ArrayList;
import java.util.List;

import com.ruthlessps.model.Animation;
import com.ruthlessps.model.Graphic;
import com.ruthlessps.model.Item;
import com.ruthlessps.model.Skill;
import com.ruthlessps.world.content.combat.magic.lunar.LunarSpell;
import com.ruthlessps.world.entity.impl.player.Player;

public class PlankMakeSpell extends LunarSpell {
	class LogToPlank {
		int logId;
		int plankId;
		int xp;

		LogToPlank(int logId, int plankId, int xp) {
			this.logId = logId;
			this.plankId = plankId;
			this.xp = xp;
		}
	}

	private List<LogToPlank> planks = new ArrayList<>();

	public PlankMakeSpell() {
		planks.add(new LogToPlank(1521, 8878, 100)); // oak
	}

	@Override
	public void execute(Player player) {
		int made = 0;
		for (LogToPlank p : planks) {
			if (player.hasItem(p.logId)) {
				player.performGraphic(new Graphic(1063));
				player.performAnimation(new Animation(6298));
				player.getInventory().delete(p.logId, 1);
				player.getInventory().add(new Item(p.plankId, 1));
				player.getSkillManager().addExperience(Skill.MAGIC, p.xp);
				made++;
			}
		}

		if (made == 0) {
			player.sendMessage("You need some logs to peform this spell.");
		}

	}

	@Override
	public Item[] getItemRequirements() {
		Item[] arr = { new Item(9075, 2), };
		return arr;
	}

	@Override
	public int getLevelRequirement() {
		return 86;
	}

}
