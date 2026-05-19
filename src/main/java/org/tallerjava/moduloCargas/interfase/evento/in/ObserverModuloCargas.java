package org.tallerjava.moduloCargas.interfase.evento.in;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.tallerjava.moduloCargas.aplicacion.CargaServicios;
import org.tallerjava.moduloCargas.dominio.ClienteComun;
import org.tallerjava.moduloCargas.dominio.ClienteProfesional;
import org.tallerjava.moduloCargas.dominio.CuentaUTE;
import org.tallerjava.moduloCargas.dominio.MedioPago;
import org.tallerjava.moduloCargas.dominio.Tarjeta;
import org.tallerjava.moduloCargas.dominio.TipoProfesional;
import org.tallerjava.moduloCargas.dominio.TipoTarjeta;
import org.tallerjava.moduloComun.eventosCliente.ClientesAltaClienteComun;
import org.tallerjava.moduloComun.eventosCliente.ClientesAltaClienteProfesional;
import org.tallerjava.moduloComun.eventosCliente.ClientesAltaMedioPago;
import org.tallerjava.moduloComun.eventosCliente.ClientesAltaMedioPago.TipoMedio;

@ApplicationScoped
public class ObserverModuloCargas {
    @Inject
    private CargaServicios servicioCargas;
    
    public void accept(@Observes ClientesAltaClienteComun event) {
        ClienteComun cliente = new ClienteComun();
        cliente.setId(event.getId());
        cliente.setCedula(event.getCedula());
        cliente.setNombreCompleto(event.getNombreCompleto());
        cliente.setTelefono(event.getTelefono());
        servicioCargas.registrarCliente(cliente);
    }
    
    public void accept(@Observes ClientesAltaClienteProfesional event) {
        ClienteProfesional cliente = new ClienteProfesional();
        cliente.setId(event.getId());
        cliente.setCedula(event.getCedula());
        cliente.setNombreCompleto(event.getNombreCompleto());
        cliente.setTelefono(event.getTelefono());
        cliente.setTipo(TipoProfesional.valueOf(event.getTipo().name()));
        cliente.setPorcentajeDescuento(event.getPorcentajeDescuento());
        servicioCargas.registrarCliente(cliente);
    }
    
    public void accept(@Observes ClientesAltaMedioPago event) {
        MedioPago medioPago;
        if (event.getMedio() == TipoMedio.CUENTA_UTE) {
            CuentaUTE cuenta = new CuentaUTE();
            cuenta.setId(event.getMedioId());
            cuenta.setNumeroCuenta(event.getNumeroCuenta());
            medioPago = cuenta;
        } else {
            Tarjeta tarjeta = new Tarjeta();
            tarjeta.setId(event.getMedioId());
            tarjeta.setNumero(event.getNumero());
            tarjeta.setFechaVencimiento(event.getFechaVencimiento());
            tarjeta.setDigitoVerificacion(event.getDigitoVerificacion());
            if (event.getMedio() == TipoMedio.TARJETA_DEBITO) {
                tarjeta.setTipo(TipoTarjeta.DEBITO);
            } else {
                tarjeta.setTipo(TipoTarjeta.CREDITO);
            }
            medioPago = tarjeta;
        }
        servicioCargas.vincularMedioPago(event.getClienteId(), medioPago);
    }
}
