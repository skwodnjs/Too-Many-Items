package net.jwn.jwn_items.item;

import net.jwn.jwn_items.inventory.MyStuffProvider;
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
            System.out.println("reset");
            pPlayer.getCapability(PlayerStatsProvider.playerStatsCapability).ifPresent(playerStats -> {
                playerStats.reset(pPlayer);
            });
        }
        pPlayer.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
            myStuff.reset();
        });
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
