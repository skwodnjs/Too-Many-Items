package net.jwn.jwn_items.util;

import net.jwn.jwn_items.item.ItemType;
import net.jwn.jwn_items.item.ModItem;
import net.jwn.jwn_items.item.ModItems;

import java.util.ArrayList;
import java.util.Random;

public class ModItemProvider {
    private static final ArrayList<ModItem> ACTIVE_ITEMS_GRADE0 = new ArrayList<>();
    private static final ArrayList<ModItem> ACTIVE_ITEMS_GRADE1 = new ArrayList<>();
    private static final ArrayList<ModItem> ACTIVE_ITEMS_GRADE2 = new ArrayList<>();
    private static final ArrayList<ModItem> ACTIVE_ITEMS_GRADE3 = new ArrayList<>();
    private static final ArrayList<ModItem> ACTIVE_ITEMS_GRADE4 = new ArrayList<>();
    private static final ArrayList<ModItem> ACTIVE_ITEMS_GRADE_LEGEND = new ArrayList<>();

    private static final ArrayList<ModItem> PASSIVE_ITEMS_GRADE0 = new ArrayList<>();
    private static final ArrayList<ModItem> PASSIVE_ITEMS_GRADE1 = new ArrayList<>();
    private static final ArrayList<ModItem> PASSIVE_ITEMS_GRADE2 = new ArrayList<>();
    private static final ArrayList<ModItem> PASSIVE_ITEMS_GRADE3 = new ArrayList<>();
    private static final ArrayList<ModItem> PASSIVE_ITEMS_GRADE4 = new ArrayList<>();
    private static final ArrayList<ModItem> PASSIVE_ITEMS_GRADE_LEGEND = new ArrayList<>();

    static {
        ACTIVE_ITEMS_GRADE0.add((ModItem) ModItems.D1_ITEM.get());
        ACTIVE_ITEMS_GRADE0.add((ModItem) ModItems.D6_ITEM.get());

        //
    }

    public static ModItem getRandomItem(ItemType itemType, int grade) {
        if (itemType == ItemType.ACTIVE) {
            Random random = new Random();
            int randomIndex;
            switch (grade) {
                case 0:
                    randomIndex = random.nextInt(ACTIVE_ITEMS_GRADE0.size());
                    return ACTIVE_ITEMS_GRADE0.get(randomIndex);
                case 1:
                    randomIndex = random.nextInt(ACTIVE_ITEMS_GRADE1.size());
                    return ACTIVE_ITEMS_GRADE1.get(randomIndex);
                case 2:
                    randomIndex = random.nextInt(ACTIVE_ITEMS_GRADE2.size());
                    return ACTIVE_ITEMS_GRADE2.get(randomIndex);
                case 3:
                    randomIndex = random.nextInt(ACTIVE_ITEMS_GRADE3.size());
                    return ACTIVE_ITEMS_GRADE3.get(randomIndex);
                case 4:
                    randomIndex = random.nextInt(ACTIVE_ITEMS_GRADE4.size());
                    return ACTIVE_ITEMS_GRADE4.get(randomIndex);
                case 5:
                    randomIndex = random.nextInt(ACTIVE_ITEMS_GRADE_LEGEND.size());
                    return ACTIVE_ITEMS_GRADE_LEGEND.get(randomIndex);
                default:
                    return null;
            }
        }
        else if (itemType == ItemType.PASSIVE) {
            Random random = new Random();
            int randomIndex;
            switch (grade) {
                case 0:
                    randomIndex = random.nextInt(PASSIVE_ITEMS_GRADE0.size());
                    return PASSIVE_ITEMS_GRADE0.get(randomIndex);
                case 1:
                    randomIndex = random.nextInt(PASSIVE_ITEMS_GRADE1.size());
                    return PASSIVE_ITEMS_GRADE1.get(randomIndex);
                case 2:
                    randomIndex = random.nextInt(PASSIVE_ITEMS_GRADE2.size());
                    return PASSIVE_ITEMS_GRADE2.get(randomIndex);
                case 3:
                    randomIndex = random.nextInt(PASSIVE_ITEMS_GRADE3.size());
                    return PASSIVE_ITEMS_GRADE3.get(randomIndex);
                case 4:
                    randomIndex = random.nextInt(PASSIVE_ITEMS_GRADE4.size());
                    return PASSIVE_ITEMS_GRADE4.get(randomIndex);
                case 5:
                    randomIndex = random.nextInt(PASSIVE_ITEMS_GRADE_LEGEND.size());
                    return PASSIVE_ITEMS_GRADE_LEGEND.get(randomIndex);
                default:
                    return null;
            }
        }
        else {
            return null;
        }
    }
}
