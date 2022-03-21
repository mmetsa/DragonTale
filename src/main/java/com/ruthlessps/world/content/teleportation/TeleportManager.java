package com.ruthlessps.world.content.teleportation;

import com.ruthlessps.GameSettings;
import com.ruthlessps.model.Item;
import com.ruthlessps.model.PlayerRanks.DonorRights;
import com.ruthlessps.model.RegionInstance.RegionInstanceType;
import com.ruthlessps.model.GameMode;
import com.ruthlessps.model.Position;
import com.ruthlessps.model.RegionInstance;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.content.Achievements;
import com.ruthlessps.world.content.Achievements.AchievementData;
import com.ruthlessps.world.content.ColourSet;
import com.ruthlessps.world.content.ZoneTasks;
import com.ruthlessps.world.content.minigames.impl.KillGame;
import com.ruthlessps.world.content.minigames.impl.Legio;
import com.ruthlessps.world.entity.impl.player.Player;

/**
 * Handles teleporting
 * 
 * @author 2012
 * 
 */
public class TeleportManager {

	/**
	 * The favourite teleports
	 */
	private boolean[] favourites = new boolean[25];

	/**
	 * Represents the teleporting type
	 * 
	 * @author 2012
	 *
	 */
	public enum TeleportType {
		/**
		 * The melee
		 */
		MELEE(-15645, 49100),
		/**
		 * The range
		 */
		RANGED(-15644, 49200),
		/**
		 * The magic
		 */
		MAGIC(-15643, 49300),
		/**
		 * The minigames
		 */
		MINIGAME(-15642, 49400),
		/**
		 * The pvp zones
		 */
		PVP(-15641, 49500),
		/**
		 * The donor zones
		 */
		DONOR(-15640, 49600),
		/**
		 * Other zones
		 */
		OTHER(-15639, 49700);

		

		/**
		 * The button id
		 */
		private int button;

		/**
		 * The interface id
		 */
		private int interfaceId;

		/**
		 * Creates a new teleporting type
		 * 
		 * @param button
		 *            the button
		 * @param interfaceId
		 *            the interface id
		 */
		TeleportType(int button, int interfaceId) {
			this.setButton(button);
			this.setInterfaceId(interfaceId);
		}

		/**
		 * Gets the button
		 * 
		 * @return the button
		 */
		public int getButton() {
			return button;
		}

		/**
		 * Sets the button
		 * 
		 * @param button
		 *            the button to set
		 */
		public void setButton(int button) {
			this.button = button;
		}

		/**
		 * Gets the interface
		 * 
		 * @return the interfaceId
		 */
		public int getInterfaceId() {
			return interfaceId;
		}

		/**
		 * Sets the interface
		 * 
		 * @param interfaceId
		 *            the interfaceId to set
		 */
		public void setInterfaceId(int interfaceId) {
			this.interfaceId = interfaceId;
		}
	}

	/**
	 * The teleport
	 */
	public enum Teleport {
		
		TRAINING_ZONE(-16432, -13921, new Position(3305, 4975, 0)),
		PENGUIN_ZONE(-16428, -13921, new Position(3103, 9491, 0)),

