package net.jwn.jwn_items.networking.packet;

import net.jwn.jwn_items.capability.CoolTimeProvider;
import net.jwn.jwn_items.item.ActiveItem;
import net.jwn.jwn_items.item.ModItem;
import net.jwn.jwn_items.item.ModItemProvider;
import net.jwn.jwn_items.item.ModItems;
import net.jwn.jwn_items.item.active.ChargedTNT;
import net.jwn.jwn_items.item.active.D1;
import net.jwn.jwn_items.item.active.D6;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class UseSkillC2SPacket {
    private final int id;
    private final int itemLevel;

    public UseSkillC2SPacket(int id, int itemLevel) {
        this.id = id;
        this.itemLevel = itemLevel;
    }
    public UseSkillC2SPacket(FriendlyByteBuf buf) {
        this.id = buf.readInt();
        this.itemLevel = buf.readInt();
    }
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.id);
        buf.writeInt(this.itemLevel);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();

            int adjustedCoolTime = ((ActiveItem) ModItemProvider.getItemById(id)).getCoolTime(itemLevel);
            int skillStack = ((ActiveItem) ModItemProvider.getItemById(id)).getChargeStack();

            AtomicBoolean canUseSkill = new AtomicBoolean(false);
            player.getCapability(CoolTimeProvider.coolTimeCapability).ifPresent(coolTime -> {
                if (coolTime.canUseSkill(adjustedCoolTime, skillStack)) {
                    canUseSkill.set(true);
                    coolTime.add(adjustedCoolTime);
                } else {
                    player.sendSystemMessage(Component.literal("can't use now"));
                }
            });

            if (canUseSkill.get()) {
                if (id == ((ModItem) ModItems.D1_ITEM.get()).id) {
                    D1.operateServer(player);
                } else if (id == ((ModItem) ModItems.D6_ITEM.get()).id) {
                    D6.operateServer(player);
                } else if (id == ((ModItem) ModItems.CHARGED_TNT_ITEM.get()).id) {
                    ChargedTNT.operateServer(player);
                }
            }
        });
        return true;
    }
}
