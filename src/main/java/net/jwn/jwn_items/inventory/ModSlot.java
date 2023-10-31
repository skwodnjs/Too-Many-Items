package net.jwn.jwn_items.inventory;

public class ModSlot {
    public int itemID;
    public int level;
    public boolean locked;

    public ModSlot() {
        this.itemID = 0;
        level = 0;
        this.locked = false;
    }

    public ModSlot(int itemID, int level, boolean locked) {
        this.itemID = itemID;
        this.level = level;
        this.locked = locked;
    }
}
