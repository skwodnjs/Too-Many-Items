package net.jwn.jwn_items.item;

import net.jwn.jwn_items.Main;
import net.jwn.jwn_items.item.consumables.PillItem;
import net.jwn.jwn_items.item.consumables.TestItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);

    public static final RegistryObject<Item> PILL_ITEM = ITEMS.register("pill",
            () -> new PillItem(new Item.Properties(), ItemType.CONSUMABLES));

    public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test",
            () -> new TestItem(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
