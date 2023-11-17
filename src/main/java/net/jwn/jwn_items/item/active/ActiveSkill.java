package net.jwn.jwn_items.item.active;

import net.jwn.jwn_items.capability.ModItemDataProvider;
import net.jwn.jwn_items.capability.PlayerStatProvider;
import net.jwn.jwn_items.effect.ModEffects;
import net.jwn.jwn_items.item.ItemType;
import net.jwn.jwn_items.item.ModItem;
import net.jwn.jwn_items.item.ModItemProvider;
import net.jwn.jwn_items.item.ModItems;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

import java.util.*;

public class ActiveSkill {
    public static void operateD1Server(Player player) {
        ArrayList<Integer> modItemList = new ArrayList<>();
        player.getInventory().items.forEach(itemStack -> {
            if (itemStack.getItem() instanceof ModItem modItem) {
                if (modItem.itemType != ItemType.CONSUMABLES) {
                    int slot = player.getInventory().items.indexOf(itemStack);
                    modItemList.add(slot);
                }
            }
        });
        Random random = new Random();
        int targetIndex = modItemList.get(random.nextInt(modItemList.size()));
        ModItem oldItem = (ModItem) player.getInventory().getItem(targetIndex).getItem();
        ModItem newItem = ModItemProvider.getRandomItem(oldItem.itemType, oldItem.quality);
        player.getInventory().setItem(targetIndex, newItem.getDefaultInstance());
    }
    public static void operateD6Server(Player player) {
        ArrayList<Integer> modItemList = new ArrayList<>();
        player.getInventory().items.forEach(itemStack -> {
            if (itemStack.getItem() instanceof ModItem modItem) {
                if (modItem.itemType != ItemType.CONSUMABLES) {
                    int slot = player.getInventory().items.indexOf(itemStack);
                    modItemList.add(slot);
                }
            }
        });
        for (int index : modItemList) {
            ModItem oldItem = (ModItem) player.getInventory().getItem(index).getItem();
            System.out.println(oldItem);
            ModItem newItem = ModItemProvider.getRandomItem(oldItem.itemType, oldItem.quality);
            player.getInventory().setItem(index, newItem.getDefaultInstance());
        }
    }
    public static void operateChargedTNT(Player player) {
        PrimedTnt primedtnt = new PrimedTnt(player.level(), player.position().x + 0.5D, player.position().y, player.position().z + 0.5D, player);
        player.level().addFreshEntity(primedtnt);
        player.level().playSound((Player)null, primedtnt.getX(), primedtnt.getY(), primedtnt.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
        player.level().gameEvent(player, GameEvent.PRIME_FUSE, player.position());}
    public static void operatePrescriptionServer(Player player) {
        Random random = new Random();
        int count = random.nextInt(5) + 1;
        Vec3 spawnPlace = player.position().add(player.getLookAngle().scale(3)).add((Math.random() - 0.5) * 0.1, 0, (Math.random() - 0.5) * 0.1);
        for (int i = 0; i < count; i++) {
            ItemEntity itemEntity = new ItemEntity(player.level(), spawnPlace.x, player.position().y, spawnPlace.z,
                    ModItems.PILL_ITEM.get().getDefaultInstance(), 0, 0.1, 0);
            player.level().addFreshEntity(itemEntity);
        }
    }
    public static void operateRadarServer(Player player) {
        List<Block> blocksToDetect = Arrays.asList(
                Blocks.COAL_ORE, Blocks.DEEPSLATE_COAL_ORE,
                Blocks.IRON_ORE, Blocks.DEEPSLATE_IRON_ORE, Blocks.RAW_IRON_BLOCK,
                Blocks.COPPER_ORE, Blocks.DEEPSLATE_COPPER_ORE, Blocks.RAW_COPPER_BLOCK,
                Blocks.GOLD_ORE, Blocks.DEEPSLATE_GOLD_ORE,
                Blocks.REDSTONE_ORE, Blocks.DEEPSLATE_REDSTONE_ORE,
                Blocks.LAPIS_ORE, Blocks.DEEPSLATE_LAPIS_ORE,
                Blocks.EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE,
                Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE
        );

        Map<Block, Integer> detectedBlock = new HashMap<>();

        detectedBlock.put(Blocks.COAL_ORE, 0);
        detectedBlock.put(Blocks.DEEPSLATE_COAL_ORE, 0);
        detectedBlock.put(Blocks.IRON_ORE, 0);
        detectedBlock.put(Blocks.DEEPSLATE_IRON_ORE, 0);
        detectedBlock.put(Blocks.RAW_IRON_BLOCK, 0);
        detectedBlock.put(Blocks.COPPER_ORE, 0);
        detectedBlock.put(Blocks.DEEPSLATE_COPPER_ORE, 0);
        detectedBlock.put(Blocks.RAW_COPPER_BLOCK, 0);
        detectedBlock.put(Blocks.GOLD_ORE, 0);
        detectedBlock.put(Blocks.DEEPSLATE_GOLD_ORE, 0);
        detectedBlock.put(Blocks.REDSTONE_ORE, 0);
        detectedBlock.put(Blocks.DEEPSLATE_REDSTONE_ORE, 0);
        detectedBlock.put(Blocks.LAPIS_ORE, 0);
        detectedBlock.put(Blocks.DEEPSLATE_LAPIS_ORE, 0);
        detectedBlock.put(Blocks.EMERALD_ORE, 0);
        detectedBlock.put(Blocks.DEEPSLATE_EMERALD_ORE, 0);
        detectedBlock.put(Blocks.DIAMOND_ORE, 0);
        detectedBlock.put(Blocks.DEEPSLATE_DIAMOND_ORE, 0);

        for (int i = -5; i <= 5; i++) {
            for (int j = -5; j <= 5; j++) {
                for (int k = -5; k <= 5; k++) {
                    BlockPos targetBlockPos = player.getOnPos().offset(i, k, j);
                    Block targetBlock = player.level().getBlockState(targetBlockPos).getBlock();
                    if (blocksToDetect.contains(targetBlock)) {
                        detectedBlock.compute(targetBlock, (key, value) -> value + 1);
                    }
                }
            }
        }

        if (detectedBlock.get(Blocks.DEEPSLATE_DIAMOND_ORE) != 0 || detectedBlock.get(Blocks.DIAMOND_ORE) != 0) {
            String ore = I18n.get(Blocks.DIAMOND_ORE.getDescriptionId());
            String ea = I18n.get("message.jwn_items.ea");
            int count = detectedBlock.get(Blocks.DEEPSLATE_DIAMOND_ORE) + detectedBlock.get(Blocks.DIAMOND_ORE);
            player.sendSystemMessage(Component.literal(ore + ": " + count + ea));
        } else if (detectedBlock.get(Blocks.DEEPSLATE_GOLD_ORE) != 0 || detectedBlock.get(Blocks.GOLD_ORE) != 0) {
            String ore = I18n.get(Blocks.GOLD_ORE.getDescriptionId());
            String ea = I18n.get("message.jwn_items.ea");
            int count = detectedBlock.get(Blocks.DEEPSLATE_GOLD_ORE) + detectedBlock.get(Blocks.GOLD_ORE);
            player.sendSystemMessage(Component.literal(ore + ": " + count + ea));
        } else if (detectedBlock.get(Blocks.DEEPSLATE_IRON_ORE) != 0 || detectedBlock.get(Blocks.IRON_ORE) != 0 || detectedBlock.get(Blocks.RAW_IRON_BLOCK) != 0) {
            String ore = I18n.get(Blocks.IRON_ORE.getDescriptionId());
            String ea = I18n.get("message.jwn_items.ea");
            int count = detectedBlock.get(Blocks.DEEPSLATE_IRON_ORE) + detectedBlock.get(Blocks.IRON_ORE) + detectedBlock.get(Blocks.RAW_IRON_BLOCK);
            player.sendSystemMessage(Component.literal(ore + ": " + count + ea));
        } else if (detectedBlock.get(Blocks.DEEPSLATE_EMERALD_ORE) != 0 || detectedBlock.get(Blocks.EMERALD_ORE) != 0
        || detectedBlock.get(Blocks.DEEPSLATE_LAPIS_ORE) != 0 || detectedBlock.get(Blocks.LAPIS_ORE) != 0
        || detectedBlock.get(Blocks.DEEPSLATE_REDSTONE_ORE) != 0 || detectedBlock.get(Blocks.REDSTONE_ORE) != 0) {
            String emerald_ore = I18n.get(Blocks.EMERALD_ORE.getDescriptionId());
            String lapis_ore = I18n.get(Blocks.LAPIS_ORE.getDescriptionId());
            String redstone_ore = I18n.get(Blocks.REDSTONE_ORE.getDescriptionId());
            String ea = I18n.get("message.jwn_items.ea");
            int emerald_count = detectedBlock.get(Blocks.DEEPSLATE_EMERALD_ORE) + detectedBlock.get(Blocks.EMERALD_ORE);
            int lapis_count = detectedBlock.get(Blocks.DEEPSLATE_LAPIS_ORE) + detectedBlock.get(Blocks.LAPIS_ORE);
            int redstone_count = detectedBlock.get(Blocks.DEEPSLATE_REDSTONE_ORE) + detectedBlock.get(Blocks.REDSTONE_ORE);
            player.sendSystemMessage(Component.literal(emerald_ore + ": " + emerald_count + ea + " / " + lapis_ore + ": " + lapis_count + ea + " / " + redstone_ore + ": " + redstone_count + ea));
        } else if (detectedBlock.get(Blocks.DEEPSLATE_COPPER_ORE) != 0 || detectedBlock.get(Blocks.COPPER_ORE) != 0 || detectedBlock.get(Blocks.RAW_COPPER_BLOCK) != 0) {
            String ore = I18n.get(Blocks.COPPER_ORE.getDescriptionId());
            String ea = I18n.get("message.jwn_items.ea");
            int count = detectedBlock.get(Blocks.DEEPSLATE_COPPER_ORE) + detectedBlock.get(Blocks.COPPER_ORE) + detectedBlock.get(Blocks.RAW_COPPER_BLOCK);
            player.sendSystemMessage(Component.literal(ore + ": " + count + ea));
        } else if (detectedBlock.get(Blocks.DEEPSLATE_COAL_ORE) != 0 || detectedBlock.get(Blocks.COAL_ORE) != 0) {
            String ore = I18n.get(Blocks.COAL_ORE.getDescriptionId());
            String ea = I18n.get("message.jwn_items.ea");
            int count = detectedBlock.get(Blocks.DEEPSLATE_COAL_ORE) + detectedBlock.get(Blocks.COAL_ORE);
            player.sendSystemMessage(Component.literal(ore + ": " + count + ea));
        } else {
            player.sendSystemMessage(Component.translatable("message.jwn_items.nothing"));
        }
    }
    public static void operateStar(Player player, int itemLevel) {
        player.getCapability(PlayerStatProvider.playerStatsCapability).ifPresent(playerStat -> {
            player.getCapability(ModItemDataProvider.modItemDataCapability).ifPresent(modItemData -> {
                modItemData.setStarStat(playerStat.get());
            });
        });
        player.addEffect(new MobEffectInstance(ModEffects.STAR_EFFECT.get(), (15 + itemLevel * 5) * 20));
    }
    public static void operateLavaWalker(Player player) {
        player.addEffect(new MobEffectInstance(ModEffects.LAVA_WALKER_EFFECT.get(), 60 * 20));
    }
}
