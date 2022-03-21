package com.ruthlessps.engine.task.impl;

import com.ruthlessps.engine.task.Task;
import com.ruthlessps.engine.task.TaskManager;
import com.ruthlessps.world.entity.impl.player.Player;

public class BonusExperienceTask extends Task {

	public static void addBonusXp(final Player p, int minutes) {
		boolean startEvent = p.getMinutesBonusExp() == -1;
		p.setMinutesBonusExp(startEvent ? (minutes + 1) : minutes, true);
		p.getPacketSender().sendMessage("<col=330099>You have " + p.getMinutesBonusExp() + " minutes of bonus experience left.");
		if (startEvent) {
			TaskManager.submit(new BonusExperienceTask(p));
		}
	}

	final Player player;
	int msg;

	public BonusExperienceTask(final Player player) {
		super(100, player, false);
		this.player = player;
	}

	@Override
	public void execute() {
		if (player == null || !player.isRegistered()) {
			stop();
			return;
		}
		player.setMinutesBonusExp(-1, true);
		int newMinutes = player.getMinutesBonusExp();
		if (newMinutes < 0) {
			player.getPacketSender().sendMessage("<col=330099>Your bonus experience has run out.");
			player.setMinutesBonusExp(-1, false);
			stop();
		} else if (msg == 2) {
			player.getPacketSender().sendMessage("<col=330099>You have " + player.getMinutesBonusExp() + " minutes of bonus experience left.");
			msg = 0;
		}
		msg++;
	}
}
