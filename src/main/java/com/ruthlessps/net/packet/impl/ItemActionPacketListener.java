package com.ruthlessps.net.packet.impl;

import com.ruthlessps.drops.NPCDrops;
import com.ruthlessps.engine.task.impl.QuestAssigner;
import com.ruthlessps.model.Flag;
import com.ruthlessps.model.GameMode;
import com.ruthlessps.model.GameObject;
import com.ruthlessps.model.Item;
import com.ruthlessps.model.PlayerRanks;
import com.ruthlessps.model.Position;
import com.ruthlessps.net.packet.Packet;
import com.ruthlessps.net.packet.PacketListener;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.World;
import com.ruthlessps.world.content.BoostManager;
import com.ruthlessps.world.content.Consumables;
import com.ruthlessps.world.content.Deals;
import com.ruthlessps.world.content.Digging;
import com.ruthlessps.world.content.DonorTickets;
import com.ruthlessps.world.content.ExperienceLamps;
import com.ruthlessps.world.content.Gambling;
import com.ruthlessps.world.content.MoneyPouch;
import com.ruthlessps.world.content.Morphing;
import com.ruthlessps.world.content.PlayerPanel;
import com.ruthlessps.world.content.combat.range.DwarfMultiCannon;
import com.ruthlessps.world.content.dialogue.DialogueManager;
import com.ruthlessps.world.content.skill.impl.construction.Construction;
import com.ruthlessps.world.content.skill.impl.dungeoneering.ItemBinding;
import com.ruthlessps.world.content.skill.impl.herblore.Herblore;
import com.ruthlessps.world.content.skill.impl.herblore.IngredientsBook;
import com.ruthlessps.world.content.skill.impl.herblore.SkillCasket;
import com.ruthlessps.world.content.skill.impl.hunter.BoxTrap;
import com.ruthlessps.world.content.skill.impl.hunter.Hunter;
import com.ruthlessps.world.content.skill.impl.hunter.JarData;
import com.ruthlessps.world.content.skill.impl.hunter.PuroPuro;
import com.ruthlessps.world.content.skill.impl.hunter.SnareTrap;
import com.ruthlessps.world.content.skill.impl.hunter.Trap.TrapState;
import com.ruthlessps.world.content.skill.impl.prayer.Prayer;
import com.ruthlessps.world.content.skill.impl.runecrafting.Runecrafting;
import com.ruthlessps.world.content.skill.impl.runecrafting.RunecraftingPouches;
import com.ruthlessps.world.content.skill.impl.runecrafting.RunecraftingPouches.RunecraftingPouch;
import com.ruthlessps.world.content.skill.impl.slayer.SlayerDialogues;
import com.ruthlessps.world.content.skill.impl.slayer.SlayerTasks;
import com.ruthlessps.world.content.skill.impl.summoning.CharmingImp;
import com.ruthlessps.world.content.skill.impl.summoning.SummoningData;
import com.ruthlessps.world.content.skill.impl.woodcutting.BirdNests;
import com.ruthlessps.world.content.teleportation.JewelryTeleporting;
import com.ruthlessps.world.entity.impl.player.Player;

public class ItemActionPacketListener implements PacketListener {

	public static final int SECOND_ITEM_ACTION_OPCODE = 75;

	public static final int FIRST_ITEM_ACTION_OPCODE = 122;

	public static final int THIRD_ITEM_ACTION_OPCODE = 16;

