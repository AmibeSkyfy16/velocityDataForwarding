@echo off
title [FabricServer1]

:loop

java -Xms2G -Xmx2G -jar fabric-server-launch.jar -nogui

if "%1"=="stop" goto end

timeout /t 20 /nobreak

goto loop
:end
exit
