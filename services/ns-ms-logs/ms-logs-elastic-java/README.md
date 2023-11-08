# Micro servicio ms-login-java
Servicio que permite simular una autenticacion de un usuario.

## Compilacion
A continuacion se relacionan los pasos para efectuarla compilacion y contruccion del proyecto.
```
    1. Compilacion: Ubiquese sobre el contenido del proyecto y ejecute el siguiente comando
        - mvn clean compile

    2. Contruccion: Si el proyecto se compilo correctamente se puede proceder a realizar la construccion del proyecto mediante el siguinte comando
        - mvn  package

    Si la compilacion fue exitosa ubique en la carpeta ./target el jar generado.
```

## Parametros De Ejecucion
El servicio presenta una serie de parametros para su ejecucion, los cuales se listan en seguida y deben ser seteados para su ejecucion.
```
    NAME_COMPONENT= Nombre del servicio
    PORT= Indica el puerto por donde se estaria ejecutando el proyecto
    CORS= Indica la regla de cors que se estaria aplicando
    APPVER= Version generad
    VERDATA= fecha de generacion de la version
    ENV= Entorno que se esta ejecutando
    DATABASE= Nombre de la base de datos
    DATABASEURL= url de la base de datos
    DATABASEUSERNAME= usuario de la base de datos
    DATABASEPASSWORD= password del usuario de la base de datos.
    DATABASEDRIVER= Clase driver de acceso a la base de datos
    DATABASEPOOLSIZE= Pool de connecction a la base de datos
    DATABASETIMEOUT= time de la connection a la base de datos

```

# 1. PASOS PARA LA EJECUCION DEL PROYECTO
Para efectuar la ejecucion del servicio realice la siguiente ejecucion
```

    java -jar  ./target/ms-login-elastic-java-0.0.1-SNAPSHOT.jar --PORT=8080 --CORS=* --APPVER=1.0 --VERDATA=2023 --ENV=dev  --NAME_COMPONENT=ms-login-elastic-java --ELASTICSEARCH_HOST=https://elk-logs-pt.es.westus2.azure.elastic-cloud.com --ELASTICSEARCH_CLUSTER=elk-logs-pt --ELASTICSEARCH_USER=dsapp --ELASTICSEARCH_PASSWORD=12345678
```

## 1.1 Iniciar Componentes de OtelCollector y Backend de Telemetria
```
    docker compose -f ./OtelCollector/docker-compose.yaml up --force-recreate
```

## 1.2 Ejecucion del Proyecto Localmente
Para efectuar la ejecucion del servicio de forma local realice tiene las siguientes opciones

#### 1.2.2 Ejecucion Con Instrumentacion Manual
En este escenario de ejecucion no se tiene ningun agente de se encargue de interceptar o monitorear lo que sucede dentrol del servicios,
si no es el mismo servicio el que realiza la instrumentacion.
```
    Primera instruccion utiliza un collector local

    java -jar ./target/ms-login-java-0.0.1-SNAPSHOT.jar --PORT=8080 --CORS=* --APPVER=1.0 --VERDATA=2023 --ENV=dev --DATABASE=DATABASE --DATABASEURL=DATABASEURL --DATABASEUSERNAME=DATABASEUSERNAME --DATABASEPASSWORD=DATABASEPASSWORD --DATABASEDRIVER=DATABASEDRIVER --DATABASEPOOLSIZE=DATABASEPOOLSIZE --DATABASEPOOLSIZE=DATABASEPOOLSIZE --DATABASETIMEOUT=DATABASETIMEOUT --OTEL_TRACES_EXPORTER=none --OTEL_METRICS_EXPORTER=oltp --OTEL_LOGS_EXPORTER=otlp --OTEL_EXPORTER_OTLP_ENDPOINT=http://127.0.0.1:4317 --OTEL_EXPORTER_OTLP_LOGS_PROTOCOL=http/json --NAME_COMPONENT=ms-login-java

    Segunda instruccion utiliza un collector remoto

    java -jar ./target/ms-login-java-0.0.1-SNAPSHOT.jar --PORT=8080 --CORS=* --APPVER=1.0 --VERDATA=2023 --ENV=dev --DATABASE=DATABASE --DATABASEURL=DATABASEURL --DATABASEUSERNAME=DATABASEUSERNAME --DATABASEPASSWORD=DATABASEPASSWORD --DATABASEDRIVER=DATABASEDRIVER --DATABASEPOOLSIZE=DATABASEPOOLSIZE --DATABASEPOOLSIZE=DATABASEPOOLSIZE --DATABASETIMEOUT=DATABASETIMEOUT --OTEL_TRACES_EXPORTER=none --OTEL_METRICS_EXPORTER=oltp --OTEL_LOGS_EXPORTER=otlp --OTEL_EXPORTER_OTLP_ENDPOINT=https://apiaksngms.net:443 --NAME_COMPONENT=ms-login-java 

```

