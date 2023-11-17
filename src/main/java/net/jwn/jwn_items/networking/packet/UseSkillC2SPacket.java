package net.jwn.jwn_items.networking.packet;

import net.jwn.jwn_items.capability.CoolTimeProvider;
import net.jwn.jwn_items.item.active.ActiveItem;
import net.jwn.jwn_items.item.ModItem;
import net.jwn.jwn_items.item.ModItemProvider;
import net.jwn.jwn_items.item.ModItems;
import net.jwn.jwn_items.item.active.ActiveSkill;
import net.jwn.jwn_items.util.Functions;
import net.minecraft.client.resources.language.I18n;
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

            int levelCoolTime = ((ActiveItem) ModItemProvider.getItemById(id)).getCoolTime(itemLevel);
            int skillStack = ((ActiveItem) ModItemProvider.getItemById(id)).getMaxStack();

            AtomicBoolean canUseSkill = new AtomicBoolean(false);
            player.getCapability(CoolTimeProvider.coolTimeCapability).ifPresent(coolTime -> {
                if (coolTime.canUseSkill(levelCoolTime, skillStack)) {
                    canUseSkill.set(true);
                    coolTime.add(levelCoolTime);
                } else {
                    String message = I18n.get("message.jwn_items.coolTimeLeft");
                    String secondLeft = I18n.get("message.jwn_items.secondLeft");
                    ModItem item = ModItemProvider.getItemById(id);
                    player.sendSystemMessage(Component.literal(message + " (" + Functions.getWaitingTime(coolTime.get(), (ActiveItem) item, itemLevel) + secondLeft + ")"));
                }
            });

            if (canUseSkill.get()) {
                if (id == ((ModItem) ModItems.D1_ITEM.get()).id) {
                    ActiveSkill.operateD1Server(player);
                } else if (id == ((ModItem) ModItems.D6_ITEM.get()).id) {
                    ActiveSkill.operateD6Server(player);
                } else if (id == ((ModItem) ModItems.CHARGED_TNT_ITEM.get()).id) {
                    ActiveSkill.operateChargedTNT(player);
                } else if (id == ((ModItem) ModItems.PRESCRIPTION.get()).id) {
                    ActiveSkill.operatePrescriptionServer(player);
                } else if (id == ((ModItem) ModItems.RADAR.get()).id) {
                    ActiveSkill.operateRadarServer(player);
                } else if (id == ((ModItem) ModItems.STAR.get()).id) {
                    ActiveSkill.operateStar(player, itemLevel);
                }
            }

            Functions.printModItemData(player);
        });
        return true;
    }
}
