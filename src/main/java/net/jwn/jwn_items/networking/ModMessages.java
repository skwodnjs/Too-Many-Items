package net.jwn.jwn_items.networking;

import net.jwn.jwn_items.Main;
import net.jwn.jwn_items.item.passive.AddTagS2CPacket;
import net.jwn.jwn_items.networking.packet.*;
import net.jwn.jwn_items.networking.packet.UseSkillC2SPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetID = 0;
    private static int id() {
        return packetID++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Main.MOD_ID, "messages"))
                .networkProtocolVersion(() ->"1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        // sync
        net.messageBuilder(CoolTimeSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(CoolTimeSyncS2CPacket::new)
                .encoder(CoolTimeSyncS2CPacket::toBytes)
                .consumerMainThread(CoolTimeSyncS2CPacket::handle)
                .add();
        net.messageBuilder(CoolTimeSyncC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(CoolTimeSyncC2SPacket::new)
                .encoder(CoolTimeSyncC2SPacket::toBytes)
                .consumerMainThread(CoolTimeSyncC2SPacket::handle)
                .add();
        net.messageBuilder(FoundStuffSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(FoundStuffSyncS2CPacket::new)
                .encoder(FoundStuffSyncS2CPacket::toBytes)
                .consumerMainThread(FoundStuffSyncS2CPacket::handle)
                .add();
        net.messageBuilder(FoundStuffSyncC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(FoundStuffSyncC2SPacket::new)
                .encoder(FoundStuffSyncC2SPacket::toBytes)
                .consumerMainThread(FoundStuffSyncC2SPacket::handle)
                .add();
        net.messageBuilder(MyStuffSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(MyStuffSyncS2CPacket::new)
                .encoder(MyStuffSyncS2CPacket::toBytes)
                .consumerMainThread(MyStuffSyncS2CPacket::handle)
                .add();
        net.messageBuilder(MyStuffSyncC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MyStuffSyncC2SPacket::new)
                .encoder(MyStuffSyncC2SPacket::toBytes)
                .consumerMainThread(MyStuffSyncC2SPacket::handle)
                .add();
        net.messageBuilder(PlayerOptionSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PlayerOptionSyncS2CPacket::new)
                .encoder(PlayerOptionSyncS2CPacket::toBytes)
                .consumerMainThread(PlayerOptionSyncS2CPacket::handle)
                .add();
        net.messageBuilder(PlayerOptionSyncC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PlayerOptionSyncC2SPacket::new)
                .encoder(PlayerOptionSyncC2SPacket::toBytes)
                .consumerMainThread(PlayerOptionSyncC2SPacket::handle)
                .add();
        net.messageBuilder(PlayerStatsSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PlayerStatsSyncS2CPacket::new)
                .encoder(PlayerStatsSyncS2CPacket::toBytes)
                .consumerMainThread(PlayerStatsSyncS2CPacket::handle)
                .add();
        net.messageBuilder(PlayerStatsSyncC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PlayerStatsSyncC2SPacket::new)
                .encoder(PlayerStatsSyncC2SPacket::toBytes)
                .consumerMainThread(PlayerStatsSyncC2SPacket::handle)
                .add();

        // print
        net.messageBuilder(PrintStatC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PrintStatC2SPacket::new)
                .encoder(PrintStatC2SPacket::toBytes)
                .consumerMainThread(PrintStatC2SPacket::handle)
                .add();
        net.messageBuilder(PrintStuffC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PrintStuffC2SPacket::new)
                .encoder(PrintStuffC2SPacket::toBytes)
                .consumerMainThread(PrintStuffC2SPacket::handle)
                .add();
        net.messageBuilder(PrintCoolTimeC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PrintCoolTimeC2SPacket::new)
                .encoder(PrintCoolTimeC2SPacket::toBytes)
                .consumerMainThread(PrintCoolTimeC2SPacket::handle)
                .add();
        net.messageBuilder(PrintFoundStuffC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PrintFoundStuffC2SPacket::new)
                .encoder(PrintFoundStuffC2SPacket::toBytes)
                .consumerMainThread(PrintFoundStuffC2SPacket::handle)
                .add();

        // skill
        net.messageBuilder(UseSkillC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(UseSkillC2SPacket::new)
                .encoder(UseSkillC2SPacket::toBytes)
                .consumerMainThread(UseSkillC2SPacket::handle)
                .add();
        net.messageBuilder(AddTagS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(AddTagS2CPacket::new)
                .encoder(AddTagS2CPacket::toBytes)
                .consumerMainThread(AddTagS2CPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}