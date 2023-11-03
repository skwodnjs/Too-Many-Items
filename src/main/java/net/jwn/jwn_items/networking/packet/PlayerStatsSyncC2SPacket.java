package net.jwn.jwn_items.networking.packet;

import net.jwn.jwn_items.capability.PlayerStatProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerStatsSyncC2SPacket {
    float[] stats = new float[15];

    public PlayerStatsSyncC2SPacket(float[] stats) {
        this.stats = stats;
    }

    public void toBytes(FriendlyByteBuf buf) {
        for (int i = 0; i < this.stats.length; i++) {
            buf.writeFloat(stats[i]);
        }
    }

    public PlayerStatsSyncC2SPacket(FriendlyByteBuf buf) {
        for (int i = 0; i < this.stats.length; i++) {
            this.stats[i] = buf.readFloat();
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();

            player.getCapability(PlayerStatProvider.playerStatsCapability).ifPresent(playerStats -> {
                playerStats.set(this.stats);
            });
        });
        return true;
    }
}
