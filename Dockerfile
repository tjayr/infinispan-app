FROM openjdk:17-jdk-alpine

ENV JAVA_OPTS="-Xms300m -Xmx300m"

ARG ARTIFACT_PATH=target/infinispan-cluster-app-0.0.1-SNAPSHOT.jar

RUN mkdir -p /opt/infinispan-cluster-app

COPY ${ARTIFACT_PATH} /opt/infinispan-cluster-app

ADD docker-entrypoint.sh .

RUN chmod 0755 docker-entrypoint.sh

ENTRYPOINT ./docker-entrypoint.sh "$JAVA_OPTS"

