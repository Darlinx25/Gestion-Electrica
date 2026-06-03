package org.tallerjava.moduloCargas.interfase.evento.in;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.tallerjava.moduloCargas.aplicacion.CargaServicios;
import org.tallerjava.moduloCargas.dominio.*;
import org.tallerjava.moduloCargas.dominio.repositorios.CargaRepo;
import org.tallerjava.moduloComun.eventosCarga.CargaFinalizadaEvent;
import org.tallerjava.moduloComun.eventosCarga.CargaIniciadaEvent;
import org.tallerjava.moduloComun.eventosCliente.ClientesAltaClienteComun;
import org.tallerjava.moduloComun.eventosCliente.ClientesAltaClienteProfesional;
import org.tallerjava.moduloComun.eventosCliente.ClientesAltaMedioPago;
import org.tallerjava.moduloComun.eventosCliente.ClientesAltaMedioPago.TipoMedio;
import org.tallerjava.moduloComun.eventosPago.PagosPagarCarga;

@ApplicationScoped
public class ObserverModuloCargas {
    @Inject
    private CargaServicios servicioCargas;

    @Inject
    CargaRepo cargaRepo;

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
        servicioCargas.altaMedioPago(event.getClienteId(), medioPago);
    }

    public void inicioDeCarga(@Observes CargaIniciadaEvent event){
        Carga c = cargaRepo.buscarCargaActivaPorCliente(event.getClienteId());

        System.out.println("Id de la carga:");
        System.out.println(c.getId());
        System.out.println("Hora Inicio de la carga:");
        System.out.println(c.getHoraInicio());
        System.out.println("Estado de la carga: ");
        System.out.println(c.getEstado());

    }
    public void finDeCarga(@Observes CargaFinalizadaEvent event){
        Carga c = cargaRepo.buscarCargaPorId(event.getCargaId());
        System.out.println("Id de la carga:");
        System.out.println(c.getId());
        System.out.println("Hora fin de la carga:");
        System.out.println(c.getHoraFin());
        System.out.println("Estado de la carga: ");
        System.out.println(c.getEstado());


    }
    public void accept(@Observes PagosPagarCarga event) {
        Carga c = cargaRepo.buscarCargaPorId(event.getCargaId());
        c.setPagado(true);
        cargaRepo.guardarCarga(c);
    }
}
