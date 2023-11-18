package net.jwn.jwn_items.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ColdLavaBlockEntity extends BlockEntity implements TickBlockEntity {
    private int ticks;

    public ColdLavaBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.COLD_LAVA_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public void tick() {
        ticks++;
        if (ticks >= 3 * 20) {
            level.setBlock(getBlockPos(), Blocks.LAVA.defaultBlockState(), 3);
        }
    }
}
