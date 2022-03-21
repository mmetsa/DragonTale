package com.ruthlessps.world.content.dialogue.impl;

import com.ruthlessps.world.content.dialogue.Dialogue;
import com.ruthlessps.world.content.dialogue.DialogueExpression;
import com.ruthlessps.world.content.dialogue.DialogueManager;
import com.ruthlessps.world.content.dialogue.DialogueType;
import com.ruthlessps.world.entity.impl.player.Player;

public class Mandrith {

	public static Dialogue getDialogue(Player player) {
		return new Dialogue() {

			@Override
			public DialogueExpression animation() {
				return DialogueExpression.NORMAL;
			}

			@Override
			public String[] dialogue() {
				String KDR = "N/A";
				int kc = player.getPlayerKillingAttributes().getPlayerKills();
				int dc = player.getPlayerKillingAttributes().getPlayerDeaths();
				if (kc >= 5 && dc >= 5) {
					KDR = String.valueOf((double) (kc / dc));
				}
				return new String[] {
						"You have killed " + player.getPlayerKillingAttributes().getPlayerKills()
								+ " players. You have died " + player.getPlayerKillingAttributes().getPlayerDeaths()
								+ " times.",
						"You currently have a killstreak of "
								+ player.getPlayerKillingAttributes().getPlayerKillStreak() + " and your",
						"KDR is currently " + KDR + "." };
			}

			@Override
			public Dialogue nextDialogue() {
				return DialogueManager.getDialogues().get(19);
			}

			@Override
			public int npcId() {
				return 8725;
			}

			@Override
			public DialogueType type() {
				return DialogueType.NPC_STATEMENT;
			}
		};
	}
}
