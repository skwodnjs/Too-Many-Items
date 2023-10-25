package net.jwn.jwn_items.networking.packet.skills;

import net.jwn.jwn_items.item.ItemType;
import net.jwn.jwn_items.item.ModItem;
import net.jwn.jwn_items.item.ModItems;
import net.jwn.jwn_items.util.ModItemProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Supplier;

public class D1SkillC2SPacket {
    public static final int ID = ((ModItem) ModItems.D1_ITEM.get()).getItemID();

    public D1SkillC2SPacket() {
    }
    public D1SkillC2SPacket(FriendlyByteBuf buf) {
    }
    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();

            ArrayList<Integer> modItemList = new ArrayList<>();
            player.getInventory().items.forEach(itemStack -> {
                if (itemStack.getItem() instanceof ModItem modItem) {
                    if (modItem.itemType != ItemType.CONSUMABLES) {
                        int slot = player.getInventory().items.indexOf(itemStack);
                        modItemList.add(slot);
                    }
                }
            });
            Random random = new Random();
            int targetIndex = modItemList.get(random.nextInt(modItemList.size()));
            ModItem oldItem = (ModItem) player.getInventory().getItem(targetIndex).getItem();
            ModItem newItem = ModItemProvider.getRandomItem(oldItem.itemType, oldItem.grade);
            player.getInventory().setItem(targetIndex, newItem.getDefaultInstance());
        });
        return true;
    }
}
