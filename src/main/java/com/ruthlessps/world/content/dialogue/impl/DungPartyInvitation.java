package com.ruthlessps.world.content.dialogue.impl;

import com.ruthlessps.world.content.dialogue.Dialogue;
import com.ruthlessps.world.content.dialogue.DialogueExpression;
import com.ruthlessps.world.content.dialogue.DialogueType;
import com.ruthlessps.world.entity.impl.player.Player;

/**
 * Represents a Dungeoneering party invitation dialogue
 * 
 * @author Gabriel Hannason
 */

public class DungPartyInvitation extends Dialogue {

	private Player inviter, p;

	public DungPartyInvitation(Player inviter, Player p) {
		this.inviter = inviter;
		this.p = p;
	}

	@Override
	public DialogueExpression animation() {
		return null;
	}

	@Override
	public String[] dialogue() {
		return new String[] { "" + inviter.getUsername() + " has invited you to join their Dungeoneering party." };
	}

	@Override
	public Dialogue nextDialogue() {
		return new Dialogue() {

			@Override
			public DialogueExpression animation() {
				return null;
			}

			@Override
			public String[] dialogue() {
				return new String[] { "Join " + inviter.getUsername() + "'s party",
						"Don't join " + inviter.getUsername() + "'s party." };
			}

			@Override
			public void specialAction() {
				p.getMinigameAttributes().getDungeoneeringAttributes()
						.setPartyInvitation(inviter.getMinigameAttributes().getDungeoneeringAttributes().getParty());
				p.setDialogueActionId(66);
			}

			@Override
			public DialogueType type() {
				return DialogueType.OPTION;
			}

		};
	}

	@Override
	public int npcId() {
		return -1;
	}

	@Override
	public DialogueType type() {
		return DialogueType.STATEMENT;
	}
}