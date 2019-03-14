FROM maven as builder

COPY . .

RUN ls -lrt
RUN mvn clean install -B -s mvn_settings_external.xml

FROM predixedge/predix-edge-java-jre-1-11:1.0.4


LABEL maintainer="Predix Edge Application Services"
LABEL hub="https://hub.docker.com"
LABEL org="https://hub.docker.com/u/predixedge"
LABEL repo="predix-edge-sample-scaler-java"
LABEL version="1.0.39"
LABEL support="https://forum.predix.io"
LABEL license="https://github.com/PredixDev/predix-docker-samples/blob/master/LICENSE.md"

WORKDIR /usr/src/predix-edge-sample-scaler-java
COPY scripts/entrypoint.sh .
COPY --from=builder target/predix-edge-sample-scaler-java-*-jar-with-dependencies.jar .

#CMD ["java",  "-XX:NativeMemoryTracking=summary", "-XX:+UseContainerSupport", "-jar", "predix-edge-sample-scaler-java-1.0.23-SNAPSHOT-jar-with-dependencies.jar"]
ENTRYPOINT ["/usr/src/predix-edge-sample-scaler-java/entrypoint.sh"]
