package com.ruthlessps.world.content.skill.impl.summoning;

import com.ruthlessps.model.definitions.NpcDefinition;
import com.ruthlessps.world.entity.impl.npc.NPC;
import com.ruthlessps.world.entity.impl.npc.Pet;
import com.ruthlessps.world.entity.impl.player.Player;

import java.util.ArrayList;
import java.util.List;

public class BossPets {

	public static enum BossPet {

		PET_GEMSTONE(585, 13453, 19665),
		PET_CLOBE(699, 701, 9385),
		PET_PROSTEX(700, 702, 9386),
		PET_REDONEX(698, 703, 9387),
		PET_BLOOTSHOT(518, 530, 9493),
		PET_WINTER(517, 529, 9492),
		PET_CAMO(515, 528, 9491),
		PET_SILVER(507, 527, 9402),
		PET_CASH(506, 526, 9414),
		PET_DARTH(505, 525, 9413),
		PET_SKY(504, 524, 9411),
		PET_PIKA(3101, 542, 9410),
		PET_SQUIRTILE(519, 541, 9409),
		PET_LUCARIO(6139, 539, 9407),
		PET_MR_KRABBS(4657, 538, 9406),
		PET_SONIC(2579, 537, 9405),
		PET_HOMER(2676, 535, 9404),
		PET_MEWTWO(508, 531, 9403),
		PET_CHARMELON(509, 532, 9401),
		PET_OREO(503, 523, 9412),
		PET_MORTY(3711, 3001, 15250),
		PET_LEFOSH(6309, 3005, 15253),
		PET_IKTOMI(6307, 6308, 15252),
		PET_RICK(3000, 3002, 15251),
		PET_AMERICAN(502, 522, 1647),
		PET_RAINBOW(799, 5222, 13730),
		PET_POINTZONE_BOSS(4500, 5224, 13731),
		PET_LEGION(3699, 5226, 13732),
		PET_ZARTHYX(3700, 5228, 13733),
		PET_RUCORD(3701, 5240, 10168),
		PET_ARCHUS(4000, 5242, 12422),
		PET_RAZIEL(401, 5244, 12423),
		PET_GORG(402, 5246, 12424),
		PET_HARNAN(404, 5248, 12425),
		PET_LANDAZAR(3250, 5260, 12426),
		PET_XINTOR(580, 5262, 12427),
		PET_ZANYTE(2261, 5264, 12428),
		PET_ARCHIE(2259, 5266, 12429),
		PET_ESTPURE(3004, 5268, 12430),
		PET_GALARS(3006, 5270, 12431),
		PET_UNICORN(3922, 3249, 5195),
		PET_RASTA(4201, 4201, 12703),
		PET_SHIELD1(3975, 3975, 3975),
		PET_SHIELD2(3977, 3977, 3977),
		PET_SHIELD3(3979, 3979, 3979),
		PET_ROC(4972, 4972, 12508),
		PET_VORTEX(3721, 3721, 7763),
		PET_VORAGO(2741, 2741, 9400),
		PET_YEAGER(440, 440, 3803),
		THIEVING_PET(3286,3286,12487),
		PET_BANDICOOT(1231, 2343, 5196);

		public static BossPet forId(int itemId) {
			for (BossPet p : BossPet.values()) {
				if (p != null && p.itemId == itemId) {
					return p;
				}
			}
			return null;
		}

		public static BossPet forSpawnId(int spawnNpcId) {
			for (BossPet p : BossPet.values()) {
				if (p != null && p.spawnNpcId == spawnNpcId) {
					return p;
				}
			}
			return null;
		}

		public int npcId, spawnNpcId, itemId;

		BossPet(int npcId, int spawnNpcId, int itemId) {
			this.npcId = npcId;
			this.spawnNpcId = spawnNpcId;
			this.itemId = itemId;
		}
	}

	public static void clearPetMenu(Player player) {
		player.getPA().sendString(28507, "");
		player.getPA().sendString(28517, "");
		player.getPA().sendString(28518, " ");
		player.getPA().sendString(28519, " ");
		player.getPA().sendString(28508, "Pet Level:  ");
		player.getPA().sendString(28509, "Pet exp.:  ");
		player.getPA().sendString(28510, "Exp. left:  ");
		player.getPA().sendString(28511, "Max hit:  ");

		int id = 29000;
		for (int i = 0; i < 100; i++) {
			player.getPA().sendString(id+i, " ");
		}
	}

	public static void buildPetMenu(Player player) {
		clearPetMenu(player);
		int id = 29000;
		for (int i = 0; i < player.getPlayerPets().size(); i++) {
			int pet_id = ((Pet)player.getPlayerPets().get(i)).getId();
			player.getPA().sendString(id+i, NpcDefinition.forId(pet_id).getName());
		}
		player.getPA().sendInterface(28500);

	}

	public static void showPetInfo(Player player, int id) {
		int default_id = 29000;
		player.getPA().sendString(28507, NpcDefinition.forId(player.getPlayerPets().get(id - default_id).getId()).getName());
		player.getPA().sendString(28508, "Pet Level:  " + player.getPlayerPets().get(id - default_id).getLevel());
		player.getPA().sendString(28509, "Pet exp.:  " + player.getPlayerPets().get(id - default_id).getExperience());
		player.getPA().sendString(28510, "Exp. left:  " + Pet.getExperienceLeft(player.getPlayerPets().get(id - default_id)));
		player.getPA().sendString(28511, "Max hit:  " + player.getPlayerPets().get(id - default_id).getMaxHit());
		player.getPA().sendString(28517, "Bonus 1:  " + player.getPlayerPets().get(id - default_id).getBonuses(0));
		player.getPA().sendString(28518, "Bonus 2:  " + player.getPlayerPets().get(id - default_id).getBonuses(1));
		player.getPA().sendString(28519, "Bonus 3:  " + player.getPlayerPets().get(id - default_id).getBonuses(2));
	}

	public static boolean pickup(Player player, NPC npc) {
		BossPet pet = BossPet.forSpawnId(npc.getId());
		if (pet != null) {
			if (player.getSummoning().getFamiliar() != null && player.getSummoning().getFamiliar().getSummonNpc() != null && player.getSummoning().getFamiliar().getSummonNpc().isRegistered()) {
				if (player.getSummoning().getFamiliar().getSummonNpc().getId() == pet.spawnNpcId && player.getSummoning().getFamiliar().getSummonNpc().getIndex() == npc.getIndex()) {
					player.getSummoning().unsummon(true, true);
					player.getPacketSender().sendMessage("You pick up your pet.");
					player.getAttributes().setPet(null);
					return true;
				} else {
					player.getPacketSender().sendMessage("This is not your pet to pick up.");
				}
			} else {
				player.getPacketSender().sendMessage("This is not your pet to pick up.");
			}
		}
		return false;
	}

}
