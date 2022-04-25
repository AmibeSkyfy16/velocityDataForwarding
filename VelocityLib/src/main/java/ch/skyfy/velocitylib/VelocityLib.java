package ch.skyfy.velocitylib;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VelocityLib implements ModInitializer {

    public static final String MOD_ID = "velocity_lib";

    public static final Identifier REQUEST_CLIENT_TO_TELEPORT_PLAYER = new Identifier(MOD_ID, "teleport_player");
    public static final Identifier REQUEST_CLIENT_TO_SEND_PLAYER_DATA = new Identifier(MOD_ID, "send_player_data");

    public static final Identifier REQUEST_SERVER_DATA_HAS_ARRIVED = new Identifier(MOD_ID, "player_data_has_arrived");

    @SuppressWarnings("unused")
    public static final Logger LOGGER = LogManager.getLogger();


    @Override
    public void onInitialize() {
        System.out.println("VelocityLib onInitialize()");
    }
}
