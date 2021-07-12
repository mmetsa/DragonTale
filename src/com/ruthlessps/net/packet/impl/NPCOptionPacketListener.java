package com.ruthlessps.net.packet.impl;

import com.ruthlessps.GameSettings;
import com.ruthlessps.engine.task.impl.QuestAssigner;
import com.ruthlessps.engine.task.impl.WalkToTask;
import com.ruthlessps.engine.task.impl.WalkToTask.FinalizedMovementTask;
import com.ruthlessps.model.Graphic;
import com.ruthlessps.model.Locations.Location;
import com.ruthlessps.model.PlayerRanks.DonorRights;
import com.ruthlessps.model.PlayerRanks.PlayerRights;
import com.ruthlessps.model.Position;
import com.ruthlessps.model.Skill;
import com.ruthlessps.model.container.impl.Shop.ShopManager;
import com.ruthlessps.model.definitions.NpcDefinition;
import com.ruthlessps.net.packet.Packet;
import com.ruthlessps.net.packet.PacketListener;
import com.ruthlessps.world.World;
import com.ruthlessps.world.content.BankPin;
import com.ruthlessps.world.content.EnergyHandler;
import com.ruthlessps.world.content.LoyaltyProgram;
import com.ruthlessps.world.content.combat.CombatFactory;
import com.ruthlessps.world.content.combat.magic.CombatSpell;
import com.ruthlessps.world.content.combat.magic.CombatSpells;
import com.ruthlessps.world.content.combat.weapon.CombatSpecial;
import com.ruthlessps.world.content.dialogue.DialogueManager;
import com.ruthlessps.world.content.skill.impl.crafting.Tanning;
import com.ruthlessps.world.content.skill.impl.dungeoneering.Dungeoneering;
import com.ruthlessps.world.content.skill.impl.fishing.Fishing;
import com.ruthlessps.world.content.skill.impl.hunter.PuroPuro;
import com.ruthlessps.world.content.skill.impl.runecrafting.DesoSpan;
import com.ruthlessps.world.content.skill.impl.slayer.SlayerDialogues;
import com.ruthlessps.world.content.skill.impl.slayer.SlayerMaster;
import com.ruthlessps.world.content.skill.impl.slayer.SlayerTasks;
import com.ruthlessps.world.content.skill.impl.summoning.BossPets;
import com.ruthlessps.world.content.skill.impl.summoning.Summoning;
import com.ruthlessps.world.content.skill.impl.summoning.SummoningData;
import com.ruthlessps.world.content.teleportation.TeleportHandler;
import com.ruthlessps.world.entity.impl.npc.NPC;
import com.ruthlessps.world.entity.impl.npc.Pet;
import com.ruthlessps.world.entity.impl.player.Player;

public class NPCOptionPacketListener implements PacketListener {

	public static final int ATTACK_NPC = 72, FIRST_CLICK_OPCODE = 155, MAGE_NPC = 131, SECOND_CLICK_OPCODE = 17,
			THIRD_CLICK_OPCODE = 21, FOURTH_CLICK_OPCODE = 18;

