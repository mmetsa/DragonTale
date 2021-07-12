package com.ruthlessps.world.content;

import com.ruthlessps.drops.NPCDrops;
import com.ruthlessps.world.content.combat.CombatFactory;
import com.ruthlessps.world.content.combat.DesolaceFormulas;
import com.ruthlessps.world.entity.impl.player.Player;

public class BoostManager {
	
	final static int INTERFACE_ID = 63000;
	
	public static void showBoosts(Player player) {
		reCalculateBoosts(player);
		player.getPacketSender().sendInterface(63000);
		player.getPacketSender().sendString(INTERFACE_ID + 2, "Drop Rate Boost: "+player.getAttributes().calculateBonusDropRate(0) * 100 + "%");
		player.getPacketSender().sendString(INTERFACE_ID + 3, "Double Drop Rate Boost: "+NPCDrops.getDoubleDropChance(player)+"%");
		player.getPacketSender().sendString(INTERFACE_ID + 4, "Melee Damage Multiplier: " + player.getAttributes().getMeleeMultiplier());
		player.getPacketSender().sendString(INTERFACE_ID + 5, "Magic Damage Multiplier: " + player.getAttributes().getMagicMultiplier());
		player.getPacketSender().sendString(INTERFACE_ID + 6, "Ranged Damage Multiplier: " + player.getAttributes().getRangedMultiplier());
		player.getPacketSender().sendString(INTERFACE_ID + 7, "Defence Multiplier: "+(player.getAttributes().getDefMultiplier()));
		player.getPacketSender().sendString(INTERFACE_ID + 8, "Health Boost: ");
		player.getPacketSender().sendString(INTERFACE_ID + 9, player.getAttributes().getPrayerBonus() != 0 ? "Prayer Boost: "+player.getAttributes().getPrayerBonus() : "");
		player.getPacketSender().sendString(INTERFACE_ID + 10, player.pointsMultiplier != 0?"Bonus Points Modifier: "+player.getPointsManager().getModifier() : "");
		
	}

	public static void reCalculateBoosts(Player player) {
		DesolaceFormulas.calculateMaxMeleeHit(player, player);
		DesolaceFormulas.getMagicMaxhit(player, player);
		CombatFactory.calculateMaxRangedHit(player, player);
		DesolaceFormulas.getMagicDefence(player);
		DesolaceFormulas.getRangedDefence(player);
		DesolaceFormulas.getMeleeDefence(player);
	}
	
	
	
	
	
	
}