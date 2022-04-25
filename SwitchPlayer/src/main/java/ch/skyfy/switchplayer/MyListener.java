package ch.skyfy.switchplayer;

import com.google.common.io.ByteStreams;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static ch.skyfy.switchplayer.TestVelocity.TEST_CHANNEL;

public class MyListener {

    private final ProxyServer server;

    public MyListener(ProxyServer server) {
        this.server = server;
    }

    @Subscribe(order = PostOrder.NORMAL)
    public void onServerConnectedEvent(ServerConnectedEvent serverConnectedEvent) {
        if (serverConnectedEvent.getPreviousServer().isEmpty()) {
            // first connection
            System.out.println("first connection");
        } else {

            System.out.println("player " + serverConnectedEvent.getPlayer().getUsername() +
                    " switch from " + serverConnectedEvent.getPreviousServer().get().getServerInfo().getName() +
                    " to " + serverConnectedEvent.getServer().getServerInfo().getName());

            serverConnectedEvent.getServer().sendPluginMessage(TEST_CHANNEL, new byte[]{1, 2, 1, 2});

        }
    }

    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) {
        var data = event.getData();

        if (data.length == 0) {
            System.out.println("EMPTY DATA");
            return;
        }

//        try(var bais = new ByteArrayInputStream(data); var ois = new ObjectInputStream(bais)) {
//            PlayerInfo playerInfo = (PlayerInfo) ois.readObject();
//            System.out.println("player name: " + playerInfo.playerName());
//        } catch (IOException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }

        // ALL IS SHIT
//        System.out.println("raw data UTF 8 " + new String(data, StandardCharsets.UTF_8));
//        System.out.println("\n");
//        System.out.println("raw data ISO_8859_1 " + new String(data, StandardCharsets.ISO_8859_1));
//        System.out.println("raw data US_ASCII " + new String(data, StandardCharsets.US_ASCII));


//         NOT WORK
        var dis = ByteStreams.newDataInput(event.getData());
        var v1 = dis.readUTF();
        System.out.println("v1: " + v1);

        // NOT WORK
//        try (var dis2 = new DataInputStream(new ByteArrayInputStream(event.getData()))) {
//            var v11 = dis2.read();
//            System.out.println("v1: " + v11);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        // print some infos
        System.out.println("event : " + event.getIdentifier().getId());
        System.out.println("source: " + event.getSource().toString());
    }

    private void readAllByte(){

    }

}
