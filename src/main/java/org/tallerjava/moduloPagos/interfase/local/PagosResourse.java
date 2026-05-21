package org.tallerjava.moduloPagos.interfase.local;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import org.tallerjava.moduloCargas.interfase.CargaDTO;
import org.tallerjava.moduloPagos.aplicacion.PagoServicios;

@ApplicationScoped
public class PagosResourse {


    @Inject
    private PagoServicios pagosServicios;

    public void pagarCarga(long clienteId, float importe, long medioPagoId){
        pagosServicios.pagarCarga(clienteId,importe,medioPagoId);
    }
    
    public List<CargaDTO> consultarPagos(long clienteId, LocalDateTime ini, LocalDateTime fin) {
        return pagosServicios.consultarPagos(clienteId, ini, fin);
    }
}
