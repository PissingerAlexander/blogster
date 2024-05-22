#!/bin/bash

trap "docker compose down; sudo rm -r data/*; exit" SIGINT

docker compose up
