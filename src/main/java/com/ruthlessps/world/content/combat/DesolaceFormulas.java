package com.ruthlessps.world.content.combat;

import com.ruthlessps.engine.task.impl.SetEffects;
import com.ruthlessps.model.Graphic;
import com.ruthlessps.model.Skill;
import com.ruthlessps.model.container.impl.Equipment;
import com.ruthlessps.model.definitions.ItemDefinition;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.content.Achievements;
import com.ruthlessps.world.content.BonusManager;
import com.ruthlessps.world.content.HourlyNpc;
import com.ruthlessps.world.content.Achievements.AchievementData;
import com.ruthlessps.world.content.combat.effect.EquipmentBonus;
import com.ruthlessps.world.content.combat.magic.CombatSpell;
import com.ruthlessps.world.content.combat.prayer.CurseHandler;
import com.ruthlessps.world.content.combat.prayer.PrayerHandler;
import com.ruthlessps.world.content.combat.range.CombatRangedAmmo.RangedWeaponData;
import com.ruthlessps.world.content.combat.weapon.FightType;
import com.ruthlessps.world.content.skill.SkillManager;
import com.ruthlessps.world.entity.impl.Character;
import com.ruthlessps.world.entity.impl.npc.NPC;
import com.ruthlessps.world.entity.impl.player.Player;

public class DesolaceFormulas {

	/*
	 * =========================================================================
	 * =====
	 */
	/*
	 * ===================================MELEE=================================
	 * ====
	 */

	/**
	 * Obsidian items
	 */

	public static final int[] obsidianWeapons = { 746, 747, 6523, 6525, 6526, 6527, 6528, 11595 };
	public static int bestMeleeAtk(Player p) {
		if (p.getBonusManager().getAttackBonus()[0] > p.getBonusManager().getAttackBonus()[1]
				&& p.getBonusManager().getAttackBonus()[0] > p.getBonusManager().getAttackBonus()[2]) {
			return 0;
		}
		if (p.getBonusManager().getAttackBonus()[1] > p.getBonusManager().getAttackBonus()[0]
				&& p.getBonusManager().getAttackBonus()[1] > p.getBonusManager().getAttackBonus()[2]) {
			return 1;
		}
		return p.getBonusManager().getAttackBonus()[2] <= p.getBonusManager().getAttackBonus()[1]
				|| p.getBonusManager().getAttackBonus()[2] <= p.getBonusManager().getAttackBonus()[0] ? 0 : 2;
	}

	public static int bestMeleeDef(Player p) {
		if (p.getBonusManager().getDefenceBonus()[0] > p.getBonusManager().getDefenceBonus()[1]
				&& p.getBonusManager().getDefenceBonus()[0] > p.getBonusManager().getDefenceBonus()[2]) {
			return 0;
		}
		if (p.getBonusManager().getDefenceBonus()[1] > p.getBonusManager().getDefenceBonus()[0]
				&& p.getBonusManager().getDefenceBonus()[1] > p.getBonusManager().getDefenceBonus()[2]) {
			return 1;
		}
		return p.getBonusManager().getDefenceBonus()[2] <= p.getBonusManager().getDefenceBonus()[0]
				|| p.getBonusManager().getDefenceBonus()[2] <= p.getBonusManager().getDefenceBonus()[1] ? 0 : 2;
	}

