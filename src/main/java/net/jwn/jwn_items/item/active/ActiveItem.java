package net.jwn.jwn_items.item.active;

import net.jwn.jwn_items.capability.MyStuffProvider;
import net.jwn.jwn_items.event.custom.ModItemUsedSuccessfullyEvent;
import net.jwn.jwn_items.item.ItemType;
import net.jwn.jwn_items.item.ModItem;
import net.jwn.jwn_items.item.Quality;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;

import java.util.concurrent.atomic.AtomicBoolean;

public class ActiveItem extends ModItem {
    private final int coolTimeDefault;
    private final int maxStack;
    private final int levelWeight;

    public ActiveItem(Properties pProperties, int id, Quality quality, int coolTimeDefault, int chargeStack, int levelWeight) {
        super(pProperties, ItemType.ACTIVE, id, quality);
        this.coolTimeDefault = coolTimeDefault;
        this.maxStack = chargeStack;
        this.levelWeight = levelWeight;
    }

    public int getCoolTime(int level) {
        return coolTimeDefault - (level - 1) * levelWeight;
    }

    public int getMaxStack() {
        return maxStack;
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
