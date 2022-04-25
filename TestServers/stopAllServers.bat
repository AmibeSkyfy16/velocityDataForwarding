title Stopping all servers
@echo off

Taskkill /FI "WINDOWTITLE eq [FabricServer1]"
Taskkill /FI "WINDOWTITLE eq [FabricServer2]"
Taskkill /FI "WINDOWTITLE eq velocity"

exit