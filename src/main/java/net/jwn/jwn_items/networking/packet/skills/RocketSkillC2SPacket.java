package net.jwn.jwn_items.networking.packet.skills;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RocketSkillC2SPacket {
    public static final int ID = 2;

    public RocketSkillC2SPacket() {
    }
    public RocketSkillC2SPacket(FriendlyByteBuf buf) {
    }
    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();

//            player.teleportRelative(0, 10, 0);
//            player.setDeltaMovement(1,1,0);
        });
        return true;
    }
}
