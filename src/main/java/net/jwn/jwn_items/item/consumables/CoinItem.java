package net.jwn.jwn_items.item.consumables;

import net.jwn.jwn_items.capability.PlayerStatProvider;
import net.jwn.jwn_items.event.custom.ModItemUsedSuccessfullyEvent;
import net.jwn.jwn_items.item.Quality;
import net.jwn.jwn_items.util.Stat;
import net.jwn.jwn_items.util.StatType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;

public class CoinItem extends ConsumableItem {
    final int value;
    public CoinItem(Properties pProperties, int id, Quality quality, int value) {
        super(pProperties, id, quality);
        this.value = value;
    }

    @Override
    public void operate(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        pPlayer.getCapability(PlayerStatProvider.playerStatsCapability).ifPresent(playerStat -> {
            playerStat.add(pPlayer, new Stat(StatType.COIN, value));
        });
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        operate(pLevel, pPlayer, pUsedHand);
        MinecraftForge.EVENT_BUS.post(new ModItemUsedSuccessfullyEvent(pPlayer, itemType, id));

        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        if (!pPlayer.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }
}