	private static void firstAction(final Player player, Packet packet) {
		int interfaceId = packet.readUnsignedShort();
		int slot = packet.readShort();
		int itemId = packet.readShort();

		if (interfaceId == 38274) {
			Construction.handleItemClick(itemId, player);
			return;
		}

		if (slot < 0 || slot > player.getInventory().capacity())
			return;
		if (player.getInventory().getItems()[slot].getId() != itemId)
			return;
		player.setInteractingItem(player.getInventory().getItems()[slot]);
		if (Prayer.buryBone(player, itemId)) {
			return;
		}

		if (Consumables.isFood(player, itemId, slot))
			return;
		if (Consumables.isPotion(itemId)) {
			Consumables.handlePotion(player, itemId, slot);
			return;
		}
		if (BirdNests.isNest(itemId)) {
			BirdNests.searchNest(player, itemId);
			return;
		}
		if (Herblore.cleanHerb(player, itemId))
			return;
		if (DonorTickets.handleTicket(player, itemId))
			return;
		if (ExperienceLamps.handleLamp(player, itemId)) {
			return;
		}
		switch (itemId) {
		case 15098:
			if(!player.getClanChatName().equalsIgnoreCase("dice")) {
				player.getPacketSender().sendMessage("@red@You must be in the Dice clan chat to roll dice!");
				return;
			}
			Gambling.rollDice(player);
			break;
			case 15078:
				player.getAttributes().transformToNPC(1864);
				player.getAttributes().calculateBonusDropRate(0);
				break;
			case 15080:
				player.getAttributes().transformToNPC(640);
				player.getAttributes().calculateBonusDropRate(0);
				break;
			case 15082:
				player.getAttributes().transformToNPC(1235);
				player.getAttributes().calculateBonusDropRate(0);
				break;
			case 15086:
				player.getAttributes().transformToNPC(1230);
				player.getAttributes().calculateBonusDropRate(0);
				break;
			case 15088:
				player.getAttributes().transformToNPC(1233);
				player.getAttributes().calculateBonusDropRate(0);
				break;
		case 611:
			QuestAssigner.assign(player);
		break;
		case 612:
			QuestAssigner.tellQuest(player);
		break;
		case 10936:
			Deals.give(1, player);
			break;
		case 10944:
			Deals.give(2, player);
			break;
		case 12421:
			Deals.give(3, player);
			break;
		case 15356:
			Deals.give(4, player);
			break;
		case 14796:
			player.setInfinitePrayer(true);
			break;
		case 19864:
			int amt = player.getInventory().getAmount(19864);
			player.getInventory().delete(19864, player.getInventory().getAmount(19864));
			player.getPointsManager().increasePoints("slayer", amt);
			PlayerPanel.refreshPanel(player);
			player.getPacketSender().sendMessage("You successfully turn your "+ amt+" slayer tokens into Slayer points");
			break;
		case 18829:
			player.getGroupSlayer().createGroup();
			break;
		case 1321:
			player.getInventory().delete(1321, 1);
			player.getPointsManager().setWithIncrease("voting", 1);
			player.getPacketSender().sendMessage("Your vote point has been added to your vote points.");
			PlayerPanel.refreshPanel(player);
			break;
		case 6798:
				player.handleDonor(player.getAmountDonated(), player.getAmountDonated()+1);
			player.incrementAmountDonated(1);
				player.getInventory().delete(6798, 1);
				player.getPointsManager().setWithIncrease("donation", 1);
				player.getPacketSender().sendMessage("Your 1 donator point has been added.");
				PlayerPanel.refreshPanel(player);
		break;
			case 6853:
				if(player.getInventory().isFull() && (!player.getInventory().contains(13664))) {
					player.getPA().sendMessage("You need 1 free inventory space to do this!");
					return;
				} else {
					player.getInventory().delete(6853, 1);
					player.getInventory().add(13664, 5000 + Misc.random(50000));
					player.getPA().sendMessage("You claim some money.");
				}
		case 6799:
			player.handleDonor(player.getAmountDonated(), player.getAmountDonated()+5);
			player.incrementAmountDonated(5);
				player.getInventory().delete(6799, 1);
				player.getPointsManager().setWithIncrease("donation", 5);
				player.getPacketSender().sendMessage("Your 5 donator points has been added.");
				PlayerPanel.refreshPanel(player);
			break;
		case 6800:
			player.handleDonor(player.getAmountDonated(), player.getAmountDonated()+10);
			player.incrementAmountDonated(10);
				player.getInventory().delete(6800, 1);
				player.getPointsManager().setWithIncrease("donation", 10);
				player.getPacketSender().sendMessage("Your 10 donator points has been added.");
				PlayerPanel.refreshPanel(player);
			break;
		case 6801:
			player.handleDonor(player.getAmountDonated(), player.getAmountDonated()+25);
			player.incrementAmountDonated(25);
				player.getInventory().delete(6801, 1);
				player.getPointsManager().setWithIncrease("donation", 25);
				player.getPacketSender().sendMessage("Your 25 donator points has been added.");
				PlayerPanel.refreshPanel(player);
			break;
		case 6802:
			player.handleDonor(player.getAmountDonated(), player.getAmountDonated()+50);
				player.incrementAmountDonated(50);
				player.getInventory().delete(6802, 1);
				player.getPointsManager().setWithIncrease("donation", 50);
				player.getPacketSender().sendMessage("Your 50 donator points has been added.");
				PlayerPanel.refreshPanel(player);
			break;
		case 6803:
			player.handleDonor(player.getAmountDonated(), player.getAmountDonated()+100);
			player.incrementAmountDonated(100);
				player.getInventory().delete(6803, 1);
				player.getPointsManager().setWithIncrease("donation", 100);
				player.getPacketSender().sendMessage("Your 100 donator points has been added.");
				PlayerPanel.refreshPanel(player);
			break;
		case 6804:
			player.handleDonor(player.getAmountDonated(), player.getAmountDonated()+150);
			player.incrementAmountDonated(150);
				player.getInventory().delete(6804, 1);
				player.getPointsManager().setWithIncrease("donation", 150);
				player.getPacketSender().sendMessage("Your 150 donator points has been added.");
				PlayerPanel.refreshPanel(player);
			break;
		case 6805:
			player.handleDonor(player.getAmountDonated(), player.getAmountDonated()+200);
			player.incrementAmountDonated(200);
				player.getInventory().delete(6805, 1);
				player.getPointsManager().setWithIncrease("donation", 200);
				player.getPacketSender().sendMessage("Your 200 donator points has been added.");
				PlayerPanel.refreshPanel(player);
			break;
		case 6806:
			player.handleDonor(player.getAmountDonated(), player.getAmountDonated()+250);
			player.incrementAmountDonated(250);
				player.getInventory().delete(6806, 1);
				player.getPointsManager().setWithIncrease("donation", 250);
				player.getPacketSender().sendMessage("Your 250 donator points has been added.");
				PlayerPanel.refreshPanel(player);
			break;
		case 6807:
			player.handleDonor(player.getAmountDonated(), player.getAmountDonated()+500);
			player.incrementAmountDonated(500);
				player.getInventory().delete(6807, 1);
				player.getPointsManager().setWithIncrease("donation", 500);
				player.getPacketSender().sendMessage("Your 500 donator points has been added.");
				PlayerPanel.refreshPanel(player);
			break;
		case 1856:
			player.getPacketSender().sendString(1, "www.rivalps.tech/showthread.php?tid=14&pid=28#pid28");
			break;
		case 13663:
			if (player.getInterfaceId() > 0) {
				player.getPacketSender().sendMessage("Please close the interface you have open before doing this.");
				return;
			}
			player.setUsableObject(new Object[2]).setUsableObject(0, "reset");
			player.getPacketSender().sendString(38006, "Choose stat to reset!")
					.sendMessage("@red@Please select a skill you wish to reset and then click on the 'Confirm' button.")
					.sendString(38090, "Which skill would you like to reset?");
			player.getPacketSender().sendInterface(38000);
			break;
		case 19670:
			if (player.busy()) {
				player.getPacketSender().sendMessage("You can not do this right now.");
				return;
			}
			player.setDialogueActionId(70);
			DialogueManager.start(player, player.getGameMode() == GameMode.NORMAL ? 108 : 109);
			break;
		case 7956:
			SkillCasket.openCasket(player, Misc.random(3)+1);
			break;
		case 20692:
			player.getInventory().delete(20692, 1);
			player.getInventory().add(11609, 1000);
			break;
		case 407:
			player.getInventory().delete(407, 1);
			if (Misc.getRandom(3) < 3) {
				player.getInventory().add(409, 1);
			} else if (Misc.getRandom(4) < 4) {
				player.getInventory().add(411, 1);
			} else
				player.getInventory().add(413, 1);
			break;
		case 405:
			player.getInventory().delete(405, 1);
			if (Misc.getRandom(1) < 1) {
				int coins = Misc.getRandom(30000);
				player.getInventory().add(995, coins);
				player.getPacketSender().sendMessage("The casket contained " + coins + " coins!");
			} else
				player.getPacketSender().sendMessage("The casket was empty.");
			break;
		case 15084:
			Gambling.rollDice(player);
			break;
		case 299:
			Gambling.plantSeed(player);
			break;
		case 4155:
			if (player.getSlayer().getSlayerTask() == SlayerTasks.NO_TASK) {
				player.getPacketSender().sendInterfaceRemoval();
				player.getPacketSender().sendMessage("Your Enchanted gem will only work if you have a Slayer task.");
				return;
			}
			DialogueManager.start(player, SlayerDialogues.dialogue(player));
			break;
		case 11858:
		case 11860:
		case 11862:
		case 11848:
		case 11856:
		case 11850:
		case 11854:
		case 11852:
		case 11846:
			if (!player.getClickDelay().elapsed(2000) || !player.getInventory().contains(itemId))
				return;
			if (player.busy()) {
				player.getPacketSender().sendMessage("You cannot open this right now.");
				return;
			}

			int[] items = itemId == 11858 ? new int[] { 10350, 10348, 10346, 10352 }
			: itemId == 11860 ? new int[] { 10334, 10330, 10332, 10336 }
			: itemId == 11862 ? new int[] { 10342, 10338, 10340, 10344 }
			: itemId == 11848 ? new int[] { 4716, 4720, 4722, 4718 }
			: itemId == 11856 ? new int[] { 4753, 4757, 4759, 4755 }
			: itemId == 11850 ? new int[] { 4724, 4728, 4730, 4726 }
			: itemId == 11854 ? new int[] { 4745, 4749, 4751, 4747 }
			: itemId == 11852 ? new int[] { 4732, 4734, 4736, 4738 }
			: new int[] { 4708, 4712, 4714, 4710 };

			if (player.getInventory().getFreeSlots() < items.length) {
				player.getPacketSender().sendMessage("You do not have enough space in your inventory.");
				return;
			}
			player.getInventory().delete(itemId, 1);
			for (int i : items) {
				player.getInventory().add(i, 1);
			}
			player.getPacketSender().sendMessage("You open the set and find items inside.");
			player.getClickDelay().reset();
			break;
		case 952:
			Digging.dig(player);
			break;
		case 10006:
			// Hunter.getInstance().laySnare(client);
			Hunter.layTrap(player, new SnareTrap(new GameObject(19175, new Position(player.getPosition().getX(),
					player.getPosition().getY(), player.getPosition().getZ())), TrapState.SET, 200, player));
			break;
		case 10008:
			Hunter.layTrap(player, new BoxTrap(new GameObject(19187, new Position(player.getPosition().getX(),
					player.getPosition().getY(), player.getPosition().getZ())), TrapState.SET, 200, player));
			break;
		case 5509:
		case 5510:
		case 5512:
			RunecraftingPouches.fill(player, RunecraftingPouch.forId(itemId));
			break;
		case 292:
			player.setActiveBook(new IngredientsBook());
			player.getActiveBook().readBook(player, false);
			break;

		case 85:
			if (player.getDonor() != PlayerRanks.DonorRights.NONE) {
				player.sendMessage("You cannot claim this rank.");
				return;
			}
			player.getInventory().delete(85, 1);
			player.setDonor(PlayerRanks.DonorRights.DONOR);
			player.getPacketSender().sendRights();
			player.sendMessage("Thank you for your donation, you are now a donor.");
			break;
		case 275:
			if (player.getDonor() == PlayerRanks.DonorRights.DELUXE_DONOR
					|| player.getDonor() == PlayerRanks.DonorRights.SPONSOR) {
				player.sendMessage("You cannot claim this rank.");
				return;
			}
			player.getInventory().delete(275, 1);
			player.setDonor(PlayerRanks.DonorRights.DELUXE_DONOR);
			player.getPacketSender().sendRights();
			player.sendMessage("Thank you for your donation, you are now a deluxe donor.");
			World.sendMessage("<img=6><col=00ff00><shad=0> " + player.getUsername()
			+ " has just become a Deluxe Donator!");
			break;
		case 293:
			if (player.getDonor() == PlayerRanks.DonorRights.SPONSOR
					|| player.getDonor() == PlayerRanks.DonorRights.SUPER_SPONSOR) {
				player.sendMessage("You cannot claim this rank.");
				return;
			}
			player.getInventory().delete(293, 1);
			player.setDonor(PlayerRanks.DonorRights.SPONSOR);
			player.getPacketSender().sendRights();
			player.sendMessage("Thank you for your donation, you are now a sponsor.");
			World.sendMessage("<img=7><col=FF0000><shad=0> " + player.getUsername()
			+ " has just become a Sponsor!");
			break;
		case 6509:
			if (player.getDonor() == PlayerRanks.DonorRights.SUPER_SPONSOR) {
				player.sendMessage("You cannot claim this rank.");
				return;
			}
			player.getInventory().delete(6509, 1);
			player.setDonor(PlayerRanks.DonorRights.SUPER_SPONSOR);
			player.getPacketSender().sendRights();
			player.sendMessage("Thank you for your donation, you are now a sponsor.");
			World.sendMessage("<img=656><col=F300FF><shad=0> " + player.getUsername()
			+ " <col=000CFF><shad=0>has just <col=36FF00><shad=0>become a <col=FF0000><shad=0>Super Sponsor!");
			break;
		case 6199:
			if (player.getInventory().getFreeSlots() <= 0) {
				player.sendMessage("You need 1 open space to receive your reward.");
				return;
			}

			if (player.getInterfaceId() > 0) {
				player.getPacketSender().sendMessage("Please close any interfaces you have open!");
			} else {
				player.regularBox.open(player);
			}
			break;
		case 4032:
			if (player.getInventory().getFreeSlots() <= 0) {
				player.sendMessage("You need 1 open space to receive your reward.");
				return;
			}

			//FancyBox.giveReward(player);
			if (player.getInterfaceId() > 0) {
				player.getPacketSender().sendMessage("Please close any interfaces you have open!");
			} else {
				player.fancyBox.open(player);
			}
			break;
		case 2801:
			if (player.getInventory().getFreeSlots() <= 0) {
				player.sendMessage("You need 1 open space to receive your reward.");
				return;
			}
			if (player.getInterfaceId() > 0) {
				player.getPacketSender().sendMessage("Please close any interfaces you have open!");
			} else {
				player.torvaBox.open(player);
			}
			//TorvaMysteryBox.giveReward(player);
			break;
		case 6183:
			if (player.getInventory().getFreeSlots() <= 0) {
				player.sendMessage("You need 1 open space to receive your reward.");
				return;
			}

			if (player.getInterfaceId() > 0) {
				player.getPacketSender().sendMessage("Please close any interfaces you have open!");
			} else {
				player.fancyBox.open(player);
			}
			break;
		case 15501:
			if (player.getInventory().getFreeSlots() <= 0) {
				player.sendMessage("You need 1 open space to receive your reward.");
				return;
			}

			if (player.getInterfaceId() > 0) {
				player.getPacketSender().sendMessage("Please close any interfaces you have open!");
			} else {
				player.megaBox.open(player);
			}
			//SpinMysteryBox.open(player); //fingers crossed that the packets work as intented, we need to reboot now
			//MegaMysteryBox.giveReward(player);
			break;
		case 6930:
			if (player.getInventory().getFreeSlots() <= 0) {
				player.sendMessage("You need 1 open space to receive your reward.");
				return;
			}

			if (player.getInterfaceId() > 0) {
				player.getPacketSender().sendMessage("Please close any interfaces you have open!");
			} else {
				player.donatorBox.open(player);
			}
			break;
		case 916:
			if (player.getInventory().getFreeSlots() <= 0) {
				player.sendMessage("You need 1 open space to receive your reward.");
				return;
			}

			if (player.getInterfaceId() > 0) {
				player.getPacketSender().sendMessage("Please close any interfaces you have open!");
			} else {
				player.omegaBox.open(player);
			}
			break;
		case 917:
			if (player.getInventory().getFreeSlots() <= 0) {
				player.sendMessage("You need 1 open space to receive your reward.");
				return;
			}

			if (player.getInterfaceId() > 0) {
				player.getPacketSender().sendMessage("Please close any interfaces you have open!");
			} else {
				player.sharpyBox.open(player);
			}
			break;
		case 18768:
			if (player.getInventory().getFreeSlots() <= 0) {
				player.sendMessage("You need 1 open space to receive your reward.");
				return;
			}

			if (player.getInterfaceId() > 0) {
				player.getPacketSender().sendMessage("Please close any interfaces you have open!");
			} else {
				player.pvmBox.open(player);
			}
			break;
		case 2800:
			if (player.getInventory().getFreeSlots() <= 0) {
				player.sendMessage("You need 1 open space to receive your reward.");
				return;
			}

			if (player.getInterfaceId() > 0) {
				player.getPacketSender().sendMessage("Please close any interfaces you have open!");
			} else {
				player.petBox.open(player);
			}
			break;
		case 14421:
			player.setKcReq(true);
			player.getInventory().delete(14421, 1);
			break;
			
		case 11882:
			if (player.getInventory().getFreeSlots() <= 3) {
				player.sendMessage("You need 4 open spaces to open this box.");
				return;
			}

			player.getInventory().delete(11882, 1);
			player.getInventory().add(2595, 1);
			player.getInventory().add(2591, 1);
			player.getInventory().add(3473, 1);
			player.getInventory().add(2597, 1);
			player.getInventory().refreshItems();
			break;
		case 6855:
			if (player.getInventory().getFreeSlots() <= 13) {
				player.sendMessage("You need 14 open spaces to open this box.");
				return;
			}

			player.getInventory().delete(6855, 1);
			player.getInventory().add(556, 1000);
			player.getInventory().add(558, 1000);
			player.getInventory().add(555, 1000);
			player.getInventory().add(554, 1000);
			player.getInventory().add(557, 1000);
			player.getInventory().add(559, 1000);
			player.getInventory().add(564, 1000);
			player.getInventory().add(562, 1000);
			player.getInventory().add(566, 1000);
			player.getInventory().add(9075, 1000);
			player.getInventory().add(563, 1000);
			player.getInventory().add(561, 1000);
			player.getInventory().add(560, 1000);
			player.getInventory().add(565, 1000);
			player.getInventory().refreshItems();
			break;
		case 11884:
			if (player.getInventory().getFreeSlots() <= 3) {
				player.sendMessage("You need 4 open spaces to open this box.");
				return;
			}

			player.getInventory().delete(11884, 1);
			player.getInventory().add(2595, 1);
			player.getInventory().add(2591, 1);
			player.getInventory().add(2593, 1);
			player.getInventory().add(2597, 1);
			player.getInventory().refreshItems();
			break;
		case 11906:
			if (player.getInventory().getFreeSlots() <= 2) {
				player.sendMessage("You need 3 open spaces to open this box.");
				return;
			}

			player.getInventory().delete(11906, 1);
			player.getInventory().add(7394, 1);
			player.getInventory().add(7390, 1);
			player.getInventory().add(7386, 1);
			player.getInventory().refreshItems();
			break;
		case 15262:
			if (!player.getClickDelay().elapsed(1000))
				return;
			player.getInventory().delete(15262, 1);
			player.getInventory().add(18016, 10000).refreshItems();
			player.getClickDelay().reset();
			break;
		case 6:
			DwarfMultiCannon.setupCannon(player);
			break;
		}
	}

