package com.ruthlessps.world.entity;

import com.ruthlessps.engine.task.TaskManager;
import com.ruthlessps.model.GameObject;
import com.ruthlessps.net.PlayerSession;
import com.ruthlessps.net.SessionState;
import com.ruthlessps.world.World;
import com.ruthlessps.world.clip.region.RegionClipping;
import com.ruthlessps.world.content.CustomObjects;
import com.ruthlessps.world.content.gambling.GamblingManager;
import com.ruthlessps.world.content.gambling.GamblingManager.GambleStage;
import com.ruthlessps.world.entity.impl.npc.NPC;
import com.ruthlessps.world.entity.impl.player.Player;

public class EntityHandler {

	public static void deregister(Entity entity) {
		if (entity.isPlayer()) {
			Player player = (Player) entity;
			if (!player.getGambling().getStage().equals(GambleStage.OFFLINE)) {
				GamblingManager.autoDisconnectWin(player);
			}
			World.getPlayers().remove(player);
		} else if (entity.isNpc()) {
			NPC npc = (NPC) entity;
			TaskManager.cancelTasks(npc.getCombatBuilder());
			TaskManager.cancelTasks(entity);
			World.getNpcs().remove(npc);
		} else if (entity.isGameObject()) {
			GameObject gameObject = (GameObject) entity;
			RegionClipping.removeObject(gameObject);
			CustomObjects.deleteGlobalObjectWithinDistance(gameObject);
		}
	}

	public static void register(Entity entity) {
		if (entity.isPlayer()) {
			Player player = (Player) entity;
			PlayerSession session = player.getSession();
			if (session.getState() == SessionState.LOGGING_IN && !World.getLoginQueue().contains(player)) {
				World.getLoginQueue().add(player);
			}
		}
		if (entity.isNpc()) {
			NPC npc = (NPC) entity;
			World.getNpcs().add(npc);
		} else if (entity.isGameObject()) {
			GameObject gameObject = (GameObject) entity;
			CustomObjects.spawnGlobalObjectWithinDistance(gameObject);
			if(gameObject.getId() == 0) {
				return;
			}
			RegionClipping.addObject(gameObject);
		}
	}
}
