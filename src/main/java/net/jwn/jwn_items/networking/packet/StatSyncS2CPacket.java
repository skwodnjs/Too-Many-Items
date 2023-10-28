package net.jwn.jwn_items.networking.packet;

import net.jwn.jwn_items.capability.PlayerStatsProvider;
import net.jwn.jwn_items.stat.Stat;
import net.jwn.jwn_items.stat.StatType;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class StatSyncS2CPacket {
    private final float[] playerStats = new float[15];

    public StatSyncS2CPacket(float playerStats[]) {
        for (int i = 0; i < playerStats.length; i++) {
            this.playerStats[i] = playerStats[i];
        }
    }

    public void toBytes(FriendlyByteBuf buf) {
        for (int i = 0; i < playerStats.length; i++) {
            buf.writeFloat(this.playerStats[i]);
        }
    }

    public StatSyncS2CPacket(FriendlyByteBuf buf) {
        for (int i = 0; i < playerStats.length; i++) {
            this.playerStats[i] = buf.readFloat();
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!
            Player player = Minecraft.getInstance().player;
            player.getCapability(PlayerStatsProvider.playerStatsCapability).ifPresent(stats -> {
                for (int i = 0; i < playerStats.length; i++) {
                    stats.set(player, new Stat(StatType.getStatTypeById(i), playerStats[i]));
                }
            });
        });
        return true;
    }
}
