package net.jwn.jwn_items.hud;

import net.jwn.jwn_items.Main;
import net.jwn.jwn_items.capability.MyStuffProvider;
import net.jwn.jwn_items.inventory.ModSlot;
import net.jwn.jwn_items.item.ModItem;
import net.jwn.jwn_items.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ActiveItemHubOverlay {
    private static final ResourceLocation IMAGE = new ResourceLocation(Main.MOD_ID, "textures/gui/my_stuff.png");

    public static final IGuiOverlay ACTIVE_ITEM_HUD = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Player player = Minecraft.getInstance().player;

        int x = 13;
        int y = 13;
        guiGraphics.blit(IMAGE, x, y, 109, 166, 48, 26, 256, 256);
        player.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
            ModSlot mainActiveSlot = myStuff.getActiveSlots()[0];
            ModSlot subActiveSlot = myStuff.getActiveSlots()[1];
            ModItem mainItem = ModItems.ModItemsProvider.___getItemByID(mainActiveSlot.itemID);
            ModItem subItem = ModItems.ModItemsProvider.___getItemByID(subActiveSlot.itemID);
            ResourceLocation mainItemResourceLocation = new ResourceLocation(Main.MOD_ID, "textures/item/" + mainItem + ".png");
            ResourceLocation subItemResourceLocation = new ResourceLocation(Main.MOD_ID, "textures/item/" + subItem + ".png");
            if (mainActiveSlot.itemID != 0) {
                guiGraphics.blit(mainItemResourceLocation, x + 5, y + 5, 0, 0, 16, 16, 16, 16);
                if (mainActiveSlot.locked) {
                    guiGraphics.blit(IMAGE, x + 15, y + 15, 54, 166, 7, 7, 256, 256);
                }
            }
            if (subActiveSlot.itemID != 0) {
                guiGraphics.blit(subItemResourceLocation, x + 31, y + 9, 0, 0, 16, 16, 16, 16);
                if (subActiveSlot.locked) {
                    guiGraphics.blit(IMAGE, x + 41, y + 19, 54, 166, 7, 7, 256, 256);
                }
            }
            // int pX, int pY, int pBlitOffset, int pWidth, int pHeight, TextureAtlasSprite pSprite, float pRed, float pGreen, float pBlue, float pAlpha
        });
    };
}

