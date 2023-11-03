package net.jwn.jwn_items.item.passive;

import net.jwn.jwn_items.item.PassiveItem;
import net.jwn.jwn_items.util.Stat;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.CropBlock;

import java.util.List;

public class RapidGrowth extends PassiveItem {
    public RapidGrowth(Properties pProperties, int id, int quality, List<Stat> statList) {
        super(pProperties, id, quality, statList);
    }

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
