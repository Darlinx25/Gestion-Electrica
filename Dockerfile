FROM quay.io/wildfly/wildfly:27.0.0.Final-jdk17

RUN mkdir -p /opt/jboss/wildfly/modules/system/layers/base/org/mariadb/main
RUN /opt/jboss/wildfly/bin/add-user.sh admin admin1234 --silent
COPY mariadb-java-client-3.3.3.jar /opt/jboss/wildfly/modules/system/layers/base/org/mariadb/main/
COPY module.xml /opt/jboss/wildfly/modules/system/layers/base/org/mariadb/main/

COPY config_docker.cli /opt/jboss/wildfly/bin/
COPY target/Gestion-Electrica.war /opt/jboss/wildfly/standalone/
COPY docker-entrypoint.sh /opt/jboss/wildfly/bin/

USER jboss
ENTRYPOINT ["/opt/jboss/wildfly/bin/docker-entrypoint.sh"]
CMD ["-c", "standalone-full.xml", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
