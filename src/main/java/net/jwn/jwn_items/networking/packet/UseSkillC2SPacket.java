package net.jwn.jwn_items.networking.packet;

import net.jwn.jwn_items.capability.CoolTimeProvider;
import net.jwn.jwn_items.item.ModActiveItem;
import net.jwn.jwn_items.item.ModItem;
import net.jwn.jwn_items.item.ModItems;
import net.jwn.jwn_items.item.active.D1;
import net.jwn.jwn_items.item.active.D6;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class UseSkillC2SPacket {
    private final int ID;
    private final int itemLevel;

    public UseSkillC2SPacket(int ID, int itemLevel) {
        this.ID = ID;
        this.itemLevel = itemLevel;
    }
    public UseSkillC2SPacket(FriendlyByteBuf buf) {
        this.ID = buf.readInt();
        this.itemLevel = buf.readInt();
    }
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.ID);
        buf.writeInt(this.itemLevel);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();

            int itemCoolTime = ((ModActiveItem) ModItems.ModItemsProvider.getItemByID(ID)).getCoolTime(itemLevel);
            int skillStack = ((ModActiveItem) ModItems.ModItemsProvider.getItemByID(ID)).getStack();

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
                if (ID == ((ModItem) ModItems.D1_ITEM.get()).getItemID()) {
                    D1.operateServer(player);
                } else if (ID == ((ModItem) ModItems.D6_ITEM.get()).getItemID()) {
                    D6.operateServer(player);
                }
            }
        });
        return true;
    }
}
