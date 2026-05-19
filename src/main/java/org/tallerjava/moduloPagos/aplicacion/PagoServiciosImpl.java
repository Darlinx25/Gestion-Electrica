package org.tallerjava.moduloPagos.aplicacion;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.tallerjava.moduloPagos.dominio.Cliente;
import org.tallerjava.moduloPagos.dominio.CuentaUTE;
import org.tallerjava.moduloPagos.dominio.MedioPago;
import org.tallerjava.moduloPagos.dominio.Tarjeta;
import org.tallerjava.moduloPagos.dominio.repositorios.PagoRepo;

@ApplicationScoped
public class PagoServiciosImpl implements PagoServicios {
    @Inject
    private PagoRepo pagoRepo;
    
    
    @Override
    public void pagarCarga(long clienteId, float importe, long medioPagoId){
        MedioPago medioPago = pagoRepo.buscarMedioPagoPorId(medioPagoId);
        if (medioPago == null) {
            throw new IllegalArgumentException("Medio de pago no encontrado: " + medioPagoId);
        }
        if (medioPago instanceof Tarjeta tarjeta) {
            System.out.println("Procesando pago con tarjeta " + tarjeta.getTipo()
                    + " por $" + importe);
        } else if (medioPago instanceof CuentaUTE cuenta) {
            System.out.println("Procesando pago con Cuenta UTE "
                    + cuenta.getNumeroCuenta() + " por $" + importe);
        }
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
