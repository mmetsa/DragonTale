package com.ruthlessps.world.content.skill.impl.slayer;

import com.ruthlessps.model.Locations;
import com.ruthlessps.model.Skill;
import com.ruthlessps.model.definitions.NpcDefinition;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.World;
import com.ruthlessps.world.content.Achievements;
import com.ruthlessps.world.content.PlayerPanel;
import com.ruthlessps.world.content.Achievements.AchievementData;
import com.ruthlessps.world.content.dialogue.DialogueManager;
import com.ruthlessps.world.entity.impl.npc.NPC;
import com.ruthlessps.world.entity.impl.player.Player;

public class GroupSlayer {
	
	
	public static boolean checkGroupSlayer(Player player, int amount) {
		if(player.isGroupLeader() && amount >= 3) {
			return true;
		}
		return false;
	}






	private int amountToSlay;
	private SlayerTasks slayerTask;
	public int checkGroupSize() {
		return player.getGroupSlayer().getGroupSize();
	}
	
	
	
	
	public int getGroupSize() {
		return this.groupSize;
	}
	public void setGroupSize(int size) {
		this.groupSize = size;
	}
	private void incrementGroupSize() {
		this.groupSize += 1;
	}
	
	public void createGroup() {
		if(player.isGroupLeader() == false && player.getGroupName().equals("NONE")) {
			player.setGroupLeader(true);
			player.setGroupName(player.getUsername());
			player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You have successfully created a Slayer Group! Use the Gem on others to invite them!");
			Achievements.doProgress(player, AchievementData.CREATE_SLAYER_GROUP);
			PlayerPanel.refreshPanel(player);
		} else {
			player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You are already in a Slayer Group!");
		}
	}
	
	public void deleteGroup() {
		if(player.isGroupLeader() && !(player.getGroupName().equals("NONE"))) {
			for(Player targets:World.getPlayers()) {
				if(targets != null) {
					if(targets.getGroupName().equals(player.getUsername())) {
						targets.setGroupLeader(false);
						targets.setGroupName("NONE");
						targets.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>Your Slayer Group has been deleted!");
						targets.getSlayer().setSlayerTask(SlayerTasks.NO_TASK);
						targets.getSlayer().setAmountToSlay(0);
						PlayerPanel.refreshPanel(targets);
						setGroupSize(1);
					}
				}
			}
		} else {
			player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You are not a group leader!");
		}
	}
	
	public void quitGroup() {
		if(!player.getGroupName().equals("NONE")) {
			player.getSlayer().setAmountToSlay(0);
			player.getSlayer().setSlayerTask(SlayerTasks.NO_TASK);
			for(Player groupie: World.getPlayers()) {
				if(groupie != null) {
					if(groupie.getGroupName().equals(player.getGroupName())) {
						groupie.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>"+player.getUsername()+" has quit the Slayer Group!");
						PlayerPanel.refreshPanel(groupie);
						if(groupie.isGroupLeader()) {
							groupie.getGroupSlayer().setGroupSize(groupie.getGroupSlayer().getGroupSize()-1);
						}
					}
				}
			}
			player.setGroupName("NONE");
		}
	}
	
