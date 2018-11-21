#!/usr/bin/env bash

docker run -t -i -p 3025:3025 -p 3110:3110 -p 3143:3143 -p 3465:3465 -p 3993:3993 -p 3995:3995 -d greenmail/standalone:1.5.7