	@Override
	public void handleMessage(Player player, Packet packet) {
		if (player.isTeleporting() || player.isPlayerLocked() || player.getMovementQueue().isLockMovement())
			return;
		switch (packet.getOpcode()) {
		case ATTACK_NPC:
			attackNPC(player, packet);
			break;
		case FIRST_CLICK_OPCODE:
			firstClick(player, packet);
			break;
		case SECOND_CLICK_OPCODE:
			handleSecondClick(player, packet);
			break;
		case THIRD_CLICK_OPCODE:
			handleThirdClick(player, packet);
			break;
		case FOURTH_CLICK_OPCODE:
			handleFourthClick(player, packet);
			break;
		case MAGE_NPC:
			int npcIndex = packet.readLEShortA();
			int spellId = packet.readShortA();

			if (npcIndex < 0 || spellId < 0 || npcIndex > World.getNpcs().capacity()) {
				return;
			}

			NPC n = World.getNpcs().get(npcIndex);
			player.setEntityInteraction(n);

			CombatSpell spell = CombatSpells.getSpell(spellId);

			if (n == null || spell == null) {
				player.getMovementQueue().reset();
				return;
			}

			if (!NpcDefinition.getDefinitions()[n.getId()].isAttackable()) {
				player.getMovementQueue().reset();
				return;
			}

			if (n.getConstitution() <= 0) {
				player.getMovementQueue().reset();
				return;
			}

			player.setPositionToFace(n.getPosition());
			player.setCastSpell(spell);
			if (player.getCombatBuilder().getStrategy() == null) {
				player.getCombatBuilder().determineStrategy();
			}
			if (CombatFactory.checkAttackDistance(player, n)) {
				player.getMovementQueue().reset();
			}
			player.getCombatBuilder().resetCooldown();
			player.getCombatBuilder().attack(n);
			break;
		}
	}

	private static void attackNPC(Player player, Packet packet) {
		int index = packet.readShortA();
		if (index < 0 || index > World.getNpcs().capacity())
			return;
		final NPC interact = World.getNpcs().get(index);
		if (interact == null)
			return;
		if (player.getRights().isDeveloper())
			player.sendMessage("NPC ID: " + interact.getId());

		if (!NpcDefinition.getDefinitions()[interact.getId()].isAttackable()) {
			return;
		}

		if (interact.getConstitution() <= 0) {
			player.getMovementQueue().reset();
			return;
		}
		if(interact.getDefinition().getIsSuperior() && !player.getSuperior()) {
			player.getPacketSender().sendMessage("This is not your Superior monster!");
			player.getMovementQueue().reset();
			return;
		}

		if (player.getCombatBuilder().getStrategy() == null) {
			player.getCombatBuilder().determineStrategy();
		}
		if (CombatFactory.checkAttackDistance(player, interact)) {
			player.getMovementQueue().reset();
		}

		player.getCombatBuilder().attack(interact);
	}

