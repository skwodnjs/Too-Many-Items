package net.jwn.jwn_items.networking.packet;

import net.jwn.jwn_items.capability.MyStuffProvider;
import net.jwn.jwn_items.capability.PlayerStatsProvider;
import net.jwn.jwn_items.item.ModItem;
import net.jwn.jwn_items.item.ModItems;
import net.jwn.jwn_items.stat.StatType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ChangeMainActiveItemC2SPacket {
    public static final int ID = ((ModItem) ModItems.D1_ITEM.get()).getItemID();

    public ChangeMainActiveItemC2SPacket() {
    }
    public ChangeMainActiveItemC2SPacket(FriendlyByteBuf buf) {
    }
    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();

            player.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
                myStuff.changeMainActiveItem();
            });
        });
        return true;
    }
}
