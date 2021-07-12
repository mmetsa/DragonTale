package com.ruthlessps.world.entity.impl.player;

import com.ruthlessps.GameServer;
import com.ruthlessps.GameSettings;
import com.ruthlessps.engine.task.TaskManager;
import com.ruthlessps.engine.task.impl.BonusExperienceTask;
import com.ruthlessps.engine.task.impl.CombatSkullEffect;
import com.ruthlessps.engine.task.impl.FireImmunityTask;
import com.ruthlessps.engine.task.impl.OverloadPotionTask;
import com.ruthlessps.engine.task.impl.PlayerSkillsTask;
import com.ruthlessps.engine.task.impl.PlayerSpecialAmountTask;
import com.ruthlessps.engine.task.impl.PrayerRenewalPotionTask;
import com.ruthlessps.engine.task.impl.StaffOfLightSpecialAttackTask;
import com.ruthlessps.model.Flag;
import com.ruthlessps.model.GameMode;
import com.ruthlessps.model.Item;
import com.ruthlessps.model.Locations;
import com.ruthlessps.model.MagicSpellbook;
import com.ruthlessps.model.Position;
import com.ruthlessps.model.PlayerRanks.PlayerRights;
import com.ruthlessps.model.Skill;
import com.ruthlessps.model.Locations.Location;
import com.ruthlessps.model.container.impl.Bank;
import com.ruthlessps.model.container.impl.Equipment;
import com.ruthlessps.model.definitions.WeaponAnimations;
import com.ruthlessps.model.definitions.WeaponInterfaces;
import com.ruthlessps.net.PlayerSession;
import com.ruthlessps.net.SessionState;
import com.ruthlessps.net.security.ConnectionHandler;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.World;
import com.ruthlessps.world.content.Achievements;
import com.ruthlessps.world.content.BonusManager;
import com.ruthlessps.world.content.ColourSet;
import com.ruthlessps.world.content.Lottery;
import com.ruthlessps.world.content.PlayerLogs;
import com.ruthlessps.world.content.PlayerPanel;
import com.ruthlessps.world.content.PlayersOnline;
import com.ruthlessps.world.content.StaffList;
import com.ruthlessps.world.content.StartScreen;
import com.ruthlessps.world.content.TopList;
import com.ruthlessps.world.content.clan.ClanChatManager;
import com.ruthlessps.world.content.combat.effect.CombatPoisonEffect;
import com.ruthlessps.world.content.combat.effect.CombatTeleblockEffect;
import com.ruthlessps.world.content.combat.magic.Autocasting;
import com.ruthlessps.world.content.combat.prayer.CurseHandler;
import com.ruthlessps.world.content.combat.prayer.PrayerHandler;
import com.ruthlessps.world.content.combat.pvp.BountyHunter;
import com.ruthlessps.world.content.combat.range.DwarfMultiCannon;
import com.ruthlessps.world.content.combat.weapon.CombatSpecial;
import com.ruthlessps.world.content.skill.impl.hunter.Hunter;
import com.ruthlessps.world.content.skill.impl.slayer.Slayer;
import com.ruthlessps.world.content.teleportation.TeleportManager;

import mysql.impl.Donation;
//import mysql.impl.Hiscores;
import mysql.impl.Login;

public class PlayerHandler {

