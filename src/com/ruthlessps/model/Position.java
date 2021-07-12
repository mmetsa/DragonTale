package com.ruthlessps.model;

import com.ruthlessps.util.Misc;
import com.ruthlessps.world.clip.region.RegionClipping;
import com.ruthlessps.world.entity.impl.player.Player;

/**
 * Represents a single world tile position.
 * 
 * @author relex lawl
 */

public class Position {

	/**
	 * The x coordinate of the position.
	 */
	private int x;

	/**
	 * The y coordinate of the position.
	 */
	private int y;

	/**
	 * The height level of the position.
	 */
	private int z;

	/**
	 * The Position constructor.
	 * 
	 * @param x
	 *            The x-type coordinate of the position.
	 * @param y
	 *            The y-type coordinate of the position.
	 */
	public Position(int x, int y) {
		this(x, y, 0);
	}

	/**
	 * The Position constructor.
	 * 
	 * @param x
	 *            The x-type coordinate of the position.
	 * @param y
	 *            The y-type coordinate of the position.
	 * @param z
	 *            The height of the position.
	 */
	public Position(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Adds steps/coordinates to this position.
	 */
	public Position add(int x, int y) {
		this.x += x;
		this.y += y;
		return this;
	}

	/**
	 * Adds steps/coordinates to this position.
	 */
	public Position add(int x, int y, int z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	public Position copy() {
		return new Position(x, y, z);
	}

	public double distanceToPoint(int pointX, int pointY) {
		return Math.sqrt(Math.pow(x - pointX, 2) + Math.pow(y - pointY, 2));
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Position)) {
			return false;
		}
		Position position = (Position) other;
		return position.x == x && position.y == y && position.z == z;
	}

	/**
	 * Gets the distance between this position and another position. Only X and Y
	 * are considered (i.e. 2 dimensions).
	 * 
	 * @param other
	 *            The other position.
	 * @return The distance.
	 */
	public int getDistance(Position other) {
		int deltaX = x - other.x;
		int deltaY = y - other.y;
		return (int) Math.ceil(Math.sqrt(deltaX * deltaX + deltaY * deltaY));
	}

	/**
	 * Gets the local x coordinate relative to a specific region.
	 * 
	 * @return The local x coordinate.
	 */
	public int getLocalX() {
		return x - 8 * getRegionX();
	}

	public int getRegionId() {
		int regionX = x >> 3;
		int regionY = y >> 3;
		return (regionX / 8 << 8) + regionY / 8;
	}

	/**
	 * Gets the local x coordinate relative to a specific region.
	 * 
	 * @param position
	 *            The region the coordinate will be relative to.
	 * @return The local x coordinate.
	 */
	public int getLocalX(Position position) {
		return x - 8 * position.getRegionX();
	}

	/**
	 * Gets the local y coordinate relative to a specific region.
	 * 
	 * @return The local y coordinate.
	 */
	public int getLocalY() {
		return y - 8 * getRegionY();
	}

	/**
	 * Gets the local y coordinate relative to a specific region.
	 * 
	 * @param position
	 *            The region the coordinate will be relative to.
	 * @return The local y coordinate.
	 */
	public int getLocalY(Position position) {
		return y - 8 * position.getRegionY();
	}

	/**
	 * Gets the region x coordinate.
	 * 
	 * @return The region x coordinate.
	 */
	public int getRegionX() {
		return (x >> 3) - 6;
	}

	/**
	 * Gets the region y coordinate.
	 * 
	 * @return The region y coordinate.
	 */
	public int getRegionY() {
		return (y >> 3) - 6;
	}

	/**
	 * Gets the x coordinate of this position.
	 * 
	 * @return The associated x coordinate.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y coordinate of this position.
	 * 
	 * @return The associated y coordinate.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Gets the height level of this position.
	 * 
	 * @return The associated height level.
	 */
	public int getZ() {
		return z;
	}

	@Override
	public int hashCode() {
		return z << 30 | x << 15 | y;
	}

	/**
	 * 
	 * @param Position
	 * @return this does not work for some reason!
	 */
	
	public boolean isViewableFrom(Position other) {
		/*if (this.getZ() != other.getZ())
			return false;*/
		Position p = Misc.delta(this, other);
		return p.x <= 14 && p.x >= -15 && p.y <= 14 && p.y >= -15;
	}

