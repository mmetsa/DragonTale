package com.ruthlessps.net.packet.impl.commands.donators;

import com.ruthlessps.model.PlayerRanks.DonorRights;
import com.ruthlessps.model.Position;
import com.ruthlessps.world.content.teleportation.TeleportHandler;
import com.ruthlessps.world.entity.impl.player.Player;

public class DonatorCommands {

	public static void execute(Player player, String command, String[] parts) {
		if (command.equalsIgnoreCase("dzone")) {
			TeleportHandler.teleportPlayer(player, new Position(2022, 4755, 0),
					player.getSpellbook().getTeleportType());
		}
		if(command.equalsIgnoreCase("boxdonor")) {
			if(player.getDonor() == DonorRights.DONOR) {
				if(player.getInventory().isFull()) {
					player.getPacketSender().sendMessage("@red@You need at least 1 free inventory spot!");
				} else {
					player.getInventory().add(85, 1);
					player.setDonor(DonorRights.NONE);
					player.getPacketSender().sendMessage("@red@You successfully remove your donator rank.");
				}
			} else if(player.getDonor() == DonorRights.DELUXE_DONOR) {
				if(player.getInventory().isFull()) {
					player.getPacketSender().sendMessage("@red@You need at least 1 free inventory spot!");
				} else {
					player.getInventory().add(275, 1);
					player.setDonor(DonorRights.NONE);
					player.getPacketSender().sendMessage("@red@You successfully remove your deluxe donator rank.");
				}
			} else if(player.getDonor() == DonorRights.SPONSOR) {
				if(player.getInventory().isFull()) {
					player.getPacketSender().sendMessage("@red@You need at least 1 free inventory spot!");
				} else {
					player.getInventory().add(293, 1);
					player.setDonor(DonorRights.NONE);
					player.getPacketSender().sendMessage("@red@You successfully remove your sponsor rank.");
				}
			} else if(player.getDonor() == DonorRights.SUPER_SPONSOR) {
				if(player.getInventory().isFull()) {
					player.getPacketSender().sendMessage("@red@You need at least 1 free inventory spot!");
				} else {
					player.getInventory().add(6509, 1);
					player.setDonor(DonorRights.NONE);
					player.getPacketSender().sendMessage("@red@You successfully remove your super sponsor rank.");
				}
			}
		}
	}

}
