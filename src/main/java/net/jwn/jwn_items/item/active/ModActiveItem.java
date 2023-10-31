package net.jwn.jwn_items.item.active;

import net.jwn.jwn_items.item.ItemType;
import net.jwn.jwn_items.item.ModStuffItem;

public class ModActiveItem extends ModStuffItem {
    private final int cool_time;
    private final int stack;
    private final int weight;

    public ModActiveItem(Properties pProperties, int quality, int ID, int cool_time, int stack, int weight) {
        super(pProperties, ItemType.ACTIVE, quality, ID);
        this.cool_time = cool_time;
        this.stack = stack;
        this.weight = weight;
    }

    public int getCoolTime(int level) {
        return (cool_time - (level - 1) * weight) * 20;
    }

    public int getStack() {
        return stack;
    }
}