		POINTZONE(23037, -13921, new Position(1952, 5027, 0)),
		MYSTERY_BOX_ZONE(23056, -13921, new Position(1886, 5473, 0)),
		MONEY_BOX_ZONE(23094, -13921, new Position(2655, 10015, 0)),
		MINECRAFT_ZONE(23075, -13921, new Position(2354, 4981, 0)),
		CAMO_WARS(-16408, -13917, new Position(2540, 5785, 0)),
		LEFOSH(-16404, -13729, new Position(2721, 4905, 0)),
		IKTOMI(-16400, -13725, new Position(2642, 4917, 2)),
		ZIVA(-16396, -13321, new Position(3417, 2983, 0)),
		VORAGO(-16392, -13721, new Position(2785, 10024, 0)),
		//VORTEX(-16432, -13725, new Position(2833, 9563, 0)),
		//ZEUS(-16428, -13725, new Position(3813, 3562, 0)),
		WARLORD(-16424, -13321, new Position(3422, 9621, 0)),
		ZULRAH(-16420, -13321, new Position(2793, 3321, 0)),
		GOD_WARS(-16416, -13725, new Position(2666, 3997, 1)),
		DONOR_CAMO_WARS(-16412, -13421, new Position(2340, 4739, 0)),
		DUEL_ARENA(-16132, -13629, new Position(3364, 3267, 0)),
		HUNGER_GAMES(-16128, -13625, new Position(2442, 3090, 0)),
		FUN_PK(-16120, -13625, new Position(1837, 5087, 2)),
		ZOMBIES(-16124, -13621, new Position(3504, 3565, 0)),
		CHAOS_TEMPLE(-16032, -13529, new Position(3239, 3619, 0)),
		EDGEVILLE_DITCH_1V1(-16028, -13525, new Position(3087, 3510, 0)),
		VARROCK_DITCH_MULTI(-16024, -13521, new Position(3246, 3501, 0)),
		WILDERNESS_CASTLE(-16020, -13517, new Position(3005, 3631, 0)),
		ROGUES_CASTLE(-16016, -13517, new Position(3307, 3916, 0)),
		BONE_YARD(-16012, -13517, new Position(3221, 3752, 0)),
		SPIDER_HILL(-16008, -13517, new Position(3146, 3872, 0)),
		GRAVEYARD(-16004, -13517, new Position(3178, 3683, 0)),
		DONOR_ZONE(-15932, -13429, new Position(2022, 4755, 0)),
		DELUXE_DONOR_ZONE(-15928, -13425, new Position(2319, 9624, 0)),
		SPONSOR_ZONE(-15924, -13421, new Position(3216, 3117, 0)),
		SUPERSPONSOR_ZONE(-15920, -13421, new Position(1762, 5088, 2)),
		DELUXE_BOSS(-15904, -13429, new Position(2025, 4256, 1)),
		SPONSOR_BOSS(-15900, -13429, new Position(2462, 4254, 0)),
		SUPERSPONSOR_BOSS(-15896, -13429, new Position(2586, 9609, 0)),
		GAMBLING_ZONE(-15832, -13329, new Position(3050, 3378, 0)),
		NOMAD(-15828, -13325, new Position(1891, 3177, 0)),
		RECIPE_FOR_DISASTER(-15824, -13321, new Position(3017, 2827, 0)),
		MELEE_MINIGAME(-16096, -13517, new Position(3177, 3030, 0)),
		RANGE_MINIGAME(-16092, -13517, new Position(2013, 4822, 0)),
		MAGIC_MINIGAME(-16088, -13517, new Position(2781, 3864, 0))
		;

		/**
		 * The button
		 */
		private int button;

		/**
		 * The favourite id
		 */
		private int favouriteId;

		/**
		 * The position
		 */
		private Position position;

		/**
		 * Creates a new teleport
		 * 
		 * @param button
		 *            the button
		 * @param favouriteId
		 *            the favourite id
		 * @param position
		 *            the position
		 */
		Teleport(int button, int favouriteId, Position position) {
			this.setButton(button);
			this.setFavouriteId(favouriteId);
			this.setPosition(position);
	}

		/**
		 * Gets the button
		 * 
		 * @return the button
		 */
		public int getButton() {
			return button;
		}

		/**
		 * Sets the button
		 * 
		 * @param button
		 *            the button to set
		 */
		public void setButton(int button) {
			this.button = button;
		}

		/**
		 * Gets the id
		 * 
		 * @return the favouriteId
		 */
		public int getFavouriteId() {
			return favouriteId;
		}

		/**
		 * Sets the id
		 * 
		 * @param favouriteId
		 *            the favouriteId to set
		 */
		public void setFavouriteId(int favouriteId) {
			this.favouriteId = favouriteId;
		}

		/**
		 * Gets the position
		 * 
		 * @return the position
		 */
		public Position getPosition() {
			return position;
		}

		/**
		 * Sets the position
		 * 
		 * @param position
		 *            the position to set
		 */
		public void setPosition(Position position) {
			this.position = position;
		}
	}

