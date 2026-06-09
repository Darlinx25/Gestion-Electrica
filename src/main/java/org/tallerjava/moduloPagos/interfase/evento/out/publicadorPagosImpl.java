package org.tallerjava.moduloPagos.interfase.evento.out;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import org.tallerjava.moduloComun.eventosPago.PagoTarjetaError;
import org.tallerjava.moduloComun.eventosPago.PagoTarjetaRealizado;
import org.tallerjava.moduloComun.eventosPago.PagoUTERealizado;
import org.tallerjava.moduloComun.eventosPago.PagosPagarCarga;

@ApplicationScoped
public class publicadorPagosImpl implements publicadorPagos{
    @Inject
    private Event<PagosPagarCarga> PagosPagarCarga;
    @Inject
    private Event<PagoTarjetaRealizado> pagoTarjetaRealizado;
    @Inject
    private Event<PagoTarjetaError> pagoTarjetaError;
    @Inject
    private Event<PagoUTERealizado> pagoUTERealizado;

    @Override
    public void pagarCarga(long cargaId){
        PagosPagarCarga.fire(new PagosPagarCarga(cargaId));
    }
    
    @Override
    public void pagoTarjetaRealizado() {
        pagoTarjetaRealizado.fire(new PagoTarjetaRealizado());
    }
    
    @Override
    public void pagoTarjetaError() {
        pagoTarjetaError.fire(new PagoTarjetaError());
    }
    
    @Override
    public void pagoUTERealizado() {
        pagoUTERealizado.fire(new PagoUTERealizado());
    }
}
