package com.ruthlessps.world.entity.impl.player;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.*;
import com.ruthlessps.drops.DropLog;
import com.ruthlessps.drops.DropLog.DropLogEntry;
import com.ruthlessps.engine.task.impl.FamiliarSpawnTask;
import com.ruthlessps.model.GameMode;
import com.ruthlessps.model.Gender;
import com.ruthlessps.model.Item;
import com.ruthlessps.model.MagicSpellbook;
import com.ruthlessps.model.PlayerRanks.DonorRights;
import com.ruthlessps.model.PlayerRanks.PlayerRights;
import com.ruthlessps.model.PlayerRelations.PrivateChatStatus;
import com.ruthlessps.model.Position;
import com.ruthlessps.model.Prayerbook;
import com.ruthlessps.model.container.impl.Bank;
import com.ruthlessps.net.login.LoginResponses;
import com.ruthlessps.world.content.ColourSet;
import com.ruthlessps.world.content.KillsTracker;
import com.ruthlessps.world.content.KillsTracker.KillsEntry;
import com.ruthlessps.world.content.LoyaltyProgram.LoyaltyTitles;
import com.ruthlessps.world.content.PointsManager;
import com.ruthlessps.world.content.combat.magic.CombatSpells;
import com.ruthlessps.world.content.combat.weapon.FightType;
import com.ruthlessps.world.content.skill.SkillManager.Skills;
import com.ruthlessps.world.content.skill.impl.slayer.SlayerMaster;
import com.ruthlessps.world.content.skill.impl.slayer.SlayerTasks;
import com.ruthlessps.world.entity.impl.npc.Pet;

public class PlayerLoading {

	

