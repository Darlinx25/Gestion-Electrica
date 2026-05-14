package org.tallerjava.moduloCargas.aplicacion;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.tallerjava.moduloCargas.dominio.Cargador;
import org.tallerjava.moduloCargas.dominio.EstacionCarga;
import org.tallerjava.moduloCargas.dominio.repositorios.CargaRepo;
import org.tallerjava.moduloCargas.interfase.CargadorDTO;

@ApplicationScoped

public class CargaServiciosImp implements CargaServicios {
    @Inject
    private CargaRepo cargaRepo;
    @Override
    @Transactional
    public long altaEstacion(EstacionCarga estacion) {
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
}