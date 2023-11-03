package net.jwn.jwn_items.gui;

import net.jwn.jwn_items.Main;
import net.jwn.jwn_items.capability.MyStuffProvider;
import net.jwn.jwn_items.capability.PlayerStatsProvider;
import net.jwn.jwn_items.inventory.ModSlot;
import net.jwn.jwn_items.item.ItemType;
import net.jwn.jwn_items.item.ModItem;
import net.jwn.jwn_items.item.ModItems;
import net.jwn.jwn_items.networking.ModMessages;
import net.jwn.jwn_items.networking.packet.ChangeMainActiveItemC2SPacket;
import net.jwn.jwn_items.networking.packet.MyStuffSyncC2SPacket;
import net.jwn.jwn_items.util.KeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.ImageWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.Arrays;

import static net.jwn.jwn_items.util.ModResourceLocations.*;
import static net.jwn.jwn_items.util.Options.*;

public class MyStuffScreen extends Screen {
    public MyStuffScreen() {
        super(Component.literal("My Stuff"));
    }

    private int leftPos, topPos;
    private final boolean[] removableActiveSlot = new boolean[ACTIVE_MAX_UPGRADE];
    private final boolean[] removablePassiveSlot = new boolean[PASSIVE_MAX];
    private boolean removeMode = false;

    private ModSlot[] activeSlot;
    private ModSlot[] passiveSlot;

    private boolean activeUpgraded;

    private float[] stats;

    private static final ResourceLocation BACKGROUND_RESOURCE = new ResourceLocation(Main.MOD_ID, "textures/gui/my_stuff.png");
    private static final ResourceLocation BACKGROUND_UPGRADE_RESOURCE = new ResourceLocation(Main.MOD_ID, "textures/gui/my_stuff_upgrade.png");

