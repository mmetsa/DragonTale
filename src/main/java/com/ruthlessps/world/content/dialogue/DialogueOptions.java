package com.ruthlessps.world.content.dialogue;

import com.ruthlessps.GameSettings;
import com.ruthlessps.engine.task.impl.BonusExperienceTask;
import com.ruthlessps.model.Flag;
import com.ruthlessps.model.GameMode;
import com.ruthlessps.model.GameObject;
import com.ruthlessps.model.Locations.Location;
import com.ruthlessps.model.PlayerRanks.DonorRights;
import com.ruthlessps.model.PlayerRanks.PlayerRights;
import com.ruthlessps.model.Position;
import com.ruthlessps.model.Skill;
import com.ruthlessps.model.container.impl.Shop.ShopManager;
import com.ruthlessps.model.input.impl.BuyShards;
import com.ruthlessps.model.input.impl.ChangePassword;
import com.ruthlessps.model.input.impl.DonateToDropWell;
import com.ruthlessps.model.input.impl.DonateToPointsWell;
import com.ruthlessps.model.input.impl.DonateToWell;
import com.ruthlessps.model.input.impl.SellShards;
import com.ruthlessps.model.input.impl.SetEmail;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.World;
import com.ruthlessps.world.content.Achievements;
import com.ruthlessps.world.content.Achievements.AchievementData;
import com.ruthlessps.world.content.Artifacts;
import com.ruthlessps.world.content.BankPin;
import com.ruthlessps.world.content.CustomObjects;
import com.ruthlessps.world.content.DonorTickets;
import com.ruthlessps.world.content.DoubleDropWell;
import com.ruthlessps.world.content.Gambling.FlowersData;
import com.ruthlessps.world.content.Lottery;
import com.ruthlessps.world.content.LoyaltyProgram;
import com.ruthlessps.world.content.PkSets;
import com.ruthlessps.world.content.PlayerPanel;
import com.ruthlessps.world.content.PointsWell;
import com.ruthlessps.world.content.Scoreboards;
import com.ruthlessps.world.content.WellOfGoodwill;
import com.ruthlessps.world.content.clan.ClanChatManager;
import com.ruthlessps.world.content.dialogue.impl.AgilityTicketExchange;
import com.ruthlessps.world.content.dialogue.impl.Mandrith;
import com.ruthlessps.world.content.minigames.impl.Graveyard;
import com.ruthlessps.world.content.minigames.impl.Nomad;
import com.ruthlessps.world.content.minigames.impl.RecipeForDisaster;
import com.ruthlessps.world.content.skill.impl.dungeoneering.Dungeoneering;
import com.ruthlessps.world.content.skill.impl.dungeoneering.DungeoneeringFloor;
import com.ruthlessps.world.content.skill.impl.mining.Mining;
import com.ruthlessps.world.content.skill.impl.slayer.Slayer;
import com.ruthlessps.world.content.skill.impl.slayer.SlayerDialogues;
import com.ruthlessps.world.content.skill.impl.slayer.SlayerMaster;
import com.ruthlessps.world.content.skill.impl.slayer.SlayerTasks;
import com.ruthlessps.world.content.skill.impl.summoning.CharmingImp;
import com.ruthlessps.world.content.skill.impl.summoning.SummoningTab;
import com.ruthlessps.world.content.teleportation.JewelryTeleporting;
import com.ruthlessps.world.content.teleportation.TeleportHandler;
import com.ruthlessps.world.content.teleportation.TeleportType;
import com.ruthlessps.world.entity.impl.player.Player;

//import mysql.impl.Store;

public class DialogueOptions {

	// Last id used = 78

	public static int FIRST_OPTION_OF_FIVE = 2494;
	public static int SECOND_OPTION_OF_FIVE = 2495;
	public static int THIRD_OPTION_OF_FIVE = 2496;
	public static int FOURTH_OPTION_OF_FIVE = 2497;
	public static int FIFTH_OPTION_OF_FIVE = 2498;

	public static int FIRST_OPTION_OF_FOUR = 2482;
	public static int SECOND_OPTION_OF_FOUR = 2483;
	public static int THIRD_OPTION_OF_FOUR = 2484;
	public static int FOURTH_OPTION_OF_FOUR = 2485;

	public static int FIRST_OPTION_OF_THREE = 2471;
	public static int SECOND_OPTION_OF_THREE = 2472;
	public static int THIRD_OPTION_OF_THREE = 2473;

	public static int FIRST_OPTION_OF_TWO = 2461;
	public static int SECOND_OPTION_OF_TWO = 2462;

