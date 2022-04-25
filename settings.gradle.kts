pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/") {
            name = "Fabric"
        }
        gradlePluginPortal()
    }
}

rootProject.name = "velocity-data-forwarding"
//include("switchPlayer", "velocityLib")
include("VelocityLib", "SwitchPlayer")