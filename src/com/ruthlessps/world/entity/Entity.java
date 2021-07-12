package com.ruthlessps.world.entity;

import com.ruthlessps.GameSettings;
import com.ruthlessps.model.Animation;
import com.ruthlessps.model.GameObject;
import com.ruthlessps.model.Graphic;
import com.ruthlessps.model.Position;
import com.ruthlessps.world.entity.impl.npc.NPC;
import com.ruthlessps.world.entity.impl.player.Player;

public class Entity {

	/**
	 * The entity's unique index.
	 */
	private int index;

	/**
	 * The entity's tile size.
	 */
	private int size = 1;

	/**
	 * The default position the entity is in.
	 */
	private Position position = GameSettings.DEFAULT_POSITION.copy();

	/**
	 * The entity's first position in current map region.
	 */
	private Position lastKnownRegion;

	/**
	 * The Entity constructor.
	 * 
	 * @param position
	 *            The position the entity is currently in.
	 */
	public Entity(Position position) {
		setPosition(position);
		lastKnownRegion = position;
	}

	/**
	 * Gets the entity's unique index.
	 * 
	 * @return The entity's index.
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Gets this entity's first position upon entering their current map region.
	 * 
	 * @return The lastKnownRegion instance.
	 */
	public Position getLastKnownRegion() {
		return lastKnownRegion;
	}

	/**
	 * Gets the entity position.
	 * 
	 * @return the entity's world position
	 */
	public Position getPosition() {
		return position;
	}

	public int getX() {
		return this.position.getX();
	}

	public int getY() {
		return this.position.getY();
	}

	/**
	 * gets the entity's tile size.
	 * 
	 * @return The size the entity occupies in the world.
	 */
	public int getSize() {
		return size;
	}

	public boolean isGameObject() {
		return this instanceof GameObject;
	}

	public boolean isNpc() {
		return this instanceof NPC;
	}

	public boolean isPlayer() {
		return this instanceof Player;
	}

	/**
	 * Performs an animation.
	 * 
	 * @param animation
	 *            The animation to perform.
	 */
	public void performAnimation(Animation animation) {

	}

	/**
	 * Performs a graphic.
	 * 
	 * @param graphic
	 *            The graphic to perform.
	 */
	public void performGraphic(Graphic graphic) {

	}

	/**
	 * Sets the entity's index.
	 * 
	 * @param index
	 *            The value the entity's index will contain.
	 * @return The Entity instance.
	 */
	public Entity setIndex(int index) {
		this.index = index;
		return this;
	}

	/**
	 * Sets the entity's current region's position.
	 * 
	 * @param lastKnownRegion
	 *            The position in which the player first entered the current region.
	 * @return The Entity instance.
	 */
	public Entity setLastKnownRegion(Position lastKnownRegion) {
		this.lastKnownRegion = lastKnownRegion;
		return this;
	}

	/**
	 * Sets the entity position
	 * 
	 * @param position
	 *            the world position
	 */
	public Entity setPosition(Position position) {
		this.position = position;
		return this;
	}

	/**
	 * Sets the entity's tile size
	 * 
	 * @return The Entity instance.
	 */
	public Entity setSize(int size) {
		this.size = size;
		return this;
	}
}
