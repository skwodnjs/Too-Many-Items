package net.jwn.jwn_items.item;

import net.jwn.jwn_items.Main;
import net.jwn.jwn_items.item.active.ModActiveItem;
import net.jwn.jwn_items.item.consumables.PillItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);

    public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test",
            () -> new TestItem(new Item.Properties()));

    public static final RegistryObject<Item> PILL_ITEM = ITEMS.register("pill",
            () -> new PillItem(new Item.Properties(), 0, 1));

    public static final RegistryObject<Item> D1_ITEM = ITEMS.register("d1",
            () -> new ModActiveItem(new Item.Properties(), 0, 2));

    public static final RegistryObject<Item> D6_ITEM = ITEMS.register("d6",
            () -> new ModActiveItem(new Item.Properties(), 0, 3));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
