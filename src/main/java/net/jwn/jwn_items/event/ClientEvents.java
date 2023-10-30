package net.jwn.jwn_items.event;

import net.jwn.jwn_items.Main;
import net.jwn.jwn_items.capability.MyStuffProvider;
import net.jwn.jwn_items.capability.PlayerOptions;
import net.jwn.jwn_items.capability.PlayerOptionsProvider;
import net.jwn.jwn_items.gui.MyStuffScreen;
import net.jwn.jwn_items.hud.StatHudOverLay;
import net.jwn.jwn_items.item.ItemType;
import net.jwn.jwn_items.item.ModItems;
import net.jwn.jwn_items.networking.ModMessages;
import net.jwn.jwn_items.networking.packet.ChangeMainActiveItemC2SPacket;
import net.jwn.jwn_items.networking.packet.OptionSyncC2SPacket;
import net.jwn.jwn_items.networking.packet.StatsC2SPacket;
import net.jwn.jwn_items.networking.packet.StuffC2SPacket;
import net.jwn.jwn_items.skill.ModSkills;
import net.jwn.jwn_items.capability.PlayerStatsProvider;
import net.jwn.jwn_items.stat.StatType;
import net.jwn.jwn_items.util.KeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.jwn.jwn_items.util.Options.*;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = Main.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            Player player = Minecraft.getInstance().player;
            if (KeyBindings.ACTIVE_SKILL_KEY.consumeClick()) {
                player.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
                    ModSkills.useSkill(player, myStuff.getIDOfMainActiveItem());
                });
            } else if (KeyBindings.MY_STUFF_KEY.consumeClick()) {
                Minecraft.getInstance().setScreen(new MyStuffScreen());
                System.out.println("--- MY STUFFS /CLIENT SIDE ---");
                player.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
                    System.out.println("--- ACTIVE ITEM ---");
                    for (int i = 0; i < (myStuff.getActiveLimit() ? ACTIVE_MAX : ACTIVE_MAX_UPGRADE); i++) {
                        System.out.printf("%d\t", myStuff.getMyStuffForActive()[i]);
                    }
                    System.out.println();
                    System.out.println("get ID of main active item: " + myStuff.getIDOfMainActiveItem());

                    System.out.println("--- PASSIVE ITEM ---");
                    for (int i = 0; i < PASSIVE_MAX / 3; i++) {
                        System.out.printf("%d\t", myStuff.getMyStuffForPassive()[i]);
                    }
                    System.out.println();
                    for (int i = PASSIVE_MAX / 3; i < PASSIVE_MAX * 2 / 3; i++) {
                        System.out.printf("%d\t", myStuff.getMyStuffForPassive()[i]);
                    }
                    System.out.println();
                    for (int i = PASSIVE_MAX * 2 / 3; i < PASSIVE_MAX; i++) {
                        System.out.printf("%d\t", myStuff.getMyStuffForPassive()[i]);
                    }
                    System.out.println();
                    System.out.println("get last slot of passive item: " + myStuff.getLastEmptySlot(ItemType.PASSIVE));
                });
                ModMessages.sendToServer(new StuffC2SPacket());
            } else if (KeyBindings.STATS_KEY.consumeClick()) {
                player.getCapability(PlayerOptionsProvider.playerOptionsCapability).ifPresent(playerOptions -> {
                    playerOptions.statHudToggle();
                    ModMessages.sendToServer(new OptionSyncC2SPacket(playerOptions.getStatHudOption(), playerOptions.getStatHudDetailOptions()));
                });
                System.out.println("--- STATS / CLIENT SIDE ---");
                player.getCapability(PlayerStatsProvider.playerStatsCapability).ifPresent(playerStats -> {
                    System.out.println("HEALTH BY CONSUMABLES: " + playerStats.getValue(StatType.HEALTH_BY_CONSUMABLES));
                    System.out.println("DAMAGE BY CONSUMABLES: " + playerStats.getValue(StatType.DAMAGE_BY_CONSUMABLES));
                    System.out.println("ATTACK SPEED BY CONSUMABLES: " + playerStats.getValue(StatType.ATTACK_SPEED_BY_CONSUMABLES));
                    System.out.println("ATTACK RANGE BY CONSUMABLES: " + playerStats.getValue(StatType.ATTACK_RANGE_BY_CONSUMABLES));
                    System.out.println("MINING SPEED BY CONSUMABLES: " + playerStats.getValue(StatType.MINING_SPEED_BY_CONSUMABLES));
                    System.out.println("MOVEMENT SPEED BY CONSUMABLES: " + playerStats.getValue(StatType.MOVEMENT_SPEED_BY_CONSUMABLES));
                    System.out.println("LUCK BY CONSUMABLES: " + playerStats.getValue(StatType.LUCK_BY_CONSUMABLES));
                    System.out.println("HEALTH BY ITEM: " + playerStats.getValue(StatType.HEALTH_BY_ITEM));
                    System.out.println("DAMAGE BY ITEM: " + playerStats.getValue(StatType.DAMAGE_BY_ITEM));
                    System.out.println("ATTACK SPEED BY ITEM: " + playerStats.getValue(StatType.ATTACK_SPEED_BY_ITEM));
                    System.out.println("ATTACK RANGE BY ITEM: " + playerStats.getValue(StatType.ATTACK_RANGE_BY_ITEM));
                    System.out.println("MINING SPEED BY ITEM: " + playerStats.getValue(StatType.MINING_SPEED_BY_ITEM));
                    System.out.println("MOVEMENT SPEED BY ITEM: " + playerStats.getValue(StatType.MOVEMENT_SPEED_BY_ITEM));
                    System.out.println("LUCK BY ITEM: " + playerStats.getValue(StatType.LUCK_BY_ITEM));
                    System.out.println("COIN: " + playerStats.getValue(StatType.COIN));
                });
                ModMessages.sendToServer(new StatsC2SPacket());
            } else if (KeyBindings.ACTIVE_CHANGE_KEY.consumeClick()) {
                player.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
                    myStuff.changeMainActiveItem();
                });
                ModMessages.sendToServer(new ChangeMainActiveItemC2SPacket());
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
        }

        @SubscribeEvent
        public static void onRegisterGuiOverlaysEvent(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("stat_hud", StatHudOverLay.STAT_HUD);
        }
    }
}
