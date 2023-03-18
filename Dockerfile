FROM openjdk:11.0.11-jre
ADD target/java-dapr-kafka.jar app.jar
ENTRYPOINT exec java -Xms2456m -Xmx2456m -jar app.jar