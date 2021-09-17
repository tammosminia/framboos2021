#!/bin/bash
set -e
sbt assembly
rsync target/scala-2.13/framboos-fatjar-1.0.jar pi@ananas.local:framboos-fatjar-1.0.jar
#chmod +x autostart.sh
#rsync autostart.sh pi@ananas.local:autostart.sh