	private static void firstClick(Player player, Packet packet) {
		int index = packet.readLEShort();
		if (index < 0 || index > World.getNpcs().capacity())
			return;
		final NPC npc = World.getNpcs().get(index);
		if (npc == null)
			return;
		player.setEntityInteraction(npc);
		if (player.getRights() == PlayerRights.DEVELOPER) {
			player.getPacketSender().sendMessage("First click npc id: " + npc.getId());
		}

		player.setWalkToTask(new WalkToTask(player, npc.getPosition(), npc.getSize(), new FinalizedMovementTask() {
			@Override
			public void execute() {
				if (SummoningData.beastOfBurden(npc.getId())) {
					Summoning summoning = player.getSummoning();
					if (summoning.getBeastOfBurden() != null && summoning.getFamiliar() != null
							&& summoning.getFamiliar().getSummonNpc() != null
							&& summoning.getFamiliar().getSummonNpc().getIndex() == npc.getIndex()) {
						summoning.store();
						player.getMovementQueue().reset();
					} else {
						player.getPacketSender().sendMessage("That familiar is not yours!");
					}
					return;
				}
				switch (npc.getId()) {
				case 704:
					ShopManager.getShops().get(50).open(player);
					player.getPacketSender()
					.sendMessage("<img=10><col=FF0000><shad=0>You currently have @bla@"
							+ player.getPointsManager().getPoints("donation") + " <col=FF0000><shad=0>donation points.")
					.sendMessage("<img=10><col=5500FF><shad=0>You can buy donation points by doing :: donate.");
			player.getPacketSender().sendMessage("<img=10><col=BF0000><shad=0> Make sure to examine items inside shop, to see more info!");
			break;
				case 3340:
					if(player.getSlayer().getSlayerTask().getNpcId() != 3340) {
						player.getPacketSender().sendMessage("@red@This is not your slayer task! Go get a new one!");
						player.getCombatBuilder().cooldown(true);
					}
					break;
				case 538:
					if(player.getAttributes().getPet().getId() == 538) {
						player.openBank();
					}
					break;
				case 3706:
					if(player.getSlayer().getSlayerTask().getNpcId() != 3706) {
						player.getPacketSender().sendMessage("@red@This is not your slayer task! Go get a new one!");
						player.getCombatBuilder().cooldown(true);
					}
					break;
				case 200:
					QuestAssigner.handleBob(player, player.getQuestType());
					break;
				case 947:
					player.getPlayerOwnedShopManager().options();
					break;
				case 705:
					ShopManager.getShops().get(4).open(player);
					break;
				case 920:
					ShopManager.getShops().get(68).open(player);
					player.getPacketSender().sendMessage("@blu@You currently have @bla@"
							+ player.getPointsManager().getPoints("prestige") + " @blu@Prestige points!");
					player.getPacketSender().sendMessage("<img=10><col=BF0000><shad=0> Make sure to examine items inside shop, to see more info!");
					break;
				case 6113:
					ShopManager.getShops().get(76).open(player);
					break;
				case 1862:
					ShopManager.getShops().get(77).open(player);
					break;
				case 510:
					ShopManager.getShops().get(78).open(player);
					break;
				case 4662:
					ShopManager.getShops().get(79).open(player);
					break;
				case 815:
					ShopManager.getShops().get(49).open(player);
					break;
				case 816:
					ShopManager.getShops().get(51).open(player);
					break;
				case 817:
					ShopManager.getShops().get(52).open(player);
					break;
				case 818:
					ShopManager.getShops().get(53).open(player);
					break;
				case 819:
					ShopManager.getShops().get(54).open(player);
					break;
				case 820:
					ShopManager.getShops().get(55).open(player);
					break;
				case 825:
					ShopManager.getShops().get(56).open(player);
					break;
				case 826:
					ShopManager.getShops().get(57).open(player);
					break;
				case 845:
					ShopManager.getShops().get(58).open(player);
					break;
				case 843:
					ShopManager.getShops().get(59).open(player);
					break;
				case 846:
					ShopManager.getShops().get(60).open(player);
					break;
				case 847:
					ShopManager.getShops().get(61).open(player);
					break;
				case 457:
					DialogueManager.start(player, 117);
					player.setDialogueActionId(74);
					break;
				case 8710:
				case 8707:
				case 8706:
				case 8705:
					EnergyHandler.rest(player);
					break;
				// 947:
				case 11226:
					if (Dungeoneering.doingDungeoneering(player)) {
						ShopManager.getShops().get(45).open(player);
					}
					break;
				case 9713:
					player.setDialogueActionId(69);
					break;
				case 2622:
					ShopManager.getShops().get(43).open(player);
					break;
				case 3101:
					DialogueManager.start(player, 90);
					player.setDialogueActionId(57);
					break;
				case 1597:
				case 8275:
				case 9085:
				case 7780:
				case 4549:
				case 4566:
				case 4567:
				case 4568:
					if (npc.getId() != player.getSlayer().getSlayerMaster().getNpcId()) {
						player.getPacketSender().sendMessage("This is not your current Slayer master.");
						return;
					}
					if (player.getSlayer().getSlayerTask() == SlayerTasks.NO_TASK) {
						DialogueManager.start(player, SlayerDialogues.secondDialogue(player));
					} else {
						DialogueManager.start(player, SlayerDialogues.dialogue(player));
					}
					break;
				case 4550:
					if (npc.getId() != player.getSlayer().getSlayerMaster().getNpcId()) {
						player.getPacketSender().sendMessage("This is not your current Slayer master.");
						return;
					} else {
						if(player.isGroupLeader()) {
							if (player.getSlayer().getSlayerTask() == SlayerTasks.NO_TASK) {
								DialogueManager.start(player, SlayerDialogues.secondDialogue(player));
							} else {
								DialogueManager.start(player, SlayerDialogues.dialogue(player));
							}
						} else {
							player.getPacketSender().sendMessage("You need to be a Group Leader to talk to me!");
						}
					}
					break;
				case 437:
					DialogueManager.start(player, 99);
					player.setDialogueActionId(58);
					break;
				case 5112:
					ShopManager.getShops().get(38).open(player);
					break;
				case 743:
					DialogueManager.start(player, 749);
					player.setDialogueActionId(750);
					break;
				case 8591:
					if(!player.getEquipment().contains(3285)) {
						player.getPacketSender().sendMessage("@red@You need to wield an egyptian sword to talk to Nomad!");
						return;
					}
					// player.nomadQuest[0] = player.nomadQuest[1] =
					// player.nomadQuest[2] = false;
					if (!player.getMinigameAttributes().getNomadAttributes().hasFinishedPart(0)) {
						DialogueManager.start(player, 48);
						player.setDialogueActionId(23);
					} else if (player.getMinigameAttributes().getNomadAttributes().hasFinishedPart(0)
							&& !player.getMinigameAttributes().getNomadAttributes().hasFinishedPart(1)) {
						DialogueManager.start(player, 50);
						player.setDialogueActionId(24);
					} else if (player.getMinigameAttributes().getNomadAttributes().hasFinishedPart(1))
						DialogueManager.start(player, 53);
					break;
				case 3385:
					if (player.getMinigameAttributes().getRecipeForDisasterAttributes().hasFinishedPart(0) && player
							.getMinigameAttributes().getRecipeForDisasterAttributes().getWavesCompleted() < 6) {
						DialogueManager.start(player, 39);
						return;
					}
					if (player.getMinigameAttributes().getRecipeForDisasterAttributes().getWavesCompleted() == 6) {
						DialogueManager.start(player, 46);
						return;
					}
					DialogueManager.start(player, 38);
					player.setDialogueActionId(20);
					break;
				case 6139:
					DialogueManager.start(player, 30);
					player.setDialogueActionId(17);
					break;
				case 3789:
					player.getPacketSender().sendInterface(18730);
					player.getPacketSender().sendString(18729,
							"Commendations: " + Integer.toString(player.getPointsManager().getPoints("pc")));
					break;
				case 650:
					ShopManager.getShops().get(35).open(player);
					break;
				case 6055:
				case 6056:
				case 6057:
				case 6058:
				case 6059:
				case 6060:
				case 6061:
				case 6062:
				case 6063:
				case 6064:
				case 7903:
					if (npc.getId() == 7903 && player.getLocation() == Location.MEMBER_ZONE) {
						if (player.getDonor() != DonorRights.SPONSOR) {
							player.getPacketSender().sendMessage("You must be at least a Sponsor to use this.");
							return;
						}
					}
					PuroPuro.catchImpling(player, npc);
					break;
				case 8022:
				case 8028:
					DesoSpan.siphon(player, npc);
					break;
				case 2579:
					player.setDialogueActionId(13);
					DialogueManager.start(player, 24);
					break;
				case 8725:
					player.setDialogueActionId(10);
					DialogueManager.start(player, 19);
					break;
				case 4249:
					player.setDialogueActionId(9);
					DialogueManager.start(player, 64);
					break;
				case 6807:
				case 6994:
				case 6995:
				case 6867:
				case 6868:
				case 6794:
				case 6795:
				case 6815:
				case 6816:
				case 6874:
				case 6873:
				case 3594:
				case 3590:
				case 3596:
					if (player.getSummoning().getFamiliar() == null
							|| player.getSummoning().getFamiliar().getSummonNpc() == null
							|| player.getSummoning().getFamiliar().getSummonNpc().getIndex() != npc.getIndex()) {
						player.getPacketSender().sendMessage("That is not your familiar.");
						return;
					}
					player.getSummoning().store();
					break;
				case 605:
					player.setDialogueActionId(8);
					DialogueManager.start(player, 13);
					break;
				case 6970:
					player.setDialogueActionId(3);
					DialogueManager.start(player, 3);
					break;
				case 318:
				case 316:
				case 313:
				case 312:
					player.setEntityInteraction(npc);
					Fishing.setupFishing(player, Fishing.forSpot(npc.getId(), false));
					break;
				case 805:
					ShopManager.getShops().get(34).open(player);
					break;
				case 462:
					ShopManager.getShops().get(33).open(player);
					break;
				case 461:
					ShopManager.getShops().get(32).open(player);
					break;
				case 8444:
					if (player.getDonor() != DonorRights.DONOR && player.getDonor() != DonorRights.DELUXE_DONOR
							&& player.getDonor() != DonorRights.SPONSOR) {
						player.getPacketSender().sendMessage("You must be a donor to access this store.");
						return;
					}
					ShopManager.getShops().get(31).open(player);
					break;
				case 8459:
					ShopManager.getShops().get(30).open(player);
					break;
				case 3299:
					ShopManager.getShops().get(21).open(player);
					break;
				case 548:
					ShopManager.getShops().get(20).open(player);
					break;
				case 1685:
					ShopManager.getShops().get(19).open(player);
				case 4657:
					ShopManager.getShops().get(48).open(player);
					player.getPacketSender()
							.sendMessage("<img=10><col=FF0000><shad=0>You currently have @bla@"
									+ player.getPointsManager().getPoints("donation") + " <col=FF0000><shad=0>donation points.")
							.sendMessage("<img=10><col=5500FF><shad=0>You can buy donation points by doing :: donate.");
					player.getPacketSender().sendMessage("<img=10><col=BF0000><shad=0> Make sure to examine items inside shop, to see more info!");
					break;
				case 308:
					ShopManager.getShops().get(18).open(player);
					break;
				case 802:
					ShopManager.getShops().get(17).open(player);
					break;
				case 278:
					ShopManager.getShops().get(16).open(player);
					break;
				case 4946:
					ShopManager.getShops().get(15).open(player);
					break;
				case 948:
					ShopManager.getShops().get(13).open(player);
					break;
				case 4906:
					ShopManager.getShops().get(14).open(player);
					break;
				case 520:
					ShopManager.getShops().get(75).open(player);
					break;
				case 521:
					ShopManager.getShops().get(12).open(player);
					break;
				case 2292:
					ShopManager.getShops().get(11).open(player);
					break;
				case 2676:
					player.getPacketSender().sendInterface(3559);
					player.getAppearance().setCanChangeAppearance(true);
					break;
				case 494:
				case 1360:
					player.getBank(player.getCurrentBankTab()).open();
					break;
				}
				if (!(npc.getId() >= 8705 && npc.getId() <= 8710)) {
					npc.setPositionToFace(player.getPosition());
				}
				player.setPositionToFace(npc.getPosition());
			}
		}));
	}

