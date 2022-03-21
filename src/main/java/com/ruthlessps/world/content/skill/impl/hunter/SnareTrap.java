package com.ruthlessps.world.content.skill.impl.hunter;

import com.ruthlessps.model.GameObject;
import com.ruthlessps.world.entity.impl.player.Player;

/**
 *
 * @author Faris
 */
public class SnareTrap extends Trap {

	private TrapState state;

	public SnareTrap(GameObject obj, TrapState state, int ticks, Player p) {
		super(obj, state, ticks, p);
	}

	/**
	 * @return the state
	 */
	public TrapState getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(TrapState state) {
		this.state = state;
	}

}
