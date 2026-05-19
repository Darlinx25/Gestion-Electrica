package org.tallerjava.moduloClientes.interfase.evento.out;

import org.tallerjava.moduloComun.eventosCliente.ClientesAltaMedioPago;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import org.tallerjava.moduloClientes.dominio.Cliente;
import org.tallerjava.moduloClientes.dominio.ClienteComun;
import org.tallerjava.moduloClientes.dominio.ClienteProfesional;
import org.tallerjava.moduloClientes.dominio.CuentaUTE;
import org.tallerjava.moduloClientes.dominio.MedioPago;
import org.tallerjava.moduloClientes.dominio.Tarjeta;
import org.tallerjava.moduloClientes.dominio.TipoTarjeta;
import org.tallerjava.moduloComun.eventosCliente.ClientesAltaClienteComun;
import org.tallerjava.moduloComun.eventosCliente.ClientesAltaClienteProfesional;
import org.tallerjava.moduloComun.eventosCliente.ClientesAltaClienteProfesional.TipoProfesional;
import org.tallerjava.moduloComun.eventosCliente.ClientesAltaMedioPago.TipoMedio;

@ApplicationScoped
public class PublicadorEventoCliente {

    @Inject
    private Event<ClientesAltaClienteComun> altaClienteComun;
    
    @Inject
    private Event<ClientesAltaClienteProfesional> altaClienteProfesional;
    
    @Inject
    private Event<ClientesAltaMedioPago> altaMedioPago;

    public void publicarCliente(Cliente cliente) {
        if (cliente instanceof ClienteComun c) {
            ClientesAltaClienteComun evento = new ClientesAltaClienteComun();
            evento.setId(c.getId());
            evento.setCedula(c.getCedula());
            evento.setNombreCompleto(c.getNombreCompleto());
            evento.setTelefono(c.getTelefono());
            altaClienteComun.fire(evento);
        }
        if (cliente instanceof ClienteProfesional c) {
            ClientesAltaClienteProfesional evento = new ClientesAltaClienteProfesional();
            evento.setId(c.getId());
            evento.setCedula(c.getCedula());
            evento.setNombreCompleto(c.getNombreCompleto());
            evento.setTelefono(c.getTelefono());
            evento.setTipo(TipoProfesional.valueOf(c.getTipo().name()));
            evento.setPorcentajeDescuento(c.getPorcentajeDescuento());
            altaClienteProfesional.fire(evento);
        }
    }
    
    public void publicarNuevoMedioPago(MedioPago medioPago) {
        ClientesAltaMedioPago evento = new ClientesAltaMedioPago();
        evento.setClienteId(medioPago.getCliente().getId());
        if (medioPago instanceof CuentaUTE mp) {
            evento.setMedio(TipoMedio.CUENTA_UTE);
            evento.setMedioId(mp.getId());
            evento.setNumeroCuenta(mp.getNumeroCuenta());
        } else {
            evento.setMedioId(((Tarjeta) medioPago).getId());
            evento.setNumero(((Tarjeta) medioPago).getNumero());
            evento.setFechaVencimiento(((Tarjeta) medioPago).getFechaVencimiento());
            evento.setDigitoVerificacion(((Tarjeta) medioPago).getDigitoVerificacion());
            if (((Tarjeta) medioPago).getTipo() == TipoTarjeta.DEBITO) {
                evento.setMedio(TipoMedio.TARJETA_DEBITO);
            } else {
                evento.setMedio(TipoMedio.TARJETA_CREDITO);
            }
        }
        altaMedioPago.fire(evento);
    }
}