	public static int calculateMaxMeleeHit(Character entity, Character victim) {
		double maxHit = 0;
		if (entity.isNpc()) {
			NPC npc = (NPC) entity;
			maxHit = npc.getDefinition().getMaxHit();
			if (npc.getStrengthWeakened()[0]) {
				maxHit -= (int) ((0.10) * (maxHit));
			} else if (npc.getStrengthWeakened()[1]) {
				maxHit -= (int) ((0.20) * (maxHit));
			} else if (npc.getStrengthWeakened()[2]) {
				maxHit -= (int) ((0.30) * (maxHit));
			}

			/** CUSTOM NPCS **/
			if (npc.getId() == 2026) { // Dharok the wretched
				maxHit += (int) ((npc.getDefaultConstitution() - npc.getConstitution()) * 0.2);
			}
		} else {
			Player plr = (Player) entity;
			int meleeBoost = 1;
			
			double boost = 0.0;
			double base = 0;
			double effective = getEffectiveStr(plr);
			double specialBonus = 1;
			if (plr.isSpecialActivated()) {
				specialBonus = plr.getCombatSpecial().getStrengthBonus();
			}
			double strengthBonus = plr.getBonusManager().getOtherBonus()[0];
			base = (13 + effective + (strengthBonus / 8) + ((effective * strengthBonus) / 65)) / 11;
			if (plr.fullDH())
				base += ((plr.getSkillManager().getMaxLevel(Skill.CONSTITUTION) - plr.getConstitution()) * .045) + 1;
			if (specialBonus > 1)
				base = (base * specialBonus);
			if (hasObsidianEffect(plr) || EquipmentBonus.wearingVoid(plr, CombatType.MELEE))
				//base = (base * 1.2);
				meleeBoost += 0.2;
			meleeBoost += (plr.getAttributes().getMeleeMultiplier() - 1);
			boost += meleeBoost;
			if (victim.isNpc()) {
				NPC npc = (NPC) victim;
				if (npc.getDefenceWeakened()[0]) {
					base += (int) ((0.10) * (base));
				} else if (npc.getDefenceWeakened()[1]) {
					base += (int) ((0.20) * (base));
				} else if (npc.getDefenceWeakened()[2]) {
					base += (int) ((0.30) * (base));
				}

				if (npc.getId() == 4500 || npc.getId() == 3712) {
					boost += 6.0;
				}
				if(npc.getId() == HourlyNpc.CURRENT_NPC) {
					boost += 0.3;
				}
				/** SLAYER HELMET **/
				if (npc.getId() == plr.getSlayer().getSlayerTask().getNpcId()) {
					boost = getBoost(plr, boost);
					if(plr.getEquipment().getItems()[Equipment.HEAD_SLOT].getId() == 14927) {
						boost += 0.025;
					}
					if(plr.getEquipment().getItems()[Equipment.BODY_SLOT].getId() == 14925) {
						boost += 0.025;
					}
					if(plr.getEquipment().getItems()[Equipment.LEG_SLOT].getId() == 14926) {
						boost += 0.025;
					}
					if(plr.getEquipment().getItems()[Equipment.HANDS_SLOT].getId() == 11540) {
						boost += 0.025;
					}
					if(plr.getEquipment().getItems()[Equipment.FEET_SLOT].getId() == 11541) {
						boost += 0.025;
					}
					if(plr.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId() == 9121) {
						boost += 0.15;
					}
					if(plr.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId() == 4503) {
						boost += 0.25;
					}
					if(plr.getEquipment().containsAll(14927,14925,14926,11540,11541,9121)) {
						boost += 0.325;
					} else if(plr.getEquipment().containsAll(14927,14925,14926,11540,11541,4503)) {
						boost += 0.425;
					}
				}
			}
			base *= boost;
			maxHit = (base *= 10);
			if(plr.getAttributes().getPet().getId() == 4201) {
				maxHit = maxHit * 1.35;
			} else if(plr.getAttributes().getPet().getId() == 5266
					|| plr.getAttributes().getPet().getId() == 5264
					|| plr.getAttributes().getPet().getId() == 13453) {
				maxHit = maxHit * 1.2;
			}
			if(maxHit > 5000) {
				Achievements.doProgress(plr, AchievementData.MAXHIT_5000);
			}
		}
		if (victim.isPlayer()) {
			Player p = (Player) victim;
			if (p.hasStaffOfLightEffect()) {
				maxHit = maxHit / 2;
				p.performGraphic(new Graphic(2319));
			}
		}
		return (int) Math.floor(maxHit);
	}

	static double getBoost(Player plr, double boost) {
		if (plr.getEquipment().getItems()[Equipment.HEAD_SLOT].getId() == 13263) { //reg
			boost += 0.1;
		}
		if (plr.getEquipment().getItems()[Equipment.HEAD_SLOT].getId() == 15497) { //i1
			boost += 0.2;
		}
		if (plr.getEquipment().getItems()[Equipment.HEAD_SLOT].getId() == 14637) { //i2
			boost += 0.3;
		}
		if (plr.getEquipment().getItems()[Equipment.HEAD_SLOT].getId() == 15492) { //i3
			boost += 0.4;
		}
		if (plr.getEquipment().getItems()[Equipment.HEAD_SLOT].getId() == 14636) { //i4
			boost += 0.5;
		}
		if (plr.getEquipment().getItems()[Equipment.HEAD_SLOT].getId() == 3317) { //i5
			boost += 0.5;
		}
		if (plr.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId() == 4503) {
			boost += 0.25;
		}
		if (plr.getEquipment().getItems()[Equipment.CAPE_SLOT].getId() == 6070) {
			boost += 0.5;
		} else if (plr.getEquipment().getItems()[Equipment.CAPE_SLOT].getId() == 6959) {
			boost += 2;
		}
		return boost;
	}

