package net.jwn.jwn_items.item;

import net.jwn.jwn_items.capability.*;
import net.jwn.jwn_items.event.custom.PlayerStatsChangedEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;

public class TestItem extends Item {
    public TestItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        System.out.println("reset");
        pPlayer.getCapability(PlayerStatProvider.playerStatsCapability).ifPresent(PlayerStat::reset);
        pPlayer.getCapability(CoolTimeProvider.coolTimeCapability).ifPresent(CoolTime::reset);
        pPlayer.getCapability(MyStuffProvider.myStuffCapability).ifPresent(MyStuff::reset);
        pPlayer.getCapability(FoundStuffProvider.foundStuffCapability).ifPresent(FoundStuff::reset);
        MinecraftForge.EVENT_BUS.post(new PlayerStatsChangedEvent(pPlayer));
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
