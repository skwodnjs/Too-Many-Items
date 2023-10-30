package net.jwn.jwn_items.capability;

import net.jwn.jwn_items.item.ItemType;
import net.jwn.jwn_items.item.ModItem;
import net.minecraft.nbt.CompoundTag;

import java.util.Arrays;

import static net.jwn.jwn_items.util.Options.*;

public class MyStuff {

    int[] myStuffForActive = new int[ACTIVE_MAX_UPGRADE];
    int[] myStuffForPassive = new int[PASSIVE_MAX];
    boolean[] activeLock = new boolean[ACTIVE_MAX_UPGRADE];
    boolean[] passiveLock = new boolean[PASSIVE_MAX];
    boolean activeLimit = true;

    public void set(int[] myStuffForActive, int[] myStuffForPassive, boolean[] activeLock, boolean[] passiveLock, boolean activeLimit) {
        this.myStuffForActive = myStuffForActive;
        this.myStuffForPassive = myStuffForPassive;
        this.activeLock = activeLock;
        this.passiveLock = passiveLock;
        this.activeLimit = activeLimit;
    }

    public boolean getActiveLimit() {
        return activeLimit;
    }

    public boolean[] getActiveLock() {
        return activeLock;
    }

    public boolean[] getPassiveLock() {
        return passiveLock;
    }

    public void setActiveLock(int slot, boolean lock) {
        this.activeLock[slot] = lock;
    }

    public void setPassiveLock(int slot, boolean lock) {
        this.passiveLock[slot] = lock;
    }

    public int[] getMyStuffForActive() {
        return myStuffForActive;
    }

    public int[] getMyStuffForPassive() {
        return myStuffForPassive;
    }

    private int getSlotIfHas(ModItem item) {
        if (item.itemType == ItemType.ACTIVE) {
            for (int i = 0; i < myStuffForActive.length; i++) {
                if (myStuffForActive[i] / 10 == item.ID) {
                    return i;
                }
            }
        } else if (item.itemType == ItemType.PASSIVE) {
            for (int i = 0; i < myStuffForPassive.length; i++) {
                if (myStuffForPassive[i] / 10 == item.ID) {
                    return i;
                }
            }
        }
        return -1;
    }

    private boolean isSlotFull(ItemType itemType) {
        return switch (itemType) {
            case ACTIVE -> activeLimit ? myStuffForActive[ACTIVE_MAX - 1] != 0 : myStuffForActive[ACTIVE_MAX_UPGRADE - 1] != 0;
            case PASSIVE -> myStuffForPassive[PASSIVE_MAX - 1] != 0;
            case CONSUMABLES -> true;
        };
    }

    public int getLastEmptySlot(ItemType itemType) {
        if (itemType == ItemType.ACTIVE) {
            int active_max = (activeLimit) ? ACTIVE_MAX : ACTIVE_MAX_UPGRADE;
            for (int i = 0; i < active_max; i++) {
                if (myStuffForActive[i] == 0) return i;
            }
        } else if (itemType == ItemType.PASSIVE) {
            for (int i = 0; i < PASSIVE_MAX; i++) {
                if (myStuffForPassive[i] == 0) return i;
            }
        } return -1;
    }

    public int getIDOfMainActiveItem() {
        return myStuffForActive[0] / 10;
    }

    public void changeMainActiveItem() {
        int active_item_num = 0;
        for (int i = 0; i < myStuffForActive.length; i++) {
            if (myStuffForActive[i] != 0) {
                active_item_num++;
            }
        }
        if (active_item_num == 0) return;

        int mainItem = myStuffForActive[0];
        boolean mainLock = activeLock[0];
        for (int i = 0; i < active_item_num - 1; i++) {
            myStuffForActive[i] = myStuffForActive[i+1];
            activeLock[i] = activeLock[i+1];
        }
        myStuffForActive[active_item_num - 1] = mainItem;
        activeLock[active_item_num - 1] = mainLock;
    }

    public void reset() {
        Arrays.fill(myStuffForActive, 0);
        Arrays.fill(myStuffForPassive, 0);
        Arrays.fill(activeLock, false);
        Arrays.fill(passiveLock, false);
        activeLimit = false;
    }

