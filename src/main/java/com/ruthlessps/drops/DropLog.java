package com.ruthlessps.drops;

import java.util.Collections;
import java.util.Comparator;

import com.ruthlessps.model.definitions.ItemDefinition;
import com.ruthlessps.world.entity.impl.player.Player;

public class DropLog {

	public static class DropLogEntry {

		public int item;

		public int amount;
		boolean rareDrop;

		DropLogEntry(int item, int amount) {
			this.item = item;
			this.amount = amount;
			// this.rareDrop = ItemDefinition.forId(item).getValue() > 200000 ||
			// DropAnnouncer.shouldAnnounce(item);
		}
	}

	private static boolean addItem(Player player, DropLogEntry drop) {
		if (player.getDropLog().size() >= 98) {
			DropLogEntry drop2 = player.getDropLog().get(player.getDropLog().size() - 1);
			int value1 = ItemDefinition.forId(drop.item).getValue() * drop.amount;
			int value2 = ItemDefinition.forId(drop2.item).getValue() * drop2.amount;
			if (value1 > value2) {
				player.getDropLog().remove(drop2);
				return true;
			} else {
				return false;
			}
		}
		return true;
	}

	public static int getIndex(Player player, DropLogEntry drop) {
		for (int i = 0; i < player.getDropLog().size(); i++) {
			if (player.getDropLog().get(i).item == drop.item) {
				return i;
			}
		}
		return -1;
	}

	public static void open(Player player) {
		try {
			/* RESORT THE KILLS */
			player.getDropLog().sort((drop1, drop2) -> {
				ItemDefinition def1 = ItemDefinition.forId(drop1.item);
				ItemDefinition def2 = ItemDefinition.forId(drop2.item);
				int value1 = def1.getValue() * drop1.amount;
				int value2 = def2.getValue() * drop2.amount;
				return Integer.compare(value2, value1);
			});
			/* SHOW THE INTERFACE */
			resetInterface(player);
			player.getPacketSender().sendString(637, "Drop Log (Sorted by value)").sendInterface(8134);
			player.getPacketSender().sendString(8147, "@dre@ - @whi@ Rare drops");
			int index = 1;
			for (DropLogEntry entry : player.getDropLog()) {
				if (entry.rareDrop) {
					int toSend = 8147 + index > 8196 ? 12174 + index : 8147 + index;
					player.getPacketSender().sendString(toSend,
							"@or1@ " + ItemDefinition.forId(entry.item).getName() + ": x" + entry.amount + "");
					index++;
				}
			}
			index += 1;
			player.getPacketSender().sendString(8147 + index, "@dre@ - @whi@ Common drops");
			index++;
			for (DropLogEntry entry : player.getDropLog()) {
				if (entry.rareDrop)
					continue;
				int toSend = 8147 + index > 8196 ? 12174 + index : 8147 + index;
				player.getPacketSender().sendString(toSend,
						"@or1@ " + ItemDefinition.forId(entry.item).getName() + ": x" + entry.amount + "");
				index++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void resetInterface(Player player) {
		for (int i = 8145; i < 8196; i++)
			player.getPacketSender().sendString(i, "");
		for (int i = 12174; i < 12224; i++)
			player.getPacketSender().sendString(i, "");
		player.getPacketSender().sendString(8136, "Close window");
	}

	public static void submit(Player player, DropLogEntry drop) {
		int index = getIndex(player, drop);
		if (index >= 0) {
			player.getDropLog().get(index).amount += drop.amount;
		} else {
			if (addItem(player, drop)) {
				player.getDropLog().add(drop);
			}
		}
	}

	public static void submit(Player player, DropLogEntry[] drops) {
		for (DropLogEntry drop : drops) {
			if (drop != null)
				submit(player, drop);
		}
	}

}
