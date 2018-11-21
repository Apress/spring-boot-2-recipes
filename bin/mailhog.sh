#!/usr/bin/env bash
docker pull mailhog/mailhog
docker run --name sbr-mailhog -p 1025:1025 -p 8025:8025 -d mailhog/mailhog