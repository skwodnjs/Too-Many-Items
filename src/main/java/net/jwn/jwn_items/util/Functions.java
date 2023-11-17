package net.jwn.jwn_items.util;

import net.jwn.jwn_items.capability.*;
import net.jwn.jwn_items.item.active.ActiveItem;
import net.jwn.jwn_items.item.ItemType;
import net.jwn.jwn_items.networking.ModMessages;
import net.jwn.jwn_items.networking.packet.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.atomic.AtomicReference;

import static net.jwn.jwn_items.capability.MyStuff.*;

public class Functions {
    // print capabilities
    public static void printStat(Player player) {
        player.getCapability(PlayerStatProvider.playerStatsCapability).ifPresent(playerStats -> {
            System.out.println("PRINT STAT");
            System.out.println((player.level().isClientSide) ? "CLIENT SIDE" : "SERVER SIDE");
            for (int i = 0; i < 14; i++) {
                System.out.printf("%f ; ", playerStats.getValue(i));
            }
            System.out.println();
            System.out.println("coin: " + playerStats.getValue(14));
        });
    }
    public static void printInventory(Player player) {
        System.out.println("--- MY STUFFS / " + ((player.level().isClientSide) ? "CLIENT SIDE ---" : "SERVER SIDE ---"));
        player.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
            System.out.println("--- ACTIVE ITEM ---");
            for (int i = 0; i < (myStuff.isActiveUpgraded() ? ACTIVE_MAX : ACTIVE_MAX_UPGRADE); i++) {
                System.out.printf("%d\t", myStuff.getActiveSlots()[i].itemId);
            }
            System.out.println();
            System.out.println("get ID of main active item: " + myStuff.getActiveSlots()[0].itemId);

            System.out.println("--- PASSIVE ITEM ---");
            for (int i = 0; i < PASSIVE_MAX / 3; i++) {
                System.out.printf("%d\t", myStuff.getPassiveSlots()[i].itemId);
            }
            System.out.println();
            for (int i = PASSIVE_MAX / 3; i < PASSIVE_MAX * 2 / 3; i++) {
                System.out.printf("%d\t", myStuff.getPassiveSlots()[i].itemId);
            }
            System.out.println();
            for (int i = PASSIVE_MAX * 2 / 3; i < PASSIVE_MAX; i++) {
                System.out.printf("%d\t", myStuff.getPassiveSlots()[i].itemId);
            }
            System.out.println();
            System.out.println("get last slot of passive item: " + myStuff.getEmptySlot(ItemType.PASSIVE));
        });
    }
    public static void printCoolTime(Player player) {
        System.out.println("COOL TIME");
        System.out.println((player.level().isClientSide) ? "CLIENT" : "SERVER");
        player.getCapability(CoolTimeProvider.coolTimeCapability).ifPresent(coolTime -> {
            System.out.println(coolTime.get() + " tick");
            System.out.println((coolTime.get() / 20) + " sec");
        });
    }
    public static void printFoundStuff(Player player) {
        System.out.println("FOUND STUFF");
        System.out.println((player.level().isClientSide) ? "CLIENT" : "SERVER");
        player.getCapability(FoundStuffProvider.foundStuffCapability).ifPresent(foundStuff -> {
            for (int i = 0; i < foundStuff.get().length; i++) {
                System.out.printf("%d ", foundStuff.get()[i]);
                if (i % 10 == 0 && i > 1) System.out.println();
            }
        });
    }

    // sync
    public static void syncCoolTimeS2C(ServerPlayer player) {
        player.getCapability(CoolTimeProvider.coolTimeCapability).ifPresent(coolTime -> {
            ModMessages.sendToPlayer(new CoolTimeSyncS2CPacket(coolTime.get()), player);
        });
    }
    public static void syncCoolTimeC2S(Player player) {
        player.getCapability(CoolTimeProvider.coolTimeCapability).ifPresent(coolTime -> {
            ModMessages.sendToServer(new CoolTimeSyncC2SPacket(coolTime.get()));
        });
    }

    public static void syncFoundStuffS2C(ServerPlayer player) {
        player.getCapability(FoundStuffProvider.foundStuffCapability).ifPresent(foundStuff -> {
            ModMessages.sendToPlayer(new FoundStuffSyncS2CPacket(foundStuff.get()), player);
        });
    }
    public static void syncFoundStuffC2S(Player player) {
        player.getCapability(FoundStuffProvider.foundStuffCapability).ifPresent(foundStuff -> {
            ModMessages.sendToServer(new FoundStuffSyncC2SPacket(foundStuff.get()));
        });
    }

    public static void syncMyStuffS2C(ServerPlayer player) {
        player.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
            ModMessages.sendToPlayer(new MyStuffSyncS2CPacket(myStuff.getActiveSlots(), myStuff.getPassiveSlots(), myStuff.isActiveUpgraded()), player);
        });
    }
    public static void syncMyStuffC2S(Player player) {
        player.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
            ModMessages.sendToServer(new MyStuffSyncC2SPacket(myStuff.getActiveSlots(), myStuff.getPassiveSlots(), myStuff.isActiveUpgraded()));
        });
    }

    public static void syncPlayerOptionS2C(ServerPlayer player) {
        player.getCapability(PlayerOptionProvider.playerOptionsCapability).ifPresent(playerOptions -> {
            ModMessages.sendToPlayer(new PlayerOptionSyncS2CPacket(playerOptions.getStatHudOption(), playerOptions.getStatHudDetailOptions()), player);
        });
    }
    public static void syncPlayerOptionC2S(Player player) {
        player.getCapability(PlayerOptionProvider.playerOptionsCapability).ifPresent(playerOptions -> {
            ModMessages.sendToServer(new PlayerOptionSyncC2SPacket(playerOptions.getStatHudOption(), playerOptions.getStatHudDetailOptions()));
        });
    }

    public static void syncPlayerStatS2C(ServerPlayer player) {
        player.getCapability(PlayerStatProvider.playerStatsCapability).ifPresent(playerStat -> {
            ModMessages.sendToPlayer(new PlayerStatsSyncS2CPacket(playerStat.get()), player);
        });
    }
    public static void syncPlayerStatC2S(Player player) {
        player.getCapability(PlayerStatProvider.playerStatsCapability).ifPresent(playerStat -> {
            ModMessages.sendToServer(new PlayerStatsSyncC2SPacket(playerStat.get()));
        });
    }

    // screen
    public static int getChargedStack(int coolTime, ActiveItem activeItem, int level) {
        int maxValue = activeItem.getMaxStack() * activeItem.getCoolTime(level);
        int usableTime = maxValue - coolTime;
        return usableTime / activeItem.getCoolTime(level);
    }
    public static int getWaitingTime(int coolTime, ActiveItem activeItem, int level) {
        int leftSec = coolTime % activeItem.getCoolTime(level) / 20 + 1;
        if (leftSec == 1 && getChargedStack(coolTime, activeItem, level) == activeItem.getMaxStack()) {
            return 0;
        }
        return leftSec;
    }

    public static float addSingleStat(Player player, Stat stat) {
        AtomicReference<Float> toReturn = new AtomicReference<>(0f);
        player.getCapability(PlayerStatProvider.playerStatsCapability).ifPresent(playerStats -> {
            float appliedValue = playerStats.add(player, stat);
            toReturn.set(appliedValue);
        });
        return toReturn.get();
    }
}
