package net.jwn.jwn_items.capability;

import net.minecraft.nbt.CompoundTag;

public class CoolTime {
    int time = 0;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
        System.out.println(time);
    }

    public void sub() {
        if (time > 0) {
            time -= 1;
        }
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
