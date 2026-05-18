package org.tallerjava.moduloClientes.interfase.evento.out;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import org.tallerjava.moduloClientes.dominio.CuentaUTE;
import org.tallerjava.moduloClientes.dominio.MedioPago;
import org.tallerjava.moduloClientes.dominio.Tarjeta;
import org.tallerjava.moduloClientes.dominio.TipoTarjeta;
import org.tallerjava.moduloClientes.interfase.evento.out.ClientesAltaMedioPago.TipoMedio;

@ApplicationScoped
public class PublicadorEventoCliente {

    @Inject
    private Event<ClientesAltaMedioPago> altaMedioPago;

    //capaz mejor sincronizar con ids los pagos y clientes del módulo clientes
    public void publicarNuevoMedioPago(MedioPago medioPago) {
        ClientesAltaMedioPago evento = new ClientesAltaMedioPago();
            evento.setClienteCedula(medioPago.getCliente().getCedula());
            evento.setClienteNombreCompleto(medioPago.getCliente().getNombreCompleto());
            evento.setClienteTelefono(medioPago.getCliente().getTelefono());
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
