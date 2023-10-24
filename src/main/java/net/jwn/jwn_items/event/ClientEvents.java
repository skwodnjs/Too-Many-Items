package net.jwn.jwn_items.event;

import net.jwn.jwn_items.Main;
import net.jwn.jwn_items.stat.PlayerStatsProvider;
import net.jwn.jwn_items.stat.StatType;
import net.jwn.jwn_items.util.KeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = Main.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (KeyBindings.ONE_KEY.consumeClick()) {
                Minecraft.getInstance().player.getCapability(PlayerStatsProvider.playerStatsCapability).ifPresent(playerStats -> {
                    for (int i = 0; i < 15; i++) {
                        System.out.println(playerStats.getValue(StatType.getStatTypeById(i)));
                    }
                });
            } else if (KeyBindings.TWO_KEY.consumeClick()) {

            } else if (KeyBindings.THREE_KEY.consumeClick()) {

            }
        }
    }

    @Mod.EventBusSubscriber(modid = Main.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBindings.TWO_KEY);
            event.register(KeyBindings.ONE_KEY);
            event.register(KeyBindings.THREE_KEY);
        }
    }
}
