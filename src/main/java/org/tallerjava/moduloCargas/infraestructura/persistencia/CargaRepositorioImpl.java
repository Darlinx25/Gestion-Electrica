package org.tallerjava.moduloCargas.infraestructura.persistencia;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.tallerjava.moduloCargas.dominio.*;
import org.tallerjava.moduloCargas.dominio.repositorios.CargaRepo;


import java.time.LocalDateTime;
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

    @Override
    public EstacionCarga buscaEstacionPorId(long id){
        return em.find(EstacionCarga.class, id);
    }

    @Override
    public Cargador buscaCargadorPorId(long id){
        return em.find(Cargador.class, id);
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
    public Carga buscarCargaPorId(long cargaId){
        return em.createQuery("SELECT c FROM Carga c WHERE c.id = :cargaId",Carga.class).setParameter("cargaId", cargaId)
                .getSingleResult();
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

    @Override
    public Cliente buscarClientePorId(long id) {
        return em.find(Cliente.class, id);
    }
    @Override
    public MedioPago buscarMedioPagoPorId(long id) {
        return em.find(MedioPago.class, id);
    }
    @Override
    public void registrarCliente(Cliente cliente) {
        em.persist(cliente);
    }
    
    @Override
    public void altaMedioPago(MedioPago medioPago) {
        em.persist(medioPago);
    }

    @Override
    public List<Carga> verHistorico(Long clienteId, LocalDateTime ini, LocalDateTime fin){
        try {
            return em.createQuery("SELECT c FROM Carga c WHERE c.cliente.id = :clienteId AND c.horaInicio BETWEEN :ini AND :fin",
                    Carga.class).setParameter("clienteId", clienteId).setParameter("ini", ini).setParameter("fin", fin).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public boolean cargaSinPagar(Long clienteId){

        List<Carga> cargas = em.createQuery("SELECT c FROM Carga c WHERE c.cliente.id = :clienteId AND c.pagado = FALSE",
                Carga.class).setParameter("clienteId", clienteId).getResultList();
        if (cargas.isEmpty()){
            return false;
        }else {
            return true;
        }

    }
}
