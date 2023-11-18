package net.jwn.jwn_items.item;

import net.minecraft.world.item.Item;

public class ModItem extends Item {
    public final ItemType itemType;
    public final Quality quality;
    public final int id;

    public ModItem(Properties pProperties, ItemType itemType, int id, Quality quality) {
        super(pProperties);
        this.itemType = itemType;
        this.id = id;
        this.quality = quality;
    }
}
