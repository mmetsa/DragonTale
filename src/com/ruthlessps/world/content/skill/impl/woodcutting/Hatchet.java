package com.ruthlessps.world.content.skill.impl.woodcutting;

import java.util.HashMap;
import java.util.Map;

import com.ruthlessps.model.Skill;
import com.ruthlessps.model.container.impl.Equipment;
import com.ruthlessps.world.entity.impl.player.Player;

public enum Hatchet {
	BRONZE(1351, 1, 879, 1.0), IRON(1349, 1, 877, 1.3), STEEL(1353, 6, 875, 1.5), BLACK(1361, 6, 873, 1.7), MITHRIL(
			1355, 21, 871, 1.9), ADAMANT(1357, 31, 869,
					2), RUNE(1359, 41, 867, 2.2), DRAGON(6739, 61, 2846, 2.28), ADZE(13661, 80, 10227, 2.5), BEGINNER(16361, 50, 867, 2.2),
	ADVANCED(16363, 60, 867, 2.2),ELITE(16365, 70, 867, 2.2),MASTER(16367, 80, 867, 2.2);

	public static Map<Integer, Hatchet> hatchets = new HashMap<Integer, Hatchet>();

	static {
		for (Hatchet hatchet : Hatchet.values()) {
			hatchets.put(hatchet.getId(), hatchet);
		}
	}

	public static Hatchet forId(int id) {
		return hatchets.get(id);
	}

	private int id, req, anim;

	private double speed;

	private Hatchet(int id, int level, int animation, double speed) {
		this.id = id;
		this.req = level;
		this.anim = animation;
		this.speed = speed;
	}

	public int getAnim() {
		return anim;
	}

	public int getId() {
		return id;
	}

	public int getRequiredLevel() {
		return req;
	}

	public double getSpeed() {
		return speed;
	}

	public static int getHatchet(Player p) {
		for (Hatchet h : values()) {
			if (p.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId() == h.getId()) {
				return h.getId();
			} else if (p.getInventory().contains(h.getId())) {
				return h.getId();
			}
		}
		return -1;
	}

	public static int getChopTimer(Player player, Hatchet h) {
		int skillReducement = (int) (player.getSkillManager().getMaxLevel(Skill.WOODCUTTING) * 0.05);
		int axeReducement = (int) h.getSpeed();
		return skillReducement + axeReducement;
	}
}