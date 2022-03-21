package com.ruthlessps.world.content;

import com.ruthlessps.model.Item;
import com.ruthlessps.model.Locations.Location;
import com.ruthlessps.model.container.impl.Bank;
import com.ruthlessps.world.World;
import com.ruthlessps.world.content.kaleem.trade.TradeManager;
import com.ruthlessps.world.entity.impl.player.Player;

/**
 * Handles the money pouch
 * 
 * @author Goml Perfected by ruthless
 */
public class MoneyPouch {

	public static final int BUCKS_ID = 13664; // item id
	public static final long BUCKS_VALUE = 1000000000; // 1b
	public static long MONEY_POUCH_SIZE = Long.MAX_VALUE;

	/**
	 * Deposits money into the money pouch
	 * 
	 * @param amount
	 *            How many coins to deposit
	 * @return true Returns true if transaction was successful
	 * @return false Returns false if transaction was unsuccessful
	 */
	public static boolean depositMoney(Player plr, int amount, boolean bucks) {
		if (amount <= 0)
			return false;
		if (plr.getInterfaceId() > 0) {
			plr.getPacketSender().sendMessage("Please close the interface you have open before opening another one.");
			return false;
		}
		if (plr.getConstitution() <= 0) {
			plr.getPacketSender().sendMessage("You cannot do this while dying.");
			return false;
		}
		if (plr.getLocation() == Location.WILDERNESS) {
			plr.getPacketSender().sendMessage("You cannot do this here.");
			return false;
		}

		if (validateAmount(plr, amount) || bucks && plr.getInventory().getAmount(BUCKS_ID) >= amount) {
			long addedMoney = bucks ? plr.getMoneyInPouch() + (amount * BUCKS_VALUE) : plr.getMoneyInPouch() + amount;

			if (addedMoney < 0) {
				plr.getPA().sendMessage("Your pouch cannot hold more coins.");
				return true;
			}

			if (addedMoney > MONEY_POUCH_SIZE) {
				if (bucks) {
					plr.getPA().sendMessage("Your money pouch cannot hold that amount!");
					return true;
				}
				long canStore = MONEY_POUCH_SIZE - plr.getMoneyInPouch();
				plr.getInventory().delete(995, (int) canStore);
				plr.setMoneyInPouch(plr.getMoneyInPouch() + canStore);
				plr.getPacketSender().sendString(8135, "" + plr.getMoneyInPouch());
				plr.getPacketSender().sendMessage("You've added " + canStore + " coins to your money pouch.");
				return true;
			} else {
				if (bucks) {

					long toAdd = amount * BUCKS_VALUE;
					plr.getInventory().delete(BUCKS_ID, amount);
					plr.setMoneyInPouch(plr.getMoneyInPouch() + toAdd);
					plr.getPacketSender().sendMessage("You've added " + toAdd + " coins to your money pouch.");

				} else {
					plr.getInventory().delete(995, amount);
					plr.setMoneyInPouch(plr.getMoneyInPouch() + amount);
					plr.getPacketSender().sendMessage("You've added " + amount + " coins to your money pouch.");
				}

				plr.getPacketSender().sendString(8135, "" + plr.getMoneyInPouch());
				return true;
			}
		} else {
			plr.getPacketSender().sendMessage("You do not seem to have " + amount + " coins in your inventory.");
			return false;
		}
	}
	
	
	public static boolean addMoney(Player plr, int amount, boolean bucks) {
		if (amount <= 0)
			return false;

			long addedMoney = bucks ? plr.getMoneyInPouch() + (amount * BUCKS_VALUE) : plr.getMoneyInPouch() + amount;

			if (addedMoney < 0) {
				plr.getPA().sendMessage("Your pouch cannot hold more coins.");
				return true;
			}

			if (addedMoney > MONEY_POUCH_SIZE) {
				if (bucks) {
					plr.getPA().sendMessage("Your money pouch cannot hold that amount!");
					return true;
				}
				long canStore = MONEY_POUCH_SIZE - plr.getMoneyInPouch();
				plr.setMoneyInPouch(plr.getMoneyInPouch() + canStore);
				plr.getPacketSender().sendString(8135, "" + plr.getMoneyInPouch());
				plr.getPacketSender().sendMessage("You've added " + canStore + " coins to your money pouch.");
				return true;
			} else {
				if (bucks) {

					long toAdd = amount * BUCKS_VALUE;
					plr.getInventory().delete(BUCKS_ID, amount);
					plr.setMoneyInPouch(plr.getMoneyInPouch() + toAdd);
					plr.getPacketSender().sendMessage("Added " + toAdd + " coins to your money pouch.");

				} else {
					plr.getInventory().delete(995, amount);
					plr.setMoneyInPouch(plr.getMoneyInPouch() + amount);
					plr.getPacketSender().sendMessage("Added " + amount + " coins to your money pouch.");
				}

				plr.getPacketSender().sendString(8135, "" + plr.getMoneyInPouch());
				return true;
			}
	}
	
	

