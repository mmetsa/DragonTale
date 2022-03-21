package com.ruthlessps.model.input.impl;

import com.ruthlessps.model.input.Input;
import com.ruthlessps.world.content.Achievements;
import com.ruthlessps.world.content.Achievements.AchievementData;
import com.ruthlessps.world.content.PlayerPanel;
import com.ruthlessps.world.entity.impl.player.Player;

public class SetEmail extends Input {

	@Override
	public void handleSyntax(Player player, String syntax) {
		player.getPacketSender().sendInterfaceRemoval();
		if (syntax.length() <= 3 || !syntax.contains("@") || syntax.endsWith("@")) {
			player.getPacketSender().sendMessage("Invalid syntax, please enter a valid one.");
			return;
		}
		if (player.getBankPinAttributes().hasBankPin() && !player.getBankPinAttributes().hasEnteredBankPin()) {
			player.getPacketSender().sendMessage("Please visit the nearest bank and enter your pin before doing this.");
			return;
		}
		if (player.getEmailAddress() != null && syntax.equalsIgnoreCase(player.getEmailAddress())) {
			player.getPacketSender().sendMessage("This is already your email-address!");
			return;
		}
		syntax = syntax.toLowerCase();
		syntax = syntax.substring(0, 1).toUpperCase() + syntax.substring(1);
		player.setEmailAddress(syntax);
		Achievements.doProgress(player, AchievementData.SET_EMAIL);
		player.getPacketSender().sendMessage("Your account's email-adress is now: " + syntax + ".");
			PlayerPanel.refreshPanel(player);
	}
}
