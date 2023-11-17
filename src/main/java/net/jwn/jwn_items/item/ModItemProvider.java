package net.jwn.jwn_items.item;

import java.util.ArrayList;
import java.util.Random;

public class ModItemProvider {
    public static final int ITEM_TOTAL = 15;

    public static ModItem getRandomItem(ItemType itemType, int quality) {
        ArrayList<ModItem> modItems = new ArrayList<>();
        for (int id = 0; id < ITEM_TOTAL; id++) {
            if (getItemById(id).itemType == itemType && getItemById(id).quality == quality) {
                modItems.add(getItemById(id));
            }
        }
        Random random = new Random();
        int randomIndex = random.nextInt(modItems.size());
        return modItems.get(randomIndex);
    }

    public static ModItem getItemById(int id) {
        switch (id) {
            case 0: return (ModItem) ModItems.PILL_ITEM.get();
            case 1: return (ModItem) ModItems.D1_ITEM.get();
            case 2: return (ModItem) ModItems.D6_ITEM.get();
            case 3: return (ModItem) ModItems.MUSTACHE_ITEM.get();
            case 4: return (ModItem) ModItems.CHARGED_TNT_ITEM.get();
            case 5: return (ModItem) ModItems.BATTERY_5V.get();
            case 6: return (ModItem) ModItems.AGING.get();
            case 7: return (ModItem) ModItems.RAPID_GROWTH.get();
            case 8: return (ModItem) ModItems.AUTO_PORTION.get();
            case 9: return (ModItem) ModItems.COIN.get();
            case 10: return (ModItem) ModItems.COIN10.get();
            case 11: return (ModItem) ModItems.PRESCRIPTION.get();
            case 12: return (ModItem) ModItems.RADAR.get();
            case 13: return (ModItem) ModItems.STAR.get();
            case 14: return (ModItem) ModItems.LAVA_WALKER.get();
        };
        return null;
    }
}
