package net.jwn.jwn_items.gui;

import net.jwn.jwn_items.Main;
import net.jwn.jwn_items.capability.MyStuffProvider;
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

import static net.jwn.jwn_items.util.Options.*;

public class MyStuffScreen extends Screen {
    public MyStuffScreen() {
        super(Component.literal("My Stuff"));
    }

    private int leftPos, topPos;
    private int[] myStuffForActive;
    private int[] myStuffForPassive;
    private boolean[] activeLock;
    private boolean[] passiveLock;
    private boolean[] removableActiveSlot = new boolean[ACTIVE_MAX_UPGRADE];
    private boolean[] removablePassiveSlot = new boolean[PASSIVE_MAX];
    private boolean removeMode = false;

    private boolean activeLimit;

    private static final ResourceLocation BACKGROUND_RESOURCE = new ResourceLocation(Main.MOD_ID, "textures/gui/my_stuff.png");
    private static final ResourceLocation BACKGROUND_UPGRADE_RESOURCE = new ResourceLocation(Main.MOD_ID, "textures/gui/my_stuff_upgrade.png");
    private static final ResourceLocation DAMAGE_RESOURCE = new ResourceLocation(Main.MOD_ID, "textures/gui/damage.png");
    private static final ResourceLocation NO_IMAGE_RESOURCE = new ResourceLocation(Main.MOD_ID, "textures/item/no_image.png");