	@SuppressWarnings("unused")
	private static void secondAction(Player player, Packet packet) {
		int interfaceId = packet.readLEShortA();
		int slot = packet.readLEShort();
		int itemId = packet.readShortA();
		if (slot < 0 || slot > player.getInventory().capacity())
			return;
		if (player.getInventory().getItems()[slot].getId() != itemId)
			return;
		if (SummoningData.isPouch(player, itemId, 2))
			return;
		switch (itemId) {
		case 6500:
			if (player.getCombatBuilder().isAttacking() || player.getCombatBuilder().isBeingAttacked()) {
				player.getPacketSender().sendMessage("You cannot configure this right now.");
				return;
			}
			player.getPacketSender().sendInterfaceRemoval();
			DialogueManager.start(player, 101);
			player.setDialogueActionId(60);
			break;
		case 1712:
		case 1710:
		case 1708:
		case 1706:
		case 11118:
		case 11120:
		case 11122:
		case 11124:
			JewelryTeleporting.rub(player, itemId);
			break;
		case 16150:
			player.getPA().sendMessage("Doesn't do anything at the moment.");
			break;
		case 19864:
			int amt = player.getInventory().getAmount(19864);
			player.getInventory().delete(19864, player.getInventory().getAmount(19864));
			player.getPointsManager().increasePoints("slayer", amt);
			player.getPacketSender().sendMessage("You successfully turn your "+ amt+" slayer tokens into Slayer points");
			break;
		case 3278:
			player.getInventory().delete(3278, 1);
			player.getInventory().add(3277, 1);
			player.getInventory().add(3281, 1);
			player.getPacketSender().sendMessage("You remove the scope from your sniper.");
			break;
		case 3280:
			player.getInventory().delete(3280, 1);
			player.getInventory().add(3279, 1);
			player.getInventory().add(3283, 1);
			player.getPacketSender().sendMessage("You remove the suppressor from your submachine gun.");
			break;
		case 3135:
			player.getInventory().delete(3135, 1);
			player.getInventory().add(3092, 1);
			player.getInventory().add(3283, 1);
			player.getPacketSender().sendMessage("You remove the suppressor from your Desert Eagle.");
			break;
		case 3082:
			player.getInventory().delete(3082, 1);
			player.getInventory().add(3081, 1);
			player.getInventory().add(3282, 1);
			player.getPacketSender().sendMessage("You remove the grenade launcher from your AK-47.");
			break;
		case 15078:
			Morphing.rub(player, itemId);
			break;
		case 1704:
			player.getPacketSender().sendMessage("Your amulet has run out of charges.");
			break;
		case 11126:
			player.getPacketSender().sendMessage("Your bracelet has run out of charges.");
			break;
		case 13281:
		case 13282:
		case 13283:
		case 13284:
		case 13285:
		case 13286:
		case 13287:
		case 13288:
			player.getSlayer().handleSlayerRingTP(itemId);
			break;
		case 5509:
		case 5510:
		case 5512:
			RunecraftingPouches.check(player,  RunecraftingPouch.forId(itemId));
			break;
		case 995:
			MoneyPouch.depositMoney(player, player.getInventory().getAmount(995), false);
			break;
		case MoneyPouch.BUCKS_ID:
			MoneyPouch.depositMoney(player, player.getInventory().getAmount(MoneyPouch.BUCKS_ID), true);
			break;
		case 1438:
		case 1448:
		case 1440:
		case 1442:
		case 1444:
		case 1446:
		case 1454:
		case 1452:
		case 1462:
		case 1458:
		case 1456:
		case 1450:
			Runecrafting.handleTalisman(player, itemId);
			break;
		}
	}

