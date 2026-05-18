
package org.tallerjava.moduloCargas.infraestructura.persistencia;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.tallerjava.moduloCargas.dominio.Carga;
import org.tallerjava.moduloCargas.dominio.Cargador;
import org.tallerjava.moduloCargas.dominio.EstacionCarga;
import org.tallerjava.moduloCargas.dominio.repositorios.CargaRepo;
import org.tallerjava.moduloClientes.dominio.Cliente;

import java.util.List;

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

    public EstacionCarga buscaEstacionPorId(long id){
        return em.find(EstacionCarga.class, id);
    }

    @Override
    public long guardarCarga(Carga carga){
        em.persist(carga);
        return carga.getId();
    }

    @Override
    public List<EstacionCarga> obtenerEstaciones(){
        return em.createQuery("SELECT DISTINCT e FROM EstacionCarga e LEFT JOIN FETCH e.cargadores", EstacionCarga.class).getResultList();
    }

    @Override
    public Carga buscarCargaActivaPorCliente(long clienteId) {
        try {
            return em.createQuery(
                            "SELECT c FROM Carga c WHERE c.cliente.id = :clienteId AND c.estado = org.tallerjava.moduloCargas.dominio.EstadoCarga.ACTIVA",
                            Carga.class)
                    .setParameter("clienteId", clienteId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
