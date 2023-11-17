package net.jwn.jwn_items.util;

import net.jwn.jwn_items.capability.CoolTimeProvider;
import net.jwn.jwn_items.item.ModItem;
import net.jwn.jwn_items.item.ModItems;
import net.jwn.jwn_items.item.active.ActiveItem;
import net.jwn.jwn_items.item.ModItemProvider;
import net.jwn.jwn_items.item.active.ActiveSkill;
import net.jwn.jwn_items.networking.ModMessages;
import net.jwn.jwn_items.networking.packet.UseSkillC2SPacket;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.atomic.AtomicBoolean;

public class ModSkills {
    public static void useSkill(Player player, int id, int itemLevel) {
        // you should play sound in both side
        // on client
        int levelCoolTime = ((ActiveItem) ModItemProvider.getItemById(id)).getCoolTime(itemLevel);
        int skillStack = ((ActiveItem) ModItemProvider.getItemById(id)).getMaxStack();

        AtomicBoolean canUseSkill = new AtomicBoolean(false);
        player.getCapability(CoolTimeProvider.coolTimeCapability).ifPresent(coolTime -> {
            if (coolTime.canUseSkill(levelCoolTime, skillStack)) {
                canUseSkill.set(true);
                coolTime.add(levelCoolTime);
            }
        });

        if (canUseSkill.get()) {
            if (id == ((ModItem) ModItems.STAR.get()).id) {
                ActiveSkill.operateStar(player, itemLevel);
            }
        }

        // on server
        ModMessages.sendToServer(new UseSkillC2SPacket(id, itemLevel));
    }
}
