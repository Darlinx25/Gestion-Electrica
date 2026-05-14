package org.tallerjava.moduloCargas.aplicacion;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.tallerjava.moduloCargas.dominio.EstacionCarga;
import org.tallerjava.moduloCargas.dominio.repositorios.CargaRepo;
@ApplicationScoped
public class CargaServiciosImp implements CargaServicios {
    @Inject
    private CargaRepo cargaRepo;
    @Override
    @Transactional
    public long altaEstacion(EstacionCarga estacion) {
        return cargaRepo.altaEstacion(estacion);
    }
}
