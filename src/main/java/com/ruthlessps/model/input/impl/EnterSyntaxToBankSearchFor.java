package com.ruthlessps.model.input.impl;

import com.ruthlessps.model.container.impl.Bank.BankSearchAttributes;
import com.ruthlessps.model.input.Input;
import com.ruthlessps.world.entity.impl.player.Player;

public class EnterSyntaxToBankSearchFor extends Input {

	@Override
	public void handleSyntax(Player player, String syntax) {
		boolean searchingBank = player.isBanking() && player.getBankSearchingAttribtues().isSearchingBank();
		if (searchingBank)
			BankSearchAttributes.beginSearch(player, syntax);
	}
}
