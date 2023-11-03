package net.jwn.jwn_items.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Monster.class)
public class MixinMonster extends PathfinderMob {
    // Monster 말고 처리해야할 것 : Zoglin, Shulker, Ghast, Slime, Phantom, Hoglin ( See Enemy Interface )
    int shockTimer;
    int ahchooTimer;
    Vec3 shockPos;

    protected MixinMonster(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public void baseTick() {
        if (!level().isClientSide) {
            if (getTags().contains("shocked")) {
                // 감전소리 3초 지속
                if (shockTimer == 0) {
                    setNoAi(true);
                    shockPos = this.position();
                }
                setDeltaMovement(0, getDeltaMovement().y - 0.5, 0);
                shockTimer += 1;
                if (shockTimer == 60) {
                    setNoAi(false);
                    shockTimer = 0;
                    getTags().remove("shocked");
                }
            }
            if (getTags().contains("ahchoo")) {
                if (ahchooTimer == 0) {
                    // 재채기 시작 소리
                    setNoAi(true);
                } else if (ahchooTimer == 20) {
                    // 재채기 끝나는 소리
                    setDeltaMovement(0, 0.4, 0);
                    setNoAi(false);
                    ahchooTimer = 0;
                    getTags().remove("ahchoo");
                }
                ahchooTimer += 1;
            }
        }
        super.baseTick();
    }
}
