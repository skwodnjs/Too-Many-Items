package net.jwn.jwn_items.networking.packet.skills;

import net.jwn.jwn_items.networking.ModMessages;
import net.minecraft.world.entity.player.Player;

public class ModSkills {
    public static void useSkill(Player player, int ID) {
        if (ID == RocketSkillC2SPacket.ID) {
//            player.setDeltaMovement(1,1,0);
            ModMessages.sendToServer(new RocketSkillC2SPacket());
        }
    }
}
