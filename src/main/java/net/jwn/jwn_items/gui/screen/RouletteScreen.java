package net.jwn.jwn_items.gui.screen;

import net.jwn.jwn_items.Main;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class RouletteScreen extends Screen {
    public RouletteScreen() {
        super(Component.literal("roulette_screen"));
    }

    private int leftPos, topPos;
    private static final ResourceLocation BACKGROUND_RESOURCE = new ResourceLocation(Main.MOD_ID, "textures/gui/roulette_screen.png");

    @Override
    protected void init() {
        super.init();
        this.leftPos = (width - 176) / 2;
        this.topPos = (height - 60) / 2;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        pGuiGraphics.blit(BACKGROUND_RESOURCE, leftPos, topPos, 0, 0, 176, 60);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        pGuiGraphics.drawString(font, title, leftPos + 7, topPos + 7, 0x404040, false);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
