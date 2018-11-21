FROM openjdk:11-jre-slim
MAINTAINER Marten Deinum (marten@deinum.biz)
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