	public static void handle(Player player, int id) {
		if (player.getRights() == PlayerRights.DEVELOPER) {
			player.getPacketSender()
					.sendMessage("Dialogue button id: " + id + ", action id: " + player.getDialogueActionId())
					.sendConsoleMessage("Dialogue button id: " + id + ", action id: " + player.getDialogueActionId());
		}
		if (id == FIRST_OPTION_OF_FIVE) {
			switch (player.getDialogueActionId()) {
			case 0:
				TeleportHandler.teleportPlayer(player, new Position(2695, 3714),
						player.getSpellbook().getTeleportType());
				break;
			case 1:
				TeleportHandler.teleportPlayer(player, new Position(3420, 3510),
						player.getSpellbook().getTeleportType());
				break;
			case 2:
				TeleportHandler.teleportPlayer(player, new Position(3235, 3295),
						player.getSpellbook().getTeleportType());
				break;
			case 9:
				DialogueManager.start(player, 16);
				break;
			case 10:
				Artifacts.sellArtifacts(player);
				break;
			case 11:
				Scoreboards.open(player, Scoreboards.TOP_KILLSTREAKS);
				break;
			case 12:
				TeleportHandler.teleportPlayer(player, new Position(3087, 3517),
						player.getSpellbook().getTeleportType());
				break;
			case 13:
				player.setDialogueActionId(78);
				DialogueManager.start(player, 124);
				break;
			case 14:
				TeleportHandler.teleportPlayer(player, new Position(3097 + Misc.getRandom(1), 9869 + Misc.getRandom(1)),
						player.getSpellbook().getTeleportType());
				break;
			case 15000:
				player.setNpcTransformationId(-1);
				player.getUpdateFlag().flag(Flag.APPEARANCE);
				player.getPacketSender().sendInterfaceRemoval();
				player.sendMessage("<col=ff0000>You transformed into a Normal person.");
				break;
			case 15001:
				player.setNpcTransformationId(511);
				player.getUpdateFlag().flag(Flag.APPEARANCE);
				player.getPacketSender().sendInterfaceRemoval();
				player.sendMessage("<col=ff0000>You transformed into the " + GameSettings.SERVER_NAME + " Point Store.");
				break;
			case 15002:
				player.setNpcTransformationId(605);
				player.getUpdateFlag().flag(Flag.APPEARANCE);
				player.getPacketSender().sendInterfaceRemoval();
				player.sendMessage("<col=ff0000>You transformed into a Spongebob.");
				break;
			case 15003:
				player.setNpcTransformationId(2579);
				player.getUpdateFlag().flag(Flag.APPEARANCE);
				player.getPacketSender().sendInterfaceRemoval();
				player.sendMessage("<col=ff0000>You transformed into a Sonic.");
				break;
			case 15:
				player.getPacketSender().sendInterfaceRemoval();
				int total = player.getSkillManager().getMaxLevel(Skill.ATTACK)
						+ player.getSkillManager().getMaxLevel(Skill.STRENGTH);
				boolean has99 = player.getSkillManager().getMaxLevel(Skill.ATTACK) >= 99
						|| player.getSkillManager().getMaxLevel(Skill.STRENGTH) >= 99;
				if (total < 130 && !has99) {
					player.getPacketSender().sendMessage("");
					player.getPacketSender().sendMessage("You do not meet the requirements of a Warrior.");
					player.getPacketSender()
							.sendMessage("You need to have a total Attack and Strength level of at least 140.");
					player.getPacketSender().sendMessage("Having level 99 in either is fine aswell.");
					return;
				}
				TeleportHandler.teleportPlayer(player, new Position(2855, 3543),
						player.getSpellbook().getTeleportType());
				break;
			case 17:
				player.setInputHandling(new SetEmail());
				player.getPacketSender().sendEnterInputPrompt("Enter your email address:");
				break;
			case 29:
				SlayerMaster.changeSlayerMaster(player, SlayerMaster.BEGINNER_MASTER);
				break;
			case 36:
				TeleportHandler.teleportPlayer(player, new Position(2871, 5318, 2),
						player.getSpellbook().getTeleportType());
				break;
			case 38:
				TeleportHandler.teleportPlayer(player, new Position(2273, 4681),
						player.getSpellbook().getTeleportType());
				break;
			case 40:
				TeleportHandler.teleportPlayer(player, new Position(3476, 9502),
						player.getSpellbook().getTeleportType());
				break;
			case 48:
				JewelryTeleporting.teleport(player, new Position(3088, 3506));
				break;
			case 59:
				if (player.getClickDelay().elapsed(1500)) {
					PkSets.buySet(player, PkSets.PURE_SET);
				}
				break;
			case 60:
				player.setDialogueActionId(61);
				DialogueManager.start(player, 102);
				break;
			case 67:
				player.getPacketSender().sendInterfaceRemoval();
				if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty() != null) {
					if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty().getOwner().getUsername()
							.equals(player.getUsername())) {
						player.getMinigameAttributes().getDungeoneeringAttributes().getParty()
								.setDungeoneeringFloor(DungeoneeringFloor.FIRST_FLOOR);
						player.getMinigameAttributes().getDungeoneeringAttributes().getParty()
								.sendMessage("The party leader has changed floor.");
						player.getMinigameAttributes().getDungeoneeringAttributes().getParty().refreshInterface();
					}
				}
				break;
			case 68:
				player.getPacketSender().sendInterfaceRemoval();
				if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty() != null) {
					if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty().getOwner().getUsername()
							.equals(player.getUsername())) {
						player.getMinigameAttributes().getDungeoneeringAttributes().getParty().setComplexity(1);
						player.getMinigameAttributes().getDungeoneeringAttributes().getParty()
								.sendMessage("The party leader has changed complexity.");
						player.getMinigameAttributes().getDungeoneeringAttributes().getParty().refreshInterface();
					}
				}
				break;
			}
		} else if (id == SECOND_OPTION_OF_FIVE) {
			switch (player.getDialogueActionId()) {
			case 0:
				TeleportHandler.teleportPlayer(player,
						new Position(3557 + (Misc.getRandom(2)), 9946 + Misc.getRandom(2)),
						player.getSpellbook().getTeleportType());
				break;
			case 1:
				TeleportHandler.teleportPlayer(player, new Position(2933, 9849),
						player.getSpellbook().getTeleportType());
				break;
			case 2:
				TeleportHandler.teleportPlayer(player, new Position(2802, 9148),
						player.getSpellbook().getTeleportType());
				break;
			case 9:
				Lottery.enterLottery(player);
				break;
			case 10:
				player.setDialogueActionId(59);
				DialogueManager.start(player, 100);
				break;
			case 11:
				Scoreboards.open(player, Scoreboards.TOP_PKERS);
				break;
			case 12:
				TeleportHandler.teleportPlayer(player, new Position(2980 + Misc.getRandom(3), 3596 + Misc.getRandom(3)),
						player.getSpellbook().getTeleportType());
				break;
			case 13:
				player.getPacketSender().sendInterfaceRemoval();
				for (AchievementData d : AchievementData.values()) {
					if (!player.getAchievementAttributes().getCompletion()[d.ordinal()]) {
						player.getPacketSender()
								.sendMessage("You must have completed all achievements in order to buy this cape.");
						return;
					}
				}
				boolean usePouch = player.getMoneyInPouch() >= 100000000;
				if (!usePouch && player.getInventory().getAmount(995) < 100000000) {
					player.getPacketSender().sendMessage("You do not have enough coins.");
					return;
				}
				if (usePouch) {
					player.setMoneyInPouch(player.getMoneyInPouch() - 100000000);
					player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
				} else
					player.getInventory().delete(995, 100000000);
				player.getInventory().add(14022, 1);
				player.getPacketSender().sendMessage("You've purchased an Completionist cape.");
				break;
			case 14:
				TeleportHandler.teleportPlayer(player, new Position(3184 + Misc.getRandom(1), 5471 + Misc.getRandom(1)),
						player.getSpellbook().getTeleportType());
				break;
			case 15:
				TeleportHandler.teleportPlayer(player, new Position(2663 + Misc.getRandom(1), 2651 + Misc.getRandom(1)),
						player.getSpellbook().getTeleportType());
				break;
			case 17:
				if (player.getBankPinAttributes().hasBankPin()) {
					DialogueManager.start(player, 12);
					player.setDialogueActionId(8);
				} else {
					BankPin.init(player, false);
				}
				break;
			case 29:
				SlayerMaster.changeSlayerMaster(player, SlayerMaster.INTERMEDIATE_MASTER);
				break;
			case 36:
				TeleportHandler.teleportPlayer(player, new Position(1908, 4367),
						player.getSpellbook().getTeleportType());
				break;
			case 38:
				DialogueManager.start(player, 70);
				player.setDialogueActionId(39);
				break;
			case 40:
				TeleportHandler.teleportPlayer(player, new Position(2839, 9557),
						player.getSpellbook().getTeleportType());
				break;
			case 48:
				JewelryTeleporting.teleport(player, new Position(3213, 3423));
				break;
			case 59:
				if (player.getClickDelay().elapsed(1500)) {
					PkSets.buySet(player, PkSets.MELEE_MAIN_SET);
				}
				break;
			case 60:
				player.setDialogueActionId(62);
				DialogueManager.start(player, 102);
				break;
			case 15000:
				player.setNpcTransformationId(508);
				player.getUpdateFlag().flag(Flag.APPEARANCE);
				player.getPacketSender().sendInterfaceRemoval();
				player.sendMessage("<col=ff0000>You transformed into a Mewtwo.");
				break;
			case 15001:
				player.setNpcTransformationId(515);
				player.getUpdateFlag().flag(Flag.APPEARANCE);
				player.getPacketSender().sendInterfaceRemoval();
				player.sendMessage("<col=ff0000>You transformed into a Camouflague Torva Boss.");
				break;
			case 15002:
				player.setNpcTransformationId(519);
				player.getUpdateFlag().flag(Flag.APPEARANCE);
				player.getPacketSender().sendInterfaceRemoval();
				player.sendMessage("<col=ff0000>You transformed into a Squirtle.");
				break;
			case 15003:
				player.setNpcTransformationId(2676);
				player.getUpdateFlag().flag(Flag.APPEARANCE);
				player.getPacketSender().sendInterfaceRemoval();
				player.sendMessage("<col=ff0000>You transformed into a Homer.");
				break;
			case 68:
				player.getPacketSender().sendInterfaceRemoval();
				if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty() != null) {
					if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty().getOwner().getUsername()
							.equals(player.getUsername())) {
						player.getMinigameAttributes().getDungeoneeringAttributes().getParty().setComplexity(2);
						player.getMinigameAttributes().getDungeoneeringAttributes().getParty()
								.sendMessage("The party leader has changed complexity.");
						player.getMinigameAttributes().getDungeoneeringAttributes().getParty().refreshInterface();
					}
				}
				break;
			}
		} else if (id == THIRD_OPTION_OF_FIVE) {
			switch (player.getDialogueActionId()) {
			case 0:
				TeleportHandler.teleportPlayer(player,
						new Position(3204 + (Misc.getRandom(2)), 3263 + Misc.getRandom(2)),
						player.getSpellbook().getTeleportType());
				break;
			case 1:
				TeleportHandler.teleportPlayer(player, new Position(2480, 5175),
						player.getSpellbook().getTeleportType());
				break;
			case 2:
				TeleportHandler.teleportPlayer(player, new Position(2793, 2773),
						player.getSpellbook().getTeleportType());
				break;
			case 9:
				DialogueManager.start(player, Lottery.Dialogues.getCurrentPot(player));
				break;
			case 10:
				DialogueManager.start(player, Mandrith.getDialogue(player));
				break;
			case 11:
				Scoreboards.open(player, Scoreboards.TOP_TOTAL_EXP);
				break;
			case 12:
				TeleportHandler.teleportPlayer(player, new Position(3239 + Misc.getRandom(2), 3619 + Misc.getRandom(2)),
						player.getSpellbook().getTeleportType());
				break;
			case 15000:
				player.setNpcTransformationId(509);
				player.getUpdateFlag().flag(Flag.APPEARANCE);
				player.getPacketSender().sendInterfaceRemoval();
				player.sendMessage("<col=ff0000>You transformed into a Charmeleon.");
				break;
			case 15001:
				player.setNpcTransformationId(517);
				player.getUpdateFlag().flag(Flag.APPEARANCE);
				player.getPacketSender().sendInterfaceRemoval();
				player.sendMessage("<col=ff0000>You transformed into a Winter Camo Torva Boss.");
				break;
			case 15002:
				player.setNpcTransformationId(3101);
				player.getUpdateFlag().flag(Flag.APPEARANCE);
				player.getPacketSender().sendInterfaceRemoval();
				player.sendMessage("<col=ff0000>You transformed into a Pikachu.");
				break;
			case 15003:
				player.setNpcTransformationId(6139);
				player.getUpdateFlag().flag(Flag.APPEARANCE);
				player.getPacketSender().sendInterfaceRemoval();
				player.sendMessage("<col=ff0000>You transformed into a Lucario.");
				break;
			case 13:
				player.getPacketSender().sendInterfaceRemoval();
				if (!player.getUnlockedLoyaltyTitles()[LoyaltyProgram.LoyaltyTitles.VETERAN.ordinal()]) {
					player.getPacketSender().sendMessage(
							"You must have unlocked the 'Veteran' Loyalty Title in order to buy this cape.");
					return;
				}
				boolean usePouch = player.getMoneyInPouch() >= 75000000;
				if (!usePouch && player.getInventory().getAmount(995) < 75000000) {
					player.getPacketSender().sendMessage("You do not have enough coins.");
					return;
				}
				if (usePouch) {
					player.setMoneyInPouch(player.getMoneyInPouch() - 75000000);
					player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
				} else
					player.getInventory().delete(995, 75000000);
				player.getInventory().add(14021, 1);
				player.getPacketSender().sendMessage("You've purchased a Veteran cape.");
				DialogueManager.start(player, 122);
				player.setDialogueActionId(76);
				break;
			case 14:
				TeleportHandler.teleportPlayer(player, new Position(2713, 9564),
						player.getSpellbook().getTeleportType());
				break;
			case 15:
				TeleportHandler.teleportPlayer(player, new Position(3368 + Misc.getRandom(5), 3267 + Misc.getRandom(3)),
						player.getSpellbook().getTeleportType());
				break;
			case 17:
				player.getPacketSender().sendInterfaceRemoval();
				if (player.getBankPinAttributes().hasBankPin()) {
					player.getPacketSender()
							.sendMessage("Please disable your bank pin before doing this!");
					return;
				}
				if (player.getSummoning().getFamiliar() != null) {
					player.getPacketSender().sendMessage("Please dismiss your familiar first.");
					return;
				}
				/*
				 * if (player.getGameMode() == GameMode.NORMAL) { DialogueManager.start(player,
				 * 83); } else {
				 */
				player.setDialogueActionId(46);
				DialogueManager.start(player, 84);
				//}
				break;
			case 29:
				SlayerMaster.changeSlayerMaster(player, SlayerMaster.HEROIC_MASTER);
				break;
			case 36:
				player.setDialogueActionId(37);
				DialogueManager.start(player, 70);
				break;
			case 38:
				TeleportHandler.teleportPlayer(player, new Position(2547, 9448),
						player.getSpellbook().getTeleportType());
				break;
			case 40:
				TeleportHandler.teleportPlayer(player, new Position(2891, 4767),
						player.getSpellbook().getTeleportType());
				break;
			case 48:
				JewelryTeleporting.teleport(player, new Position(3368, 3267));
				break;
			case 59:
				if (player.getClickDelay().elapsed(1500)) {
					PkSets.buySet(player, PkSets.RANGE_MAIN_SET);
				}
				break;
			case 60:
				player.setDialogueActionId(63);
				DialogueManager.start(player, 102);
				break;
			case 68:
				player.getPacketSender().sendInterfaceRemoval();
				if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty() != null) {
					if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty().getOwner().getUsername()
							.equals(player.getUsername())) {
						player.getMinigameAttributes().getDungeoneeringAttributes().getParty().setComplexity(3);
						player.getMinigameAttributes().getDungeoneeringAttributes().getParty()
								.sendMessage("The party leader has changed complexity.");
						player.getMinigameAttributes().getDungeoneeringAttributes().getParty().refreshInterface();
					}
				}
				break;
			}
		} else if (id == FOURTH_OPTION_OF_FIVE) {
			switch (player.getDialogueActionId()) {
			case 0:
				TeleportHandler.teleportPlayer(player,
						new Position(3173 - (Misc.getRandom(2)), 2981 + Misc.getRandom(2)),
						player.getSpellbook().getTeleportType());
				break;
			case 1:
				TeleportHandler.teleportPlayer(player, new Position(3279, 2964),
						player.getSpellbook().getTeleportType());
				break;
			case 2:
				TeleportHandler.teleportPlayer(player, new Position(3085, 9672),
						player.getSpellbook().getTeleportType());
				break;
			case 9:
				DialogueManager.start(player, Lottery.Dialogues.getLastWinner(player));
				break;
			case 15000:
				player.setNpcTransformationId(510);
				player.getUpdateFlag().flag(Flag.APPEARANCE);
				player.getPacketSender().sendInterfaceRemoval();
				player.sendMessage("<col=ff0000>You transformed into a Luigi.");
				break;
			case 15001:
				player.setNpcTransformationId(518);
				player.getUpdateFlag().flag(Flag.APPEARANCE);
				player.getPacketSender().sendInterfaceRemoval();
				player.sendMessage("<col=ff0000>You transformed into a Bloodshot Camo Torva Boss.");
				break;
			case 15002:
				player.setNpcTransformationId(4657);
				player.getUpdateFlag().flag(Flag.APPEARANCE);
				player.getPacketSender().sendInterfaceRemoval();
				player.sendMessage("<col=ff0000>You transformed into a Mr. Krabs.");
				break;
			case 15003:
				player.setDialogueActionId(-1);
				DialogueManager.start(player, -1);
				break;
			case 10:
				ShopManager.getShops().get(26).open(player);
				break;
			case 11:
				Scoreboards.open(player, Scoreboards.TOP_ACHIEVER);
				break;
			case 12:
				TeleportHandler.teleportPlayer(player,
						new Position(3329 + Misc.getRandom(2), 3660 + Misc.getRandom(2), 0),
						player.getSpellbook().getTeleportType());
				break;
			case 13:
				player.getPacketSender().sendInterfaceRemoval();
				if (!player.getUnlockedLoyaltyTitles()[LoyaltyProgram.LoyaltyTitles.MAXED.ordinal()]) {
					player.getPacketSender()
							.sendMessage("You must have unlocked the 'Maxed' Loyalty Title in order to buy this cape.");
					return;
				}
				boolean usePouch = player.getMoneyInPouch() >= 50000000;
				if (!usePouch && player.getInventory().getAmount(995) < 50000000) {
					player.getPacketSender().sendMessage("You do not have enough coins.");
					return;
				}
				if (usePouch) {
					player.setMoneyInPouch(player.getMoneyInPouch() - 50000000);
					player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
				} else
					player.getInventory().delete(995, 50000000);
				player.getInventory().add(14019, 1);
				player.getPacketSender().sendMessage("You've purchased a Max cape.");
				break;
			case 14:
				TeleportHandler.teleportPlayer(player, new Position(2884, 9797),
						player.getSpellbook().getTeleportType());
				break;
			case 15:
				TeleportHandler.teleportPlayer(player, new Position(3565, 3313),
						player.getSpellbook().getTeleportType());
				break;
			case 17:
				player.setInputHandling(new ChangePassword());
				player.getPacketSender().sendEnterInputPrompt("Enter a new password:");
				break;
			case 29:
				SlayerMaster.changeSlayerMaster(player, SlayerMaster.ELITE_MASTER);
				break;
			case 36:
				TeleportHandler.teleportPlayer(player, new Position(2717, 9805),
						player.getSpellbook().getTeleportType());
				break;
			case 38:
				TeleportHandler.teleportPlayer(player, new Position(1891, 3177),
						player.getSpellbook().getTeleportType());
				break;
			case 40:
				TeleportHandler.teleportPlayer(player, new Position(3050, 9573),
						player.getSpellbook().getTeleportType());
				break;
			case 48:
				JewelryTeleporting.teleport(player, new Position(2447, 5169));
				break;
			case 59:
				if (player.getClickDelay().elapsed(1500)) {
					PkSets.buySet(player, PkSets.MAGIC_MAIN_SET);
				}
				break;
			case 60:
				player.setDialogueActionId(64);
				DialogueManager.start(player, 102);
				break;
			case 68:
				player.getPacketSender().sendInterfaceRemoval();
				if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty() != null) {
					if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty().getOwner().getUsername()
							.equals(player.getUsername())) {
						player.getMinigameAttributes().getDungeoneeringAttributes().getParty().setComplexity(4);
						player.getMinigameAttributes().getDungeoneeringAttributes().getParty()
								.sendMessage("The party leader has changed complexity.");
						player.getMinigameAttributes().getDungeoneeringAttributes().getParty().refreshInterface();
					}
				}
				break;
			}
		} else if (id == FIFTH_OPTION_OF_FIVE) {
			switch (player.getDialogueActionId()) {
			case 0:
				player.setDialogueActionId(1);
				DialogueManager.next(player);
				break;
			case 1:
				player.setDialogueActionId(2);
				DialogueManager.next(player);
				break;
			case 2:
				player.setDialogueActionId(0);
				DialogueManager.start(player, 0);
				break;
			case 15000:
				player.setDialogueActionId(15001);
				DialogueManager.start(player, 15001);
				break;
			case 15001:
				player.setDialogueActionId(15002);
				DialogueManager.start(player, 15002);
				break;
			case 15002:
				player.setDialogueActionId(15003);
				DialogueManager.start(player, 15003);
				break;
			case 15003:
				player.setDialogueActionId(15000);
				DialogueManager.start(player, 15000);
				break;
			case 9:
			case 10:
			case 11:
			case 13:
			case 17:
			case 48:
			case 60:
			case 67:
			case 68:
				player.getPacketSender().sendInterfaceRemoval();
				break;
			case 29:
				SlayerMaster.changeSlayerMaster(player, SlayerMaster.GOD_MASTER);
				break;
			case 12:
				int random = Misc.getRandom(4);
				switch (random) {
				case 0:
					TeleportHandler.teleportPlayer(player, new Position(3035, 3701, 0),
							player.getSpellbook().getTeleportType());
					break;
				case 1:
					TeleportHandler.teleportPlayer(player, new Position(3036, 3694, 0),
							player.getSpellbook().getTeleportType());
					break;
				case 2:
					TeleportHandler.teleportPlayer(player, new Position(3045, 3697, 0),
							player.getSpellbook().getTeleportType());
					break;
				case 3:
					TeleportHandler.teleportPlayer(player, new Position(3043, 3691, 0),
							player.getSpellbook().getTeleportType());
					break;
				case 4:
					TeleportHandler.teleportPlayer(player, new Position(3037, 3687, 0),
							player.getSpellbook().getTeleportType());
					break;
				}
				break;
			case 14:
				DialogueManager.start(player, 23);
				player.setDialogueActionId(14);
				break;
			case 15:
				DialogueManager.start(player, 32);
				player.setDialogueActionId(18);
				break;
			case 36:
				DialogueManager.start(player, 66);
				player.setDialogueActionId(38);
				break;
			case 38:
				DialogueManager.start(player, 68);
				player.setDialogueActionId(40);
				break;
			case 40:
				DialogueManager.start(player, 69);
				player.setDialogueActionId(41);
				break;
			case 59:
				if (player.getClickDelay().elapsed(1500)) {
					PkSets.buySet(player, PkSets.HYBRIDING_MAIN_SET);
				}
				break;
			}
		} else if (id == FIRST_OPTION_OF_FOUR) {
			switch (player.getDialogueActionId()) {
			/*case 5:
				player.getPacketSender().sendInterfaceRemoval();
				new Thread(new Store(player)).start();
				break;*/
			case 65:
				player.getPacketSender().sendInterfaceRemoval();
				if (player.getSlayer().getDuoPartner() != null) {
					player.getPacketSender().sendMessage("You already have a duo partner.");
					return;
				}
				player.getPacketSender()
						.sendMessage("To do Social slayer, simply use your Slayer gem on another player.");
				break;
			case 101:
				player.getPlayerOwnedShopManager().open();
				break;
			case 8:
				ShopManager.getShops().get(27).open(player);
				break;
			case 9:
				TeleportHandler.teleportPlayer(player, new Position(3184, 3434),
						player.getSpellbook().getTeleportType());
				break;
			case 14:
				TeleportHandler.teleportPlayer(player, new Position(2871, 5318, 2),
						player.getSpellbook().getTeleportType());
				break;
			case 18:
				TeleportHandler.teleportPlayer(player, new Position(2439 + Misc.getRandom(2), 5171 + Misc.getRandom(2)),
						player.getSpellbook().getTeleportType());
				break;
			case 26:
				TeleportHandler.teleportPlayer(player, new Position(2480, 3435),
						player.getSpellbook().getTeleportType());
				break;
			case 27:
				ClanChatManager.createClan(player);
				break;
			case 28:
				player.setDialogueActionId(29);
				DialogueManager.start(player, 62);
				break;
			case 30:
			case 90:
				if(player.getSlayer().getSlayerMaster() == SlayerMaster.GOD_MASTER && player.isGroupLeader()) {
					player.getGroupSlayer().assignGroupTask();
				} else {
					player.getSlayer().assignTask();
				}
				break;
			case 31:
				DialogueManager.start(player, SlayerDialogues.findAssignment(player));
				break;
			case 41:
				DialogueManager.start(player, 76);
				break;
			case 130:
				DialogueManager.start(player, 76);
				break;
			case 131:
				DialogueManager.start(player, 76);
				break;
			case 45:
				GameMode.set(player, GameMode.NORMAL);
				player.getPacketSender().sendInterfaceRemoval();
				break;
			}
		} else if (id == SECOND_OPTION_OF_FOUR) {
			switch (player.getDialogueActionId()) {
			case 101:
				player.getPlayerOwnedShopManager().openEditor();
				break;
			case 41:
				WellOfGoodwill.checkWell(player);
				break;
			case 130:
				DoubleDropWell.checkWell(player);
				break;
			case 131:
				PointsWell.checkWell(player);
				break;
			case 5:
				DialogueManager.start(player, DonorTickets.getTotalFunds(player));
				break;
			case 65:
				player.getPacketSender().sendInterfaceRemoval();
				if (player.getSlayer().getDuoPartner() != null) {
					Slayer.resetDuo(player, World.getPlayerByName(player.getSlayer().getDuoPartner()));
				}
				break;
			case 8:
				LoyaltyProgram.open(player);
				break;
			case 9:
				DialogueManager.start(player, 14);
				break;
			case 14:
				player.getPacketSender().sendInterfaceRemoval();
				if (player.getSkillManager().getCurrentLevel(Skill.SLAYER) < 50) {
					player.getPacketSender()
							.sendMessage("You need a Slayer level of at least 50 to visit this dungeon.");
					return;
				}
				TeleportHandler.teleportPlayer(player, new Position(2731, 5095),
						player.getSpellbook().getTeleportType());
				break;
			case 18:
				TeleportHandler.teleportPlayer(player, new Position(2399, 5177),
						player.getSpellbook().getTeleportType());
				break;
			case 26:
				player.getPacketSender().sendInterfaceRemoval();
				if (player.getSkillManager().getMaxLevel(Skill.AGILITY) < 35) {
					player.getPacketSender()
							.sendMessage("You need an Agility level of at least level 35 to use this course.");
					return;
				}
				TeleportHandler.teleportPlayer(player, new Position(2552, 3556),
						player.getSpellbook().getTeleportType());
				break;
			case 27:
				ClanChatManager.clanChatSetupInterface(player, true);
				break;
			case 28:
				if (player.getSlayer().getSlayerMaster().getPosition() != null) {
					TeleportHandler.teleportPlayer(player,
							new Position(player.getSlayer().getSlayerMaster().getPosition().getX(),
									player.getSlayer().getSlayerMaster().getPosition().getY(),
									player.getSlayer().getSlayerMaster().getPosition().getZ()),
							player.getSpellbook().getTeleportType());
					if (player.getSkillManager().getCurrentLevel(Skill.SLAYER) <= 50)
						player.getPacketSender().sendMessage("")
								.sendMessage("You can train Slayer with a friend by using a Slayer gem on them.")
								.sendMessage("Slayer gems can be bought from all Slayer masters.");
					;
				}
				break;
			case 45:
				GameMode.set(player, GameMode.IRONMAN);
				player.getPacketSender().sendInterfaceRemoval();
				break;
			case 30:
			case 90:
			case 31:
				if (player.getSlayer().getSlayerTask() == SlayerTasks.NO_TASK) {
					player.getPacketSender().sendInterfaceRemoval();
					ShopManager.getShops().get(40).open(player);
				} else {
					DialogueManager.start(player, SlayerDialogues.resetTaskDialogue(player));
				}
				break;
			}
		} else if (id == THIRD_OPTION_OF_FOUR) {
			switch (player.getDialogueActionId()) {
			case 5:
				player.getPacketSender().sendInterfaceRemoval();
				if (player.getRights() == PlayerRights.PLAYER) {
					player.getPacketSender().sendMessage("You need to be a member to teleport to this zone.")
							.sendMessage("To become a donator, visit Rivalps.com/store and purchase a scroll.");
					return;
				}
				TeleportHandler.teleportPlayer(player, new Position(2022, 4755),
						player.getSpellbook().getTeleportType());
				break;
			case 90:
				player.getPacketSender().sendString(36030,
						"Current Points:   " + player.getPointsManager().getPoints("slayer"));
				player.getPacketSender().sendInterface(36000);
				break;
			case 101:
				player.getPlayerOwnedShopManager().claimEarnings();
				break;
			case 65:
				player.getGroupSlayer().quitGroup();
				player.getPacketSender().sendInterfaceRemoval();
				break;
			case 8:
				LoyaltyProgram.reset(player);
				player.getPacketSender().sendInterfaceRemoval();
				break;
			case 9:
				ShopManager.getShops().get(41).open(player);
				break;
			case 14:
				TeleportHandler.teleportPlayer(player, new Position(1745, 5325),
						player.getSpellbook().getTeleportType());
				break;
			case 18:
				TeleportHandler.teleportPlayer(player, new Position(3503, 3562),
						player.getSpellbook().getTeleportType());
				break;
			case 26:
				player.getPacketSender().sendInterfaceRemoval();
				if (player.getSkillManager().getMaxLevel(Skill.AGILITY) < 55) {
					player.getPacketSender()
							.sendMessage("You need an Agility level of at least level 55 to use this course.");
					return;
				}
				TeleportHandler.teleportPlayer(player, new Position(2998, 3914),
						player.getSpellbook().getTeleportType());
				break;
			case 27:
				ClanChatManager.deleteClan(player);
				break;
			case 28:
				TeleportHandler.teleportPlayer(player, new Position(3249, 9490, 0),
						player.getSpellbook().getTeleportType());
				break;
			case 31:
				DialogueManager.start(player, SlayerDialogues.extendTask(player));
				break;
			case 41:
				player.setInputHandling(new DonateToWell());
				player.getPacketSender().sendInterfaceRemoval()
						.sendEnterAmountPrompt("How many 1b Bucks would you like to contribute?");
				break;
			case 130:
				player.setInputHandling(new DonateToDropWell());
				player.getPacketSender().sendInterfaceRemoval()
						.sendEnterAmountPrompt("How many 1b Bucks would you like to contribute?");
				break;
			case 131:
				player.setInputHandling(new DonateToPointsWell());
				player.getPacketSender().sendInterfaceRemoval()
						.sendEnterAmountPrompt("How many 1b Bucks would you like to contribute?");
				break;
			case 45:
				GameMode.set(player, GameMode.HARDCORE);
				player.getPacketSender().sendInterfaceRemoval();
				break;
			case 30:
				player.getPacketSender().sendInterfaceRemoval().sendInterface(36000);
				break;
			}
		} else if (id == FOURTH_OPTION_OF_FOUR) {
			switch (player.getDialogueActionId()) {
			case 5:
			case 8:
			case 9:
			case 26:
			case 27:
			case 41:
			case 130:
			case 101:
			case 65:
				player.getPacketSender().sendInterfaceRemoval();
				break;
			case 28:
				SlayerMaster.changeSlayerMaster(player, SlayerMaster.GOD_MASTER);
				break;
			case 14:
				player.setDialogueActionId(14);
				DialogueManager.start(player, 22);
				break;
			case 18:
				DialogueManager.start(player, 25);
				player.setDialogueActionId(15);
				break;
			case 30:
			case 31:
			case 90:
				player.getPacketSender().sendInterfaceRemoval();
				if (player.getSlayer().getDuoPartner() != null) {
					Slayer.resetDuo(player, World.getPlayerByName(player.getSlayer().getDuoPartner()));
				}
				break;
			case 45:
				player.getPacketSender().sendString(1,
						"wwww.rivalps.com/index.php?/topic/104-game-mode-guide/?p=420");
				break;
			}
		} else if (id == FIRST_OPTION_OF_TWO) {
			switch (player.getDialogueActionId()) {
			case 98:
				player.getSlayer().extendTask(player);
				break;
			case 420:
				player.getPacketSender().sendInterfaceRemoval().sendMessage("You empty your inventory.");
				player.getSkillManager().stopSkilling();
				player.getInventory().resetItems().refreshItems();
				player.getPacketSender().sendInterfaceRemoval();
				break;
			case 15005:
				TeleportHandler.teleportPlayer(player, new Position(2208,4958,0), player.getSpellbook().getTeleportType());
				Achievements.doProgress(player, AchievementData.FOUR_GODS);
				if(!player.getInventory().contains(13260)) {
					player.getDropKillCount().put(3003, 0);player.getDropKillCount().put(3020, 0);player.getDropKillCount().put(3160, 0);player.getDropKillCount().put(3120, 0);
				} else {
					player.getInventory().delete(13260, 1);
				}
					player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You travel to the Mighty Four Gods! Be sure to show some respect!");
				break;
			case 750:
				if(player.getInventory().contains(20259)) {
					if(player.getInventory().getAmount(20259) >= 500) {
						player.getInventory().delete(20259, 500);
						TeleportHandler.teleportPlayer(player, new Position(2948, 3147, 0),
								player.getSpellbook().getTeleportType());
						player.getPacketSender().sendMessage("@blu@The sailor teleports you to Karamja!");
					} else {
						player.getPacketSender().sendInterfaceRemoval();
						player.getPacketSender().sendMessage("@red@You do not have 500 luigi bones!");
					}
				} else {
					player.getPacketSender().sendInterfaceRemoval();
					player.getPacketSender().sendMessage("@red@You need to have 500 luigi bones!");
				}
				break;
			case 128:
				if (player.getTeleportType() != null) {
					TeleportHandler.teleportPlayer(player, player.getTeleportType().getPosition(),
							player.getSpellbook().getTeleportType());
					player.getPacketSender().sendMessage("You teleport to "
							+ Misc.ucFirst(player.getTeleportType().name().toLowerCase().replaceAll("_", " ")));
					player.setTeleportType(null);
				}
				break;
			case 3:
				ShopManager.getShops().get(22).open(player);
				break;
			case 4:
				SummoningTab.handleDismiss(player, true);
				break;
			case 7:
				BankPin.init(player, false);
				break;
			case 8:
				BankPin.deletePin(player);
				break;
			case 20:
				player.getPacketSender().sendInterfaceRemoval();
				DialogueManager.start(player, 39);
				player.getMinigameAttributes().getRecipeForDisasterAttributes().setPartFinished(0, true);
				PlayerPanel.refreshPanel(player);
				break;
			case 23:
				DialogueManager.start(player, 50);
				player.getMinigameAttributes().getNomadAttributes().setPartFinished(0, true);
				player.setDialogueActionId(24);
				PlayerPanel.refreshPanel(player);
				break;
			case 24:
				player.getPacketSender().sendInterfaceRemoval();
				break;
			case 33:
				player.getPacketSender().sendInterfaceRemoval();
				player.getSlayer().resetSlayerTask();
				break;
			case 34:
				player.getPacketSender().sendInterfaceRemoval();
				player.getSlayer().handleInvitation(true);
				break;
			case 87:
				player.getPacketSender().sendInterfaceRemoval();
				player.getGroupSlayer().groupInvitation(true);
				break;
			case 37:
				TeleportHandler.teleportPlayer(player, new Position(2961, 3882),
						player.getSpellbook().getTeleportType());
				break;
			case 39:
				TeleportHandler.teleportPlayer(player, new Position(3281, 3914),
						player.getSpellbook().getTeleportType());
				break;
			case 42:
				player.getPacketSender().sendInterfaceRemoval();
				if (player.getInteractingObject() != null && player.getInteractingObject().getDefinition() != null
						&& player.getInteractingObject().getDefinition().getName().equalsIgnoreCase("flowers")) {
					if (CustomObjects.objectExists(player.getInteractingObject().getPosition())) {
						player.getInventory().add(FlowersData.forObject(player.getInteractingObject().getId()).itemId,
								1);
						CustomObjects.deleteGlobalObject(player.getInteractingObject());
						player.setInteractingObject(null);
					}
				}
				break;
			case 46:
				player.getPacketSender().sendInterfaceRemoval();
				DialogueManager.start(player, 82);
				player.setPlayerLocked(true).setDialogueActionId(45);
				break;
			case 57:
				Graveyard.start(player);
				break;
			case 66:
				player.getPacketSender().sendInterfaceRemoval();
				if (player.getLocation() == Location.DUNGEONEERING
						&& player.getMinigameAttributes().getDungeoneeringAttributes().getParty() == null) {
					if (player.getMinigameAttributes().getDungeoneeringAttributes().getPartyInvitation() != null) {
						player.getMinigameAttributes().getDungeoneeringAttributes().getPartyInvitation().add(player);
					}
				}
				player.getMinigameAttributes().getDungeoneeringAttributes().setPartyInvitation(null);
				break;
			case 71:
				if (player.getClickDelay().elapsed(1000)) {
					if (Dungeoneering.doingDungeoneering(player)) {
						Dungeoneering.leave(player, false, true);
						player.getClickDelay().reset();
					}
				}
				break;
			case 72:
				if (player.getClickDelay().elapsed(1000)) {
					if (Dungeoneering.doingDungeoneering(player)) {
						Dungeoneering.leave(player, false, player.getMinigameAttributes().getDungeoneeringAttributes()
								.getParty().getOwner() == player ? false : true);
						player.getClickDelay().reset();
					}
				}
				break;
			case 73:
				player.getPacketSender().sendInterfaceRemoval();
				player.moveTo(new Position(3653, player.getPosition().getY()));
				break;
			case 74:
				player.getPacketSender().sendMessage("The ghost teleports you away.");
				player.getPacketSender().sendInterfaceRemoval();
				player.moveTo(new Position(3651, 3486));
				break;
			case 76:
				player.getPacketSender().sendInterfaceRemoval();
				if (player.getRights().isStaff()) {
					player.getPacketSender().sendMessage("You cannot change your rank.");
					return;
				}
				// player.setRights(PlayerRights.VETERAN);
				player.getPacketSender().sendRights();
				break;
			case 78:
				player.getPacketSender().sendString(38006, "Select a skill...").sendString(38090,
						"Which skill would you like to prestige?");
				player.getPacketSender().sendInterface(38000);
				player.setUsableObject(new Object[2]).setUsableObject(0, "prestige");
				break;
			}
		} else if (id == SECOND_OPTION_OF_TWO) {
			switch (player.getDialogueActionId()) {
			case 3:
				ShopManager.getShops().get(23).open(player);
				break;
			case 135:
			case 98:
				player.getSkillManager().stopSkilling();
				player.getPacketSender().sendInterfaceRemoval();
				break;
			case 4:
			case 16:
			case 20:
			case 23:
			case 33:
			case 37:
			case 39:
			case 42:
			case 44:
			case 46:
			case 57:
			case 71:
			case 72:
			case 73:
			case 74:
			case 76:
			case 78:
			case 128:
			case 420:
			case 750:
			case 15005:
				player.getPacketSender().sendInterfaceRemoval();
				break;
			case 7:
			case 8:
				player.getPacketSender().sendInterfaceRemoval();
				player.getBank(player.getCurrentBankTab()).open();
				break;
			case 24:
				Nomad.startFight(player);
				break;
			case 34:
				player.getPacketSender().sendInterfaceRemoval();
				player.getSlayer().handleInvitation(false);
				break;
			case 87:
				player.getPacketSender().sendInterfaceRemoval();
				player.getGroupSlayer().groupInvitation(false);
				break;
			case 66:
				player.getPacketSender().sendInterfaceRemoval();
				if (player.getMinigameAttributes().getDungeoneeringAttributes().getPartyInvitation() != null && player
						.getMinigameAttributes().getDungeoneeringAttributes().getPartyInvitation().getOwner() != null)
					player.getMinigameAttributes().getDungeoneeringAttributes().getPartyInvitation().getOwner()
							.getPacketSender()
							.sendMessage("" + player.getUsername() + " has declined your invitation.");
				player.getMinigameAttributes().getDungeoneeringAttributes().setPartyInvitation(null);
				break;
			}
		} else if (id == FIRST_OPTION_OF_THREE) {
			switch (player.getDialogueActionId()) {
			case 15:
				DialogueManager.start(player, 35);
				player.setDialogueActionId(19);
				break;
			case 19:
				DialogueManager.start(player, 33);
				player.setDialogueActionId(21);
				break;
			case 21:
				TeleportHandler.teleportPlayer(player, new Position(3080, 3498),
						player.getSpellbook().getTeleportType());
				break;
			case 22:
				TeleportHandler.teleportPlayer(player, new Position(1891, 3177),
						player.getSpellbook().getTeleportType());
				break;
			case 25:
				TeleportHandler.teleportPlayer(player, new Position(2589, 4319), TeleportType.PURO_PURO);
				break;
			case 35:
				player.getPacketSender()
						.sendEnterAmountPrompt("How many shards would you like to buy? (You can use K, M, B prefixes)");
				player.setInputHandling(new BuyShards());
				break;
			case 41:
				TeleportHandler.teleportPlayer(player, new Position(2884 + Misc.getRandom(1), 4374 + Misc.getRandom(1)),
						player.getSpellbook().getTeleportType());
				break;
			case 47:
				TeleportHandler.teleportPlayer(player, new Position(2911, 4832),
						player.getSpellbook().getTeleportType());
				break;
			case 48:
				if (player.getInteractingObject() != null) {
					Mining.startMining(player, new GameObject(24444, player.getInteractingObject().getPosition()));
				}
				player.getPacketSender().sendInterfaceRemoval();
				break;
			case 56:
				TeleportHandler.teleportPlayer(player, new Position(2717, 3499),
						player.getSpellbook().getTeleportType());
				break;
			case 58:
				DialogueManager.start(player, AgilityTicketExchange.getDialogue(player));
				break;
			case 61:
				CharmingImp.changeConfig(player, 0, 0);
				break;
			case 62:
				CharmingImp.changeConfig(player, 1, 0);
				break;
			case 63:
				CharmingImp.changeConfig(player, 2, 0);
				break;
			case 64:
				CharmingImp.changeConfig(player, 3, 0);
				break;
			case 69:
				ShopManager.getShops().get(44).open(player);
				player.getPacketSender().sendMessage("<col=660000>You currently have "
						+ player.getPointsManager().getPoints("dung") + " Dungeoneering tokens.");
				break;
			case 70:
			case 71:
				if (player.getInventory().contains(19670) && player.getClickDelay().elapsed(700)) {
					final int amt = player.getDialogueActionId() == 70 ? 1 : player.getInventory().getAmount(19670);
					player.getPacketSender().sendInterfaceRemoval();
					player.getInventory().delete(19670, amt);
					player.getPacketSender().sendMessage(
							"You claim the " + (amt > 1 ? "scrolls" : "scroll") + " and receive your reward.");
					int minutes = player.getGameMode() == GameMode.NORMAL ? 60 : 30;
					BonusExperienceTask.addBonusXp(player, minutes * amt);
					player.getClickDelay().reset();
				}
				break;
			}
		} else if (id == SECOND_OPTION_OF_THREE) {
			switch (player.getDialogueActionId()) {
			case 15:
				DialogueManager.start(player, 25);
				player.setDialogueActionId(15);
				break;
			case 21:
				RecipeForDisaster.openQuestLog(player);
				break;
			case 19:
				DialogueManager.start(player, 33);
				player.setDialogueActionId(22);
				break;
			case 22:
				Nomad.openQuestLog(player);
				break;
			case 25:
				player.getPacketSender().sendInterfaceRemoval();
				if (player.getSkillManager().getCurrentLevel(Skill.HUNTER) < 23) {
					player.getPacketSender()
							.sendMessage("You need a Hunter level of at least 23 to visit the hunting area.");
					return;
				}
				TeleportHandler.teleportPlayer(player, new Position(2922, 2885),
						player.getSpellbook().getTeleportType());
				break;
			case 35:
				player.getPacketSender().sendEnterAmountPrompt(
						"How many shards would you like to sell? (You can use K, M, B prefixes)");
				player.setInputHandling(new SellShards());
				break;
			case 41:
				TeleportHandler.teleportPlayer(player, new Position(2903, 5204),
						player.getSpellbook().getTeleportType());
				break;
			case 47:
				TeleportHandler.teleportPlayer(player, new Position(3023, 9740),
						player.getSpellbook().getTeleportType());
				break;
			case 48:
				if (player.getInteractingObject() != null) {
					Mining.startMining(player, new GameObject(24445, player.getInteractingObject().getPosition()));
				}
				player.getPacketSender().sendInterfaceRemoval();
				break;
			case 56:
				player.getPacketSender().sendInterfaceRemoval();
				if (player.getSkillManager().getCurrentLevel(Skill.WOODCUTTING) < 60) {
					player.getPacketSender()
							.sendMessage("You need a Woodcutting level of at least 60 to teleport there.");
					return;
				}
				TeleportHandler.teleportPlayer(player, new Position(2711, 3463),
						player.getSpellbook().getTeleportType());
				break;
			case 58:
				ShopManager.getShops().get(39).open(player);
				break;
			case 61:
				CharmingImp.changeConfig(player, 0, 1);
				break;
			case 62:
				CharmingImp.changeConfig(player, 1, 1);
				break;
			case 63:
				CharmingImp.changeConfig(player, 2, 1);
				break;
			case 64:
				CharmingImp.changeConfig(player, 3, 1);
				break;
			case 69:
				if (player.getClickDelay().elapsed(1000)) {
					Dungeoneering.start(player);
				}
				break;
			case 70:
			case 71:
				final boolean all = player.getDialogueActionId() == 71;
				player.getPacketSender().sendInterfaceRemoval();
				if (player.getInventory().getFreeSlots() == 0) {
					player.getPacketSender().sendMessage("You do not have enough free inventory space to do that.");
					return;
				}
				if (player.getInventory().contains(19670) && player.getClickDelay().elapsed(700)) {
					int amt = player.getInventory().getAmount(19670);

					player.getInventory().delete(19670, amt);
					int ant = 1;
					if(player.getGameMode() == GameMode.HARDCORE || player.getGameMode() == GameMode.IRONMAN) {
						ant = 2;
						if(player.getDonor() == DonorRights.DONOR) {
							ant = 3;
						} else if(player.getDonor() == DonorRights.DELUXE_DONOR) {
							ant = 4;
						} else if(player.getDonor() == DonorRights.SPONSOR) {
							ant = 5;
						} else if(player.getDonor() == DonorRights.SUPER_SPONSOR) {
							ant = 6;
						} else if(player.getDonor() == DonorRights.GOLDBAG) {
							ant = 6;
						}
					} else {
						if(player.getDonor() == DonorRights.DONOR) {
							ant = 2;
						} else if(player.getDonor() == DonorRights.DELUXE_DONOR) {
							ant = 3;
						} else if(player.getDonor() == DonorRights.SPONSOR) {
							ant = 4;
						} else if(player.getDonor() == DonorRights.SUPER_SPONSOR) {
							ant = 5;
						} else if(player.getDonor() == DonorRights.GOLDBAG) {
							ant = 5;
						}
					}
					player.getPointsManager().increasePoints("voting", ant*amt);
					player.getPointsManager().refreshPanel();
					if (player.getGameMode() == GameMode.NORMAL) {
						player.getInventory().add(13664, amt);
					} else {
						player.getInventory().add(13664, amt);
					}
					player.getPacketSender().sendMessage(
							"You claim the " + (amt > 1 ? "scrolls" : "scroll") + " and receive your reward.");
					player.getClickDelay().reset();
				}
				break;
			}
		} else if (id == THIRD_OPTION_OF_THREE) {
			switch (player.getDialogueActionId()) {
			case 5:
			case 10:
			case 15:
			case 19:
			case 21:
			case 22:
			case 25:
			case 35:
			case 47:
			case 48:
			case 56:
			case 58:
			case 61:
			case 62:
			case 63:
			case 64:
			case 69:
			case 70:
			case 71:
			case 77:
				player.getPacketSender().sendInterfaceRemoval();
				break;
			case 41:
				player.setDialogueActionId(36);
				DialogueManager.start(player, 65);
				break;
			}
		}
	}

}
