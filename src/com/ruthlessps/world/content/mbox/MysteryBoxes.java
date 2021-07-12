package com.ruthlessps.world.content.mbox;

import java.util.ArrayList;

import com.ruthlessps.model.Item;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.content.mbox.impl.MegaBox.Rewards;
import com.ruthlessps.world.entity.impl.player.Player;

public abstract class MysteryBoxes {

	//Mystery box id
	public abstract int getBox();
	
	//Name of the box
	public abstract String getName();
	
	//Gets what array it should use
	public abstract ArrayList<Item> getArray(Player player);
	
	//Get rewards
	public abstract ArrayList<MysteryBoxRewards> getRewards();

	/**
	 * Opens up the box interface
	 * @param player
	 */
	public void open(Player player) {
		//Sets the title
		player.getPacketSender().sendString(65005, getName());
		//Creates a new array list of items
		ArrayList<Item> rewards = new ArrayList<Item>();
		//Fills the array with all rewards
		for(MysteryBoxRewards reward : getRewards())
			rewards.add(reward.reward);
		//Updates the possible reward container
		player.getPacketSender().sendItemsOnInterface(65011, rewards.toArray(new Item[0]));
		//Sends the rewards to the client
		player.getPacketSender().sendRewards(getRewards());
		//Refresh the latest rewards 
		player.getPacketSender().sendItemsOnInterface(65013, getArray(player).toArray(new Item[0]));
		//Opens the interface
		player.getPacketSender().sendInterface(65000);
		//Sets a variable to know what box you have open
		player.boxOpen = getName();
	}
	public void openFast(Player player) {
		//Creates a new array list of items
		ArrayList<Item> rewards = new ArrayList<Item>();
		//Fills the array with all rewards
		for(MysteryBoxRewards reward : getRewards())
			rewards.add(reward.reward);
		player.boxOpen = getName();
		spinFast(player);
	}
	
	public void spin(Player player) {
		//Checks if the player has the box in his inventory
		if(!player.getInventory().contains(getBox())) {
			player.getPacketSender().sendMessage("You do not have a "+getName()+" to spin with!");
			return;
		}
		//Checks if the player isnt already spinning for a reward
		if(player.winnerIndex != -1) {
			player.getPacketSender().sendMessage("Please wait before your current spin is done.");
			return;
		}
		double random = Math.random();
		Rarity rarity = Rarity.COMMON;
		if(random <= 0.7) { 
			rarity = Rarity.COMMON;
		} else if(random <= 0.90) {
			rarity = Rarity.UNCOMMON;
		} else if(random <= 0.96) {
			rarity = Rarity.RARE;
		} else if(random <= 0.99) {
			rarity = Rarity.VERY_RARE;
		} else {
			rarity = Rarity.UNCOMMON;
		}
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		//Adds all rewards with the same rarity as it rolled to the array
		for(int i = 0; i < getRewards().size(); i++)
			if(getRewards().get(i).rarity.equals(rarity))
				indexes.add(i);
		//Sets what item index you have won
		int won = indexes.get(Misc.random(indexes.size() - 1));
		//Removes the box from the inventory
		player.getInventory().delete(new Item(getBox())).refreshItems();
		//Sets the winner index incase of dc/closing interface
		player.winnerIndex = won;
		//Sends the winner index to the client
		player.getPacketSender().sendWinnerIndex(won);
	}
	public void spinFast(Player player) {
		//Checks if the player has the box in his inventory
		if(!player.getInventory().contains(getBox())) {
			player.getPacketSender().sendMessage("You do not have a "+getName()+" to open!");
			return;
		}
		//Checks if the player isnt already spinning for a reward
		if(player.winnerIndex != -1) {
			player.getPacketSender().sendMessage("Please wait before your current spin is done.");
			return;
		}
		//SUVALINE NUMBER NULLIST ÜHENI
		double random = Math.random();
		//ANNAB REWARDI, VAADATES MIS NUMBER TULI
		//MUUDA NEID NUMBREID NII, ET COMMONIL OLEKS VOIMALIKULT SUUR TÕENÄOSUS JA RARE JA LEGENDARY ON UMBES 3-4% VMS
		Rarity rarity = Rarity.COMMON;
		if(random <= 0.40) { //Kui arv tuli 0 kuni 0.25 ehk 25% tõenäosus
			rarity = Rarity.COMMON;//Siis tuleb common reward
		} else if(random <= 0.70) {//Kui arv tuli 0.26 kuni 0.79 ehk 54% tõenäosus
			rarity = Rarity.UNCOMMON;//Siis tuleb uncommon
		} else if(random <= 0.84) {//Kui arv tuli 0.80 kuni 0.83 ehk 4% tõenäosus
			rarity = Rarity.RARE;//Siis tuleb rare
		} else if(random <= 0.90) {//Kui arv tuli 0.84 kuni 1.0 ehk 16% tõenäosus
			rarity = Rarity.VERY_RARE;//Siis tuleb very rare
		} else {
			rarity = Rarity.UNCOMMON;
		}
		//rarity = random >= 0.75 ? Rarity.COMMON : random >= 0.21 ? Rarity.UNCOMMON : random >= 0.04 ? Rarity.RARE : Rarity.VERY_RARE;
		//Creates an empty array for the rewards with the same rarity
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		//Adds all rewards with the same rarity as it rolled to the array
		for(int i = 0; i < getRewards().size(); i++)
			if(getRewards().get(i).rarity.equals(rarity))
				indexes.add(i);
		//Sets what item index you have won
		int won = indexes.get(Misc.random(indexes.size() - 1));
		player.getInventory().delete(new Item(getBox())).refreshItems();
		player.winnerIndex = won;
		Item receive = getRewards().get(player.winnerIndex).reward;
		//Adds the rewards
		player.getInventory().add(receive).refreshItems();
		//Refreshes the inventory
		//Resets the winner index
		player.winnerIndex = -1;
	}
	
	public void receive(Player player) {
		//Checks if the player actaully won something
		if(player.winnerIndex == -1)
			return;
		//Item reward
		Item receive = getRewards().get(player.winnerIndex).reward;
		//Adds the rewards
		player.getInventory().add(receive).refreshItems();
		//Refreshes the inventory
		//Resets the winner index
		player.winnerIndex = -1;
		//Adds the winning to rewards
		getArray(player).add(0, receive);
		//If you have more then 100 rewards remove the last
		if(getArray(player).size() > 100)
			getArray(player).remove(getArray(player).size() - 1);
		//Refresh the latest rewards 
		player.getPacketSender().sendItemsOnInterface(65013, getArray(player).toArray(new Item[0]));
	}
}