	public static int getAttackDelay(Player plr) {
		int id = plr.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId();
		String s = ItemDefinition.forId(id).getName().toLowerCase();
		if (id == -1)
			return 2;// unarmed
		if (id == 18357 || id == 14684)
			return 3;
		if(id == 15)
			return 4;
		RangedWeaponData rangedData = plr.getRangedWeaponData();
		if (rangedData != null) {
			int speed = rangedData.getType().getAttackDelay();
			if (plr.getFightType() == FightType.SHORTBOW_RAPID || plr.getFightType() == FightType.DART_RAPID
					|| plr.getFightType() == FightType.KNIFE_RAPID || plr.getFightType() == FightType.THROWNAXE_RAPID
					|| plr.getFightType() == FightType.JAVELIN_RAPID) {
				speed--;
			}
			return speed;
		}
		if (id == 18365)
			return 3;
		else if (id == 18349 || id == 3276) // CCbow and rapier
			return 4;
		if (id == 18353) // cmaul
			return 7;// chaotic maul

		if (id == 20000)
			return 4;// gs
		if (id == 20001)
			return 4;// gs
		if (id == 20002)
			return 4;// gs
		if (id == 20003)
			return 4;// gs
		if (id == 18349)
			return 5;// chaotic rapier
		if (id == 18353) // cmaul
			return 7;// chaotic maul
		if (id == 16877)
			return 4;// dung 16877 shortbow
		if (id == 19143)
			return 3;// sara shortbow
		if (id == 19146)
			return 4;// guthix shortbow
		if (id == 19149)
			return 3;// zammy shortbow

		switch (id) {
		case 11235:
		case 11288:
		case 11289:
		case 11290:
		case 11291:
		case 13405: // dbow
		case 15701: // dark bow
		case 15702: // dark bow
		case 15703: // dark bow
		case 15704: // dark bow
		case 19146: // guthix bow
			return 9;
		case 19730:
		case 7046:
		case 7047:
		case 7048:
		case 7049:
		case 7151:
		case 17293:
			return 4;
		case 14097:
			return 1;
		case 13879:
			return 8;
		case 15241: // hand cannon
			return 8;
		case 11730:
			return 4;
		case 14484:
			return 5;
		case 13883:
			return 6;
		case 10887:
		case 6528:
		case 11595:
		case 15039:
			return 7;
		case 13905:
			return 5;
		case 13907:
			return 5;
		case 18353:
			return 7;
		case 18349:
			return 4;
		case 20000:
		case 20001:
		case 20002:
		case 20003:
			return 4;

		case 16403: // long primal
			return 5;
		}

		if (s.endsWith("greataxe"))
			return 7;
		else if (s.equals("torags hammers"))
			return 5;
		else if (s.equals("guthans warspear"))
			return 5;
		else if (s.equals("veracs flail"))
			return 5;
		else if (s.equals("ahrims staff"))
			return 6;
		else if (s.equals("chaotic crossbow"))
			return 4;
		else if (s.equals("vortex crossbow"))
			return 4;
		else if (s.contains("staff")) {
			if (s.contains("zamarok") || s.contains("guthix") || s.contains("saradomian") || s.contains("slayer")
					|| s.contains("ancient"))
				return 4;
			else
				return 5;
		} else if (s.contains("aril")) {
			if (s.contains("composite") || s.equals("seercull"))
				return 5;
			else if (s.contains("Ogre"))
				return 8;
			else if (s.contains("short") || s.contains("hunt") || s.contains("sword"))
				return 4;
			else if (s.contains("long") || s.contains("crystal"))
				return 6;
			else if (s.contains("'bow"))
				return 7;

			return 5;
		} else if (s.contains("dagger"))
			return 4;
		else if (s.contains("godsword") || s.contains("2h"))
			return 6;
		else if (s.contains("longsword"))
			return 5;
		else if (s.contains("sword"))
			return 4;
		else if (s.contains("scimitar") || s.contains("katana"))
			return 4;
		else if (s.contains("mace"))
			return 5;
		else if (s.contains("battleaxe"))
			return 6;
		else if (s.contains("pickaxe"))
			return 5;
		else if (s.contains("thrownaxe"))
			return 5;
		else if (s.contains("axe"))
			return 5;
		else if (s.contains("warhammer"))
			return 6;
		else if (s.contains("2h"))
			return 7;
		else if (s.contains("spear"))
			return 5;
		else if (s.contains("claw"))
			return 4;
		else if (s.contains("halberd"))
			return 7;

		// sara sword, 2400ms
		else if (s.equals("granite maul"))
			return 7;
		else if (s.equals("toktz-xil-ak"))// sword
			return 4;
		else if (s.equals("tzhaar-ket-em"))// mace
			return 5;
		else if (s.equals("tzhaar-ket-om"))// maul
			return 7;
		else if (s.equals("chaotic maul"))// maul
			return 7;
		else if (s.equals("toktz-xil-ek"))// knife
			return 4;
		else if (s.equals("toktz-xil-ul"))// rings
			return 4;
		else if (s.equals("toktz-mej-tal"))// staff
			return 6;
		else if (s.contains("whip"))
			return 4;
		else if (s.contains("dart"))
			return 3;
		else if (s.contains("knife"))
			return 3;
		else if (s.contains("javelin"))
			return 6;
		return 5;
	}

