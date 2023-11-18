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


    public static void register(IEventBus modEventBus) {
        BLOCK_ENTITIES.register(modEventBus);
    }
}
