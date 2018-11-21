#!/usr/bin/env bash

docker run --name sb2r-postgres -e POSTGRES_USER=customers -e POSTGRES_PASSWORD=customers -e POSTGRES_DB=customers -p 5432:5432 -d postgres:alpine
