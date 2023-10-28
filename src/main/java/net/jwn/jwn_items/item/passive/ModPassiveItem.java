package net.jwn.jwn_items.item.passive;

import net.jwn.jwn_items.capability.PlayerStatsProvider;
import net.jwn.jwn_items.item.ItemType;
import net.jwn.jwn_items.item.ModStuffItem;
import net.jwn.jwn_items.stat.Stat;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class ModPassiveItem extends ModStuffItem {
    public final List<Stat> statList;

    public ModPassiveItem(Properties pProperties, int quality, int ID, List<Stat> statList) {
        super(pProperties, ItemType.PASSIVE, quality, ID);
        this.statList = statList;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        if (super.use(pLevel, pPlayer, pUsedHand).getResult() == InteractionResult.sidedSuccess(pLevel.isClientSide())) {
            pPlayer.getCapability(PlayerStatsProvider.playerStatsCapability).ifPresent(playerStats -> {
                statList.forEach(stat -> {
                    playerStats.add(pPlayer, stat);
                });
            });
            return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }
}
