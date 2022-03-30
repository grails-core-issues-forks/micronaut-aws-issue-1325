#!/bin/bash
set -e
EXIT_STATUS=0

./gradlew :function:shadowJar
./gradlew :app:shadowJar
cd cdk
cdk synth
cdk deploy
cd ..

exit $EXIT_STATUS

