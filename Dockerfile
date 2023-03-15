FROM openjdk:11.0.11-jre

# Install Dapr
#RUN apt-get update && apt-get install -y curl && \
#    curl -s https://raw.githubusercontent.com/dapr/cli/master/install/install.sh | bash && \
#    chmod +x /usr/src/app/bin/* && \
#    mv /usr/src/app/bin/* /usr/local/bin/ && \
#    dapr init
#
#ADD target/test-java-dapr-kafka-1.0-SNAPSHOT.jar app.jar
#
#COPY ./component/pubsub.yaml /components/your-component.yaml
#
#ENV DAPR_APP_PORT=8889
#ENV DAPR_LOG_LEVEL=debug
#ENV DAPR_CONFIG=/components/your-component.yaml
#
## Expose Dapr port
#EXPOSE $DAPR_APP_PORT

#ENTRYPOINT exec java -Xms2456m -Xmx2456m -jar app.jar
#CMD ["dapr", "run", "--app-id", "myapp", "--app-port", "8889", "java", "-jar", "/app.jar"]
#COPY dapr /dapr
WORKDIR /dapr
RUN apk add --no-cache curl && \
    curl -fsSL -o install.sh https://raw.githubusercontent.com/dapr/cli/master/install/install.sh && \
    chmod +x ./install.sh && \
    ./install.sh && \
    dapr init --log-level debug
CMD ["dapr", "run", "--app-id", "myapp", "--app-port", "8080", "java", "-jar", "build/libs/myapp.jar"]

