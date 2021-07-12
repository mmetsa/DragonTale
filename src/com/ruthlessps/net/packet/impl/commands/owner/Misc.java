package com.ruthlessps.net.packet.impl.commands.owner;

import com.ruthlessps.CharacterBackup;
import com.ruthlessps.model.Flag;
import com.ruthlessps.model.Item;
import com.ruthlessps.model.definitions.ItemDefinition;
import com.ruthlessps.world.World;
import com.ruthlessps.world.entity.impl.player.Player;

public class Misc {

	public static void checkCommands(Player player, String wholeCommand, String[] command) {
		if (command[0].equalsIgnoreCase("giveitem")) {
			String targetName = wholeCommand
					.substring(command[0].length() + 1 + command[1].length() + 1 + command[2].length() + 1);
			Player target = World.getPlayerByName(targetName);
			if (target == null) {
				player.getPacketSender().sendMessage("No such player exists.");
				return;
			}
			if (target.getInventory().isFull()) {
				player.getPacketSender().sendMessage("This players inventory is full plese tell them.");
				target.getPacketSender()
						.sendMessage(player.getUsername() + " is trying to give you an item, please make some room.");
				return;
			}
			int itemId = Integer.parseInt(command[1]);
			int amount = Integer.parseInt(command[2]);
			player.getPacketSender().sendMessage("You give " + target.getUsername() + " x" + amount + " of "
					+ ItemDefinition.forId(itemId).getName() + ".");
			target.getPacketSender().sendMessage("You have been given x" + amount + " of "
					+ ItemDefinition.forId(itemId).getName() + " by " + player.getUsername() + ".");
			target.getInventory().add(new Item(itemId, amount));
		}
		if (command[0].equalsIgnoreCase("toggleinvis")) {
			player.setNpcTransformationId(player.getNpcTransformationId() > 0 ? -1 : 8254);
			player.getUpdateFlag().flag(Flag.APPEARANCE);
		}
		if (command[0].equalsIgnoreCase("resetinv")) {
			String targetName = wholeCommand.substring(command[0].length() + 1);
			Player target = World.getPlayerByName(targetName);
			if (target == null) {
				player.getPacketSender().sendConsoleMessage("No such player exists.");
				return;
			}
			target.getInventory().resetItems().refreshItems();
			target.getPacketSender().sendMessage("Your inventory has been cleared by " + player.getUsername());
			player.getPacketSender()
					.sendMessage("You have successfully cleared " + target.getUsername() + "'s inventory.");
		}
		if(command[0].equals("backup")) {
			CharacterBackup.timer.reset(0);
			player.getPacketSender().sendConsoleMessage("Backup Method Called.");
		}
		if (command[0].equalsIgnoreCase("resetbank")) {
			String targetName = wholeCommand.substring(command[0].length() + 1);
			Player target = World.getPlayerByName(targetName);
			if (target == null) {
				player.getPacketSender().sendConsoleMessage("No such player exists.");
				return;
			}
			for (int i = 0; i < target.getBanks().length; i++) {
				target.getBank(i).resetItems().refreshItems();
			}
		}
	}
}
