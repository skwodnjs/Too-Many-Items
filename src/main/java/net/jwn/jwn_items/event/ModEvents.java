package net.jwn.jwn_items.event;

import net.jwn.jwn_items.Main;
import net.jwn.jwn_items.capability.*;
import net.jwn.jwn_items.event.custom.ModItemUsedSuccessfullyEvent;
import net.jwn.jwn_items.event.custom.PlayerStatsChangedEvent;
import net.jwn.jwn_items.item.ItemType;
import net.jwn.jwn_items.item.ModItem;
import net.jwn.jwn_items.item.ModItems;
import net.jwn.jwn_items.item.passive.Aging;
import net.jwn.jwn_items.item.passive.Battery5V;
import net.jwn.jwn_items.item.passive.Mustache;
import net.jwn.jwn_items.item.passive.RapidGrowth;
import net.jwn.jwn_items.util.StatType;
import net.jwn.jwn_items.util.Functions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Mod.EventBusSubscriber(modid = Main.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (event.phase == TickEvent.Phase.END) {
            player.getCapability(CoolTimeProvider.coolTimeCapability).ifPresent(CoolTime::sub);
            if (event.side == LogicalSide.SERVER) {
                player.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
                    // logic for passive item
                    List<Integer> passiveId = Arrays.stream(myStuff.getPassiveSlots())
                            .map(modSlot -> modSlot.itemId).toList();
                    if (passiveId.contains(((ModItem) ModItems.MUSTACHE_ITEM.get()).id)) {
                        int level = myStuff.getLevelById(((ModItem) ModItems.MUSTACHE_ITEM.get()).id);
                        if (Math.random() < 0.01 + level * 0.01) Mustache.operateServer(player);
                    }
                    if (passiveId.contains(((ModItem) ModItems.BATTERY_5V.get()).id)) {
                        int level = myStuff.getLevelById(((ModItem) ModItems.BATTERY_5V.get()).id);
                        if (Math.random() < 0.01 + level * 0.01) Battery5V.operateServer(player);
                    }
                    if (passiveId.contains(((ModItem) ModItems.AGING.get()).id)) {
                        int level = myStuff.getLevelById(((ModItem) ModItems.AGING.get()).id);
                        if (Math.random() < 0.01 + level * 0.01) Aging.operateServer(player);
                    }
                    if (passiveId.contains(((ModItem) ModItems.RAPID_GROWTH.get()).id)) {
                        int level = myStuff.getLevelById(((ModItem) ModItems.RAPID_GROWTH.get()).id);
                        if (Math.random() < 0.5 + level * 0.01) RapidGrowth.operateServer(player);
                    }
                });
            }
            // else {} for client side
        }
    }

    @SubscribeEvent
    public static void onModItemUsedSuccessfullyEvent(ModItemUsedSuccessfullyEvent event) {
        // 도감에 등록
        Player player = event.player;
        player.getCapability(FoundStuffProvider.foundStuffCapability).ifPresent(foundStuff -> {
            if (event.itemType == ItemType.CONSUMABLES) foundStuff.set(event.id, 1);
            player.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
                int myStuffLevel = myStuff.getLevelById(event.id);
                if (foundStuff.get()[event.id] < myStuffLevel) {
                    foundStuff.set(event.id, myStuffLevel);
                }
            });
        });
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(PlayerStatProvider.playerStatsCapability).isPresent()) {
                event.addCapability(new ResourceLocation(Main.MOD_ID, "player_stats"), new PlayerStatProvider());
            }
            if (!event.getObject().getCapability(MyStuffProvider.myStuffCapability).isPresent()) {
                event.addCapability(new ResourceLocation(Main.MOD_ID, "my_stuff"), new MyStuffProvider());
            }
            if (!event.getObject().getCapability(CoolTimeProvider.coolTimeCapability).isPresent()) {
                event.addCapability(new ResourceLocation(Main.MOD_ID, "cool_time"), new CoolTimeProvider());
            }
            if (!event.getObject().getCapability(PlayerStatProvider.playerStatsCapability).isPresent()) {
                event.addCapability(new ResourceLocation(Main.MOD_ID, "player_options"), new PlayerOptionProvider());
            }
            if (!event.getObject().getCapability(FoundStuffProvider.foundStuffCapability).isPresent()) {
                event.addCapability(new ResourceLocation(Main.MOD_ID, "found_stuff"), new FoundStuffProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerStatsChangedEvent(PlayerStatsChangedEvent event) {
        // 스텟 실제로 적용
        if (!event.player.level().isClientSide) {
            event.player.getCapability(PlayerStatProvider.playerStatsCapability).ifPresent(playerStats -> {
                float healthValue = playerStats.getValue(0) + playerStats.getValue(7);
                float damageValue = playerStats.getValue(1) + playerStats.getValue(8);
                float attackSpeedValue = playerStats.getValue(2) + playerStats.getValue(9);
                float attackRangeValue = playerStats.getValue(3) + playerStats.getValue(10);
                float movementSpeedValue = playerStats.getValue(5) + playerStats.getValue(12);

                // HEALTH
                float healthCorrectionValue = 20.0f + 2 * Math.min(healthValue, PlayerStat.MAX_HEALTH);
                event.player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(healthCorrectionValue);
                event.player.setHealth(event.player.getMaxHealth());

                // DAMAGE
                float damageCorrectionValue = 2.0f + 4.0f / 60.0f * Math.min(damageValue, PlayerStat.MAX_STAT);
                event.player.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(damageCorrectionValue);

                // MOVEMENT SPEED
                float movementSpeedCorrectionValue = 0.1f + 0.06f / 60 * Math.min(movementSpeedValue, PlayerStat.MAX_STAT);
                event.player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(movementSpeedCorrectionValue);

                // ATTACK RANGE
                float attackRangeCorrectionValue = 3 + 2 / 60.0f * Math.min(attackRangeValue, PlayerStat.MAX_STAT);
                event.player.getAttribute(ForgeMod.ENTITY_REACH.get()).setBaseValue(attackRangeCorrectionValue);

                // ATTACK SPEED
                float attackSpeedCorrectionValue = 4.0f + 0.8f / 60.0f * Math.min(attackSpeedValue, PlayerStat.MAX_STAT);
                event.player.getAttribute(Attributes.ATTACK_SPEED).setBaseValue(attackSpeedCorrectionValue);
            });
        }
    }

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        // mining speed 보정
        double miningSpeedCorrectionValue;
        AtomicReference<Double> miningSpeedAtomicValue = new AtomicReference<>(0.0);
        event.getEntity().getCapability(PlayerStatProvider.playerStatsCapability).ifPresent(playerStats -> {
            miningSpeedAtomicValue.updateAndGet(stat -> stat + playerStats.getValue(StatType.MINING_SPEED_BY_ITEM) + playerStats.getValue(StatType.MINING_SPEED_BY_CONSUMABLES));
        });
        miningSpeedCorrectionValue = 1.0 + 0.4 / 60.0 * miningSpeedAtomicValue.get();
        event.setNewSpeed(event.getOriginalSpeed() * (float) miningSpeedCorrectionValue);
    }

    @SubscribeEvent
    public static void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        // sync capabilities, server to client
        if (!event.getEntity().level().isClientSide) {
            Player player = event.getEntity();
            Functions.syncPlayerStatS2C((ServerPlayer) player);
            MinecraftForge.EVENT_BUS.post(new PlayerStatsChangedEvent(player));
            Functions.syncMyStuffS2C((ServerPlayer) player);
            Functions.syncFoundStuffS2C((ServerPlayer) player);
            Functions.syncPlayerOptionS2C((ServerPlayer) player);
            Functions.syncCoolTimeS2C((ServerPlayer) player);
        }
    }

    @SubscribeEvent
    public static void onClone(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().reviveCaps();
            // Player Stat
            event.getOriginal().getCapability(PlayerStatProvider.playerStatsCapability).ifPresent(oldStore -> {
                event.getEntity().getCapability(PlayerStatProvider.playerStatsCapability).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            MinecraftForge.EVENT_BUS.post(new PlayerStatsChangedEvent(event.getEntity()));
            // My Stuff
            event.getOriginal().getCapability(MyStuffProvider.myStuffCapability).ifPresent(oldStore -> {
                event.getEntity().getCapability(MyStuffProvider.myStuffCapability).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            // Player Option
            event.getOriginal().getCapability(PlayerOptionProvider.playerOptionsCapability).ifPresent(oldStore -> {
                event.getEntity().getCapability(PlayerOptionProvider.playerOptionsCapability).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            // Cool Time
            event.getOriginal().getCapability(CoolTimeProvider.coolTimeCapability).ifPresent(oldStore -> {
                event.getEntity().getCapability(CoolTimeProvider.coolTimeCapability).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            // Stuff I Found
            event.getOriginal().getCapability(FoundStuffProvider.foundStuffCapability).ifPresent(oldStore -> {
                event.getEntity().getCapability(FoundStuffProvider.foundStuffCapability).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            event.getOriginal().invalidateCaps();
        }
    }
}
