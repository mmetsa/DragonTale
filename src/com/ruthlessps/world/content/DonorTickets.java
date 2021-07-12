package com.ruthlessps.world.content;

import com.ruthlessps.model.PlayerRanks.DonorRights;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.content.dialogue.Dialogue;
import com.ruthlessps.world.content.dialogue.DialogueExpression;
import com.ruthlessps.world.content.dialogue.DialogueManager;
import com.ruthlessps.world.content.dialogue.DialogueType;
import com.ruthlessps.world.entity.impl.player.Player;

public class DonorTickets {

	public static void checkForRankUpdate(Player player) {
		if (player.getRights().isStaff()) {
			return;
		}
		DonorRights rights = null;
		if (player.getAmountDonated() >= 20)
			rights = DonorRights.DONOR;
		if (player.getAmountDonated() >= 50)
			rights = DonorRights.DELUXE_DONOR;
		if (player.getAmountDonated() >= 100)
			rights = DonorRights.SPONSOR;
		if (rights != null && rights != player.getDonor()) {
			player.getPacketSender().sendMessage("You're now a" + Misc.formatText(rights.toString().toLowerCase())
					+ "! Thank you for your support!");
			player.setDonor(rights);
			player.getPacketSender().sendRights();
		}
	}

	public static Dialogue getTotalFunds(final Player player) {
		return new Dialogue() {

			@Override
			public DialogueExpression animation() {
				return DialogueExpression.NORMAL;
			}

			@Override
			public String[] dialogue() {
				return player.getAmountDonated() > 0
						? new String[] { "Your account has donated $" + player.getAmountDonated() + " in total.",
								"Thank you for supporting us!" }
						: new String[] { "Your account has donated $" + player.getAmountDonated() + " in total." };
			}

			@Override
			public Dialogue nextDialogue() {
				return DialogueManager.getDialogues().get(5);
			}

			@Override
			public int npcId() {
				return 4657;
			}

			@Override
			public DialogueType type() {
				return DialogueType.NPC_STATEMENT;
			}
		};
	}

	public static boolean handleTicket(Player player, int item) {
		switch (item) {
		case 10942:
		case 10934:
		case 10935:
		case 10943:
			int funds = item == 10942 ? 10 : item == 10934 ? 20 : item == 10935 ? 50 : item == 10943 ? 100 : -1;
			player.getInventory().delete(item, 1);
			player.incrementAmountDonated(funds);
			player.getPacketSender().sendMessage("Your account has gained funds worth $" + funds
					+ ". Your total is now at $" + player.getAmountDonated() + ".");
			checkForRankUpdate(player);
			PlayerPanel.refreshPanel(player);
			break;
		}
		return false;
	}
}
