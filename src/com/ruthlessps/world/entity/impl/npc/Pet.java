package com.ruthlessps.world.entity.impl.npc;

import com.ruthlessps.model.definitions.NpcDefinition;
import com.ruthlessps.world.entity.impl.player.Player;

public class Pet {
    private int npcId;
    private int level;
    private int experience;
    private String[] bonuses;
    private double dropRateBoost;
    private static final int[] EXP_ARRAY = { 0, 100, 200, 400, 800, 1600, 3200, 6400, 12800, 25600};
    private static final int MAX_EXP = 30000;

    public static int getLevelForExperience(int experience) {
        if (experience < MAX_EXP) {
            for (int j = 9; j >= 0; j--) {
                if (EXP_ARRAY[j] <= experience) {
                    return j + 1;
                }
            }
        } else {
            return 10;
        }
        return 0;
    }

    public static void addExperience(Player player, int experience) {
        Pet pet = player.getAttributes().getPet();
        if (pet.experience >= MAX_EXP)
            return;
        int startingLevel = pet.level;
        pet.experience += experience;
        int newLevel = getLevelForExperience(pet.experience);
        if (newLevel > startingLevel) {
            int level = newLevel - startingLevel;
            pet.level += level;
            player.getPA().sendMessage("Your Pet has leveled up. He's now Level " + pet.level);
        }
    }

    public enum PetData {
        PET_RICK(3002, 15251, new String[] {"2% Drop Rate Boost", "", ""}, 0.02),
        PET_MORTY(3001, 15250, new String[] {"2% Drop Rate Boost", "", ""}, 0.02),

        PET_AMERICAN(522, 1647, new String[] {"5% Drop Rate Boost", "", ""}, 0.05),
        PET_OREO(523, 9412, new String[] {"5% Drop Rate Boost", "", ""}, 0.05),
        PET_SKY(524, 9411, new String[] {"5% Drop Rate Boost", "", ""}, 0.05),
        PET_DARTH(525, 9413, new String[] {"10% Drop Rate Boost", "", ""}, 0.1),
        PET_CASH(526, 9414, new String[] {"10% Drop Rate Boost", "", ""}, 0.1),
        PET_SILVER(527, 9402, new String[] {"15% Drop Rate Boost", "", ""}, 0.15),

        PET_CLOBE(701, 9385, new String[] {"5% Drop Rate Boost", "", ""}, 0.05),
        PET_PROSTEX(702, 9386, new String[] {"5% Drop Rate Boost", "", ""}, 0.05),
        PET_REDONEX(703, 9387, new String[] {"5% Drop Rate Boost", "", ""}, 0.05),
        PET_LEGION(5226, 13732, new String[] {"10% Drop Rate Boost", "", ""}, 0.1),
        PET_ZARTHYX(5228, 13733, new String[] {"10% Drop Rate Boost", "", ""}, 0.1),
        PET_RUCORD(5240, 10168, new String[] {"15% Drop Rate Boost", "", ""}, 0.15),

        PET_ARCHUS(5242, 12422, new String[] {"5% Drop Rate Boost", "", ""}, 0.05),
        PET_RAZIEL(5244, 12423, new String[] {"5% Drop Rate Boost", "", ""}, 0.05),
        PET_GORG(5246, 12424, new String[] {"5% Drop Rate Boost", "", ""}, 0.05),
        PET_HARNAN(5248, 12425, new String[] {"10% Drop Rate Boost", "", ""}, 0.1),
        PET_LANDAZAR(5260, 12426, new String[] {"10% Drop Rate Boost", "", ""}, 0.1),
        PET_XINTOR(5262, 12427, new String[] {"15% Drop Rate Boost", "", ""}, 0.15),

        PET_PIKACHU(542, 9410, new String[] {"20% Drop Rate Boost", "2x Slayer pts", ""}, 0.2),
        PET_SQUIRTLE(541, 9409, new String[] {"20% Drop Rate Boost", "2x Loyalty pts", ""}, 0.2),
        PET_LUCARIO(539, 9407, new String[] {"20% Drop Rate Boost", "Soulsplit Effect", ""}, 0.2),
        PET_MR_KRAB(538, 9406, new String[] {"20% Drop Rate Boost", "Divine Effect", ""}, 0.2),
        PET_SONIC(537, 9405, new String[] {"20% Drop Rate Boost", "3x Prestige pts", ""}, 0.2),
        PET_HOMER(535, 9404, new String[] {"20% Drop Rate Boost", "2x Boss pts", ""}, 0.2),
        PET_MEWTWO(531, 9403, new String[] {"5% Drop Rate Boost", "1.5x PZ Points", ""}, 0.05),
        PET_CHARMELEON(532, 9401, new String[] {"10% Drop Rate Boost", "2x PZ Points", ""}, 0.1),
        PET_LEFOSH(3005, 13253, new String[] {"20% Drop Rate Boost", "", ""}, 0.2),
        PET_IKTOMI(6308, 15252, new String[] {"20% Drop Rate Boost", "", ""}, 0.2),
        PET_RAINBOW_T(5222, 13730, new String[] {"15% Drop Rate Boost", "", ""}, 0.15),
        PET_RAINBOW_UNICORN(3249, 5195, new String[] {"20% Drop Rate Boost", "2x Skilling pts", ""}, 0.2),
        PET_RASTADOG(4201, 12703, new String[] {"50% Drop Rate Boost", "35% All Combat Boost", "10% Double Drop Chance"}, 0.5),
        PET_MELEE_SHIELD(3975, 3975, new String[] {"25% Drop Rate Boost", "", ""}, 0.25),
        PET_RANGED_SHIELD(3977, 3977, new String[] {"25% Drop Rate Boost", "", ""}, 0.25),
        PET_MAGIC_SHIELD(3979, 3979, new String[] {"25% Drop Rate Boost", "", ""}, 0.25),
        PET_FLYING_ROC(4972, 12508, new String[] {"15% Drop Rate Boost", "10% Ranged Boost", ""}, 0.15),
        PET_VORTEX(3721, 7763, new String[] {"10% Drop Rate Boost", "", ""}, 0.1),
        PET_VORAGO(2741, 9400, new String[] {"30% Drop Rate Boost", "3% Double Drop Chance", ""}, 0.3),
        PET_YEAGER(440, 3803, new String[] {"", "", ""}, 0.0),
        PET_BANDICOOT(2343, 5196, new String[] {"20% Drop Rate Boost", "2x Prestige pts", ""}, 0.2),

