package net.jwn.jwn_items.stat;

import net.jwn.jwn_items.event.custom.PlayerStatsChangedEvent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;

import java.util.Arrays;
import java.util.List;

public class PlayerStats {
    private float[] playerStats = new float[15];

    public static final float MAX_HEALTH = 30.0f;
    public static final float MAX_STAT = 60.0f;
    public static final float MIN_STAT = 0.0f;
    public static final float MAX_COIN = 99.0f;

    public float getValue(StatType statType) {
        int index = statType.ordinal();
        return playerStats[index];
    }

    public float getValue(int index) {
        return playerStats[index];
    }

    public float add(Player player, Stat addStat) {
        int index = addStat.getStatType().ordinal();

        float addValue = addStat.getValue();
        float oldValue = playerStats[index];
        float newValue;

        if (addValue > 0) {
            if (index == 0) {
                float itemValue = playerStats[index + 7];
                newValue = Math.min(oldValue + itemValue + addValue, MAX_HEALTH) - itemValue;
            } else if (index < 7) {
                float itemValue = playerStats[index + 7];
                newValue = Math.min(oldValue + itemValue + addValue, MAX_STAT) - itemValue;
            } else if (index < 14) {
                newValue = oldValue + addValue;
            } else {
                newValue = Math.min(oldValue + addValue, MAX_COIN);
            }
        } else {
            newValue = Math.max(oldValue + addValue, MIN_STAT);
        }

        Stat newStat = new Stat(addStat.getStatType(), newValue);
        Stat appliedStat = new Stat(addStat.getStatType(), playerStats[index] - oldValue);
        playerStats[index] = newStat.getValue();
        MinecraftForge.EVENT_BUS.post(new PlayerStatsChangedEvent(player));

        System.out.println(newStat.getStatType().getName());
        System.out.println("저장 전: " + oldValue);
        System.out.println("저장 후: " + playerStats[index]);
        return appliedStat.getValue();
    }

    public void set(Player player, Stat setStat) {
        int index = setStat.getStatType().ordinal();
        float setValue = setStat.getValue();

        if ((index == 0 || index == 7) && (setStat.getValue() < MIN_STAT || setStat.getValue() > MAX_HEALTH)) return;
        else if ((index == 14) && (setStat.getValue() < MIN_STAT || setStat.getValue() > MAX_COIN)) return;
        else if (setStat.getValue() < MIN_STAT || setStat.getValue() > MAX_STAT) return;

        playerStats[index] = setValue;
        MinecraftForge.EVENT_BUS.post(new PlayerStatsChangedEvent(player));
    }

    public void reset(Player player) {
        Arrays.fill(playerStats, 0);
        MinecraftForge.EVENT_BUS.post(new PlayerStatsChangedEvent(player));
    }

    public void copyFrom(PlayerStats source) {
        for (int index = 0; index < playerStats.length; index++) {
            playerStats[index] = source.getValue(index);
        }
    }

    public void saveNBTData(CompoundTag nbt) {
        for (int index = 0; index < playerStats.length; index++) {
            nbt.putFloat("player_stat_" + index, playerStats[index]);
        }
    }

    public void loadNBTData(CompoundTag nbt) {
        for (int index = 0; index < playerStats.length; index++) {
            playerStats[index] = nbt.getFloat("player_stat_" + index);
        }
    }
}
