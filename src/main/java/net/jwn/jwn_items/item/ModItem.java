package net.jwn.jwn_items.item;

import net.jwn.jwn_items.event.custom.ModItemUsedEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;

public abstract class ModItem extends Item {
    public final ItemType itemType;
    public final int ID;

    public ModItem(Properties pProperties, ItemType itemType, int ID) {
        super(pProperties);
        this.itemType = itemType;
        this.ID = ID;
    }

    protected abstract void playSound(Level level, Player player, SoundEvent soundEvent, float volume, float pitch);

    protected abstract void displayMessage(Player pPlayer, Component message);
}
