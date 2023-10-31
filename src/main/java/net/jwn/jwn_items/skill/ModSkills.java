package net.jwn.jwn_items.skill;

import net.jwn.jwn_items.capability.CoolTimeProvider;
import net.jwn.jwn_items.networking.ModMessages;
import net.jwn.jwn_items.skill.packet.D1SkillC2SPacket;
import net.jwn.jwn_items.skill.packet.D6SkillC2SPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.atomic.AtomicInteger;

public class ModSkills {
    public static void useSkill(Player player, int ID, int itemLevel) {
        if (ID == D1SkillC2SPacket.ID) {
            // you should play sound in both side
            // on client
            // on server
            ModMessages.sendToServer(new D1SkillC2SPacket(itemLevel));
        } else if (ID == D6SkillC2SPacket.ID) {
            ModMessages.sendToServer(new D6SkillC2SPacket(itemLevel));
        }
    }
}
