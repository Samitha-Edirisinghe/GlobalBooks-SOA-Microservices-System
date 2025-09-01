# catalog-service/Dockerfile

# Use an official Eclipse Temurin base image for Java 24
FROM tomcat:11.0-jdk17-temurin

# Tomcat location and PATH
ENV CATALINA_HOME=/opt/tomcat
ENV PATH=$CATALINA_HOME/bin:$PATH

# Install wget (and unzip if you need it later), fetch & install Tomcat
RUN apt-get update && \
    apt-get install -y --no-install-recommends wget ca-certificates && \
    wget https://archive.apache.org/dist/tomcat/tomcat-11/v11.0.0-M24/bin/apache-tomcat-11.0.0-M24.tar.gz -O /tmp/tomcat.tar.gz && \
    tar -xf /tmp/tomcat.tar.gz -C /opt && \
    mv /opt/apache-tomcat-11.0.0-M24 /opt/tomcat && \
    rm -rf /tmp/tomcat.tar.gz /var/lib/apt/lists/*

# Copy the built WAR into Tomcat webapps -- ensure this file exists before building the image
COPY target/catalog-service.war $CATALINA_HOME/webapps/

EXPOSE 8080

# Use full path to avoid shell lookup issues
CMD ["/opt/tomcat/bin/catalina.sh", "run"]
