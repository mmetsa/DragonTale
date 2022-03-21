package com.ruthlessps.world.content.minigames.impl;

import java.util.Objects;

import com.ruthlessps.engine.task.Task;
import com.ruthlessps.engine.task.TaskManager;
import com.ruthlessps.model.GroundItem;
import com.ruthlessps.model.Item;
import com.ruthlessps.model.Locations.Location;
import com.ruthlessps.model.Position;
import com.ruthlessps.model.RegionInstance;
import com.ruthlessps.model.RegionInstance.RegionInstanceType;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.World;
import com.ruthlessps.world.content.Achievements;
import com.ruthlessps.world.content.Achievements.AchievementData;
import com.ruthlessps.world.content.dialogue.DialogueManager;
import com.ruthlessps.world.entity.impl.GroundItemManager;
import com.ruthlessps.world.entity.impl.npc.NPC;
import com.ruthlessps.world.entity.impl.player.Player;

public class Legio {


	public static void leave(Player player) {
		player.getCombatBuilder().reset(true);
		player.moveTo(new Position(2903, 2708, 0));
		if (player.getRegionInstance() != null)
			player.getRegionInstance().destruct();
		player.restart();
	}
	
	public static void end(Player player) {
		if (player.getRegionInstance() != null)
			player.getRegionInstance().destruct();
	}


	public static void start(Player player) {
		/*if(Objects.nonNull(player.getSummoning().getFamiliar())){
			player.sendMessage("<img=10><col=FF0000><shad=0>You must first dismiss your familiar");
			return;
		}*/
		player.getPacketSender().sendInterfaceRemoval();
		player.moveTo(new Position(2397, 4697, player.getIndex() * 4));
		player.setRegionInstance(new RegionInstance(player, RegionInstanceType.LEGIO));
		NPC n = new NPC(7164, new Position(2397, 4705, player.getPosition().getZ())).setSpawnedFor(player);
		World.register(n);
		player.getRegionInstance().getNpcsList().add(n);
	}
}
