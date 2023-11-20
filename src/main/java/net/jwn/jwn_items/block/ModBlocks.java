package net.jwn.jwn_items.block;

import net.jwn.jwn_items.Main;
import net.jwn.jwn_items.block.custom.*;
import net.jwn.jwn_items.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static net.jwn.jwn_items.item.ModItems.ITEM_STACK;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MOD_ID);

    public static final RegistryObject<Block> TEST_FLOORING = registerBlock("test_flooring",
            () -> new FlooringBlock(BlockBehaviour.Properties.of().noOcclusion().lightLevel(value -> 15)));

    public static final RegistryObject<Block> POOP_BLOCK = registerBlock("poop",
            () -> new PoopBlock(BlockBehaviour.Properties.of().noOcclusion()));

    public static final RegistryObject<Block> COLD_LAVA = registerBlock("cold_lava",
            () -> new ColdLavaBlock(BlockBehaviour.Properties.of().noOcclusion()));

    public static final RegistryObject<Block> SHOP_COMMON = registerBlock("shop_common",
            () -> new ShopCommonBlock(BlockBehaviour.Properties.of().noOcclusion()));

    public static final RegistryObject<Block> SHOP_RARE = registerBlock("shop_rare",
            () -> new ShopRareBlock(BlockBehaviour.Properties.of().noOcclusion()));

    public static final RegistryObject<Block> ROULETTE_COMMON = registerBlock("roulette_common",
            () -> new RouletteCommonBlock(BlockBehaviour.Properties.of().noOcclusion()));

    public static final RegistryObject<Block> ROULETTE_RARE = registerBlock("roulette_rare",
            () -> new RouletteRareBlock(BlockBehaviour.Properties.of().noOcclusion()));

    public static final RegistryObject<Block> SYNTHESIS_COMMON = registerBlock("synthesis_common",
            () -> new SynthesisCommonBlock(BlockBehaviour.Properties.of().noOcclusion()));

    public static final RegistryObject<Block> SYNTHESIS_RARE = registerBlock("synthesis_rare",
            () -> new SynthesisRareBlock(BlockBehaviour.Properties.of().noOcclusion()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().stacksTo(ITEM_STACK)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
