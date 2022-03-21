package com.ruthlessps.engine.task.impl;

import com.ruthlessps.engine.task.Task;
import com.ruthlessps.model.ForceMovement;
import com.ruthlessps.model.Position;
import com.ruthlessps.world.entity.impl.player.Player;

/**
 * A {@link Task} implementation that handles forced movement. An example of
 * forced movement is the Wilderness ditch.
 * 
 * @author Professor Oak
 */
public class ForceMovementTask extends Task {

	private Player player;
	private Position end;
	private Position start;

	public ForceMovementTask(Player player, int delay, ForceMovement forceM) {
		super(delay, player, (delay == 0 ? true : false));
		this.player = player;
		this.start = forceM.getStart().copy();
		this.end = forceM.getEnd().copy();

		// Reset movement queue
		player.getMovementQueue().reset();

		// Playerupdating
		player.setForceMovement(forceM);
	}

	@Override
	protected void execute() {
		int x = start.getX() + end.getX();
		int y = start.getY() + end.getY();
		player.moveTo(new Position(x, y, player.getPosition().getZ()));
		player.setForceMovement(null);
		stop();
	}
}
