package net.jwn.jwn_items.util;

import net.jwn.jwn_items.item.ModItem;
import net.jwn.jwn_items.item.ModItems;
import net.jwn.jwn_items.networking.ModMessages;
import net.jwn.jwn_items.networking.packet.UseSkillC2SPacket;
import net.minecraft.world.entity.player.Player;

public class ModSkills {
    public static void useSkill(Player player, int ID, int itemLevel) {
        // you should play sound in both side
        // on client
        if (ID == ((ModItem) ModItems.D1_ITEM.get()).getItemID()) {
            // on client
        } else if (ID == ((ModItem) ModItems.D6_ITEM.get()).getItemID()) {
            // on client
        }
        // on server
        ModMessages.sendToServer(new UseSkillC2SPacket(ID, itemLevel));
    }
}
