package net.jwn.jwn_items.effect.custom;

import net.jwn.jwn_items.capability.PlayerStatProvider;
import net.jwn.jwn_items.event.custom.PlayerStatsChangedEvent;
import net.jwn.jwn_items.util.StatType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;

public class StarEffect extends MobEffect {
    public StarEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (pLivingEntity instanceof Player player) {
            player.getCapability(PlayerStatProvider.playerStatsCapability).ifPresent(playerStat -> {
                float coin = playerStat.getValue(StatType.COIN);
                playerStat.set(new float[]{30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, coin});
                MinecraftForge.EVENT_BUS.post(new PlayerStatsChangedEvent(player));
            });
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
