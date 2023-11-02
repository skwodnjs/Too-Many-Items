package net.jwn.jwn_items.item.passive;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Mustache {

    // Player Tick Event
    public static void operateServer(Player player) {
        Vec3 pStart = player.position().add(-5, -1, -5);
        Vec3 pEnd = player.position().add(5, 1, 5);
        AABB aabb = new AABB(pStart, pEnd);
        player.level().getEntities(player, aabb).forEach(entity -> {
            if (entity instanceof Mob) {
                double r = Math.random();
                if (r < 0.002) {
                    entity.setDeltaMovement(0, 0.7, 0);
                }
            }
        });
    }
}
