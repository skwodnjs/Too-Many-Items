package net.jwn.jwn_items.item.passive;

import net.jwn.jwn_items.capability.MyStuffProvider;
import net.jwn.jwn_items.capability.PlayerStatProvider;
import net.jwn.jwn_items.event.custom.ModItemUsedSuccessfullyEvent;
import net.jwn.jwn_items.item.ItemType;
import net.jwn.jwn_items.item.ModItem;
import net.jwn.jwn_items.item.Quality;
import net.jwn.jwn_items.util.Stat;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class PassiveItem extends ModItem {
    public final List<Stat> statListLv1;
    public final List<Stat> statListLv5;

    public PassiveItem(Properties pProperties, int id, Quality quality, List<Stat> statListLv1, List<Stat> statListLv5) {
        super(pProperties, ItemType.PASSIVE, id, quality);
        this.statListLv1 = statListLv1;
        this.statListLv5 = statListLv5;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        AtomicBoolean success = new AtomicBoolean(false);
        Component message;
        AtomicInteger itemLevel = new AtomicInteger();
        pPlayer.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
            success.set(myStuff.addItem(this));
            itemLevel.set(myStuff.getLevelById(this.id));
        });
        if (!success.get()) {
            message = Component.literal("fail");
        } else {
            message = Component.literal("success");
            if (itemLevel.get() == 1) {
                pPlayer.getCapability(PlayerStatProvider.playerStatsCapability).ifPresent(playerStat -> {
                    statListLv1.forEach(stat -> {
                        playerStat.add(pPlayer, stat);
                    });
                });
            } else if (itemLevel.get() == 5) {
                pPlayer.getCapability(PlayerStatProvider.playerStatsCapability).ifPresent(playerStat -> {
                    statListLv5.forEach(stat -> {
                        playerStat.add(pPlayer, stat);
                    });
                });
            }
            MinecraftForge.EVENT_BUS.post(new ModItemUsedSuccessfullyEvent(pPlayer, itemType, this.id));
        }
        pPlayer.displayClientMessage(message, true);

        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);

        if (success.get()) {
            if (!pPlayer.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }
}
