package net.jwn.jwn_items.networking.packet;

import net.jwn.jwn_items.capability.MyStuffProvider;
import net.jwn.jwn_items.item.ItemType;
import net.jwn.jwn_items.item.ModItem;
import net.jwn.jwn_items.item.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class StuffC2SPacket {
    public static final int ID = ((ModItem) ModItems.D1_ITEM.get()).getItemID();

    public StuffC2SPacket() {
    }
    public StuffC2SPacket(FriendlyByteBuf buf) {
    }
    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();

            System.out.println("--- MY STUFF / SERVER SIDE ---");
            player.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
                System.out.println("--- ACTIVE ITEM ---");
                for (int i = 0; i < 5; i++) {
                    System.out.printf("%d\t", myStuff.getMyStuffForActive()[i]);
                }
                System.out.println();
                System.out.println("get ID of main active item: " + myStuff.getIDOfMainActiveItem());

                System.out.println("--- PASSIVE ITEM ---");
                for (int i = 0; i < 10; i++) {
                    System.out.printf("%d\t", myStuff.getMyStuffForPassive()[i]);
                }
                System.out.println();
                for (int i = 0; i < 10; i++) {
                    System.out.printf("%d\t", myStuff.getMyStuffForPassive()[i + 10]);
                }
                System.out.println();
                for (int i = 0; i < 10; i++) {
                    System.out.printf("%d\t", myStuff.getMyStuffForPassive()[i + 20]);
                }
                System.out.println();
                System.out.println("get last slot of passive item: " + myStuff.getLastEmptySlot(ItemType.PASSIVE));
            });
        });
        return true;
    }
}
