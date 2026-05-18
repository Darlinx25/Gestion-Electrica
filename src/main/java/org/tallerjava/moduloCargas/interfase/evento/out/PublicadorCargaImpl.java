package org.tallerjava.moduloCargas.interfase.evento.out;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.ws.rs.ApplicationPath;
import org.tallerjava.moduloComun.eventosCarga.CargaFinalizadaEvent;
import org.tallerjava.moduloComun.eventosCarga.CargaIniciadaEvent;

import java.time.LocalDateTime;

@ApplicationScoped
public class PublicadorCargaImpl  implements PublicadorCarga {

    @Inject
    private Event<CargaIniciadaEvent> cargaIniciadaEvent;

    @Inject
    private Event<CargaFinalizadaEvent> cargaFinalizadaEventEvent;

    public void iniciarCarga(long cargaId,long clienteId){
        cargaIniciadaEvent.fire(new CargaIniciadaEvent(cargaId, clienteId, LocalDateTime.now()));
    }

}
