package net.jwn.jwn_items.item.consumables;

import net.jwn.jwn_items.stat.PlayerStatsProvider;
import net.jwn.jwn_items.stat.StatType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TestItem extends Item {
    public TestItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide) {
            pPlayer.getCapability(PlayerStatsProvider.playerStatsCapability).ifPresent(playerStats -> {
                playerStats.reset(pPlayer);
                System.out.println("reset");
            });
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
