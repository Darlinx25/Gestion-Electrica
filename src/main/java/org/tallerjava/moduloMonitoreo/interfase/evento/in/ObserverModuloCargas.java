package org.tallerjava.moduloMonitoreo.interfase.evento.in;

import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.tallerjava.moduloComun.eventosCarga.CargaFinalizadaEvent;
import org.tallerjava.moduloComun.eventosCarga.CargaIniciadaEvent;
import org.tallerjava.moduloMonitoreo.infraestructura.RegistradorDeMetricas;

public class ObserverModuloCargas {

    @Inject
    private RegistradorDeMetricas register;
    
    public void accept(@Observes CargaIniciadaEvent event) {
        register.incrementarGauge(RegistradorDeMetricas.CARGAS_ACTIVAS);
    }
    
    public void accept(@Observes CargaFinalizadaEvent event) {
        register.incrementarCounter(RegistradorDeMetricas.CARGAS_REALIZADAS);
        register.decrementarGauge(RegistradorDeMetricas.CARGAS_ACTIVAS);
    }
}
