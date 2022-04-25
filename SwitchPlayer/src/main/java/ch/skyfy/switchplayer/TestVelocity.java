package ch.skyfy.switchplayer;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;

@Plugin(id = "switchplayer", name = "Switch Player", version = "1.0.0-SNAPSHOT", description = "Add a command to switch a player to another server", authors = {"Skyfy16"})
public class TestVelocity {

    public static final ChannelIdentifier TEST_CHANNEL = MinecraftChannelIdentifier.create("test", "a_test");

    private final ProxyServer server;

    @Inject
    public TestVelocity(ProxyServer server) {
        this.server = server;

        System.out.println("listing servers");
        for (var allServer : server.getAllServers()) {
            System.out.println("server: getName " + allServer.getServerInfo().getName());
            System.out.println("server: getPort " + allServer.getServerInfo().getAddress().getPort());
        }
    }


    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {

        server.getChannelRegistrar().register(TEST_CHANNEL);

        server.getEventManager().register(this, new MyListener(server));


//        CommandMeta meta = server.getCommandManager().metaBuilder("switchPlayer").aliases("sw").build();
//        server.getCommandManager().register(meta, new SwitchPlayerTestCommand(server));
    }

}
