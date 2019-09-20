FROM openjdk:8
ADD target/springboot-neo4j.jar springboot-neo4j.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "springboot-neo4j.jar"]