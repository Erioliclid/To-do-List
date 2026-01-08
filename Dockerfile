FROM openjdk:26-ea-21-oraclelinux8
WORKDIR /app
COPY target/To-do-List-0.0.1-SNAPSHOT.jar /app/to_do_list.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "to_do_list.jar", "--spring.profiles.active=docker"]