	public static double getEffectiveStr(Player plr) {
		return ((plr.getSkillManager().getCurrentLevel(Skill.STRENGTH)) * getPrayerStr(plr)) + getStyleBonus(plr);
	}

	public static int getMagicAttack(Player plr) {
		boolean voidEquipment = EquipmentBonus.wearingVoid(plr, CombatType.MAGIC);
		int attackLevel = plr.getSkillManager().getCurrentLevel(Skill.MAGIC);
		if (voidEquipment)
			attackLevel += plr.getSkillManager().getCurrentLevel(Skill.MAGIC) * 0.2;
		if (plr.getPrayerActive()[PrayerHandler.MYSTIC_WILL] || plr.getCurseActive()[CurseHandler.SAP_MAGE]) {
			attackLevel *= 1.05;
		} else if (plr.getPrayerActive()[PrayerHandler.MYSTIC_LORE]) {
			attackLevel *= 1.10;
		} else if (plr.getPrayerActive()[PrayerHandler.MYSTIC_MIGHT]) {
			attackLevel *= 1.15;
		} else if (plr.getPrayerActive()[PrayerHandler.AUGURY]) {
			attackLevel *= 1.22;
		} else if (plr.getCurseActive()[CurseHandler.LEECH_MAGIC]) {
			attackLevel *= 1.18;
		}
		attackLevel *= plr.isSpecialActivated() ? plr.getCombatSpecial().getAccuracyBonus() : 1;

		return (int) (attackLevel + (plr.getBonusManager().getAttackBonus()[3] * 2));
	}

	/**
	 * Calculates a player's magic defence level
	 *
	 * @return The player's magic defence level
	 */
	public static int getMagicDefence(Player plr) {

		int defenceLevel = plr.getSkillManager().getCurrentLevel(Skill.DEFENCE) / 2
				+ plr.getSkillManager().getCurrentLevel(Skill.MAGIC) / 2;

		if (plr.getPrayerActive()[PrayerHandler.THICK_SKIN]) {
			defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.05;
		} else if (plr.getPrayerActive()[PrayerHandler.ROCK_SKIN]) {
			defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.1;
		} else if (plr.getPrayerActive()[PrayerHandler.STEEL_SKIN]) {
			defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.15;
		} else if (plr.getPrayerActive()[PrayerHandler.CHIVALRY]) {
			defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.2;
		} else if (plr.getPrayerActive()[PrayerHandler.PIETY]) {
			defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
		} else if (plr.getPrayerActive()[PrayerHandler.RIGOUR]) {
			defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
		} else if (plr.getPrayerActive()[PrayerHandler.AUGURY]) {
			defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
		} else if (plr.getCurseActive()[CurseHandler.TURMOIL]) { // turmoil
			defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.20 + plr.getLeechedBonuses()[0];
		}
		defenceLevel *= (double)1 + plr.getAttributes().getDefBoost();

		return (int) (defenceLevel + plr.getBonusManager().getDefenceBonus()[3]
				+ (plr.getBonusManager().getDefenceBonus()[3] / 3));
	}

