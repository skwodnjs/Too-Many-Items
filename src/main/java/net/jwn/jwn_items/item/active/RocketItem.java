package net.jwn.jwn_items.item.active;

import net.jwn.jwn_items.inventory.MyStuffProvider;
import net.jwn.jwn_items.item.ItemType;
import net.jwn.jwn_items.item.ModStuffItem;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RocketItem extends ModStuffItem {
    public RocketItem(Properties pProperties, int ID) {
        super(pProperties, ItemType.ACTIVE, ID);
    }

    @Override
    protected void playSound(Level level, Player player, SoundEvent soundEvent, float volume, float pitch) {

    }

    @Override
    protected void displayMessage(Player pPlayer, Component message) {
        pPlayer.displayClientMessage(message, true);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        InteractionResultHolder<ItemStack> result = super.use(pLevel, pPlayer, pUsedHand);

        Component message;

        if (result.getResult() == InteractionResult.FAIL) {
            message = Component.literal("fail");
//            playSound();
        } else {
            message = Component.literal("success");
//            playSound();
        }

        displayMessage(pPlayer, message);

        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        if (!pPlayer.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }
}
