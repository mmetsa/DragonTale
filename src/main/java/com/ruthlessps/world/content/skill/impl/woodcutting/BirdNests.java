package com.ruthlessps.world.content.skill.impl.woodcutting;

import com.ruthlessps.model.GroundItem;
import com.ruthlessps.model.Item;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.entity.impl.GroundItemManager;
import com.ruthlessps.world.entity.impl.player.Player;

/**
 * @author Optimum I do not give permission to release this anywhere else
 */

public class BirdNests {

	/**
	 * Ints.
	 */

	public static final int[] BIRD_NEST_IDS = { 5073 };
	public static final int[] SEED_REWARDS = { 5312, 5313, 5314, 5315, 5316, 5283, 5284, 5285, 5286, 5287, 5288, 5289,
			5290, 5317 };
	public static final int[] RING_REWARDS = { 596 };
	public static final int EMPTY = 5075;
	public static final int RED = 5076;
	public static final int BLUE = 5077;
	public static final int GREEN = 5078;
	public static final int AMOUNT = 1;

	/**
	 * Generates the random drop and creates a ground item where the player is
	 * standing
	 */

	public static void dropNest(Player p) {
		if (p.getPosition().getZ() > 0) {
			return;
		}
		if (Misc.getRandom(500) == 0) {
			Item nest = new Item(5073);
			if (nest != null) {
				nest.setAmount(1);
				GroundItemManager.spawnGroundItem(p,
						new GroundItem(nest, p.getPosition().copy(), p.getUsername(), false, 80, true, 80));
				p.getPacketSender().sendMessage("A bird's nest falls out of the tree!");
			}
		}
	}

	/**
	 * 
	 * Egg nests
	 * 
	 */

	public static final void eggNest(Player p, int itemId) {
		if (itemId == 5070) {
			p.getInventory().add(RED, AMOUNT);
		}
		if (itemId == 5071) {
			p.getInventory().add(GREEN, AMOUNT);
		}
		if (itemId == 5072) {
			p.getInventory().add(BLUE, AMOUNT);
		}
	}

	/**
	 * Check if the item is a nest
	 *
	 */
	public static boolean isNest(final int itemId) {
		for (int nest : BIRD_NEST_IDS) {
			if (nest == itemId) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * Determines what loot you get from ring bird nests
	 * 
	 */
	public static final void ringNest(Player p, int itemId) {
		if (itemId == 5074) {
			int random = Misc.getRandom(1000);
			if (random >= 0 && random <= 340) {
				p.getInventory().add(RING_REWARDS[0], AMOUNT);
			} else if (random >= 341 && random <= 750) {
				p.getInventory().add(RING_REWARDS[1], AMOUNT);
			} else if (random >= 751 && random <= 910) {
				p.getInventory().add(RING_REWARDS[2], AMOUNT);
			} else if (random >= 911 && random <= 989) {
				p.getInventory().add(RING_REWARDS[3], AMOUNT);
			} else if (random >= 990) {
				p.getInventory().add(RING_REWARDS[4], AMOUNT);
			}
		}
	}

	/**
	 * 
	 * Searches the nest.
	 * 
	 */

	public static final void searchNest(Player p, int itemId) {
		if (p.getInventory().getFreeSlots() <= 0) {
			p.getPacketSender().sendMessage("You do not have enough free inventory slots to do this.");
			return;
		}
		p.getInventory().delete(itemId, 1);
		seedNest(p, itemId);
		p.getInventory().add(EMPTY, AMOUNT);
	}

	/**
	 * 
	 * Determines what loot you get from seed bird nests
	 * 
	 */

	private static final void seedNest(Player p, int itemId) {
		if (itemId == 5073) {
			int random = Misc.getRandom(1000);
			if(random < 250) {
				p.getPA().sendMessage("You search the nest but find nothing!");
			}else if (random <= 260) {
				p.getInventory().add(4703, AMOUNT);
			} else if (random <= 270) {
				p.getInventory().add(295, AMOUNT);
			} else if (random <= 370) {
				p.getInventory().add(15273, 200);
			} else if (random <= 470) {
				p.getInventory().add(19864, Misc.random(105)+25);
			} else if (random <= 570) {
				p.getInventory().add(313, Misc.random(1300));
			} else if (random <= 580) {
				p.getInventory().add(596, AMOUNT);
			} else if (random <= 780) {
				p.getInventory().add(15501, Misc.random(2)+1);
			} else if (random <= 980) {
				p.getInventory().add(2801, Misc.random(2)+1);
			} else if (random <= 982) {
				p.getInventory().add(2572, AMOUNT);
			} else if (random <= 983) {
				p.getInventory().add(293, AMOUNT);
			} else if (random <= 999) {
				p.getInventory().add(13664, Misc.random(50)+10);
			} else if (random == 1000) {
				p.getInventory().add(12508, AMOUNT);
				p.getPA().sendMessage("You feel a weird noise. You found a bird!");
			}
		}
	}

}