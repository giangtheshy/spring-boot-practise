FROM openjdk:11

LABEL version="1.0"
LABEL description="Spring Boot Application"

ADD target/*.jar app.jar
#ENTRYPOINT ["java","-jar","app.jar"]
CMD java -Xdebug  -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005 -Djava.security.egd=file:/dev/./urandom -jar app.jar
