package com.ruthlessps.net.packet.impl;

import com.ruthlessps.engine.task.impl.WalkToTask;
import com.ruthlessps.engine.task.impl.WalkToTask.FinalizedMovementTask;
import com.ruthlessps.model.Animation;
import com.ruthlessps.model.GameObject;
import com.ruthlessps.model.Graphic;
import com.ruthlessps.model.Item;
import com.ruthlessps.model.Locations.Location;
import com.ruthlessps.model.PlayerRanks.PlayerRights;
import com.ruthlessps.model.Position;
import com.ruthlessps.model.Skill;
import com.ruthlessps.net.packet.Packet;
import com.ruthlessps.net.packet.PacketListener;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.World;
import com.ruthlessps.world.clip.region.RegionClipping;
import com.ruthlessps.world.content.ItemForging;
import com.ruthlessps.world.content.dialogue.DialogueManager;
import com.ruthlessps.world.content.skill.impl.cooking.Cooking;
import com.ruthlessps.world.content.skill.impl.cooking.CookingData;
import com.ruthlessps.world.content.skill.impl.crafting.Gems;
import com.ruthlessps.world.content.skill.impl.crafting.LeatherMaking;
import com.ruthlessps.world.content.skill.impl.firemaking.Firemaking;
import com.ruthlessps.world.content.skill.impl.fletching.Fletching;
import com.ruthlessps.world.content.skill.impl.herblore.Herblore;
import com.ruthlessps.world.content.skill.impl.herblore.PotionCombinating;
import com.ruthlessps.world.content.skill.impl.herblore.WeaponPoison;
import com.ruthlessps.world.content.skill.impl.prayer.BonesOnAltar;
import com.ruthlessps.world.content.skill.impl.prayer.Prayer;
import com.ruthlessps.world.content.skill.impl.slayer.SlayerDialogues;
import com.ruthlessps.world.content.skill.impl.slayer.SlayerMaster;
import com.ruthlessps.world.content.skill.impl.slayer.SlayerTasks;
import com.ruthlessps.world.content.skill.impl.smithing.EquipmentMaking;
import com.ruthlessps.world.entity.impl.player.Player;

/**
 * This packet listener is called when a player 'uses' an item on another
 * entity.
 * 
 * @author relex lawl
 */

public class UseItemPacketListener implements PacketListener {

	public final static int USE_ITEM = 122;

	public final static int ITEM_ON_NPC = 57;

	public final static int ITEM_ON_ITEM = 53;

	public final static int ITEM_ON_OBJECT = 192;

	public final static int ITEM_ON_GROUND_ITEM = 25;

	public static final int ITEM_ON_PLAYER = 14;
	static int[] oreos = {14559, 14560, 14561, 14562, 14563, 14564, 14565};
	static int[] skys = {14566, 14567, 14568, 14569, 14570, 14571, 14572};
	static int[] darth = {14581, 14582, 14583, 14584, 14585, 14586, 20690};
	static int[] cash = {79, 80, 14587, 14588, 3314, 14590, 14591};
	static int[] silver = {81, 14596, 14597, 14598, 14599, 14619, 18742};
	static int[] prostex = {4553, 4554, 4555, 4556, 4557};
	static int[] redonex = {3999, 4000, 4001, 4002, 4003};
	static int[] legion = {934, 935, 937, 940, 2884};
	static int[] zarthyx = {933, 938, 979, 3246, 14453};
	static int[] rucord = {932, 936, 939, 996, 3248};
	static int[] vortex = {3271, 3272, 3273, 3274, 3275, 3276};
	static int[] raziel = {20526,20528,20300,7041,7047,7153,7621};
	static int[] gorg = {20834,20840,20838,20832,20836,19730,7042};
	static int[] harnan = {20732,20733,20786,20969,21057,20971,20973};
	static int[] landazar = {20301,20302,20310,7044,7049,7154,7672};
	static int[] xintor = {20942,20946,20940,20944,20948,20924,20934};
	static int[] subjugation = {3307,3309,3310,3311,3312,3313,17293};
	static int[] camo = {3619, 3620, 3621, 3622, 3623, 3624, 3625};
	static int[] winter = {618, 619, 620, 621, 623, 624, 625};
	static int[] bloodshot = {665, 666, 669, 670, 671, 672, 673};
	static int[] rainbow = {2603, 3626, 3627, 3628, 3629, 3630, 3631, 3632};
	static int[] burst = {1840, 1841, 1842};
	static int[] cryptic = {1843, 1845, 1846, 1848, 1849, 1850, 20652};

