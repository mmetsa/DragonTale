package com.ruthlessps.world.content.combat.strategy;

import java.util.HashMap;
import java.util.Map;

import com.ruthlessps.world.content.combat.strategy.impl.Aviansie;
import com.ruthlessps.world.content.combat.strategy.impl.Bandos;
import com.ruthlessps.world.content.combat.strategy.impl.BandosAvatar;
import com.ruthlessps.world.content.combat.strategy.impl.BandosWarlord;
import com.ruthlessps.world.content.combat.strategy.impl.Bork;
import com.ruthlessps.world.content.combat.strategy.impl.ChaosElemental;
import com.ruthlessps.world.content.combat.strategy.impl.CorporealBeast;
import com.ruthlessps.world.content.combat.strategy.impl.CustomMagicStrategy;
import com.ruthlessps.world.content.combat.strategy.impl.DagannothSupreme;
import com.ruthlessps.world.content.combat.strategy.impl.DefaultMagicCombatStrategy;
import com.ruthlessps.world.content.combat.strategy.impl.DefaultMeleeCombatStrategy;
import com.ruthlessps.world.content.combat.strategy.impl.DefaultRangedCombatStrategy;
import com.ruthlessps.world.content.combat.strategy.impl.Demon;
import com.ruthlessps.world.content.combat.strategy.impl.Diablo;
import com.ruthlessps.world.content.combat.strategy.impl.Dragon;
import com.ruthlessps.world.content.combat.strategy.impl.Glacor;
import com.ruthlessps.world.content.combat.strategy.impl.Gritch;
import com.ruthlessps.world.content.combat.strategy.impl.HeartWrencher;
import com.ruthlessps.world.content.combat.strategy.impl.Jad;
import com.ruthlessps.world.content.combat.strategy.impl.KalphiteQueen;
import com.ruthlessps.world.content.combat.strategy.impl.Legio;
import com.ruthlessps.world.content.combat.strategy.impl.MagicSpell;
import com.ruthlessps.world.content.combat.strategy.impl.Nomad;
import com.ruthlessps.world.content.combat.strategy.impl.PlaneFreezer;
import com.ruthlessps.world.content.combat.strategy.impl.PointzoneDragon;
import com.ruthlessps.world.content.combat.strategy.impl.Revenant;
import com.ruthlessps.world.content.combat.strategy.impl.Spinolyp;
import com.ruthlessps.world.content.combat.strategy.impl.TormentedDemon;
import com.ruthlessps.world.content.combat.strategy.impl.TorvaBoss;
import com.ruthlessps.world.content.combat.strategy.impl.Vorago;
import com.ruthlessps.world.content.combat.strategy.impl.Vorkath;
import com.ruthlessps.world.content.combat.strategy.impl.Vortex;
import com.ruthlessps.world.content.combat.strategy.impl.WildyTorva;
import com.ruthlessps.world.content.combat.strategy.impl.Zulrah;

public class CombatStrategies {

	private static final DefaultMeleeCombatStrategy defaultMeleeCombatStrategy = new DefaultMeleeCombatStrategy();
	private static final DefaultMagicCombatStrategy defaultMagicCombatStrategy = new DefaultMagicCombatStrategy();
	private static final DefaultRangedCombatStrategy defaultRangedCombatStrategy = new DefaultRangedCombatStrategy();
	private static final CustomMagicStrategy customMagicStrategy = new CustomMagicStrategy();
	private static final Map<Integer, CombatStrategy> STRATEGIES = new HashMap<Integer, CombatStrategy>();

	public static CombatStrategy getDefaultMagicStrategy() {
		return defaultMagicCombatStrategy;
	}

	public static CombatStrategy getDefaultMeleeStrategy() {
		return defaultMeleeCombatStrategy;
	}
	public static CombatStrategy getCustomMagicStrategy() {
		return customMagicStrategy;
	}

	public static CombatStrategy getDefaultRangedStrategy() {
		return defaultRangedCombatStrategy;
	}

	public static CombatStrategy getStrategy(int npc) {
		if (STRATEGIES.get(npc) != null) {
			return STRATEGIES.get(npc);
		}
		return defaultMeleeCombatStrategy;
	}

