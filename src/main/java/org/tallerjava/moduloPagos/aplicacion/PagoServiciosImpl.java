package org.tallerjava.moduloPagos.aplicacion;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.tallerjava.moduloPagos.dominio.Cliente;
import org.tallerjava.moduloPagos.dominio.MedioPago;
import org.tallerjava.moduloPagos.dominio.repositorios.PagoRepo;

@ApplicationScoped
public class PagoServiciosImpl implements PagoServicios {
    @Inject
    private PagoRepo pagoRepo;
    
    
    @Override
    public void pagarCarga(Cliente cliente, float importe, MedioPago medioPago){
        
    }
    
    @Override
    public void vincularMedioPago(long clienteId, MedioPago medioPago) {
        
    }
    
    
}
