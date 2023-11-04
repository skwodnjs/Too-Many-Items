package net.jwn.jwn_items.hud;

import net.jwn.jwn_items.Main;
import net.jwn.jwn_items.capability.MyStuffProvider;
import net.jwn.jwn_items.item.ModItemProvider;
import net.jwn.jwn_items.util.ModResourceLocations;
import net.jwn.jwn_items.util.ModSlot;
import net.jwn.jwn_items.item.ModItem;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ActiveItemHubOverlay {
    public static final IGuiOverlay ACTIVE_ITEM_HUD = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Player player = Minecraft.getInstance().player;

        int x = 13;
        int y = 13;
        guiGraphics.blit(ModResourceLocations.SCREEN_RESOURCE, x, y, 93, 166, 35, 18, 256, 256);
        player.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
            ModSlot mainActiveSlot = myStuff.getActiveSlots()[0];
            ModSlot subActiveSlot = myStuff.getActiveSlots()[1];
            ModItem mainItem = ModItemProvider.getItemById(mainActiveSlot.itemId);
            ModItem subItem = ModItemProvider.getItemById(subActiveSlot.itemId);
            ResourceLocation mainItemResourceLocation = new ResourceLocation(Main.MOD_ID, "textures/item/" + mainItem + ".png");
            ResourceLocation subItemResourceLocation = new ResourceLocation(Main.MOD_ID, "textures/item/" + subItem + ".png");
            if (mainActiveSlot.itemId != 0) {
                guiGraphics.blit(mainItemResourceLocation, x + 1, y + 1, 0, 0, 16, 16, 16, 16);
                if (mainActiveSlot.locked) {
                    guiGraphics.blit(ModResourceLocations.SCREEN_RESOURCE, x + 11, y + 11, 2, 166, 7, 7, 256, 256);
                }
            }
            if (subActiveSlot.itemId != 0) {
                guiGraphics.blit(subItemResourceLocation, x + 19, y, 0, 0, 16, 16, 16, 16);
                if (subActiveSlot.locked) {
                    guiGraphics.blit(ModResourceLocations.SCREEN_RESOURCE, x + 28, y + 9, 2, 166, 7, 7, 256, 256);
                }
            }
        });
    };
}

