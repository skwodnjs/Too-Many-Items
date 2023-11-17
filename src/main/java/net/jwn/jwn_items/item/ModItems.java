package net.jwn.jwn_items.item;

import net.jwn.jwn_items.Main;
import net.jwn.jwn_items.item.active.ActiveItem;
import net.jwn.jwn_items.item.consumables.Coin;
import net.jwn.jwn_items.item.consumables.Pill;
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
    private static final int ITEM_STACK = 64;

    public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test",
            () -> new TestItem(new Item.Properties().stacksTo(ITEM_STACK)));

    public static final RegistryObject<Item> PILL_ITEM = ITEMS.register("pill",
            () -> new Pill(new Item.Properties().stacksTo(ITEM_STACK), 0, 0));

    public static final RegistryObject<Item> D1_ITEM = ITEMS.register("d1",
            () -> new ActiveItem(new Item.Properties().stacksTo(ITEM_STACK), 1, 0, 45 * 20, 1, 10 * 20));

    public static final RegistryObject<Item> D6_ITEM = ITEMS.register("d6",
            () -> new ActiveItem(new Item.Properties().stacksTo(ITEM_STACK), 2, 0, 45 * 20, 1, 10 * 20));

    public static final RegistryObject<Item> MUSTACHE_ITEM = ITEMS.register("mustache",
            () -> new PassiveItem(new Item.Properties().stacksTo(ITEM_STACK), 3, 0,
                    List.of(new Stat(StatType.LUCK_BY_ITEM, 40))
            ));

    public static final RegistryObject<Item> CHARGED_TNT_ITEM = ITEMS.register("charged_tnt",
            () -> new ActiveItem(new Item.Properties().stacksTo(ITEM_STACK), 4, 0, 30 * 20, 3, 5 * 20));

    public static final RegistryObject<Item> BATTERY_5V = ITEMS.register("battery_5v",
            () -> new PassiveItem(new Item.Properties().stacksTo(ITEM_STACK), 5, 0,
                    List.of(new Stat(StatType.ATTACK_SPEED_BY_ITEM, 3))
            ));

    public static final RegistryObject<Item> AGING = ITEMS.register("aging",
            () -> new PassiveItem(new Item.Properties().stacksTo(ITEM_STACK), 6, 0, List.of()));

    public static final RegistryObject<Item> RAPID_GROWTH = ITEMS.register("rapid_growth",
            () -> new PassiveItem(new Item.Properties().stacksTo(ITEM_STACK), 7, 0, List.of()));

    public static final RegistryObject<Item> AUTO_PORTION = ITEMS.register("auto_portion",
            () -> new PassiveItem(new Item.Properties().stacksTo(ITEM_STACK), 8, 0, List.of()));

    public static final RegistryObject<Item> COIN = ITEMS.register("coin",
            () -> new Coin(new Item.Properties().stacksTo(ITEM_STACK), 9, 0, 1));

    public static final RegistryObject<Item> COIN10 = ITEMS.register("coin10",
            () -> new Coin(new Item.Properties().stacksTo(ITEM_STACK), 10, 0, 10));

    public static final RegistryObject<Item> PRESCRIPTION = ITEMS.register("prescription",
            () -> new ActiveItem(new Item.Properties().stacksTo(ITEM_STACK), 11, 0, 50 * 20, 1, 10 * 20));

    public static final RegistryObject<Item> RADAR = ITEMS.register("radar",
            () -> new ActiveItem(new Item.Properties().stacksTo(ITEM_STACK), 12, 0, 50 * 20, 1, 10 * 20));

    public static final RegistryObject<Item> STAR = ITEMS.register("star",
            () -> new ActiveItem(new Item.Properties().stacksTo(ITEM_STACK), 13, 0, 300 * 20, 1, 0));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
