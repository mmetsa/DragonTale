package com.ruthlessps.world.content;

import com.ruthlessps.util.Misc;
import com.ruthlessps.world.entity.impl.player.Player;

/**
 * bank-pin
 * 
 * @author Gabriel Hannason NOTE: This was taken & redone from my PI base
 */
public class BankPin {

	public static class BankPinAttributes {
		private boolean hasBankPin;

		private boolean hasEnteredBankPin;
		private int[] bankPin = new int[4];
		private int[] enteredBankPin = new int[4];
		private int bankPins[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		private int invalidAttempts;
		private long lastAttempt;

		public BankPinAttributes() {
		}

		public int[] getBankPin() {
			return bankPin;
		}

		public int[] getBankPins() {
			return bankPins;
		}

		public int[] getEnteredBankPin() {
			return enteredBankPin;
		}

		public int getInvalidAttempts() {
			return invalidAttempts;
		}

		public long getLastAttempt() {
			return lastAttempt;
		}

		public boolean hasBankPin() {
			return hasBankPin;
		}

		public boolean hasEnteredBankPin() {
			return hasEnteredBankPin;
		}

		public BankPinAttributes setBankPin(int[] bankPin) {
			this.bankPin = bankPin;
			return this;
		}

		public BankPinAttributes setHasBankPin(boolean hasBankPin) {
			this.hasBankPin = hasBankPin;
			return this;
		}

		public BankPinAttributes setHasEnteredBankPin(boolean hasEnteredBankPin) {
			this.hasEnteredBankPin = hasEnteredBankPin;
			return this;
		}

		public BankPinAttributes setInvalidAttempts(int invalidAttempts) {
			this.invalidAttempts = invalidAttempts;
			return this;
		}

		public BankPinAttributes setLastAttempt(long lastAttempt) {
			this.lastAttempt = lastAttempt;
			return this;
		}
	}

	private static final int stringIds[] = { 14883, 14884, 14885, 14886, 14887, 14888, 14889, 14890, 14891, 14892 };

	private static final int actionButtons[] = { -27998, -27997, -27996, -27995, -27994, -27993, -27992, -27991, -27990,
			-27989 };

	public static void clickedButton(Player player, int button) {
		sendPins(player);
		if (player.getBankPinAttributes().getEnteredBankPin()[0] == -1) {
			player.getPacketSender().sendString(15313, "Now click the @red@SECOND @whi@digit");
			player.getPacketSender().sendString(14913, "@red@*");
			for (int i = 0; i < actionButtons.length; i++)
				if (actionButtons[i] == button)
					player.getBankPinAttributes().getEnteredBankPin()[0] = player.getBankPinAttributes()
							.getBankPins()[i];
		} else if (player.getBankPinAttributes().getEnteredBankPin()[1] == -1) {
			player.getPacketSender().sendString(15313, "Now click the @red@THIRD @whi@digit");
			player.getPacketSender().sendString(14914, "@red@*");
			for (int i = 0; i < actionButtons.length; i++)
				if (actionButtons[i] == button)
					player.getBankPinAttributes().getEnteredBankPin()[1] = player.getBankPinAttributes()
							.getBankPins()[i];
		} else if (player.getBankPinAttributes().getEnteredBankPin()[2] == -1) {
			player.getPacketSender().sendString(15313, "Now click the @red@FINAL @whi@digit");
			player.getPacketSender().sendString(14915, "@red@*");
			for (int i = 0; i < actionButtons.length; i++)
				if (actionButtons[i] == button)
					player.getBankPinAttributes().getEnteredBankPin()[2] = player.getBankPinAttributes()
							.getBankPins()[i];
		} else if (player.getBankPinAttributes().getEnteredBankPin()[3] == -1) {
			player.getPacketSender().sendString(14916, "@red@*");
			for (int i = 0; i < actionButtons.length; i++)
				if (actionButtons[i] == button)
					player.getBankPinAttributes().getEnteredBankPin()[3] = player.getBankPinAttributes()
							.getBankPins()[i];
			if (!player.getBankPinAttributes().hasBankPin()) {
				player.getBankPinAttributes().setHasBankPin(true).setHasEnteredBankPin(true)
						.setBankPin(player.getBankPinAttributes().getEnteredBankPin());
				player.getPacketSender()
						.sendMessage((player.changinPin == 1 ? "You've changed your bank-pin. Your digit is "
								: "You've created a bank-pin. Your digit is ")
								+ player.getBankPinAttributes().getEnteredBankPin()[0] + "-"
								+ player.getBankPinAttributes().getEnteredBankPin()[1] + "-"
								+ player.getBankPinAttributes().getEnteredBankPin()[2] + "-"
								+ player.getBankPinAttributes().getEnteredBankPin()[3] + ". Please write it down.");
				player.getPacketSender().sendInterfaceRemoval();
				if (player.changinPin == 1) {
					player.changinPin = -1;
				}
				player.getPacketSender().sendString(37716, "You have a PIN");
				return;
			}
			for (int i = 0; i < player.getBankPinAttributes().getEnteredBankPin().length; i++) {
				if (player.getBankPinAttributes().getEnteredBankPin()[i] != player.getBankPinAttributes()
						.getBankPin()[i]) {
					player.getPacketSender().sendInterfaceRemoval();
					int invalidAttempts = player.getBankPinAttributes().getInvalidAttempts() + 1;
					if (invalidAttempts >= 3)
						player.getBankPinAttributes().setLastAttempt(System.currentTimeMillis());
					player.getBankPinAttributes().setInvalidAttempts(invalidAttempts);
					player.getPacketSender().sendMessage("Invalid bank-pin entered entered.");
					return;
				}
			}
			player.getBankPinAttributes().setInvalidAttempts(0).setHasEnteredBankPin(true);
			if (player.changinPin == 1) {
				BankPin.deletePin(player);
				BankPin.init(player, false);
				return;
			} else if (player.changinPin == 2) {
				player.changinPin = -1;
				BankPin.deletePin(player);
				return;
			}
			if (player.openBank()) {
				player.getBank(0).open();
			} else {
				player.getPacketSender().sendInterfaceRemoval();
			}
		}
		randomizeNumbers(player);
	}