    private void setting() {
        Player player = Minecraft.getInstance().player;
        player.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
            activeSlot = myStuff.getActiveSlots();
            passiveSlot = myStuff.getPassiveSlots();
            activeUpgraded = myStuff.isActiveUpgraded();
        });
        player.getCapability(PlayerStatsProvider.playerStatsCapability).ifPresent(playerStats -> {
            stats = playerStats.getAll();
        });
    }

    @Override
    protected void init() {
        super.init();
        setting();

        this.leftPos = (width - 176) / 2;
        this.topPos = (height - 166) / 2;

        ImageWidget health, damage, attack_speed, attack_range, mining_speed, movement_speed, luck, coin;
        ImageButton mode;

        health = new ImageWidget(leftPos + 13, topPos + 23, 12, 12, HEALTH_RESOURCE);
        damage = new ImageWidget(leftPos + 13, topPos + 36, 12, 12, DAMAGE_RESOURCE);
        attack_speed = new ImageWidget(leftPos + 50, topPos + 23, 12, 12, ATTACK_SPEED_RESOURCE);
        attack_range = new ImageWidget(leftPos + 50, topPos + 36, 12, 12, ATTACK_RANGE_RESOURCE);
        mining_speed = new ImageWidget(leftPos + 87, topPos + 23, 12, 12, MINING_SPEED_RESOURCE);
        movement_speed = new ImageWidget(leftPos + 87, topPos + 36, 12, 12, MOVEMENT_SPEED_RESOURCE);
        luck = new ImageWidget(leftPos + 124, topPos + 23, 12, 12, LUCK_RESOURCE);
        coin = new ImageWidget(leftPos + 124, topPos + 36, 12, 12, COIN_RESOURCE);

        health.setTooltip(Tooltip.create(Component.literal("health: " + (stats[0] + stats[7]) + "\n" + stats[7] + " increased by item")));
        damage.setTooltip(Tooltip.create(Component.literal("damage: " + (stats[1] + stats[7]) + "\n" + stats[8] + " increased by item")));
        attack_speed.setTooltip(Tooltip.create(Component.literal("attack_speed: " + (stats[2] + stats[7]) + "\n" + stats[9] + " increased by item")));
        attack_range.setTooltip(Tooltip.create(Component.literal("attack_range: " + (stats[3] + stats[7]) + "\n" + stats[10] + " increased by item")));
        mining_speed.setTooltip(Tooltip.create(Component.literal("mining_speed: " + (stats[4] + stats[7]) + "\n" + stats[11] + " increased by item")));
        movement_speed.setTooltip(Tooltip.create(Component.literal("movement_speed: " + (stats[5] + stats[7]) + "\n" + stats[12] + " increased by item")));
        luck.setTooltip(Tooltip.create(Component.literal("luck: " + (stats[6] + stats[7]) + "\n" + stats[13] + " increased by item")));
        coin.setTooltip(Tooltip.create(Component.literal("coin: " + stats[14])));

        addRenderableWidget(health);
        addRenderableWidget(damage);
        addRenderableWidget(attack_speed);
        addRenderableWidget(attack_range);
        addRenderableWidget(mining_speed);
        addRenderableWidget(movement_speed);
        addRenderableWidget(luck);
        addRenderableWidget(coin);

        mode = new ImageButton(leftPos + 152, topPos + 65, 16, 16, (removeMode) ? (isRemovableItemExists()) ? 93 : 77 : 61, 166, 0,
                BACKGROUND_RESOURCE, 256, 256, pButton -> {
            if (isRemovableItemExists()) removeItem();
            else changeMode();
        });
        addRenderableWidget(mode);

        for (int i = 0; i < activeSlot.length; i++) {
            if (activeSlot[i].itemID != 0) {
                if (i == 0) {
                    drawSlot(leftPos + 12, topPos + 61, activeSlot[i].itemID, i, activeSlot[i].locked, removableActiveSlot[i]);
                } else {
                    drawSlot(leftPos + 34 * i, topPos + 65, activeSlot[i].itemID, i, activeSlot[i].locked, removableActiveSlot[i]);
                }
            }
        }

        for (int i = 0; i < passiveSlot.length; i++) {
            if (passiveSlot[i].itemID != 0) {
                drawSlot(leftPos + 8 + 18 * (i % 9), topPos + 89 + 24 * (i / 9), passiveSlot[i].itemID, i, passiveSlot[i].locked, removablePassiveSlot[i]);
            }
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        pGuiGraphics.blit(activeUpgraded ? BACKGROUND_RESOURCE : BACKGROUND_UPGRADE_RESOURCE, leftPos, topPos, 0, 0, 176, 166);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        String title = I18n.get("title." + Main.MOD_ID + ".my_stuff");
        pGuiGraphics.drawString(font, title, leftPos + 7, topPos + 7, 0x404040, false);
    }

    public void drawSlot(int pX, int pY, int itemID, int slot, boolean locked, boolean deleted) {
        ModItem item = ModItems.ModItemsProvider.___getItemByID(itemID);
        ResourceLocation itemResourceLocation = new ResourceLocation(Main.MOD_ID, "textures/item/" + item + ".png");
        if (deleted) {
            ImageButton deletedBackground = new ImageButton(pX - 1, pY - 1, 18, 18, 36, 166, 0, BACKGROUND_RESOURCE, 256, 256, pButton -> makeItemRemovable(item.itemType, slot, false));
            addRenderableWidget(deletedBackground);
        }
        ImageButton imageButton;
        if (removeMode) {
            imageButton = new ImageButton(pX, pY, 16, 16, 0, 0, 0, itemResourceLocation, 16, 16, pButton -> makeItemRemovable(item.itemType, slot, !deleted));
        } else {
            imageButton = new ImageButton(pX, pY, 16, 16, 0, 0, 0, itemResourceLocation, 16, 16, pButton -> lockItem(item.itemType, slot, !locked));
        }
        imageButton.setTooltip(Tooltip.create(Component.literal(item.toString())));
        addRenderableWidget(imageButton);
        if (locked) {
            ImageButton lock = new ImageButton(pX + 10, pY + 10, 7, 7, 54, 166, 0,
                    BACKGROUND_RESOURCE, 256, 256, pButton -> lockItem(item.itemType, slot, false));
            addRenderableWidget(lock);
        }

        // level, 그 전에 cool time 표기부터
    }

    private boolean isRemovableItemExists() {
        if (!removeMode) return false;
        boolean toReturn = false;
        for (boolean b : removableActiveSlot) {
            if (b) {
                toReturn = true;
                break;
            }
        }
        for (boolean b : removablePassiveSlot) {
            if (b) {
                toReturn = true;
                break;
            }
        }
        return toReturn;
    }

    private void removeItem() {
        Player player = Minecraft.getInstance().player;
        player.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
            for (int i = 0; i < removableActiveSlot.length; i++) {
                if (removableActiveSlot[i]) myStuff.removeItem(ItemType.ACTIVE, i);
            }
            for (int i = 0; i < removablePassiveSlot.length; i++) {
                if (removablePassiveSlot[i]) myStuff.removeItem(ItemType.PASSIVE, i);
            }
            ModMessages.sendToServer(new MyStuffSyncC2SPacket(myStuff.getActiveSlots(), myStuff.getPassiveSlots(), myStuff.isActiveUpgraded()));
            Arrays.fill(removableActiveSlot, false);
            Arrays.fill(removablePassiveSlot, false);
        });
        rebuildWidgets();
    }

    private void changeMode() {
        removeMode = !removeMode;
        rebuildWidgets();
    }

    private void makeItemRemovable(ItemType itemType, int slot, boolean removable) {
        if (itemType == ItemType.ACTIVE) {
            if (activeSlot[slot].locked) return;
            removableActiveSlot[slot] = removable;
        }
        else if (itemType == ItemType.PASSIVE) {
            if (passiveSlot[slot].locked) return;
            removablePassiveSlot[slot] = removable;
        }
        rebuildWidgets();
    }

    private void lockItem(ItemType itemType, int slot, boolean locked) {
        Player player = Minecraft.getInstance().player;
        player.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
            if (itemType == ItemType.ACTIVE) myStuff.lockActiveSlot(slot, locked);
            else if (itemType == ItemType.PASSIVE) myStuff.lockPassiveSlot(slot, locked);
            ModMessages.sendToServer(new MyStuffSyncC2SPacket(myStuff.getActiveSlots(), myStuff.getPassiveSlots(), myStuff.isActiveUpgraded()));
        });
        rebuildWidgets();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (pKeyCode == KeyBindings.MY_STUFF_KEY.getKey().getValue()) {
            onClose();
            return true;
        } else if (pKeyCode == KeyBindings.ACTIVE_CHANGE_KEY.getKey().getValue()) {
            Minecraft.getInstance().player.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
                myStuff.changeMainActiveItem();
            });
            ModMessages.sendToServer(new ChangeMainActiveItemC2SPacket());
            rebuildWidgets();
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }
}