	/**
	 * Calculates a player's magic max hit
	 *
	 * @return The player's magic max hit damage
	 */
	public static int getMagicMaxhit(Character c, Character victim) {
		int damage = 0;
		CombatSpell spell = c.getCurrentlyCasting();
		if (spell != null) {
			if (spell.maximumHit() > 0)
				damage += spell.maximumHit();
			else {
				if (c.isNpc()) {
					damage = ((NPC) c).getDefinition().getMaxHit();
				} else {
					damage = 1;
				}
			}
		}
		if(c.isPlayer() && ((Player)c).getEquipment().containsAny(Player.magicStaves)) {
			damage+=((Player)c).getBonusManager().getOtherBonus()[BonusManager.MAGIC_DAMAGE]/2;
		}

		if (c.isNpc()) {
			if (spell == null) {
				damage = Misc.getRandom(((NPC) c).getDefinition().getMaxHit());
			}
			return damage;
		}

		Player p = (Player) c;
		double damageMultiplier = 1;
		int magicBoost = 0;
		if(SetEffects.battlemage(p)) {
			damageMultiplier += .10;
		}
		if(SetEffects.subjugation(p)) {
			damageMultiplier += .25;
		}
		if(p.getEquipment().getItems()[Equipment.BODY_SLOT].getId() == 14319 && p.getEquipment().getItems()[Equipment.LEG_SLOT].getId() == 14331 && p.getEquipment().getItems()[Equipment.HEAD_SLOT].getId() == 14341) {
			damageMultiplier += .3;
		}
		switch (p.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId()) {
		case 4675:
		case 6914:
		case 15246:
			damageMultiplier += .10;
			break;
		case 18355:
			damageMultiplier += .20;
			break;
		}

		boolean specialAttack = p.isSpecialActivated();

		int maxHit = -1;

		if (specialAttack) {
			switch (p.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId()) {
			case 19780:
				damage = maxHit = 750;
				break;
			case 11730:
				damage = maxHit = 310;
				break;
			}
		} else {
			damageMultiplier += 0.25;
		}

		if (p.getEquipment().getItems()[Equipment.AMULET_SLOT].getId() == 18335) {
			damageMultiplier += .10;
		}
		if (victim.isNpc()) {
			NPC npc = (NPC) victim;
			if(npc.getId() == HourlyNpc.CURRENT_NPC) {
				damageMultiplier += 0.3;
			}
			if (npc.getId() == p.getSlayer().getSlayerTask().getNpcId()) {
				damageMultiplier = getBoost(p, damageMultiplier);
			}
		}
		damageMultiplier += (p.getAttributes().getMagicMultiplier() - 1);
		damage *= damageMultiplier;

		if (maxHit > 0) {
			if (damage > maxHit) {
				damage = maxHit;
			}
		}
		if(p.getAttributes().getPet().getId() == 5270 || p.getAttributes().getPet().getId() == 13453) {
			damage = (int) (damage * 1.2);
		} else if(p.getAttributes().getPet().getId() == 420) {
			damage = (int) (damage * 1.35);
		}
		return damage;
	}

