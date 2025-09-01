# bpel-process/Dockerfile (fixed)

# Use an official Eclipse Temurin base image for Java 17
FROM eclipse-temurin:17-jdk

# Environment for Tomcat
ENV CATALINA_HOME=/opt/tomcat
ENV PATH=$CATALINA_HOME/bin:$PATH

# Install needed packages (wget + unzip) and install Tomcat
RUN apt-get update && \
    apt-get install -y --no-install-recommends wget unzip ca-certificates && \
    wget https://archive.apache.org/dist/tomcat/tomcat-10/v10.1.20/bin/apache-tomcat-10.1.20.tar.gz -O /tmp/tomcat.tar.gz && \
    tar -xf /tmp/tomcat.tar.gz -C /opt && \
    mv /opt/apache-tomcat-10.1.20 /opt/tomcat && \
    rm -rf /tmp/tomcat.tar.gz /var/lib/apt/lists/*

# Download and deploy Apache ODE
RUN wget https://archive.apache.org/dist/ode/apache-ode-war-1.3.8.zip -O /tmp/ode.zip && \
    unzip /tmp/ode.zip -d /tmp/ && \
    cp /tmp/apache-ode-war-1.3.8/ode.war $CATALINA_HOME/webapps/ && \
    rm -rf /tmp/*

# Copy BPEL process files
COPY target/bpel/ /opt/tomcat/webapps/ode/WEB-INF/processes/PlaceOrderProcess/

EXPOSE 8080

# Use full path so we don't depend on shell lookup
CMD ["/opt/tomcat/bin/catalina.sh", "run"]