	public static void init() {
		DefaultMagicCombatStrategy defaultMagicStrategy = new DefaultMagicCombatStrategy();
		STRATEGIES.put(13, defaultMagicStrategy);
		STRATEGIES.put(172, defaultMagicStrategy);
		STRATEGIES.put(174, defaultMagicStrategy);
		STRATEGIES.put(2025, defaultMagicStrategy);
		STRATEGIES.put(3495, defaultMagicStrategy);
		STRATEGIES.put(3496, defaultMagicStrategy);
		STRATEGIES.put(3491, defaultMagicStrategy);
		STRATEGIES.put(2882, defaultMagicStrategy);
		STRATEGIES.put(13451, defaultMagicStrategy);
		STRATEGIES.put(13452, defaultMagicStrategy);
		STRATEGIES.put(13453, defaultMagicStrategy);
		STRATEGIES.put(13454, defaultMagicStrategy);
		STRATEGIES.put(1643, defaultMagicStrategy);
		STRATEGIES.put(6254, defaultMagicStrategy);
		STRATEGIES.put(6257, defaultMagicStrategy);
		STRATEGIES.put(6278, defaultMagicStrategy);
		STRATEGIES.put(6221, defaultMagicStrategy);
		STRATEGIES.put(3006, new MagicSpell());
		STRATEGIES.put(3120, new MagicSpell());
		STRATEGIES.put(11250, new MagicSpell());
		STRATEGIES.put(4000, new MagicSpell());
		STRATEGIES.put(401, new MagicSpell());
		STRATEGIES.put(402, new MagicSpell());
		STRATEGIES.put(404, new MagicSpell());
		STRATEGIES.put(3250, new MagicSpell());
		STRATEGIES.put(580, new MagicSpell());

		DefaultRangedCombatStrategy defaultRangedStrategy = new DefaultRangedCombatStrategy();
		STRATEGIES.put(688, defaultRangedStrategy);
		STRATEGIES.put(2028, defaultRangedStrategy);
		STRATEGIES.put(6220, defaultRangedStrategy);
		STRATEGIES.put(6256, defaultRangedStrategy);
		STRATEGIES.put(6276, defaultRangedStrategy);
		STRATEGIES.put(6252, defaultRangedStrategy);
		STRATEGIES.put(27, defaultRangedStrategy);
		STRATEGIES.put(3020, defaultRangedStrategy);
		STRATEGIES.put(3004, defaultRangedStrategy);
		STRATEGIES.put(698, defaultRangedStrategy);
		STRATEGIES.put(699, defaultRangedStrategy);
		STRATEGIES.put(700, defaultRangedStrategy);
		STRATEGIES.put(3699, defaultRangedStrategy);
		STRATEGIES.put(3700, defaultRangedStrategy);
		STRATEGIES.put(3701, defaultRangedStrategy);
		STRATEGIES.put(3705, new Vortex());
		STRATEGIES.put(514, new Vorago());
		STRATEGIES.put(515, new TorvaBoss());
		STRATEGIES.put(517, new TorvaBoss());
		STRATEGIES.put(518, new TorvaBoss());
		STRATEGIES.put(2745, new Jad());
		STRATEGIES.put(8528, new Nomad());
		STRATEGIES.put(8349, new TormentedDemon());
		STRATEGIES.put(3200, new ChaosElemental());
		STRATEGIES.put(4540, new BandosAvatar());
		STRATEGIES.put(8133, new CorporealBeast());
		STRATEGIES.put(2896, new Spinolyp());
		STRATEGIES.put(2881, new DagannothSupreme());
		STRATEGIES.put(6206, new Gritch());
		STRATEGIES.put(1382, new Glacor());
		STRATEGIES.put(9939, new PlaneFreezer());
		STRATEGIES.put(536, new WildyTorva());
		STRATEGIES.put(11248, new Diablo());
		STRATEGIES.put(7164, new Legio());
		STRATEGIES.put(2047, new Zulrah());
		STRATEGIES.put(256, new BandosWarlord());
		STRATEGIES.put(6313, new HeartWrencher());
		STRATEGIES.put(7136, new Bork());

		Dragon dragonStrategy = new Dragon();
		PointzoneDragon pointzoneDragonStrategy = new PointzoneDragon();
		STRATEGIES.put(50, dragonStrategy);
		STRATEGIES.put(941, dragonStrategy);
		STRATEGIES.put(55, dragonStrategy);
		STRATEGIES.put(53, dragonStrategy);
		STRATEGIES.put(54, dragonStrategy);
		STRATEGIES.put(51, dragonStrategy);
		STRATEGIES.put(1590, dragonStrategy);
		STRATEGIES.put(1591, dragonStrategy);
		STRATEGIES.put(1592, dragonStrategy);
		STRATEGIES.put(5362, dragonStrategy);
		STRATEGIES.put(5363, dragonStrategy);
		STRATEGIES.put(3712, pointzoneDragonStrategy);
		STRATEGIES.put(4500, pointzoneDragonStrategy);
		Aviansie aviansieStrategy = new Aviansie();
		STRATEGIES.put(6246, aviansieStrategy);
		STRATEGIES.put(6230, aviansieStrategy);
		STRATEGIES.put(6231, aviansieStrategy);

		KalphiteQueen kalphiteQueenStrategy = new KalphiteQueen();
		STRATEGIES.put(1158, kalphiteQueenStrategy);
		STRATEGIES.put(1160, kalphiteQueenStrategy);

		Revenant revenantStrategy = new Revenant();
		STRATEGIES.put(6715, revenantStrategy);
		STRATEGIES.put(6716, revenantStrategy);
		STRATEGIES.put(6701, revenantStrategy);
		STRATEGIES.put(6725, revenantStrategy);
		STRATEGIES.put(6691, revenantStrategy);
	}
}
