package net.jwn.jwn_items.gui;

import net.jwn.jwn_items.Main;
import net.jwn.jwn_items.capability.CoolTimeProvider;
import net.jwn.jwn_items.capability.MyStuffProvider;
import net.jwn.jwn_items.capability.PlayerStatProvider;
import net.jwn.jwn_items.item.*;
import net.jwn.jwn_items.util.ModSlot;
import net.jwn.jwn_items.networking.ModMessages;
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
import static net.jwn.jwn_items.capability.MyStuff.*;

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
    private float[] stats;

    private int leftCoolTimeSecond;
    private int chargedStack;
    private int maxCharged;

    private static final ResourceLocation BACKGROUND_RESOURCE = new ResourceLocation(Main.MOD_ID, "textures/gui/screen.png");

    private void setting() {
        Player player = Minecraft.getInstance().player;
        player.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
            activeSlot = myStuff.getActiveSlots();
            passiveSlot = myStuff.getPassiveSlots();
        });
        player.getCapability(PlayerStatProvider.playerStatsCapability).ifPresent(playerStats -> {
            stats = playerStats.get();
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

        health = new ImageWidget(leftPos + 7, topPos + 54, 16, 16, HEALTH_RESOURCE);
        damage = new ImageWidget(leftPos + 29, topPos + 54, 16, 16, DAMAGE_RESOURCE);
        attack_speed = new ImageWidget(leftPos + 49, topPos + 54, 16, 16, ATTACK_SPEED_RESOURCE);
        attack_range = new ImageWidget(leftPos + 70, topPos + 54, 16, 16, ATTACK_RANGE_RESOURCE);
        mining_speed = new ImageWidget(leftPos + 90, topPos + 54, 16, 16, MINING_SPEED_RESOURCE);
        movement_speed = new ImageWidget(leftPos + 111, topPos + 54, 16, 16, MOVEMENT_SPEED_RESOURCE);
        luck = new ImageWidget(leftPos + 131, topPos + 54, 16, 16, LUCK_RESOURCE);
        coin = new ImageWidget(leftPos + 152, topPos + 54, 16, 16, COIN_RESOURCE);

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

        mode = new ImageButton(leftPos + 132, topPos + 19, 16, 16, (removeMode) ? (isRemovableItemExists()) ? 59 : 43 : 27, 166, 0,
                BACKGROUND_RESOURCE, 256, 256, pButton -> {
            if (isRemovableItemExists()) removeItem();
            else changeMode();
        });
        addRenderableWidget(mode);

        for (int i = 0; i < activeSlot.length; i++) {
            if (activeSlot[i].itemId != 0) {
                drawSlot(leftPos + 8 + 25 * i, topPos + 18, ItemType.ACTIVE, i);
            }
        }

        for (int i = 0; i < passiveSlot.length; i++) {
            if (passiveSlot[i].itemId != 0) {
                drawSlot(leftPos + 8 + 18 * (i % 9), topPos + 84 + 29 * (i / 9), ItemType.PASSIVE, i);
            }
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        pGuiGraphics.blit(BACKGROUND_RESOURCE, leftPos, topPos, 0, 0, 176, 166);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        String title = I18n.get("title." + Main.MOD_ID + ".my_stuff");
        pGuiGraphics.drawString(font, title, leftPos + 7, topPos + 7, 0x404040, false);

        Player player = Minecraft.getInstance().player;
        player.getCapability(CoolTimeProvider.coolTimeCapability).ifPresent(coolTime -> {
            if (activeSlot[0].itemId != 0) {
                ActiveItem activeItem = (ActiveItem) ModItemProvider.getItemById(activeSlot[0].itemId);
                maxCharged = activeItem.getChargeStack();
                leftCoolTimeSecond = (coolTime.get() == 0) ? 0 :
                        (coolTime.get() / 20) % (activeItem.getCoolTime(activeSlot[0].level) / 20) + 1;
                chargedStack = (coolTime.get() == 0) ? maxCharged :
                        maxCharged - 1 - ((coolTime.get() - 1) / 20) / (activeItem.getCoolTime(activeSlot[0].level) / 20);
            }
        });
        pGuiGraphics.drawString(font, chargedStack + "/"  + maxCharged + "  " + leftCoolTimeSecond + " sec left", leftPos + 7, topPos + 42, 0x404040, false);
    }

    public void drawSlot(int pX, int pY, ItemType itemType, int slot) {
        boolean locked = (itemType == ItemType.ACTIVE) ? activeSlot[slot].locked : passiveSlot[slot].locked;
        boolean isRemovable = (itemType == ItemType.ACTIVE) ? removableActiveSlot[slot] : removablePassiveSlot[slot];
        ModItem item = ModItemProvider.getItemById((itemType == ItemType.ACTIVE) ? activeSlot[slot].itemId : passiveSlot[slot].itemId);
        ResourceLocation itemResourceLocation = new ResourceLocation(Main.MOD_ID, "textures/item/" + item + ".png");
        if (isRemovable) {
            ImageButton removableSign = new ImageButton(pX - 1, pY - 1, 18, 18, 9, 166, 0,
                    BACKGROUND_RESOURCE, 256, 256, pButton -> makeItemRemovable(item.itemType, slot, false));
            addRenderableWidget(removableSign);
        }
        ImageButton imageButton;
        imageButton = new ImageButton(pX, pY, 16, 16, 0, 0, 0, itemResourceLocation, 16, 16,
                (removeMode) ? pButton -> makeItemRemovable(itemType, slot, !isRemovable) : pButton -> lockItem(itemType, slot, !locked));
        imageButton.setTooltip(Tooltip.create(Component.translatable("item." + Main.MOD_ID + "." + item)));
        addRenderableWidget(imageButton);
        if (locked) {
            ImageButton lock = new ImageButton(pX + 10, pY + 10, 7, 7, 2, 166, 0,
                    BACKGROUND_RESOURCE, 256, 256, pButton -> lockItem(itemType, slot, false));
            addRenderableWidget(lock);
        }
        int level = (itemType == ItemType.ACTIVE) ? activeSlot[slot].level : passiveSlot[slot].level;
        for (int i = 0; i < level; i++) {
            ImageButton levelButton = new ImageButton(pX + 1 + 3 * i, pY + 19, 2, 2, 0, 166, 0, BACKGROUND_RESOURCE, 256, 256, pButton -> {});
            addRenderableWidget(levelButton);
        }
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
            for (int i = 1; i <= removableActiveSlot.length; i++) {
                if (removableActiveSlot[removableActiveSlot.length - i]) myStuff.removeItem(ItemType.ACTIVE, removableActiveSlot.length - i);
            }
            for (int i = 1; i <= removablePassiveSlot.length; i++) {
                if (removablePassiveSlot[removablePassiveSlot.length- i]) myStuff.removeItem(ItemType.PASSIVE, removablePassiveSlot.length - i);
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
                ModMessages.sendToServer(new MyStuffSyncC2SPacket(myStuff.getActiveSlots(), myStuff.getPassiveSlots(), myStuff.isActiveUpgraded()));
            });
            rebuildWidgets();
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }
}