	public void handleFourthClick(Player player, Packet packet) {
		int index = packet.readLEShort();
		if (index < 0 || index > World.getNpcs().capacity())
			return;
		final NPC npc = World.getNpcs().get(index);
		if (npc == null)
			return;
		player.setEntityInteraction(npc);
		if (player.getRights() == PlayerRights.DEVELOPER)
			player.getPacketSender().sendMessage("Fourth click npc id: " + npc.getId());
		player.setWalkToTask(new WalkToTask(player, npc.getPosition(), npc.getSize(), new FinalizedMovementTask() {
			@Override
			public void execute() {
				switch (npc.getId()) {
				case 1861:
					ShopManager.getShops().get(2).open(player);
					break;
				case 705:
					ShopManager.getShops().get(7).open(player);
					break;
				case 2253:
					ShopManager.getShops().get(8).open(player);
					break;
				case 1597:
				case 9085:
				case 8275:
				case 7780:
				case 4549:
				case 4550:
				case 4566:
				case 4567:
				case 4568:
					player.getPacketSender().sendString(36030,
							"Current Points:   " + player.getPointsManager().getPoints("slayer"));
					player.getPacketSender().sendInterface(36000);
					break;
				}
				npc.setPositionToFace(player.getPosition());
				player.setPositionToFace(npc.getPosition());
			}
		}));
	}

