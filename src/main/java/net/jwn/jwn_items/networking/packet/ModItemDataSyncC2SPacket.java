package net.jwn.jwn_items.networking.packet;

import net.jwn.jwn_items.capability.ModItemDataProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ModItemDataSyncC2SPacket {
    float[] starStat = new float[15];

    public ModItemDataSyncC2SPacket(float[][] floats) {
        this.starStat = floats[0];
    }

    public void toBytes(FriendlyByteBuf buf) {
        for (int i = 0; i < 15; i++) {
            buf.writeFloat(starStat[i]);
        }
    }

    public ModItemDataSyncC2SPacket(FriendlyByteBuf buf) {
        for (int i = 0; i < 15; i++) {
            starStat[i] = buf.readFloat();
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!
            ServerPlayer player = context.getSender();

            player.getCapability(ModItemDataProvider.modItemDataCapability).ifPresent(modItemData -> {
                modItemData.setStarStat(starStat);
            });
        });
        return true;
    }
}
