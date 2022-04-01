# Tracing prototype

## Setup

Run clean install
```
$ mvn clean install
```
Create run configurations for each application with the following vm args:
```
Ingestor App:
-Dcom.sun.management.jmxremote
-Dcom.sun.management.jmxremote.ssl=false
-Dcom.sun.management.jmxremote.authenticate=false
-Dcom.sun.management.jmxremote.local.only=false
-Dcom.sun.management.jmxremote.port=9014
-Dcom.sun.management.jmxremote.rmi.port=9014
-Djava.rmi.server.hostname=127.0.0.1
-javaagent:<path_to_m2>/.m2/repository/io/opentelemetry/javaagent/opentelemetry-javaagent/1.12.0/opentelemetry-javaagent-1.12.0.jar
-Dotel.exporter.otlp.endpoint=http://192.168.64.4:4317
-Dotel.resource.attributes=service.name=ingestor

Aggregator app:
-Dcom.sun.management.jmxremote
-Dcom.sun.management.jmxremote.ssl=false
-Dcom.sun.management.jmxremote.authenticate=false
-Dcom.sun.management.jmxremote.local.only=false
-Dcom.sun.management.jmxremote.port=9013
-Dcom.sun.management.jmxremote.rmi.port=9013
-Djava.rmi.server.hostname=127.0.0.1
-javaagent:<path_to_m2>/.m2/repository/io/opentelemetry/javaagent/opentelemetry-javaagent/1.12.0/opentelemetry-javaagent-1.12.0.jar
-Dotel.exporter.otlp.endpoint=http://192.168.64.4:4317
-Dotel.resource.attributes=service.name=aggregator
```
**Note**: Replace `<path_to_m2>` with the full parent directory of your `.m2` directory.

Bring up the docker test stack
```
$ docker-compose up
```

Start both ingestor and aggregator applications.