	public static void deletePin(Player player) {
		player.getBankPinAttributes().setHasBankPin(false).setHasEnteredBankPin(false).setInvalidAttempts(0)
				.setLastAttempt(System.currentTimeMillis());
		for (int i = 0; i < player.getBankPinAttributes().getBankPin().length; i++) {
			player.getBankPinAttributes().getBankPin()[i] = -1;
			player.getBankPinAttributes().getEnteredBankPin()[i] = -1;
		}
		if (player.changinPin == 1) {
			player.getPacketSender().sendString(37716, "You don't have a bank pin").sendInterfaceRemoval()
					.sendMessage("Your old PIN has been deleted, please enter a new one.");
		} else {
			player.getPacketSender().sendString(37716, "You don't have a bank pin")
					.sendMessage("Your bank-pin was deleted.").sendInterfaceRemoval();
		}
		// this should fix the issue where your pin was nulled
		player.getBankPinAttributes().hasBankPin = false;
	}

	public static void init(Player player, boolean openBankAfter) {
		if (player.getBankPinAttributes().getInvalidAttempts() == 3) {
			if (System.currentTimeMillis() - player.getBankPinAttributes().getLastAttempt() < 400000) {
				player.getPacketSender().sendMessage("You must wait " + (int) ((400
						- (System.currentTimeMillis() - player.getBankPinAttributes().getLastAttempt()) * 0.001))
						+ " seconds before attempting to enter your bank-pin again.");
				return;
			} else
				player.getBankPinAttributes().setInvalidAttempts(0);
			player.getPacketSender().sendInterfaceRemoval();
		}
		player.setOpenBank(openBankAfter);
		randomizeNumbers(player);
		if (player.changinPin == 1) {
			player.getPacketSender().sendString(37716, "You don't have a bank pin");
		}
		player.getPacketSender().sendString(15313, "First click the @red@FIRST @whi@digit");
		player.getPacketSender().sendString(14923, "");
		player.getPacketSender().sendString(14913, "@whi@?");
		player.getPacketSender().sendString(14914, "@whi@?");
		player.getPacketSender().sendString(14915, "@whi@?");
		player.getPacketSender().sendString(14916, "@whi@?");
		sendPins(player);
		player.getPacketSender().sendInterface(37535);
		for (int i = 0; i < player.getBankPinAttributes().getEnteredBankPin().length; i++)
			player.getBankPinAttributes().getEnteredBankPin()[i] = -1;
	}

