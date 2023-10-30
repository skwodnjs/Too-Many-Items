package net.jwn.jwn_items.capability;

import net.minecraft.nbt.CompoundTag;

public class PlayerOptions {
    boolean statHudOption = true;
    boolean[] statHudDetailOptions = {true, true, true, true, true, true, true, true};

    public void statHudToggle() {
        statHudOption = !statHudOption;
    }
    public void setStatHudDetail(int index, boolean on) {
        statHudDetailOptions[index] = on;
    }
    public boolean getStatHudOption() {
        return statHudOption;
    }
    public boolean[] getStatHudDetailOptions() {
        return statHudDetailOptions;
    }
    public void set(boolean statHudOption, boolean[] statHudDetailOptions) {
        this.statHudOption = statHudOption;
        this.statHudDetailOptions = statHudDetailOptions;
    }

    public void copyFrom(PlayerOptions playerOptions) {
        statHudOption = playerOptions.statHudOption;
        statHudDetailOptions = playerOptions.statHudDetailOptions;
    }
    public void saveNBTData(CompoundTag nbt) {
        nbt.putBoolean("option_stat_hud", statHudOption);
        for (int i = 0; i < statHudDetailOptions.length; i++) {
            nbt.putBoolean("option_stat_hud_detail_" + i, statHudDetailOptions[i]);
        }
    }
    public void loadNBTData(CompoundTag nbt) {
        statHudOption = nbt.getBoolean("option_stat_hud");
        for (int i = 0; i < statHudDetailOptions.length; i++) {
            statHudDetailOptions[i] = nbt.getBoolean("option_stat_hud_detail_" + i);
        }
    }
}
