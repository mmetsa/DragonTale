package com.ruthlessps.engine.task.impl;

import com.ruthlessps.engine.task.Task;
import com.ruthlessps.model.Locations;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.World;

/**
 * @author Gabriel Hannason
 */
public class ServerTimeUpdateTask extends Task {

	private int tick = 0;

	public ServerTimeUpdateTask() {
		super(40);
	}

	@Override
	protected void execute() {

		if (tick >= 6 && (Locations.PLAYERS_IN_WILD >= 3 || Locations.PLAYERS_IN_DUEL_ARENA >= 3)) {
			if (Locations.PLAYERS_IN_WILD > Locations.PLAYERS_IN_DUEL_ARENA
					|| Misc.getRandom(3) == 1 && Locations.PLAYERS_IN_WILD >= 2) {
				World.sendMessage(
						"@blu@There are currently " + Locations.PLAYERS_IN_WILD + " players roaming the Wilderness!");
			} else if (Locations.PLAYERS_IN_DUEL_ARENA > Locations.PLAYERS_IN_WILD) {
				World.sendMessage(
						"@blu@There are currently " + Locations.PLAYERS_IN_DUEL_ARENA + " players at the Duel Arena!");
			}
			tick = 0;
		}

		tick++;
	}
}