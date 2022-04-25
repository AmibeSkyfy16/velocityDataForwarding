title Starting all servers
@echo off

Taskkill /FI "WINDOWTITLE eq [FabricServer1]"
Taskkill /FI "WINDOWTITLE eq [FabricServer2]"
Taskkill /FI "WINDOWTITLE eq velocity"

timeout /t 3 > nul

cd /d .\server1
cmd /C start classicLauncher.bat

cd /d ..\server2
cmd /C start classicLauncher.bat

cd /d ..\velocity
cmd /C start start.bat

exit