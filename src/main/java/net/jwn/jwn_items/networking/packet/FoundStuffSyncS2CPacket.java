package net.jwn.jwn_items.networking.packet;

import net.jwn.jwn_items.capability.FoundStuffProvider;
import net.jwn.jwn_items.capability.MyStuffProvider;
import net.jwn.jwn_items.inventory.ModSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static net.jwn.jwn_items.item.ModItems.ModItemsProvider.ITEM_TOTAL;
import static net.jwn.jwn_items.util.Options.ACTIVE_MAX_UPGRADE;
import static net.jwn.jwn_items.util.Options.PASSIVE_MAX;

public class FoundStuffSyncS2CPacket {
    int[] foundStuffLevel = new int[ITEM_TOTAL];

    public FoundStuffSyncS2CPacket(int[] foundStuffLevel) {
        this.foundStuffLevel = foundStuffLevel;
    }

    public void toBytes(FriendlyByteBuf buf) {
        for (int i = 0; i < ITEM_TOTAL; i++) {
            buf.writeInt(foundStuffLevel[i]);
        }
    }

    public FoundStuffSyncS2CPacket(FriendlyByteBuf buf) {
        for (int i = 0; i < ITEM_TOTAL; i++) {
            foundStuffLevel[i] = buf.readInt();
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            Player player = Minecraft.getInstance().player;

            player.getCapability(FoundStuffProvider.foundStuffCapability).ifPresent(foundStuff -> {
                foundStuff.set(foundStuffLevel);
            });
        });
        return true;
    }
}
