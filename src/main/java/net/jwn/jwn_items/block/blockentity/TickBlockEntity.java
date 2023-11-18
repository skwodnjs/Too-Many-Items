package net.jwn.jwn_items.block.blockentity;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;

public interface TickBlockEntity {
    void tick();

    static <T extends BlockEntity> BlockEntityTicker<T> getTickerHelper(Level level) {
        return level.isClientSide() ? null : (level0, pos0, state0, blockEntity) -> ((TickBlockEntity)blockEntity).tick();
    }
}