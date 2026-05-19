package org.tallerjava.moduloPagos.interfase.evento.in;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.tallerjava.moduloComun.eventosCliente.ClientesAltaMedioPago;
import org.tallerjava.moduloPagos.aplicacion.PagoServicios;

@ApplicationScoped
public class ObserverModuloPagos {
    @Inject
    private PagoServicios servicioPagos;
    
    public void accept(@Observes ClientesAltaMedioPago event) {
    }
}
