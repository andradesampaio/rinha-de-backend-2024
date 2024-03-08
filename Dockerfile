FROM openjdk:17-alpine

COPY target/rinha-backend-2024-0.0.1-SNAPSHOT.jar /usr/local/tomcat/webapps/ROOT.jar

#Não fizeram diferença
#ENV JAVA_OPTS="-XX:+UseG1GC -XX:MaxRAMPercentage=60.0 -XX:InitialRAMPercentage=60.0"

EXPOSE 3000

CMD ["java", "-jar", "/usr/local/tomcat/webapps/ROOT.jar"]