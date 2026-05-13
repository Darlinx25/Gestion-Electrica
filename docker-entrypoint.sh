#!/bin/bash
set -e

/opt/jboss/wildfly/bin/standalone.sh "$@" &

until /opt/jboss/wildfly/bin/jboss-cli.sh -c \
    --command=":read-attribute(name=server-state)" 2>/dev/null | grep -q "running"; do
    sleep 2
done

/opt/jboss/wildfly/bin/jboss-cli.sh -c --file=/opt/jboss/wildfly/bin/config_docker.cli

wait
