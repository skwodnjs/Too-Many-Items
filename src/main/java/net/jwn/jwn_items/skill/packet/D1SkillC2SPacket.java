package net.jwn.jwn_items.skill.packet;

import net.jwn.jwn_items.capability.CoolTimeProvider;
import net.jwn.jwn_items.item.ItemType;
import net.jwn.jwn_items.item.ModItem;
import net.jwn.jwn_items.item.ModItems;
import net.jwn.jwn_items.item.ModItems.ModItemsProvider;
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

            // cool time
            player.getCapability(CoolTimeProvider.coolTimeCapability).ifPresent(coolTime -> {
                if (coolTime.getTime() == 0) {
                    //
                    coolTime.setTime(50 * 20);
                }
            });

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
            ModItem newItem = ModItemsProvider.getRandomItem(oldItem.itemType, oldItem.quality);
            player.getInventory().setItem(targetIndex, newItem.getDefaultInstance());
        });
        return true;
    }
}
