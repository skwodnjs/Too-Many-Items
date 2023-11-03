package net.jwn.jwn_items.item.passive;

import net.jwn.jwn_items.item.PassiveItem;
import net.jwn.jwn_items.util.Stat;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Aging extends PassiveItem {
    public Aging(Properties pProperties, int id, int quality, List<Stat> statList) {
        super(pProperties, id, quality, statList);
    }

    public static void operateServer(Player player) {
        Vec3 pStart = player.position().add(-5, -1, -5);
        Vec3 pEnd = player.position().add(5, 1, 5);
        AABB aabb = new AABB(pStart, pEnd);
        player.level().getEntities(player, aabb).forEach(entity -> {
            if (entity instanceof AgeableMob ageableMob) {
                if (ageableMob.getAge() < 0) {
                    ((AgeableMob) entity).setAge(-1);
                }
            }
        });
    }
}
