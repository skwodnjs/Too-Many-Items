package net.jwn.jwn_items.item.passive;

import net.jwn.jwn_items.capability.MyStuffProvider;
import net.jwn.jwn_items.item.ModItem;
import net.jwn.jwn_items.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;
import java.util.List;

public class PassiveSkill {
    private static void agingServerTick(Player player) {
        Vec3 pStart = player.position().add(-5, -1, -5);
        Vec3 pEnd = player.position().add(5, 1, 5);
        AABB aabb = new AABB(pStart, pEnd);
        player.level().getEntities(player, aabb).forEach(entity -> {
            if (entity instanceof AgeableMob ageableMob) {
                if (ageableMob.getAge() < 0) {
                    ((AgeableMob) entity).setAge(-1);
                }
            }
        });
    }
    private static void battery5vServerTick(Player player) {
        Vec3 pStart = player.position().add(-5, -1, -5);
        Vec3 pEnd = player.position().add(5, 1, 5);
        AABB aabb = new AABB(pStart, pEnd);
        player.level().getEntities(player, aabb).forEach(entity -> {
            if (entity instanceof Monster) {
                entity.addTag("shocked");
            }
        });
    }
    private static void mustacheServerTick(Player player) {
        Vec3 pStart = player.position().add(-5, -1, -5);
        Vec3 pEnd = player.position().add(5, 1, 5);
        AABB aabb = new AABB(pStart, pEnd);
        player.level().getEntities(player, aabb).forEach(entity -> {
            if (entity instanceof Monster) {
                entity.addTag("ahchoo");
            }
        });
    }
    private static void rapidGrowthServerTick(Player player) {
        for (int i = -5; i <= 5; i++) {
            for (int j = -5; j <= 5; j++) {
                for (int k = 0; k < 2; k++) {
                    BlockPos targetBlockPos = player.getOnPos().offset(i, k, j);
                    if (player.level().getBlockState(targetBlockPos).getBlock() instanceof CropBlock cropBlock) {
                        if (Math.random() < 0.01) cropBlock.growCrops(player.level(), targetBlockPos, player.level().getBlockState(targetBlockPos));
                    }
                }
            }
        }
    }
    private static void autoPortionServerTick(Player player, int level) {
        if (player.isSwimming()) {
            player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 60 + 10 * level, 0));
        }
        if (player.isOnFire()) {
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 60 + 10 * level, 0));
        }
    }

    public static void operateServerTick(Player player) {
        player.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
            List<Integer> passiveId = Arrays.stream(myStuff.getPassiveSlots()).map(modSlot -> modSlot.itemId).toList();
            if (passiveId.contains(((ModItem) ModItems.MUSTACHE_ITEM.get()).id)) {
                int level = myStuff.getLevelById(((ModItem) ModItems.MUSTACHE_ITEM.get()).id);
                if (Math.random() < 0.01 + level * 0.01) mustacheServerTick(player);
            }
            if (passiveId.contains(((ModItem) ModItems.BATTERY_5V.get()).id)) {
                int level = myStuff.getLevelById(((ModItem) ModItems.BATTERY_5V.get()).id);
                if (Math.random() < 0.01 + level * 0.01) battery5vServerTick(player);
            }
            if (passiveId.contains(((ModItem) ModItems.AGING.get()).id)) {
                int level = myStuff.getLevelById(((ModItem) ModItems.AGING.get()).id);
                if (Math.random() < 0.01 + level * 0.01) agingServerTick(player);
            }
            if (passiveId.contains(((ModItem) ModItems.RAPID_GROWTH.get()).id)) {
                int level = myStuff.getLevelById(((ModItem) ModItems.RAPID_GROWTH.get()).id);
                if (Math.random() < 0.01 + level * 0.01) rapidGrowthServerTick(player);
            }
            if (passiveId.contains(((ModItem) ModItems.AUTO_PORTION.get()).id)) {
                int level = myStuff.getLevelById(((ModItem) ModItems.AUTO_PORTION.get()).id);
                if (Math.random() < 0.01 + level * 0.01) autoPortionServerTick(player, level);
            }
        });
    }
}