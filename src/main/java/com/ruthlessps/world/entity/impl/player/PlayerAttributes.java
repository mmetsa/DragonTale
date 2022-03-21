package com.ruthlessps.world.entity.impl.player;

import com.ruthlessps.model.Flag;
import com.ruthlessps.model.Item;
import com.ruthlessps.world.content.HourlyNpc;
import com.ruthlessps.world.content.WellOfGoodwill;
import com.ruthlessps.world.entity.impl.npc.Pet;

import java.util.Objects;


/**
 * This class is responsible for handling various boosts that a Player might have.
 * This is mainly made so that the Player class itself is more clean.
 * @author Maiko Metsalu
 */
public class PlayerAttributes {

    private final Player player;
    private Pet pet;
    private int dropRateTimer;
    private int doubleDropTimer;
    private int lifestealTimer;
    private int meleeBoost;
    private int rangedBoost;
    private int magicBoost;
    private int defBoost;
    private int prayerBonus;

    PlayerAttributes(Player player) {
        this.player = player;
    }

    public int getPrayerBonus() {
        return this.prayerBonus;
    }

    public void setPrayerBonus(int prayerBonus) {
        this.prayerBonus = prayerBonus;
    }

    public void transformToNPC(int npcId) {
        player.setNpcTransformationId(npcId);
        player.getUpdateFlag().flag(Flag.APPEARANCE);
    }

    public void incrementMeleeBoost() {
        this.meleeBoost++;
    }

    public void setMeleeBoost(int meleeBoost) {
        this.meleeBoost = meleeBoost;

    }

    public int getMeleeBoost() {
        return this.meleeBoost;
    }

    public double getMeleeMultiplier() {
        if(meleeBoost < 20_000) {
            return 1 + (double)meleeBoost / 100_000;
        } else {
            return 1.2;
        }
    }

    public int getRangedBoost() {
        return this.meleeBoost;
    }

    public double getRangedMultiplier() {
        if(rangedBoost < 20_000) {
            return 1 + (double)rangedBoost / 100_000;
        } else {
            return 1.2;
        }
    }

    public void incrementRangedBoost() {
        this.rangedBoost++;
    }

    public void setRangedBoost(int rangedBoost) {
        this.rangedBoost = rangedBoost;

    }

    public void incrementMagicBoost() {
        this.magicBoost++;
    }

    public void setMagicBoost(int magicBoost) {
        this.magicBoost = magicBoost;

    }

    public int getMagicBoost() {
        return this.magicBoost;
    }

    public double getMagicMultiplier() {
        if(magicBoost < 20_000) {
            return 1 + (double)magicBoost / 100_000;
        } else {
            return 1.2;
        }
    }

    public void incrementDefBoost() {
        this.defBoost++;
    }

    public void setDefBoost(int defBoost) {
        this.defBoost = defBoost;

    }

    public int getDefBoost() {
        return this.defBoost;
    }

    public double getDefMultiplier() {
        if(defBoost < 20_000) {
            return 1 + (double)defBoost / 100_000;
        } else {
            return 1.2;
        }
    }


    /**
     * Calculates a Player's bonus drop rate
     * Takes into account various Player attributes, such as
     * Donator Rank, Worn items, Pets, etc.
     * The Maximum Bonus Drop rate is 10.0, which means the Player has 100% Drop Rate
     *
     * @return bonus drop rate
     */
    public double calculateBonusDropRate(int npcId) {
        double bonusDropRate = 0;

        bonusDropRate += player.getDonor().getDropRateBoost();

        if(player.getAttributes().getPet() != null) {
            bonusDropRate += player.getAttributes().getPet().getDropRateBoost();
        }
        bonusDropRate += player.getGameMode().getDropRateBoost();

        for (Item item : player.getEquipment().getItems()) {
            bonusDropRate += item.getDefinition().getDropRateBoost();
        }

        if(WellOfGoodwill.isActive()) {
            bonusDropRate += 0.10;
        }

        if(dropRateTimer > 1) {
            bonusDropRate += 0.1;
        }

        if (player.getNpcTransformationId() != 0) {
            int id = player.getNpcTransformationId();
            if(id == 1864 || id == 640 || id == 1235 || id == 1230 || id == 1233) {
                bonusDropRate += 0.15;
            }
        }
        if(npcId == player.getSlayer().getSlayerTask().getNpcId()) {
            if(player.getEquipment().contains(15497)) {
                bonusDropRate+= 0.02;
            } else if(player.getEquipment().contains(14637)) {
                bonusDropRate+= 0.04;
            } else if(player.getEquipment().contains(15492)) {
                bonusDropRate+= 0.06;
            } else if(player.getEquipment().contains(14636)) {
                bonusDropRate+= 0.08;
            } else if(player.getEquipment().contains(3317)) {
                bonusDropRate+= 0.1;
            }
        }

        if(npcId == HourlyNpc.CURRENT_NPC) {
            bonusDropRate += 0.2;
        }

        return bonusDropRate;
    }

    int getDropRateTimer() {
        return dropRateTimer;
    }

    public void setDropRateTimer(int timer) {
        this.dropRateTimer = timer;
    }

    /**
     * Decrement Player bonus boost timers
     * This method is called out each game tick.
     */
    void decrementTimers() {
        if(dropRateTimer >= 1) {
            dropRateTimer--;
            if (dropRateTimer < 1) {
                player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>Your drop rate boost effect has expired!");
            }
        }
        if(doubleDropTimer >= 1) {
            doubleDropTimer--;
            if (doubleDropTimer < 1) {
                player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>Your double drop boost effect has expired!");
            }
        }
        if(lifestealTimer >= 1) {
            lifestealTimer--;
            if (lifestealTimer < 1) {
                player.getPacketSender().sendMessage("<img=10><col=FF7400><shad=0>Your lifesteal effect has expired!");
            }
        }
    }

    public Pet getPet() {
        return Objects.requireNonNullElseGet(pet, () -> new Pet(0, 0, 0, new String[0], 0D));
    }

    public void setPet(Pet pet) {
        this.pet = pet;
        if(pet != null)
            this.pet.setDropRateBoost(Pet.PetData.getDropRateBoost(pet.getId()));
    }

    public int getLifestealTimer() {
        return lifestealTimer;
    }

    public void setLifestealTimer(int lifestealTimer) {
        this.lifestealTimer = lifestealTimer;
    }

    public int getDoubleDropTimer() {
        return doubleDropTimer;
    }

    public void setDoubleDropTimer(int doubleDropTimer) {
        this.doubleDropTimer = doubleDropTimer;
    }

}
