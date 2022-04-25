package ch.skyfy.velocitylib.client;

import ch.skyfy.velocitylib.common.PlayerData;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.nbt.NbtCompound;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static ch.skyfy.velocitylib.VelocityLib.*;

@Environment(EnvType.CLIENT)
public class VelocityLibClient implements ClientModInitializer {

    private final AtomicReference<PlayerData> playerDataAtomicReference = new AtomicReference<>(null);
    private final AtomicBoolean serverAnswer = new AtomicBoolean(false);
    private final AtomicBoolean disconnected = new AtomicBoolean(false);
    private final AtomicBoolean joined = new AtomicBoolean(false);

    @Override
    public void onInitializeClient() {
        System.out.println("VelocityLibClient onInitializeClient()");
        registerReceiver();
    }

    private void registerReceiver() {


        // Server asks the client to teleport the player to another server
        ClientPlayNetworking.registerGlobalReceiver(REQUEST_CLIENT_TO_TELEPORT_PLAYER, (client, handler, buf, responseSender) -> {
            var playerData = buf.readNbt();
            if (playerData == null) return;

            var x = playerData.getDouble("x");
            var y = playerData.getDouble("y");
            var z = playerData.getDouble("z");
            var yaw = playerData.getDouble("yaw");
            var pitch = playerData.getDouble("pitch");
            var dimension = playerData.getString("dimension");
            var serverTargetName = playerData.getString("serverTargetName");
            var serverSourcePort = playerData.getInt("serverSourcePort");

            if (client.player == null) return;

            if (playerDataAtomicReference.get() != null) return;

            playerDataAtomicReference.set(new PlayerData(x, y, z, yaw, pitch, dimension, serverSourcePort));

            // We just fire a command that will be intercepted and executed by the velocity proxy server
            client.player.sendChatMessage("/sw " + client.player.getEntityName() + " " + serverTargetName);
        });


        ClientPlayNetworking.registerGlobalReceiver(REQUEST_CLIENT_TO_SEND_PLAYER_DATA, (client, handler, buf, responseSender) -> {

            // If the player connected to the server for the first time
            if (playerDataAtomicReference.get() == null) {
                client.execute(() -> {
                    ClientPlayNetworking.send(REQUEST_SERVER_DATA_HAS_ARRIVED, PacketByteBufs.empty());
                });
                return;
            }

            var playerData = playerDataAtomicReference.get();
            var playerDataNBT = new NbtCompound();
            playerDataNBT.putDouble("x", playerData.x());
            playerDataNBT.putDouble("y", playerData.y());
            playerDataNBT.putDouble("z", playerData.z());
            playerDataNBT.putDouble("yaw", playerData.yaw());
            playerDataNBT.putDouble("pitch", playerData.pitch());
            playerDataNBT.putString("dimension", playerData.dimension());

            client.execute(() -> {
                ClientPlayNetworking.send(REQUEST_SERVER_DATA_HAS_ARRIVED, PacketByteBufs.create().writeNbt(playerDataNBT));
            });

            playerDataAtomicReference.set(null);

        });
    }

}
