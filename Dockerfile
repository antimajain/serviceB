FROM openjdk:11
EXPOSE 8081
ADD target/serviceB-0.0.1-SNAPSHOT.jar serviceB-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "serviceB-0.0.1-SNAPSHOT.jar"]