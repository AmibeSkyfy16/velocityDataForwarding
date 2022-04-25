package ch.skyfy.switchplayer;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.slf4j.Logger;

import java.nio.file.Path;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;
import static com.mojang.brigadier.builder.RequiredArgumentBuilder.argument;

@Plugin(id = "myfirstplugin", name = "My First Plugin waee", version = "0.1.0-SNAPSHOT",
        url = "https://example.org", description = "I did it!", authors = {"Me"})
public class TestVelocity {

    @Inject
    private Injector injector;

    private final ProxyServer server;
    private final Logger logger;
    private final Path dataDirectory;

    @Inject
    public TestVelocity(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;

        for (RegisteredServer allServer : server.getAllServers()) {
            System.out.println("server: getName " + allServer.getServerInfo().getName());
            System.out.println("server: getPort " + allServer.getServerInfo().getAddress().getPort());
        }

//        CommandMeta meta =  server.getCommandManager().metaBuilder("test").aliases("otherAlias", "anotherAlias").build();

//        server.getCommandManager().register(meta, new TestCommand(server));

        logger.info("Hello there! I made my first plugin with Velocity.");
    }

    public BrigadierCommand createBrigadierCommand() {
        LiteralCommandNode<CommandSource> helloNode = LiteralArgumentBuilder
                .<CommandSource>literal("test")
                .executes(context -> {
                    Component message = Component.text("Hello World", NamedTextColor.AQUA);
                    context.getSource().sendMessage(message);
                    return 1; // indicates success
                })
                .build();

        // BrigadierCommand implements Command
        BrigadierCommand command = new BrigadierCommand(helloNode);
        return command;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        var node = LiteralArgumentBuilder.<CommandSource>literal("switchPlayer")
                .then(argument("playerName", StringArgumentType.string()))
//                .then(argument("serverTarget", StringArgumentType.greedyString()))
//                .then(argument("posX", DoubleArgumentType.doubleArg()))
//                .then(argument("posY", DoubleArgumentType.doubleArg()))
//                .then(argument("posZ", DoubleArgumentType.doubleArg()))
//                .then(argument("yaw", DoubleArgumentType.doubleArg()))
//                .then(argument("pitch", DoubleArgumentType.doubleArg()))
                .executes(context -> {
                    System.out.println("COMMAND switchPlayer EXECUTED");
                    context.getArguments().forEach((s, commandSourceParsedArgument) -> {
                        System.out.println("arg: " + s);
                        System.out.println("value: " + commandSourceParsedArgument.toString());
                        System.out.println("value class: " + commandSourceParsedArgument.getClass().getName());
                    });
                    return 1;
                }).build();

        server.getCommandManager().register(new BrigadierCommand(node));
    }

//    @Subscribe
//    public void onProxyInitialization(ProxyInitializeEvent event) {
//        // Do some operation demanding access to the Velocity API here.
//        // For instance, we could register an event:
//
//        // Allow autocompletion regardless of capitalisation
////        injector.getInstance(Key.get(new TypeLiteral<VelocityCommandManager<CommandSource>>() {
////        })).setCommandSuggestionProcessor(new CloudSuggestionProcessor());
//
////        injector.getInstance(MessageCommand.class);
//
//        CommandMeta meta =  server.getCommandManager().metaBuilder("test").aliases("otherAlias", "anotherAlias").build();
//        CommandMeta meta2 =  server.getCommandManager().metaBuilder("kick").aliases("e", "ee").build();
//
////        server.getCommandManager().register(meta, new TestCommand(server));
////        server.getCommandManager().register(meta2, new CommandKick(server));
//
//
//
//        server.getEventManager().register(this, new PluginListener());
//    }

    @Subscribe
    public void postJoinEvent(ServerPostConnectEvent event) {
//        String uuid = event.getPlayer().getUniqueId().toString();

//        String[] val = PlayerDestiMap.get(uuid);
//
//        if (val != null) {
//            // key: UUID (string)
//            // value: [0] targetServer, [1] targetDestination, [2] onlineUUID
//
//            event.getPlayer().getCurrentServer().ifPresent(serverConnection -> {
//
//                if (serverConnection.getServerInfo().getName().equalsIgnoreCase(val[0])) {
//
//                    ByteArrayDataOutput out = ByteStreams.newDataOutput();
//
//                    out.writeUTF(BungeeMessages.SERVER_DESTI);
//                    out.writeUTF(val[1]);
//                    out.writeUTF(val[2]);
//
//                    serverConnection.sendPluginMessage(AP_CHANNEL, out.toByteArray());
//
//                }
//            });
//        }
    }

}
