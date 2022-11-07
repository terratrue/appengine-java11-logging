# A custom Logback appender for App Engine standard Java 11 runtime

This Logback appender supports the following functionality:

1. Group app logs for a request under a new request log entry
2. Full text search on the app logs
3. Filter on metadata fields of the request log and app logs

We include the Java code of the `google.appengine.logging.v1` package that we build from
its [protobuf specification](https://github.com/googleapis/googleapis/tree/master/google/appengine/logging/v1)

We also include a simple Spring Boot application to demonstrate the usage of the Logback appender.

## To build and run the application locally

1. Run the following command to start the application

```shell
mvn clean package spring-boot:run -Pdev
```

2. Visit `http://localhost:8080` in your browser

## To build and deploy to App Engine

We assume you are already familiar with App Engine standard environment and how to deploy Java 11
application.

1. Make sure you have configured a Java 11 SDK locally
2. Run the following command to set the GCP project that you want to deploy to.

```shell
gcloud config set project <PROJECT_ID>
```

3. Run the following command to build and deploy

```shell
mvn clean install -Pprod && mvn appengine:deploy -Pprod
```

4. Visit `https://<PROJECT_ID>.appspot.com` in your browser