	public static void randomizeNumbers(Player player) {
		int i = Misc.getRandom(5);
		switch (i) {
		case 0:
			player.getBankPinAttributes().getBankPins()[0] = 1;
			player.getBankPinAttributes().getBankPins()[1] = 7;
			player.getBankPinAttributes().getBankPins()[2] = 0;
			player.getBankPinAttributes().getBankPins()[3] = 8;
			player.getBankPinAttributes().getBankPins()[4] = 4;
			player.getBankPinAttributes().getBankPins()[5] = 6;
			player.getBankPinAttributes().getBankPins()[6] = 5;
			player.getBankPinAttributes().getBankPins()[7] = 9;
			player.getBankPinAttributes().getBankPins()[8] = 3;
			player.getBankPinAttributes().getBankPins()[9] = 2;
			break;

		case 1:
			player.getBankPinAttributes().getBankPins()[0] = 5;
			player.getBankPinAttributes().getBankPins()[1] = 4;
			player.getBankPinAttributes().getBankPins()[2] = 3;
			player.getBankPinAttributes().getBankPins()[3] = 7;
			player.getBankPinAttributes().getBankPins()[4] = 8;
			player.getBankPinAttributes().getBankPins()[5] = 6;
			player.getBankPinAttributes().getBankPins()[6] = 9;
			player.getBankPinAttributes().getBankPins()[7] = 2;
			player.getBankPinAttributes().getBankPins()[8] = 1;
			player.getBankPinAttributes().getBankPins()[9] = 0;
			break;

		case 2:
			player.getBankPinAttributes().getBankPins()[0] = 4;
			player.getBankPinAttributes().getBankPins()[1] = 7;
			player.getBankPinAttributes().getBankPins()[2] = 6;
			player.getBankPinAttributes().getBankPins()[3] = 5;
			player.getBankPinAttributes().getBankPins()[4] = 2;
			player.getBankPinAttributes().getBankPins()[5] = 3;
			player.getBankPinAttributes().getBankPins()[6] = 1;
			player.getBankPinAttributes().getBankPins()[7] = 8;
			player.getBankPinAttributes().getBankPins()[8] = 9;
			player.getBankPinAttributes().getBankPins()[9] = 0;
			break;

		case 3:
			player.getBankPinAttributes().getBankPins()[0] = 9;
			player.getBankPinAttributes().getBankPins()[1] = 4;
			player.getBankPinAttributes().getBankPins()[2] = 2;
			player.getBankPinAttributes().getBankPins()[3] = 7;
			player.getBankPinAttributes().getBankPins()[4] = 8;
			player.getBankPinAttributes().getBankPins()[5] = 6;
			player.getBankPinAttributes().getBankPins()[6] = 0;
			player.getBankPinAttributes().getBankPins()[7] = 3;
			player.getBankPinAttributes().getBankPins()[8] = 1;
			player.getBankPinAttributes().getBankPins()[9] = 5;
			break;

		case 4:
			player.getBankPinAttributes().getBankPins()[0] = 8;
			player.getBankPinAttributes().getBankPins()[1] = 7;
			player.getBankPinAttributes().getBankPins()[2] = 6;
			player.getBankPinAttributes().getBankPins()[3] = 2;
			player.getBankPinAttributes().getBankPins()[4] = 5;
			player.getBankPinAttributes().getBankPins()[5] = 4;
			player.getBankPinAttributes().getBankPins()[6] = 1;
			player.getBankPinAttributes().getBankPins()[7] = 0;
			player.getBankPinAttributes().getBankPins()[8] = 3;
			player.getBankPinAttributes().getBankPins()[9] = 9;
			break;
		}
		sendPins(player);
	}

	private static void sendPins(Player player) {
		for (int i = 0; i < player.getBankPinAttributes().getBankPins().length; i++)
			player.getPacketSender().sendString(stringIds[i], "" + player.getBankPinAttributes().getBankPins()[i]);
	}
}
