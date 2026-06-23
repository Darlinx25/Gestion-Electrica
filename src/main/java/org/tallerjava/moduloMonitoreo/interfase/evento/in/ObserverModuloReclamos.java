package org.tallerjava.moduloMonitoreo.interfase.evento.in;

import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.tallerjava.moduloComun.eventosCliente.ReclamoEtiquetadoEvent;
import org.tallerjava.moduloMonitoreo.infraestructura.RegistradorDeMetricas;


public class ObserverModuloReclamos {
    @Inject
    private RegistradorDeMetricas register;

    public void accept(@Observes ReclamoEtiquetadoEvent event) {
        if ("NEGATIVO".equals(event.getEtiqueta())) {
            register.incrementarCounter(RegistradorDeMetricas.RECLAMOS_NEGATIVOS);
        }
    }
}