	/**
	 * Calculates a player's Melee attack level (how likely that they're going to
	 * hit through defence)
	 * 
	 * @param plr
	 *            The player's Meelee attack level
	 * @return The player's Melee attack level
	 */
	@SuppressWarnings("incomplete-switch")
	public static int getMeleeAttack(Player plr) {
		int attackLevel = plr.getSkillManager().getCurrentLevel(Skill.ATTACK);
		switch (plr.getFightType().getStyle()) {
		case AGGRESSIVE:
			attackLevel += 3;
			break;
		case CONTROLLED:
			attackLevel += 1;
			break;
		}
		boolean hasVoid = EquipmentBonus.wearingVoid(plr, CombatType.MELEE);

		if (PrayerHandler.isActivated(plr, PrayerHandler.CLARITY_OF_THOUGHT)) {
			attackLevel += plr.getSkillManager().getMaxLevel(Skill.ATTACK) * 0.05;
		} else if (PrayerHandler.isActivated(plr, PrayerHandler.IMPROVED_REFLEXES)) {
			attackLevel += plr.getSkillManager().getMaxLevel(Skill.ATTACK) * 0.1;
		} else if (PrayerHandler.isActivated(plr, PrayerHandler.INCREDIBLE_REFLEXES)) {
			attackLevel += plr.getSkillManager().getMaxLevel(Skill.ATTACK) * 0.15;
		} else if (PrayerHandler.isActivated(plr, PrayerHandler.CHIVALRY)) {
			attackLevel += plr.getSkillManager().getMaxLevel(Skill.ATTACK) * 0.15;
		} else if (PrayerHandler.isActivated(plr, PrayerHandler.PIETY)) {
			attackLevel += plr.getSkillManager().getMaxLevel(Skill.ATTACK) * 0.2;
		} else if (CurseHandler.isActivated(plr, CurseHandler.LEECH_ATTACK)) {
			attackLevel += plr.getSkillManager().getMaxLevel(Skill.ATTACK) * 0.05 + plr.getLeechedBonuses()[2];
		} else if (CurseHandler.isActivated(plr, CurseHandler.TURMOIL)) {
			attackLevel += plr.getSkillManager().getMaxLevel(Skill.ATTACK) * 0.3 + plr.getLeechedBonuses()[2];
		}

		if (hasVoid) {
			attackLevel += plr.getSkillManager().getMaxLevel(Skill.ATTACK) * 0.1;
		}
		attackLevel *= plr.isSpecialActivated() ? plr.getCombatSpecial().getAccuracyBonus() : 1;
		int i = (int) plr.getBonusManager().getAttackBonus()[bestMeleeAtk(plr)];

		if (hasObsidianEffect(plr) || hasVoid)
			i *= 1.20;
		return (int) (attackLevel + (attackLevel * 0.15) + (i + i * 0.04));
	}

	/**
	 * Calculates a player's Melee Defence level
	 * 
	 * @param plr
	 *            The player to calculate Melee defence for
	 * @return The player's Melee defence level
	 */
	public static int getMeleeDefence(Player plr) {
		int defenceLevel = plr.getSkillManager().getCurrentLevel(Skill.DEFENCE);
		int i = (int) plr.getBonusManager().getDefenceBonus()[bestMeleeDef(plr)];
		if (plr.getPrayerActive()[PrayerHandler.THICK_SKIN]) {
			defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.05;
		} else if (plr.getPrayerActive()[PrayerHandler.ROCK_SKIN]) {
			defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.1;
		} else if (plr.getPrayerActive()[PrayerHandler.STEEL_SKIN]) {
			defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.15;
		} else if (plr.getPrayerActive()[PrayerHandler.CHIVALRY]) {
			defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.2;
		} else if (plr.getPrayerActive()[PrayerHandler.PIETY]) {
			defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
		} else if (plr.getPrayerActive()[PrayerHandler.RIGOUR]) {
			defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
		} else if (plr.getPrayerActive()[PrayerHandler.AUGURY]) {
			defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
		} else if (plr.getCurseActive()[CurseHandler.TURMOIL]) { // turmoil
			defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.15;
		}
		defenceLevel *= (double)1 + plr.getAttributes().getDefMultiplier();
		return (int) (defenceLevel + (defenceLevel * 0.15) + (i + i * 0.05));
	}

	public static double getPrayerStr(Player plr) {
		if (plr.getPrayerActive()[1] || plr.getCurseActive()[CurseHandler.LEECH_STRENGTH])
			return 1.05;
		else if (plr.getPrayerActive()[6])
			return 1.1;
		else if (plr.getPrayerActive()[14])
			return 1.15;
		else if (plr.getPrayerActive()[24])
			return 1.18;
		else if (plr.getPrayerActive()[25])
			return 1.23;
		else if (plr.getCurseActive()[CurseHandler.TURMOIL])
			return 1.24;
		return 1;
	}

