package org.tallerjava.moduloClientes.infraestructura.messaging;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;

@ApplicationScoped
public class ProductorReclamo {
    @Resource(lookup = "java:jboss/exported/jms/queue/ReclamoQueue")
    private Queue reclamoQueue;

    @Inject
    private JMSContext context;

    public void enviarReclamo(long reclamoId, String informacion) {
        context.createProducer().send(reclamoQueue, "{\"id\":%d,\"texto\":\"%s\"}".formatted(reclamoId, informacion));
    }
}
