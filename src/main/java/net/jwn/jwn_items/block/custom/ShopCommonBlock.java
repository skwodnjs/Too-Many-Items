package net.jwn.jwn_items.block.custom;

import net.jwn.jwn_items.block.blockentity.ShopCommonBlockEntity;
import net.jwn.jwn_items.block.blockentity.SynthesisCommonBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class ShopCommonBlock extends BaseEntityBlock {
    public ShopCommonBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (pPlacer instanceof ServerPlayer player) {
            ((ShopCommonBlockEntity) pLevel.getBlockEntity(pPos)).setOwner(player.getUUID());
        }
    }

    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof SynthesisCommonBlockEntity synthesisBlockCommonEntity) synthesisBlockCommonEntity.drops();
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof ShopCommonBlockEntity shopCommonBlockEntity) {
                if (pPlayer.getUUID().equals(((ShopCommonBlockEntity) pLevel.getBlockEntity(pPos)).getOwner())) {
                    NetworkHooks.openScreen((ServerPlayer) pPlayer, shopCommonBlockEntity, pPos);
                } else {
                    pPlayer.sendSystemMessage(Component.translatable("message.jwn_items.cannot_use_owner_block"));
                }
            } else {
                throw new IllegalStateException("Our container provider is missing!");
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ShopCommonBlockEntity(pPos, pState);
    }
}
