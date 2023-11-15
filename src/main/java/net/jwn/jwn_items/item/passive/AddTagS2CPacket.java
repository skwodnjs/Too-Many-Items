package net.jwn.jwn_items.item.passive;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AddTagS2CPacket {
    int pId;
    String tag;

    public AddTagS2CPacket(int pId, String tag) {
        this.pId = pId;
        this.tag = tag;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(pId);
        buf.writeUtf(tag);
    }

    public AddTagS2CPacket(FriendlyByteBuf buf) {
        pId = buf.readInt();
        tag = buf.readUtf();
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!
            Minecraft.getInstance().level.getEntity(pId).addTag(tag);
        });
        return true;
    }
}
