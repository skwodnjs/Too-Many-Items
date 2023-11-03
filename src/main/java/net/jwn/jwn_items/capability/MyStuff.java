package net.jwn.jwn_items.capability;

import net.jwn.jwn_items.util.ModSlot;
import net.jwn.jwn_items.item.ItemType;
import net.jwn.jwn_items.item.ModItem;
import net.minecraft.nbt.CompoundTag;

import java.util.Arrays;

public class MyStuff {
    public static final int ACTIVE_MAX_UPGRADE = 5;
    public static final int ACTIVE_MAX = 3;
    public static final int PASSIVE_MAX = 27;

    int[] activeItemID = new int[ACTIVE_MAX_UPGRADE];
    int[] activeItemLevel = new int[ACTIVE_MAX_UPGRADE];
    boolean[] activeItemLocked = new boolean[ACTIVE_MAX_UPGRADE];

    int[] passiveItemID = new int[PASSIVE_MAX];
    int[] passiveItemLevel = new int[PASSIVE_MAX];
    boolean[] passiveItemLocked = new boolean[PASSIVE_MAX];

    boolean activeUpgraded = false;

    public ModSlot[] getActiveSlots() {
        ModSlot[] toReturn = new ModSlot[ACTIVE_MAX_UPGRADE];
        for (int i = 0; i < ACTIVE_MAX_UPGRADE; i++) {
            toReturn[i] = new ModSlot(activeItemID[i], activeItemLevel[i], activeItemLocked[i]);
        }
        return toReturn;
    }

    public ModSlot[] getPassiveSlots() {
        ModSlot[] toReturn = new ModSlot[PASSIVE_MAX];
        for (int i = 0; i < PASSIVE_MAX; i++) {
            toReturn[i] = new ModSlot(passiveItemID[i], passiveItemLevel[i], passiveItemLocked[i]);
        }
        return toReturn;
    }

    public boolean isActiveUpgraded() {
        return activeUpgraded;
    }

    public void set(ModSlot[] activeSlots, ModSlot[] passiveSlots, boolean activeUpgraded) {
        for (int i = 0; i < activeSlots.length; i++) {
            this.activeItemID[i] = activeSlots[i].itemId;
            this.activeItemLevel[i] = activeSlots[i].level;
            this.activeItemLocked[i] = activeSlots[i].locked;
        }
        for (int i = 0; i < passiveSlots.length; i++) {
            this.passiveItemID[i] = passiveSlots[i].itemId;
            this.passiveItemLevel[i] = passiveSlots[i].level;
            this.passiveItemLocked[i] = passiveSlots[i].locked;
        }
        this.activeUpgraded = activeUpgraded;
    }

    public void reset() {
        Arrays.fill(activeItemID, 0);
        Arrays.fill(activeItemLevel, 0);
        Arrays.fill(activeItemLocked, false);
        Arrays.fill(passiveItemID, 0);
        Arrays.fill(passiveItemLevel, 0);
        Arrays.fill(passiveItemLocked, false);
        activeUpgraded = false;
    }

    public void lockActiveSlot(int index, boolean locked) {
        activeItemLocked[index] = locked;
    }

    public void lockPassiveSlot(int index, boolean locked) {
        passiveItemLocked[index] = locked;
    }

    private boolean isFull(ItemType itemType) {
        return switch (itemType) {
            case ACTIVE -> activeUpgraded ? activeItemID[ACTIVE_MAX_UPGRADE - 1] != 0 : activeItemID[ACTIVE_MAX - 1] != 0;
            case PASSIVE -> passiveItemID[PASSIVE_MAX - 1] != 0;
            case CONSUMABLES -> true;
        };
    }

    public int getEmptySlot(ItemType itemType) {
        if (itemType == ItemType.ACTIVE) {
            int l = (activeUpgraded) ? ACTIVE_MAX_UPGRADE : ACTIVE_MAX;
            for (int i = 0; i < l; i++) {
                if (activeItemID[i] == 0) return i;
            }
        } else if (itemType == ItemType.PASSIVE) {
            for (int i = 0; i < PASSIVE_MAX; i++) {
                if (passiveItemID[i] == 0) return i;
            }
        } return -1;
    }

    public boolean addItem(ModItem modItem) {
        if (isFull(modItem.itemType)) return false;
        if (modItem.itemType == ItemType.CONSUMABLES) return false;

        int index;
        if (getIndex(modItem) == -1)  {
            index = getEmptySlot(modItem.itemType);
        } else {
            index = getIndex(modItem);
        }

        int level;
        if (modItem.itemType == ItemType.ACTIVE) {
            level = activeItemLevel[index];
            if (level >= 5) return false;
            activeItemID[index] = modItem.id;
            activeItemLevel[index] = level + 1;
        } else {
            level = passiveItemLevel[index];
            if (level >= 5) return false;
            passiveItemID[index] = modItem.id;
            passiveItemLevel[index] = level + 1;
        }
        return true;
    }

