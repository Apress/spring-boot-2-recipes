#!/usr/bin/env bash

find . -name 'pom.xml' -execdir mvn clean verify \;
