package net.jwn.jwn_items.networking.packet;

import net.jwn.jwn_items.capability.CoolTimeProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CoolTimeSyncC2SPacket {
    int time;

    public CoolTimeSyncC2SPacket(int coolTime) {
        this.time = coolTime;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(time);
    }

    public CoolTimeSyncC2SPacket(FriendlyByteBuf buf) {
        time = buf.readInt();
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();

            player.getCapability(CoolTimeProvider.coolTimeCapability).ifPresent(coolTime -> {
                coolTime.set(time);
            });
        });
        return true;
    }
}
