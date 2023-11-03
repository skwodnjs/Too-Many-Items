package net.jwn.jwn_items.item.active;

import net.jwn.jwn_items.item.*;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.Random;

public class D1 extends ActiveItem {
    public D1(Properties pProperties, int id, int quality, int coolTimeDefault, int chargeStack, int levelWeight) {
        super(pProperties, id, quality, coolTimeDefault, chargeStack, levelWeight);
    }

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
        Random random = new Random();
        int targetIndex = modItemList.get(random.nextInt(modItemList.size()));
        ModItem oldItem = (ModItem) player.getInventory().getItem(targetIndex).getItem();
        ModItem newItem = ModItemProvider.getRandomItem(oldItem.itemType, oldItem.quality);
        player.getInventory().setItem(targetIndex, newItem.getDefaultInstance());
    }
}
