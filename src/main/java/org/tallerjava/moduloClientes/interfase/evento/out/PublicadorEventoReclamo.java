package org.tallerjava.moduloClientes.interfase.evento.out;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import org.tallerjava.moduloComun.eventosCliente.ReclamoEtiquetadoEvent;

@ApplicationScoped
public class PublicadorEventoReclamo {
    @Inject
    private Event<ReclamoEtiquetadoEvent> evento;

    public void publicar(long reclamoId, String etiqueta) {
        evento.fire(new ReclamoEtiquetadoEvent(reclamoId, etiqueta));
    }
}