package net.jwn.jwn_items.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.atomic.AtomicBoolean;

public class CoolTime {
    int time = 0;

    public int getTime() {
        return time;
    }

    public void addTime(int time) {
        this.time += time;
    }

    public void sub() {
        if (time > 0) {
            time -= 1;
        }
    }

    public boolean canUseSkill(int coolTime, int stack) {
        return coolTime * (stack - 1) >= time;
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
