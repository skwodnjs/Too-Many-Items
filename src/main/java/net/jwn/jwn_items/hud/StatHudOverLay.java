package net.jwn.jwn_items.hud;

import net.jwn.jwn_items.capability.PlayerOptionProvider;
import net.jwn.jwn_items.capability.PlayerStatProvider;
import net.jwn.jwn_items.util.StatType;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import static net.jwn.jwn_items.util.ModResourceLocations.STAT_RESOURCES;

public class StatHudOverLay {
    public static final IGuiOverlay STAT_HUD = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Player player = Minecraft.getInstance().player;

        int x = 13;
        int y = (screenHeight - 13 * 7) / 2;

        float[] stats = new float[8];
        player.getCapability(PlayerStatProvider.playerStatsCapability).ifPresent(playerStats -> {
            for (int i = 0; i < 7; i++) {
                stats[i] = playerStats.getValue(i) + playerStats.getValue(i + 7);
            }
            stats[7] = playerStats.getValue(StatType.COIN);
        });

        player.getCapability(PlayerOptionProvider.playerOptionsCapability).ifPresent(playerOptions -> {
            if (playerOptions.getStatHudOption()) {
                String text;
                int row = 0;
                for (int i = 0; i < playerOptions.getStatHudDetailOptions().length; i++) {
                    if (playerOptions.getStatHudDetailOptions()[i]) {
                        guiGraphics.blit(STAT_RESOURCES[i], x, y - 2 + row * 13, 0, 0, 12, 12, 12, 12);
                        guiGraphics.drawString(Minecraft.getInstance().font, " " + stats[i], x + 14, y + row * 13, 0x000000, false);
                        row++;
                    }
                }
            }
        });
    };
}
