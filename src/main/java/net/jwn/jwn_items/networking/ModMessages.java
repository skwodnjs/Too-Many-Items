package net.jwn.jwn_items.networking;

import net.jwn.jwn_items.Main;
import net.jwn.jwn_items.networking.packet.StatSyncS2CPacket;
import net.jwn.jwn_items.networking.packet.skills.RocketSkillC2SPacket;
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

        net.messageBuilder(StatSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(StatSyncS2CPacket::new)
                .encoder(StatSyncS2CPacket::toBytes)
                .consumerMainThread(StatSyncS2CPacket::handle)
                .add();

        net.messageBuilder(RocketSkillC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(RocketSkillC2SPacket::new)
                .encoder(RocketSkillC2SPacket::toBytes)
                .consumerMainThread(RocketSkillC2SPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}