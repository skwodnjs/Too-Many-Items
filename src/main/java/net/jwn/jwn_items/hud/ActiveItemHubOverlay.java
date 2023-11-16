package net.jwn.jwn_items.hud;

import net.jwn.jwn_items.Main;
import net.jwn.jwn_items.capability.MyStuffProvider;
import net.jwn.jwn_items.capability.PlayerStatProvider;
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
        guiGraphics.blit(ModResourceLocations.SCREEN_RESOURCE, x, y, 93, 166, 18, 18, 256, 256);

        player.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
            ModSlot mainActiveSlot = myStuff.getActiveSlots()[0];
            ModItem mainItem = ModItemProvider.getItemById(mainActiveSlot.itemId);
            if (mainItem.id != 0) {
                ResourceLocation mainItemResourceLocation = new ResourceLocation(Main.MOD_ID, "textures/item/" + mainItem + ".png");
                guiGraphics.blit(mainItemResourceLocation, x + 1, y + 1, 0, 0, 16, 16, 16, 16);
            }

            int subSlot = myStuff.isActiveUpgraded() ? 4 : 2;
            for (int i = 0; i < subSlot ; i++) {
                guiGraphics.blit(ModResourceLocations.SCREEN_RESOURCE, x + 19 + i * 17, y, 111, 166, 16, 16, 256, 256);
                ModSlot activeSlot = myStuff.getActiveSlots()[i + 1];
                ModItem item = ModItemProvider.getItemById(activeSlot.itemId);
                if (item.id != 0) {
                    ResourceLocation itemResourceLocation = new ResourceLocation(Main.MOD_ID, "textures/item/" + item + ".png");
                    guiGraphics.blit(itemResourceLocation, x + 19 + i * 17, y + 1, 0, 0, 16, 16, 16, 16);
                }
            }
        });
    };
}