	public static void handleSoloUpgrade(Player player, int id, int numb, int chance) {

		if(player.getInventory().contains(id)) {
			switch(numb) {
				case 100:
					player.getInventory().delete(id, 1);
					if(Misc.random(chance) == 0) {
						player.getInventory().add(3508, 1);
						player.getPacketSender().sendMessage("The upgrade was successful! You received a Demonic Cryptic Sword.");
					}
					break;
				case 1:
					player.getInventory().delete(id, 1);
					if(Misc.random(chance) == 0) {
						player.getInventory().add(oreos[Misc.random(oreos.length-1)], 1);
						player.getPacketSender().sendMessage("The upgrade was successful! You received an Oreo Torva item.");
					} else {
						player.getPacketSender().sendMessage("The upgrade was unsuccessful!");
					}
				break;
				case 2:
					player.getInventory().delete(id, 1);
					if(Misc.random(chance) == 0) {
						player.getInventory().add(skys[Misc.random(skys.length-1)], 1);
						player.getPacketSender().sendMessage("The upgrade was successful! You received a Sky Torva item.");
					} else {
						player.getPacketSender().sendMessage("The upgrade was unsuccessful!");
					}
				break;
				case 3:
					player.getInventory().delete(id, 1);
					if(Misc.random(chance) == 0) {
						player.getInventory().add(darth[Misc.random(darth.length-1)], 1);
						player.getPacketSender().sendMessage("The upgrade was successful! You received a Darth Torva item.");
					} else {
						player.getPacketSender().sendMessage("The upgrade was unsuccessful!");
					}
				break;
				case 4:
					player.getInventory().delete(id, 1);
					if(Misc.random(chance) == 0) {
						player.getInventory().add(cash[Misc.random(cash.length-1)], 1);
						player.getPacketSender().sendMessage("The upgrade was successful! You received a Cash Torva item.");
					} else {
						player.getPacketSender().sendMessage("The upgrade was unsuccessful!");
					}
				break;
				case 5:
					player.getInventory().delete(id, 1);
					if(Misc.random(chance) == 0) {
						player.getInventory().add(silver[Misc.random(silver.length-1)], 1);
						player.getPacketSender().sendMessage("The upgrade was successful! You received a Silver Torva item.");
					} else {
						player.getPacketSender().sendMessage("The upgrade was unsuccessful!");
					}
				break;
				case 6:
					player.getInventory().delete(id, 1);
					if(Misc.random(chance) == 0) {
						player.getInventory().add(prostex[Misc.random(prostex.length-1)], 1);
						player.getPacketSender().sendMessage("The upgrade was successful! You received a Prostex item.");
					} else {
						player.getPacketSender().sendMessage("The upgrade was unsuccessful!");
					}
				break;
				case 7:
					player.getInventory().delete(id, 1);
					if(Misc.random(chance) == 0) {
						player.getInventory().add(redonex[Misc.random(redonex.length-1)], 1);
						player.getPacketSender().sendMessage("The upgrade was successful! You received a Redonex item.");
					} else {
						player.getPacketSender().sendMessage("The upgrade was unsuccessful!");
					}
				break;
				case 8:
					player.getInventory().delete(id, 1);
					if(Misc.random(chance) == 0) {
						player.getInventory().add(legion[Misc.random(legion.length-1)], 1);
						player.getPacketSender().sendMessage("The upgrade was successful! You received a Legion item.");
					} else {
						player.getPacketSender().sendMessage("The upgrade was unsuccessful!");
					}
				break;
				case 9:
					player.getInventory().delete(id, 1);
					if(Misc.random(chance) == 0) {
						player.getInventory().add(zarthyx[Misc.random(zarthyx.length-1)], 1);
						player.getPacketSender().sendMessage("The upgrade was successful! You received a Zarthyx item.");
					} else {
						player.getPacketSender().sendMessage("The upgrade was unsuccessful!");
					}
				break;
				case 10:
					player.getInventory().delete(id, 1);
					if(Misc.random(chance) == 0) {
						player.getInventory().add(rucord[Misc.random(rucord.length-1)], 1);
						player.getPacketSender().sendMessage("The upgrade was successful! You received a Rucord item.");
					} else {
						player.getPacketSender().sendMessage("The upgrade was unsuccessful!");
					}
				break;
				case 11:
					player.getInventory().delete(id, 1);
					if(Misc.random(chance) == 0) {
						player.getInventory().add(raziel[Misc.random(raziel.length-1)], 1);
						player.getPacketSender().sendMessage("The upgrade was successful! You received a Raziel item.");
					} else {
						player.getPacketSender().sendMessage("The upgrade was unsuccessful!");
					}
				break;
				case 12:
					player.getInventory().delete(id, 1);
					if(Misc.random(chance) == 0) {
						player.getInventory().add(gorg[Misc.random(gorg.length-1)], 1);
						player.getPacketSender().sendMessage("The upgrade was successful! You received a Gorg item.");
					} else {
						player.getPacketSender().sendMessage("The upgrade was unsuccessful!");
					}
				break;
				case 13:
					player.getInventory().delete(id, 1);
					if(Misc.random(chance) == 0) {
						player.getInventory().add(harnan[Misc.random(harnan.length-1)], 1);
						player.getPacketSender().sendMessage("The upgrade was successful! You received a Harnan item.");
					} else {
						player.getPacketSender().sendMessage("The upgrade was unsuccessful!");
					}
				break;
				case 14:
					player.getInventory().delete(id, 1);
					if(Misc.random(chance) == 0) {
						player.getInventory().add(landazar[Misc.random(landazar.length-1)], 1);
						player.getPacketSender().sendMessage("The upgrade was successful! You received a Landazar item.");
					} else {
						player.getPacketSender().sendMessage("The upgrade was unsuccessful!");
					}
				break;
				case 15:
					player.getInventory().delete(id, 1);
					if(Misc.random(chance) == 0) {
						player.getInventory().add(xintor[Misc.random(xintor.length-1)], 1);
						player.getPacketSender().sendMessage("The upgrade was successful! You received a Xintor item.");
					} else {
						player.getPacketSender().sendMessage("The upgrade was unsuccessful!");
					}
				break;
				case 16:
					player.getInventory().delete(id, 1);
					if(Misc.random(chance) == 0) {
						player.getInventory().add(subjugation[Misc.random(subjugation.length-1)], 1);
						player.getPacketSender().sendMessage("The upgrade was successful! You received a Subjugation item.");
					} else {
						player.getPacketSender().sendMessage("The upgrade was unsuccessful!");
					}
				break;
				case 17:
					player.getInventory().delete(id, 1);
					if(Misc.random(chance) == 0) {
						player.getInventory().add(camo[Misc.random(camo.length-1)], 1);
						player.getPacketSender().sendMessage("The upgrade was successful! You received a Camouflage item.");
					} else {
						player.getPacketSender().sendMessage("The upgrade was unsuccessful!");
					}
				break;
				case 18:
					player.getInventory().delete(id, 1);
					if(Misc.random(chance) == 0) {
						player.getInventory().add(vortex[Misc.random(vortex.length-1)], 1);
						player.getPacketSender().sendMessage("The upgrade was successful! You received a Vortex item.");
					} else {
						player.getPacketSender().sendMessage("The upgrade was unsuccessful!");
					}
				break;
				case 19:
					player.getInventory().delete(id, 1);
					if(Misc.random(chance) == 0) {
						player.getInventory().add(winter[Misc.random(winter.length-1)], 1);
						player.getPacketSender().sendMessage("The upgrade was successful! You received a Winter Camo item.");
					} else {
						player.getPacketSender().sendMessage("The upgrade was unsuccessful!");
					}
				break;
				case 20:
					player.getInventory().delete(id, 1);
					if(Misc.random(chance) == 0) {
						player.getInventory().add(bloodshot[Misc.random(bloodshot.length-1)], 1);
						player.getPacketSender().sendMessage("The upgrade was successful! You received a Bloodshot item.");
					} else {
						player.getPacketSender().sendMessage("The upgrade was unsuccessful!");
					}
				break;
				case 21:
					player.getInventory().delete(id, 1);
					if(Misc.random(chance) == 0) {
						player.getInventory().add(burst[Misc.random(burst.length-1)], 1);
						player.getPacketSender().sendMessage("The upgrade was successful! You received a Burst item.");
					} else {
						player.getPacketSender().sendMessage("The upgrade was unsuccessful!");
					}
				break;
				case 22:
					player.getInventory().delete(id, 1);
					if(Misc.random(chance) == 0) {
						player.getInventory().add(cryptic[Misc.random(cryptic.length-1)], 1);
						player.getPacketSender().sendMessage("The upgrade was successful! You received a Cryptic item.");
					} else {
						player.getPacketSender().sendMessage("The upgrade was unsuccessful!");
					}
				break;
				case 23:
					player.getInventory().delete(id, 1);
					if(Misc.random(chance) == 0) {
						player.getInventory().add(rainbow[Misc.random(rainbow.length-1)], 1);
						player.getPacketSender().sendMessage("The upgrade was successful! You received a Rainbow item.");
					} else {
						player.getPacketSender().sendMessage("The upgrade was unsuccessful!");
					}
				break;
				case 101:
					player.getInventory().delete(id, 1);
					if(Misc.random(chance) == 0) {
						player.getInventory().add(7803, 1);
						player.getPacketSender().sendMessage("The upgrade was successful! You received a Dropp Collector(u).");
					} else {
						player.getPacketSender().sendMessage("The upgrade was unsuccessful!");
					}
				break;
			}
		} else {
			player.getPacketSender().sendMessage("The item needs to be in your inventory!");
		}
		return;
		
	}
	private static void itemOnItem(Player player, Packet packet) {
		int usedWithSlot = packet.readUnsignedShort();
		int itemUsedSlot = packet.readUnsignedShortA();
		if (usedWithSlot < 0 || itemUsedSlot < 0 || itemUsedSlot > player.getInventory().capacity()
				|| usedWithSlot > player.getInventory().capacity())
			return;
		Item usedWith = player.getInventory().getItems()[usedWithSlot];
		Item itemUsedWith = player.getInventory().getItems()[itemUsedSlot];
		if (usedWith.getId() == 6573 || itemUsedWith.getId() == 6573) {
			player.getPacketSender().sendMessage("To make an Amulet of Fury, you need to put an onyx in a furnace.");
			return;
		}
		WeaponPoison.execute(player, itemUsedWith.getId(), usedWith.getId());
		if (itemUsedWith.getId() == 590 || usedWith.getId() == 590)
			Firemaking.lightFire(player, itemUsedWith.getId() == 590 ? usedWith.getId() : itemUsedWith.getId(), false,
					1);
		if (itemUsedWith.getDefinition().getName().contains("(") && usedWith.getDefinition().getName().contains("("))
			PotionCombinating.combinePotion(player, usedWith.getId(), itemUsedWith.getId());
		if (usedWith.getId() == Herblore.VIAL || itemUsedWith.getId() == Herblore.VIAL) {
			if (Herblore.makeUnfinishedPotion(player, usedWith.getId())
					|| Herblore.makeUnfinishedPotion(player, itemUsedWith.getId()))
				return;
		}
		if(itemUsedWith.getId() == 4703) {
		int item = usedWith.getId();
				//AMERICAN
			if(item == 20250 || item == 20251 || item == 20252 || item == 20253 || item == 14556 || item == 14557 || item == 14558) {
				handleSoloUpgrade(player, item, 1, 2);
				//OREO
			} else if(item == 14560 || item == 14561 || item == 14562 || item == 14563 || item == 14564 || item == 14565 || item == 14559) {
				handleSoloUpgrade(player, item, 2, 3);
				//SKY
			} else if(item == 14566 || item == 14567 || item == 14568 || item == 14569 || item == 14570 || item == 14571 || item == 14572) {
				handleSoloUpgrade(player, item, 3, 4);
				//DARTH
			} else if(item == 14581 || item == 14582 || item == 14583 || item == 14584 || item == 14585 || item == 14586 || item == 20690) {
				handleSoloUpgrade(player, item, 4, 5);
				//CASH
			} else if(item == 79 || item == 80 || item == 14587 || item == 14588 || item == 3314 || item == 14590 || item == 14591) {
				handleSoloUpgrade(player, item, 5, 6);
				//CLOBE
			} else if(item == 4450 || item == 4451 || item == 4452 || item == 4453 || item == 4454) {
				handleSoloUpgrade(player, item, 6, 2);
				//PROSTEX
			} else if(item == 4553 || item == 4554 || item == 4555 || item == 4556 || item == 4557) {
				handleSoloUpgrade(player, item, 7, 3);
				//REDONEX
			} else if(item == 3999 || item == 4000 || item == 4001 || item == 4002 || item == 4003) {
				handleSoloUpgrade(player, item, 8, 4);
				//LEGION
			} else if(item == 934 || item == 935 || item == 937 || item == 940 || item == 2884) {
				handleSoloUpgrade(player, item, 9, 5);
				//ZARTHYX
			} else if(item == 933 || item == 938 || item == 979 || item == 3246 || item == 14453) {
				handleSoloUpgrade(player, item, 10, 6);
				//ARCHUS
			} else if(item == 20458 || item == 20460 || item == 20462 || item == 7040 || item == 7046 || item == 7619 || item == 7152) {
				handleSoloUpgrade(player, item, 11, 2);
				//RAZIEL
			} else if(item == 20526 || item == 20528 || item == 20300 || item == 7041 || item == 7047 || item == 7153 || item == 7621) {
				handleSoloUpgrade(player, item, 12, 3);
				//GORG
			} else if(item == 20834 || item == 20840 || item == 20838 || item == 20832 || item == 20836 || item == 19730 || item == 7042) {
				handleSoloUpgrade(player, item, 13, 4);
				//HARNAN
			} else if(item == 20732 || item == 20733 || item == 20786 || item == 20969 || item == 21057 || item == 20971 || item == 20973) {
				handleSoloUpgrade(player, item, 14, 5);
				//LANDAZAR
			} else if(item == 20301 || item == 20302 || item == 20310 || item == 7044 || item == 7049 || item == 7154 || item == 7672) {
				handleSoloUpgrade(player, item, 15, 6);
			} else if(item == 20942 || item == 20946 || item == 20940 || item == 20944 || item == 20948 || item == 20924 || item == 20934) {
				handleSoloUpgrade(player, item, 16, 8); //XINTOR TO SUBJUGATION
			} else if(item == 81 || item == 14596 || item == 14597 || item == 14598 || item == 14599 || item == 14619 || item == 18742) {
				handleSoloUpgrade(player, item, 17, 8); //SILVER TO CAMO
			} else if(item == 932 || item == 936 || item == 939 || item == 996 || item == 3248) {
				handleSoloUpgrade(player, item, 18, 8); //RUCORD TO VORTEX
			} else if(item == 3619 || item == 3620 || item == 3621 || item == 3622 || item == 3623 || item == 3624 || item == 3625) {
				handleSoloUpgrade(player, item, 19, 10); //CAMO TO WINTER
			} else if(item == 618 || item == 619 || item == 620 || item == 621 || item == 625 || item == 623 || item == 624) {
				handleSoloUpgrade(player, item, 20, 12); //WINTER TO BLOODSHOT
			} else if(item == 2603 || item == 3626 || item == 3627 || item == 3628 || item == 3629 || item == 3630 || item == 3631 || item == 3632) {
				handleSoloUpgrade(player, item, 21, 15); //RAINBOW TO BURST
			} else if(item == 1840 || item == 1841 || item == 1842) {
				handleSoloUpgrade(player, item, 22, 17); //BURST TO CRYPTIC
			} else if(item == 665 || item == 666 || item == 669 || item == 670 || item == 671 || item == 672 || item == 673) {
				handleSoloUpgrade(player, item, 23, 13); //BLOODSHOT TO RAINBOW
			} else if(item == 1849) {
				handleSoloUpgrade(player, item, 100, 19);
			} else if(item == 1009) {
				handleSoloUpgrade(player, item, 101, 1);
			}
			return;
		}

		if (Herblore.finishPotion(player, usedWith.getId(), itemUsedWith.getId())
				|| Herblore.finishPotion(player, itemUsedWith.getId(), usedWith.getId()))
			return;
		if ((usedWith.getId() == 3279) && (itemUsedWith.getId() == 3283) && (player.getInventory().contains(3279))
				&& (player.getInventory().contains(3283))) {
			player.getPacketSender().sendMessage("You attach a suppressor to your weapon.");
			player.getInventory().delete(3279, 1);
			player.getInventory().delete(3283, 1);
			player.getInventory().add(3280, 1);
		}
		if ((usedWith.getId() == 3081) && (itemUsedWith.getId() == 3282) && (player.getInventory().contains(3081))
				&& (player.getInventory().contains(3282))) {
			player.getPacketSender().sendMessage("You attach a grenade launcher to your AK-47.");
			player.getInventory().delete(3081, 1);
			player.getInventory().delete(3282, 1);
			player.getInventory().add(3082, 1);
		}
		if ((usedWith.getId() == 3092) && (itemUsedWith.getId() == 3283) && (player.getInventory().contains(3092))
				&& (player.getInventory().contains(3283))) {
			player.getPacketSender().sendMessage("You attach a suppressor to your Desert Eagle.");
			player.getInventory().delete(3092, 1);
			player.getInventory().delete(3283, 1);
			player.getInventory().add(3135, 1);
		}
		if ((usedWith.getId() == 3277) && (itemUsedWith.getId() == 3281) && (player.getInventory().contains(3277))
				&& (player.getInventory().contains(3281))) {
			player.getPacketSender().sendMessage("You attach a scope to your sniper.");
			player.getInventory().delete(3277, 1);
			player.getInventory().delete(3281, 1);
			player.getInventory().add(3278, 1);
		}
		if (usedWith.getId() == 946 || itemUsedWith.getId() == 946)
			Fletching.openSelection(player, usedWith.getId() == 946 ? itemUsedWith.getId() : usedWith.getId());
		if (usedWith.getId() == 1777 || itemUsedWith.getId() == 1777)
			Fletching.openBowStringSelection(player,
					usedWith.getId() == 1777 ? itemUsedWith.getId() : usedWith.getId());
		if (usedWith.getId() == 53 || itemUsedWith.getId() == 53 || usedWith.getId() == 52
				|| itemUsedWith.getId() == 52)
			Fletching.makeArrows(player, usedWith.getId(), itemUsedWith.getId());
		if (itemUsedWith.getId() == 1755 || usedWith.getId() == 1755)
			Gems.selectionInterface(player, usedWith.getId() == 1755 ? itemUsedWith.getId() : usedWith.getId());
		if (usedWith.getId() == 1733 || itemUsedWith.getId() == 1733)
			LeatherMaking.craftLeatherDialogue(player, usedWith.getId(), itemUsedWith.getId());
		Herblore.handleSpecialPotion(player, itemUsedWith.getId(), usedWith.getId());
		ItemForging.forgeItem(player, itemUsedWith.getId(), usedWith.getId());
		if (player.getRights() == PlayerRights.DEVELOPER)
			player.getPacketSender().sendMessage(
					"ItemOnItem - [usedItem, usedWith] : [" + usedWith.getId() + ", " + itemUsedWith.getId() + "]");
	}

