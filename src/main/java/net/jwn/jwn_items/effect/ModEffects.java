package net.jwn.jwn_items.effect;

import net.jwn.jwn_items.Main;
import net.jwn.jwn_items.effect.custom.LavaWalkerEffect;
import net.jwn.jwn_items.effect.custom.StarEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Main.MOD_ID);

    public static final RegistryObject<MobEffect> STAR_EFFECT = MOB_EFFECTS.register("star_effect",
            () -> new StarEffect(MobEffectCategory.BENEFICIAL, 3124687));
    public static final RegistryObject<MobEffect> LAVA_WALKER_EFFECT = MOB_EFFECTS.register("lava_walker",
            () -> new LavaWalkerEffect(MobEffectCategory.BENEFICIAL, 3124687));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
