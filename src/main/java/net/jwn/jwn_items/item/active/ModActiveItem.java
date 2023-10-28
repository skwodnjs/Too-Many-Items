package net.jwn.jwn_items.item.active;

import net.jwn.jwn_items.item.ItemType;
import net.jwn.jwn_items.item.ModStuffItem;

public class ModActiveItem extends ModStuffItem {
    private int COOL_TIME;

    public ModActiveItem(Properties pProperties, int quality, int ID) {
        super(pProperties, ItemType.ACTIVE, quality, ID);
    }

    public int getCoolTime() {
        return (60 - quality * 10) * 20;
    }
}
