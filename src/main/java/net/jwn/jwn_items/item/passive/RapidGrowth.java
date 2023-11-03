package net.jwn.jwn_items.item.passive;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.CropBlock;

public class RapidGrowth {
    public static void operateServer(Player player) {
        for (int i = -5; i <= 5; i++) {
            for (int j = -5; j <= 5; j++) {
                for (int k = 0; k < 2; k++) {
                    BlockPos targetBlockPos = player.getOnPos().offset(i, k, j);
                    if (player.level().getBlockState(targetBlockPos).getBlock() instanceof CropBlock cropBlock) {
                        if (Math.random() < 0.01) cropBlock.growCrops(player.level(), targetBlockPos, player.level().getBlockState(targetBlockPos));
                    }
                }
            }
        }
    }
}
