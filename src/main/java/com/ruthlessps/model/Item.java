package com.ruthlessps.model;

import com.ruthlessps.GameSettings;
import com.ruthlessps.model.definitions.ItemDefinition;

/**
 * Represents an item which is owned by a player.
 * 
 * @author relex lawl
 */

public class Item {

	//TODO make items have optional drop rate booste

	public static int getNoted(int id) {
		int noted = id + 1;
		if (id == 11283 || id == 11284) {
			noted = 11285;
		}
		if (ItemDefinition.forId(noted).getName().equals(ItemDefinition.forId(id).getName())) {
			return noted;
		}
		return id;
	}

	public static Item getNoted(int id, int amount) {
		int notedItem = id + 1;
		if (ItemDefinition.forId(notedItem).getName().equals(ItemDefinition.forId(id).getName())) {
			return new Item(notedItem, amount);
		}
		return new Item(id, amount);
	}

	public static int getUnNoted(int id) {
		int unNoted = id - 1;
		if (id == 11284 || id == 11285) {
			unNoted = 11283;
		}
		if (ItemDefinition.forId(unNoted).getName().equals(ItemDefinition.forId(id).getName())) {
			return unNoted;
		}
		return id;
	}

	public static boolean sellable(int item) {
		return new Item(item).sellable();
	}

	public static boolean tradeable(int item) {
		return new Item(item).tradeable();
	}

	/**
	 * The item id.
	 */
	private int id;

	/**
	 * Amount of the item.
	 */
	private int amount;

	private int slot;

	/**
	 * The Drop Rate Boost of the item.
	 */
	private double dropRateBoost;

	/** ITEM RARITY **/
	public ItemRarity rarity;

	/**
	 * An Item object constructor.
	 * 
	 * @param id
	 *            Item id.
	 */
	public Item(int id) {
		this(id, 1);
	}

	/**
	 * An Item object constructor.
	 * 
	 * @param id
	 *            Item id.
	 * @param amount
	 *            Item amount.
	 */
	public Item(int id, int amount) {
		this.id = id;
		this.amount = amount;
	}

	/**
	 * Copying the item by making a new item with same values.
	 */
	public Item copy() {
		return new Item(id, amount);
	}

	/**
	 * Decrement the amount by 1.
	 */
	public void decrementAmount() {
		if ((amount - 1) < 0) {
			return;
		}
		amount--;
	}

	/**
	 * Decrement the amount by the specified amount.
	 */
	public void decrementAmountBy(int amount) {
		if ((this.amount - amount) < 1) {
			this.amount = 0;
		} else {
			this.amount -= amount;
		}
	}

	/**
	 * Gets the amount of the item.
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Gets item's definition.
	 */
	public ItemDefinition getDefinition() {
		return ItemDefinition.forId(id);
	}

	/**
	 * Gets the item's id.
	 */
	public int getId() {
		return id;
	}

	public int getSlot() {
		return this.slot;
	}

	/**
	 * Increment the amount by 1.
	 */
	public void incrementAmount() {
		if ((amount + 1) > Integer.MAX_VALUE) {
			return;
		}
		amount++;
	}

	/**
	 * Increment the amount by the specified amount.
	 */
	public void incrementAmountBy(int amount) {
		if ((this.amount + amount) > Integer.MAX_VALUE) {
			this.amount = Integer.MAX_VALUE;
		} else {
			this.amount += amount;
		}
	}

	public boolean sellable() {
		String name = getDefinition().getName().toLowerCase();
		if (name.contains("clue scroll"))
			return false;
		if (name.contains("overload") || name.contains("extreme"))
			return false;
		if (name.toLowerCase().contains("(deg)") || name.toLowerCase().contains("brawling"))
			return false;
		for (int i : GameSettings.UNSELLABLE_ITEMS) {
			if (id == i)
				return false;
		}
		return true;
	}

	/**
	 * Sets the amount of the item.
	 */
	public Item setAmount(int amount) {
		this.amount = amount;
		return this;
	}

	/**
	 * Sets the item's id.
	 * 
	 * @param id
	 *            New item id.
	 */
	public Item setId(int id) {
		this.id = id;
		return this;
	}

	public Item setRarity(ItemRarity rarity) {
		this.rarity = rarity;
		return this;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public boolean tradeable() {
		String name = getDefinition().getName().toLowerCase();
		if (name.contains("clue scroll"))
			return false;
		if (name.contains("overload") || name.contains("extreme"))
			return false;
		if (name.toLowerCase().contains("(deg)") || name.toLowerCase().contains("brawling"))
			return false;
		for (int i : GameSettings.UNTRADEABLE_ITEMS) {
			if (id == i)
				return false;
		}
		return true;
	}
}