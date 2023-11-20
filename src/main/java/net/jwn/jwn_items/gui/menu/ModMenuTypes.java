package net.jwn.jwn_items.gui.menu;

import net.jwn.jwn_items.Main;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, Main.MOD_ID);

    public static final RegistryObject<MenuType<SynthesisCommonScreenMenu>> SYNTHESIS_COMMON_MENU =
            registerMenuType("synthesis_common_menu", SynthesisCommonScreenMenu::new);
    public static final RegistryObject<MenuType<SynthesisRareScreenMenu>> SYNTHESIS_RARE_MENU =
            registerMenuType("synthesis_rare_menu", SynthesisRareScreenMenu::new);
    public static final RegistryObject<MenuType<ShopCommonScreenMenu>> SHOP_COMMON_MENU =
            registerMenuType("shop_common_menu", ShopCommonScreenMenu::new);
    public static final RegistryObject<MenuType<ShopRareScreenMenu>> SHOP_RARE_MENU =
            registerMenuType("shop_rare_menu", ShopRareScreenMenu::new);
    public static final RegistryObject<MenuType<RouletteCommonScreenMenu>> ROULETTE_COMMON_MENU =
            registerMenuType("roulette_common_menu", RouletteCommonScreenMenu::new);
    public static final RegistryObject<MenuType<RouletteRareScreenMenu>> ROULETTE_RARE_MENU =
            registerMenuType("roulette_rare_menu", RouletteRareScreenMenu::new);

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
