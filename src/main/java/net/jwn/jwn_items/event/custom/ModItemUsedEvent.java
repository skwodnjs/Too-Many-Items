package net.jwn.jwn_items.event.custom;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;

public class ModItemUsedEvent extends Event {
    public final Player player;


    public ModItemUsedEvent(Player player) {
        this.player = player;
    }
}
