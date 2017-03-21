#!/bin/bash
docker run --name play-db -e POSTGRES_USER=play -e POSTGRES_PASSWORD=play -p "5433:5432" -d postgres
