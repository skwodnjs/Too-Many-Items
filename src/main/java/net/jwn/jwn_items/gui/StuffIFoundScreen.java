package net.jwn.jwn_items.gui;

import net.jwn.jwn_items.Main;
import net.jwn.jwn_items.capability.FoundStuffProvider;
import net.jwn.jwn_items.item.ModItems;
import net.jwn.jwn_items.util.KeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.lwjgl.glfw.GLFW;

public class StuffIFoundScreen extends Screen {
    public StuffIFoundScreen() {
        super(Component.literal("Stuff I Found"));
    }

    private int leftPos, topPos;
    private int page = 1;

    private int[] items;

    private static final ResourceLocation BACKGROUND_RESOURCE = new ResourceLocation(Main.MOD_ID, "textures/gui/found_stuff.png");

    private void setting() {
        Player player = Minecraft.getInstance().player;
        player.getCapability(FoundStuffProvider.foundStuffCapability).ifPresent(foundStuff -> {
            items = foundStuff.getFoundStuffLevel();
        });
    }

    @Override
    protected void init() {
        super.init();
        setting();

        this.leftPos = (width - 176) / 2;
        this.topPos = (height - 166) / 2;

        for (int i = 0; i < 54; i++) {
            int id = (page - 1) * 54 + i;
            if (id < items.length) {
                ImageButton item;
                if (items[i] == 0) {
                    item = new ImageButton(leftPos + 8 + (i % 9) * 18, topPos + 18 + (i / 9) * 18 + (i / 27) * 12, 16, 16, 0, 166, 0,
                            BACKGROUND_RESOURCE, 256, 256, pButton -> {});
                    item.setTooltip(Tooltip.create(Component.literal("Unknown")));
                } else {
                    ResourceLocation itemResourceLocation = new ResourceLocation(Main.MOD_ID, "textures/item/" + ModItems.ModItemsProvider.getItemByID(i) + ".png");
                    item = new ImageButton(leftPos + 8 + (i % 9) * 18, topPos + 18 + (i / 9) * 18 + (i / 27) * 12, 16, 16,0, 0, 0,
                            itemResourceLocation, 16, 16, pButton -> {});
                    item.setTooltip(Tooltip.create(Component.literal("level: " + items[i])));
                }
                addRenderableWidget(item);
            }
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        pGuiGraphics.blit(BACKGROUND_RESOURCE, leftPos, topPos, 0, 0, 176, 166);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        String title = I18n.get("title." + Main.MOD_ID + ".found_stuff");
        pGuiGraphics.drawString(font, title, leftPos + 7, topPos + 7, 0x404040, false);

        pGuiGraphics.drawString(font, "-" + page + "-", leftPos + 83, topPos + 150, 0x404040, false);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (pKeyCode == KeyBindings.FOUND_STUFF_KEY.getKey().getValue()) {
            onClose();
            return true;
        } else if (pKeyCode == GLFW.GLFW_KEY_LEFT) {
            page = Math.max(page - 1, 1);
            rebuildWidgets();
        } else if (pKeyCode == GLFW.GLFW_KEY_RIGHT) {
            page = Math.min(page + 1, (items.length - 1) / 54 + 1);
            rebuildWidgets();
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }
}
