FROM openjdk:17-alpine
LABEL maintener="@ngmartinezs"
LABEL description="Proyecto de microservicios con Spring Boot para simular la autenticación de usuarios."
LABEL version="1.0.0"

#ENV JAVA_HOME=/opt/java/openjdk
#ENV PATH="$JAVA_HOME/bin"

COPY /target/ms-datosPrincipales-java-0.0.1-SNAPSHOT.jar /opt/java/ms-datosPrincipales-java-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "/opt/java/ms-datosPrincipales-java-0.0.1-SNAPSHOT.jar"]

