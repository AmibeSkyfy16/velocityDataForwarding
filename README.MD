# Description

The goal of this project is to be able to teleport players to specific coordinates and dimensions
between multiple servers using velocity as a proxy server.

This root project contains 3 important folders

    1. SwitchPlayer
        It's the velocity plugin for our proxy server
        His goal is to connect player betweens server and handle player data like coordinate, dimension, etc.

        gradle custom task
            1. When the build task finished, it will automatically copy our plugin to the proxy server under plugins folder

    2. VelocityLib
        It's a Fabric mod, used server side.
        It send and received player data like coordinate to/from server proxy

        gradle custom task
            1. When the build task finished, the task "copyJarToServer" will be fired
               (it just copy .jar to mods folder of our fabric servers)
            2. "restartAllServer" -> start or restart all servers including velocity proxy server
            3. "stopAllServer" -> stop all servers

    3. TestServers
        this folder contains two fabric servers called "server1" and "server2"
        and our velocity proxy server

Another explaination of what im trying to do : https://pastebin.com/8AuCNad9