package net.jwn.jwn_items.capability;

import net.minecraft.nbt.CompoundTag;

public class ModItemData {
    float[] starStat = new float[15];

    public float[] getStarStat() {
        return starStat;
    }
    public void setStarStat(float[] starStat) {
        this.starStat = starStat;
    }

    public void copyFrom(ModItemData modItemData) {
        this.starStat = modItemData.starStat;
    }

    public void saveNBTData(CompoundTag nbt) {
        for (int i = 0; i < 15; i++) {
            nbt.putFloat("star_stat_" + i, starStat[i]);
        }

    }

    public void loadNBTData(CompoundTag nbt) {
        float[] stats = new float[15];
        for (int i = 0; i < 15; i++) {
            stats[i] = nbt.getFloat("star_stat_" + i);
        }
        this.starStat = stats;
    }
}