	public static void toBank(Player player) {
		if (!player.isBanking() || player.getInterfaceId() != 5292)
			return;

		if (player.getMoneyInPouch() == 0) {
			player.getPacketSender().sendMessage("You money pouch is empty.");
			return;
		}

		int amount = player.getMoneyInPouchAsInt();
		int bankAmt = player.getBank(Bank.getTabForItem(player, 995)).getAmount(995);
		int totalAmount = bankAmt + amount;
		player.setCurrentBankTab(Bank.getTabForItem(player, 995));
		if (player.getBank(player.getCurrentBankTab()).getFreeSlots() <= 0
				&& !player.getBank(player.getCurrentBankTab()).contains(995)) {
			player.getPacketSender().sendMessage("Your bank is currently full.");
			return;
		}
		if (totalAmount > Integer.MAX_VALUE || totalAmount < 0) {
			int canWithdraw = (Integer.MAX_VALUE - player.getBank(Bank.getTabForItem(player, 995)).getAmount(995));
			if (canWithdraw <= 0) {
				player.getPacketSender().sendMessage("You cannot withdraw more money into your bank.");
				return;
			}
			player.setMoneyInPouch(player.getMoneyInPouch() - canWithdraw);
			player.getBank(Bank.getTabForItem(player, 995)).add(995, canWithdraw);
			player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
			player.getPacketSender().sendMessage("You could only withdraw " + canWithdraw + " coins.");
		} else {
			player.getBank(player.getCurrentBankTab()).add(995, amount);
			player.setMoneyInPouch(player.getMoneyInPouch() - amount);
			player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
		}
		player.getPacketSender().sendMessage("You can not withraw coins at the moment!");
		return;
	}

	/**
	 * Validates that the player has the coins in their inventory
	 * 
	 * @param amount
	 *            The amount the player wishes to insert
	 * @return true Returns true if the player has the coins in their inventory
	 * @return false Returns false if the player does not have the coins in their
	 *         inventory
	 */
	private static boolean validateAmount(Player plr, int amount) {
		return plr.getInventory().getAmount(995) >= amount;
	}

