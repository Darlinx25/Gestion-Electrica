package org.tallerjava.moduloPagos.interfase.local;

import jakarta.inject.Inject;
import org.tallerjava.moduloPagos.dominio.Cliente;
import org.tallerjava.moduloPagos.aplicacion.PagoServicios;
import org.tallerjava.moduloPagos.dominio.MedioPago;

public class PagosResourse {


    @Inject
    private PagoServicios pagosServicios;

    public void pagarCarga(Cliente cliente, float importe, MedioPago medioPago){
        pagosServicios.pagarCarga(cliente,importe,medioPago);
    }
}
