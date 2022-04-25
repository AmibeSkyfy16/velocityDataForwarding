package ch.skyfy.switchplayer;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public final class PlayerInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 0L;
    private final String thingDoTo;
    private final String playerName;
    private final String targetServerName;
    private final String targetDimensionName;
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    public PlayerInfo(String thingDoTo, String playerName, String targetServerName, String targetDimensionName, double x, double y, double z, float yaw, float pitch) {
        this.thingDoTo = thingDoTo;
        this.playerName = playerName;
        this.targetServerName = targetServerName;
        this.targetDimensionName = targetDimensionName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public String thingDoTo() {
        return thingDoTo;
    }

    public String playerName() {
        return playerName;
    }

    public String targetServerName() {
        return targetServerName;
    }

    public String targetDimensionName() {
        return targetDimensionName;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double z() {
        return z;
    }

    public float yaw() {
        return yaw;
    }

    public float pitch() {
        return pitch;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PlayerInfo) obj;
        return Objects.equals(this.thingDoTo, that.thingDoTo) &&
                Objects.equals(this.playerName, that.playerName) &&
                Objects.equals(this.targetServerName, that.targetServerName) &&
                Objects.equals(this.targetDimensionName, that.targetDimensionName) &&
                Double.doubleToLongBits(this.x) == Double.doubleToLongBits(that.x) &&
                Double.doubleToLongBits(this.y) == Double.doubleToLongBits(that.y) &&
                Double.doubleToLongBits(this.z) == Double.doubleToLongBits(that.z) &&
                Float.floatToIntBits(this.yaw) == Float.floatToIntBits(that.yaw) &&
                Float.floatToIntBits(this.pitch) == Float.floatToIntBits(that.pitch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(thingDoTo, playerName, targetServerName, targetDimensionName, x, y, z, yaw, pitch);
    }

    @Override
    public String toString() {
        return "PlayerInfo[" +
                "thingDoTo=" + thingDoTo + ", " +
                "playerName=" + playerName + ", " +
                "targetServerName=" + targetServerName + ", " +
                "targetDimensionName=" + targetDimensionName + ", " +
                "x=" + x + ", " +
                "y=" + y + ", " +
                "z=" + z + ", " +
                "yaw=" + yaw + ", " +
                "pitch=" + pitch + ']';
    }

}
