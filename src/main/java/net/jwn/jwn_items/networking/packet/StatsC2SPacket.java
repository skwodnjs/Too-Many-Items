package net.jwn.jwn_items.networking.packet;

import net.jwn.jwn_items.capability.CoolTimeProvider;
import net.jwn.jwn_items.capability.PlayerStatsProvider;
import net.jwn.jwn_items.item.ItemType;
import net.jwn.jwn_items.item.ModItem;
import net.jwn.jwn_items.item.ModItems;
import net.jwn.jwn_items.item.ModItems.ModItemsProvider;
import net.jwn.jwn_items.stat.StatType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Supplier;

public class StatsC2SPacket {
    public static final int ID = ((ModItem) ModItems.D1_ITEM.get()).getItemID();

    public StatsC2SPacket() {
    }
    public StatsC2SPacket(FriendlyByteBuf buf) {
    }
    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();

            System.out.println("--- STATS / SERVER SIDE ---");
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
        });
        return true;
    }
}
