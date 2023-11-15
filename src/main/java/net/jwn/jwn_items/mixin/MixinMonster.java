package net.jwn.jwn_items.mixin;

import net.jwn.jwn_items.Timer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

import java.util.HashMap;

@Mixin(Monster.class)
public abstract class MixinMonster extends PathfinderMob implements Timer {
    // Monster 가 아닌 것 : Zoglin, Shulker, Ghast, Slime, Phantom, Hoglin ( See Enemy Interface )
    // Monster 에 해당하는 것 : Blaze, Spider(Cave also), Warden, Giant, WitherBoss, Vex, EnderMan, Gardian, Piglin, Envermite,
    // Zombie, Zoglin, Skeleton, Creeper, PatrollingMonster, Silverfish

    // Monster 에 해당되는데 뭔가 적용하면 안될 것 같은 것 : Warden, WitherBoss
    // Monster 은 아닌데 적용해야 할 것 같은 것 : Shulker, Ghast, Phantom

    protected MixinMonster(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    private void tagStunCycle(String tag, int duration) {
        String newTag = tag + (level().isClientSide ? "Client" : "Server");
        if (!timer.containsKey(newTag)) timer.put(newTag, 0);
        timer.compute(newTag, (k, v) -> v + 1);

        if (!level().isClientSide) {
            if (timer.get(newTag) == 1) {
                setNoAi(true);
            } else if (timer.get(newTag) == duration) {
                setNoAi(false);
            }
        }

        if (timer.get(newTag) == duration) {
            timer.replace(newTag, 0);
            getTags().remove(tag);
        }
    }

    @Override
    public void baseTick() {
        if (getTags().contains("shocked")) {
            tagStunCycle("shocked", 40);
        }
        if (getTags().contains("ahchoo")) {
            tagStunCycle("ahchoo", 40);
        }
        super.baseTick();
    }
}
