package net.jwn.jwn_items.networking.packet;

import net.jwn.jwn_items.capability.FoundStuffProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static net.jwn.jwn_items.item.ModItemProvider.ITEM_TOTAL;

public class FoundStuffSyncC2SPacket {
    int[] foundStuffLevel = new int[ITEM_TOTAL];

    public FoundStuffSyncC2SPacket(int[] foundStuffLevel) {
        this.foundStuffLevel = foundStuffLevel;
    }

    public void toBytes(FriendlyByteBuf buf) {
        for (int i = 0; i < ITEM_TOTAL; i++) {
            buf.writeInt(foundStuffLevel[i]);
        }
    }

    public FoundStuffSyncC2SPacket(FriendlyByteBuf buf) {
        for (int i = 0; i < ITEM_TOTAL; i++) {
            foundStuffLevel[i] = buf.readInt();
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();

            player.getCapability(FoundStuffProvider.foundStuffCapability).ifPresent(foundStuff -> {
                foundStuff.set(foundStuffLevel);
            });
        });
        return true;
    }
}
