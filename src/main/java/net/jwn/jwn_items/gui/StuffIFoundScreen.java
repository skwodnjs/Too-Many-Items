package net.jwn.jwn_items.gui;

import net.jwn.jwn_items.Main;
import net.jwn.jwn_items.capability.FoundStuffProvider;
import net.jwn.jwn_items.item.ItemType;
import net.jwn.jwn_items.item.ModItemProvider;
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
import net.minecraft.world.item.Item;
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
            items = foundStuff.get();
        });
    }

    @Override
    protected void init() {
        super.init();
        setting();
        System.out.println(items.length);

        this.leftPos = (width - 176) / 2;
        this.topPos = (height - 166) / 2;

        for (int i = 0; i < 54; i++) {
            int itemId = (page - 1) * 54 + i;
            if (itemId < items.length) {
                drawSlot(leftPos + 7 + (i % 9) * 18, topPos + 17 + (i / 9) * 22, items[itemId] == 0 ? -1 : i, items[itemId], ModItemProvider.getItemById(itemId).itemType == ItemType.CONSUMABLES);
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

    private void drawSlot(int pX, int pY, int itemId, int level, boolean isConsumable) {
        ImageButton background = new ImageButton(pX, pY, 18, 18, (level == 5 || (isConsumable && level != 0)) ? 34 : 16, 166, 0,
                BACKGROUND_RESOURCE, 256, 256, pButton -> {});
        ImageButton item;
        if (itemId == -1) {
            item = new ImageButton(pX + 1, pY + 1, 16, 16, 0, 166, 0,
                    BACKGROUND_RESOURCE, 256, 256, pButton -> {});
            item.setTooltip(Tooltip.create(Component.translatable("tooltip.jwn_items.unknown")));
        } else {
            ResourceLocation itemResourceLocation = new ResourceLocation(Main.MOD_ID, "textures/item/" + ModItemProvider.getItemById(itemId) + ".png");
            item = new ImageButton(pX + 1, pY + 1, 16, 16, 0, 0, 0,
                    itemResourceLocation, 16, 16, pButton -> {});
            String name = I18n.get("item.jwn_items." + ModItemProvider.getItemById(itemId));
            String tooltip = I18n.get("tooltip.jwn_items." + ModItemProvider.getItemById(itemId));
            String lvl5 = I18n.get("tooltip.jwn_items.lvl_5");
            String tooltip5 = I18n.get("tooltip.jwn_items." + ModItemProvider.getItemById(itemId) + "_5");
            String fullTooltip = name + "\n\n" + tooltip + (level > 1 ? "\n\n" + lvl5 + tooltip5 : "");
            item.setTooltip(Tooltip.create(Component.literal(fullTooltip)));
        }
        addRenderableWidget(background);
        addRenderableWidget(item);
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
