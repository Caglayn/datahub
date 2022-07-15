FROM caglayn/debianjdk18:latest
RUN mkdir /home/javaapp
WORKDIR /home/javaapp
COPY target/*.jar dataserviceapp.jar
EXPOSE 8091
ENTRYPOINT ["/opt/jdk-18/bin/java","-jar","/dataserviceapp.jar"]

# /opt/jdk-18/bin/java -- java commm