package com.ruthlessps.world.content.skill.impl.fishing;

import com.ruthlessps.engine.task.Task;
import com.ruthlessps.engine.task.TaskManager;
import com.ruthlessps.model.Animation;
import com.ruthlessps.model.Direction;
import com.ruthlessps.model.GameMode;
import com.ruthlessps.model.Skill;
import com.ruthlessps.model.definitions.ItemDefinition;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.content.Achievements;
import com.ruthlessps.world.content.Achievements.AchievementData;
import com.ruthlessps.world.entity.impl.player.Player;

public class Fishing {

	public enum Spot {

		LURE(318, new int[] { 335, 331 }, 309, 314, new int[] { 20, 30 }, true, new int[] { 1550, 2120 }, 623),

		CAGE(312, new int[] { 377 }, 301, -1, new int[] { 40 }, false, new int[] { 2980 }, 619),

		BIGNET(313, new int[] { 353, 341, 363 }, 305, -1, new int[] { 16, 23, 46 }, false,
				new int[] { 780, 1130, 2120 }, 620),

		SMALLNET(316, new int[] { 317, 321 }, 303, -1, new int[] { 1, 15 }, false, new int[] { 290, 550 }, 621),

		MONK_FISH(318, new int[] { 7944, 389 }, 305, -1, new int[] { 62, 81 }, false, new int[] { 6985, 10122 }, 621),

		HARPOON(312, new int[] { 359, 371 }, 311, -1, new int[] { 35, 50 }, true, new int[] { 2530, 5100 }, 618),

		HARPOON2(313, new int[] { 383 }, 311, -1, new int[] { 76 }, true, new int[] { 8547 }, 618),

		BAIT(316, new int[] { 327, 345 }, 307, 313, new int[] { 5, 10 }, true, new int[] { 650, 980 }, 623),

		ROCKTAIL(10091, new int[] { 15270 }, 309, 25, new int[] { 91 }, false, new int[] { 18010 }, 623),
		AFK(2026, new int[] {6204}, 303, -1, new int[] {1}, false, new int[] {100}, 621);

		int npcId, equipment, bait, anim;
		int[] rawFish, fishingReqs, xp;
		boolean second;

		private Spot(int npcId, int[] rawFish, int equipment, int bait, int[] fishingReqs, boolean second, int[] xp,
				int anim) {
			this.npcId = npcId;
			this.rawFish = rawFish;
			this.equipment = equipment;
			this.bait = bait;
			this.fishingReqs = fishingReqs;
			this.second = second;
			this.xp = xp;
			this.anim = anim;
		}

		public int getAnim() {
			return anim;
		}

		public int getBait() {
			return bait;
		}

		public int getEquipment() {
			return equipment;
		}

		public int[] getLevelReq() {
			return fishingReqs;
		}

		public int getNPCId() {
			return npcId;
		}

		public int[] getRawFish() {
			return rawFish;
		}

		public boolean getSecond() {
			return second;
		}

		public int[] getXp() {
			return xp;
		}
	}

	public static Spot forSpot(int npcId, boolean secondClick) {
		for (Spot s : Spot.values()) {
			if (secondClick) {
				if (s.getSecond()) {
					if (s.getNPCId() == npcId) {
						if (s != null) {
							return s;
						}
					}
				}
			} else {
				if (s.getNPCId() == npcId && !s.getSecond()) {
					if (s != null) {
						return s;
					}
				}
			}
		}
		return null;
	}

	private static int getDelay(int req) {
		int timer = 1;
		timer += req * 0.08;
		return timer;
	}

	public static int getMax(Player p, int[] reqs) {
		int tempInt = -1;
		for (int i : reqs) {
			if (p.getSkillManager().getCurrentLevel(Skill.FISHING) >= i) {
				tempInt++;
			}
		}
		return tempInt;
	}

