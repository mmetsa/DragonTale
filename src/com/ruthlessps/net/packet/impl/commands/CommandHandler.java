package com.ruthlessps.net.packet.impl.commands;

import com.ruthlessps.net.packet.Packet;
import com.ruthlessps.net.packet.PacketListener;
import com.ruthlessps.net.packet.impl.commands.administrator.AdministratorCommands;
import com.ruthlessps.net.packet.impl.commands.developer.DeveloperCommands;
import com.ruthlessps.net.packet.impl.commands.donators.DeluxeCommands;
import com.ruthlessps.net.packet.impl.commands.donators.DonatorCommands;
import com.ruthlessps.net.packet.impl.commands.donators.SponsorCommands;
import com.ruthlessps.net.packet.impl.commands.donators.SuperSponsorCommands;
import com.ruthlessps.net.packet.impl.commands.moderator.ModeratorCommands;
import com.ruthlessps.net.packet.impl.commands.owner.OwnerCommands;
import com.ruthlessps.net.packet.impl.commands.player.PlayerCommands;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.entity.impl.player.Player;

public class CommandHandler implements PacketListener {

	@Override
	public void handleMessage(Player player, Packet packet) {
		String command = Misc.readString(packet.getBuffer());
		String[] parts = command.toLowerCase().split(" ");
		if (command.contains("\r") || command.contains("\n")) {
			return;
		}
		getUserCommands(player, command, parts);
	}

	private void getUserCommands(Player player, String command, String[] parts) {
		switch (player.getDonor()) {
		case DONOR:
			DonatorCommands.execute(player, command, parts);
			break;
		case DELUXE_DONOR:
			DonatorCommands.execute(player, command, parts);
			DeluxeCommands.execute(player, command, parts);
			break;
		case SPONSOR:
			DonatorCommands.execute(player, command, parts);
			DeluxeCommands.execute(player, command, parts);
			SponsorCommands.execute(player, command, parts);
			break;
			case SUPER_SPONSOR:
			case KING:
			case VIP:
			case DRAGON:
			case VETERAN:
			case HEARTH:
			case GOLDBAG:
			case GEM:
				DonatorCommands.execute(player, command, parts);
				DeluxeCommands.execute(player, command, parts);
				SponsorCommands.execute(player, command, parts);
				SuperSponsorCommands.execute(player, command, parts);
				break;
		default:
			break;
		}
		switch (player.getRights()) {
			case HELPER:
				DonatorCommands.execute(player, command, parts);
				DeluxeCommands.execute(player, command, parts);
				SponsorCommands.execute(player, command, parts);
				PlayerCommands.execute(player, command, parts);
			break;
			case PLAYER:
			case FORUM_ADMINISTRATOR:
			case GFX_ARTIST:
			case TRIAL_FORUM_MODERATOR:
			case WEB_DEVELOPER:
				PlayerCommands.execute(player, command, parts);
			break;
			case MODERATOR:
			case TRIAL_MODERATOR:
			case GLOBAL_MODERATOR:
				PlayerCommands.execute(player, command, parts);
				ModeratorCommands.execute(player, command, parts);
			break;
			case GLOBAL_ADMINISTRATOR:
			case ADMINISTRATOR:
				PlayerCommands.execute(player, command, parts);
				ModeratorCommands.execute(player, command, parts);
				AdministratorCommands.execute(player, command, parts);
			break;
			case DEVELOPER:
			case OWNER:
				PlayerCommands.execute(player, command, parts);
				ModeratorCommands.execute(player, command, parts);
				AdministratorCommands.execute(player, command, parts);
				DeveloperCommands.execute(player, command, parts);
				OwnerCommands.execute(player, command, parts);
			break;
			default:
				System.err.println("Error with User Command Handler.");
			break;
		}
	}

}
