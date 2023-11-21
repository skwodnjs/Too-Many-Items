package net.jwn.jwn_items.block.blockentity;

import net.jwn.jwn_items.Main;
import net.jwn.jwn_items.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Main.MOD_ID);

    public static final RegistryObject<BlockEntityType<ColdLavaBlockEntity>> COLD_LAVA_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("cold_lava",
                    () -> BlockEntityType.Builder.of(ColdLavaBlockEntity::new, ModBlocks.COLD_LAVA.get())
                            .build(null));
    public static final RegistryObject<BlockEntityType<SynthesisCommonBlockEntity>> SYNTHESIS_COMMON_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("synthesis_common",
                    () -> BlockEntityType.Builder.of(SynthesisCommonBlockEntity::new, ModBlocks.SYNTHESIS_COMMON.get())
                            .build(null));
    public static final RegistryObject<BlockEntityType<SynthesisRareBlockEntity>> SYNTHESIS_RARE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("synthesis_rare",
                    () -> BlockEntityType.Builder.of(SynthesisRareBlockEntity::new, ModBlocks.SYNTHESIS_RARE.get())
                            .build(null));
    public static final RegistryObject<BlockEntityType<ShopCommonBlockEntity>> SHOP_COMMON_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("shop_common",
                    () -> BlockEntityType.Builder.of(ShopCommonBlockEntity::new, ModBlocks.SHOP_COMMON.get())
                            .build(null));
    public static final RegistryObject<BlockEntityType<ShopRareBlockEntity>> SHOP_RARE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("shop_rare",
                    () -> BlockEntityType.Builder.of(ShopRareBlockEntity::new, ModBlocks.SHOP_RARE.get())
                            .build(null));
    public static final RegistryObject<BlockEntityType<RouletteCommonBlockEntity>> ROULETTE_COMMON_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("roulette_common",
                    () -> BlockEntityType.Builder.of(RouletteCommonBlockEntity::new, ModBlocks.ROULETTE_COMMON.get())
                            .build(null));
    public static final RegistryObject<BlockEntityType<RouletteRareBlockEntity>> ROULETTE_RARE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("roulette_rare",
                    () -> BlockEntityType.Builder.of(RouletteRareBlockEntity::new, ModBlocks.ROULETTE_RARE.get())
                            .build(null));

    public static void register(IEventBus modEventBus) {
        BLOCK_ENTITIES.register(modEventBus);
    }
}
