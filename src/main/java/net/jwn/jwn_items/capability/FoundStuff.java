package net.jwn.jwn_items.capability;

import net.minecraft.nbt.CompoundTag;

import static net.jwn.jwn_items.item.ModItems.ModItemsProvider.___ITEM_TOTAL;

public class FoundStuff {
    int[] foundStuffLevel = new int[___ITEM_TOTAL];

    public void set(int[] foundStuffLevel) {
        this.foundStuffLevel = foundStuffLevel;
    }

    public int[] getFoundStuffLevel() {
        return foundStuffLevel;
    }

    public void set(int id, int level) {
        foundStuffLevel[id] = level;
    }

    public void copyFrom(FoundStuff foundStuff) {
        foundStuffLevel = foundStuff.foundStuffLevel;
    }

    public void saveNBTData(CompoundTag nbt) {
        for (int i = 0; i < ___ITEM_TOTAL; i++) {
            nbt.putInt("found_stuff_" + i, foundStuffLevel[i]);
        }
    }

    public void loadNBTData(CompoundTag nbt) {
        for (int i = 0; i < ___ITEM_TOTAL; i++) {
            foundStuffLevel[i] = nbt.getInt("found_stuff_" + i);
        }
    }
}
