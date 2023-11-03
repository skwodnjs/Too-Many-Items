package net.jwn.jwn_items.networking.packet;

import net.jwn.jwn_items.util.Functions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PrintFoundStuffC2SPacket {
    public PrintFoundStuffC2SPacket() {
    }
    public PrintFoundStuffC2SPacket(FriendlyByteBuf buf) {
    }
    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();

            Functions.printFoundStuff(player);
        });
        return true;
    }
}
