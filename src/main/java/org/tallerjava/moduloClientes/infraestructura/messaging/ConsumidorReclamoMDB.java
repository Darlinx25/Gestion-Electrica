package org.tallerjava.moduloClientes.infraestructura.messaging;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.tallerjava.moduloClientes.aplicacion.ClasificadorService;
import org.tallerjava.moduloClientes.dominio.Reclamo;
import org.tallerjava.moduloClientes.interfase.evento.out.PublicadorEventoReclamo;


@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:jboss/exported/jms/queue/ReclamoQueue"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue")
})
public class ConsumidorReclamoMDB implements MessageListener {

    @Inject
    private ClasificadorService clasificador;

    @Inject
    private PublicadorEventoReclamo publicador;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage tm = (TextMessage) message;
            String body = tm.getText();

            long reclamoId = Long.parseLong(body.substring(body.indexOf("\"id\":") + 5, body.indexOf(",", body.indexOf("\"id\":"))).trim());

            int txtIdx = body.indexOf("\"texto\":\"") + 9;
            int txtFin = body.lastIndexOf("\"}");
            String texto = body.substring(txtIdx, txtFin);

            String etiqueta = clasificador.clasificar(texto);

            Reclamo reclamo = em.find(Reclamo.class, reclamoId);
            if (reclamo != null) {
                reclamo.setEtiqueta(etiqueta);
                em.merge(reclamo);
                publicador.publicar(reclamoId, etiqueta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
