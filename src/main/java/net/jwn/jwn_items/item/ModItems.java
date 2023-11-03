package net.jwn.jwn_items.item;

import net.jwn.jwn_items.Main;
import net.jwn.jwn_items.item.consumables.PillItem;
import net.jwn.jwn_items.stat.Stat;
import net.jwn.jwn_items.stat.StatType;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);
    private static final int ITEM_STACK = 64;

    public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test",
            () -> new TestItem(new Item.Properties().stacksTo(ITEM_STACK)));

    public static final RegistryObject<Item> PILL_ITEM = ITEMS.register("pill",
            () -> new PillItem(new Item.Properties().stacksTo(ITEM_STACK), 0, 0));

    public static final RegistryObject<Item> D1_ITEM = ITEMS.register("d1",
            () -> new ModActiveItem(new Item.Properties().stacksTo(ITEM_STACK), 0, 1, 50, 1, 10));

    public static final RegistryObject<Item> D6_ITEM = ITEMS.register("d6",
            () -> new ModActiveItem(new Item.Properties().stacksTo(ITEM_STACK), 0, 2, 50, 1, 10));

    public static final RegistryObject<Item> MUSTACHE_ITEM = ITEMS.register("mustache",
            () -> new ModPassiveItem(new Item.Properties().stacksTo(ITEM_STACK), 0, 3,
                    Arrays.asList(new Stat(StatType.LUCK_BY_ITEM, 3))
            ));

    public static final RegistryObject<Item> CHARGED_TNT_ITEM = ITEMS.register("charged_tnt",
            () -> new ModActiveItem(new Item.Properties().stacksTo(ITEM_STACK), 0, 4, 30, 3, 5));

    public static final RegistryObject<Item> BATTERY_5V = ITEMS.register("battery_5v",
            () -> new ModPassiveItem(new Item.Properties().stacksTo(ITEM_STACK), 0, 5, List.of()));

    public static final RegistryObject<Item> AGING = ITEMS.register("aging",
            () -> new ModPassiveItem(new Item.Properties().stacksTo(ITEM_STACK), 0, 6, List.of()));

    public static final RegistryObject<Item> RAPID_GROWTH = ITEMS.register("rapid_growth",
            () -> new ModPassiveItem(new Item.Properties().stacksTo(ITEM_STACK), 0, 7, List.of()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static class ModItemsProvider {
        private static final ModItem[] ITEM_ARRAY = (ModItem[]) Arrays.stream((RegistryObject<Item>[]) ITEMS.getEntries().toArray())
                .map(RegistryObject::get).toArray();

        public static final int ITEM_TOTAL = ITEM_ARRAY.length;

        public static int getId(ModItem item) {
            for (int i = 0; i < ITEM_ARRAY.length; i++) {
                if (ITEM_ARRAY[i] == item) return i;
            }
            return -1;
        }

        public static ModItem getItemById(int id) {
            return ITEM_ARRAY[id];
        }

        public static final int ___ITEM_TOTAL = 8;

        private static final ArrayList<ModItem> ACTIVE_ITEMS_QUALITY0 = new ArrayList<>();
        private static final ArrayList<ModItem> ACTIVE_ITEMS_QUALITY1 = new ArrayList<>();
        private static final ArrayList<ModItem> ACTIVE_ITEMS_QUALITY2 = new ArrayList<>();
        private static final ArrayList<ModItem> ACTIVE_ITEMS_QUALITY3 = new ArrayList<>();
        private static final ArrayList<ModItem> ACTIVE_ITEMS_QUALITY4 = new ArrayList<>();
        private static final ArrayList<ModItem> ACTIVE_ITEMS_QUALITY_LEGEND = new ArrayList<>();

        private static final ArrayList<ModItem> PASSIVE_ITEMS_QUALITY0 = new ArrayList<>();
        private static final ArrayList<ModItem> PASSIVE_ITEMS_QUALITY1 = new ArrayList<>();
        private static final ArrayList<ModItem> PASSIVE_ITEMS_QUALITY2 = new ArrayList<>();
        private static final ArrayList<ModItem> PASSIVE_ITEMS_QUALITY3 = new ArrayList<>();
        private static final ArrayList<ModItem> PASSIVE_ITEMS_QUALITY4 = new ArrayList<>();
        private static final ArrayList<ModItem> PASSIVE_ITEMS_QUALITY_LEGEND = new ArrayList<>();

        static {
            ACTIVE_ITEMS_QUALITY0.add((ModItem) ModItems.D1_ITEM.get());
            ACTIVE_ITEMS_QUALITY0.add((ModItem) ModItems.D6_ITEM.get());
            PASSIVE_ITEMS_QUALITY0.add((ModItem) ModItems.MUSTACHE_ITEM.get());
            //
        }

        public static ModItem getRandomItem(ItemType itemType, int quality) {
            for (int i = 0; i< ITEMS.getEntries().size(); i++) {
                System.out.println(((RegistryObject<Item>) ITEMS.getEntries().toArray()[i]).get());
            }
            if (itemType == ItemType.ACTIVE) {
                Random random = new Random();
                int randomIndex;
                switch (quality) {
                    case 0:
                        randomIndex = random.nextInt(ACTIVE_ITEMS_QUALITY0.size());
                        return ACTIVE_ITEMS_QUALITY0.get(randomIndex);
                    case 1:
                        randomIndex = random.nextInt(ACTIVE_ITEMS_QUALITY1.size());
                        return ACTIVE_ITEMS_QUALITY1.get(randomIndex);
                    case 2:
                        randomIndex = random.nextInt(ACTIVE_ITEMS_QUALITY2.size());
                        return ACTIVE_ITEMS_QUALITY2.get(randomIndex);
                    case 3:
                        randomIndex = random.nextInt(ACTIVE_ITEMS_QUALITY3.size());
                        return ACTIVE_ITEMS_QUALITY3.get(randomIndex);
                    case 4:
                        randomIndex = random.nextInt(ACTIVE_ITEMS_QUALITY4.size());
                        return ACTIVE_ITEMS_QUALITY4.get(randomIndex);
                    case 5:
                        randomIndex = random.nextInt(ACTIVE_ITEMS_QUALITY_LEGEND.size());
                        return ACTIVE_ITEMS_QUALITY_LEGEND.get(randomIndex);
                    default:
                        return null;
                }
            }
            else if (itemType == ItemType.PASSIVE) {
                Random random = new Random();
                int randomIndex;
                switch (quality) {
                    case 0:
                        randomIndex = random.nextInt(PASSIVE_ITEMS_QUALITY0.size());
                        return PASSIVE_ITEMS_QUALITY0.get(randomIndex);
                    case 1:
                        randomIndex = random.nextInt(PASSIVE_ITEMS_QUALITY1.size());
                        return PASSIVE_ITEMS_QUALITY1.get(randomIndex);
                    case 2:
                        randomIndex = random.nextInt(PASSIVE_ITEMS_QUALITY2.size());
                        return PASSIVE_ITEMS_QUALITY2.get(randomIndex);
                    case 3:
                        randomIndex = random.nextInt(PASSIVE_ITEMS_QUALITY3.size());
                        return PASSIVE_ITEMS_QUALITY3.get(randomIndex);
                    case 4:
                        randomIndex = random.nextInt(PASSIVE_ITEMS_QUALITY4.size());
                        return PASSIVE_ITEMS_QUALITY4.get(randomIndex);
                    case 5:
                        randomIndex = random.nextInt(PASSIVE_ITEMS_QUALITY_LEGEND.size());
                        return PASSIVE_ITEMS_QUALITY_LEGEND.get(randomIndex);
                    default:
                        return null;
                }
            }
            else {
                return null;
            }
        }

        @Nullable
        public static ModItem ___getItemByID(int id) {
            switch (id) {
                case 0: return (ModItem) ModItems.PILL_ITEM.get();
                case 1: return (ModItem) ModItems.D1_ITEM.get();
                case 2: return (ModItem) ModItems.D6_ITEM.get();
                case 3: return (ModItem) MUSTACHE_ITEM.get();
                case 4: return (ModItem) CHARGED_TNT_ITEM.get();
                case 5: return (ModItem) BATTERY_5V.get();
                case 6: return (ModItem) AGING.get();
                case 7: return (ModItem) RAPID_GROWTH.get();
            };
            return null;
        }
    }
}
