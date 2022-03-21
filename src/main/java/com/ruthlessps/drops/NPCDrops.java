package com.ruthlessps.drops;

import java.security.SecureRandom;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ruthlessps.drops.DropLog.DropLogEntry;
import com.ruthlessps.model.GameMode;
import com.ruthlessps.model.GroundItem;
import com.ruthlessps.model.Item;
import com.ruthlessps.model.Skill;
import com.ruthlessps.model.PlayerRanks.DonorRights;
import com.ruthlessps.model.container.impl.Bank;
import com.ruthlessps.model.container.impl.Equipment;
import com.ruthlessps.util.JsonLoader;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.content.DoubleDropWell;
import com.ruthlessps.world.content.HourlyNpc;
import com.ruthlessps.world.content.WellOfGoodwill;
import com.ruthlessps.world.content.ZoneTasks;
import com.ruthlessps.world.content.skill.impl.prayer.BonesData;
import com.ruthlessps.world.entity.impl.GroundItemManager;
import com.ruthlessps.world.entity.impl.npc.NPC;
import com.ruthlessps.world.entity.impl.player.Player;

public class NPCDrops {

	private static Map<Integer, NpcDrop[]> npcDrops = new HashMap<>();
	private static List<NpcDrop> constantDrops = new ArrayList<>();
	private static List<NpcDrop> potentialDrops = new ArrayList<>();
	private static List<NpcDrop> finalDropList = new ArrayList<>();

	public static void clearDropTable(Player player) {
		for(int i = 61006; i < 61095; i+= 3) {
			player.getPA().sendItemOnInterface(i, 0, 1);
			player.getPA().sendString(i + 1, "...");
		}
	}

	public static void loadDropTable(Player player, NPC npc) {
		clearDropTable(player);
		int npcId = npc.getId();
		NpcDrop[] drops = npcDrops.get(npcId);
		if (drops == null) {
			return;
		}
		Arrays.sort(drops, Comparator.comparingInt(NpcDrop::getChance));
		int item_interface_id = 61006;
		int chance_interface_id = 61007;
		for (NpcDrop drop : drops) {
			if (drop == null)
				continue;
			player.getPacketSender().sendItemOnInterface(item_interface_id, drop.getId(), drop.getCount().length > 1 ? drop.getCount()[1] : drop.getCount()[0]);
			int dropChance = drop.getChance();
			if(dropChance != 1) {
				player.getPacketSender().sendString(chance_interface_id, "1 / " + Math.round(dropChance - dropChance * player.getAttributes().calculateBonusDropRate(npcId)/10));
			} else {
				player.getPacketSender().sendString(chance_interface_id, "1 / 1");
			}
			item_interface_id += 3;
			chance_interface_id += 3;
		}
		player.getPacketSender().sendString(61003, "Viewing Drop Table for NPC - " + npc.getDefinition().getName());
		player.getPacketSender().sendInterface(61000);
	}

