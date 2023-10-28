package net.jwn.jwn_items.item;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public abstract class ModItem extends Item {
    public final ItemType itemType;
    public final int ID;
    public final int quality;

    public ModItem(Properties pProperties, ItemType itemType, int quality, int ID) {
        super(pProperties);
        this.itemType = itemType;
        this.ID = ID;
        this.quality = quality;
    }

    public int getItemID() {
        return ID;
    }

    protected abstract void playSound(Level level, Player player, SoundEvent soundEvent, float volume, float pitch);

    protected abstract void displayMessage(Player pPlayer, Component message);
}
