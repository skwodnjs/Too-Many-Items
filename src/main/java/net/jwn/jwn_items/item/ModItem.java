package net.jwn.jwn_items.item;


import net.minecraft.world.item.Item;

public class ModItem extends Item {
    public final ItemType itemType;
    public final int quality;
    public final int id;

    public ModItem(Properties pProperties, ItemType itemType, int id, int quality) {
        super(pProperties);
        this.itemType = itemType;
        this.id = id;
        this.quality = quality;
    }
}