	/**
	 * Sends the favourites
	 * 
	 * @param player
	 *            the player
	 */
	public static void sendFavourites(Player player) {
		for (int i = 0; i < player.getTeleport().getFavourites().length; i++) {
			if (player.getTeleport().getFavourites()[i]) {
				player.getPacketSender().sendConfig(1200 + i, 1);
				player.getPacketSender().sendMessage(":fav:" + i);
			} else {
				player.getPacketSender().sendConfig(1200 + i, 0);
			}
		}
		ColourSet.loadColours(player);
		
	}

	/**
	 * Handles button interaction
	 * 
	 * @param player
	 *            the player
	 * @param button
	 *            the button
	 * @return the interaction
	 */
	public static boolean handleButtonInteraction(final Player player, final int button) {
		for (TeleportType type : TeleportType.values()) {
			if (type.getButton() == button) {
				player.getPacketSender().sendInterface(type.getInterfaceId());
				return true;
			}
		}
		for (Teleport teleport : Teleport.values()) {
			if (teleport.getButton() == button) {
				switch (teleport) {
				case CHAOS_TEMPLE:
				case WILDERNESS_CASTLE:
				case SPIDER_HILL:
				case GRAVEYARD:
				case BONE_YARD:
				case ROGUES_CASTLE:
					if(player.getAttributes().getPet().getId() != 0) {
						player.getPacketSender().sendMessage("<img=10><col=FF0000><shad=0>Please dismiss your familiar first");
						return false;
					}
					if (player.getInventory().containsAny(GameSettings.BANNEDWILD_ITEMS)) {
						player.sendMessage("<img=10><col=FF0000><shad=0>You have items that are not allowed in the wilderness.");
					} else if (player.getEquipment().containsAny(GameSettings.BANNEDWILD_ITEMS)) {
						player.sendMessage("<img=10><col=FF0000><shad=0>You're wearing an item that is now allowed in the wilderness.");
					} else {
						player.getPacketSender().sendInterface(35_000);
						player.setTeleportType(teleport);
					}
					return true;
					case POINTZONE:
						if(!player.getZoneTaskAttributes().getTwoOfThreeCompletion(0)) {
							player.getPA().sendMessage("You need 2/3 of The first zone completed to Teleport to Pointzone!");
							return false;
						}
						break;
					case MYSTERY_BOX_ZONE:
						if (!player.getZoneTaskAttributes().getTwoOfThreeCompletion(1)) {
							player.getPA().sendMessage("You need 2/3 of The previous zone completed to Teleport to Mystery Box Zone!");
							return false;
						}
						break;
					case MINECRAFT_ZONE:
						if(!player.getZoneTaskAttributes().getTwoOfThreeCompletion(2)) {
							player.getPA().sendMessage("You need 2/3 of The previous zone completed to Teleport to Minecraft Zone!");
							return false;
						}
						break;
					case MONEY_BOX_ZONE:
						if(!player.getZoneTaskAttributes().getTwoOfThreeCompletion(3)) {
							player.getPA().sendMessage("You need 2/3 of The previous zone completed to Teleport to Moneybag Zone!");
							return false;
						}
						break;
				/*case OREO_TORVA_ZONE:
					if(player.getKcReq()) {
						break;
					} else {
						if(player.getDropKillCount().get(502) < 40) {
							player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to kill 40 American Torvas before you can kill Oreo Torvas!");
							return false;
						}
					}dd
					break;*/
				/*case SKY_TORVA_ZONE:
					if(player.getKcReq()) {
						break;
					} else {
					if(player.getDropKillCount().get(503) < 60) {
						player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to kill 60 Oreo Torvas before you can kill Sky Torvas!");
						return false;
					}
					}
					break;*/
				case DUEL_ARENA:
					if(player.getAttributes().getPet().getId() != 0) {
						player.getPacketSender().sendMessage("@red@Please dismiss your familiar first");
						return false;
					}
					break;
				case HUNGER_GAMES:
					if(player.getAttributes().getPet().getId() != 0) {
						player.getPacketSender().sendMessage("@red@Please dismiss your familiar first");
						return false;
					}
					KillGame.addToWaitArea(player);
					return false;
					case MELEE_MINIGAME:
					case MAGIC_MINIGAME:
					case RANGE_MINIGAME:
						player.meleeKills = 0;
						break;
				case GAMBLING_ZONE:
					if(player.getAttributes().getPet().getId() != 0) {
						player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>Please dismiss your familiar first");
						return false;
					}
					break;
				/*case DARTH_MAUL_TORVA_ZONE:
					if(player.getKcReq()) {
						break;
					} else {
					if(player.getDropKillCount().get(504) < 80) {
						player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to kill 80 Sky Torvas before you can kill Darth Torvas!");
						return false;
					}
					}
					break;
				case CASH_TORVA_ZONE:
					if(player.getKcReq()) {
						break;
					} else {
					if(player.getDropKillCount().get(505) < 100) {
						player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to kill 100 Darth Torvas before you can kill Cash Torvas!");
						return false;
					}
					}
					break;
				case SILVER_TORVA_ZONE:
					if(player.getKcReq()) {
						break;
					} else {
					if(player.getDropKillCount().get(506) < 120) {
						player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to kill 120 Cash Torvas before you can kill Silver Torvas!");
						return false;
					}
					}
					break;
				case CAMO_TORVA_BOSS:
					if(player.getKcReq()) {
						break;
					} else {
					if(player.getDropKillCount().get(507) < 200) {
						player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to kill 200 Silver Torvas before you can kill Camo Bosses!");
						return false;
					}
					}
					break;
				case WINTER_TORVA_BOSS:
					if(player.getKcReq()) {
						break;
					} else {
					if(player.getDropKillCount().get(515) < 50) {
						player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to kill 50 Camo Bosses before you can kill Winter Bosses!");
						return false;
					}
					}
					break;
				case BLOODSHOT_TORVA_BOSS:
					if(player.getKcReq()) {
						break;
					} else {
					if(player.getDropKillCount().get(517) < 75) {
						player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to kill 75 Winter Bosses before you can kill Bloodshot Bosses!");
						return false;
					}
					}
					break;
				case RAINBOW:
					if(player.getKcReq()) {
						break;
					} else {
					if(player.getDropKillCount().get(518) < 100) {
						player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to kill 100 Bloodshot Bosses before you can kill Rainbow Bosses!");
						return false;
					}
					}
					break;
				case LEFOSH:
					if(player.getKcReq()) {
						break;
					} else {
					if(player.getDropKillCount().get(799) < 125) {
						player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to kill 125 Rainbow Bosses before you can kill Le'Fosh!");
						return false;
					}
					}
					break;
				case IKTOMI:
					if(player.getKcReq()) {
						break;
					} else {
					if(player.getDropKillCount().get(6309) < 150) {
						player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to kill 150 Le'Fosh Bosses before you can kill Iktomi!");
						return false;
					}
					}
					break;
					case CLOBEZONE:
						if(player.getKcReq()) {
							break;
						}
						if(player.getDropKillCount().get(3711) < 20 || player.getDropKillCount().get(3000) < 20) {
							player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to kill 20 Ricks and Morty's before you can kill Clobe Warriors!");
							return false;
						}
					break;
				case PROSTEXZONE:
					if(player.getKcReq()) {
						break;
					} else {
					if(player.getDropKillCount().get(699) < 40) {
						player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to kill 40 Clobe Warriors before you can kill Prostex Warriors!");
						return false;
					}
					}
					break;
				case REDONEXZONE:
					if(player.getKcReq()) {
						break;
					} else {
					if(player.getDropKillCount().get(700) < 60) {
						player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to kill 60 Prostex Warriors before you can kill Redonex Warriors!");
						return false;
					}
					}
					break;
				case LEGION:
					if(player.getKcReq()) {
						break;
					} else {
					if(player.getDropKillCount().get(698) < 80) {
						player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to kill 80 Redonex Warriors before you can kill Legion Warriors!");
						return false;
					}
					}
					break;
				case ZARTHYX:
					if(player.getKcReq()) {
						break;
					} else {
					if(player.getDropKillCount().get(3699) < 100) {
						player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to kill 100 Legion Warriors before you can kill Zarthyx Warriors!");
						return false;
					}
					}
					break;
				case RUCORD:
					if(player.getKcReq()) {
						break;
					} else {
					if(player.getDropKillCount().get(3700) < 120 ) {
						player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to kill 120 Zarthyx Warriors before you can kill Rucord Warriors!");
						return false;
					}
					}
					break;
				case VORTEX:
					if(player.getKcReq()) {
						break;
					} else {
					if(player.getDropKillCount().get(3701) < 175) {
						player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to kill 175 Rucord Warriors before you can kill Vortex Warriors!");
						return false;
					}
					}
					break;*/
				case VORAGO:
				case ZIVA:
					if(player.getKcReq()) {
						break;
					} else {
						final int[] total = {0};
						player.getDropKillCount().forEach((k, v) -> total[0] += v);
						if(total[0] < 1000) {
							player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to kill 1000 NPCs before you can kill Vorago/Ziva!");
							return false;
						}
					}
					break;
				/*case VORKATHMELEE:
					if(player.getKcReq()) {
						break;
					} else {
						int total = 0;
						for(int i = 0; i<= 3000; i++) {
							if(player.getDropKillCount().get(i) != null) {
								total += player.getDropKillCount().get(i);
							}
						}
						if(total < 3000) {
							player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to kill 3000 NPCs before you can kill Melee Shield Boss!");
							return false;
						}
						if(player.getDropKillCount().get(6309) < 150) {
							player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to have access to all bosses before you can kill Melee Shield Boss!");
							return false;
						}
					}
					break;
				case VORKATRANGE:
					if(player.getKcReq()) {
						break;
					} else {
						int total = 0;
						for(int i = 0; i<= 3000; i++) {
							if(player.getDropKillCount().get(i) != null) {
								total += player.getDropKillCount().get(i);
							}
						}
						if(total < 3000) {
							player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to kill 10000 NPCs before you can kill Range Shield Boss!");
							return false;
						}
						if(player.getDropKillCount().get(6309) < 150) {
							player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to have access to all bosses before you can kill Range Shield Boss!");
							return false;
						}
					}
					break;
				case VORKATHMAGIC:
					if(player.getKcReq()) {
						break;
					} else {
						int total = 0;
						for(int i = 0; i<= 3000; i++) {
							if(player.getDropKillCount().get(i) != null) {
								total += player.getDropKillCount().get(i);
							}
						}
						if(total < 3000) {
							player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to kill 3000 NPCs before you can kill Magic Shield Boss!");
							return false;
						}
						if(player.getDropKillCount().get(6309) < 150) {
							player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to have access to all bosses before you can kill Magic Shield Boss!");
							return false;
						}
					}
					break;
				case ARCHUS:
					if(player.getKcReq()) {
						break;
					} else {
						if(player.getDropKillCount().get(3711) < 20 || player.getDropKillCount().get(3000) < 20) {
							player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to kill 20 Ricks and Morty's before you can kill Archus Magicians!");
							return false;		
						}
					}
				case RAZIEL:
					if(player.getKcReq()) {
						break;
					} else {
					if(player.getDropKillCount().get(4000) < 50) {
						player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to kill 50 Archus Magicians before you can kill Raziel Magicians!");
						return false;
					}
					}
					break;
				case GORG:
					if(player.getKcReq()) {
						break;
					} else {
					if(player.getDropKillCount().get(401) < 75) {
						player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to kill 75 Raziel Magicians before you can kill Gorg Magicians!");
						return false;
					}
					}
					break;
				case HARNAN:
					if(player.getKcReq()) {
						break;
					} else {
					if(player.getDropKillCount().get(402) < 100) {
						player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to kill 100 Gorg Magicians before you can kill Harnan Magicians!");
						return false;
					}
					}
					break;
				case LANDAZAR:
					if(player.getKcReq()) {
						break;
					} else {
					if(player.getDropKillCount().get(404) < 125) {
						player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to kill 125 Harnan Magicians before you can kill Landazar Magicians!");
						return false;
					}
					}
					break;
				case XINTOR:
					if(player.getKcReq()) {
						break;
					} else {
						if(player.getDropKillCount().get(3250) < 150) {
							player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to kill 150 Landazar Magicians before you can kill Xintor Magicians!");
							return false;
						}
					}
					break;
				case ZEUS:
					if(player.getKcReq()) {
						break;
					} else {
					if(player.getDropKillCount().get(580) < 175) {
						player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>You need to kill 175 Xintor Magicians before you can kill Zeus!");
						return false;
					}
					}
					break;*/
				case DONOR_ZONE:
					if (player.getDonor().ordinal() >= DonorRights.DONOR.ordinal())
						break;
					else {
						player.sendMessage("<img=10><col=FF7400><shad=0>You must be at least a donor to teleport here.");
						return false;
					}
				case SUPERSPONSOR_ZONE:
					if (player.getDonor().ordinal() >= DonorRights.SUPER_SPONSOR.ordinal())
						break;
					else {
						player.sendMessage("<img=10><col=FF7400><shad=0>You must be at least a donor to teleport here.");
						return false;
					}
				case DONOR_CAMO_WARS:
					if (player.getDonor().ordinal() >= DonorRights.DONOR.ordinal())
						break;
					else {
						player.sendMessage("<img=10><col=FF7400><shad=0>You must be at least a donator to play this minigame.");
						return false;
					}
				case DELUXE_DONOR_ZONE:
					if (player.getDonor().ordinal() >= DonorRights.DELUXE_DONOR.ordinal())
						break;
					else {
						player.sendMessage("<img=10><col=FF7400><shad=0>You must be at least a deluxe donor to teleport here.");
						return false;
					}
				case DELUXE_BOSS:
					if (player.getDonor().ordinal() >= DonorRights.DELUXE_DONOR.ordinal())
						break;
					else {
						player.sendMessage("<img=10><col=FF7400><shad=0>You must be at least a deluxe donor to teleport here.");
						return false;
					}
				case SPONSOR_BOSS:
					if (player.getDonor().ordinal() >= DonorRights.SPONSOR.ordinal())
						break;
					else {
						player.sendMessage("<img=10><col=FF7400><shad=0>You must be at least a sponsor to teleport here.");
						return false;
					}
				case SUPERSPONSOR_BOSS:
					if (player.getDonor().ordinal() >= DonorRights.SUPER_SPONSOR.ordinal())
						break;
					else {
						player.sendMessage("<img=10><col=FF7400><shad=0>You must be at least a Super sponsor to teleport here.");
						return false;
					}
				case SPONSOR_ZONE:
					if (player.getDonor().ordinal() >= DonorRights.SPONSOR.ordinal())
						break;
					else {
						player.sendMessage("<img=10><col=FF7400><shad=0>You must be a sponsor to teleport here.");
						return false;
					}
				default:
					break;
				}
				if (teleport.getPosition().equals(new Position(0, 0, 0))) {
					player.getPacketSender().sendMessage("This teleport is not avaliable.");
					return true;
				}
				TeleportHandler.teleportPlayer(player, teleport.getPosition(), player.getSpellbook().getTeleportType());
				player.getPacketSender().sendMessage(
						"You teleport to " + Misc.ucFirst(teleport.name().toLowerCase().replaceAll("_", " ")));
				return true;
			}
			if (teleport.getFavouriteId() == button) {
				int slot = teleport.ordinal();

				int config = slot;
				if (slot > 7) {
					config += 3;
				}
				if (player.getTeleport().getFavourites()[slot]) {
					player.getPacketSender().sendConfig(1200 + config, 0);
					player.getTeleport().getFavourites()[slot] = false;
				} else {
					if (getTotalFavourites(player) == 12) {
						player.getPacketSender().sendConfig(1200 + config, 0);
						player.getTeleport().getFavourites()[slot] = false;
						player.getPacketSender().sendMessage("Your favorites category is full.");
						return true;
					}
					player.getPacketSender().sendConfig(1200 + config, 1);
					player.getTeleport().getFavourites()[slot] = true;
				}
				return true;
			}
		}
		if (button == -14822) {
			player.getTeleport().setFavourites(new boolean[25]);
		}
		return false;
	}

	/**
	 * Gets the total favourites
	 * 
	 * @param player
	 *            the player
	 * @return the total
	 */
	private static int getTotalFavourites(Player player) {
		int total = 0;
		for (boolean f : player.getTeleport().getFavourites()) {
			if (f) {
				total++;
			}
		}
		return total;
	}

	public boolean[] getFavourites() {
		return favourites;
	}

	public void setFavourites(boolean[] favourites) {
		this.favourites = favourites;
	}
}