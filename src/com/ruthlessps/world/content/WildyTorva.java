package com.ruthlessps.world.content;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.ruthlessps.engine.task.Task;
import com.ruthlessps.engine.task.TaskManager;
import com.ruthlessps.model.GroundItem;
import com.ruthlessps.model.Item;
import com.ruthlessps.model.Position;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.World;
import com.ruthlessps.world.content.combat.CombatBuilder.CombatDamageCache;
import com.ruthlessps.world.content.combat.CombatFactory;
import com.ruthlessps.world.entity.impl.GroundItemManager;
import com.ruthlessps.world.entity.impl.npc.NPC;
import com.ruthlessps.world.entity.impl.player.Player;

/**
 * 
 * @author Miscellania
 *
 */
public class WildyTorva extends NPC {
	
	
	public static int[] COMMONLOOT = {13664};
	public static int[] MEDIUMLOOT = {19670};
	public static int[] RARELOOT = {15501, 2801};
	public static int[] SUPERRARELOOT = {19658, 19663, 19660, 19662, 19659, 19665, 601};
	public static WildyTorvaLocation location;
	
	/**
	 * 
	 */
	public static final int NPC_ID = 585;

	/**
	 * 
	 */
	public static final WildyTorvaLocation[] LOCATIONS = {
			new WildyTorvaLocation(3304, 3931, 0, "Rogue's Castle"), // done
			new WildyTorvaLocation(3237, 3762, 0, "Bone Yard"),
			new WildyTorvaLocation(3145, 3888, 0, "Spider Hill"),
			new WildyTorvaLocation(3186, 3691, 0, "Graveyard")
	};
	
	/**
	 * 
	 */
	private static WildyTorva current;
	
	/**
	 * 
	 * @param position
	 */
	public WildyTorva(Position position) {
		
		super(NPC_ID, position);
		
		//setConstitution(96500/3); //7,650
		//setDefaultConstitution(96500);
		
	}
	
	/**
	 * 
	 */
	public static void initialize() {

		TaskManager.submit(new Task(3000, false) { //6000
			
			@Override
			public void execute() {
				spawn();
			}
			
		});
	
	}

	/**
	 * 
	 */
	public static void spawn() {
		
		if(getCurrent() != null) {
			return;
		}
		location = Misc.randomElement(LOCATIONS);
		WildyTorva instance = new WildyTorva(location.copy());
		//System.out.println(instance.getPosition());

		World.register(instance);
		setCurrent(instance);
		//System.out.print("spawned.");
		
		World.sendMessage("<img=10> @blu@[DragonTale God]@red@ A DragonTale God has spawned at "+location.getLocation()+"!");
		World.sendMessage("<img=10> @red@Drops : DragonTale god armor, ray gun, DragonTale god pet, mbox, mmbox,bucks.");

	}

	/**
	 * 
	 * @param npc
	 */
	public static void handleDrops(NPC npc) {
		System.out.println(npc.getCombatBuilder().getDamageMap().size()+ " people got drops from NPC ID: "+npc.getId());
		if (npc.getCombatBuilder().getDamageMap().size() == 0) {
			return;
		}
		Map<Player, Integer> killers = new HashMap<>();

		for (Entry<Player, CombatDamageCache> entry : npc.getCombatBuilder().getDamageMap().entrySet()) {

			if (entry == null) {
				continue;
			}

			long timeout = entry.getValue().getStopwatch().elapsed();

			if (timeout > CombatFactory.DAMAGE_CACHE_TIMEOUT) {
				continue;
			}

			Player player = entry.getKey();

			if (player.getConstitution() <= 0 || !player.isRegistered()) {
				continue;
			}

			killers.put(player, entry.getValue().getDamage());

		}

		npc.getCombatBuilder().getDamageMap().clear();

		List<Entry<Player, Integer>> result = sortEntries(killers);
		int count = 0;
		if(result.size() < 6) {
			for (Entry<Player, Integer> entry : result) {
				Player killer = entry.getKey();
				int damage = entry.getValue();
				com.ruthlessps.drops.NPCDrops.handleDrops(killer, npc);
				killer.increaseDKC(npc.getId());
				if (++count >= 3) {
					break;
				}
			}
		} else {
			for (Entry<Player, Integer> entry : result) {
				Player killer = entry.getKey();
				int damage = entry.getValue();
				com.ruthlessps.drops.NPCDrops.handleDrops(killer, npc);
				killer.increaseDKC(npc.getId());
			}
		}

	}
	public static void handleDrop(NPC npc) {

		setCurrent(null);

		if (npc.getCombatBuilder().getDamageMap().size() == 0) {
			return;
		}

		Map<Player, Integer> killers = new HashMap<>();

		for (Entry<Player, CombatDamageCache> entry : npc.getCombatBuilder().getDamageMap().entrySet()) {

			if (entry == null) {
				continue;
			}

			long timeout = entry.getValue().getStopwatch().elapsed();

			if (timeout > CombatFactory.DAMAGE_CACHE_TIMEOUT) {
				continue;
			}

			Player player = entry.getKey();

			if (player.getConstitution() <= 0 || !player.isRegistered()) {
				continue;
			}

			killers.put(player, entry.getValue().getDamage());

		}

		npc.getCombatBuilder().getDamageMap().clear();

		List<Entry<Player, Integer>> result = sortEntries(killers);
		int count = 0;

		for (Entry<Player, Integer> entry : result) {

			Player killer = entry.getKey();
			int damage = entry.getValue();

			handleDrop(npc, killer, damage);

			if (++count >= 5) {
				break;
			}

		}

	}
	
