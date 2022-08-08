FROM caglayn/debianjdk18
RUN mkdir /home/javaapp
WORKDIR /home/javaapp
COPY target/*.jar /home/javaapp/userserviceapp.jar
EXPOSE 8093
ENTRYPOINT ["/opt/jdk-18/bin/java","-jar","userserviceapp.jar"]

# /opt/jdk-18/bin/java -- java commm