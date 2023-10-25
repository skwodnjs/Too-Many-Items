package net.jwn.jwn_items.item;

import net.jwn.jwn_items.inventory.MyStuffProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.concurrent.atomic.AtomicBoolean;

public class ModStuffItem extends ModItem {
    public ModStuffItem(Properties pProperties, ItemType itemType, int grade, int ID) {
        super(pProperties, itemType, grade, ID);
    }

    @Override
    protected void playSound(Level level, Player player, SoundEvent soundEvent, float volume, float pitch) {

    }

    @Override
    protected void displayMessage(Player pPlayer, Component message) {

    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        AtomicBoolean success = new AtomicBoolean(false);
        pPlayer.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
            success.set(myStuff.addItem(this));
        });

        Component message;

        if (!success.get()) {
            message = Component.literal("full inventory");
//            playSound();
        } else {
            message = Component.literal("success");
//            playSound(); grades 에 따른 sound 재생
        }

        displayMessage(pPlayer, message);

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
