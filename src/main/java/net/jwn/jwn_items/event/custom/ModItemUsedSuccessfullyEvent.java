package net.jwn.jwn_items.event.custom;

import net.jwn.jwn_items.item.ItemType;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;

public class ModItemUsedSuccessfullyEvent extends Event {
    public final Player player;
    public final ItemType itemType;
    public final int id;

    public ModItemUsedSuccessfullyEvent(Player player, ItemType itemType, int id) {
        this.player = player;
        this.itemType = itemType;
        this.id = id;
    }
}
