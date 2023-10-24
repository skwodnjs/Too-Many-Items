package net.jwn.jwn_items.item;

import net.jwn.jwn_items.inventory.MyStuffProvider;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class ModStuffItem extends ModItem {
    public ModStuffItem(Properties pProperties, ItemType itemType, int ID) {
        super(pProperties, itemType, ID);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        AtomicBoolean success = new AtomicBoolean(false);
        pPlayer.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
            success.set(myStuff.addItem(this));
        });

        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);

        if (success.get()) {
            return InteractionResultHolder.success(itemstack);
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }
}
