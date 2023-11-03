package net.jwn.jwn_items.networking.packet;

import net.jwn.jwn_items.capability.CoolTimeProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CoolTimeSyncS2CPacket {
    int time;

    public CoolTimeSyncS2CPacket(int coolTime) {
        this.time = coolTime;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(time);
    }

    public CoolTimeSyncS2CPacket(FriendlyByteBuf buf) {
        time = buf.readInt();
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!
            Player player = Minecraft.getInstance().player;

            player.getCapability(CoolTimeProvider.coolTimeCapability).ifPresent(coolTime -> {
                coolTime.set(time);
            });
        });
        return true;
    }
}