    public boolean addItem(ModItem item) {
        if (isSlotFull(item.itemType)) return false;
        if (item.itemType == ItemType.CONSUMABLES) return false;

        int slot = getSlotIfHas(item);
        int level = 0;

        if (slot != -1) {
            if (item.itemType == ItemType.ACTIVE) {
                level = myStuffForActive[slot] % 10;
            } else if (item.itemType == ItemType.PASSIVE) {
                level = myStuffForPassive[slot] % 10;
            }
        } else {
            slot = getLastEmptySlot(item.itemType);
        }

        if (level >= 5) return false;

        int value = item.ID * 10 + level + 1;
        if (item.itemType == ItemType.ACTIVE) {
            myStuffForActive[slot] = value;
        } else if (item.itemType == ItemType.PASSIVE) {
            myStuffForPassive[slot] = value;
        }
        return true;
    }

    public boolean removeItem(ItemType itemType, int slot) {
        if (slot < 0) return false;
        else if (itemType == ItemType.CONSUMABLES) return false;
        else if (itemType == ItemType.ACTIVE && slot >= ACTIVE_MAX_UPGRADE) return false;
        else if (itemType == ItemType.PASSIVE && slot >= PASSIVE_MAX) return false;

        if (itemType == ItemType.ACTIVE) {
            myStuffForActive[slot] = 0;
            activeLock[slot] = false;
            for (int i = slot; i < ACTIVE_MAX_UPGRADE - 1; i++) {
                myStuffForActive[i] = myStuffForActive[i+1];
                activeLock[i] = activeLock[i+1];
            }
        } else if (itemType == ItemType.PASSIVE) {
            myStuffForPassive[slot] = 0;
            passiveLock[slot] = false;
            for (int i = slot; i < PASSIVE_MAX - 1; i++) {
                myStuffForPassive[i] = myStuffForPassive[i+1];
                passiveLock[i] = passiveLock[i+1];
            }
        } return true;
    }

    public void copyFrom(MyStuff myStuff) {
        System.arraycopy(myStuff.myStuffForActive, 0, myStuffForActive, 0, myStuffForActive.length);
        System.arraycopy(myStuff.myStuffForPassive, 0, myStuffForPassive, 0, myStuffForPassive.length);
        System.arraycopy(myStuff.activeLock, 0, activeLock, 0, activeLock.length);
        System.arraycopy(myStuff.passiveLock, 0, passiveLock, 0, passiveLock.length);
        activeLimit = myStuff.activeLimit;
    }

    public void saveNBTData(CompoundTag nbt) {
        for (int index = 0; index < myStuffForActive.length; index++) {
            nbt.putInt("my_stuff_active_" + index, myStuffForActive[index]);
        }
        for (int index = 0; index < myStuffForPassive.length; index++) {
            nbt.putInt("my_stuff_passive_" + index, myStuffForPassive[index]);
        }
        for (int index = 0; index < activeLock.length; index++) {
            nbt.putBoolean("active_lock_" + index, activeLock[index]);
        }
        for (int index = 0; index < activeLock.length; index++) {
            nbt.putBoolean("passive_lock_" + index, passiveLock[index]);
        }
        nbt.putBoolean("passive_limit", activeLimit);
    }

    public void loadNBTData(CompoundTag nbt) {
        for (int index = 0; index < myStuffForActive.length; index++) {
            myStuffForActive[index] = nbt.getInt("my_stuff_active_" + index);
        }
        for (int index = 0; index < myStuffForPassive.length; index++) {
            myStuffForPassive[index] = nbt.getInt("my_stuff_passive_" + index);
        }
        for (int index = 0; index < activeLock.length; index++) {
            activeLock[index] = nbt.getBoolean("active_lock_" + index);
        }
        for (int index = 0; index < activeLock.length; index++) {
            passiveLock[index] = nbt.getBoolean("passive_lock_" + index);
        }
        activeLimit = nbt.getBoolean("passive_limit");
    }
}
