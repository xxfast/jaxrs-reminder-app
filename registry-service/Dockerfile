FROM openjdk:8
ADD target/reminders-registry-service.jar reminders-registry-service.jar
EXPOSE 8761
ENTRYPOINT ["java" , "-jar", "reminders-registry-service.jar"]