	/**
	 * Checks if this location is within range of another.
	 * 
	 * @param other
	 *            The other location.
	 * @return <code>true</code> if the location is in range, <code>false</code> if
	 *         not.
	 */
	public boolean isWithinDistance(Position other) {
		if (z != other.z)
			return false;
		int deltaX = other.x - x, deltaY = other.y - y;
		return deltaX <= 14 && deltaX >= -15 && deltaY <= 14 && deltaY >= -15;
	}

	/**
	 * Checks if the position is within distance of another.
	 * 
	 * @param other
	 *            The other position.
	 * @param distance
	 *            The distance.
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean isWithinDistance(Position other, int distance) {
		if (z != other.getZ())
			return false;
		int deltaX = Math.abs(x - other.x);
		int deltaY = Math.abs(y - other.y);
		return deltaX <= distance && deltaY <= distance;
	}

	/**
	 * Checks if this location is within interaction range of another.
	 * 
	 * @param other
	 *            The other location.
	 * @return <code>true</code> if the location is in range, <code>false</code> if
	 *         not.
	 */
	public boolean isWithinInteractionDistance(Position other) {
		if (z != other.z) {
			return false;
		}
		int deltaX = other.x - x, deltaY = other.y - y;
		return deltaX <= 2 && deltaX >= -3 && deltaY <= 2 && deltaY >= -3;
	}

	/**
	 * Checks if {@code position} has the same values as this position.
	 * 
	 * @param position
	 *            The position to check.
	 * @return The values of {@code position} are the same as this position's.
	 */
	public boolean sameAs(Position position) {
		return position.x == x && position.y == y && position.z == z;
	}

	public static Position generateLocalPosition(Player player) {
		int dir = -1;
		int x = 0, y = 0;
		if (!RegionClipping.blockedNorth(player.getPosition())) {
			dir = 0;
		} else if (!RegionClipping.blockedEast(player.getPosition())) {
			dir = 4;
		} else if (!RegionClipping.blockedSouth(player.getPosition())) {
			dir = 8;
		} else if (!RegionClipping.blockedWest(player.getPosition())) {
			dir = 12;
		}
		int random = Misc.getRandom(3);

		boolean found = false;

		if (random == 0) {
			if (!RegionClipping.blockedNorth(player.getPosition())) {
				y = 1;
				found = true;
			}
		} else if (random == 1) {
			if (!RegionClipping.blockedEast(player.getPosition())) {
				x = 1;
				found = true;
			}
		} else if (random == 2) {
			if (!RegionClipping.blockedSouth(player.getPosition())) {
				y = -1;
				found = true;
			}
		} else if (random == 3) {
			if (!RegionClipping.blockedWest(player.getPosition())) {
				x = -1;
				found = true;
			}
		}
		if (!found) {
			if (dir == 0) {
				y = 1;
			} else if (dir == 4) {
				x = 1;
			} else if (dir == 8) {
				y = -1;
			} else if (dir == 12) {
				x = -1;
			}
		}
		if (x == 0 && y == 0)
			return null;
		int spawnX = player.getPosition().getX();
		int spawnY = player.getPosition().getY();
		if (x == 1) {
			if (player.getPosition().getX() + x > spawnX + 1)
				return null;
		}
		if (x == -1) {
			if (player.getPosition().getX() - x < spawnX - 1)
				return null;
		}
		if (y == 1) {
			if (player.getPosition().getY() + y > spawnY + 1)
				return null;
		}
		if (y == -1) {
			if (player.getPosition().getY() - y < spawnY - 1)
				return null;
		}
		return new Position(x, y);
	}

	/**
	 * Sets the player's associated Position values.
	 * 
	 * @param x
	 *            The new x coordinate.
	 * @param y
	 *            The new y coordinate.
	 * @param z
	 *            The new height level.
	 */
	public void set(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void setAs(Position other) {
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
	}

	/**
	 * Sets the x coordinate of this position.
	 * 
	 * @return The Position instance.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Sets the y coordinate of this position.
	 * 
	 * @return The Position instance.
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Sets the height level of this position.
	 * 
	 * @return The Position instance.
	 */
	public Position setZ(int z) {
		this.z = z;
		return this;
	}

	public Position translate(int diffX, int diffY) {
		return new Position(x + diffX, y + diffY, z);
	}

	@Override
	public String toString() {
		return "Position values: [x, y, z] - [" + x + ", " + y + ", " + z + "].";
	}

}