#!/bin/bash

cd "$(dirname "$0")"
#./gradlew linkDebugExecutableUikitX64 -i
./gradlew iosDeployIPhone13Debug -i
