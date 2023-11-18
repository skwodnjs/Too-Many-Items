package net.jwn.jwn_items.gui.screen;

import net.jwn.jwn_items.Main;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ShopScreen extends Screen {
    public ShopScreen() {
        super(Component.literal("synthesis_shop"));
    }

    private int leftPos, topPos;
    private static final ResourceLocation BACKGROUND_RESOURCE = new ResourceLocation(Main.MOD_ID, "textures/gui/shop_screen.png");

    @Override
    protected void init() {
        super.init();
        this.leftPos = (width - 176) / 2;
        this.topPos = (height - 166) / 2;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        pGuiGraphics.blit(BACKGROUND_RESOURCE, leftPos, topPos, 0, 0, 176, 166);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        pGuiGraphics.drawString(font, title, leftPos + 7, topPos + 7, 0x404040, false);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
