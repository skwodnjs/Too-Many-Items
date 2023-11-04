package net.jwn.jwn_items.item.active;

import net.jwn.jwn_items.Main;
import net.jwn.jwn_items.item.ItemType;
import net.jwn.jwn_items.item.ModItem;
import net.jwn.jwn_items.item.ModItemProvider;
import net.jwn.jwn_items.item.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Random;

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
}
