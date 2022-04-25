@file:Suppress("GradlePackageVersionRange")

plugins {
    id("fabric-loom") version "0.11-SNAPSHOT"
}

val archivesBaseName = property("archives_base_name")
group = property("maven_group")!!
version = property("mod_version")!!

dependencies {
    minecraft("com.mojang:minecraft:${properties["minecraft_version"]}")
    mappings("net.fabricmc:yarn:${properties["yarn_mappings"]}:v2")

    modImplementation("net.fabricmc:fabric-loader:${properties["loader_version"]}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${properties["fabric_version"]}")

    implementation("org.apache.commons:commons-lang3:3.12.0")
}

tasks {

    processResources {
        inputs.property("version", project.version)
        filteringCharset = "UTF-8"
        filesMatching("fabric.mod.json") {
            expand(mutableMapOf("version" to project.version))
        }
    }

    named<Jar>("jar") {
        from("LICENSE") {
            rename {
                "${it}_${archivesBaseName}"
            }
        }
    }

    named<DefaultTask>("build") {
        println("build started")
    }

    create("copyJarToServer") {
        mustRunAfter("build")
        listOf(project.property("server1") as String, project.property("server2") as String).forEach {
            copyFile(it)
        }
    }

    create<Exec>("restartAllServer") {
        doFirst {
            println("Starting server1")
            workingDir = file("..\\TestServers")
            commandLine = listOf("cmd", "/C", "start", "restartAllServers.bat")
        }
    }

    create<Exec>("stopAllServers") {
        doFirst {
            println("Starting server1")
            workingDir = file("..\\TestServers")
            commandLine = listOf("cmd", "/C", "start", "stopAllServers.bat")
        }
    }
}

java {
    withSourcesJar()
}

fun copyFile(path: String) {
    println("path: $path")
    copy {
        from(".\\build\\libs\\velocitylib-1.0-SNAPSHOT.jar")
        into(path)
    }
}