	@Override
	public void handleMessage(Player player, Packet packet) {
		if (player.getConstitution() <= 0)
			return;
		//System.out.println(packet.getOpcode());
		switch (packet.getOpcode()) {
		case SECOND_ITEM_ACTION_OPCODE:
			secondAction(player, packet);
			break;
		case FIRST_ITEM_ACTION_OPCODE:
			firstAction(player, packet);
			break;
		case THIRD_ITEM_ACTION_OPCODE:
			thirdClickAction(player, packet);
			break;
		}
	}

	@SuppressWarnings("unused")
	private void thirdClickAction(Player player, Packet packet) {
		int itemId = packet.readShortA();
		int slot = packet.readLEShortA();
		int interfaceId = packet.readLEShortA();
		if (slot < 0 || slot > player.getInventory().capacity())
			return;
		if (player.getInventory().getItems()[slot].getId() != itemId)
			return;
		if (JarData.forJar(itemId) != null) {
			PuroPuro.lootJar(player, new Item(itemId, 1), JarData.forJar(itemId));
			return;
		}
		if (SummoningData.isPouch(player, itemId, 3)) {
			return;
		}
		if (ItemBinding.isBindable(itemId)) {
			ItemBinding.bindItem(player, itemId);
			return;
		}
		switch (itemId) {
		case 596:
			player.getPacketSender()
					.sendMessage("Your drop rate is currently increased by " + player.getAttributes().calculateBonusDropRate(0)*100+"%");
			break;
		case 19864:
			int amt = player.getInventory().getAmount(19864);
			player.getInventory().delete(19864, player.getInventory().getAmount(19864));
			player.getPointsManager().increasePoints("slayer", amt);
			player.getPacketSender().sendMessage("You successfully turn your "+ amt+" slayer tokens into Slayer points");
			break;
		case 18829:
			player.getGroupSlayer().deleteGroup();
			break;
		case 612:
			if(player.getQuestType() == -1) {
				player.getPacketSender().sendMessage("You do not have a quest to quit!");
				return;
			} else {
				player.setQuestType(-1);
				player.setCollectAmt(0);
				player.getPacketSender().sendMessage("You have quit your quest!");
				break;
			}
		case 14019:
		case 14022:
			player.setCurrentCape(itemId);
			int[] colors = itemId == 14019 ? player.getMaxCapeColors() : player.getCompCapeColors();
			String[] join = new String[colors.length];
			for (int i = 0; i < colors.length; i++) {
				join[i] = Integer.toString(colors[i]);
			}
			player.getPacketSender().sendString(60000, "[CUSTOMIZATION]" + itemId + "," + String.join(",", join));
			player.getPacketSender().sendInterface(60000);
			break;
			
			
		case 19670:
			if (player.busy()) {
				player.getPacketSender().sendMessage("You can not do this right now.");
				return;
			}
			player.setDialogueActionId(71);
			DialogueManager.start(player, player.getGameMode() == GameMode.NORMAL ? 108 : 109);
			break;
		case 6199:
			if (player.getInventory().getFreeSlots() <= 0) {
				player.sendMessage("You need 1 open space to receive your reward.");
				return;
			}

			if (player.getInterfaceId() > 0) {
				player.getPacketSender().sendMessage("Please close any interfaces you have open!");
			} else {
				player.regularBox.openFast(player);
			}
			break;
		case 4032:
			case 6183:
				if (player.getInventory().getFreeSlots() <= 0) {
				player.sendMessage("You need 1 open space to receive your reward.");
				return;
			}

			//FancyBox.giveReward(player);
			if (player.getInterfaceId() > 0) {
				player.getPacketSender().sendMessage("Please close any interfaces you have open!");
			} else {
				player.fancyBox.openFast(player);
			}
			break;
		case 2801:
			if (player.getInventory().getFreeSlots() <= 0) {
				player.sendMessage("You need 1 open space to receive your reward.");
				return;
			}
			if (player.getInterfaceId() > 0) {
				player.getPacketSender().sendMessage("Please close any interfaces you have open!");
			} else {
				player.torvaBox.openFast(player);
			}
			//TorvaMysteryBox.giveReward(player);
			break;

			case 15501:
			if (player.getInventory().getFreeSlots() <= 0) {
				player.sendMessage("You need 1 open space to receive your reward.");
				return;
			}

			if (player.getInterfaceId() > 0) {
				player.getPacketSender().sendMessage("Please close any interfaces you have open!");
			} else {
				player.megaBox.openFast(player);
			}
			//SpinMysteryBox.open(player); //fingers crossed that the packets work as intented, we need to reboot now
			//MegaMysteryBox.giveReward(player);
			break;
		case 6930:
			if (player.getInventory().getFreeSlots() <= 0) {
				player.sendMessage("You need 1 open space to receive your reward.");
				return;
			}

			if (player.getInterfaceId() > 0) {
				player.getPacketSender().sendMessage("Please close any interfaces you have open!");
			} else {
				player.donatorBox.openFast(player);
			}
			break;
		case 916:
			if (player.getInventory().getFreeSlots() <= 0) {
				player.sendMessage("You need 1 open space to receive your reward.");
				return;
			}

			if (player.getInterfaceId() > 0) {
				player.getPacketSender().sendMessage("Please close any interfaces you have open!");
			} else {
				player.omegaBox.openFast(player);
			}
			break;
		case 917:
			if (player.getInventory().getFreeSlots() <= 0) {
				player.sendMessage("You need 1 open space to receive your reward.");
				return;
			}

			if (player.getInterfaceId() > 0) {
				player.getPacketSender().sendMessage("Please close any interfaces you have open!");
			} else {
				player.sharpyBox.openFast(player);
			}
			break;
		case 18768:
			if (player.getInventory().getFreeSlots() <= 0) {
				player.sendMessage("You need 1 open space to receive your reward.");
				return;
			}

			if (player.getInterfaceId() > 0) {
				player.getPacketSender().sendMessage("Please close any interfaces you have open!");
			} else {
				player.pvmBox.openFast(player);
			}
			break;
		case 2800:
			if (player.getInventory().getFreeSlots() <= 0) {
				player.sendMessage("You need 1 open space to receive your reward.");
				return;
			}

			if (player.getInterfaceId() > 0) {
				player.getPacketSender().sendMessage("Please close any interfaces you have open!");
			} else {
				player.petBox.openFast(player);
			}
			break;
		case 6500:
			CharmingImp.sendConfig(player);
			break;
		case 4155:
			player.getPacketSender().sendInterfaceRemoval();
			DialogueManager.start(player, 103);
			player.setDialogueActionId(65);
			break;
		case 13281:
		case 13282:
		case 13283:
		case 13284:
		case 13285:
		case 13286:
		case 13287:
		case 13288:
			player.getPacketSender().sendInterfaceRemoval();
			player.getPacketSender().sendMessage(player.getSlayer().getSlayerTask() == SlayerTasks.NO_TASK
					? ("You do not have a Slayer task.")
					: ("Your current task is to kill another " + (player.getSlayer().getAmountToSlay()) + " "
							+ Misc.formatText(
									player.getSlayer().getSlayerTask().toString().toLowerCase().replaceAll("_", " "))
							+ "s."));
			break;
		case 6570:
			if (player.getInventory().contains(6570) && player.getInventory().getAmount(6529) >= 50000) {
				player.getInventory().delete(6570, 1).delete(6529, 50000).add(19111, 1);
				player.getPacketSender().sendMessage("You have upgraded your Fire cape into a TokHaar-Kal cape!");
			} else {
				player.getPacketSender().sendMessage(
						"You need at least 50.000 Tokkul to upgrade your Fire Cape into a TokHaar-Kal cape.");
			}
			break;
		case 15262:
			if (!player.getClickDelay().elapsed(1300))
				return;
			int amt2 = player.getInventory().getAmount(15262);
			if (amt2 > 0)
				player.getInventory().delete(15262, amt2).add(18016, 10000 * amt2);
			player.getClickDelay().reset();
			break;
		case 5509:
		case 5510:
		case 5512:
			RunecraftingPouches.empty(player, RunecraftingPouch.forId(itemId));
			break;
		case 21084: // DFS
		case 21083:
		case 21082:
			player.getPacketSender()
					.sendMessage("Your Dragonfire shield has " + player.getDfsCharges() + "/20 dragon-fire charges.");
			break;
		}
	}

}