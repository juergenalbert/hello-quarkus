# hello-quarkus Project

## Bootstrapping the application
```shell script
mvn io.quarkus.platform:quarkus-maven-plugin:2.7.1.Final:create \
    -DprojectGroupId=de.sk9 \
    -DprojectArtifactId=hello-quarkus \
    -Dextensions="resteasy"
```

## Viewing and adding extensions
```shell script
mvn quarkus:list-extensions
mvn quarkus:add-extension -Dextensions="groupId:artifactId"
```

## Running the application in dev mode
```shell script
mvn compile quarkus:dev
```

NOTE: Quarkus Dev UI in browser: http://localhost:8080/q/dev/.

## Packaging and running the application

### Non Über JAR
```shell script
mvn package
```
```shell script
java -jar target/quarkus-app/quarkus-run.jar
```

### Über JAR
```shell script
mvn package -Dquarkus.package.type=uber-jar
```
```shell script
java -jar target/hello-quarkus-1.0.0-SNAPSHOT-runner.jar
```

### Native executable
```shell script
mvn package -Pnative
```
```shell script
target/hello-quarkus-1.0.0-SNAPSHOT-runner
```

### Build & Run OCI-Image (Java)
```shell script
mvn package -Dquarkus.container-image.build=true -Dquarkus.container-image.name=hello-quarkus-java
```
```shell script
docker run -p8080:8080 juergi/hello-quarkus-java:1.0.0-SNAPSHOT
```
https://quarkus.io/guides/container-image#quarkus-container-image_quarkus.container-image.group


### Build OCI-Image (native)
```shell script
mvn quarkus:add-extension -Dextensions="container-image-jib"
```
```shell script
mvn package -Pnative -Dquarkus.native.native-image-xmx=2048m
mvn package -Pnative -Dquarkus.container-image.build=true -Dquarkus.native.reuse-existing=true
```

### Run in Docker
```shell script
docker run -p8080:8080 juergi/hello-quarkus:1.0.0-SNAPSHOT
```

### Run in Kubernetes
#### Alternative 1
```shell script
docker save juergi/hello-quarkus:1.0.0-SNAPSHOT > hello-quarkus.tar
microk8s ctr --debug image import hello-quarkus.tar
kubectl create deployment --image=juergi/hello-quarkus:1.0.0-SNAPSHOT hello-quarkus
kubectl expose deployment hello-quarkus --port=8080 --name=hello-quarkus
kubectl port-forward service/hello-quarkus 8080:8080
xdg-open http://localhost:8080/hello

```

#### Alternative 2 (registry at u3 equals kubernetes node)
```shell script
docker tag juergi/hello-quarkus:1.0.0-SNAPSHOT u3:32000/hello-quarkus:latest
docker push u3:32000/hello-quarkus:latest
kubectl create deployment --image=localhost:32000/hello-quarkus:latest hello-quarkus
kubectl expose deployment hello-quarkus --type=NodePort --port 8080 --name=hello-quarkus-service
kubectl patch service hello-quarkus-service --type='json' --patch='[{"op": "replace", "path": "/spec/ports/0/nodePort", "value":30000}]'
xdg-open http://u3:30000/hello
```