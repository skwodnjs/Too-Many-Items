package net.jwn.jwn_items.event.custom;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;

public class ModItemUsedSuccessfullyEvent extends Event {
    public final Player player;
    public final int id;

    // 도감 등록을 위한 이벤트. 서버 / 클라이언트에서 각각 발동.
    public ModItemUsedSuccessfullyEvent(Player player, int id) {
        this.player = player;
        this.id = id;
    }
}
