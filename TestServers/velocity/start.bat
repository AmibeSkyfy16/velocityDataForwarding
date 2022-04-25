@echo off
title Velocity
java -Xms512M -Xmx512M -XX:+UseG1GC -XX:G1HeapRegionSize=4M -XX:+UnlockExperimentalVMOptions -XX:+ParallelRefProcEnabled -XX:+AlwaysPreTouch -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -jar velocity-3.1.2-SNAPSHOT-136.jar
pause