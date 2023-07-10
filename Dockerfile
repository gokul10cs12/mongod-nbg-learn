FROM openjdk:11
LABEL authors="gokul.nb"
EXPOSE 8080
COPY ./target/mongod-nbg-learn-*-SNAPSHOT.jar learn.jar
ENTRYPOINT ["java", "-jar", "/learn.jar"]