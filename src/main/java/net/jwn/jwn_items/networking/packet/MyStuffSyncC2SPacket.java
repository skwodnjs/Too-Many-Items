package net.jwn.jwn_items.networking.packet;

import net.jwn.jwn_items.capability.MyStuffProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static net.jwn.jwn_items.util.Options.ACTIVE_MAX_UPGRADE;
import static net.jwn.jwn_items.util.Options.PASSIVE_MAX;

public class MyStuffSyncC2SPacket {
    int[] myStuffForActive = new int[ACTIVE_MAX_UPGRADE];
    int[] myStuffForPassive = new int[PASSIVE_MAX];
    boolean[] activeLock = new boolean[ACTIVE_MAX_UPGRADE];
    boolean[] passiveLock = new boolean[PASSIVE_MAX];
    boolean activeLimit = true;

    public MyStuffSyncC2SPacket(int[] myStuffForActive, int[] myStuffForPassive, boolean[] activeLock, boolean[] passiveLock, boolean activeLimit) {
        this.myStuffForActive = myStuffForActive;
        this.myStuffForPassive = myStuffForPassive;
        this.activeLock = activeLock;
        this.passiveLock = passiveLock;
        this.activeLimit = activeLimit;
    }

    public void toBytes(FriendlyByteBuf buf) {
        for (int i = 0; i < myStuffForActive.length; i++) {
            buf.writeInt(myStuffForActive[i]);
        }
        for (int i = 0; i < myStuffForPassive.length; i++) {
            buf.writeInt(myStuffForPassive[i]);
        }
        for (int i = 0; i < activeLock.length; i++) {
            buf.writeBoolean(activeLock[i]);
        }
        for (int i = 0; i < passiveLock.length; i++) {
            buf.writeBoolean(passiveLock[i]);
        }
        buf.writeBoolean(activeLimit);
    }

    public MyStuffSyncC2SPacket(FriendlyByteBuf buf) {
        for (int i = 0; i < myStuffForActive.length; i++) {
            myStuffForActive[i] = buf.readInt();
        }
        for (int i = 0; i < myStuffForPassive.length; i++) {
            myStuffForPassive[i] = buf.readInt();
        }
        for (int i = 0; i < activeLock.length; i++) {
            activeLock[i] = buf.readBoolean();
        }
        for (int i = 0; i < passiveLock.length; i++) {
            passiveLock[i] = buf.readBoolean();
        }
        activeLimit = buf.readBoolean();
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();

            player.getCapability(MyStuffProvider.myStuffCapability).ifPresent(myStuff -> {
                myStuff.set(myStuffForActive, myStuffForPassive, activeLock, passiveLock, activeLimit);
            });
        });
        return true;
    }
}
