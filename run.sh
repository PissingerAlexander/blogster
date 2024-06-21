#!/bin/bash

trap "docker compose down; sudo rm -r database/data/*; docker rmi software_development-frontend software_development-backend; exit" SIGINT

docker compose up
