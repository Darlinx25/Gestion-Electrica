
package org.tallerjava.moduloCargas.infraestructura.persistencia;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.tallerjava.moduloCargas.dominio.Cargador;
import org.tallerjava.moduloCargas.dominio.EstacionCarga;
import org.tallerjava.moduloCargas.dominio.repositorios.CargaRepo;

@ApplicationScoped

public class CargaRepositorioImpl implements CargaRepo {
    @PersistenceContext
    private EntityManager em;
    @Override
    public long altaEstacion(EstacionCarga estacion) {
        em.persist(estacion);
        return estacion.getId();
    }
    @Override
    public long altaCargador(Cargador cargador) {
        em.persist(cargador);
        return cargador.getId();
    }
    @Override
    public EstacionCarga buscaEstacionPorId(long id) {
        return em.find(EstacionCarga.class, id);
    }
}
