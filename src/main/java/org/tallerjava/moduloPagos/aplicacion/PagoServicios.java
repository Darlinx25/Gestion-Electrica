package org.tallerjava.moduloPagos.aplicacion;

import org.tallerjava.moduloPagos.dominio.Cliente;
import org.tallerjava.moduloPagos.dominio.MedioPago;


public interface PagoServicios {
    
    public void pagarCarga(Cliente cliente, float importe, MedioPago medioPago);
    public void registrarCliente(Cliente cliente);
    public void vincularMedioPago(long clienteId, MedioPago medioPago);
   
}
