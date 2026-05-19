package org.tallerjava.moduloPagos.aplicacion;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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
    public void registrarCliente(Cliente cliente) {
        pagoRepo.registrarCliente(cliente);
    }
    
    @Override
    @Transactional
    public void altaMedioPago(long clienteId, MedioPago medioPago) {
        Cliente cliente = pagoRepo.buscaClientePorId(clienteId);
        if (cliente == null) {
            return;
        }
        cliente.addMedioPago(medioPago);
        pagoRepo.altaMedioPago(medioPago);
    }
}
