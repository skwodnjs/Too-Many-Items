package net.jwn.jwn_items.networking.packet;

import net.jwn.jwn_items.capability.MyStuffProvider;
import net.jwn.jwn_items.util.ModSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static net.jwn.jwn_items.capability.MyStuff.*;

public class MyStuffSyncC2SPacket {
    ModSlot[] myStuffActiveSlots = new ModSlot[ACTIVE_MAX_UPGRADE];
    ModSlot[] myStuffPassiveSlots = new ModSlot[PASSIVE_MAX];
    boolean activeUpgrade;

    public MyStuffSyncC2SPacket(ModSlot[] myStuffActiveSlots, ModSlot[] myStuffPassiveSlots, boolean activeUpgrade) {
        this.myStuffActiveSlots = myStuffActiveSlots;
        this.myStuffPassiveSlots = myStuffPassiveSlots;
        this.activeUpgrade = activeUpgrade;
    }

    public void toBytes(FriendlyByteBuf buf) {
        for (int i = 0; i < myStuffActiveSlots.length; i++) {
            buf.writeInt(myStuffActiveSlots[i].itemId);
            buf.writeInt(myStuffActiveSlots[i].level);
            buf.writeBoolean(myStuffActiveSlots[i].locked);
        }
        for (int i = 0; i < myStuffPassiveSlots.length; i++) {
            buf.writeInt(myStuffPassiveSlots[i].itemId);
            buf.writeInt(myStuffPassiveSlots[i].level);
            buf.writeBoolean(myStuffPassiveSlots[i].locked);
        }
        buf.writeBoolean(activeUpgrade);
    }

    public MyStuffSyncC2SPacket(FriendlyByteBuf buf) {
        for (int i = 0; i < myStuffActiveSlots.length; i++) {
            myStuffActiveSlots[i] = new ModSlot(buf.readInt(), buf.readInt(), buf.readBoolean());
        }
        for (int i = 0; i < myStuffPassiveSlots.length; i++) {
            myStuffPassiveSlots[i] = new ModSlot(buf.readInt(), buf.readInt(), buf.readBoolean());
        }
        activeUpgrade = buf.readBoolean();
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();

            player.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
                myStuff.set(myStuffActiveSlots, myStuffPassiveSlots, activeUpgrade);
            });
        });
        return true;
    }
}