	public static int getDoubleDropChance(Player player) {
		int chance = 0;
		if(player.getAttributes().getDoubleDropTimer() > 0) {
			chance += 10;
		}
		if(player.getEquipment().containsAll(15662, 15656, 15657, 15660, 15661)) {
			chance += 25;
		}
		if(player.getInventory().contains(744)) {
			chance += 50;
		}
		//TODO
		/*
		if(player.getpetId() == 4201) {
			chance += 10;
		}
		if(player.getpetId() == 2741) {
			chance += 3;
		}
		if(DoubleDropWell.isActive()) {
			chance += 5;
		}
		 */
		chance += player.ddrBonus;
		if(player.getEquipment().containsAll(16639,16640,16641,16644)) {
			chance = 100;
		}
		return chance;
	}
	/*	public static int getDropMultiplier(Player player) {
            int amount = 1;
            if(player.getDoubleDropTimer() > 0) {
                if(Misc.random(9) == 9) {
                    amount += 1;
                }
            }
            if(player.getEquipment().containsAll(16639,16640,16641,16644)) {
                amount += 1;
            }
            if(player.getEquipment().containsAll(15662, 15656, 15657, 15660, 15661)) {
                if(Misc.random(3) == 3) {
                    amount += 1;
                }
            }
            if(player.getInventory().contains(744)) {
                if(Misc.random(2) == 2) {
                    amount += 1;
                }
            }
            if(player.ddrCounter >= 2000) {
                int odd;
                if(player.drCounter < 10000) {
                    odd = player.ddrCounter/2000;
                } else {
                    odd = 5;
                }
                if(Misc.random(99) < odd) {
                    amount += 1;
                }
            }


            return amount;
        }*/
	public static void handleDropTasks(Player player, NPC npc, NpcDrop drop) {
		if(drop.getId() == 6799) {
			ZoneTasks.doTaskProgress(player, ZoneTasks.ZoneData.MONEYBAG_ZONE, 1, drop.getCount()[0]);
		}
		if(drop.getId() == 15250) {
			ZoneTasks.doTaskProgress(player, ZoneTasks.ZoneData.TRAINING_ZONE, 2, 1);
		}
		if(drop.getId() == 9401) {
			ZoneTasks.doTaskProgress(player, ZoneTasks.ZoneData.POINTZONE, 2, 1);
		}
		if(drop.getId() == 6199 && npc.getId() == 3922) {
			ZoneTasks.doTaskProgress(player, ZoneTasks.ZoneData.MYSTERY_BOX_ZONE, 0, drop.getCount()[0]);
		}
		if(drop.getId() == 989 && npc.getId() == 3922) {
			ZoneTasks.doTaskProgress(player, ZoneTasks.ZoneData.MYSTERY_BOX_ZONE, 1, drop.getCount()[0]);
		}
		if(drop.getId() == 5195) {
			ZoneTasks.doTaskProgress(player, ZoneTasks.ZoneData.MYSTERY_BOX_ZONE, 2, 1);
		}
		if(npc.getId() == 1864 && drop.getId() == 17654) {
			ZoneTasks.doTaskProgress(player, ZoneTasks.ZoneData.MINECRAFT_ZONE, 0, 1);
		}
		if(npc.getId() == 1864 && drop.getId() == 17660) {
			ZoneTasks.doTaskProgress(player, ZoneTasks.ZoneData.MINECRAFT_ZONE, 1, 1);
		}
		if(npc.getId() == 1864 && drop.getId() == 15078) {
			ZoneTasks.doTaskProgress(player, ZoneTasks.ZoneData.MINECRAFT_ZONE, 2, 1);
		}
	}

