package org.tallerjava.moduloPagos.aplicacion;

import org.tallerjava.moduloPagos.dominio.Cliente;
import org.tallerjava.moduloPagos.dominio.MedioPago;


public interface PagoServicios {
    
    public void pagarCarga(long clienteId, float importe, long medioPagoId);
    public void registrarCliente(Cliente cliente);
    public void altaMedioPago(long clienteId, MedioPago medioPago);
   
}