	public void handleSecondClick(Player player, Packet packet) {
		int index = packet.readLEShortA();
		if (index < 0 || index > World.getNpcs().capacity())
			return;
		final NPC npc = World.getNpcs().get(index);
		if (npc == null)
			return;
		player.setEntityInteraction(npc);
		final int npcId = npc.getId();
		if (player.getRights() == PlayerRights.DEVELOPER)
			player.getPacketSender().sendMessage("Second click npc id: " + npcId);
		
		if (BossPets.pickup(player, npc)) {
			player.getMovementQueue().reset();
			return;
		}
		player.setWalkToTask(new WalkToTask(player, npc.getPosition(), npc.getSize(), new FinalizedMovementTask() {
			@Override
			public void execute() {
				switch (npc.getId()) {
				case 705:
					ShopManager.getShops().get(5).open(player);
					break;
				case 815:
					ShopManager.getShops().get(49).open(player);
					player.getPacketSender().sendMessage("<img=10><col=BF0000><shad=0> You can't buy items from this store, only sell!");
					break;
				case 860:
					ShopManager.getShops().get(74).open(player);
					player.getPacketSender().sendMessage("<img=10><col=BF0000><shad=0> You can't buy items from this store, only sell!");
					break;
				case 5087:
					ShopManager.getShops().get(26).open(player);
					break;
				case 861:
					ShopManager.getShops().get(69).open(player);
					player.getPacketSender().sendMessage("<img=10><col=BF0000><shad=0> You can't buy items from this store, only sell!");
					break;
				case 862:
					ShopManager.getShops().get(70).open(player);
					player.getPacketSender().sendMessage("<img=10><col=BF0000><shad=0> You can't buy items from this store, only sell!");
					break;
				case 863:
					ShopManager.getShops().get(71).open(player);
					player.getPacketSender().sendMessage("<img=10><col=BF0000><shad=0> You can't buy items from this store, only sell!");
					break;
				case 864:
					ShopManager.getShops().get(72).open(player);
					player.getPacketSender().sendMessage("<img=10><col=BF0000><shad=0> You can't buy items from this store, only sell!");
					break;
				case 865:
					ShopManager.getShops().get(73).open(player);
					player.getPacketSender().sendMessage("<img=10><col=BF0000><shad=0> You can't buy items from this store, only sell!");
					break;
				case 816:
					ShopManager.getShops().get(51).open(player);
					player.getPacketSender().sendMessage("<img=10><col=BF0000><shad=0> You can't buy items from this store, only sell!");
					break;
				case 817:
					ShopManager.getShops().get(52).open(player);
					player.getPacketSender().sendMessage("<img=10><col=BF0000><shad=0> You can't buy items from this store, only sell!");
					break;
				case 818:
					ShopManager.getShops().get(53).open(player);
					player.getPacketSender().sendMessage("<img=10><col=BF0000><shad=0> You can't buy items from this store, only sell!");
					break;
				case 819:
					ShopManager.getShops().get(54).open(player);
					player.getPacketSender().sendMessage("<img=10><col=BF0000><shad=0> You can't buy items from this store, only sell!");
					break;
				case 820:
					ShopManager.getShops().get(55).open(player);
					player.getPacketSender().sendMessage("<img=10><col=BF0000><shad=0> You can't buy items from this store, only sell!");
					break;
				case 825:
					ShopManager.getShops().get(56).open(player);
					player.getPacketSender().sendMessage("<img=10><col=BF0000><shad=0> You can't buy items from this store, only sell!");
					break;
				case 826:
					ShopManager.getShops().get(57).open(player);
					player.getPacketSender().sendMessage("<img=10><col=BF0000><shad=0> You can't buy items from this store, only sell!");
					break;
				case 845:
					ShopManager.getShops().get(58).open(player);
					player.getPacketSender().sendMessage("<img=10><col=BF0000><shad=0> You can't buy items from this store, only sell!");
					break;
				case 843:
					ShopManager.getShops().get(59).open(player);
					player.getPacketSender().sendMessage("<img=10><col=BF0000><shad=0> You can't buy items from this store, only sell!");
					break;
				case 846:
					ShopManager.getShops().get(60).open(player);
					player.getPacketSender().sendMessage("<img=10><col=BF0000><shad=0> You can't buy items from this store, only sell!");
					break;
				case 847:
					ShopManager.getShops().get(61).open(player);
					player.getPacketSender().sendMessage("<img=10><col=BF0000><shad=0> You can't buy items from this store, only sell!");
					break;
				case 853:
					ShopManager.getShops().get(62).open(player);
					player.getPacketSender().sendMessage("<img=10><col=BF0000><shad=0> You can't buy items from this store, only sell!");
					break;
				case 854:
					ShopManager.getShops().get(63).open(player);
					player.getPacketSender().sendMessage("<img=10><col=BF0000><shad=0> You can't buy items from this store, only sell!");
					break;
				case 855:
					ShopManager.getShops().get(64).open(player);
					player.getPacketSender().sendMessage("<img=10><col=BF0000><shad=0> You can't buy items from this store, only sell!");
					break;
				case 857:
					ShopManager.getShops().get(65).open(player);
					player.getPacketSender().sendMessage("<img=10><col=BF0000><shad=0> You can't buy items from this store, only sell!");
					break;
				case 858:
					ShopManager.getShops().get(66).open(player);
					player.getPacketSender().sendMessage("<img=10><col=BF0000><shad=0> You can't buy items from this store, only sell!");
					break;
				case 859:
					ShopManager.getShops().get(67).open(player);
					player.getPacketSender().sendMessage("<img=10><col=BF0000><shad=0> You can't buy items from this store, only sell!");
					break;
				case 2579:
					ShopManager.getShops().get(46).open(player);
					player.getPacketSender().sendMessage("@blu@You currently have @bla@"
							+ player.getPointsManager().getPoints("prestige") + " @blu@Prestige points!");
					player.getPacketSender().sendMessage("<img=10><col=BF0000><shad=0> Make sure to examine items inside shop, to see more info!");
					break;
				case 457:
					player.getPacketSender().sendMessage("The ghost teleports you away.");
					player.getPacketSender().sendInterfaceRemoval();
					player.moveTo(new Position(3651, 3486));
					break;
				case 511:
					ShopManager.getShops().get(29).open(player);
					player.getPacketSender().sendMessage("<col=255>You currently have "
							+ player.getPointsManager().getPoints(GameSettings.SERVER_NAME));
					break;
				case 2622:
					ShopManager.getShops().get(43).open(player);
					break;
				case 462:
					npc.performAnimation(CombatSpells.CONFUSE.getSpell().castAnimation().get());
					npc.forceChat("Off you go!");
					TeleportHandler.teleportPlayer(player, new Position(2911, 4832),
							player.getSpellbook().getTeleportType());
					break;
				case 3101:
					DialogueManager.start(player, 95);
					player.setDialogueActionId(57);
					break;
				case 7969:
					ShopManager.getShops().get(28).open(player);
					break;
				case 605:
					player.getPacketSender()
							.sendMessage("@blu@You currently have @bla@" + player.getPointsManager().getPoints("voting")
									+ " @blu@Voting points.")
							.sendMessage(
									"@blu@You can earn points and coins by voting. To do so, simply use the ::vote command.");
					;
					ShopManager.getShops().get(27).open(player);
					player.getPacketSender().sendMessage("<img=10><col=BF0000><shad=0> Make sure to examine items inside shop, to see more info!");
					break;
				case 1597:
				case 9085:
				case 7780:
				case 4566:
				case 4567:
				case 4568:
				case 4550:
				case 4549:
					if (npc.getId() != player.getSlayer().getSlayerMaster().getNpcId()) {
						player.getPacketSender().sendMessage("This is not your current Slayer master.");
						return;
					}
					if (player.getSlayer().getSlayerTask() == SlayerTasks.NO_TASK) {
						if(player.getSlayer().getSlayerMaster() == SlayerMaster.GOD_MASTER) {
							if(player.isGroupLeader()) {
								player.getGroupSlayer().assignGroupTask();
							} else {
								player.getPacketSender().sendMessage("Only a leader of a group can get a task!");
								return;
							}
						} else {
							player.getSlayer().assignTask();
						}
					} else {
						DialogueManager.start(player, SlayerDialogues.findAssignment(player));
					}
					break;
				case 8591:
					if (!player.getMinigameAttributes().getNomadAttributes().hasFinishedPart(1)) {
						player.getPacketSender()
								.sendMessage("You must complete Nomad's quest before being able to use this shop.");
						return;
					}
					ShopManager.getShops().get(37).open(player);
					break;
				case 805:
					Tanning.selectionInterface(player);
					break;
				case 318:
				case 316:
				case 313:
				case 312:
					player.setEntityInteraction(npc);
					Fishing.setupFishing(player, Fishing.forSpot(npc.getId(), true));
					break;
				case 4946:
					ShopManager.getShops().get(15).open(player);
					break;
				case 946:
					ShopManager.getShops().get(1).open(player);
					break;
				case 961:
					ShopManager.getShops().get(6).open(player);
					break;
				case 1861:
					ShopManager.getShops().get(3).open(player);
					break;

				case 2253:
					ShopManager.getShops().get(9).open(player);
					break;
				case 6970:
					player.setDialogueActionId(35);
					DialogueManager.start(player, 63);
					break;
				}
				npc.setPositionToFace(player.getPosition());
				player.setPositionToFace(npc.getPosition());
			}
		}));
	}

