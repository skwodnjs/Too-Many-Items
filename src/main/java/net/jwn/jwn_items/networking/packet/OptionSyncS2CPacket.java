package net.jwn.jwn_items.networking.packet;

import net.jwn.jwn_items.capability.PlayerOptionsProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OptionSyncS2CPacket {
    boolean statHudOption = true;
    boolean[] statHudDetailOptions = {true, true, true, true, true, true, true, true};

    public OptionSyncS2CPacket(boolean statHudOption, boolean[] statHudDetailOptions) {
        this.statHudOption = statHudOption;
        this.statHudDetailOptions = statHudDetailOptions;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(statHudOption);
        for (int i = 0; i < statHudDetailOptions.length; i++) {
            buf.writeBoolean(this.statHudDetailOptions[i]);
        }
    }

    public OptionSyncS2CPacket(FriendlyByteBuf buf) {
        statHudOption = buf.readBoolean();
        for (int i = 0; i < statHudDetailOptions.length; i++) {
            statHudDetailOptions[i] = buf.readBoolean();
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            Player player = Minecraft.getInstance().player;

            player.getCapability(PlayerOptionsProvider.playerOptionsCapability).ifPresent(playerOptions -> {
                playerOptions.set(statHudOption, statHudDetailOptions);
            });
        });
        return true;
    }
}