        PET_PZ_DRAGON(5224, 13731, new String[] {"15% Drop Rate Boost", "3x PZ Points", ""}, 0.15),
        PET_BLOODSHOT(530, 9493, new String[] {"10% Drop Rate Boost", "", ""}, 0.1),
        PET_WINTER(529, 9492, new String[] {"10% Drop Rate Boost", "", ""}, 0.1),
        PET_CAMO(528, 9491, new String[] {"10% Drop Rate Boost", "", ""}, 0.1),
        PET_ARCHIE(5266, 12429, new String[] {"25% Drop Rate Boost", "20% Melee Boost", ""}, 0.25),
        PET_ZANYTE(5264, 12428, new String[] {"25% Drop Rate Boost", "20% Melee Boost", ""}, 0.25),
        PET_ESTPURE(5268, 12430, new String[] {"25% Drop Rate Boost", "20% Ranged Boost", ""}, 0.25),
        PET_GALARS(5270, 12431, new String[] {"25% Drop Rate Boost", "20% Magic Boost", ""}, 0.25),
        PET_DRAGONTALE_GOD(13453, 19665, new String[] {"40% Drop Rate Boost", "20% All Combat Boost", ""}, 0.4)
        ;

        public static int forId(int itemId) {
            for(PetData p : PetData.values()) {
                if (p != null && p.itemId == itemId) {
                    return p.npcId;
                }
            }
            return -1;
        }

        public static PetData dataForId(int itemId) {
            for(PetData p : PetData.values()) {
                if (p != null && p.itemId == itemId) {
                    return p;
                }
            }
            return null;
        }

        public static double getDropRateBoost(int npcId) {
            for(PetData p : PetData.values()) {
                if (p != null && p.npcId == npcId) {
                    return p.dropRateBoost;
                }
            }
            return 0;
        }

        public int getMaxHit(int itemId) {
            for (PetData p : PetData.values()) {
                if (p != null && p.itemId == itemId) {
                    return p.maxHit;
                }
            }
            return 0;
        }


        public static String[] getBonuses(int itemId) {
            for (PetData p : PetData.values()) {
                if (p != null && p.itemId == itemId) {
                    return p.bonuses;
                }
            }
            return new String[] {"", "", ""};
        }
        private int npcId, itemId, maxHit;
        private double dropRateBoost;
        public String[] bonuses;
        PetData(int npcId, int itemId, String[] bonuses, double dropRateBoost) {
            this.npcId = npcId;
            this.itemId = itemId;
            this.bonuses = bonuses;
            this.dropRateBoost = dropRateBoost;
        }
    }

    public Pet(int npcId, int level, int experience, String[] bonuses, double dropRateBoost) {
        this.npcId = npcId;
        this.level = level;
        this.experience = experience;
        this.bonuses = bonuses;
        this.dropRateBoost = dropRateBoost;
    }

    private static int getPetIndex(Player player, int petId) {
        for(Pet pet : player.getPlayerPets()) {
            if (pet != null && pet.getId() == petId) {
                return player.getPlayerPets().indexOf(pet);
            }
        }
        return 0;
    }

    public static int getExperienceLeft(Pet pet) {
        if(pet.getLevel() < 10) {
            return pet.getExpArray()[pet.getLevel()] - pet.getExperience();
        } else {
            return 0;
        }
    }

    public int getId() {
        return this.npcId;
    }
    public int getLevel() {
        return this.level;
    }
    public int getExperience() {
        return this.experience;
    }
    public double getDropRateBoost() {
        return dropRateBoost;
    }
    public void setDropRateBoost(double boost) {
        this.dropRateBoost = boost;
    }
    private int[] getExpArray() {
        return EXP_ARRAY;
    }
    public int getMaxHit() {
        return NpcDefinition.forId(this.getId()).getMaxHit() + (NpcDefinition.forId(this.getId()).getMaxHit() / 10 * this.level - NpcDefinition.forId(this.getId()).getMaxHit() / 10);
    }
    public String getBonuses(int which) {
        return this.bonuses[which];
    }

    public static void submit(Player player, Pet[] pets) {
        for(Pet pet : pets) {
            if(pet != null) {
                submit(player, pet);
            }
        }
    }

    public static void submit(Player player, Pet pet) {
        player.getPlayerPets().add(pet);
    }

}
