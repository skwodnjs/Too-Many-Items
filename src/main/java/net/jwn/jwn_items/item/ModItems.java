package net.jwn.jwn_items.item;

import net.jwn.jwn_items.Main;
import net.jwn.jwn_items.item.active.ChargedTNT;
import net.jwn.jwn_items.item.active.D1;
import net.jwn.jwn_items.item.active.D6;
import net.jwn.jwn_items.item.consumables.Pill;
import net.jwn.jwn_items.item.passive.Aging;
import net.jwn.jwn_items.item.passive.Battery5V;
import net.jwn.jwn_items.item.passive.Mustache;
import net.jwn.jwn_items.item.passive.RapidGrowth;
import net.jwn.jwn_items.util.Stat;
import net.jwn.jwn_items.util.StatType;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);
    private static final int ITEM_STACK = 64;

    public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test",
            () -> new TestItem(new Item.Properties().stacksTo(ITEM_STACK)));

    public static final RegistryObject<Item> PILL_ITEM = ITEMS.register("pill",
            () -> new Pill(new Item.Properties().stacksTo(ITEM_STACK), 0, 0));

    public static final RegistryObject<Item> D1_ITEM = ITEMS.register("d1",
            () -> new D1(new Item.Properties().stacksTo(ITEM_STACK), 1, 0, 50, 1, 10));

    public static final RegistryObject<Item> D6_ITEM = ITEMS.register("d6",
            () -> new D6(new Item.Properties().stacksTo(ITEM_STACK), 2, 0, 50, 1, 10));

    public static final RegistryObject<Item> MUSTACHE_ITEM = ITEMS.register("mustache",
            () -> new Mustache(new Item.Properties().stacksTo(ITEM_STACK), 3, 0,
                    List.of(new Stat(StatType.LUCK_BY_ITEM, 3))
            ));

    public static final RegistryObject<Item> CHARGED_TNT_ITEM = ITEMS.register("charged_tnt",
            () -> new ChargedTNT(new Item.Properties().stacksTo(ITEM_STACK), 4, 0, 30, 3, 5));

    public static final RegistryObject<Item> BATTERY_5V = ITEMS.register("battery_5v",
            () -> new Battery5V(new Item.Properties().stacksTo(ITEM_STACK), 5, 0, List.of()));

    public static final RegistryObject<Item> AGING = ITEMS.register("aging",
            () -> new Aging(new Item.Properties().stacksTo(ITEM_STACK), 6, 0, List.of()));

    public static final RegistryObject<Item> RAPID_GROWTH = ITEMS.register("rapid_growth",
            () -> new RapidGrowth(new Item.Properties().stacksTo(ITEM_STACK), 7, 0, List.of()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
