package net.jwn.jwn_items.item.consumables;

import net.jwn.jwn_items.Main;
import net.jwn.jwn_items.capability.PlayerStatProvider;
import net.jwn.jwn_items.event.custom.ModItemUsedSuccessfullyEvent;
import net.jwn.jwn_items.event.custom.PlayerStatsChangedEvent;
import net.jwn.jwn_items.item.ConsumableItem;
import net.jwn.jwn_items.networking.ModMessages;
import net.jwn.jwn_items.networking.packet.PlayerStatsSyncS2CPacket;
import net.jwn.jwn_items.util.Stat;
import net.jwn.jwn_items.util.StatType;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Pill extends ConsumableItem {
    private final List<StatType> statTypes = Arrays.asList(
            StatType.HEALTH_BY_CONSUMABLES,
            StatType.DAMAGE_BY_CONSUMABLES,
            StatType.ATTACK_SPEED_BY_CONSUMABLES,
            StatType.ATTACK_RANGE_BY_CONSUMABLES,
            StatType.MINING_SPEED_BY_CONSUMABLES,
            StatType.MOVEMENT_SPEED_BY_CONSUMABLES
    );

    public Pill(Properties pProperties, int id, int quality) {
        super(pProperties, id, quality);
    }

    private int getRandom() {
        double r = Math.random();
        int ea;
        if (r < 0.4) {
            ea = 1;
        } else if (r < 0.7) {
            ea = 2;
        } else  if (r < 0.9) {
            ea = 3;
        } else {
            ea = 4;
        }
        return ea;
    }

    @Override
    public void operate(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide) {
            StringBuilder message = new StringBuilder();
            Collections.shuffle(statTypes);

            int changes = getRandom();
            for (int i = 0; i < changes; i++) {
                float value = (float) (Math.random() * 1.5 + 0.5);
                float sign = (Math.random() < 0.5) ? -1.0f : 1.0f;
                Stat randomStat = new Stat(statTypes.get(i), sign * value);

                float result = Stat.addSingleStat(pPlayer, randomStat);
                pPlayer.getCapability(PlayerStatProvider.playerStatsCapability).ifPresent(playerStats -> {
                    ModMessages.sendToPlayer(new PlayerStatsSyncS2CPacket(playerStats.get()), (ServerPlayer) pPlayer);
                });

                if (result != 0) {
                    message.append(I18n.get("stat." + Main.MOD_ID + "." + randomStat.getStatType().getName())).append((sign > 0) ? " +" : " -").append(Math.abs(result)).append(" ");
                }
            }
            if (message.toString().equals("")) {
                message.append(I18n.get("item." + Main.MOD_ID + ".message_no_stat_changed"));
            }
            pPlayer.displayClientMessage(Component.literal(message.toString()), true);
            MinecraftForge.EVENT_BUS.post(new PlayerStatsChangedEvent(pPlayer));
        }
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

