package net.jwn.jwn_items.capability;

import net.minecraft.nbt.CompoundTag;

import java.util.Arrays;

import static net.jwn.jwn_items.item.ModItemProvider.ITEM_TOTAL;

public class FoundStuff {
    int[] foundStuffLevel = new int[ITEM_TOTAL];

    public int[] get() {
        return foundStuffLevel;
    }

    public void set(int[] foundStuffLevel) {
        this.foundStuffLevel = foundStuffLevel;
    }

    public void set(int id, int level) {
        foundStuffLevel[id] = level;
    }

    public void reset() {
        Arrays.fill(foundStuffLevel, 0);
    }

    public void copyFrom(FoundStuff foundStuff) {
        foundStuffLevel = foundStuff.foundStuffLevel;
    }

    public void saveNBTData(CompoundTag nbt) {
        for (int i = 0; i < ITEM_TOTAL; i++) {
            nbt.putInt("found_stuff_" + i, foundStuffLevel[i]);
        }
    }

    public void loadNBTData(CompoundTag nbt) {
        for (int i = 0; i < ITEM_TOTAL; i++) {
            foundStuffLevel[i] = nbt.getInt("found_stuff_" + i);
        }
    }
}
