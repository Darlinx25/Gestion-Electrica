package org.tallerjava.moduloPagos.interfase.evento.out;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import org.tallerjava.moduloComun.eventosPago.PagosPagarCarga;

@ApplicationScoped
public class publicadorPagosImpl implements publicadorPagos{
    @Inject
    private Event<PagosPagarCarga> PagosPagarCarga;

    @Override
    public void pagarCarga(long cargaId){
        PagosPagarCarga.fire(new PagosPagarCarga(cargaId));
    }
}
