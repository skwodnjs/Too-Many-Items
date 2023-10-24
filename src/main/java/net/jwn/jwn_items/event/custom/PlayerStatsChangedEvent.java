package net.jwn.jwn_items.event.custom;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;

public class PlayerStatsChangedEvent extends Event {
    public final Player player;
    public PlayerStatsChangedEvent(Player player) {
        this.player = player;
    }
}
