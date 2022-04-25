package ch.skyfy.switchplayer;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.ProxyServer;

import java.util.List;

@SuppressWarnings("ClassCanBeRecord")
public class SwitchPlayerTestCommand implements SimpleCommand {

    private final ProxyServer server;

    public SwitchPlayerTestCommand(ProxyServer server) {
        this.server = server;
    }

    @Override
    public void execute(Invocation invocation) {
        var args = invocation.arguments();
        var playerName = args[0];
        var serverTarget = args[1];
        var playerOpt = server.getPlayer(playerName);
        playerOpt.ifPresent(player -> server.getServer(serverTarget).ifPresent(registeredServer -> {
            var con = player.createConnectionRequest(registeredServer);
            con.connect();
        }));
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        return SimpleCommand.super.suggest(invocation);
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return true;
    }
}