	public static void giveLoot(Player player, NPC npc, Position pos) {
		int chance = Misc.getRandom(100);
		int common = COMMONLOOT[Misc.getRandom(COMMONLOOT.length - 1)];
		int medium = MEDIUMLOOT[Misc.getRandom(MEDIUMLOOT.length - 1)];
		int rare = RARELOOT[Misc.getRandom(RARELOOT.length - 1)];
		int superrare = SUPERRARELOOT[Misc.getRandom(SUPERRARELOOT.length - 1)];
		
		
		//player.getPacketSender().sendMessage("chance: "+chance);
		GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(995, 7500000 + Misc.getRandom(15000000)), pos, player.getUsername(), false, 150, true, 200));
		GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(931, 1 + Misc.getRandom(4)), pos, player.getUsername(), false, 150, true, 200));

		if(chance > 500){
			//super rare
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(SUPERRARELOOT[Misc.getRandom(SUPERRARELOOT.length - 1)], 1), pos, player.getUsername(), false, 150, true, 200));
			String itemName = (new Item(superrare).getDefinition().getName());
			String itemMessage = Misc.anOrA(itemName) + " " + itemName;
			World.sendMessage(
					"<img=10><col=FF0000>" + player.getUsername() + " received " + itemMessage + " from the Wildywyrm!");
			return;
		}
		if(chance > 300) {
			//rare
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(RARELOOT[Misc.getRandom(RARELOOT.length - 1)], 1), pos, player.getUsername(), false, 150, true, 200));
			String itemName = (new Item(rare).getDefinition().getName());
			String itemMessage = Misc.anOrA(itemName) + " " + itemName;
			World.sendMessage(
					"<img=10><col=FF0000>" + player.getUsername() + " received " + itemMessage + " from the Wildywyrm!");
			return;
		} 
		if(chance > 100) {
			//medium
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(MEDIUMLOOT[Misc.getRandom(MEDIUMLOOT.length - 1)], 2), pos, player.getUsername(), false, 150, true, 200));
			String itemName = (new Item(medium).getDefinition().getName());
			String itemMessage = Misc.anOrA(itemName) + " " + itemName;
			//World.sendMessage(
					//"<img=10><col=FF0000>" + player.getUsername() + " received " + itemMessage + " from the Wildywyrm!");
			return;
		} 
		if(chance >= 0){
			//common
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(COMMONLOOT[Misc.getRandom(COMMONLOOT.length - 1)], 200), pos, player.getUsername(), false, 150, true, 200));
			String itemName = (new Item(common).getDefinition().getName());
			String itemMessage = Misc.anOrA(itemName) + " " + itemName;
			//World.sendMessage(
				//	"<img=10><col=FF0000>" + player.getUsername() + " received " + itemMessage + " from the Wildywyrm!");
			return;
		} 
	

		
	}
	
	
	/**
	 * 
	 * @param npc
	 * @param player
	 * @param damage
	 */
	public static void handleDrop(NPC npc, Player player, int damage) {
		Position pos = npc.getPosition();
		giveLoot(player, npc, pos);
	}
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	public static <K, V extends Comparable<? super V>> List<Entry<K, V>> sortEntries(Map<K, V> map) {

		List<Entry<K, V>> sortedEntries = new ArrayList<Entry<K, V>>(map.entrySet());

		Collections.sort(sortedEntries, new Comparator<Entry<K, V>>() {
			
			@Override
			public int compare(Entry<K, V> e1, Entry<K, V> e2) {
				return e2.getValue().compareTo(e1.getValue());
			}
			
		});

		return sortedEntries;
		
	}

	/**
	 * 
	 * @return
	 */
	public static WildyTorva getCurrent() {
		return current;
	}

	/**
	 * 
	 * @param current
	 */
	public static void setCurrent(WildyTorva current) {
		WildyTorva.current = current;
	}
	
	/**
	 * 
	 * @author Nick Hartskeerl <apachenick@hotmail.com>
	 *
	 */
	public static class WildyTorvaLocation extends Position {
		
		/**
		 * 
		 */
		private String location;
		
		/**
		 * 
		 * @param x
		 * @param y
		 * @param z
		 * @param location
		 */
		public WildyTorvaLocation(int x, int y, int z, String location) {
			super(x, y, z);
			setLocation(location);
		}

		/**
		 * 
		 * @return
		 */
	
		
		public String getLocation() {
			return location;
		}

		/**
		 * 
		 * @param location
		 */
		public void setLocation(String location) {
			this.location = location;
		}
		
	}

	
	
}
