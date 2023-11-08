# Configacion Kubernetes Microservicios Seguridad
Este repositorio contiene los diferentes archivos de configuracion a nivel de Kubernetes para desplegar sevicios asociado a seguridad.
    - Definicion de NameSpace
    - Definicion de ConfigMap
    - Definicion de Secret
    - Definicion de Ingress  Route
    - Definicion de OtelCollector

## Ejecucion
A continuacion se relacionan los diferentes pasos que son requeridos para efectuar la ejecucion de los diferentes archivos de configuracion a nivel de Kuberntes, y en el orden en que se deberian aplicar.
```
    1. Creacion de los parametros mediante un ConfigMap
        - kubectl apply -f ./k8s/InitialConfig-ns-ms-seguridad.yaml

    2. Creacion del OtelCollecto para el namespacr de ns-ms-seguridad
        - kubectl apply -f ./k8s/OtelCollectorConf-ns-ms-seguridad.yaml
    
```