    private void setMyStuff() {
        Player player = Minecraft.getInstance().player;
        player.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
            myStuffForActive = myStuff.getMyStuffForActive();
            myStuffForPassive = myStuff.getMyStuffForPassive();
            activeLock = myStuff.getActiveLock();
            passiveLock = myStuff.getPassiveLock();
            activeLimit = myStuff.getActiveLimit();
        });
    }

    @Override
    protected void init() {
        super.init();
        setMyStuff();

        this.leftPos = (width - 176) / 2;
        this.topPos = (height - 166) / 2;

        ImageWidget health, damage, attack_speed, attack_range, mining_speed, movement_speed, luck, coin;
        ImageButton mode;

        health = new ImageWidget(leftPos + 13, topPos + 23, 12, 12, NO_IMAGE_RESOURCE);
        damage = new ImageWidget(leftPos + 13, topPos + 36, 12, 12, DAMAGE_RESOURCE);
        attack_speed = new ImageWidget(leftPos + 50, topPos + 23, 12, 12, NO_IMAGE_RESOURCE);
        attack_range = new ImageWidget(leftPos + 50, topPos + 36, 12, 12, NO_IMAGE_RESOURCE);
        mining_speed = new ImageWidget(leftPos + 87, topPos + 23, 12, 12, NO_IMAGE_RESOURCE);
        movement_speed = new ImageWidget(leftPos + 87, topPos + 36, 12, 12, NO_IMAGE_RESOURCE);
        luck = new ImageWidget(leftPos + 124, topPos + 23, 12, 12, NO_IMAGE_RESOURCE);
        coin = new ImageWidget(leftPos + 124, topPos + 36, 12, 12, NO_IMAGE_RESOURCE);

//        health.setTooltip(Tooltip.create(Component.literal("health")));
//        damage.setTooltip(Tooltip.create(Component.literal("damage")));
//        attack_speed.setTooltip(Tooltip.create(Component.literal("attack_speed")));
//        attack_range.setTooltip(Tooltip.create(Component.literal("attack_range")));
//        mining_speed.setTooltip(Tooltip.create(Component.literal("mining_speed")));
//        movement_speed.setTooltip(Tooltip.create(Component.literal("movement_speed")));
//        luck.setTooltip(Tooltip.create(Component.literal("luck")));
//        coin.setTooltip(Tooltip.create(Component.literal("coin")));

        addRenderableWidget(health);
        addRenderableWidget(damage);
        addRenderableWidget(attack_speed);
        addRenderableWidget(attack_range);
        addRenderableWidget(mining_speed);
        addRenderableWidget(movement_speed);
        addRenderableWidget(luck);
        addRenderableWidget(coin);

        mode = new ImageButton(leftPos + 152, topPos + 65, 16, 16, (removeMode) ? (removableItemExists()) ? 93 : 77 : 61, 166, 0,
                BACKGROUND_RESOURCE, 256, 256, pButton -> {
            if (removableItemExists()) removeItem();
            else changeMode();
        });
        addRenderableWidget(mode);

        for (int i = 0; i < myStuffForActive.length; i++) {
            int id = myStuffForActive[i] / 10;
            if (id != 0) {
                int slotIndex = i;
//                ResourceLocation resourceLocation = new ResourceLocation(Main.MOD_ID, "textures/item/no_image.png");
//                ImageButton imageButton;
                if (i == 0) {
                    drawSlot(leftPos + 12, topPos + 61, id, slotIndex, activeLock[slotIndex], removableActiveSlot[slotIndex]);
                } else {
                    drawSlot(leftPos + 34 * i, topPos + 65, id, slotIndex, activeLock[slotIndex], removableActiveSlot[slotIndex]);
//                    imageButton = new ImageButton(leftPos + 34 * i, topPos + 65, 16, 16, 0, 0, 0,
//                            resourceLocation, 16, 16, pButton -> lockItem(ItemType.ACTIVE, slotIndex, !activeLock[slotIndex]));
//                    imageButton.setTooltip(Tooltip.create(Component.translatable("item." + Main.MOD_ID + "." + ModItems.ModItemsProvider.getItemByID(id).toString())));
//                    addRenderableWidget(imageButton);
//                    if (activeLock[slotIndex]) {
//                        ImageButton lock = new ImageButton(leftPos + 34 * i + 10, topPos + 65 + 10, 7, 7, 68, 166, 0,
//                                BACKGROUND_RESOURCE, 256, 256, pButton -> lockItem(ItemType.ACTIVE, slotIndex, !activeLock[slotIndex]));
//                        addRenderableWidget(lock);
//                    }
                }
            }
        }

        for (int i = 0; i < myStuffForPassive.length; i++) {
            int id = myStuffForPassive[i] / 10;
            if (id != 0) {
                int slotIndex = i;
                drawSlot(leftPos + 8 + 18 * (i % 9), topPos + 89 + 24 * (i / 9), id, slotIndex, passiveLock[slotIndex], removablePassiveSlot[slotIndex]);
//                ResourceLocation resourceLocation = new ResourceLocation(Main.MOD_ID, "textures/item/no_image.png");
//                ImageButton imageButton = new ImageButton(leftPos + 8 + 18 * (i % 9), topPos + 89 + 24 * (i / 9), 16, 16, 0, 0, 0,
//                        resourceLocation, 16, 16, pButton -> lockItem(ItemType.PASSIVE, slotIndex, !passiveLock[slotIndex]));
//                imageButton.setTooltip(Tooltip.create(Component.translatable("item." + Main.MOD_ID + "." + ModItems.ModItemsProvider.getItemByID(id).toString())));
//                addRenderableWidget(imageButton);
//                if (passiveLock[slotIndex]) {
//                    ImageButton lock = new ImageButton(leftPos + 8 + 18 * (i % 9) + 10, topPos + 89 + 24 * (i / 9) + 10, 7, 7, 68, 166, 0,
//                            BACKGROUND_RESOURCE, 256, 256, pButton -> lockItem(ItemType.PASSIVE, slotIndex, !passiveLock[slotIndex]));
//                    addRenderableWidget(lock);
//                }
            }
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        pGuiGraphics.blit(activeLimit ? BACKGROUND_RESOURCE : BACKGROUND_UPGRADE_RESOURCE, leftPos, topPos, 0, 0, 176, 166);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        String title = I18n.get("title." + Main.MOD_ID + ".my_stuff");
        pGuiGraphics.drawString(font, title, leftPos + 7, topPos + 7, 0x404040, false);
    }

    public void drawSlot(int pX, int pY, int itemID, int slot, boolean locked, boolean deleted) {
        ModItem item = ModItems.ModItemsProvider.getItemByID(itemID);
        assert item != null;
//        ResourceLocation itemResourceLocation = new ResourceLocation(Main.MOD_ID, "textures/item/" + item + ".png");
        ResourceLocation itemResourceLocation = new ResourceLocation(Main.MOD_ID, "textures/item/no_image.png");
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
    }

    private boolean removableItemExists() {
        if (!removeMode) return false;
        boolean toReturn = false;
        for (int i = 0; i < removableActiveSlot.length; i++) {
            if (removableActiveSlot[i]) toReturn = true;
        }
        for (int i = 0; i < removablePassiveSlot.length; i++) {
            if (removablePassiveSlot[i]) toReturn = true;
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
            ModMessages.sendToServer(new MyStuffSyncC2SPacket(myStuff.getMyStuffForActive(), myStuff.getMyStuffForPassive(),
                    myStuff.getActiveLock(), myStuff.getPassiveLock(), myStuff.getActiveLimit()));
            Arrays.fill(removableActiveSlot, false);
            Arrays.fill(removablePassiveSlot, false);
        });
        rebuildWidgets();
    }
    private void changeMode() {
        removeMode = !removeMode;
        rebuildWidgets();
    }

    private void makeItemRemovable(ItemType itemType, int slot, boolean delete) {
        if (itemType == ItemType.ACTIVE) {
            if (activeLock[slot]) return;
            removableActiveSlot[slot] = delete;
        }
        else if (itemType == ItemType.PASSIVE) {
            if (passiveLock[slot]) return;
            removablePassiveSlot[slot] = delete;
        }
        rebuildWidgets();
    }

    private void lockItem(ItemType itemType, int slot, boolean lock) {
        Player player = Minecraft.getInstance().player;
        player.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
            if (itemType == ItemType.ACTIVE) myStuff.setActiveLock(slot, lock);
            else if (itemType == ItemType.PASSIVE) myStuff.setPassiveLock(slot, lock);
            ModMessages.sendToServer(new MyStuffSyncC2SPacket(myStuff.getMyStuffForActive(), myStuff.getMyStuffForPassive(),
                    myStuff.getActiveLock(), myStuff.getPassiveLock(), myStuff.getActiveLimit()));
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
