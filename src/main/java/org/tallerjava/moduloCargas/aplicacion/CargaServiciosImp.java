package org.tallerjava.moduloCargas.aplicacion;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.tallerjava.moduloCargas.dominio.*;
import org.tallerjava.moduloCargas.interfase.evento.out.PublicadorCarga;
import org.tallerjava.moduloComun.eventosCarga.CargaIniciadaEvent;
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
    private PublicadorCarga publicadorCarga;

    @Override
    @Transactional
    public long altaEstacion(EstacionDTO estacionDTO) {
        EstacionCarga estacion = estacionDTO.buildEstacion();
        return cargaRepo.altaEstacion(estacion);
    }

    @Override
    @Transactional
    public long altaCargador(CargadorDTO cargadorDTO) {
        Long estacionId = cargadorDTO.getEstacionId();
        if (estacionId == null) {
            throw new IllegalArgumentException("Debe especificar una estación para el cargador");
        }
        EstacionCarga estacion = cargaRepo.buscaEstacionPorId(estacionId);
        if (estacion == null) {
            throw new IllegalArgumentException(
                    "No existe estación con id: " + estacionId);
        }
        Cargador cargador = cargadorDTO.buildCargador(estacion);
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

        publicadorCarga.iniciarCarga(cargaId, cliente.getId());
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