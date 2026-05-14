package org.tallerjava.moduloCargas.infraestructura.persistencia;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.tallerjava.moduloCargas.dominio.EstacionCarga;
import org.tallerjava.moduloCargas.dominio.repositorios.CargaRepo;

@ApplicationScoped
public class EstacionRepositorioImpl implements CargaRepo {


        @PersistenceContext
        private EntityManager em;

        @Override
        public long altaEstacion(EstacionCarga estacion) {
            em.persist(estacion);
            return estacion.getId();
        }



}
