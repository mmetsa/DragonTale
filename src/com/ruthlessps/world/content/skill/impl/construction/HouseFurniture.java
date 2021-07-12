package com.ruthlessps.world.content.skill.impl.construction;

import com.ruthlessps.world.content.skill.impl.construction.ConstructionData.HotSpots;

/**
 * 
 * @author Owner Blade
 *
 */
public class HouseFurniture {

	private int roomX, roomY, roomZ, hotSpotId, furnitureId, standardXOff, standardYOff;

	public HouseFurniture(int roomX, int roomY, int roomZ, int hotSpotId, int furnitureId, int standardXOff,
			int standardYOff) {
		this.roomX = roomX;
		this.roomY = roomY;
		this.setRoomZ(roomZ);
		this.hotSpotId = hotSpotId;
		this.furnitureId = furnitureId;
		this.standardXOff = standardXOff;
		this.standardYOff = standardYOff;
	}

	public int getFurnitureId() {
		return furnitureId;
	}

	public HotSpots getHotSpot(int roomRot) {
		return HotSpots.forObjectIdAndCoords(furnitureId, standardXOff, standardYOff);
	}

	public int getHotSpotId() {
		return hotSpotId;
	}

	public int getRoomX() {
		return roomX;
	}

	public int getRoomY() {
		return roomY;
	}

	public int getRoomZ() {
		return roomZ;
	}

	public int getStandardXOff() {
		return standardXOff;
	}

	public int getStandardYOff() {
		return standardYOff;
	}

	public void setFurnitureId(int furnitureId) {
		this.furnitureId = furnitureId;
	}

	public void setHotSpotId(int hotSpotId) {
		this.hotSpotId = hotSpotId;
	}

	public void setRoomX(int roomX) {
		this.roomX = roomX;
	}

	public void setRoomY(int roomY) {
		this.roomY = roomY;
	}

	public void setRoomZ(int roomZ) {
		this.roomZ = roomZ;
	}

	public void setStandardXOff(int standardXOff) {
		this.standardXOff = standardXOff;
	}

	public void setStandardYOff(int standardYOff) {
		this.standardYOff = standardYOff;
	}

}
