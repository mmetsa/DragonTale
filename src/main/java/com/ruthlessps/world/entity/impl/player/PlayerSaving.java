package com.ruthlessps.world.entity.impl.player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ruthlessps.GameServer;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.content.ColourSet;
import com.ruthlessps.world.content.PointsManager;

public class PlayerSaving {

	private static boolean playerExists(String p) {
		p = Misc.formatPlayerName(p.toLowerCase());
		return new File("./data/saves/characters/" + p + ".json").exists();
	}

	public static void getSavedData(Player p, String username) throws IOException {
		if (!playerExists(username)) {
			p.getPacketSender().sendMessage("This player does not exist.");
			return;
		}
		Path path = Paths.get("./data/saves/characters/", username + ".json");
		File file = path.toFile();
		parseJson(p, file);
	}

	private static void parseJson(Player p, File f) throws IOException {
		try (FileReader fileReader = new FileReader(f)) {
			JsonParser fileParser = new JsonParser();
			JsonObject reader = (JsonObject) fileParser.parse(fileReader);
			if (reader.has("ip")) {
				p.ipToUnban = reader.get("ip").toString().substring(1, (reader.get("ip").toString().length() - 1));
			}
			if (reader.has("mac")) {
				p.macToUnban = reader.get("mac").toString().substring(1, (reader.get("mac").toString().length() - 1));
			}
			if (reader.has("uuid")) {
				p.uuidToUnban = reader.get("uuid").toString().substring(1,
						(reader.get("uuid").toString().length() - 1));
			}
		}
	}

