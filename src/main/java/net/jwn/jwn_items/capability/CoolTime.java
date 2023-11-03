package net.jwn.jwn_items.capability;

import net.minecraft.nbt.CompoundTag;

public class CoolTime {
    int time = 0;

    public int get() {
        return time;
    }

    public void set(int coolTime) {
        this.time = coolTime;
    }

    public void add(int time) {
        this.time += time;
    }

    public void sub() {
        if (time > 0) {
            time -= 1;
        }
    }

    public void reset() {
        this.time = 0;
    }

    public boolean canUseSkill(int cost, int chargeStack) {
        return cost * chargeStack >= time + cost;
    }

    public void copyFrom(CoolTime coolTime) {
        this.time = coolTime.time;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("coolTime", time);
    }

    public void loadNBTData(CompoundTag nbt) {
        this.time = nbt.getInt("coolTime");
    }
}
