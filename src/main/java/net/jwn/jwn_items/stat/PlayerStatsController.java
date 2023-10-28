package net.jwn.jwn_items.stat;

import net.jwn.jwn_items.capability.PlayerStatsProvider;
import net.jwn.jwn_items.stat.Stat;
import net.minecraft.server.level.ServerPlayer;

import java.util.concurrent.atomic.AtomicReference;

public class PlayerStatsController {
    public static float addSingleStat(ServerPlayer player, Stat stat) {
        AtomicReference<Float> toReturn = new AtomicReference<>(0f);
        player.getCapability(PlayerStatsProvider.playerStatsCapability).ifPresent(playerStats -> {
            float appliedValue = playerStats.add(player, stat);
            toReturn.set(appliedValue);
        });
        return toReturn.get();
    }
}
