#!/bin/bash

docker build -t els-course -f els-course/Dockerfile .
docker build -t els-discovery -f els-discovery/Dockerfile .
docker build -t els-enrollment -f els-enrollment/Dockerfile .
docker build -t els-file-manage -f els-file-manage/Dockerfile .
docker build -t els-gateway -f els-gateway/Dockerfile .
docker build -t els-graphql -f els-graphql/Dockerfile .
docker build -t els-playback -f els-playback/Dockerfile .
docker build -t els-user -f els-user/Dockerfile .