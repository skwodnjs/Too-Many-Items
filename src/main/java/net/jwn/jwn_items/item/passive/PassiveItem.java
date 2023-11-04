package net.jwn.jwn_items.item.passive;

import net.jwn.jwn_items.capability.MyStuffProvider;
import net.jwn.jwn_items.capability.PlayerStatProvider;
import net.jwn.jwn_items.event.custom.ModItemUsedSuccessfullyEvent;
import net.jwn.jwn_items.item.ItemType;
import net.jwn.jwn_items.item.ModItem;
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

public class PassiveItem extends ModItem {
    public final List<Stat> statList;

    public PassiveItem(Properties pProperties, int id, int quality, List<Stat> statList) {
        super(pProperties, ItemType.PASSIVE, id, quality);
        this.statList = statList;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        AtomicBoolean success = new AtomicBoolean(false);
        Component message;
        pPlayer.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
            success.set(myStuff.addItem(this));
        });
        if (!success.get()) {
            message = Component.literal("fail");
        } else {
            message = Component.literal("success");
            pPlayer.getCapability(PlayerStatProvider.playerStatsCapability).ifPresent(playerStats -> {
                statList.forEach(stat -> {
                    playerStats.add(pPlayer, stat);
                });
            });
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
