package net.jwn.jwn_items.event;

import net.jwn.jwn_items.Main;
import net.jwn.jwn_items.capability.*;
import net.jwn.jwn_items.effect.ModEffects;
import net.jwn.jwn_items.event.custom.ModItemUsedSuccessfullyEvent;
import net.jwn.jwn_items.event.custom.PlayerStatsChangedEvent;
import net.jwn.jwn_items.item.ItemType;
import net.jwn.jwn_items.item.ModItem;
import net.jwn.jwn_items.item.ModItems;
import net.jwn.jwn_items.item.passive.*;
import net.jwn.jwn_items.networking.ModMessages;
import net.jwn.jwn_items.networking.packet.PlayerStatsSyncS2CPacket;
import net.jwn.jwn_items.util.StatType;
import net.jwn.jwn_items.util.Functions;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Mod.EventBusSubscriber(modid = Main.MOD_ID)
public class ModEvents {
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
            if (!event.getObject().getCapability(ModItemDataProvider.modItemDataCapability).isPresent()) {
                event.addCapability(new ResourceLocation(Main.MOD_ID, "mod_item_data"), new ModItemDataProvider());
            }
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
    public static void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (event.phase == TickEvent.Phase.END) {
            player.getCapability(CoolTimeProvider.coolTimeCapability).ifPresent(CoolTime::sub);
            PassiveSkill.operateTick(player);
            if (event.side == LogicalSide.SERVER) {
                PassiveSkill.operateServerTick((ServerPlayer) player);
            } else {
                PassiveSkill.operateClientTick(player);
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
        // 스텟 확인
        event.player.getCapability(PlayerStatProvider.playerStatsCapability).ifPresent(playerStat -> {
            event.player.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
                float luck = playerStat.getValue(StatType.LUCK_BY_CONSUMABLES) + playerStat.getValue(StatType.LUCK_BY_ITEM);
                if (luck >= 40) {
                    myStuff.activeUpgrade();
                }
            });
        });
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
            Functions.syncModItemDataS2C((ServerPlayer) player);
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
            // Mod Item Data
            event.getOriginal().getCapability(ModItemDataProvider.modItemDataCapability).ifPresent(oldStore -> {
                event.getEntity().getCapability(ModItemDataProvider.modItemDataCapability).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            event.getOriginal().invalidateCaps();
        }
    }

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (event.getEntity().hasEffect(ModEffects.STAR_EFFECT.get()) && event.getItemStack().getItem() instanceof ModItem) {
            if (!event.getLevel().isClientSide) event.getEntity().sendSystemMessage(Component.translatable("message.jwn_items.cannot_use_skill"));
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onMobEffectExpired(MobEffectEvent.Expired event) {
        if (event.getEntity() instanceof Player player && event.getEffectInstance().getEffect() == ModEffects.STAR_EFFECT.get()) {
            player.getCapability(PlayerStatProvider.playerStatsCapability).ifPresent(playerStat -> {
                player.getCapability(ModItemDataProvider.modItemDataCapability).ifPresent(modItemData -> {
                    playerStat.set(modItemData.getStarStat());
                    ModMessages.sendToPlayer(new PlayerStatsSyncS2CPacket(playerStat.get()), (ServerPlayer) player);
                    MinecraftForge.EVENT_BUS.post(new PlayerStatsChangedEvent(player));
                });
            });
        }
    }

    @SubscribeEvent
    public static void onLivingDeathEvent(LivingDeathEvent event) {
        if (event.getEntity().getTags().contains("ahchoo") && event.getSource().getDirectEntity() instanceof Player player) {
            player.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
                if (myStuff.isMaxLevel(((ModItem) (ModItems.MUSTACHE_ITEM.get())).id)) {
                    PassiveSkill.operateMustacheMaxLevel(player);
                    if (!player.level().isClientSide) Functions.syncPlayerStatS2C((ServerPlayer) player);
                }
            });
        }
    }

    @SubscribeEvent
    public static void onLivingHurtEvent(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            player.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
                if (myStuff.isMaxLevel(((ModItem) (ModItems.STAR.get())).id)) {
                    event.setAmount(event.getAmount() / 2);
                }
                if (myStuff.isMaxLevel(((ModItem) (ModItems.LAVA_WALKER.get())).id)) {
                    List<String> fireList = new ArrayList<>(){{
                       add("lava");
                       add("onFire");
                       add("inFire");
                    }};
                    if (fireList.contains(event.getSource().type().msgId())) {
                        event.setAmount(event.getAmount() / 2);
                    }
                }
            });
        }
    }
}
