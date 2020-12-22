FROM adoptopenjdk/openjdk11:ubi
RUN mkdir /opt/app
COPY data-0.0.1-SNAPSHOT.jar /opt/app

EXPOSE 8080

CMD ["java", "-jar", "/opt/app/data-0.0.1-SNAPSHOT.jar"]