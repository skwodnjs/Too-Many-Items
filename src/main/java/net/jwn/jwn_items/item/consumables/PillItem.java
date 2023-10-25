package net.jwn.jwn_items.item.consumables;

import net.jwn.jwn_items.Main;
import net.jwn.jwn_items.event.custom.PlayerStatsChangedEvent;
import net.jwn.jwn_items.item.ItemType;
import net.jwn.jwn_items.item.ModItem;
import net.jwn.jwn_items.stat.Stat;
import net.jwn.jwn_items.stat.StatType;
import net.jwn.jwn_items.util.PlayerStatsController;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PillItem extends ModItem {
    private final List<StatType> statTypes = Arrays.asList(
            StatType.HEALTH_BY_CONSUMABLES,
            StatType.DAMAGE_BY_CONSUMABLES,
            StatType.ATTACK_SPEED_BY_CONSUMABLES,
            StatType.ATTACK_RANGE_BY_CONSUMABLES,
            StatType.MINING_SPEED_BY_CONSUMABLES,
            StatType.MOVEMENT_SPEED_BY_CONSUMABLES
    );

    public PillItem(Properties pProperties, int grade, int ID) {
        super(pProperties, ItemType.CONSUMABLES, grade, ID);
    }

    protected void playSound(Level level, Player player, SoundEvent soundEvent, float volume, float pitch) {
        level.playSound(player, player.position().x, player.position().y, player.position().z, soundEvent, SoundSource.PLAYERS, volume, pitch);
    }

    protected void displayMessage(Player pPlayer, Component message) {
        pPlayer.displayClientMessage(message, true);
    }

    private int getRandomEA() {
        double r = Math.random();
        int ea;
        if (r < 0.5) {
            ea = 1;
        } else if (r < 0.7) {
            ea = 2;
        } else {
            ea = 4;
        }
        return ea;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        playSound(pLevel, pPlayer, SoundEvents.WOODEN_DOOR_OPEN, 0.5f, 0.5f);

        if (!pLevel.isClientSide) {
            String message = "";
            Collections.shuffle(statTypes);

            int ea = getRandomEA();
            for (int index = 0; index < ea; index++) {
                float value = (float) (Math.random() * 1.5 + 0.5);
                float sign;
                double r = Math.random();
                if (r < 0.5) {
                    sign = 1;
                } else {
                    sign = -1;
                }

                Stat randomStat = new Stat(statTypes.get(index), sign * value);
                float appliedValue = PlayerStatsController.addSingleStat((ServerPlayer) pPlayer, randomStat);
                if (appliedValue != 0) {
                    message += I18n.get("item." + Main.MOD_ID + ".message_" + randomStat.getStatType().getName())
                            + ((sign > 0) ? " +" : " -") + Math.abs(appliedValue) + " ";
                }
            }
            if (message == "") {
                message = I18n.get("item." + Main.MOD_ID + ".message_no_stat_changed");
            }
            displayMessage(pPlayer, Component.literal(message));
            MinecraftForge.EVENT_BUS.post(new PlayerStatsChangedEvent(pPlayer));
        }

        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        if (!pPlayer.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }
}
