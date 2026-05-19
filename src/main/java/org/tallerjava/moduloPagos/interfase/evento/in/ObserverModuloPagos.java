package org.tallerjava.moduloPagos.interfase.evento.in;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.tallerjava.moduloComun.eventosCarga.CargaFinalizadaEvent;
import org.tallerjava.moduloComun.eventosCliente.ClientesAltaClienteComun;
import org.tallerjava.moduloComun.eventosCliente.ClientesAltaClienteProfesional;
import org.tallerjava.moduloComun.eventosCliente.ClientesAltaMedioPago;
import org.tallerjava.moduloComun.eventosCliente.ClientesAltaMedioPago.TipoMedio;
import org.tallerjava.moduloPagos.aplicacion.PagoServicios;
import org.tallerjava.moduloPagos.dominio.ClienteComun;
import org.tallerjava.moduloPagos.dominio.ClienteProfesional;
import org.tallerjava.moduloPagos.dominio.CuentaUTE;
import org.tallerjava.moduloPagos.dominio.MedioPago;
import org.tallerjava.moduloPagos.dominio.Tarjeta;
import org.tallerjava.moduloPagos.dominio.TipoProfesional;
import org.tallerjava.moduloPagos.dominio.TipoTarjeta;

@ApplicationScoped
public class ObserverModuloPagos {
    @Inject
    private PagoServicios servicioPagos;
    
    public void accept(@Observes ClientesAltaClienteComun event) {
        ClienteComun cliente = new ClienteComun();
        cliente.setId(event.getId());
        cliente.setCedula(event.getCedula());
        cliente.setNombreCompleto(event.getNombreCompleto());
        cliente.setTelefono(event.getTelefono());
        servicioPagos.registrarCliente(cliente);
    }
    
    public void accept(@Observes ClientesAltaClienteProfesional event) {
        ClienteProfesional cliente = new ClienteProfesional();
        cliente.setId(event.getId());
        cliente.setCedula(event.getCedula());
        cliente.setNombreCompleto(event.getNombreCompleto());
        cliente.setTelefono(event.getTelefono());
        cliente.setTipo(TipoProfesional.valueOf(event.getTipo().name()));
        cliente.setPorcentajeDescuento(event.getPorcentajeDescuento());
        servicioPagos.registrarCliente(cliente);
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
        servicioPagos.altaMedioPago(event.getClienteId(), medioPago);
    }


    public void accept(@Observes CargaFinalizadaEvent event) {
        System.out.println("Procesando pago");
        servicioPagos.pagarCarga(event.getClienteId(),event.getImporteTotal(), event.getMedioPagoId());
    }
}
