package net.jwn.jwn_items.skill.packet;

import net.jwn.jwn_items.capability.CoolTimeProvider;
import net.jwn.jwn_items.item.ItemType;
import net.jwn.jwn_items.item.ModItem;
import net.jwn.jwn_items.item.ModItems;
import net.jwn.jwn_items.item.ModItems.ModItemsProvider;
import net.jwn.jwn_items.item.active.ModActiveItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class D6SkillC2SPacket {
    public static final int ID = ((ModItem) ModItems.D6_ITEM.get()).getItemID();
    public int itemLevel;

    public D6SkillC2SPacket(int itemLevel) {
        this.itemLevel = itemLevel;
    }
    public D6SkillC2SPacket(FriendlyByteBuf buf) {
        this.itemLevel = buf.readInt();
    }
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.itemLevel);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();

            // cool time
            int itemCoolTime = ((ModActiveItem) ModItemsProvider.getItemByID(ID)).getCoolTime(itemLevel);
            int skillStack = ((ModActiveItem) ModItemsProvider.getItemByID(ID)).getStack();

            AtomicBoolean canUseSkill = new AtomicBoolean(false);
            player.getCapability(CoolTimeProvider.coolTimeCapability).ifPresent(coolTime -> {
                if (coolTime.canUseSkill(itemCoolTime, skillStack)) {
                    canUseSkill.set(true);
                    coolTime.addTime(itemCoolTime);
                } else {
                    player.sendSystemMessage(Component.literal("can't use now\n" + (coolTime.getTime() / 20.0) + " seconds left"));
                }
            });

            if (canUseSkill.get()) {
                // skill
                ArrayList<Integer> modItemList = new ArrayList<>();
                player.getInventory().items.forEach(itemStack -> {
                    if (itemStack.getItem() instanceof ModItem modItem) {
                        if (modItem.itemType != ItemType.CONSUMABLES) {
                            int slot = player.getInventory().items.indexOf(itemStack);
                            modItemList.add(slot);
                        }
                    }
                });
                for (int index : modItemList) {
                    ModItem oldItem = (ModItem) player.getInventory().getItem(index).getItem();
                    System.out.println(oldItem);
                    ModItem newItem = ModItemsProvider.getRandomItem(oldItem.itemType, oldItem.quality);
                    player.getInventory().setItem(index, newItem.getDefaultInstance());
                }
            }
        });
        return true;
    }
}