	public static void handleLogin(Player player) {
		// Register the player
		System.out.println("[World] Registering player - [username, host] : [" + player.getUsername() + ", "
				+ player.getHostAddress() + "]");		
		player.getPlayerOwnedShopManager().hookShop();
		ConnectionHandler.add(player.getHostAddress());
		World.getPlayers().add(player);
		World.updatePlayersOnline();
		PlayersOnline.add(player);
		player.getSession().setState(SessionState.LOGGED_IN);

		// Packets
		player.getPacketSender().sendMapRegion().sendDetails();
		new Thread(new Donation(player)).start();

		player.getRecordedLogin().reset();

		player.getPacketSender().sendTabs();
		if(player.getRights() != PlayerRights.ADMINISTRATOR && player.getRights() != PlayerRights.DEVELOPER && player.getRights() != PlayerRights.OWNER && player.getRights() != PlayerRights.GLOBAL_ADMINISTRATOR) {
			player.saveData();
		}
		TopList.updateLists();

		// Setting up the player's item containers..
		for (int i = 0; i < player.getBanks().length; i++) {
			if (player.getBank(i) == null) {
				player.setBank(i, new Bank(player));
			}
		}
		player.getInventory().refreshItems();
		player.getEquipment().refreshItems();

		// Weapons and equipment..
		WeaponAnimations.assign(player, player.getEquipment().get(Equipment.WEAPON_SLOT));
		WeaponInterfaces.assign(player, player.getEquipment().get(Equipment.WEAPON_SLOT));
		CombatSpecial.updateBar(player);
		BonusManager.update(player);

		// Skills
		player.getSummoning().login();
		player.getFarming().load();
		Slayer.checkDuoSlayer(player, true);
		for (Skill skill : Skill.values()) {
			player.getSkillManager().updateSkill(skill);
		}

		// Relations
		player.getRelations().setPrivateMessageId(1).onLogin(player).updateLists(true);

		// Client configurations
		player.getPacketSender().sendConfig(172, player.isAutoRetaliate() ? 1 : 0)
				.sendTotalXp(player.getSkillManager().getTotalGainedExp())
				.sendConfig(player.getFightType().getParentId(), player.getFightType().getChildId()).sendRunStatus()
				.sendRunEnergy(player.getRunEnergy()).sendString(8135, "" + player.getMoneyInPouch())
				.sendInteractionOption("Follow", 3, false).sendInteractionOption("Trade With", 4, false)
				.sendInterfaceRemoval();

		Autocasting.onLogin(player);
		PrayerHandler.deactivateAll(player);
		CurseHandler.deactivateAll(player);
		BonusManager.sendCurseBonuses(player);
		Achievements.updateInterface(player);
		// Tasks
		TaskManager.submit(new PlayerSkillsTask(player));
		if (player.isPoisoned()) {
			TaskManager.submit(new CombatPoisonEffect(player));
		}
		if (player.getPrayerRenewalPotionTimer() > 0) {
			TaskManager.submit(new PrayerRenewalPotionTask(player));
		}
		if (player.getOverloadPotionTimer() > 0) {
			TaskManager.submit(new OverloadPotionTask(player));
		}
		if (player.getTeleblockTimer() > 0) {
			TaskManager.submit(new CombatTeleblockEffect(player));
		}
		if (player.getSkullTimer() > 0) {
			player.setSkullIcon(1);
			TaskManager.submit(new CombatSkullEffect(player));
		}
		if (player.getFireImmunity() > 0) {
			FireImmunityTask.makeImmune(player, player.getFireImmunity(), player.getFireDamageModifier());
		}
		if (player.getSpecialPercentage() < 100) {
			TaskManager.submit(new PlayerSpecialAmountTask(player));
		}
		if (player.hasStaffOfLightEffect()) {
			TaskManager.submit(new StaffOfLightSpecialAttackTask(player));
		}
		if (player.getMinutesBonusExp() >= 0) {
			TaskManager.submit(new BonusExperienceTask(player));
		}
		if(player.getSpellbook() == MagicSpellbook.NORMAL) {
			player.refreshMage();
		}

		// Update appearance&&
		player.getUpdateFlag().flag(Flag.APPEARANCE);
		if (player.isCrushVial()) {
			player.getPacketSender().sendConfig(5009, 1);
		} else {
			player.getPacketSender().sendConfig(5009, 0);
		}
		// Others
		Lottery.onLogin(player);
		Locations.login(player);
		player.getPacketSender().sendMessage("Welcome to DragonTale!");
		//player.getPacketSender().sendMessage("<col=B404AE>50% off on all products, all donations will be 100% used for advertisements!");
		//player.getPacketSender().sendMessage("<col=B404AE>50% discount ends whenever timer runs out on :: store page!");
		//player.getPacketSender().sendMessage("@red@You can donate via paypal, skrill, rsgp, giftcards and steam skins!!");
		if (player.experienceLocked())
			player.getPacketSender().sendMessage("@red@Warning: your experience is currently locked.");
		ClanChatManager.handleLogin(player);

		if (GameSettings.BONUS_EXP) {
 			player.getPacketSender()
					.sendMessage("@blu@It is currently double exp on DragonTale, take advantage of it!");
		}
		player.sendMessage("<img=10><col=0055FF>Make sure to check out ::beginner ,::top10,::referral,::guides!");
		player.sendMessage("<img=10><col=0055FF>Donation Methods : PP/Skrill/Crypto/Steam/RS/GiftCards");
		Login.login();
		PlayerPanel.refreshPanel(player);
		// New player
		if (player.newPlayer()) {
			World.sendMessage("<img=4><col=610B5E> [NEW PLAYER] <col=10781B>" + player.getUsername() + " <col=610B5E>has just joined " + GameSettings.SERVER_NAME + ", welcome them! :)");
			StartScreen.open(player);
			player.setPlayerLocked(true);
		}
		
		if (player.getPlayerOwnedShopManager().getEarnings() > 0) {
			player.sendMessage("<col=FF0000>You have unclaimed earnings in your player owned shop!");
		}
		
		player.getPacketSender().updateSpecialAttackOrb().sendIronmanMode(player.getGameMode().ordinal());
		if (player.getRights() == PlayerRights.MODERATOR || player.getRights() == PlayerRights.TRIAL_MODERATOR || player.getRights() == PlayerRights.TRIAL_FORUM_MODERATOR
				|| player.getRights() == PlayerRights.GLOBAL_MODERATOR
				|| player.getRights() == PlayerRights.ADMINISTRATOR || player.getRights() == PlayerRights.FORUM_MODERATOR
						|| player.getRights() == PlayerRights.DEVELOPER || player.getRights() == PlayerRights.FORUM_ADMINISTRATOR
				|| player.getRights() == PlayerRights.GLOBAL_ADMINISTRATOR || player.getRights() == PlayerRights.HELPER || player.getRights() == PlayerRights.WEB_DEVELOPER) {
			World.sendMessage("<img=15><col=6600CC> " + Misc.formatText(player.getRights().toString().toLowerCase())
					+ " " + player.getUsername() + " has just logged in, feel free to message them for support.");
		}
		if (player.getRights() == PlayerRights.MODERATOR || player.getRights() == PlayerRights.ADMINISTRATOR
				|| player.getRights() == PlayerRights.DEVELOPER
				|| player.getRights() == PlayerRights.FORUM_ADMINISTRATOR
				|| player.getRights() == PlayerRights.FORUM_MODERATOR
				|| player.getRights() == PlayerRights.GLOBAL_ADMINISTRATOR
				|| player.getRights() == PlayerRights.GLOBAL_MODERATOR || player.getRights() == PlayerRights.OWNER
				|| player.getRights() == PlayerRights.TRIAL_FORUM_MODERATOR
				|| player.getRights() == PlayerRights.TRIAL_MODERATOR
				|| player.getRights() == PlayerRights.HELPER
				|| player.getRights() == PlayerRights.WEB_DEVELOPER || player.getRights() == PlayerRights.YOUTUBER
				|| player.getRights() == PlayerRights.GFX_ARTIST || player.getRights() == PlayerRights.ITEM_MODELLER) {
			StaffList.login(player);
		}
		ColourSet.loadColours(player);
		StaffList.updateGlobalInterface();
		ClanChatManager.join(player, "Help");
		TeleportManager.sendFavourites(player);
		if (player.getPointsManager().getPoints("achievement") == 0) {
			Achievements.setPoints(player);
		}
		PlayerLogs.log(player.getUsername(),
				"Login from host " + player.getHostAddress() + ", serial number: " + player.getSerialNumber());
	}

