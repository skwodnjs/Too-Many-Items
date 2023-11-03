package net.jwn.jwn_items.item.passive;

import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Battery5V {
    public static void operateServer(Player player) {
        Vec3 pStart = player.position().add(-5, -1, -5);
        Vec3 pEnd = player.position().add(5, 1, 5);
        AABB aabb = new AABB(pStart, pEnd);
        player.level().getEntities(player, aabb).forEach(entity -> {
            if (entity instanceof Monster) {
                System.out.println("SHOCKED!!");
                entity.addTag("shocked");
            }
        });
    }
}
