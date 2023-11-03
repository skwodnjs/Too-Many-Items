package net.jwn.jwn_items.item.active;

import net.jwn.jwn_items.item.ActiveItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gameevent.GameEvent;

public class ChargedTNT extends ActiveItem {
    public ChargedTNT(Properties pProperties, int id, int quality, int coolTimeDefault, int chargeStack, int levelWeight) {
        super(pProperties, id, quality, coolTimeDefault, chargeStack, levelWeight);
    }

    public static void operateServer(Player player) {
        PrimedTnt primedtnt = new PrimedTnt(player.level(), player.position().x + 0.5D, player.position().y, player.position().z + 0.5D, player);
        player.level().addFreshEntity(primedtnt);
        player.level().playSound((Player)null, primedtnt.getX(), primedtnt.getY(), primedtnt.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
        player.level().gameEvent(player, GameEvent.PRIME_FUSE, player.position());
    }
}
