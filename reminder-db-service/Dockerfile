FROM openjdk:8
ADD target/reminders-mysql-service.jar reminders-mysql-service.jar
EXPOSE 9010
ENTRYPOINT ["java" , "-jar", "reminders-mysql-service.jar"]