#!/bin/sh
IMAGE=combo-service
VERSION=live-1.0.0
REGISTRY=prod-docker-registry.lienvietpostbank.com.vn/esb-ms

mvn clean install spring-boot:repackage -DskipTests -pl ${IMAGE}
docker build -t ${IMAGE} -f dockerfile/Dockerfile-${IMAGE} .
docker tag ${IMAGE}:latest ${REGISTRY}/${IMAGE}:${VERSION}
docker push ${REGISTRY}/${IMAGE}:${VERSION}
