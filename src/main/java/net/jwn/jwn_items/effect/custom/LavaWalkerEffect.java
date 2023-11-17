package net.jwn.jwn_items.effect.custom;

import net.jwn.jwn_items.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;

public class LavaWalkerEffect extends MobEffect {
    public LavaWalkerEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (pLivingEntity instanceof Player player) {
            for (int i = -4; i <= 4; i++) {
                for (int j = -4; j <= 4; j++) {
                    BlockPos targetBlockPos = player.getOnPos().offset(i, 0, j);
                    BlockState targetBlockState = player.level().getBlockState(targetBlockPos);
                    if (targetBlockState.getBlock() == Blocks.LAVA) {
                        if (((LiquidBlock) Blocks.LAVA).getFluidState(targetBlockState).isSource()) {
                            player.level().setBlock(targetBlockPos, ModBlocks.COLD_LAVA.get().defaultBlockState(), 3);
                        }
                    }
                }
            }
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
