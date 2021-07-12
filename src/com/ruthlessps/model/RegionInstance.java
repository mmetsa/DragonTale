package com.ruthlessps.model;

import java.util.concurrent.CopyOnWriteArrayList;

import com.ruthlessps.world.World;
import com.ruthlessps.world.entity.impl.Character;
import com.ruthlessps.world.entity.impl.npc.NPC;
import com.ruthlessps.world.entity.impl.player.Player;

/**
 * Handles a custom region instance for a player
 * 
 * @author Gabriel
 */
public class RegionInstance {

	public enum RegionInstanceType {
		BARROWS, LEGIO, GRAVEYARD, FIGHT_CAVE, WARRIORS_GUILD, NOMAD, RECIPE_FOR_DISASTER, CONSTRUCTION_HOUSE, CONSTRUCTION_DUNGEON;
	}

	private Player owner;
	private RegionInstanceType type;
	private CopyOnWriteArrayList<NPC> npcsList;
	private CopyOnWriteArrayList<Player> playersList;

	public RegionInstance(Player p, RegionInstanceType type) {
		this.owner = p;
		this.type = type;
		this.npcsList = new CopyOnWriteArrayList<NPC>();
		if (type == RegionInstanceType.CONSTRUCTION_HOUSE) {
			this.playersList = new CopyOnWriteArrayList<Player>();
		}
	}

	public void add(Character c) {
		if (type == RegionInstanceType.CONSTRUCTION_HOUSE) {
			if (c.isPlayer()) {
				playersList.add((Player) c);
			} else if (c.isNpc()) {
				npcsList.add((NPC) c);
			}

			if (c.getRegionInstance() == null || c.getRegionInstance() != this) {
				c.setRegionInstance(this);
			}
		}
	}

	public void destruct() {
		for (NPC n : npcsList) {
			if (n != null && n.getConstitution() > 0 && World.getNpcs().get(n.getIndex()) != null
					&& n.getSpawnedFor() != null && n.getSpawnedFor().getIndex() == owner.getIndex() && !n.isDying()) {
				World.deregister(n);
			}
		}
		npcsList.clear();
		owner.setRegionInstance(null);
	}

	@Override
	public boolean equals(Object other) {
		return (RegionInstanceType) other == type;
	}

	public CopyOnWriteArrayList<NPC> getNpcsList() {
		return npcsList;
	}

	public Player getOwner() {
		return owner;
	}

	public CopyOnWriteArrayList<Player> getPlayersList() {
		return playersList;
	}

	public RegionInstanceType getType() {
		return type;
	}

	public void remove(Character c) {
		if (type == RegionInstanceType.CONSTRUCTION_HOUSE) {
			if (c.isPlayer()) {
				playersList.remove(c);
				if (owner == ((Player) c)) {
					destruct();
				}
			} else if (c.isNpc()) {
				npcsList.remove(c);
			}

			if (c.getRegionInstance() != null && c.getRegionInstance() == this) {
				c.setRegionInstance(null);
			}
		}
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}
}
