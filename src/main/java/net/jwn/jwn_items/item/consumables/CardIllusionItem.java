package net.jwn.jwn_items.item.consumables;

import net.jwn.jwn_items.event.custom.ModItemUsedSuccessfullyEvent;
import net.jwn.jwn_items.item.Quality;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;

public class CardIllusionItem extends ConsumableItem {
    public CardIllusionItem(Properties pProperties, int id, Quality quality) {
        super(pProperties, id, quality);
    }

    @Override
    public void operate(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        Vec3 range = new Vec3(4, 4, 4);
        Vec3 startVec = pPlayer.position().add(range);
        Vec3 endVec = pPlayer.position().subtract(range);
        AABB aabb = new AABB(startVec, endVec);
        pLevel.getEntities(pPlayer, aabb).forEach(entity -> {
            if (entity instanceof Enemy && !(entity instanceof Warden || entity instanceof WitherBoss || entity instanceof EnderDragon)) {
                entity.kill();
            }
        });
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        operate(pLevel, pPlayer, pUsedHand);
        MinecraftForge.EVENT_BUS.post(new ModItemUsedSuccessfullyEvent(pPlayer, itemType, id));

        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        if (!pPlayer.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }
}
