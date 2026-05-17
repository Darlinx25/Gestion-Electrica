package org.tallerjava.moduloCargas.aplicacion;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.tallerjava.moduloCargas.dominio.*;
import org.tallerjava.moduloComun.eventos.CargaIniciadaEvent;
import org.tallerjava.moduloCargas.dominio.repositorios.CargaRepo;
import org.tallerjava.moduloCargas.interfase.CargadorDTO;
import org.tallerjava.moduloCargas.interfase.EstacionDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ApplicationScoped

public class CargaServiciosImp implements CargaServicios {

    @Inject
    private CargaRepo cargaRepo;

    @Inject
    private Event<CargaIniciadaEvent> cargaIniciadaEvent;

    @Override
    @Transactional
    public long altaEstacion(EstacionDTO estacionDTO) {
        EstacionCarga estacion = estacionDTO.buildEstacion();
        return cargaRepo.altaEstacion(estacion);
    }

    @Override
    @Transactional
    public long altaCargador(CargadorDTO cargadorDTO) {
        Cargador cargador = cargadorDTO.buildCargador();
        EstacionCarga estacion = cargaRepo.buscaEstacionPorId(cargadorDTO.getEstacionId());
        if (estacion == null) {
            throw new IllegalArgumentException(
                    "No existe estación con id: " + cargadorDTO.getEstacionId());
        }
        cargador.setEstacionCarga(estacion);
        return cargaRepo.altaCargador(cargador);
    }

    @Override
    @Transactional
    public void iniciarCarga(Cliente cliente, MedioPago medioPago){
        Carga carga = new Carga();
        carga.setCliente(cliente);
        carga.setMedioPagoId(medioPago.getId());
        carga.setFecha(LocalDate.now());
        carga.setHoraInicio(LocalDateTime.now());
        carga.setEstado(EstadoCarga.ACTIVA);

        long cargaId = cargaRepo.guardarCarga(carga);

        cargaIniciadaEvent.fire(new CargaIniciadaEvent(cargaId, cliente.getId(),LocalDateTime.now()));
    }
    @Override
    public void verCargaActual(Cliente cliente){

    }
    @Override
    public void verHistorico(Cliente cliente, LocalDateTime ini, LocalDateTime fin){

    }
    @Override
    public void finalizarCarga(Cargador cargador, float carga, float recargo){

    }
    @Override
    public void obtenerEstaciones(){

    }
}