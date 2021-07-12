package com.ruthlessps.world.content.skill.impl.slayer;

import com.ruthlessps.model.Position;
import com.ruthlessps.model.Skill;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.content.PlayerPanel;
import com.ruthlessps.world.entity.impl.player.Player;

public enum SlayerMaster {

	VANNAKA(1, 1, 1597, new Position(3249, 9487)),
	DURADEL(60, 1, 8275, new Position(3249, 9487)),
	KURADEL(90, 2, 9085,new Position(3249, 9487)),
	SUMONA(100, 5, 7780, new Position(3249, 9487)),
	BEGINNER_MASTER(1, 0, 4566, new Position(3249, 9487)),
	INTERMEDIATE_MASTER(50, 0, 4549, new Position(3249, 9487)),
	HEROIC_MASTER(75, 0, 4568, new Position(3249, 9487)),
	ELITE_MASTER(85, 0, 4567, new Position(3249, 9487)),
	GOD_MASTER(95, 1, 4550, new Position(3249, 9487));

	public static void changeSlayerMaster(Player player, SlayerMaster master) {
		player.getPacketSender().sendInterfaceRemoval();
		if (player.getSkillManager().getCurrentLevel(Skill.SLAYER) < master.getSlayerReq()) {
			player.getPacketSender()
					.sendMessage("<img=10><col=FF7400><shad=0>This Slayer master does not teach noobies. You need a Slayer level of at least "
							+ master.getSlayerReq() + ".");
			return;
		}
		if (player.getSlayerPrestige() < master.getPrestigeReq()) {
			player.getPacketSender()
					.sendMessage("<img=10><col=FF7400><shad=0>This Slayer master does not teach noobies. You need to prestige Slayer at least "
							+ master.getPrestigeReq() + " times");
			return;
		}
		if(player.getSlayer().getSlayerMaster() == SlayerMaster.GOD_MASTER && !player.getGroupName().equals("NONE")) {
			player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>Please quit your group before doing that!");
			return;
		}
		if (player.getSlayer().getSlayerTask() != SlayerTasks.NO_TASK) {
			player.getPacketSender().sendMessage("Please finish your current task before changing Slayer master.");
			return;
		}
		if (player.getSlayer().getSlayerMaster() == master) {
			player.getPacketSender().sendMessage(
					"" + Misc.formatText(master.toString().toLowerCase()) + " is already your Slayer master.");
			return;
		}
		player.getSlayer().setSlayerMaster(master);
		PlayerPanel.refreshPanel(player);
		player.getPacketSender().sendMessage("You've sucessfully changed your Slayer master.");
	}

	public static SlayerMaster forId(int id) {
		for (SlayerMaster master : SlayerMaster.values()) {
			if (master.ordinal() == id) {
				return master;
			}
		}
		return null;
	}

	private int slayerReq;
	private int prestigeReq;
	private int npcId;

	private Position position;

	private SlayerMaster(int slayerReq, int prestigeReq, int npcId, Position telePosition) {
		this.slayerReq = slayerReq;
		this.npcId = npcId;
		this.position = telePosition;
		this.prestigeReq = prestigeReq;
	}

	public int getNpcId() {
		return this.npcId;
	}

	public Position getPosition() {
		return this.position;
	}

	public int getSlayerReq() {
		return this.slayerReq;
	}
	public int getPrestigeReq() {
		return this.prestigeReq;
	}
}