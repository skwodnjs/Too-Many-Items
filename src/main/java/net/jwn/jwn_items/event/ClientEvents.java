package net.jwn.jwn_items.event;

import net.jwn.jwn_items.Main;
import net.jwn.jwn_items.capability.MyStuffProvider;
import net.jwn.jwn_items.capability.PlayerOptionProvider;
import net.jwn.jwn_items.gui.MyStuffScreen;
import net.jwn.jwn_items.gui.StuffIFoundScreen;
import net.jwn.jwn_items.hud.StatHudOverLay;
import net.jwn.jwn_items.networking.ModMessages;
import net.jwn.jwn_items.networking.packet.*;
import net.jwn.jwn_items.util.Functions;
import net.jwn.jwn_items.util.ModSkills;
import net.jwn.jwn_items.util.KeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = Main.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            Player player = Minecraft.getInstance().player;
            if (player == null) return;

            if (KeyBindings.ACTIVE_SKILL_KEY.consumeClick()) {
                player.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
                    if (myStuff.getActiveSlots()[0].itemId != 0) {
                        ModSkills.useSkill(player, myStuff.getActiveSlots()[0].itemId, myStuff.getActiveSlots()[0].level);
                    }
                });
                Functions.printCoolTime(player);
                ModMessages.sendToServer(new PrintCoolTimeC2SPacket());
            } else if (KeyBindings.MY_STUFF_KEY.consumeClick()) {
                Minecraft.getInstance().setScreen(new MyStuffScreen());
                Functions.printInventory(player);
                ModMessages.sendToServer(new PrintStuffC2SPacket());
            } else if (KeyBindings.STATS_KEY.consumeClick()) {
                player.getCapability(PlayerOptionProvider.playerOptionsCapability).ifPresent(playerOptions -> {
                    playerOptions.statHudToggle();
                    ModMessages.sendToServer(new PlayerOptionSyncC2SPacket(playerOptions.getStatHudOption(), playerOptions.getStatHudDetailOptions()));
                });
                Functions.printStat(player);
                ModMessages.sendToServer(new PrintStatC2SPacket());
            } else if (KeyBindings.ACTIVE_CHANGE_KEY.consumeClick()) {
                player.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
                    myStuff.changeMainActiveItem();
                    ModMessages.sendToServer(new MyStuffSyncC2SPacket(myStuff.getActiveSlots(), myStuff.getPassiveSlots(), myStuff.isActiveUpgraded()));
                });
            } else if (KeyBindings.FOUND_STUFF_KEY.consumeClick()) {
                Minecraft.getInstance().setScreen(new StuffIFoundScreen());
                Functions.printFoundStuff(player);
                ModMessages.sendToServer(new PrintFoundStuffC2SPacket());
            }
        }
    }

    @Mod.EventBusSubscriber(modid = Main.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBindings.STATS_KEY);
            event.register(KeyBindings.ACTIVE_SKILL_KEY);
            event.register(KeyBindings.MY_STUFF_KEY);
            event.register(KeyBindings.ACTIVE_CHANGE_KEY);
            event.register(KeyBindings.FOUND_STUFF_KEY);
        }

        @SubscribeEvent
        public static void onRegisterGuiOverlaysEvent(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("stat_hud", StatHudOverLay.STAT_HUD);
//            event.registerAboveAll("active_item_hud", ActiveItemHubOverlay.ACTIVE_ITEM_HUD);
        }
    }
}
