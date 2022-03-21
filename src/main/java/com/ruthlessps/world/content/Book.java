package com.ruthlessps.world.content;

import com.ruthlessps.model.Animation;
import com.ruthlessps.world.entity.impl.player.Player;

public abstract class Book {

	private String[][] pages;
	private int pageIndex;

	public Book(String[][] pages) {
		this.pages = pages;
	}

	public abstract String getName();

	public void decreasePage() {
		this.pageIndex -= 2;
		if (this.pageIndex < 0) {
			this.pageIndex = 0;
		}
	}

	public void increasePage() {
		this.pageIndex += 2;

		if (this.pageIndex >= pages.length) {
			this.pageIndex = pages.length - 2;
			return;
		}
	}

	public void readBook(Player player, boolean interfaceAllowed) {
		if (player.getInterfaceId() != -1 && !interfaceAllowed) {
			player.getPacketSender().sendMessage("Please close the interface you have open before opening a new one.");
			return;
		}

		player.getMovementQueue().reset();
		player.performAnimation(new Animation(1350));
		int line = 58514;

		for (int i = 0; i < 10; i++) {
			if (line == 58534) {
				continue;
			}
			if (pages[pageIndex].length > i)
				player.getPacketSender().sendString(line, pages[pageIndex][i]);
			else
				player.getPacketSender().sendString(line, "");
			line += 2;
		}
		line = 58515;
		for (int i = 0; i < 10; i++) {
			if (pages[pageIndex + 1].length > i)
				player.getPacketSender().sendString(line, pages[pageIndex + 1][i]);
			else
				player.getPacketSender().sendString(line, "");
			line += 2;
		}
		player.getPacketSender().sendString(58534, getName());
		player.getPacketSender().sendString(58535, "" + pageIndex + "");
		player.getPacketSender().sendString(58536, "" + (pageIndex + 1) + "");

		int id = 58570;
		if (pageIndex > 0) {
			id = 58580;
		}
		if (pageIndex == pages.length - 2) {
			id = 58590;
		}
		if (pages.length == 2) {
			id = 58510;
		}
		player.getPacketSender().sendInterface(id);
	}

}
