FROM openjdk
WORKDIR /app
COPY /build/libs/*0.0.1-SNAPSHOT.jar /app/forum.jar
ENTRYPOINT ["java", "-jar", "forum.jar"]