package net.jwn.jwn_items.util;

public class ModSlot {
    public int itemId;
    public int level;
    public boolean locked;

    public ModSlot(int itemID, int level, boolean locked) {
        this.itemId = itemID;
        this.level = level;
        this.locked = locked;
    }
}