	/**
	 * @param amount
	 *            How many coins to withdraw
	 * @return true Returns true if transaction was successful
	 * @return false Returns false if the transaction was unsuccessful
	 */
	public static boolean withdrawMoney(Player plr, long amount) {
		if (amount <= 0)
			return false;
		if (plr.getMoneyInPouch() <= 0) {
			plr.getPacketSender().sendMessage("Your money pouch is empty.");
			return false;
		}

		if (TradeManager.SINGLETON.contains(plr)) {
			return toTrade(plr, amount);
		}

		boolean allowWithdraw = plr.getTrading().inTrade() || plr.getDueling().inDuelScreen;
		if (!allowWithdraw) {
			if (plr.getInterfaceId() > 0) {
				plr.getPacketSender()
						.sendMessage("Please close the interface you have open before opening another one.");
				return false;
			}
			plr.getPacketSender().sendInterfaceRemoval();
		}

		if (amount > plr.getMoneyInPouch())
			amount = plr.getMoneyInPouch();

		if ((plr.getInventory().getAmount(995) + amount) < Integer.MAX_VALUE) {
			plr.setMoneyInPouch(plr.getMoneyInPouch() - amount);
			plr.getInventory().add(995, (int) amount);
			plr.getPacketSender().sendString(8135, "" + plr.getMoneyInPouch());
			plr.getPacketSender().sendMessage("You withdraw " + amount + " coins from your pouch.");
			if (allowWithdraw)
				plr.getPacketSender().sendItemContainer(plr.getInventory(), 3322);
			return true;
		} else if ((plr.getInventory().getAmount(995) + amount) > Integer.MAX_VALUE) {
			int canWithdraw = (Integer.MAX_VALUE - plr.getInventory().getAmount(995));
			if (canWithdraw == 0) {
				plr.getPacketSender().sendMessage("You cannot withdraw more money into your inventory.");
				return false;
			}
			plr.setMoneyInPouch(plr.getMoneyInPouch() - canWithdraw);
			plr.getInventory().add(995, canWithdraw);
			plr.getPacketSender().sendString(8135, "" + plr.getMoneyInPouch());
			plr.getPacketSender().sendMessage("You could only withdraw " + canWithdraw + " coins.");

			if (allowWithdraw)
				plr.getPacketSender().sendItemContainer(plr.getInventory(), 3322);
			return true;
		}
		plr.getPacketSender().sendMessage("You can not withraw coins at the moment!");
		return false;
	}

	public static boolean toTrade(Player plr, long amount) {
		
		if (amount <= 0)
			return false;
		if (plr.getMoneyInPouch() <= 0) {
			plr.getPacketSender().sendMessage("Your money pouch is empty.");
			return false;
		}

		boolean allowWithdraw = plr.getDueling().inDuelScreen;
		if (allowWithdraw) {
			if (plr.getInterfaceId() > 0) {
				plr.getPacketSender()
						.sendMessage("Please close the interface you have open before opening another one.");
				return false;
			}
			plr.getPacketSender().sendInterfaceRemoval();
		}

		if (amount > plr.getMoneyInPouch())
			amount = plr.getMoneyInPouch();

		if ((plr.getInventory().getAmount(995) + amount) < Integer.MAX_VALUE) {
			plr.setMoneyInPouch(plr.getMoneyInPouch() - amount);
			plr.getPacketSender().sendString(8135, "" + plr.getMoneyInPouch());
			plr.getPacketSender().sendMessage("You withdraw " + amount + " coins from your pouch to your trade");
			plr.getPacketSender().sendItemContainer(plr.getInventory(), 3322);
			addToTrade(plr, 995, (int) amount);
			return true;
		} else if ((plr.getInventory().getAmount(995) + amount) > Integer.MAX_VALUE) {
			int canWithdraw = (Integer.MAX_VALUE - plr.getInventory().getAmount(995));
			if (canWithdraw == 0) {
				plr.getPacketSender().sendMessage("You cannot withdraw more money into your trade.");
				return false;
			}
			plr.setMoneyInPouch(plr.getMoneyInPouch() - canWithdraw);
			plr.getPacketSender().sendString(8135, "" + plr.getMoneyInPouch());
			plr.getPacketSender().sendMessage("You could only withdraw " + canWithdraw + " coins.");
			plr.getPacketSender().sendItemContainer(plr.getInventory(), 3322);
			addToTrade(plr, 995, (int) amount);
			return true;
		}
		plr.getPacketSender().sendMessage("You can not withraw coins at the moment!");
		return false;
	}

	public static void addToTrade(Player player, int id, int amount) {

		TradeManager.SINGLETON.getSession(player).map(session -> session.get(player)).ifPresent(entry -> entry.getContainer().add(new Item(id, amount)));
	}
}