	public static void handleDrops(Player player, NPC npc) {
		int npcId = npc.getId();

		NpcDrop[] drops = npcDrops.get(npcId);
		if (drops == null) {
			return;
		}

		Integer kc = player.getDropKillCount(npcId);
		for (NpcDrop drop : drops) {
			if (drop == null)
				continue;

			int divisor = (int) (drop.getChance() - drop.getChance() * player.getAttributes().calculateBonusDropRate(npcId)/10);
			int dropId = Misc.random(divisor);
			if (drop.getChance() == 1) {
				constantDrops.add(drop);
			} else {
				if (dropId == divisor) {
					potentialDrops.add(drop);
				}
			}
		}
		if (!constantDrops.isEmpty()) {
			finalDropList.addAll(constantDrops);
		}
		if (!potentialDrops.isEmpty()) {
			finalDropList.add(potentialDrops.get(Misc.randomMinusOne(potentialDrops.size())));
		}
		if (!finalDropList.isEmpty()) {
			for (NpcDrop drop : finalDropList) {
				int amount = drop.getCount()[0];
				if (drop.getCount().length > 1) {
					amount = drop.getCount()[0] + new SecureRandom().nextInt(drop.getCount()[1] - drop.getCount()[0]);
				}
				if(Misc.random(99) == getDoubleDropChance(player)) {
					amount*=2;
				}
				handleDropTasks(player, npc, drop);
				boolean received = false;
				if(player.getEquipment().get(Equipment.AMULET_SLOT).getId() == 1009 || player.getEquipment().get(Equipment.AMULET_SLOT).getId() == 7803 || player.getEquipment().get(Equipment.AMULET_SLOT).getId() == 589 || player.getEquipment().get(Equipment.AMULET_SLOT).getId() == 11195) {
					final Item toAdd = new Item(drop.getId(), amount);


					for(Bank bank: player.getBanks()) {
						if(Objects.nonNull(bank) && !received & !bank.isFull() && !bank.isEmpty()) {
							if (toAdd.getId() != 995) {
								if(toAdd.getDefinition().isNoted()) {
									bank.add(new Item(toAdd.getId()-1, amount)).refreshItems();
									player.sendMessage("@red@Added "+toAdd.getAmount()+"x "+toAdd.getDefinition().getName()+" to your bank.");
									received = true;
								} else {
									bank.add(toAdd).refreshItems();
									player.sendMessage("@red@Added "+toAdd.getAmount()+"x "+toAdd.getDefinition().getName()+" to your bank.");
									received = true;
								}
							}
						}
					}
					if(!received) {
						int slotsOccupied = toAdd.getDefinition().isStackable() || toAdd.getDefinition().isNoted()? 1 : toAdd.getAmount();
						if(player.getInventory().getFreeSlots() >= slotsOccupied) {
							player.getInventory().add(toAdd).refreshItems();
							player.sendMessage("@red@Added "+toAdd.getAmount()+"x "+toAdd.getDefinition().getName()+" to your inventory.");
							received = true;
						}
					}
				}
				if(!received) {
					GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(drop.getId(), amount),
							npc.getPosition().copy(), player.getUsername(), false, 150, false, 200));
					DropLog.submit(player, new DropLogEntry(drop.getId(), amount));
				}
				if (DropAnnouncer.shouldAnnounce(drop)) {
					DropAnnouncer.announce(player, drop.getId(), npcId, amount, drop.getChance());
				}
			}
			finalDropList.clear();
			constantDrops.clear();
			potentialDrops.clear();
		}
	}

	public static class NpcDrop {
		private int id;
		private int[] count;
		private int chance;

		public NpcDrop(int id, int[] count, int chance) {
			this.setId(id);
			this.setCount(count);
			this.setChance(chance);
		}

		/**
		 * Sets the id
		 *
		 * @return the id
		 */
		public int getId() {
			return id;
		}

		/**
		 * Sets the id
		 *
		 * @param id
		 *            the id
		 */
		public void setId(int id) {
			this.id = id;
		}

		/**
		 * Sets the count
		 *
		 * @return the count
		 */
		public int[] getCount() {
			return count;
		}

		/**
		 * Sets the count
		 *
		 * @param count
		 *            the count
		 */
		public void setCount(int[] count) {
			this.count = count;
		}

		/**
		 * Sets the chance
		 *
		 * @return the chance
		 */
		public int getChance() {
			return chance;
		}

		/**
		 * Sets the chance
		 *
		 * @param chance
		 *            the chance
		 */
		public void setChance(int chance) {
			this.chance = chance;
		}
	}

	public static void init() {
		new JsonLoader() {

			@Override
			public String filePath() {
				return "./data/def/json/drops.json";
			}

			@Override // NOTE: load cycles through each object.
			public void load(JsonObject reader, Gson builder) {

				final int[] id = builder.fromJson(reader.get("npcIds"), int[].class);
				final NpcDrop[] dynamic = builder.fromJson(reader.get("drops"), NpcDrop[].class);

				for (int ids : id) {
					npcDrops.put(ids, dynamic);
				}
			}
		}.load();
	}

	public static Object getDrops() {
		return null;
	}
}