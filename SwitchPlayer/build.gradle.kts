repositories {
    maven("https://nexus.velocitypowered.com/repository/maven-public/"){
        name = "velocity"
    }
}

group = "ch.skyfy"
version = "1.0-SNAPSHOT"

dependencies {
    implementation("com.velocitypowered:velocity-api:3.0.1")
    annotationProcessor("com.velocitypowered:velocity-api:3.0.1")

    implementation("cloud.commandframework:cloud-velocity:1.6.2")
    implementation("cloud.commandframework:cloud-minecraft-extras:1.6.2")
}

tasks {
    build{
        doLast{
            copy {
                from(".\\build\\libs\\SwitchPlayer-1.0-SNAPSHOT.jar")
                into("E:\\Tech\\Projects\\MC\\MultiFabricServers\\velocity\\plugins")
            }
        }
    }
}