	public static void save(Player player) {
		// Create the path and file objects.
		Path path = Paths.get("./data/saves/characters/", player.getUsername() + ".json");
		File file = path.toFile();
		file.getParentFile().setWritable(true);
		// Attempt to make the player save directory if it doesn't
		// exist.
		if (!file.getParentFile().exists()) {
			try {
				file.getParentFile().mkdirs();
			} catch (SecurityException e) {
				System.out.println("Unable to create directory for player data!");
			}
		}
		try (FileWriter writer = new FileWriter(file)) {

			Gson builder = new GsonBuilder().setPrettyPrinting().create();
			JsonObject object = new JsonObject();
			object.addProperty("total-play-time-ms", player.getTotalPlayTime());
			object.addProperty("username", player.getUsername().trim());
			object.addProperty("password", player.getPassword().trim());
			object.addProperty("email", player.getEmailAddress() == null ? "null" : player.getEmailAddress().trim());
			object.addProperty("staff-rights", player.getRights().name());
			object.addProperty("donor-rights", player.getDonor().name());
			object.addProperty("ip", player.getHostAddress());
			object.addProperty("mac", player.getMac());
			object.addProperty("uuid", player.getSerialNumber());
			object.addProperty("modeler", player.isModeler());
			object.addProperty("gambler", player.isGambler());
			object.addProperty("gfx-designer", player.isGfxDesigner());
			object.addProperty("youtube", player.isYoutuber());
			object.addProperty("game-mode", player.getGameMode().name());
			object.addProperty("loyalty-title", player.getLoyaltyTitle().name());
			object.add("position", builder.toJsonTree(player.getPosition()));
			object.addProperty("online-status", player.getRelations().getStatus().name());
			object.addProperty("given-starter", player.didReceiveStarter());
			object.addProperty("kcReq", player.getKcReq());
			object.addProperty("money-pouch", player.getMoneyInPouch());
			object.addProperty("donated", (long) player.getAmountDonated());
			object.addProperty("minutes-bonus-exp", player.getMinutesBonusExp());
			object.addProperty("total-gained-exp", player.getSkillManager().getTotalGainedExp());
			object.addProperty("total-loyalty-points",
					player.getAchievementAttributes().getTotalLoyaltyPointsEarned());
			object.addProperty("quest-points", player.getQuestPoints());
			object.addProperty("quests-done", player.getQuestsDone());
			object.addProperty("collect-amt", player.getCollectAmt());
			object.addProperty("infinitePrayer", player.getInfinitePrayer());
			object.addProperty("quest-type", player.getQuestType());
			object.addProperty("quest-level", player.getQuestLevel());
			object.addProperty("bunnyhopTime", player.getBunnyhopTime());
			object.addProperty("dummyDamage", player.getDummyDamage());
			object.addProperty("npcDamage", player.getNpcDamage());
			object.addProperty("npcDamageT", player.getNpcDamageT());
			object.addProperty("npcKills", player.getNpcKills());
			object.addProperty("toggleKC", player.getToggleKC());
			object.addProperty("voteAmt", player.getVoteAmt());
			object.addProperty("player-kills", player.getPlayerKillingAttributes().getPlayerKills());
			object.addProperty("player-killstreak",
					player.getPlayerKillingAttributes().getPlayerKillStreak());
			object.addProperty("player-deaths", player.getPlayerKillingAttributes().getPlayerDeaths());
			object.addProperty("target-percentage",
					player.getPlayerKillingAttributes().getTargetPercentage());
			object.addProperty("bh-rank", player.getAppearance().getBountyHunterSkull());
			object.addProperty("gender", player.getAppearance().getGender().name());
			object.addProperty("spell-book", player.getSpellbook().name());
			object.addProperty("prayer-book", player.getPrayerbook().name());
			object.addProperty("running", player.isRunning());
			object.addProperty("run-energy", player.getRunEnergy());
			object.addProperty("music", player.musicActive());
			object.addProperty("sounds", player.soundsActive());
			object.addProperty("auto-retaliate", player.isAutoRetaliate());
			object.addProperty("xp-locked", player.experienceLocked());
			object.addProperty("veng-cast", player.hasVengeance());
			object.addProperty("last-veng", player.getLastVengeance().elapsed());
			object.addProperty("fight-type", player.getFightType().name());
			object.addProperty("sol-effect", player.getStaffOfLightEffect());
			object.addProperty("skull-timer", player.getSkullTimer());
			object.addProperty("accept-aid", player.isAcceptAid());
			object.addProperty("crush-vial", player.isCrushVial());
			object.addProperty("poison-damage", player.getPoisonDamage());
			object.addProperty("poison-immunity", player.getPoisonImmunity());
			object.addProperty("overload-timer", player.getOverloadPotionTimer());
			object.addProperty("fire-immunity", player.getFireImmunity());
			object.addProperty("fire-damage-mod", player.getFireDamageModifier());
			object.addProperty("prayer-renewal-timer", player.getPrayerRenewalPotionTimer());
			object.addProperty("teleblock-timer", player.getTeleblockTimer());
			object.addProperty("special-amount", player.getSpecialPercentage());
			object.addProperty("summon-npc",
					player.getSummoning().getFamiliar() != null
							? player.getSummoning().getFamiliar().getSummonNpc().getId()
							: -1);
			object.addProperty("summon-death",
					player.getSummoning().getFamiliar() != null
							? player.getSummoning().getFamiliar().getDeathTimer()
							: -1);
			object.addProperty("prestige-amount", player.getPrestigeAmount());
			object.addProperty("prestige-count", player.getPrestigeCount());
			object.addProperty("multiplier", player.getMultiplier());
			object.addProperty("droprate-timer", player.getAttributes().getDropRateTimer());
			object.addProperty("doubledrop-timer", player.getAttributes().getDoubleDropTimer());
			object.addProperty("lifesteal-timer", player.getAttributes().getLifestealTimer());
			object.addProperty("process-farming", player.shouldProcessFarming());
			object.addProperty("clanchat", player.getClanChatName() == null ? "null" : player.getClanChatName().trim());
			object.addProperty("autocast", player.isAutocast());
			object.addProperty("autocast-spell",
					player.getAutocastSpell() != null ? player.getAutocastSpell().spellId() : -1);
			object.addProperty("dfs-charges", player.getDfsCharges());
			object.addProperty("coins-gambled", player.getAchievementAttributes().getCoinsGambled());
			object.addProperty("slayer-master", player.getSlayer().getSlayerMaster().name());
			object.addProperty("slayer-task", player.getSlayer().getSlayerTask().name());
			object.addProperty("prev-slayer-task", player.getSlayer().getLastTask().name());
			object.addProperty("task-amount", player.getSlayer().getAmountToSlay());
			object.addProperty("task-streak", player.getSlayer().getTaskStreak());
			object.addProperty("slayer-prestige", player.getSlayerPrestige());
			object.addProperty("group-name", player.getGroupName());
			object.addProperty("group-leader", player.isGroupLeader());
			object.addProperty("duo-partner",
					player.getSlayer().getDuoPartner() == null ? "null" : player.getSlayer().getDuoPartner());
			object.addProperty("double-slay-xp", player.getSlayer().doubleSlayerXP);
			object.addProperty("recoil-deg", player.getRecoilCharges());
			object.addProperty("prayer-bonus", player.getAttributes().getPrayerBonus());
			object.addProperty("melee-bonus", player.getAttributes().getMeleeBoost());
			object.addProperty("magic-bonus", player.getAttributes().getMagicBoost());
			object.addProperty("ranged-bonus", player.getAttributes().getRangedBoost());
			object.addProperty("defence-bonus", player.getAttributes().getDefBoost());
			object.addProperty("ddr-bonus", player.ddrBonus);
			object.addProperty("points-multiplier", player.pointsMultiplier);
			object.addProperty("bills-per-kill", player.billperkill);
			object.add("brawler-deg", builder.toJsonTree(player.getBrawlerChargers()));
			object.add("player-pets", builder.toJsonTree(player.getPlayerPets()));
			object.add("killed-players", builder.toJsonTree(player.getPlayerKillingAttributes().getKilledPlayers()));
			object.add("nomad",
					builder.toJsonTree(player.getMinigameAttributes().getNomadAttributes().getQuestParts()));

			Gson gson = new Gson();
			JsonElement dropJson = builder.fromJson(gson.toJson(player.getDropKillCount()), JsonElement.class);
			object.add("drop-kill-count", dropJson);

			object.add("recipe-for-disaster", builder
					.toJsonTree(player.getMinigameAttributes().getRecipeForDisasterAttributes().getQuestParts()));
			object.addProperty("recipe-for-disaster-wave",
					player.getMinigameAttributes().getRecipeForDisasterAttributes().getWavesCompleted());
			object.add("dung-items-bound",
					builder.toJsonTree(player.getMinigameAttributes().getDungeoneeringAttributes().getBoundItems()));
			object.addProperty("rune-ess", player.getStoredRuneEssence());
			object.addProperty("pure-ess", player.getStoredPureEssence());
			object.addProperty("has-bank-pin", player.getBankPinAttributes().hasBankPin());
			object.addProperty("last-pin-attempt", player.getBankPinAttributes().getLastAttempt());
			object.addProperty("invalid-pin-attempts", player.getBankPinAttributes().getInvalidAttempts());
			object.add("bank-pin", builder.toJsonTree(player.getBankPinAttributes().getBankPin()));
			object.add("appearance", builder.toJsonTree(player.getAppearance().getLook()));
			object.add("agility-obj", builder.toJsonTree(player.getCrossedObstacles()));
			object.add("skills", builder.toJsonTree(player.getSkillManager().getSkills()));
			object.add("inventory", builder.toJsonTree(player.getInventory().getItems()));
			object.add("equipment", builder.toJsonTree(player.getEquipment().getItems()));
			object.addProperty("gun-ammo", player.getGunAmmo());
			object.add("bank-0", builder.toJsonTree(player.getBank(0).getValidItems()));
			object.add("bank-1", builder.toJsonTree(player.getBank(1).getValidItems()));
			object.add("bank-2", builder.toJsonTree(player.getBank(2).getValidItems()));
			object.add("bank-3", builder.toJsonTree(player.getBank(3).getValidItems()));
			object.add("bank-4", builder.toJsonTree(player.getBank(4).getValidItems()));
			object.add("bank-5", builder.toJsonTree(player.getBank(5).getValidItems()));
			object.add("bank-6", builder.toJsonTree(player.getBank(6).getValidItems()));
			object.add("bank-7", builder.toJsonTree(player.getBank(7).getValidItems()));
			object.add("bank-8", builder.toJsonTree(player.getBank(8).getValidItems()));
			/* STORE SUMMON */
			if (player.getSummoning().getBeastOfBurden() != null) {
				object.add("store", builder.toJsonTree(player.getSummoning().getBeastOfBurden().getValidItems()));
			}
			object.add("charm-imp", builder.toJsonTree(player.getSummoning().getCharmImpConfigs()));
			object.add("latestRewards", builder.toJsonTree(player.megaBoxRewards.toArray()));
			object.add("petRewards", builder.toJsonTree(player.petBoxRewards.toArray()));
			object.add("torvaRewards", builder.toJsonTree(player.torvaBoxRewards.toArray()));
			object.add("fancyRewards", builder.toJsonTree(player.fancyBoxRewards.toArray()));
			object.add("donatorRewards", builder.toJsonTree(player.donatorBoxRewards.toArray()));
			object.add("omegaBoxRewards", builder.toJsonTree(player.omegaBoxRewards.toArray()));
			object.add("sharpyBoxRewards", builder.toJsonTree(player.sharpyBoxRewards.toArray()));
			object.add("regularRewards", builder.toJsonTree(player.regularBoxRewards.toArray()));
			object.add("friends", builder.toJsonTree(player.getRelations().getFriendList().toArray()));
			object.add("ignores", builder.toJsonTree(player.getRelations().getIgnoreList().toArray()));
			object.add("loyalty-titles", builder.toJsonTree(player.getUnlockedLoyaltyTitles()));
			object.add("kills", builder.toJsonTree(player.getKillsTracker().toArray()));
			object.add("drops", builder.toJsonTree(player.getDropLog().toArray()));
			object.add("achievements-completion",
					builder.toJsonTree(player.getAchievementAttributes().getCompletion()));
			object.add("achievements-progress", builder.toJsonTree(player.getAchievementAttributes().getProgress()));
			object.add("zone-task-progress", builder.toJsonTree(player.getZoneTaskAttributes().getProgress()));
			object.add("zone-task-completion", builder.toJsonTree(player.getZoneTaskAttributes().getCompletion()));
			object.add("zone-progress", builder.toJsonTree(player.getZoneAttributes().getProgress()));
			object.add("zone-completion", builder.toJsonTree(player.getZoneAttributes().getCompletion()));
			object.add("zone-task-rewards", builder.toJsonTree(player.getZoneTaskAttributes().getRewards()));
			object.add("offhand-minigame-completion", builder.toJsonTree(player.getOffhandAttributes().getStageCompletion()));
			object.add("offhand-minigame-stage", builder.toJsonTree(player.getOffhandAttributes().getStage()));
			object.add("offhand-minigame-npc", builder.toJsonTree(player.getOffhandAttributes().getStageNPC()));
			object.add("max-cape-colors", builder.toJsonTree(player.getMaxCapeColors()));
			object.add("comp-cape-colors", builder.toJsonTree(player.getCompCapeColors()));
			object.add("fav-teleports", builder.toJsonTree(player.getTeleport().getFavourites()));
			
			object.addProperty("yell-title", player.getYellTitle());
			object.add("yell-colors", builder.toJsonTree(ColourSet.toArray(player.yellColours.set)));
			object.add("yell-preset1-colors", builder.toJsonTree(ColourSet.toArray(player.yellColours.presets[0])));
			object.add("yell-preset2-colors", builder.toJsonTree(ColourSet.toArray(player.yellColours.presets[1])));
			object.add("yell-preset3-colors", builder.toJsonTree(ColourSet.toArray(player.yellColours.presets[2])));

			object.add("cape-colors", builder.toJsonTree(ColourSet.toArray(player.capeColours.set)));
			object.add("cape-preset1-colors", builder.toJsonTree(ColourSet.toArray(player.capeColours.presets[0])));
			object.add("cape-preset2-colors", builder.toJsonTree(ColourSet.toArray(player.capeColours.presets[1])));
			object.add("cape-preset3-colors", builder.toJsonTree(ColourSet.toArray(player.capeColours.presets[2])));

			object.addProperty("shop-updated", player.isShopUpdated());
			object.addProperty("shop-earnings", player.getPlayerOwnedShopManager().getEarnings());
			
			for (String points : PointsManager.POINTS) {
				object.addProperty("points-" + points, player.getPointsManager().getPoints(points));
			}
			writer.write(builder.toJson(object));
		} catch (Exception e) {
			// An error happened while saving.
			GameServer.getLogger().log(Level.WARNING, "An error has occured while saving a character file!", e);
		}
	}
}