	public void groupInvitation(boolean accept) {
		Player target = World.getPlayerByName(invitation);
		if(!accept) {
			player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You declined the invitation!");
			target.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>"+player.getUsername()+" has declined your invitation!");
			return;
		}
		if(player == null) {
			return;
		}
		if(target == null) {
			return;
		}
		if(!(player.getGroupName().equals("NONE"))) {
			player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You are already in a Slayer Group!");
			return;
		}
		if(player.getSlayer().getDuoPartner() != null||target.getSlayer().getDuoPartner() != null) {
			player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>Neither of you can have a duo partner!");
			target.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>Neither of you can have a duo partner!");
			return;
		}
		if(player.getSlayer().getSlayerTask() != SlayerTasks.NO_TASK) {
			target.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>Your target already has a task!");
			return;
		}
		if(player.getSlayer().getSlayerMaster() != SlayerMaster.GOD_MASTER) {
			player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to have the GOD Slayer Master!");
			return;
		}
		if(target.getSlayer().getSlayerMaster() != SlayerMaster.GOD_MASTER) {
			target.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to have the GOD Slayer Master to invite anyone!");
			return;
		}
		target.getGroupSlayer().incrementGroupSize();
		player.setGroupName(target.getUsername());
		player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You have joined "+ target.getUsername() + "'s Slayer Group!");
		player.getSlayer().setSlayerTask(target.getSlayer().getSlayerTask());
		player.getSlayer().setAmountToSlay(target.getSlayer().getAmountToSlay());
		PlayerPanel.refreshPanel(target);
		PlayerPanel.refreshPanel(player);
		
	}
	

	
	public void assignGroupTask() {
		player.getPacketSender().sendInterfaceRemoval();
		if (player.getSlayer().getSlayerTask() != SlayerTasks.NO_TASK) {
			player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>Your Group already has a Slayer task.");
			return;
		}
		if (!player.isGroupLeader()) {
			player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You have to be the group leader to assing a task!");
			return;
		}
		if (player.getGroupSlayer().checkGroupSize() < 3) {
			player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You have to have at least 3 people in your group to get a task!");
			return;
		}
		for(Player player2: World.getPlayers()) {
			if(player2 != null) {
				if(player2.getGroupName() == player.getUsername()) {
					if (player2.getSlayer().getSlayerTask() != SlayerTasks.NO_TASK) {
						player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>"+player2.getUsername()+" already has a Slayer task.");
						return;
					}
					if (player2.getSlayer().getSlayerMaster() != SlayerMaster.GOD_MASTER) {
						player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>"+player2.getUsername()+" has to have the GOD Slayer master.");
						return;
					}
					if (player2.getInterfaceId() > 0) {
						player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>"+player2.getUsername()+" must close all their open interfaces.");
						return;
					}
				}
			}
		}
		boolean hasTask = player.getSlayer().getSlayerTask() != SlayerTasks.NO_TASK && player.getSlayer().getLastTask() != player.getSlayer().getSlayerTask();
		boolean group = player.getGroupSlayer().getGroupSize() >= 3 && player.getGroupName() == player.getUsername();
		if (!group)
			return;
		if (hasTask) {
			player.getPacketSender().sendInterfaceRemoval();
			return;
		}
		int[] taskData = SlayerTasks.getNewTaskData(player.getSlayer().getSlayerMaster());
		int slayerTaskId = taskData[0];
		int slayerTaskAmount = taskData[1];
		slayerTaskAmount = 1;
		SlayerTasks taskToSet = SlayerTasks.forId(slayerTaskId);

		if (taskToSet == null) {
			player.sendMessage("lol");
			return;
		}
		if (taskToSet == player.getSlayer().getLastTask() || NpcDefinition.forId(taskToSet.getNpcId())
				.getSlayerLevel() > player.getSkillManager().getMaxLevel(Skill.SLAYER)) {
			assignGroupTask();
			return;
		}
		player.getPacketSender().sendInterfaceRemoval();
		this.amountToSlay = slayerTaskAmount;
		this.slayerTask = taskToSet;
		DialogueManager.start(player, SlayerDialogues.receivedTask(player, player.getSlayer().getSlayerMaster(), player.getSlayer().getSlayerTask()));
		PlayerPanel.refreshPanel(player);
		if (group) {
			for(Player member:World.getPlayers()) {
				if(member != null) {
					if(member.getGroupName() == player.getUsername()) {
						member.getSlayer().setSlayerTask(taskToSet);
						member.getSlayer().setAmountToSlay(slayerTaskAmount);
						member.getPacketSender().sendInterfaceRemoval();
						DialogueManager.start(member, SlayerDialogues.receivedTask(member, player.getSlayer().getSlayerMaster(), taskToSet));
						PlayerPanel.refreshPanel(member);
					}
				}
			}
		}
	}
	
	
	




	private Player player;
	private int groupSize = 1;
	private String invitation = "";
	public GroupSlayer(Player player) {
		this.player = player;
	}




	public void setInvitation(String username) {
		this.invitation  = username;
		
	}
}