	public void handleThirdClick(Player player, Packet packet) {
		int index = packet.readShort();
		if (index < 0 || index > World.getNpcs().capacity())
			return;
		final NPC npc = World.getNpcs().get(index);
		if (npc == null)
			return;
		player.setEntityInteraction(npc).setPositionToFace(npc.getPosition().copy());
		npc.setPositionToFace(player.getPosition());
		if (player.getRights() == PlayerRights.DEVELOPER)
			player.getPacketSender().sendMessage("Third click npc id: " + npc.getId());
		player.setWalkToTask(new WalkToTask(player, npc.getPosition(), npc.getSize(), new FinalizedMovementTask() {
			@Override
			public void execute() {
				switch (npc.getId()) {
				case 3101:
					ShopManager.getShops().get(42).open(player);
					break;
				case 1597:
				case 8275:
				case 9085:
				case 7780:
				case 4549:
				case 4550:
				case 4566:
				case 4567:
				case 4568:
					ShopManager.getShops().get(40).open(player);
					break;
				case 605:
					LoyaltyProgram.open(player);
					break;
				case 946:
					ShopManager.getShops().get(0).open(player);
					break;
				case 1861:
					ShopManager.getShops().get(48).open(player);
					break;
				case 961:
					boolean restore = player.getSpecialPercentage() < 100;
					if (restore) {
						player.setSpecialPercentage(100);
						CombatSpecial.updateBar(player);
						player.getPacketSender().sendMessage("Your special attack energy has been restored.");
					}
					for (Skill skill : Skill.values()) {
						if (player.getSkillManager().getCurrentLevel(skill) < player.getSkillManager()
								.getMaxLevel(skill)) {
							player.getSkillManager().setCurrentLevel(skill,
									player.getSkillManager().getMaxLevel(skill));
							restore = true;
						}
					}
					if (restore) {
						player.performGraphic(new Graphic(1302));
						player.getPacketSender().sendMessage("Your stats have been restored.");
					} else
						player.getPacketSender().sendMessage("Your stats do not need to be restored at the moment.");
					break;
				case 2253:
					ShopManager.getShops().get(10).open(player);
					break;
				}
				npc.setPositionToFace(player.getPosition());
				player.setPositionToFace(npc.getPosition());
			}
		}));
	}
}