	@SuppressWarnings("unused")
	private static void itemOnNpc(final Player player, Packet packet) {
		int id = packet.readShortA();
		int index = packet.readShortA();
		final int slot = packet.readLEShort();
	}

	@SuppressWarnings("unused")
	private static void itemOnObject(Player player, Packet packet) {
		int interfaceType = packet.readShort();
		final int objectId = packet.readShort();
		final int objectY = packet.readLEShortA();
		final int itemSlot = packet.readLEShort();
		final int objectX = packet.readLEShortA();
		final int itemId = packet.readShort();

		if (itemSlot < 0 || itemSlot > player.getInventory().capacity())
			return;
		final Item item = player.getInventory().getItems()[itemSlot];
		if (item == null)
			return;
		final GameObject gameObject = new GameObject(objectId,
				new Position(objectX, objectY, player.getPosition().getZ()));
		if (objectId > 0 && objectId != 6 && !RegionClipping.objectExists(gameObject)) {
			// player.getPacketSender().sendMessage("An error occured. Error
			// code: "+id).sendMessage("Please report the error to a staff
			// member.");
			return;
		}
		player.setInteractingObject(gameObject);
		player.setWalkToTask(new WalkToTask(player, gameObject.getPosition().copy(), gameObject.getSize(),
				new FinalizedMovementTask() {
					@Override
					public void execute() {
						if (CookingData.forFish(item.getId()) != null && CookingData.isRange(objectId)) {
							player.setPositionToFace(gameObject.getPosition());
							Cooking.selectionInterface(player, CookingData.forFish(item.getId()));
							return;
						}
						if (Prayer.isBone(itemId) && objectId == 409) {
							BonesOnAltar.openInterface(player, itemId);
							return;
						}
						if (player.getFarming().plant(itemId, objectId, objectX, objectY))
							return;
						if (player.getFarming().useItemOnPlant(itemId, objectX, objectY))
							return;
						switch (objectId) {
						case 6189:
							if (player.getSkillManager().getCurrentLevel(Skill.CRAFTING) < 80) {
								player.getPacketSender()
										.sendMessage("You need a Crafting level of at least 80 to make that item.");
								return;
							}
							if (player.getInventory().contains(6573)) {
								if (player.getInventory().contains(1597)) {
									if (player.getInventory().contains(1759)) {
										player.performAnimation(new Animation(896));
										player.getInventory().delete(new Item(1759)).delete(new Item(6573))
												.add(new Item(6585));
										player.getPacketSender().sendMessage(
												"You put the items into the furnace to forge an Amulet of Fury.");
									} else {
										player.getPacketSender().sendMessage("You need some Ball of Wool to do this.");
									}
								} else {
									player.getPacketSender().sendMessage("You need a Necklace mould to do this.");
								}
							}
							break;
						case 7836:
						case 7808:
							if (itemId == 6055) {
								int amt = player.getInventory().getAmount(6055);
								if (amt > 0) {
									player.getInventory().delete(6055, amt);
									player.getPacketSender().sendMessage("You put the weed in the compost bin.");
									player.getSkillManager().addExperience(Skill.FARMING, 20 * amt);
								}
							}
							break;
						case 4306:
							EquipmentMaking.handleAnvil(player);
							break;
						}
					}
				}));
	}

