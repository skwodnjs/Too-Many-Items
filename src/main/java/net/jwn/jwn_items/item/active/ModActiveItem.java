package net.jwn.jwn_items.item.active;

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

public class ModActiveItem extends ModStuffItem {
    public ModActiveItem(Properties pProperties, int grade, int ID) {
        super(pProperties, ItemType.ACTIVE, grade, ID);
    }
}