	public static void setupFishing(Player p, Spot s) {
		if (s == null)
			return;
		if (p.getInventory().getFreeSlots() <= 0) {
			p.getPacketSender().sendMessage("You do not have any free inventory space.");
			p.getSkillManager().stopSkilling();
			return;
		}
		if (p.getSkillManager().getCurrentLevel(Skill.FISHING) >= s.getLevelReq()[0]) {
			if (p.getInventory().contains(s.getEquipment())) {
				if (s.getBait() != -1) {
					if (p.getInventory().contains(s.getBait())) {
						startFishing(p, s);
					} else {
						String baitName = ItemDefinition.forId(s.getBait()).getName();
						if (baitName.contains("Feather") || baitName.contains("worm"))
							baitName += "s";
						p.getPacketSender().sendMessage("You need some " + baitName + " to fish here.");
						p.performAnimation(new Animation(65535));
					}
				} else {
					startFishing(p, s);
				}
			} else {
				String def = ItemDefinition.forId(s.getEquipment()).getName().toLowerCase();
				p.getPacketSender().sendMessage("You need " + Misc.anOrA(def) + " " + def + " to fish here.");
			}
		} else {
			p.getPacketSender()
					.sendMessage("You need a fishing level of at least " + s.getLevelReq()[0] + " to fish here.");
		}
	}

	public static void startFishing(final Player p, final Spot s) {
		p.getSkillManager().stopSkilling();
		final int fishIndex = Misc.getRandom(100) >= 70 ? getMax(p, s.fishingReqs)
				: (getMax(p, s.fishingReqs) != 0 ? getMax(p, s.fishingReqs) - 1 : 0);
		if (p.getInteractingObject() != null && p.getInteractingObject().getId() != 8702)
			p.setDirection(s == Spot.MONK_FISH ? Direction.WEST : Direction.NORTH);
		p.performAnimation(new Animation(s.getAnim()));
		p.setCurrentTask(new Task(2, p, false) {
			int cycle = 0, reqCycle = Fishing.getDelay(s.getLevelReq()[fishIndex]);

			@Override
			public void execute() {
				if (p.getInventory().getFreeSlots() == 0) {
					p.getPacketSender().sendMessage("You have run out of inventory space.");
					stop();
					return;
				}
				if (!p.getInventory().contains(s.getBait())) {
					stop();
					return;
				}
				cycle++;
				p.performAnimation(new Animation(s.getAnim()));
				if (cycle >= Misc.getRandom(1) + reqCycle) {
					String def = ItemDefinition.forId(s.getRawFish()[fishIndex]).getName();
					if (def.endsWith("s"))
						def = def.substring(0, def.length() - 1);
					p.getPacketSender().sendMessage(
							"You catch " + Misc.anOrA(def) + " " + def.toLowerCase().replace("_", " ") + ".");
					if (s.getBait() != -1)
						p.getInventory().delete(s.getBait(), 1);
					if(p.getGameMode() == GameMode.HARDCORE && p.getEquipment().contains(9798)) {
						p.getInventory().add(s.getRawFish()[fishIndex], 2);
					} else if(p.getInventory().contains(15750) && Misc.random(1) == 0) {
						p.getInventory().add(s.getRawFish()[fishIndex], 2);
					} else  {
						p.getInventory().add(s.getRawFish()[fishIndex], 1);
					}
					Achievements.doProgress(p, AchievementData.CATCH_SOME_FISH);
					Achievements.doProgress(p, AchievementData.CATCH_500_FISH);
					Achievements.doProgress(p, AchievementData.CATCH_50_FISH);
					p.getSkillManager().addExperience(Skill.FISHING, s.getXp()[fishIndex]);
					setupFishing(p, s);
					setEventRunning(false);
				}
			}

			@Override
			public void stop() {
				setEventRunning(false);
				p.performAnimation(new Animation(65535));
			}
		});

		TaskManager.submit(p.getCurrentTask());
	}

}
