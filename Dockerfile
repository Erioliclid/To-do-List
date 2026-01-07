FROM openjdk:26-ea-17-jdk-slim
WORKDIR /app
COPY target/To-do-List-0.0.1-SNAPSHOT.jar /app/to_do_list.jar
ENTRYPOINT ["java", "-jar", "to_do_list.jar"]

