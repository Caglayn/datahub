FROM caglayn/debianjdk18:latest
RUN mkdir /home/javaapp
WORKDIR /home/javaapp
COPY target/*.jar datatransformserviceapp.jar
EXPOSE 8092
ENTRYPOINT ["/opt/jdk-18/bin/java","-jar","/datatransformserviceapp.jar"]

# /opt/jdk-18/bin/java -- java commm