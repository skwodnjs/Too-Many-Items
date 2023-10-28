package net.jwn.jwn_items.skill;

import net.jwn.jwn_items.networking.ModMessages;
import net.jwn.jwn_items.skill.packet.D1SkillC2SPacket;
import net.minecraft.world.entity.player.Player;

public class ModSkills {
    public static void useSkill(Player player, int ID) {
        if (ID == D1SkillC2SPacket.ID) {
            // you should play sound in both side
            // on client

            // on server
            ModMessages.sendToServer(new D1SkillC2SPacket());
        }
    }
}
