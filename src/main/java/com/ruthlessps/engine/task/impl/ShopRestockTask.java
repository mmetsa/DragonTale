package com.ruthlessps.engine.task.impl;

import com.ruthlessps.engine.task.Task;
import com.ruthlessps.model.Item;
import com.ruthlessps.model.container.impl.Shop;

public class ShopRestockTask extends Task {

	public static int getDeleteRatio(int x) {
		return (int) (Math.pow(x, 1.05) / 50 + 1);
	}

	public static int getRestockAmount(int amountMissing) {
		return (int) (Math.pow(amountMissing, 1.2) / 30 + 1);
	}

	private final Shop shop;

	public ShopRestockTask(Shop shop) {
		super(5);
		this.shop = shop;
	}

	@Override
	protected void execute() {
		if (shop.fullyRestocked()) {
			stop();
			return;
		}
		if (shop.getId() != Shop.GENERAL_STORE) {
			for (int shopItemIndex = 0; shopItemIndex < shop.getOriginalStock().length; shopItemIndex++) {

				int originalStockAmount = shop.getOriginalStock()[shopItemIndex].getAmount();
				int currentStockAmount = shop.getItems()[shopItemIndex].getAmount();

				if (originalStockAmount > currentStockAmount) {
					shop.add(shop.getItems()[shopItemIndex].getId(), 1);
				} else if (originalStockAmount < currentStockAmount) {
					shop.delete(shop.getItems()[shopItemIndex].getId(),
							getDeleteRatio(shop.getItems()[shopItemIndex].getAmount()));
				}

			}
		} else {
			for (Item it : shop.getValidItems()) {
				int delete = getDeleteRatio(it.getAmount());
				shop.delete(it.getId(), delete > 1 ? delete : 1);
			}
		}
		shop.publicRefresh();
		shop.refreshItems();
		if (shop.fullyRestocked())
			stop();
	}

	@Override
	public void stop() {
		setEventRunning(false);
		shop.setRestockingItems(false);
	}
}