    public void removeItem(ItemType itemType, int index) {
        if (itemType == ItemType.ACTIVE) {
            for (int i = index; i < ACTIVE_MAX_UPGRADE - 1; i++) {
                activeItemID[i] = activeItemID[i+1];
                activeItemLevel[i] = activeItemLevel[i+1];
                activeItemLocked[i] = activeItemLocked[i+1];
            }
            activeItemID[ACTIVE_MAX_UPGRADE - 1] = 0;
            activeItemLevel[ACTIVE_MAX_UPGRADE - 1] = 0;
            activeItemLocked[ACTIVE_MAX_UPGRADE - 1] = false;
        } else if (itemType == ItemType.PASSIVE) {
            for (int i = index; i < PASSIVE_MAX - 1; i++) {
                passiveItemID[i] = passiveItemID[i+1];
                passiveItemLevel[i] = passiveItemLevel[i+1];
                passiveItemLocked[i] = passiveItemLocked[i+1];
            }
            passiveItemID[PASSIVE_MAX - 1] = 0;
            passiveItemLevel[PASSIVE_MAX - 1] = 0;
            passiveItemLocked[PASSIVE_MAX - 1] = false;
        }
    }

    public void changeMainActiveItem() {
        int num = 0;
        for (int i = 0; i < ACTIVE_MAX_UPGRADE; i++) {
            if (activeItemID[i] != 0) {
                num++;
            }
        }
        if (num == 0) return;

        ModSlot mainItem = new ModSlot(activeItemID[0], activeItemLevel[0], activeItemLocked[0]);
        for (int i = 0; i < num - 1; i++) {
            activeItemID[i] = activeItemID[i+1];
            activeItemLevel[i] = activeItemLevel[i+1];
            activeItemLocked[i] = activeItemLocked[i+1];
        }
        activeItemID[num - 1] = mainItem.itemId;
        activeItemLevel[num - 1] = mainItem.level;
        activeItemLocked[num - 1] = mainItem.locked;
    }

    public int getLevelById(int id) {
        for (ModSlot modSlot : getActiveSlots()) {
            if (modSlot.itemId == id) {
                return modSlot.level;
            }
        }
        for (ModSlot modSlot : getPassiveSlots()) {
            if (modSlot.itemId == id) {
                return modSlot.level;
            }
        }
        return 0;
    }

    private int getIndex(ModItem modItem) {
        if (modItem.itemType == ItemType.ACTIVE) {
            for (int i = 0; i < ACTIVE_MAX_UPGRADE; i++) {
                if (activeItemID[i] == modItem.id) {
                    return i;
                }
            }
        } else if (modItem.itemType == ItemType.PASSIVE) {
            for (int i = 0; i < PASSIVE_MAX; i++) {
                if (passiveItemID[i] == modItem.id) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void copyFrom(MyStuff myStuff) {
        activeItemID = myStuff.activeItemID;
        activeItemLevel = myStuff.activeItemLevel;
        activeItemLocked = myStuff.activeItemLocked;
        passiveItemID = myStuff.passiveItemID;
        passiveItemLevel = myStuff.passiveItemLevel;
        passiveItemLocked = myStuff.passiveItemLocked;
        activeUpgraded = myStuff.activeUpgraded;
    }

    public void saveNBTData(CompoundTag nbt) {
        for (int i = 0; i < ACTIVE_MAX_UPGRADE; i++) {
            nbt.putInt("my_stuff_active_id_" + i, activeItemID[i]);
            nbt.putInt("my_stuff_active_level_" + i, activeItemLevel[i]);
            nbt.putBoolean("my_stuff_active_locked_" + i, activeItemLocked[i]);
        }
        for (int i = 0; i < PASSIVE_MAX; i++) {
            nbt.putInt("my_stuff_passive_id_" + i, passiveItemID[i]);
            nbt.putInt("my_stuff_passive_level_" + i, passiveItemLevel[i]);
            nbt.putBoolean("my_stuff_passive_locked_" + i, passiveItemLocked[i]);
        }
        nbt.putBoolean("passive_limit", activeUpgraded);
    }

    public void loadNBTData(CompoundTag nbt) {
        for (int i = 0; i < ACTIVE_MAX_UPGRADE; i++) {
            activeItemID[i] = nbt.getInt("my_stuff_active_id_" + i);
            activeItemLevel[i] = nbt.getInt("my_stuff_active_level_" + i);
            activeItemLocked[i] = nbt.getBoolean("my_stuff_active_locked_" + i);
        }
        for (int i = 0; i < PASSIVE_MAX; i++) {
            passiveItemID[i] = nbt.getInt("my_stuff_passive_id_" + i);
            passiveItemLevel[i] = nbt.getInt("my_stuff_passive_level_" + i);
            passiveItemLocked[i] = nbt.getBoolean("my_stuff_passive_locked_" + i);
        }
        activeUpgraded = nbt.getBoolean("passive_limit");
    }
}
