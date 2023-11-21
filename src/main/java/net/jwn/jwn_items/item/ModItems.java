package net.jwn.jwn_items.item;

import net.jwn.jwn_items.Main;
import net.jwn.jwn_items.item.active.ActiveItem;
import net.jwn.jwn_items.item.consumables.CardIllusionItem;
import net.jwn.jwn_items.item.consumables.CoinItem;
import net.jwn.jwn_items.item.consumables.PillItem;
import net.jwn.jwn_items.item.passive.PassiveItem;
import net.jwn.jwn_items.util.Stat;
import net.jwn.jwn_items.util.StatType;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);
    public static final int ITEM_STACK = 64;

    public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test",
            () -> new TestItem(new Item.Properties().stacksTo(ITEM_STACK)));
    public static final RegistryObject<Item> PILL_ITEM = ITEMS.register("pill",
            () -> new PillItem(new Item.Properties().stacksTo(ITEM_STACK), 0, Quality.COMMON));
    public static final RegistryObject<Item> D1_ITEM = ITEMS.register("d1",
            () -> new ActiveItem(new Item.Properties().stacksTo(ITEM_STACK), 1, Quality.COMMON, 45 * 20, 1, 10 * 20));
    public static final RegistryObject<Item> D6_ITEM = ITEMS.register("d6",
            () -> new ActiveItem(new Item.Properties().stacksTo(ITEM_STACK), 2, Quality.COMMON, 45 * 20, 1, 10 * 20));
    public static final RegistryObject<Item> MUSTACHE_ITEM = ITEMS.register("mustache",
            () -> new PassiveItem(new Item.Properties().stacksTo(ITEM_STACK), 3, Quality.COMMON,
                    List.of(new Stat(StatType.LUCK_BY_ITEM, 3)),
                    List.of()
            ));
    public static final RegistryObject<Item> CHARGED_TNT_ITEM = ITEMS.register("charged_tnt",
            () -> new ActiveItem(new Item.Properties().stacksTo(ITEM_STACK), 4, Quality.COMMON, 30 * 20, 3, 5 * 20));
    public static final RegistryObject<Item> BATTERY_5V = ITEMS.register("battery_5v",
            () -> new PassiveItem(new Item.Properties().stacksTo(ITEM_STACK), 5, Quality.COMMON,
                    List.of(new Stat(StatType.ATTACK_SPEED_BY_ITEM, 3)),
                    List.of()
            ));
    public static final RegistryObject<Item> AGING = ITEMS.register("aging",
            () -> new PassiveItem(new Item.Properties().stacksTo(ITEM_STACK), 6, Quality.COMMON,
                    List.of(),
                    List.of(new Stat(StatType.HEALTH_BY_ITEM, 6))
            ));
    public static final RegistryObject<Item> RAPID_GROWTH = ITEMS.register("rapid_growth",
            () -> new PassiveItem(new Item.Properties().stacksTo(ITEM_STACK), 7, Quality.COMMON,
                    List.of(),
                    List.of()
            ));
    public static final RegistryObject<Item> AUTO_PORTION = ITEMS.register("auto_portion",
            () -> new PassiveItem(new Item.Properties().stacksTo(ITEM_STACK), 8, Quality.COMMON,
                    List.of(),
                    List.of()
            ));
    public static final RegistryObject<Item> COIN = ITEMS.register("coin",
            () -> new CoinItem(new Item.Properties().stacksTo(ITEM_STACK), 9, Quality.COMMON, 1));
        public static final RegistryObject<Item> COIN10 = ITEMS.register("coin10",
            () -> new CoinItem(new Item.Properties().stacksTo(ITEM_STACK), 10, Quality.COMMON, 10));
    public static final RegistryObject<Item> PRESCRIPTION = ITEMS.register("prescription",
            () -> new ActiveItem(new Item.Properties().stacksTo(ITEM_STACK), 11, Quality.COMMON, 50 * 20, 1, 10 * 20));
    public static final RegistryObject<Item> RADAR = ITEMS.register("radar",
            () -> new ActiveItem(new Item.Properties().stacksTo(ITEM_STACK), 12, Quality.COMMON, 50 * 20, 1, 10 * 20));
    public static final RegistryObject<Item> STAR = ITEMS.register("star",
            () -> new ActiveItem(new Item.Properties().stacksTo(ITEM_STACK), 13, Quality.COMMON, 300 * 20, 1, 0));
    public static final RegistryObject<Item> LAVA_WALKER = ITEMS.register("lava_walker",
            () -> new ActiveItem(new Item.Properties().stacksTo(ITEM_STACK), 14, Quality.COMMON, 130 * 20, 1, 20 * 20));
    public static final RegistryObject<Item> CARD_ILLUSION = ITEMS.register("card_illusion",
            () -> new CardIllusionItem(new Item.Properties().stacksTo(ITEM_STACK), 15, Quality.COMMON));
    public static final RegistryObject<Item> INSECT_REPELLENT = ITEMS.register("insect_repellent",
            () -> new PassiveItem(new Item.Properties().stacksTo(ITEM_STACK), 16, Quality.COMMON,
                    List.of(new Stat(StatType.MOVEMENT_SPEED_BY_ITEM, 3), new Stat(StatType.ATTACK_RANGE_BY_ITEM, -1)),
                    List.of(new Stat(StatType.MOVEMENT_SPEED_BY_ITEM, 3))
            ));
    public static final RegistryObject<Item> KIMCHI = ITEMS.register("kimchi",
            () -> new PassiveItem(new Item.Properties().stacksTo(ITEM_STACK), 17, Quality.COMMON,
                    List.of(new Stat(StatType.ATTACK_SPEED_BY_ITEM, 3), new Stat(StatType.MOVEMENT_SPEED_BY_ITEM, -1)),
                    List.of(new Stat(StatType.ATTACK_SPEED_BY_ITEM, 3))
            ));
    public static final RegistryObject<Item> FRACTURED_SKULL = ITEMS.register("fractured_skull",
            () -> new PassiveItem(new Item.Properties().stacksTo(ITEM_STACK), 18, Quality.COMMON,
                    List.of(new Stat(StatType.ATTACK_RANGE_BY_ITEM, 3), new Stat(StatType.MINING_SPEED_BY_ITEM, -1)),
                    List.of(new Stat(StatType.ATTACK_RANGE_BY_ITEM, 3))
            ));
    public static final RegistryObject<Item> SPINACH = ITEMS.register("spinach",
            () -> new PassiveItem(new Item.Properties().stacksTo(ITEM_STACK), 19, Quality.COMMON,
                    List.of(new Stat(StatType.DAMAGE_BY_ITEM, 3), new Stat(StatType.ATTACK_SPEED_BY_ITEM, -1)),
                    List.of(new Stat(StatType.DAMAGE_BY_ITEM, 3))
            ));
    public static final RegistryObject<Item> SLIME_MONSTER = ITEMS.register("slime_monster",
            () -> new PassiveItem(new Item.Properties().stacksTo(ITEM_STACK), 20, Quality.COMMON,
                    List.of(new Stat(StatType.MINING_SPEED_BY_ITEM, 3), new Stat(StatType.MOVEMENT_SPEED_BY_ITEM, -1)),
                    List.of(new Stat(StatType.MINING_SPEED_BY_ITEM, 3))
            ));
    public static final RegistryObject<Item> OLD_RING = ITEMS.register("old_ring",
            () -> new PassiveItem(new Item.Properties().stacksTo(ITEM_STACK), 21, Quality.COMMON,
                    List.of(),
                    List.of()
            ));
    public static final RegistryObject<Item> COIN_PURSE = ITEMS.register("coin_purse",
            () -> new PassiveItem(new Item.Properties().stacksTo(ITEM_STACK), 22, Quality.COMMON,
                    List.of(),
                    List.of()
            ));
    public static final RegistryObject<Item> ELECTRIC_FLY_SWATTER = ITEMS.register("electric_fly_swatter",
            () -> new PassiveItem(new Item.Properties().stacksTo(ITEM_STACK), 23, Quality.COMMON,
                    List.of(),
                    List.of()
            ));
    public static final RegistryObject<Item> GHAST_SNOT = ITEMS.register("ghast_snot",
            () -> new PassiveItem(new Item.Properties().stacksTo(ITEM_STACK), 24, Quality.COMMON,
                    List.of(),
                    List.of()
            ));
    public static final RegistryObject<Item> GH_INJECTION = ITEMS.register("gh_injection",
            () -> new PassiveItem(new Item.Properties().stacksTo(ITEM_STACK), 25, Quality.COMMON,
                    List.of(),
                    List.of()
            ));
    public static final RegistryObject<Item> OLD_PILLOW = ITEMS.register("old_pillow",
            () -> new PassiveItem(new Item.Properties().stacksTo(ITEM_STACK), 26, Quality.COMMON,
                    List.of(),
                    List.of()
            ));
    public static final RegistryObject<Item> MYSTERIOUS_LIQUID = ITEMS.register("mysterious_liquid",
            () -> new PassiveItem(new Item.Properties().stacksTo(ITEM_STACK), 27, Quality.COMMON,
                    List.of(),
                    List.of()
            ));
    public static final RegistryObject<Item> EDIBLE_GOLD_DUST = ITEMS.register("edible_gold_dust",
            () -> new PassiveItem(new Item.Properties().stacksTo(ITEM_STACK), 28, Quality.COMMON,
                    List.of(),
                    List.of()
            ));
    public static final RegistryObject<Item> DUST_BUNNY = ITEMS.register("dust_bunny",
            () -> new PassiveItem(new Item.Properties().stacksTo(ITEM_STACK), 29, Quality.COMMON,
                    List.of(),
                    List.of()
            ));
    public static final RegistryObject<Item> ROASTED_EGG = ITEMS.register("roasted_egg",
            () -> new PassiveItem(new Item.Properties().stacksTo(ITEM_STACK), 30, Quality.COMMON,
                    List.of(),
                    List.of()
            ));
    public static final RegistryObject<Item> CRACKED_TRIANGULAR_FLASK = ITEMS.register("cracked_triangular_flask",
            () -> new PassiveItem(new Item.Properties().stacksTo(ITEM_STACK), 31, Quality.COMMON,
                    List.of(),
                    List.of()
            ));
    public static final RegistryObject<Item> FREEZE_DRIED_PORK_FEED = ITEMS.register("freeze_dried_pork_feed",
            () -> new PassiveItem(new Item.Properties().stacksTo(ITEM_STACK), 32, Quality.COMMON,
                    List.of(),
                    List.of()
            ));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
