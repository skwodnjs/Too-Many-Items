package net.jwn.jwn_items.item.consumables;

import net.jwn.jwn_items.item.ItemType;
import net.jwn.jwn_items.item.ModItem;
import net.jwn.jwn_items.item.Quality;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ConsumableItem extends ModItem {
    public ConsumableItem(Properties pProperties, int id, Quality quality) {
        super(pProperties, ItemType.CONSUMABLES, id, quality);
    }

    public void operate(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

    }
}
