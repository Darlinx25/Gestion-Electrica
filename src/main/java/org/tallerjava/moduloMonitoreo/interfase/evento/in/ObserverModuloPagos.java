package org.tallerjava.moduloMonitoreo.interfase.evento.in;

import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.tallerjava.moduloComun.eventosPago.PagoTarjetaError;
import org.tallerjava.moduloComun.eventosPago.PagoTarjetaRealizado;
import org.tallerjava.moduloComun.eventosPago.PagoUTERealizado;
import org.tallerjava.moduloMonitoreo.infraestructura.RegistradorDeMetricas;

public class ObserverModuloPagos {

    @Inject
    private RegistradorDeMetricas register;
    
    public void accept(@Observes PagoTarjetaRealizado event) {
        register.incrementarCounter(RegistradorDeMetricas.PAGOS_TARJETA_REALIZADOS);
    }
    
    public void accept(@Observes PagoTarjetaError event) {
        register.incrementarCounter(RegistradorDeMetricas.PAGOS_TARJETA_ERROR);
    }
    
    public void accept(@Observes PagoUTERealizado event) {
        register.incrementarCounter(RegistradorDeMetricas.PAGOS_UTE_REALIZADOS);
    }
}
