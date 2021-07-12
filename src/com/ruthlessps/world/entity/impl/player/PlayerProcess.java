package com.ruthlessps.world.entity.impl.player;

import com.ruthlessps.engine.task.impl.SetEffects;
import com.ruthlessps.model.RegionInstance.RegionInstanceType;
import com.ruthlessps.model.Skill;
import com.ruthlessps.model.Locations.Location;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.World;
import com.ruthlessps.world.content.Deals;
import com.ruthlessps.world.content.LoyaltyProgram;
import com.ruthlessps.world.content.PlayerPanel;
import com.ruthlessps.world.content.WellOfGoodwill;
import com.ruthlessps.world.content.combat.pvp.BountyHunter;
import com.ruthlessps.world.content.minigames.impl.KillGame;
import com.ruthlessps.world.content.skill.impl.construction.House;
import com.ruthlessps.world.entity.impl.GroundItemManager;

public class PlayerProcess {

	/*
	 * The player (owner) of this instance
	 */
	private Player player;

	/*
	 * The loyalty tick, once this reaches 6, the player will be given loyalty
	 * points. 6 equals 3.6 seconds.
	 */
	private int loyaltyTick;

	/*
	 * The timer tick, once this reaches 2, the player's total play time will be
	 * updated. 2 equals 1.2 seconds.
	 */
	private int timerTick;
	private int healCounter;
	private int healCounter2;
	private int healCounter3;
	private int healCounter4;
	private int healCounter5;
	public static int bunnyhop;
	public static int superiorCounter;

	/*
	 * Makes sure ground items are spawned on height change
	 */
	private int previousHeight;

	public PlayerProcess(Player player) {
		this.player = player;
		this.previousHeight = player.getPosition().getZ();
	}

	public void sequence() {

		/* SKILLS */
		if (player.shouldProcessFarming()) {
			player.getFarming().sequence();
		}

		/* MISC */

		if (previousHeight != player.getPosition().getZ()) {
			GroundItemManager.handleRegionChange(player);
			previousHeight = player.getPosition().getZ();
		}

		if (!player.isInActive()) {
			if (loyaltyTick >= 6) {
				LoyaltyProgram.incrementPoints(player);
				loyaltyTick = 0;
			}
			loyaltyTick++;
		}
		if(bunnyhop >= 700) {
			player.getBunnyhop().endGame(false);
			bunnyhop = 0;
		} else {
			if(player.getLocation() == Location.BUNNYHOP) {
				bunnyhop++;
			}
		}
		if(healCounter >= 75) {
			if(SetEffects.burst(player)) {
				player.setConstitution(player.getSkillManager().getMaxLevel(Skill.CONSTITUTION));
				healCounter = 0;
			} else {
				healCounter = 0;
			}
		} else {
			healCounter++;
		}
		if(healCounter2 >= 45) {
			if(SetEffects.cryptic(player)) {
				player.setConstitution(player.getSkillManager().getMaxLevel(Skill.CONSTITUTION));
				healCounter2 = 0;
			} else {
				healCounter2 = 0;
			}
		} else {
			healCounter2++;
		}
		if(healCounter4 >= 45) {
			if(SetEffects.skeletal(player)) {
				player.setConstitution(player.getSkillManager().getMaxLevel(Skill.CONSTITUTION));
				healCounter4 = 0;
			} else {
				healCounter4 = 0;
			}
		} else {
			healCounter4++;
		}
		if(healCounter3 >= 45) {
			if(SetEffects.tyrant(player) && SetEffects.insidious(player)) {
				player.setConstitution(player.getSkillManager().getMaxLevel(Skill.CONSTITUTION));
				healCounter3 = 0;
			} else {
				healCounter3 = 0;
			}
		} else {
			healCounter3++;
		}
		if(healCounter5 >= 45) {
			if(SetEffects.fooo(player)) {
				player.setConstitution(player.getSkillManager().getMaxLevel(Skill.CONSTITUTION));
				healCounter5 = 0;
			} else {
				healCounter5 = 0;
			}
		} else {
			healCounter3++;
		}

		if (timerTick >= 1) {
			if(World.counter  >= 1) {
				World.counter--;
			}
		if(World.counter == 1) {
			World.counter = 0;
			World.sendMessage("@blu@The special offer has expired.");
		}
		player.getAttributes().decrementTimers();
			if(player.getSuperior()) {
				superiorCounter++;
				if(superiorCounter >= 1000) {
					player.setSuperior(false);
					superiorCounter = 0;
					World.deregister(player.getSuperiorId());
				}
			}
			player.getPacketSender().sendString(39167, "@red@Time played:  @whi@"
					+ Misc.getTimePlayed((player.getTotalPlayTime() + player.getRecordedLogin().elapsed())));
			timerTick = 0;
			KillGame.process();
		}
		timerTick++;

		BountyHunter.sequence(player);

		if (player.getRegionInstance() != null
				&& (player.getRegionInstance().getType() == RegionInstanceType.CONSTRUCTION_HOUSE
						|| player.getRegionInstance().getType() == RegionInstanceType.CONSTRUCTION_DUNGEON)) {
			((House) player.getRegionInstance()).process();
		}
	}
}
