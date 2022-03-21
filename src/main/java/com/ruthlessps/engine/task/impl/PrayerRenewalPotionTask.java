package com.ruthlessps.engine.task.impl;

import com.ruthlessps.engine.task.Task;
import com.ruthlessps.model.Graphic;
import com.ruthlessps.model.Skill;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.entity.impl.player.Player;

public class PrayerRenewalPotionTask extends Task {

	final Player player;

	public PrayerRenewalPotionTask(Player player) {
		super(1, player, true);
		this.player = player;
	}

	@Override
	public void execute() {
		if (player == null || !player.isRegistered()) {
			stop();
			return;
		}
		player.setPrayerRenewalPotionTimer(player.getPrayerRenewalPotionTimer() - 1);
		if (player.getPrayerRenewalPotionTimer() > 0) {
			if (player.getSkillManager().getCurrentLevel(Skill.PRAYER) < player.getSkillManager()
					.getMaxLevel(Skill.PRAYER)) {
				player.getSkillManager().setCurrentLevel(Skill.PRAYER,
						player.getSkillManager().getCurrentLevel(Skill.PRAYER) + 1);
				if (player.getSkillManager().getCurrentLevel(Skill.PRAYER) > player.getSkillManager()
						.getMaxLevel(Skill.PRAYER))
					player.getSkillManager().setCurrentLevel(Skill.PRAYER,
							player.getSkillManager().getMaxLevel(Skill.PRAYER));
			}
			if (player.getPrayerRenewalPotionTimer() == 20)
				player.getPacketSender().sendMessage("Your Prayer Renewal's effect is about to run out.");
			if (Misc.getRandom(10) <= 2)
				player.performGraphic(new Graphic(1300));
		} else {
			player.getPacketSender().sendMessage("Your Prayer Renewal's effect has run out");
			player.setPrayerRenewalPotionTimer(0);
			stop();
		}
	}
}