#### 1.2.3 Ejecucion Con Instrumentacion Automatica Con Agente de Elastic APM
En este escenario de ejecucion se plantea hacer uso del agente de APM de Elastic, el cual se encargara de interceptar las peticiones que se realicen al servicio
```
    java -javaagent:C:/ngmartinezs/Desarrollo/Java/wsLogin/opt/elastic/elastic-apm-agent-1.39.0-javadoc.jar -Delastic.apm.service_name=my-ws -Delastic.apm.server_urls=https://2022e2cc10a94eaa901f591830d5cf96.apm.eastus2.azure.elastic-cloud.com:443 -Delastic.apm.secret_token=IgCOsjV0rQQJ3FCcCu -Delastic.apm.environment=production -Delastic.apm.application_packages=org.example -jar ./target/ms-login-java-0.0.1-SNAPSHOT.jar --PORT=8080 --CORS=* --APPVER=1.0 --VERDATA=2023 --ENV=dev --DATABASE=DATABASE --DATABASEURL=DATABASEURL --DATABASEUSERNAME=DATABASEUSERNAME --DATABASEPASSWORD=DATABASEPASSWORD --DATABASEDRIVER=DATABASEDRIVER --DATABASEPOOLSIZE=DATABASEPOOLSIZE --DATABASEPOOLSIZE=DATABASEPOOLSIZE --DATABASETIMEOUT=DATABASETIMEOUT

```

# 2. PASOS PARA LA EJECUCION DEL PROYECTO EN DOCKER

Para realizar la contenerizacion del servicio y su publicacion en un repositorio de imagenes de docker, se debe realizar los siguientes pasos.

## 2.1 Compilacion y Construccion del Proyecto


#### 2.1.2 Construccion del proyecto de forma remota
```
   En este contexto se plantea que el proyecto se construya en un servidor de integracion continua, el cual se encargara de realizar la compilacion y construccion del proyecto, o en un servidor donder se pueda realizar la compilacion y construccion de  la imagen de docker.


   docker image rm adockercrapisngms.azurecr.io/ms-login-java:1.0.7 -f 
   docker stop maven-openjdkCompileProject 
   docker rm -f maven-openjdkCompileProject &&  docker run -it --name maven-openjdkCompileProject -v .:/home -w /home maven:3.8.5-openjdk-17 mvn clean compile package

   docker image rm acrapisngms.azurecr.io/ms-login-java:1.0.7 -f

   docker build -t acrapisngms.azurecr.io/ms-login-java:1.0.7 --no-cache .
```

# 3 PASOS PARA LA EJECUCION DEL PROYECTO EN KUBERNETES
A continuacion se listan los pasos para la ejecucion del proyecto en kubernetes.

