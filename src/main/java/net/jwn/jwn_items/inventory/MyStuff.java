package net.jwn.jwn_items.inventory;

import net.jwn.jwn_items.item.ItemType;
import net.jwn.jwn_items.item.ModItem;
import net.minecraft.nbt.CompoundTag;

import java.util.Arrays;

public class MyStuff {
    int[] myStuffForActive = new int[30];
    int[] myStuffForPassive = new int[5];
    boolean passiveLimit = false;

    private final int LIMIT = 10000;

    private int getSlotIfHas(ModItem item) {
        if (item.itemType == ItemType.ACTIVE) {
            for (int i = 0; i < myStuffForActive.length; i++) {
                if ((myStuffForActive[i] - LIMIT) / 10 == item.ID) {
                    return i;
                }
            }
        } else if (item.itemType == ItemType.PASSIVE) {
            for (int i = 0; i < myStuffForPassive.length; i++) {
                if ((myStuffForPassive[i] - LIMIT) / 10 == item.ID) {
                    return i;
                }
            }
        }
        return -1;
    }

    private boolean ifSlotFull(ItemType itemType) {
        return switch (itemType) {
            case ACTIVE -> myStuffForActive[29] != 0;
            case PASSIVE -> myStuffForPassive[4] != 0;
            case CONSUMABLES -> true;
        };
    }

    private int getLastSlot(ItemType itemType) {
        if (itemType == ItemType.ACTIVE) {
            for (int i = 0; i < myStuffForActive.length; i++) {
                if (myStuffForActive[i] == 0) return i;
            }
        } else if (itemType == ItemType.PASSIVE) {
            for (int i = 0; i < myStuffForPassive.length; i++) {
                if (myStuffForPassive[i] == 0) return i;
            }
        } return -1;
    }

    public int getIDOfMainActiveItem() {
        return (myStuffForActive[0] % 1000) / 10;
    }

    public void reset() {
        Arrays.fill(myStuffForActive, 0);
        Arrays.fill(myStuffForPassive, 0);
        passiveLimit = false;
    }

    public boolean addItem(ModItem item) {
        if (ifSlotFull(item.itemType)) return false;
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
            slot = getLastSlot(item.itemType);
        }

        if (level >= 5) return false;

        int value = LIMIT + item.ID * 10 + level + 1;
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
        else if (itemType == ItemType.ACTIVE && slot > 5) return false;
        else if (itemType == ItemType.PASSIVE && slot > 30) return false;

        if (itemType == ItemType.ACTIVE) {
            for (int i = slot; i < myStuffForActive.length; i++) {
                myStuffForActive[i] = myStuffForActive[i+1];
            }
        } else if (itemType == ItemType.PASSIVE) {
            for (int i = slot; i < myStuffForPassive.length; i++) {
                myStuffForPassive[i] = myStuffForPassive[i+1];
            }
        } return true;
    }

    public void copyFrom(MyStuff myStuff) {
        System.arraycopy(myStuff.myStuffForActive, 0, myStuffForActive, 0, myStuffForActive.length);
        System.arraycopy(myStuff.myStuffForPassive, 0, myStuffForPassive, 0, myStuffForPassive.length);
        passiveLimit = myStuff.passiveLimit;
    }

    public void saveNBTData(CompoundTag nbt) {
        for (int index = 0; index < myStuffForActive.length; index++) {
            nbt.putInt("my_stuff_" + index, myStuffForActive[index]);
        }
        for (int index = 0; index < myStuffForPassive.length; index++) {
            nbt.putInt("my_stuff_" + index, myStuffForPassive[index]);
        }
        nbt.putBoolean("passive_limit", passiveLimit);
    }

    public void loadNBTData(CompoundTag nbt) {
        for (int index = 0; index < myStuffForActive.length; index++) {
            myStuffForActive[index] = nbt.getInt("my_stuff_" + index);
        }
        for (int index = 0; index < myStuffForPassive.length; index++) {
            myStuffForPassive[index] = nbt.getInt("my_stuff_" + index);
        }
        passiveLimit = nbt.getBoolean("passive_limit");
    }
}
