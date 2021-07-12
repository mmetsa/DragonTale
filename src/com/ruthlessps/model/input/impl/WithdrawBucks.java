package com.ruthlessps.model.input.impl;

import com.ruthlessps.model.Item;
import com.ruthlessps.model.input.EnterAmount;
import com.ruthlessps.world.World;
import com.ruthlessps.world.content.MoneyPouch;
import com.ruthlessps.world.entity.impl.player.Player;

public class WithdrawBucks extends EnterAmount {

	@Override
	public void handleAmount(Player player, int amount) {
		// player.getPacketSender().sendInterfaceRemoval();

		if (amount <= 0) {
			return;
		}

		long cost = amount * MoneyPouch.BUCKS_VALUE;

		if (cost > Long.MAX_VALUE || cost <= 0) {
			return;
		}

		if (player.getMoneyInPouch() < cost) {
			player.getPA().sendMessage("You don't have enough coins to withdraw that many bucks.");
			return;
		}
		if (player.getInterfaceId() > 0) {
			player.getPacketSender().sendMessage("Please close the interface you have open before opening another one.");
			return;
		}

		if (player.getTrading().inTrade()) {
			Player player2 = World.getPlayers().get(player.getTrading().getTradeWith());
			if (player2 == null || player == null)
				return;
			if (player.getTrading().offeredItems.size() == 28
					&& !player.getTrading().offeredItems.contains(MoneyPouch.BUCKS_ID)) {
				player.getPA().sendMessage("Your trade container is full!");
				return;
			}
			player.setMoneyInPouch(player.getMoneyInPouch() - cost);
			MoneyPouch.addToTrade(player,MoneyPouch.BUCKS_ID, amount);
			player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
			player.getPacketSender().sendInterfaceItems(3415, player.getTrading().offeredItems);
			player2.getPacketSender().sendInterfaceItems(3416, player.getTrading().offeredItems);
			return;
		}

		if (player.getInventory().getFreeSlots() == 0 && !player.getInventory().contains(MoneyPouch.BUCKS_ID)) {
			player.getPA().sendMessage("Your inventory is full!");
			return;
		}

		player.setMoneyInPouch(player.getMoneyInPouch() - cost);
		player.getInventory().add(MoneyPouch.BUCKS_ID, amount);
		player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
	}
}
