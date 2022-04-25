package ch.skyfy.velocitylib.server;

import ch.skyfy.velocitylib.common.PlayerInfo;
import com.google.common.io.ByteStreams;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static ch.skyfy.velocitylib.VelocityLib.*;

@Environment(EnvType.SERVER)
public class VelocityLibDedicatedServer implements DedicatedServerModInitializer {

    @Override
    public void onInitializeServer() {
        System.out.println("VelocityLibDedicatedServer onInitializeServer()");

        ServerPlayNetworking.registerGlobalReceiver(new Identifier("test", "a_test"), (server, player, handler, buf, responseSender) -> {
            System.out.println("is this line printed ?");
            System.out.println("proxy has send a message to server !");
        });

        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            System.out.println("AttackBlockCallback called");

            var thingDoTo = "teleport"; // Here the thing to do is to teleport the player
            var playerName = "Skyfy16";
            var targetServerName = "server2";
            var targetDimensionName = "overworld";
            var x = player.getX() + 10;
            var y = player.getY() + 10;
            var z = player.getZ() + 50;
            var yaw = player.getYaw();
            var pitch = player.getPitch();

            // NOT WORK, CANT READ DATA PROXY SIDE (EOFEXCEPTION)
//            sendAPacketTestOne(player, thingDoTo, playerName, targetServerName, targetDimensionName, x, y, z, yaw, pitch);

            // NOT WORK
            sendAPacketTestTwo(player, thingDoTo, playerName, targetServerName, targetDimensionName, x, y, z, yaw, pitch);

            return ActionResult.PASS;
        });

    }

    private void sendAPacketTestOne(PlayerEntity player, String thingDoTo, String playerName, String targetServerName, String targetDimensionName, double x, double y, double z, float yaw, float pitch) {
        var packet = PacketByteBufs.create();
        packet.writeString(thingDoTo);
        packet.writeString(playerName);
        packet.writeString(targetServerName);
        packet.writeString(targetDimensionName);
        packet.writeDouble(x);
        packet.writeDouble(y);
        packet.writeDouble(z);
        packet.writeFloat(yaw);
        packet.writeFloat(pitch);

        ServerPlayNetworking.send((ServerPlayerEntity) player, new Identifier("test", "a_test"), packet);
    }

    private void sendAPacketTestTwo(PlayerEntity player, String thingDoTo, String playerName, String targetServerName, String targetDimensionName, double x, double y, double z, float yaw, float pitch) {
        try (var baos = new ByteArrayOutputStream();
             var dos = new DataOutputStream(baos)) {

            dos.writeUTF(thingDoTo);
            dos.writeUTF(playerName);
            dos.writeUTF(targetServerName);
            dos.writeUTF(targetDimensionName);
            dos.writeDouble(x);
            dos.writeDouble(y);
            dos.writeDouble(z);
            dos.writeFloat(pitch);
            dos.writeFloat(yaw);

//            dos.flush(); // I found some example using this on internet, but seems that nothing change

            var byteArray = baos.toByteArray();
//            var packet = PacketByteBufs.create().writeByteArray(byteArray);
            var packetByteBuf = PacketByteBufs.create();
            packetByteBuf.writeBytes(byteArray);

            // Try to read data using ByteStreams.newDataInput(), without using PacketByteBufs.create().writeByteArray
            System.out.println("Reading data using ByteStreams.newDataInput() without using PacketByteBufs.create().writeByteArray()");
            System.out.println("byteArray.length: " + byteArray.length);
            System.out.println("printing: new String(byteArray): " + new String(byteArray));
            var dis1 = ByteStreams.newDataInput(byteArray);
            var v1 = dis1.readUTF();
            var v2 = dis1.readUTF();
            System.out.println("v1: " + v1);
            System.out.println("v2: " + v2);

            // another test
            System.out.println("Reading data using ByteStreams.newDataInput() but from PacketByteBufs.create().writeByteArray()");
            System.out.println("packetByteBuf.array().length: " + packetByteBuf.array().length);
            System.out.println("packetByteBuf.readByteArray().length: " + packetByteBuf.readByteArray().length);
            System.out.println("packetByteBuf.getWrittenBytes().length: " + packetByteBuf.getWrittenBytes().length);
            System.out.println("printing: new String(packetByteBuf.array()): " + new String(packetByteBuf.array()));
            System.out.println("printing: new String(packetByteBuf.readByteArray()): " + new String(packetByteBuf.readByteArray()));
            System.out.println("printing: new String(packetByteBuf.getWrittenBytes()): " + new String(packetByteBuf.getWrittenBytes()));
            var dis11 = ByteStreams.newDataInput(packetByteBuf.readByteArray());
            var v11 = dis11.readUTF();
            var v22 = dis11.readUTF();
            System.out.println("v11: " + v11);
            System.out.println("v11: " + v22);


            ServerPlayNetworking.send((ServerPlayerEntity) player, new Identifier("test", "a_test"), packetByteBuf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendAPacketTestThree(PlayerEntity player, String thingDoTo, String playerName, String targetServerName, String targetDimensionName, double x, double y, double z, float yaw, float pitch) {

    }


    private void printDimensionInfos(PlayerEntity player) {
        player.getServer().getWorlds().forEach(serverWorld -> {
            System.out.println("serverWorld.getDimension().getEffects().toString(): " + serverWorld.getDimension().getEffects().toString());
            System.out.println("serverWorld.getDimension().getEffects().getNamespace(): " + serverWorld.getDimension().getEffects().getNamespace());
            System.out.println("serverWorld.getDimension().getEffects().getPath(): " + serverWorld.getDimension().getEffects().getPath());
        });
    }

    /**
     * Request the client to execute the command "switchPlayer <playerName> <serverTargetName>"
     * which will be intercepted and executed by the Velocity proxy server. The player will be teleported to the target server
     * <p>
     * Once this is done, the server will send a request to the client asking for his target coordinates.
     * Once the server has received the coordinates, it can teleport the player to the right place
     */
    public static void teleportPlayer(double x, double y, double z, double yaw, double pitch, String dimension, String serverTargetName, ServerPlayerEntity serverPlayerEntity) {
        var data = new NbtCompound();
        data.putDouble("x", x);
        data.putDouble("y", y);
        data.putDouble("z", z);
        data.putDouble("yaw", yaw);
        data.putDouble("pitch", pitch);
        data.putString("dimension", dimension);
        data.putString("serverTargetName", serverTargetName);
        data.putInt("serverSourcePort", serverPlayerEntity.server.getServerPort());
        ServerPlayNetworking.send(serverPlayerEntity, REQUEST_CLIENT_TO_TELEPORT_PLAYER, PacketByteBufs.create().writeNbt(data));
    }

    // The server asks the client to send the player data
    private void registerEvents() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            server.execute(() -> {
                ServerPlayNetworking.send(handler.player, REQUEST_CLIENT_TO_SEND_PLAYER_DATA, PacketByteBufs.empty());
            });
        });
    }

    private void registerReceiver() {
        ServerPlayNetworking.registerGlobalReceiver(REQUEST_SERVER_DATA_HAS_ARRIVED, (server, player, handler, buf, responseSender) -> {
            try {
                if (buf == null) {
                    System.out.println("buf is null");
                    return;
                }
                if (buf == PacketByteBufs.empty()) {
                    System.out.println("player data is empty, skip");
                    return;
                }
                if (buf.equals(PacketByteBufs.empty())) {
                    System.out.println("player data is empty, skipdddd");
                    return;
                }

                var playerData = buf.readNbt();
                if (playerData == null) return;

                var x = playerData.getDouble("x");
                var y = playerData.getDouble("y");
                var z = playerData.getDouble("z");
                var yaw = playerData.getDouble("yaw");
                var pitch = playerData.getDouble("pitch");
                var dimension = playerData.getString("dimension");

                server.execute(() -> {
                    // find the dimension where the player must be teleported
                    server.getWorlds().forEach(serverWorld -> {
                        System.out.println("serverWorld.getDimension().getEffects().getPath(): " + serverWorld.getDimension().getEffects().getPath());
                        if (serverWorld.getDimension().getEffects().getPath().equalsIgnoreCase(dimension))
                            player.teleport(serverWorld, x, y, z, (float) yaw, (float) pitch);
                    });
                });
            } catch (Exception e) {
                e.printStackTrace();
                // TODO FIX THIS SHIT CODE
            }
        });
    }

}
