package com.ruthlessps.world.content;

import com.ruthlessps.world.content.minigames.impl.Nomad;
import com.ruthlessps.world.content.minigames.impl.RecipeForDisaster;
import com.ruthlessps.world.entity.impl.player.Player;

public class QuestJournal {

	public static void refreshPanel(Player player) {
		player.getPacketSender().sendString(39249, "@bla@Quest Points: @whi@" + player.getQuestPoints());
		player.getPacketSender().sendString(39250, RecipeForDisaster.getQuestTabPrefix(player) + "Recipe For Disaster");
		player.getPacketSender().sendString(39251, Nomad.getQuestTabPrefix(player) + "Nomad's Requiem");
	}
}