	@SuppressWarnings("unused")
	private static void itemOnPlayer(Player player, Packet packet) {
		int interfaceId = packet.readUnsignedShortA();
		int targetIndex = packet.readUnsignedShort();
		int itemId = packet.readUnsignedShort();
		int slot = packet.readLEShort();
		if (slot < 0 || slot > player.getInventory().capacity() || targetIndex > World.getPlayers().capacity())
			return;
		Player target = World.getPlayers().get(targetIndex);
		if (target == null)
			return;
		switch (itemId) {
		case 962:
			if (!player.getInventory().contains(962) || player.getRights() == PlayerRights.ADMINISTRATOR)
				return;
			player.setPositionToFace(target.getPosition());
			player.performGraphic(new Graphic(1006));
			player.performAnimation(new Animation(451));
			player.getPacketSender().sendMessage("You pull the Christmas cracker...");
			target.getPacketSender().sendMessage("" + player.getUsername() + " pulls a Christmas cracker on you..");
			player.getInventory().delete(962, 1);
			player.getPacketSender().sendMessage("The cracker explodes and you receive a Party hat!");
			player.getInventory().add(1038 + Misc.getRandom(10), 1);
			target.getPacketSender().sendMessage("" + player.getUsername() + " has received a Party hat!");
			/*
			 * if(Misc.getRandom(1) == 1) { target.getPacketSender().
			 * sendMessage("The cracker explodes and you receive a Party hat!");
			 * target.getInventory().add((1038 + Misc.getRandom(5)*2), 1);
			 * player.getPacketSender().sendMessage(""+target.getUsername()
			 * +" has received a Party hat!"); } else { player.getPacketSender().
			 * sendMessage("The cracker explodes and you receive a Party hat!");
			 * player.getInventory().add((1038 + Misc.getRandom(5)*2), 1);
			 * target.getPacketSender().sendMessage(""+player.getUsername()
			 * +" has received a Party hat!"); }
			 */
			break;
		case 18829:
			if (!player.isGroupLeader()) {
				player.getPacketSender().sendMessage("You are not a Group leader!");
				return;
			}
			Player trgt = World.getPlayers().get(targetIndex);
			if (trgt.getSlayer().getDuoPartner() != null) {
				player.getPacketSender().sendMessage("This player has a duo partner!");
				return;
			}
			if(!trgt.getGroupName().equals("NONE")) {
				player.getPacketSender().sendMessage("This player is already in a Slayer Group!");
				return;
			}
			if (trgt.getSlayer().getSlayerTask() != SlayerTasks.NO_TASK) {
				player.getPacketSender().sendMessage("This player already has a Slayer task.");
				return;
			}
			if (trgt.getSlayer().getSlayerMaster() != SlayerMaster.GOD_MASTER) {
				player.getPacketSender().sendMessage("This player does not have the God Slayer Master!");
				return;
			}
			if (trgt.busy() || trgt.getLocation() == Location.WILDERNESS) {
				player.getPacketSender().sendMessage("This player is currently busy.");
				return;
			}
			DialogueManager.start(trgt, SlayerDialogues.inviteGroup(trgt, player));
			player.getPacketSender()
					.sendMessage("You have invited " + trgt.getUsername() + " to join your Slayer Group.");
			break;
		case 4155:
			if (player.getSlayer().getDuoPartner() != null) {
				player.getPacketSender().sendMessage("You already have a duo partner.");
				return;
			}
			if (player.getSlayer().getSlayerTask() != SlayerTasks.NO_TASK) {
				player.getPacketSender().sendMessage("You already have a Slayer task. You must reset it first.");
				return;
			}
			Player duoPartner = World.getPlayers().get(targetIndex);
			if (duoPartner != null) {
				if (duoPartner.getSlayer().getDuoPartner() != null) {
					player.getPacketSender().sendMessage("This player already has a duo partner.");
					return;
				}
				if (duoPartner.getSlayer().getSlayerTask() != SlayerTasks.NO_TASK) {
					player.getPacketSender().sendMessage("This player already has a Slayer task.");
					return;
				}
				if (duoPartner.getSlayer().getSlayerMaster() != player.getSlayer().getSlayerMaster()) {
					player.getPacketSender().sendMessage("You do not have the same Slayer master as that player.");
					return;
				}
				if (duoPartner.busy() || duoPartner.getLocation() == Location.WILDERNESS) {
					player.getPacketSender().sendMessage("This player is currently busy.");
					return;
				}
				DialogueManager.start(duoPartner, SlayerDialogues.inviteDuo(duoPartner, player));
				player.getPacketSender()
						.sendMessage("You have invited " + duoPartner.getUsername() + " to join your Slayer duo team.");
			}
			break;
		}
	}

	/**
	 * The PacketListener logger to debug information and print out errors.
	 */
	// private final static Logger logger =
	// Logger.getLogger(UseItemPacketListener.class);

	@SuppressWarnings("unused")
	private static void useItem(Player player, Packet packet) {
		if (player.isTeleporting() || player.getConstitution() <= 0)
			return;
		int interfaceId = packet.readLEShortA();
		int slot = packet.readShortA();
		int id = packet.readLEShort();
	}

	@Override
	public void handleMessage(Player player, Packet packet) {
		if (player.getConstitution() <= 0)
			return;
		switch (packet.getOpcode()) {
		case ITEM_ON_ITEM:
			itemOnItem(player, packet);
			break;
		case USE_ITEM:
			useItem(player, packet);
			break;
		case ITEM_ON_OBJECT:
			itemOnObject(player, packet);
			break;
		case ITEM_ON_GROUND_ITEM:
			// TODO
			break;
		case ITEM_ON_NPC:
			itemOnNpc(player, packet);
			break;
		case ITEM_ON_PLAYER:
			itemOnPlayer(player, packet);
			break;
		}
	}
}
