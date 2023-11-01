package net.jwn.jwn_items.item.active;

import net.jwn.jwn_items.item.ItemType;
import net.jwn.jwn_items.item.ModItem;
import net.jwn.jwn_items.item.ModItems;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;

public class D6 {
    public static void operateServer(Player player) {
        ArrayList<Integer> modItemList = new ArrayList<>();
        player.getInventory().items.forEach(itemStack -> {
            if (itemStack.getItem() instanceof ModItem modItem) {
                if (modItem.itemType != ItemType.CONSUMABLES) {
                    int slot = player.getInventory().items.indexOf(itemStack);
                    modItemList.add(slot);
                }
            }
        });
        for (int index : modItemList) {
            ModItem oldItem = (ModItem) player.getInventory().getItem(index).getItem();
            System.out.println(oldItem);
            ModItem newItem = ModItems.ModItemsProvider.getRandomItem(oldItem.itemType, oldItem.quality);
            player.getInventory().setItem(index, newItem.getDefaultInstance());
        }
    }
}
