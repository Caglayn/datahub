FROM caglayn/debianjdk18
RUN mkdir /home/javaapp
WORKDIR /home/javaapp
COPY target/*.jar /home/javaapp/dataserviceapp.jar
EXPOSE 8091
ENTRYPOINT ["/opt/jdk-18/bin/java","-jar","dataserviceapp.jar"]

# /opt/jdk-18/bin/java -- java commm