	public static int getResult(Player player) {
		return getResult(player, false);
	}
	public static int getTop(Player player, boolean force) {
		Path path = Paths.get("./data/saves/characters/", player.getUsername() + ".json");
		File file = path.toFile();
		if (!file.exists()) {
			return LoginResponses.NEW_ACCOUNT;
		}
		try (FileReader fileReader = new FileReader(file)) {
			
			JsonParser fileParser = new JsonParser();
			Gson builder = new GsonBuilder().create();
			JsonElement element = fileParser.parse(fileReader);
			if (element.isJsonNull()) {
				System.err.println(player.getUsername() + " has a nulled profile");
				return LoginResponses.LOGIN_COULD_NOT_COMPLETE;
			}
			
			JsonObject reader = element.getAsJsonObject();
			if (reader.has("username")) {
				player.setUsername(reader.get("username").getAsString());
			}
			if (reader.has("prestige-count")) {
				player.setPrestigeCount(reader.get("prestige-count").getAsInt());
			}
			if (reader.has("donated")) {
				player.incrementAmountDonated(reader.get("donated").getAsInt());
			}
			if(reader.has("npcDamage")) {
				player.setNpcDamage(reader.get("npcDamage").getAsLong());
			}
			if(reader.has("npcKills")) {
				player.setNpcKills(reader.get("npcKills").getAsInt());
			}
			if(reader.has("toggleKC")) {
				player.setToggleKC(reader.get("toggleKC").getAsBoolean());
			}
			if(reader.has("infinitePrayer")) {
				player.setInfinitePrayer(reader.get("infinitePrayer").getAsBoolean());
			}
			if(reader.has("voteAmt")) {
				player.setVoteAmt(reader.get("voteAmt").getAsInt());
			}
			if (reader.has("quests-done")) {
				player.setQuestsDone(reader.get("quests-done").getAsInt());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return LoginResponses.LOGIN_SUCCESSFUL;
		}
		return LoginResponses.LOGIN_SUCCESSFUL;
	}
	public static int getResult(Player player, boolean force) {

		// Create the path and file objects.
		Path path = Paths.get("./data/saves/characters/", player.getUsername() + ".json");
		File file = path.toFile();

		// If the file doesn't exist, we're logging in for the first
		// time and can skip all of this.
		if (!file.exists()) {
			return LoginResponses.NEW_ACCOUNT;
		}

		// Now read the properties from the json parser.
		try (FileReader fileReader = new FileReader(file)) {
			
			JsonParser fileParser = new JsonParser();
			Gson builder = new GsonBuilder().create();
			JsonElement element = fileParser.parse(fileReader);
			if (element.isJsonNull()) {
				System.err.println(player.getUsername() + " has a nulled profile");
				return LoginResponses.LOGIN_COULD_NOT_COMPLETE;
			}
			
			JsonObject reader = element.getAsJsonObject();

			if (reader.has("total-play-time-ms")) {
				player.setTotalPlayTime(reader.get("total-play-time-ms").getAsLong());
			}

			if (reader.has("username")) {
				player.setUsername(reader.get("username").getAsString());
			}

			if (reader.has("password")) {
				String password = reader.get("password").getAsString();
				if(!force) {
					if (!player.getPassword().equals(password)) {
						return LoginResponses.LOGIN_INVALID_CREDENTIALS;
					}
				}
				player.setPassword(password);
			}

			if (reader.has("email")) {
				player.setEmailAddress(reader.get("email").getAsString());
			}

			if (reader.has("staff-rights")) {
				player.setRights(PlayerRights.valueOf(reader.get("staff-rights").getAsString()));
			}
			if (reader.has("donor-rights")) {
				player.setDonor(DonorRights.valueOf(reader.get("donor-rights").getAsString()));
			}

			if (reader.has("modeler")) {
				player.setModeler(reader.get("modeler").getAsBoolean());
			}
			if (reader.has("gambler")) {
				player.setGambler(reader.get("gambler").getAsBoolean());
			}
			if (reader.has("gfx-designer")) {
				player.setGfxDesigner(reader.get("gfx-designer").getAsBoolean());
			}
			if (reader.has("youtube")) {
				player.setYoutuber(reader.get("youtube").getAsBoolean());
			}
			if (reader.has("prestige-amount")) {
				player.setPrestigeAmount(reader.get("prestige-amount").getAsInt());
			}
			if (reader.has("prestige-count")) {
				player.setPrestigeCount(reader.get("prestige-count").getAsInt());
			}
			if (reader.has("droprate-timer")) {
				player.getAttributes().setDropRateTimer(reader.get("droprate-timer").getAsInt());
			}
			if (reader.has("doubledrop-timer")) {
				player.getAttributes().setDoubleDropTimer(reader.get("doubledrop-timer").getAsInt());
			}
			if (reader.has("lifesteal-timer")) {
				player.getAttributes().setLifestealTimer(reader.get("lifesteal-timer").getAsInt());
			}
			if (reader.has("multiplier")) {
				player.setMultiplier(reader.get("multiplier").getAsDouble());
			}

			if (reader.has("game-mode")) {
				String ironmanMode = reader.get("game-mode").getAsString();
				//System.out.println("Iron man mode: " + ironmanMode);
				player.setGameMode(GameMode.valueOf(ironmanMode));
			}
			if (reader.has("player-pets")) {
				Pet.submit(player, builder.fromJson(reader.get("player-pets").getAsJsonArray(), Pet[].class));
			}
			if (reader.has("drop-kill-count"))
{
				JsonObject dkc = (JsonObject) reader.get("drop-kill-count");
				Map<Integer, Integer> tmp = new HashMap<>();
				for (int i = 0; i < 10000; i++) {
					int val = 0;
					if (dkc.has(i + "")) {
						val = dkc.get(i + "").getAsInt();
					}
					if(val != 0)
						tmp.put(i, val);
				}
				player.setDropKillCount(tmp);
			}

			if (reader.has("loyalty-title")) {
				player.setLoyaltyTitle(LoyaltyTitles.valueOf(reader.get("loyalty-title").getAsString()));
			}
			if (reader.has("prayer-bonus")) {
				player.getAttributes().setPrayerBonus(reader.get("prayer-bonus").getAsInt());
			}
			if (reader.has("melee-bonus")) {
				player.getAttributes().setMeleeBoost(reader.get("melee-bonus").getAsInt());
			}
			if (reader.has("magic-bonus")) {
				player.getAttributes().setMagicBoost(reader.get("magic-bonus").getAsInt());
			}
			if (reader.has("ranged-bonus")) {
				player.getAttributes().setRangedBoost(reader.get("ranged-bonus").getAsInt());
			}
			if (reader.has("defence-bonus")) {
				player.getAttributes().setDefBoost(reader.get("defence-bonus").getAsInt());
			}
			if (reader.has("ddr-bonus")) {
				player.ddrBonus = reader.get("ddr-bonus").getAsInt();
			}
			if (reader.has("points-multiplier")) {
				player.pointsMultiplier = reader.get("points-multiplier").getAsDouble();
			}
			if (reader.has("bills-per-kill")) {
				player.billperkill = reader.get("bills-per-kill").getAsInt();
			}

			if (reader.has("position")) {
				player.getPosition().setAs(builder.fromJson(reader.get("position"), Position.class));
			}

			if (reader.has("online-status")) {
				player.getRelations().setStatus(PrivateChatStatus.valueOf(reader.get("online-status").getAsString()),
						false);
			}

			if (reader.has("money-pouch")) {
				player.setMoneyInPouch(reader.get("money-pouch").getAsLong());
			}
			if (reader.has("given-starter")) {
				player.setReceivedStarter(reader.get("given-starter").getAsBoolean());
			}
			/*if (reader.has("kcReq")) {
				player.setKcReq(reader.get("kcReq").getAsBoolean());
			}*/

			if (reader.has("donated")) {
				player.incrementAmountDonated(reader.get("donated").getAsInt());
			}

			if (reader.has("minutes-bonus-exp")) {
				player.setMinutesBonusExp(reader.get("minutes-bonus-exp").getAsInt(), false);
			}

			if (reader.has("total-gained-exp")) {
				player.getSkillManager().setTotalGainedExp(reader.get("total-gained-exp").getAsInt());
			}

			if (reader.has("total-loyalty-points")) {
				player.getAchievementAttributes()
						.incrementTotalLoyaltyPointsEarned(reader.get("total-loyalty-points").getAsDouble());
			}

			if (reader.has("quest-points")) {
				player.setQuestPoints(reader.get("quest-points").getAsInt());
			}
			if (reader.has("quests-done")) {
				player.setQuestsDone(reader.get("quests-done").getAsInt());
			}
			if (reader.has("collect-amt")) {
				player.setCollectAmt(reader.get("collect-amt").getAsInt());
			}
			if (reader.has("quest-type")) {
				player.setQuestType(reader.get("quest-type").getAsInt());
			}
			if (reader.has("quest-level")) {
				player.setQuestLevel(reader.get("quest-level").getAsInt());
			}
			if(reader.has("dummyDamage")) {
				player.setDummyDamage(reader.get("dummyDamage").getAsInt());
			}
			if(reader.has("npcDamage")) {
				player.setNpcDamage(reader.get("npcDamage").getAsLong());
			}
			if(reader.has("npcDamageT")) {
				player.setNpcDamageT(reader.get("npcDamageT").getAsInt());
			}
			if (reader.has("bunnyhopTime")) {
				player.setBunnyhopTime(reader.get("bunnyhopTime").getAsLong());
			}

			if (reader.has("player-kills")) {
				player.getPlayerKillingAttributes().setPlayerKills(reader.get("player-kills").getAsInt());
			}

			if (reader.has("player-killstreak")) {
				player.getPlayerKillingAttributes().setPlayerKillStreak(reader.get("player-killstreak").getAsInt());
			}

			if (reader.has("player-deaths")) {
				player.getPlayerKillingAttributes().setPlayerDeaths(reader.get("player-deaths").getAsInt());
			}

			if (reader.has("target-percentage")) {
				player.getPlayerKillingAttributes().setTargetPercentage(reader.get("target-percentage").getAsInt());
			}

			if (reader.has("bh-rank")) {
				player.getAppearance().setBountyHunterSkull(reader.get("bh-rank").getAsInt());
			}

			if (reader.has("gender")) {
				player.getAppearance().setGender(Gender.valueOf(reader.get("gender").getAsString()));
			}

			if (reader.has("spell-book")) {
				player.setSpellbook(MagicSpellbook.valueOf(reader.get("spell-book").getAsString()));
			}

			if (reader.has("prayer-book")) {
				player.setPrayerbook(Prayerbook.valueOf(reader.get("prayer-book").getAsString()));
			}
			if (reader.has("running")) {
				player.setRunning(reader.get("running").getAsBoolean());
			}
			if (reader.has("run-energy")) {
				player.setRunEnergy(reader.get("run-energy").getAsInt());
			}
			if (reader.has("music")) {
				player.setMusicActive(reader.get("music").getAsBoolean());
			}
			if (reader.has("sounds")) {
				player.setSoundsActive(reader.get("sounds").getAsBoolean());
			}
			if (reader.has("auto-retaliate")) {
				player.setAutoRetaliate(reader.get("auto-retaliate").getAsBoolean());
			}
			if (reader.has("xp-locked")) {
				player.setExperienceLocked(reader.get("xp-locked").getAsBoolean());
			}
			if (reader.has("veng-cast")) {
				player.setHasVengeance(reader.get("veng-cast").getAsBoolean());
			}
			if (reader.has("last-veng")) {
				player.getLastVengeance().reset(reader.get("last-veng").getAsLong());
			}
			if (reader.has("fight-type")) {
				player.setFightType(FightType.valueOf(reader.get("fight-type").getAsString()));
			}
			if (reader.has("sol-effect")) {
				player.setStaffOfLightEffect(Integer.valueOf(reader.get("sol-effect").getAsInt()));
			}
			if (reader.has("skull-timer")) {
				player.setSkullTimer(reader.get("skull-timer").getAsInt());
			}
			if (reader.has("accept-aid")) {
				player.setAcceptAid(reader.get("accept-aid").getAsBoolean());
			}
			if (reader.has("crush-vial")) {
				player.setCrushVial(reader.get("crush-vial").getAsBoolean());
			}
			if (reader.has("poison-damage")) {
				player.setPoisonDamage(reader.get("poison-damage").getAsInt());
			}
			if (reader.has("poison-immunity")) {
				player.setPoisonImmunity(reader.get("poison-immunity").getAsInt());
			}
			if (reader.has("overload-timer")) {
				player.setOverloadPotionTimer(reader.get("overload-timer").getAsInt());
			}
			if (reader.has("fire-immunity")) {
				player.setFireImmunity(reader.get("fire-immunity").getAsInt());
			}
			if (reader.has("fire-damage-mod")) {
				player.setFireDamageModifier(reader.get("fire-damage-mod").getAsInt());
			}
			if (reader.has("overload-timer")) {
				player.setOverloadPotionTimer(reader.get("overload-timer").getAsInt());
			}
			if (reader.has("prayer-renewal-timer")) {
				player.setPrayerRenewalPotionTimer(reader.get("prayer-renewal-timer").getAsInt());
			}
			if (reader.has("teleblock-timer")) {
				player.setTeleblockTimer(reader.get("teleblock-timer").getAsInt());
			}
			if (reader.has("special-amount")) {
				player.setSpecialPercentage(reader.get("special-amount").getAsInt());
			}

			if (reader.has("summon-npc")) {
				int npc = reader.get("summon-npc").getAsInt();
				if (npc > 0)
					player.getSummoning().setFamiliarSpawnTask(new FamiliarSpawnTask(player)).setFamiliarId(npc);
			}
			if (reader.has("summon-death")) {
				int death = reader.get("summon-death").getAsInt();
				if (death > 0 && player.getSummoning().getSpawnTask() != null)
					player.getSummoning().getSpawnTask().setDeathTimer(death);
			}
			if (reader.has("process-farming")) {
				player.setProcessFarming(reader.get("process-farming").getAsBoolean());
			}

			if (reader.has("clanchat")) {
				String clan = reader.get("clanchat").getAsString();
				if (!clan.equals("null"))
					player.setClanChatName(clan);
			}
			if (reader.has("autocast")) {
				player.setAutocast(reader.get("autocast").getAsBoolean());
			}
			if (reader.has("autocast-spell")) {
				int spell = reader.get("autocast-spell").getAsInt();
				if (spell != -1)
					player.setAutocastSpell(CombatSpells.getSpell(spell));
			}

			if (reader.has("dfs-charges")) {
				player.incrementDfsCharges(reader.get("dfs-charges").getAsInt());
			}
			if (reader.has("kills")) {
				KillsTracker.submit(player, builder.fromJson(reader.get("kills").getAsJsonArray(), KillsEntry[].class));
			}

			if (reader.has("drops")) {
				DropLog.submit(player, builder.fromJson(reader.get("drops").getAsJsonArray(), DropLogEntry[].class));
			}

			if (reader.has("coins-gambled")) {
				player.getAchievementAttributes().setCoinsGambled(reader.get("coins-gambled").getAsInt());
			}

			if (reader.has("slayer-master")) {
				player.getSlayer().setSlayerMaster(SlayerMaster.valueOf(reader.get("slayer-master").getAsString()));
			}

			if (reader.has("slayer-task")) {
				player.getSlayer().setSlayerTask(SlayerTasks.valueOf(reader.get("slayer-task").getAsString()));
			}

			if (reader.has("prev-slayer-task")) {
				player.getSlayer().setLastTask(SlayerTasks.valueOf(reader.get("prev-slayer-task").getAsString()));
			}

			if (reader.has("task-amount")) {
				player.getSlayer().setAmountToSlay(reader.get("task-amount").getAsInt());
			}

			if (reader.has("task-streak")) {
				player.getSlayer().setTaskStreak(reader.get("task-streak").getAsInt());
			}
			if (reader.has("slayer-prestige")) {
				player.setSlayerPrestige(reader.get("slayer-prestige").getAsInt());
			}
			if (reader.has("group-name")) {
				player.setGroupName(reader.get("group-name").getAsString());
			}
			if (reader.has("group-leader")) {
				player.setGroupLeader(reader.get("group-leader").getAsBoolean());
			}

			if (reader.has("duo-partner")) {
				String partner = reader.get("duo-partner").getAsString();
				player.getSlayer().setDuoPartner(partner.equals("null") ? null : partner);
			}

			if (reader.has("double-slay-xp")) {
				player.getSlayer().doubleSlayerXP = reader.get("double-slay-xp").getAsBoolean();
			}

			if (reader.has("recoil-deg")) {
				player.setRecoilCharges(reader.get("recoil-deg").getAsInt());
			}

			if (reader.has("brawler-deg")) {
				player.setBrawlerCharges(builder.fromJson(reader.get("brawler-deg").getAsJsonArray(), int[].class));
			}

			if (reader.has("killed-players")) {
				List<String> list = new ArrayList<String>();
				String[] killed_players = builder.fromJson(reader.get("killed-players").getAsJsonArray(),
						String[].class);
				for (String s : killed_players)
					list.add(s);
				player.getPlayerKillingAttributes().setKilledPlayers(list);
			}

			if (reader.has("killed-gods")) {
				player.getAchievementAttributes()
						.setGodsKilled(builder.fromJson(reader.get("killed-gods").getAsJsonArray(), boolean[].class));
			}

			if (reader.has("nomad")) {
				player.getMinigameAttributes().getNomadAttributes()
						.setQuestParts(builder.fromJson(reader.get("nomad").getAsJsonArray(), boolean[].class));
			}

			if (reader.has("recipe-for-disaster")) {
				player.getMinigameAttributes().getRecipeForDisasterAttributes().setQuestParts(
						builder.fromJson(reader.get("recipe-for-disaster").getAsJsonArray(), boolean[].class));
			}

			if (reader.has("recipe-for-disaster-wave")) {
				player.getMinigameAttributes().getRecipeForDisasterAttributes()
						.setWavesCompleted((reader.get("recipe-for-disaster-wave").getAsInt()));
			}

			if (reader.has("dung-items-bound")) {
				player.getMinigameAttributes().getDungeoneeringAttributes()
						.setBoundItems(builder.fromJson(reader.get("dung-items-bound").getAsJsonArray(), int[].class));
			}

			if (reader.has("rune-ess")) {
				player.setStoredRuneEssence((reader.get("rune-ess").getAsInt()));
			}

			if (reader.has("pure-ess")) {
				player.setStoredPureEssence((reader.get("pure-ess").getAsInt()));
			}

			if (reader.has("bank-pin")) {
				player.getBankPinAttributes()
						.setBankPin(builder.fromJson(reader.get("bank-pin").getAsJsonArray(), int[].class));
			}

			if (reader.has("has-bank-pin")) {
				player.getBankPinAttributes().setHasBankPin(reader.get("has-bank-pin").getAsBoolean());
			}
			if (reader.has("last-pin-attempt")) {
				player.getBankPinAttributes().setLastAttempt(reader.get("last-pin-attempt").getAsLong());
			}
			if (reader.has("invalid-pin-attempts")) {
				player.getBankPinAttributes().setInvalidAttempts(reader.get("invalid-pin-attempts").getAsInt());
			}

			if (reader.has("bank-pin")) {
				player.getBankPinAttributes()
						.setBankPin(builder.fromJson(reader.get("bank-pin").getAsJsonArray(), int[].class));
			}

			if (reader.has("appearance")) {
				player.getAppearance().set(builder.fromJson(reader.get("appearance").getAsJsonArray(), int[].class));
			}

			if (reader.has("agility-obj")) {
				player.setCrossedObstacles(
						builder.fromJson(reader.get("agility-obj").getAsJsonArray(), boolean[].class));
			}

			if (reader.has("skills")) {
				player.getSkillManager().setSkills(builder.fromJson(reader.get("skills"), Skills.class));
			}
			if (reader.has("inventory")) {
				player.getInventory()
						.setItems(builder.fromJson(reader.get("inventory").getAsJsonArray(), Item[].class));
			}
			if (reader.has("equipment")) {
				player.getEquipment()
						.setItems(builder.fromJson(reader.get("equipment").getAsJsonArray(), Item[].class));
			}

			if (reader.has("gun-ammo")) {
				player.setGunAmmo(reader.get("gun-ammo").getAsInt(), false);
			}

			/** BANK **/
			for (int i = 0; i < 9; i++) {
				if (reader.has("bank-" + i + ""))
					player.setBank(i, new Bank(player)).getBank(i).addItems(
							builder.fromJson(reader.get("bank-" + i + "").getAsJsonArray(), Item[].class), false);
			}

			if (reader.has("bank-0")) {
				player.setBank(0, new Bank(player)).getBank(0)
						.addItems(builder.fromJson(reader.get("bank-0").getAsJsonArray(), Item[].class), false);
			}
			if (reader.has("bank-1")) {
				player.setBank(1, new Bank(player)).getBank(1)
						.addItems(builder.fromJson(reader.get("bank-1").getAsJsonArray(), Item[].class), false);
			}
			if (reader.has("bank-2")) {
				player.setBank(2, new Bank(player)).getBank(2)
						.addItems(builder.fromJson(reader.get("bank-2").getAsJsonArray(), Item[].class), false);
			}
			if (reader.has("bank-3")) {
				player.setBank(3, new Bank(player)).getBank(3)
						.addItems(builder.fromJson(reader.get("bank-3").getAsJsonArray(), Item[].class), false);
			}
			if (reader.has("bank-4")) {
				player.setBank(4, new Bank(player)).getBank(4)
						.addItems(builder.fromJson(reader.get("bank-4").getAsJsonArray(), Item[].class), false);
			}
			if (reader.has("bank-5")) {
				player.setBank(5, new Bank(player)).getBank(5)
						.addItems(builder.fromJson(reader.get("bank-5").getAsJsonArray(), Item[].class), false);
			}
			if (reader.has("bank-6")) {
				player.setBank(6, new Bank(player)).getBank(6)
						.addItems(builder.fromJson(reader.get("bank-6").getAsJsonArray(), Item[].class), false);
			}
			if (reader.has("bank-7")) {
				player.setBank(7, new Bank(player)).getBank(7)
						.addItems(builder.fromJson(reader.get("bank-7").getAsJsonArray(), Item[].class), false);
			}
			if (reader.has("bank-8")) {
				player.setBank(8, new Bank(player)).getBank(8)
						.addItems(builder.fromJson(reader.get("bank-8").getAsJsonArray(), Item[].class), false);
			}

			if (reader.has("store")) {
				Item[] validStoredItems = builder.fromJson(reader.get("store").getAsJsonArray(), Item[].class);
				if (player.getSummoning().getSpawnTask() != null)
					player.getSummoning().getSpawnTask().setValidItems(validStoredItems);
			}

			if (reader.has("charm-imp")) {
				int[] charmImpConfig = builder.fromJson(reader.get("charm-imp").getAsJsonArray(), int[].class);
				player.getSummoning().setCharmimpConfig(charmImpConfig);
			}

			if(reader.has("latestRewards")) {
				Item[] rewards = builder.fromJson(reader.get("latestRewards").getAsJsonArray(), Item[].class);
				ArrayList<Item> items = new ArrayList<Item>();
				for(Item item : rewards)
					items.add(item);
				player.megaBoxRewards = items;
			}
			
			if(reader.has("petRewards")) {
				Item[] rewards = builder.fromJson(reader.get("petRewards").getAsJsonArray(), Item[].class);
				ArrayList<Item> items = new ArrayList<Item>();
				for(Item item : rewards)
					items.add(item);
				player.petBoxRewards = items;
			}
			
			if(reader.has("donatorRewards")) {
				Item[] rewards = builder.fromJson(reader.get("donatorRewards").getAsJsonArray(), Item[].class);
				ArrayList<Item> items = new ArrayList<Item>();
				for(Item item : rewards)
					items.add(item);
				player.donatorBoxRewards = items;
			}
			
			if(reader.has("omegaBoxRewards")) {
				Item[] rewards = builder.fromJson(reader.get("omegaBoxRewards").getAsJsonArray(), Item[].class);
				ArrayList<Item> items = new ArrayList<Item>();
				for(Item item : rewards)
					items.add(item);
				player.omegaBoxRewards = items;
			}
			if(reader.has("sharpyBoxRewards")) {
				Item[] rewards = builder.fromJson(reader.get("sharpyBoxRewards").getAsJsonArray(), Item[].class);
				ArrayList<Item> items = new ArrayList<Item>();
				for(Item item : rewards)
					items.add(item);
				player.sharpyBoxRewards = items;
			}
			
			if(reader.has("torvaRewards")) {
				Item[] rewards = builder.fromJson(reader.get("torvaRewards").getAsJsonArray(), Item[].class);
				ArrayList<Item> items = new ArrayList<Item>();
				for(Item item : rewards)
					items.add(item);
				player.torvaBoxRewards = items;
			}
			
			if(reader.has("fancyRewards")) {
				Item[] rewards = builder.fromJson(reader.get("fancyRewards").getAsJsonArray(), Item[].class);
				ArrayList<Item> items = new ArrayList<Item>();
				for(Item item : rewards)
					items.add(item);
				player.fancyBoxRewards = items;
			}
			
			if(reader.has("regularRewards")) {
				Item[] rewards = builder.fromJson(reader.get("regularRewards").getAsJsonArray(), Item[].class);
				ArrayList<Item> items = new ArrayList<Item>();
				for(Item item : rewards)
					items.add(item);
				player.regularBoxRewards = items;
			}
			
			if (reader.has("friends")) {
				long[] friends = builder.fromJson(reader.get("friends").getAsJsonArray(), long[].class);

				for (long l : friends) {
					player.getRelations().getFriendList().add(l);
				}
			}
			if (reader.has("ignores")) {
				long[] ignores = builder.fromJson(reader.get("ignores").getAsJsonArray(), long[].class);

				for (long l : ignores) {
					player.getRelations().getIgnoreList().add(l);
				}
			}

			if (reader.has("loyalty-titles")) {
				player.setUnlockedLoyaltyTitles(
						builder.fromJson(reader.get("loyalty-titles").getAsJsonArray(), boolean[].class));
			}
			
			if (reader.has("offhand-minigame-completion")) {
				player.getOffhandAttributes().setCompletion(
						builder.fromJson(reader.get("offhand-minigame-completion").getAsJsonArray(), boolean[].class));
			}

			if (reader.has("offhand-minigame-stage")) {
				player.getOffhandAttributes().setStage(reader.get("offhand-minigame-stage").getAsInt());
			}
			
			if (reader.has("offhand-minigame-npc")) {
				player.getOffhandAttributes().setStageNPC(reader.get("offhand-minigame-npc").getAsInt());
			}
			
			if (reader.has("achievements-completion")) {
				player.getAchievementAttributes().setCompletion(
						builder.fromJson(reader.get("achievements-completion").getAsJsonArray(), boolean[].class));
			}

			if (reader.has("achievements-progress")) {
				player.getAchievementAttributes().setProgress(
						builder.fromJson(reader.get("achievements-progress").getAsJsonArray(), int[].class));
			}
			
			if (reader.has("zone-task-completion")) {
				player.getZoneTaskAttributes().setCompletion(builder.fromJson(reader.get("zone-task-completion").getAsJsonArray(), boolean[][].class));
			}
			
			if (reader.has("zone-task-progress")) {
				player.getZoneTaskAttributes().setProgress(
						builder.fromJson(reader.get("zone-task-progress").getAsJsonArray(), int[][].class));
			}
			if (reader.has("zone-completion")) {
				player.getZoneAttributes().setCompletion(builder.fromJson(reader.get("zone-completion").getAsJsonArray(), boolean[].class));
			}
			if (reader.has("zone-progress")) {
				player.getZoneAttributes().setProgress(
						builder.fromJson(reader.get("zone-progress").getAsJsonArray(), int[].class));
			}
			if (reader.has("zone-task-rewards")) {
				player.getZoneTaskAttributes().setRewards(builder.fromJson(reader.get("zone-task-rewards").getAsJsonArray(), boolean[][].class));
			}

			if (reader.has("max-cape-colors")) {
				int[] colors = builder.fromJson(reader.get("max-cape-colors").getAsJsonArray(), int[].class);
				player.setMaxCapeColors(colors);
			}

			if (reader.has("comp-cape-colors")) {
				int[] colors = builder.fromJson(reader.get("comp-cape-colors").getAsJsonArray(), int[].class);
				player.setCompCapeColors(colors);
			}
			if (reader.has("fav-teleports")) {
				player.getTeleport()
						.setFavourites(builder.fromJson(reader.get("fav-teleports").getAsJsonArray(), boolean[].class));
			}
			if (reader.has("yell-title")) {
				player.setYellTitle(reader.get("yell-title").getAsString());
			}
			if (reader.has("yell-colors")) {
				int[] colors = builder.fromJson(reader.get("yell-colors").getAsJsonArray(), int[].class);
				ColourSet.setColours(player, player.yellColours.set, colors);
			}
			if (reader.has("yell-preset1-colors")) {
				int[] colors = builder.fromJson(reader.get("yell-preset1-colors").getAsJsonArray(), int[].class);
				ColourSet.setColours(player, player.yellColours.presets[0], colors);
			}
			if (reader.has("yell-preset2-colors")) {
				int[] colors = builder.fromJson(reader.get("yell-preset2-colors").getAsJsonArray(), int[].class);
				ColourSet.setColours(player, player.yellColours.presets[1], colors);
			}
			if (reader.has("yell-preset3-colors")) {
				int[] colors = builder.fromJson(reader.get("yell-preset2-colors").getAsJsonArray(), int[].class);
				ColourSet.setColours(player, player.yellColours.presets[2], colors);
			}

			if (reader.has("cape-colors")) {
				int[] colors = builder.fromJson(reader.get("cape-colors").getAsJsonArray(), int[].class);
				ColourSet.setColours(player, player.capeColours.set, colors);
			}
			if (reader.has("cape-preset1-colors")) {
				int[] colors = builder.fromJson(reader.get("cape-preset1-colors").getAsJsonArray(), int[].class);
				ColourSet.setColours(player, player.capeColours.presets[0], colors);
			}
			if (reader.has("cape-preset2-colors")) {
				int[] colors = builder.fromJson(reader.get("cape-preset2-colors").getAsJsonArray(), int[].class);
				ColourSet.setColours(player, player.capeColours.presets[1], colors);
			}
			if (reader.has("cape-preset3-colors")) {
				int[] colors = builder.fromJson(reader.get("cape-preset2-colors").getAsJsonArray(), int[].class);
				ColourSet.setColours(player, player.capeColours.presets[2], colors);
			}

			if (reader.has("shop-updated")) {
				player.setShopUpdated(reader.get("shop-updated").getAsBoolean());
			}

			if (reader.has("shop-earnings")) {
				player.getPlayerOwnedShopManager().setEarnings(reader.get("shop-earnings").getAsLong());
			}

			
			for (String points : PointsManager.POINTS) {
				player.getPointsManager().create(points);
				if (reader.has("points-" + points)) {
					int value = reader.get("points-" + points).getAsInt();
					//System.out.println(points + " = " + value);
					player.getPointsManager().setPoints(points, value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return LoginResponses.LOGIN_SUCCESSFUL;
		}
		return LoginResponses.LOGIN_SUCCESSFUL;
	}
}