	public static boolean handleLogout(Player player) {
		try {

			PlayerSession session = player.getSession();
			if(!(session.getChannel() == null)) {
				if (session.getChannel().isOpen()) {
					session.getChannel().close();
				}
			}

			if (!player.isRegistered()) {
				return true;
			}

			boolean exception = GameServer.isUpdating()
					|| World.getLogoutQueue().contains(player) && player.getLogoutTimer().elapsed(90000);
			if (player.logout() || exception) {
				System.out.println("[World] Deregistering player - [username, host] : [" + player.getUsername() + ", "
						+ player.getHostAddress() + "]");
				player.getSession().setState(SessionState.LOGGING_OUT);
				ConnectionHandler.remove(player.getHostAddress());
				player.setTotalPlayTime(player.getTotalPlayTime() + player.getRecordedLogin().elapsed());
				player.getPacketSender().sendInterfaceRemoval();
				if (player.getCannon() != null) {
					DwarfMultiCannon.pickupCannon(player, player.getCannon(), true);
				}
				if (exception && player.getResetPosition() != null) {
					player.moveTo(player.getResetPosition());
					player.setResetPosition(null);
				}
				if (player.getRegionInstance() != null) {
					player.getRegionInstance().destruct();
				}
				if(!player.getGroupName().equals("NONE")) {
					if(player.isGroupLeader()) {
						player.getGroupSlayer().deleteGroup();
					} else {
						player.getGroupSlayer().quitGroup();
					}
				}
				if(Location.inMinigame(player)) {
					player.moveTo(GameSettings.DEFAULT_POSITION);
				}
				if(player.getLocation() == Location.BUNNYHOP) {
					player.moveTo(GameSettings.DEFAULT_POSITION);
					PlayerProcess.bunnyhop = 0;
					
				}
				player.getTrading().declineTrade(true);
				boolean debugMessage = false;
				int[] playerXP = new int[Skill.values().length];
				for (int i = 0; i <  Skill.values().length; i++) {
					playerXP[i] = player.getSkillManager().getExperience(Skill.forId(i));
				}
				if(player.getRights() != PlayerRights.DEVELOPER && player.getRights() != PlayerRights.ADMINISTRATOR && player.getRights() != PlayerRights.OWNER) {
					if(player.getGameMode() == GameMode.NORMAL) {
						com.everythingrs.hiscores.Hiscores.update("qpxi3atu4zqw10b3apnxc4n29f1r93qlcvo4g2ecm8us8dj9k94o1gpzaubolswteh6kxebnvcxr",  "Normal Mode", player.getUsername(),
								player.getRights().ordinal(), playerXP, debugMessage);
					} else if(player.getGameMode() == GameMode.HARDCORE) {
						com.everythingrs.hiscores.Hiscores.update("qpxi3atu4zqw10b3apnxc4n29f1r93qlcvo4g2ecm8us8dj9k94o1gpzaubolswteh6kxebnvcxr",  "Hardcore Mode", player.getUsername(),
								player.getRights().ordinal(), playerXP, debugMessage);
					} else if(player.getGameMode() == GameMode.IRONMAN) {
						com.everythingrs.hiscores.Hiscores.update("qpxi3atu4zqw10b3apnxc4n29f1r93qlcvo4g2ecm8us8dj9k94o1gpzaubolswteh6kxebnvcxr",  "Ironman Mode", player.getUsername(),
								player.getRights().ordinal(), playerXP, debugMessage);
					}
				}
				if(player.getRights() != PlayerRights.ADMINISTRATOR && player.getRights() != PlayerRights.DEVELOPER && player.getRights() != PlayerRights.OWNER && player.getRights() != PlayerRights.GLOBAL_ADMINISTRATOR) {
					player.saveData();
				}
				if(player.getSuperior()) {
					World.deregister(player.getSuperiorId());
					player.setSuperior(false);
				}
				StaffList.logout(player);
				StaffList.updateGlobalInterface();
				Hunter.handleLogout(player);
				Locations.logout(player);
				player.getSummoning().unsummon(false, false);
				player.getFarming().save();
				player.getPlayerOwnedShopManager().unhookShop();
				//new Hiscores(player).run();
				BountyHunter.handleLogout(player);
				ClanChatManager.leave(player, false);
				player.getRelations().updateLists(false);
				PlayersOnline.remove(player);
				TaskManager.cancelTasks(player.getCombatBuilder());
				TaskManager.cancelTasks(player);
				player.save();
				World.getPlayers().remove(player);
				session.setState(SessionState.LOGGED_OUT);
				World.updatePlayersOnline();
				Login.login();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}
