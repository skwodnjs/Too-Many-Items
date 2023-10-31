package net.jwn.jwn_items.event;

import net.jwn.jwn_items.Main;
import net.jwn.jwn_items.capability.*;
import net.jwn.jwn_items.event.custom.PlayerStatsChangedEvent;
import net.jwn.jwn_items.item.passive.Mustache;
import net.jwn.jwn_items.networking.ModMessages;
import net.jwn.jwn_items.networking.packet.MyStuffSyncS2CPacket;
import net.jwn.jwn_items.networking.packet.OptionSyncS2CPacket;
import net.jwn.jwn_items.networking.packet.StatSyncS2CPacket;
import net.jwn.jwn_items.stat.StatType;
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

import java.util.concurrent.atomic.AtomicReference;

@Mod.EventBusSubscriber(modid = Main.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER) {
            // cool time
            event.player.getCapability(CoolTimeProvider.coolTimeCapability).ifPresent(CoolTime::sub);

            // mustache
            Mustache.operate(event.player);

            //
        } else {

        }

        // both side

    }

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(PlayerStatsProvider.playerStatsCapability).isPresent()) {
                event.addCapability(new ResourceLocation(Main.MOD_ID, "player_stats"), new PlayerStatsProvider());
            }
            if (!event.getObject().getCapability(MyStuffProvider.myStuffCapability).isPresent()) {
                event.addCapability(new ResourceLocation(Main.MOD_ID, "my_stuff"), new MyStuffProvider());
            }
            if (!event.getObject().getCapability(CoolTimeProvider.coolTimeCapability).isPresent()) {
                event.addCapability(new ResourceLocation(Main.MOD_ID, "cool_time"), new CoolTimeProvider());
            }
            if (!event.getObject().getCapability(PlayerStatsProvider.playerStatsCapability).isPresent()) {
                event.addCapability(new ResourceLocation(Main.MOD_ID, "player_options"), new PlayerOptionsProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerStatsChangedEvent(PlayerStatsChangedEvent event) {
        if (!event.player.level().isClientSide) {
            AtomicReference<Float> healthByConsumableValue = new AtomicReference<>(0.0f);
            AtomicReference<Float> damageByConsumableValue = new AtomicReference<>(0.0f);
            AtomicReference<Float> attackSpeedByConsumableValue = new AtomicReference<>(0.0f);
            AtomicReference<Float> attackRangeByConsumableValue = new AtomicReference<>(0.0f);
            AtomicReference<Float> movementSpeedByConsumableValue = new AtomicReference<>(0.0f);
            AtomicReference<Float> miningSpeedByConsumableValue = new AtomicReference<>(0.0f);
            AtomicReference<Float> luckByConsumableValue = new AtomicReference<>(0.0f);

            AtomicReference<Float> healthByItemValue = new AtomicReference<>(0.0f);
            AtomicReference<Float> damageByItemValue = new AtomicReference<>(0.0f);
            AtomicReference<Float> attackSpeedByItemValue = new AtomicReference<>(0.0f);
            AtomicReference<Float> attackRangeByItemValue = new AtomicReference<>(0.0f);
            AtomicReference<Float> movementSpeedByItemValue = new AtomicReference<>(0.0f);
            AtomicReference<Float> miningSpeedByItemValue = new AtomicReference<>(0.0f);
            AtomicReference<Float> luckByItemValue = new AtomicReference<>(0.0f);

            AtomicReference<Float> coinValue = new AtomicReference<>(0.0f);

            event.player.getCapability(PlayerStatsProvider.playerStatsCapability).ifPresent(playerStats -> {
                healthByConsumableValue.updateAndGet(stat -> stat + playerStats.getValue(StatType.HEALTH_BY_CONSUMABLES));
                damageByConsumableValue.updateAndGet(stat -> stat + playerStats.getValue(StatType.DAMAGE_BY_CONSUMABLES));
                attackSpeedByConsumableValue.updateAndGet(stat -> stat + playerStats.getValue(StatType.ATTACK_SPEED_BY_CONSUMABLES));
                attackRangeByConsumableValue.updateAndGet(stat -> stat + playerStats.getValue(StatType.ATTACK_RANGE_BY_CONSUMABLES));
                movementSpeedByConsumableValue.updateAndGet(stat -> stat + playerStats.getValue(StatType.MOVEMENT_SPEED_BY_CONSUMABLES));
                miningSpeedByConsumableValue.updateAndGet(stat -> stat + playerStats.getValue(StatType.MINING_SPEED_BY_CONSUMABLES));
                luckByConsumableValue.updateAndGet(stat -> stat + playerStats.getValue(StatType.LUCK_BY_CONSUMABLES));

                healthByItemValue.updateAndGet(stat -> stat + playerStats.getValue(StatType.HEALTH_BY_ITEM));
                damageByItemValue.updateAndGet(stat -> stat + playerStats.getValue(StatType.DAMAGE_BY_ITEM));
                attackSpeedByItemValue.updateAndGet(stat -> stat + playerStats.getValue(StatType.ATTACK_SPEED_BY_ITEM));
                attackRangeByItemValue.updateAndGet(stat -> stat + playerStats.getValue(StatType.ATTACK_RANGE_BY_ITEM));
                movementSpeedByItemValue.updateAndGet(stat -> stat + playerStats.getValue(StatType.MOVEMENT_SPEED_BY_ITEM));
                miningSpeedByItemValue.updateAndGet(stat -> stat + playerStats.getValue(StatType.MINING_SPEED_BY_ITEM));
                luckByItemValue.updateAndGet(stat -> stat + playerStats.getValue(StatType.LUCK_BY_ITEM));

                coinValue.updateAndGet(stat -> stat + playerStats.getValue(StatType.COIN));
            });

            float healthValue = healthByConsumableValue.get() + healthByItemValue.get();
            float damageValue = damageByConsumableValue.get() + damageByItemValue.get();
            float movementSpeedValue = movementSpeedByConsumableValue.get() + movementSpeedByItemValue.get();
            float attackRangeValue = attackRangeByConsumableValue.get() + attackRangeByItemValue.get();
            float attackSpeedValue = attackSpeedByConsumableValue.get() + attackSpeedByItemValue.get();
            // mining speed 와 luck 은 따로 적용할 능력치가 없음.

            // HEALTH
            float healthCorrectionValue = 20.0f + 2 * Math.min(healthValue, PlayerStats.MAX_HEALTH);
            event.player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(healthCorrectionValue);
            event.player.setHealth(event.player.getMaxHealth());

            // DAMAGE
            float damageCorrectionValue = 2.0f + 4.0f / 60.0f * Math.min(damageValue, PlayerStats.MAX_STAT);
            event.player.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(damageCorrectionValue);

            // MOVEMENT SPEED
            float movementSpeedCorrectionValue = 0.1f + 0.06f / 60 * Math.min(movementSpeedValue, PlayerStats.MAX_STAT);
            event.player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(movementSpeedCorrectionValue);

            // ATTACK RANGE
            float attackRangeCorrectionValue = 3 + 2 / 60.0f * Math.min(attackRangeValue, PlayerStats.MAX_STAT);
            event.player.getAttribute(ForgeMod.ENTITY_REACH.get()).setBaseValue(attackRangeCorrectionValue);

            // ATTACK SPEED
            float attackSpeedCorrectionValue = 4.0f + 0.8f / 60.0f * Math.min(attackSpeedValue, PlayerStats.MAX_STAT);
            event.player.getAttribute(Attributes.ATTACK_SPEED).setBaseValue(attackSpeedCorrectionValue);

            float[] playerStats = new float[]{
                healthByConsumableValue.get(),
                damageByConsumableValue.get(),
                attackSpeedByConsumableValue.get(),
                attackRangeByConsumableValue.get(),
                movementSpeedByConsumableValue.get(),
                miningSpeedByConsumableValue.get(),
                luckByConsumableValue.get(),
                healthByItemValue.get(),
                damageByItemValue.get(),
                attackSpeedByItemValue.get(),
                attackRangeByItemValue.get(),
                movementSpeedByItemValue.get(),
                miningSpeedByItemValue.get(),
                luckByItemValue.get(),
                coinValue.get()
            };

            ModMessages.sendToPlayer(new StatSyncS2CPacket(playerStats), (ServerPlayer) event.player);
        }
    }

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        double miningSpeedCorrectionValue;
        AtomicReference<Double> miningSpeedAtomicValue = new AtomicReference<>(0.0);
        event.getEntity().getCapability(PlayerStatsProvider.playerStatsCapability).ifPresent(playerStats -> {
            miningSpeedAtomicValue.updateAndGet(stat -> stat + playerStats.getValue(StatType.MINING_SPEED_BY_ITEM) + playerStats.getValue(StatType.MINING_SPEED_BY_CONSUMABLES));
        });
        miningSpeedCorrectionValue = 1.0 + 0.4 / 60.0 * miningSpeedAtomicValue.get();
        event.setNewSpeed(event.getOriginalSpeed() * (float) miningSpeedCorrectionValue);
    }

    @SubscribeEvent
    public static void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getEntity().level().isClientSide) {
            Player player = event.getEntity();
            // Player Stat
            player.getCapability(PlayerStatsProvider.playerStatsCapability).ifPresent(playerStats -> {
                float[] stats = new float[15];
                for (int i = 0; i < stats.length; i++) {
                    stats[i] = playerStats.getValue(i);
                }
                MinecraftForge.EVENT_BUS.post(new PlayerStatsChangedEvent(player));
            });
            // My Stuff
            player.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
                ModMessages.sendToPlayer(new MyStuffSyncS2CPacket(myStuff.getActiveSlots(), myStuff.getPassiveSlots(), myStuff.getActiveUpgraded()), (ServerPlayer) player);
            });
            // Player Option
            player.getCapability(PlayerOptionsProvider.playerOptionsCapability).ifPresent(playerOptions -> {
                ModMessages.sendToPlayer(new OptionSyncS2CPacket(playerOptions.getStatHudOption(), playerOptions.getStatHudDetailOptions()), (ServerPlayer) player);
            });
        }
    }

    @SubscribeEvent
    public static void onClone(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().reviveCaps();
            // Player Stat
            event.getOriginal().getCapability(PlayerStatsProvider.playerStatsCapability).ifPresent(oldStore -> {
                event.getEntity().getCapability(PlayerStatsProvider.playerStatsCapability).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                    MinecraftForge.EVENT_BUS.post(new PlayerStatsChangedEvent(event.getEntity()));
                });
            });
            // My Stuff
            event.getOriginal().getCapability(MyStuffProvider.myStuffCapability).ifPresent(oldStore -> {
                event.getEntity().getCapability(MyStuffProvider.myStuffCapability).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            // Player Option
            event.getOriginal().getCapability(PlayerOptionsProvider.playerOptionsCapability).ifPresent(oldStore -> {
                event.getEntity().getCapability(PlayerOptionsProvider.playerOptionsCapability).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            event.getOriginal().invalidateCaps();
        }
    }
}
