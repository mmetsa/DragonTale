package com.ruthlessps.model.input.impl;

import java.sql.PreparedStatement;

import com.ruthlessps.model.input.Input;
import com.ruthlessps.util.NameUtils;
import com.ruthlessps.world.entity.impl.player.Player;

import mysql.MySQLController;
import mysql.MySQLController.Database;
import mysql.MySQLDatabase;

public class ChangePassword extends Input {

	@Override
	public void handleSyntax(Player player, String syntax) {
		player.getPacketSender().sendInterfaceRemoval();
		if (syntax == null || syntax.length() <= 2 || syntax.length() > 15 || !NameUtils.isValidName(syntax)) {
			player.getPacketSender().sendMessage("That password is invalid. Please try another password.");
			return;
		}
		if (syntax.contains("_")) {
			player.getPacketSender().sendMessage("Your password can not contain underscores.");
			return;
		}
		if (player.getBankPinAttributes().hasBankPin()) {
			player.getPacketSender().sendMessage("Please visit the nearest bank and enter your pin before doing this.");
			return;
		}

		MySQLDatabase recovery = MySQLController.getController().getDatabase(Database.RECOVERY);
		if (!recovery.active || recovery.getConnection() == null) {
			player.getPacketSender().sendMessage("This service is currently unavailable.");
			return;
		}
		boolean success = false;
		try {
			PreparedStatement preparedStatement = recovery.getConnection()
					.prepareStatement("DELETE FROM users WHERE USERNAME = ?");
			preparedStatement.setString(1, player.getUsername());
			preparedStatement.executeUpdate();
			preparedStatement = recovery.getConnection()
					.prepareStatement("INSERT INTO users (username,password,email) VALUES (?, ?, ?)");
			preparedStatement.setString(1, player.getUsername());
			preparedStatement.setString(2, player.getPassword());
			preparedStatement.setString(3, syntax);
			preparedStatement.executeUpdate();
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		if (success) {
			player.setPassword(syntax);
			player.getPacketSender().sendMessage("Your account's password is now: " + syntax);
		} else {
			player.getPacketSender().sendMessage("An error occured. Please try again.");
		}
	}
}
