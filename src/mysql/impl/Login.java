package mysql.impl;

import java.sql.PreparedStatement;

import com.ruthlessps.world.World;

import mysql.MySQLController;
import mysql.MySQLController.Database;
import mysql.MySQLDatabase;

public class Login {

	public static void login() {
		MySQLDatabase playersonline = MySQLController.getController().getDatabase(Database.RECOVERY);
		if (!playersonline.active || playersonline.getConnection() == null) {
			return;
		}
		try {
			PreparedStatement preparedStatement = playersonline.getConnection()
					.prepareStatement("DELETE FROM players WHERE id = 1");
			preparedStatement.executeUpdate();
			preparedStatement = playersonline.getConnection()
					.prepareStatement("INSERT INTO players (id, online) VALUES (?, ?)");
			preparedStatement.setInt(1, 1);
			preparedStatement.setInt(2, World.getPlayers().size());
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
