package net.jwn.jwn_items.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.jwn.jwn_items.Main;
import net.jwn.jwn_items.gui.menu.RouletteCommonScreenMenu;
import net.jwn.jwn_items.gui.menu.RouletteRareScreenMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class RouletteRareScreen extends AbstractContainerScreen<RouletteRareScreenMenu> {
    private static final ResourceLocation resourceLocation = new ResourceLocation(Main.MOD_ID, "textures/gui/roulette_screen.png");

    public RouletteRareScreen(RouletteRareScreenMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.leftPos = (width - 176) / 2;
        this.topPos = (height - 166) / 2;
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, resourceLocation);
        pGuiGraphics.blit(resourceLocation, leftPos, topPos, 0, 0, 176, 166);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
}
