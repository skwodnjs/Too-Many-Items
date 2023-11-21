package net.jwn.jwn_items.item;

import java.util.ArrayList;
import java.util.Random;

public class ModItemProvider {
    public static final int ITEM_TOTAL = 33;

    public static ModItem getRandomItem(ItemType itemType, Quality quality) {
        ArrayList<ModItem> modItems = new ArrayList<>();
        for (int id = 0; id < ITEM_TOTAL; id++) {
            if (getItemById(id).itemType == itemType && getItemById(id).quality == quality) {
                getItemById(id);
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
            case 15: return (ModItem) ModItems.CARD_ILLUSION.get();
            case 16: return (ModItem) ModItems.INSECT_REPELLENT.get();
            case 17: return (ModItem) ModItems.KIMCHI.get();
            case 18: return (ModItem) ModItems.FRACTURED_SKULL.get();
            case 19: return (ModItem) ModItems.SPINACH.get();
            case 20: return (ModItem) ModItems.SLIME_MONSTER.get();
            case 21: return (ModItem) ModItems.OLD_RING.get();
            case 22: return (ModItem) ModItems.COIN_PURSE.get();
            case 23: return (ModItem) ModItems.ELECTRIC_FLY_SWATTER.get();
            case 24: return (ModItem) ModItems.GHAST_SNOT.get();
            case 25: return (ModItem) ModItems.GH_INJECTION.get();
            case 26: return (ModItem) ModItems.OLD_PILLOW.get();
            case 27: return (ModItem) ModItems.MYSTERIOUS_LIQUID.get();
            case 28: return (ModItem) ModItems.EDIBLE_GOLD_DUST.get();
            case 29: return (ModItem) ModItems.DUST_BUNNY.get();
            case 30: return (ModItem) ModItems.ROASTED_EGG.get();
            case 31: return (ModItem) ModItems.CRACKED_TRIANGULAR_FLASK.get();
            case 32: return (ModItem) ModItems.FREEZE_DRIED_PORK_FEED.get();
        };
        return null;
    }
}
