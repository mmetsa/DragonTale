package com.ruthlessps.world.content.skill.impl.construction;

import com.ruthlessps.world.content.skill.impl.construction.ConstructionData.RoomData;

/**
 * 
 * @author Owner Blade
 *
 */
public class Room {

	private int rotation, type, theme;
	private int x, y;
	private boolean[] doors;

	public Room(int rotation, int type, int theme) {
		this.rotation = rotation;
		this.type = type;
		this.theme = theme;
		getVarData();
	}

	public boolean[] getDoors() {
		return doors;
	}

	public int getRotation() {
		return rotation;
	}

	public int getType() {
		return type;
	}

	private void getVarData() {
		RoomData rd = ConstructionData.RoomData.forID(type);
		x = rd.getX();
		y = rd.getY();
		doors = rd.getRotatedDoors(rotation);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return theme;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
}