package net.jwn.jwn_items.item.passive;

import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Aging {
    public static void operateServer(Player player) {
        Vec3 pStart = player.position().add(-5, -1, -5);
        Vec3 pEnd = player.position().add(5, 1, 5);
        AABB aabb = new AABB(pStart, pEnd);
        player.level().getEntities(player, aabb).forEach(entity -> {
            if (entity instanceof AgeableMob ageableMob) {
                if (ageableMob.getAge() < 0) {
                    System.out.println("AGING!!");
                    ((AgeableMob) entity).setAge(-1);
                }
            }
        });
    }
}