## 3.1 Coneccion al almacen de images
Se parte que las imagen del proyecto ya se encuentra en un almacen de imagenes de docker, por lo cual se debe realizar la coneccion al almacen de imagenes de docker. en este caso las imagenes estan en un ARC de Azure.
```
    az login

    az acr login --name acrapisngms.azurecr.io --expose-token

    docker login acrapisngms.azurecr.io -u 00000000-0000-0000-0000-000000000000 -p eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IkszSUk6VlhERTpTSlZHOjc3QUw6Q0VZTjpYTzZIOlRHWlA6S1hBTDpIWUROOlJKNUc6TjQ2NTo0TkdNIn0.eyJqdGkiOiI1MDdmNDY5Yi1kNzRhLTRmNzYtYjZiNi1lMjdkODBmMzdiNWQiLCJzdWIiOiJsaXZlLmNvbSNuZ21hcnRpbmV6c0BnbWFpbC5jb20iLCJuYmYiOjE2OTI5NzA2ODYsImV4cCI6MTY5Mjk4MjM4NiwiaWF0IjoxNjkyOTcwNjg2LCJpc3MiOiJBenVyZSBDb250YWluZXIgUmVnaXN0cnkiLCJhdWQiOiJhY3JhcGlzbmdtcy5henVyZWNyLmlvIiwidmVyc2lvbiI6IjEuMCIsInJpZCI6IjcyNDRkMDdiOGVhYjQwOGI5MWRkOGIyYjliNWQwZDRkIiwiZ3JhbnRfdHlwZSI6InJlZnJlc2hfdG9rZW4iLCJhcHBpZCI6IjA0YjA3Nzk1LThkZGItNDYxYS1iYmVlLTAyZjllMWJmN2I0NiIsInRlbmFudCI6ImMyNWNjMWE3LTZjNGEtNDEzZC05Njg2LTBmNTU3MmJhMmQzNiIsInBlcm1pc3Npb25zIjp7IkFjdGlvbnMiOlsicmVhZCIsIndyaXRlIiwiZGVsZXRlIiwiZGVsZXRlZC9yZWFkIiwiZGVsZXRlZC9yZXN0b3JlL2FjdGlvbiJdLCJOb3RBY3Rpb25zIjpudWxsfSwicm9sZXMiOltdfQ.ZbDme1-rtlh9XitaTk0c3tIrVB8rJCy_KhRmRzU6EuJAoqan9ENNIGKVszwaksawgjZX5AdyNOXQCbxNz487pT51VU5PlO2czJfDbbp030NQlQhWcxi8ARvIKPveXDeSUi0-9vea3lPeYH_E33AHTSFdd8mGJl4d4RUj1aAp-4votEI6Uw5QDWxow8Cz9UHVezjwF9-YlI7HKJLdJpnd11au4I6EFIy8ketCXKfYlc3EnXHh8-TFz0YBh7YwbWumgLoYy5f7H9W8gRUumZSJnqouk_gatZ2D2Xblo92xPPOdag_OvYAqX8N1dPeomb-E38iVGKNriG6j1Kx1ndrrjw
```

## 3.2 Trasmision de la imagen al almacen de imagenes
En este se realiza el traslado de la imagen del servicio al almacen de imagenes de docker.
```
    az acr repository delete  --n acrapisngms.azurecr.io --image ms-login-java:1.0.7 --yes 
    docker push acrapisngms.azurecr.io/ms-login-java:1.0.7
  
    az acr repository list --name acrapisngms.azurecr.io --output table
```

## 3.3 Creacion de los recursos de kubernetes
A continuacion se procedera el despliegue del servicio en kubernetes, para ello se debe realizar la creacion de los recursos de kubernetes, los cuales se listan a continuacion.
```
  kubectl delete deployment ms-login-java-deployment -n ns-ms-seguridad && \
  kubectl delete service ms-login-java-servicio -n ns-ms-seguridad && \
  kubectl apply -f ./k8s/ms-login-java-k8s.yaml && \
  kubectl get pod -A

  Si se desea realizar la consulta de la imagen
    kubectl get pods -o jsonpath="{.items[*].spec.containers[*].image}" -n apimsngms
```
    az role assignment list --scope /fdf6fb47-e81e-4f7c-a404-3214313bf30a/RG_APINGMS/providers/Microsoft.ContainerRegistry/registries/acrapisngms.azurecr.io -o table

    az aks update -n aks-apisngms  -g RG_APINGMS --attach-acr acrapisngms
##