	/**
	 * Calculates a player's Ranged attack (level). Credits: Dexter Morgan
	 * 
	 * @param plr
	 *            The player to calculate Ranged attack level for
	 * @return The player's Ranged attack level
	 */
	public static int getRangedAttack(Player plr) {
		int rangeLevel = plr.getSkillManager().getCurrentLevel(Skill.RANGED);
		boolean hasVoid = EquipmentBonus.wearingVoid(plr, CombatType.RANGED);
		double accuracy = plr.isSpecialActivated() ? plr.getCombatSpecial().getAccuracyBonus() : 1;
		rangeLevel *= accuracy;
		if (hasVoid) {
			rangeLevel += SkillManager.getLevelForExperience(plr.getSkillManager().getExperience(Skill.RANGED)) * 0.15;
		}
		if (plr.getCurseActive()[PrayerHandler.SHARP_EYE] || plr.getCurseActive()[CurseHandler.SAP_RANGER]) {
			rangeLevel *= 1.05;
		} else if (plr.getPrayerActive()[PrayerHandler.HAWK_EYE]) {
			rangeLevel *= 1.10;
		} else if (plr.getPrayerActive()[PrayerHandler.EAGLE_EYE]) {
			rangeLevel *= 1.15;
		} else if (plr.getPrayerActive()[PrayerHandler.RIGOUR]) {
			rangeLevel *= 1.22;
		} else if (plr.getCurseActive()[CurseHandler.LEECH_RANGED]) {
			rangeLevel *= 1.10;
		}
		if (hasVoid && accuracy > 1.15)
			rangeLevel *= 1.68;
		/*
		 * Slay helm
		 *
		 * if(plr.getAdvancedSkills().getSlayer().getSlayerTask() != null &&
		 * plr.getEquipment().getItems()[Equipment.HEAD_SLOT].getId() == 15492) {
		 * if(plr.getCombatAttributes().getCurrentEnemy() != null &&
		 * plr.getCombatAttributes().getCurrentEnemy().isNpc()) { NPC n =
		 * (NPC)plr.getCombatAttributes().getCurrentEnemy(); if(n != null && n.getId()
		 * == plr.getAdvancedSkills().getSlayer().getSlayerTask().getNpcId()) rangeLevel
		 * *= 1.12; } }
		 */
		return (int) (rangeLevel + (plr.getBonusManager().getAttackBonus()[4] * 2));
	}

	/**
	 * Calculates a player's Ranged defence level.
	 * 
	 * @param plr
	 *            The player to calculate the Ranged defence level for
	 * @return The player's Ranged defence level
	 */
	public static int getRangedDefence(Player plr) {
		int defenceLevel = plr.getSkillManager().getCurrentLevel(Skill.DEFENCE);
		if (plr.getPrayerActive()[PrayerHandler.THICK_SKIN]) {
			defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.05;
		} else if (plr.getPrayerActive()[PrayerHandler.ROCK_SKIN]) {
			defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.1;
		} else if (plr.getPrayerActive()[PrayerHandler.STEEL_SKIN]) {
			defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.15;
		} else if (plr.getPrayerActive()[PrayerHandler.CHIVALRY]) {
			defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.2;
		} else if (plr.getPrayerActive()[PrayerHandler.PIETY]) {
			defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
		} else if (plr.getPrayerActive()[PrayerHandler.RIGOUR]) {
			defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
		} else if (plr.getPrayerActive()[PrayerHandler.AUGURY]) {
			defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
		} else if (plr.getCurseActive()[CurseHandler.TURMOIL]) { // turmoil
			defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.20 + plr.getLeechedBonuses()[0];
		}
		defenceLevel *= (double)1 + plr.getAttributes().getDefMultiplier();
		return (int) (defenceLevel + plr.getBonusManager().getDefenceBonus()[4]
				+ (plr.getBonusManager().getDefenceBonus()[4] / 2));
	}

	@SuppressWarnings("incomplete-switch")
	public static int getStyleBonus(Player plr) {
		switch (plr.getFightType().getStyle()) {
		case AGGRESSIVE:
		case ACCURATE:
			return 3;
		case CONTROLLED:
			return 1;
		}
		return 0;
	}

	public static boolean hasObsidianEffect(Player plr) {
		if (plr.getEquipment().getItems()[2].getId() != 11128)
			return false;

		for (int weapon : obsidianWeapons) {
			if (plr.getEquipment().getItems()[3].getId() == weapon)
				return true;
		}
		return false;
	}
}
