package com.ruthlessps.model.definitions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.ruthlessps.model.container.impl.Equipment;

/**
 * This file manages every item definition, which includes their name,
 * description, value, skill requirements, etc.
 * 
 * @author relex lawl
 */

public class ItemDefinition {

	public enum EquipmentType {
		HAT(Equipment.HEAD_SLOT),

		CAPE(Equipment.CAPE_SLOT),

		SHIELD(Equipment.SHIELD_SLOT),

		GLOVES(Equipment.HANDS_SLOT),

		BOOTS(Equipment.FEET_SLOT),

		AMULET(Equipment.AMULET_SLOT),

		RING(Equipment.RING_SLOT),

		ARROWS(Equipment.AMMUNITION_SLOT),

		FULL_MASK(Equipment.HEAD_SLOT),

		FULL_HELMET(Equipment.HEAD_SLOT),

		BODY(Equipment.BODY_SLOT),

		PLATEBODY(Equipment.BODY_SLOT),

		LEGS(Equipment.LEG_SLOT),

		WEAPON(Equipment.WEAPON_SLOT),

		HALF_MASK(Equipment.HEAD_SLOT),

		BEARD_MASK(Equipment.HEAD_SLOT),

		;

		private int slot;

		private EquipmentType(int slot) {
			this.slot = slot;
		}
	}

	/**
	 * The directory in which item definitions are found.
	 */
	private static final String FILE_DIRECTORY = "./data/def/txt/items.txt";

	/**
	 * The max amount of items that will be loaded.
	 */
	private static final int MAX_AMOUNT_OF_ITEMS = 22694;

	/**
	 * ItemDefinition array containing all items' definition values.
	 */
	private static ItemDefinition[] definitions = new ItemDefinition[MAX_AMOUNT_OF_ITEMS];

	/**
	 * Gets the item definition correspondent to the id.
	 * 
	 * @param id
	 *            The id of the item to fetch definition for.
	 * @return definitions[id].
	 */
	public static ItemDefinition forId(int id) {
		return (id < 0 || id > definitions.length || definitions[id] == null) ? new ItemDefinition() : definitions[id];
	}

	public static ItemDefinition[] getDefinitions() {
		return definitions;
	}

	public static int getItemId(String itemName) {
		for (int i = 0; i < MAX_AMOUNT_OF_ITEMS; i++) {
			if (definitions[i] != null) {
				if (definitions[i].getName().equalsIgnoreCase(itemName)) {
					return definitions[i].getId();
				}
			}
		}
		return -1;
	}

	/**
	 * Gets the max amount of items that will be loaded in Niobe.
	 * 
	 * @return The maximum amount of item definitions loaded.
	 */
	public static int getMaxAmountOfItems() {
		return MAX_AMOUNT_OF_ITEMS;
	}

	/**
	 * Loading all item definitions
	 */
	public static void init() {
		ItemDefinition definition = new ItemDefinition();
		try {
			File file = new File(FILE_DIRECTORY);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.contains("inish")) {
					definitions[definition.id] = definition;
					continue;
				}
				String[] args = line.split(": ");
				if (args.length <= 1)
					continue;
				String token = args[0], value = args[1];
				if (line.contains("Bonus[")) {
					String[] other = line.split("]");
					int index = Integer.valueOf(line.substring(6, other[0].length()));
					double bonus = Double.valueOf(value);
					definition.bonus[index] = bonus;
					continue;
				}
				if (line.contains("Requirement[")) {
					String[] other = line.split("]");
					int index = Integer.valueOf(line.substring(12, other[0].length()));
					int requirement = Integer.valueOf(value);
					definition.requirement[index] = requirement;
					continue;
				}
				switch (token.toLowerCase()) {
				case "item id":
					int id = Integer.valueOf(value);
					definition = new ItemDefinition();
					definition.id = id;
					break;
				case "name":
					if (value == null)
						continue;
					if (definition == null) {
						break;
					}
					definition.name = value;
					break;
				case "examine":
					definition.description = value;
					break;
				case "value":
					int price = Integer.valueOf(value);
					definition.value = price;
					break;
				case "stackable":
					definition.stackable = Boolean.valueOf(value);
					break;
				case "noted":
					definition.noted = Boolean.valueOf(value);
					break;
				case "double-handed":
					definition.isTwoHanded = Boolean.valueOf(value);
					break;
				case "equipment type":
					definition.equipmentType = EquipmentType.valueOf(value);
					break;
				case "is weapon":
					definition.weapon = Boolean.valueOf(value);
					break;
				case "dr-boost":
					definition.dropRateBoost = Double.valueOf(value);
					break;
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The id of the item.
	 */
	private int id = 0;

	/**
	 * The name of the item.
	 */
	private String name = "None";

	/**
	 * The item's description.
	 */
	private String description = "Null";

	/**
	 * Flag to check if item is stackable.
	 */
	private boolean stackable;

	/**
	 * The item's shop value.
	 */
	private int value;

	/**
	 * Flag that checks if item is noted.
	 */
	private boolean noted;

	private boolean isTwoHanded;

	private boolean weapon;

	private double dropRateBoost;

	private EquipmentType equipmentType = EquipmentType.WEAPON;

	private double[] bonus = new double[18];

	private int[] requirement = new int[25];

	public double[] getBonus() {
		return bonus;
	}

	/**
	 * Gets the item's description.
	 * 
	 * @return description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the item's equipment slot index.
	 * 
	 * @return equipmentSlot.
	 */
	public int getEquipmentSlot() {
		return equipmentType.slot;
	}

	public EquipmentType getEquipmentType() {
		return equipmentType;
	}

	/**
	 * Gets the item's id.
	 * 
	 * @return id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the item's name.
	 * 
	 * @return name.
	 */
	public String getName() {
		return name;
	}

	public double getDropRateBoost() {
		return dropRateBoost;
	}

	public int[] getRequirement() {
		return requirement;
	}

	/**
	 * Gets the item's shop value.
	 * 
	 * @return value.
	 */
	public int getValue() {
		return isNoted() ? ItemDefinition.forId(getId() - 1).value : value;
	}

	/**
	 * Checks if item is full body.
	 */
	public boolean isFullBody() {
		return equipmentType.equals(EquipmentType.PLATEBODY);
	}

	/**
	 * Checks if item is full helm.
	 */
	public boolean isFullHelm() {
		return equipmentType.equals(EquipmentType.FULL_HELMET);
	}

	/**
	 * Checks if item is noted.
	 * 
	 * @return noted.
	 */
	public boolean isNoted() {
		return noted;
	}

	/**
	 * Checks if the item is stackable.
	 * 
	 * @return stackable.
	 */
	public boolean isStackable() {
		if (noted)
			return true;
		return stackable;
	}

	/**
	 * Checks if item is two-handed
	 */
	public boolean isTwoHanded() {
		return isTwoHanded;
	}

	public boolean isWeapon() {
		return weapon;
	}

	@Override
	public String toString() {
		return "[ItemDefinition(" + id + ")] - Name: " + name + "; equipment slot: " + getEquipmentSlot() + "; value: "
				+ value + "; stackable ? " + Boolean.toString(stackable) + "; noted ? " + Boolean.toString(noted)
				+ "; 2h ? " + isTwoHanded;
	}
}
