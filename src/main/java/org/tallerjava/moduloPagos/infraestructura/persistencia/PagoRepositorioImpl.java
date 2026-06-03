package org.tallerjava.moduloPagos.infraestructura.persistencia;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.tallerjava.moduloPagos.dominio.Carga;
import org.tallerjava.moduloPagos.dominio.Cliente;
import org.tallerjava.moduloPagos.dominio.MedioPago;
import org.tallerjava.moduloPagos.dominio.repositorios.PagoRepo;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class PagoRepositorioImpl implements PagoRepo {
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void registrarCliente(Cliente cliente) {
        em.persist(cliente);
    }
    
    @Override
    public Cliente buscaClientePorId(long id) {
        return em.find(Cliente.class,id);
    }

    @Override
    public Carga buscaCargaPorId(long id) {return em.find(Carga.class,id);}
    
    @Override
    public void altaMedioPago(MedioPago medioPago) {
        em.persist(medioPago);
    }

    @Override
    public MedioPago buscarMedioPagoPorId(long id) {
        return em.find(MedioPago.class, id);
    }

    @Override
    public long guardarCarga(Carga carga){
        em.persist(carga);
        return carga.getId();
    }

    @Override
    public List<Carga> consultarPagos(long clienteId, LocalDateTime ini, LocalDateTime fin) {
        try {
            return em.createQuery("SELECT c FROM Carga_Pagos c WHERE c.cliente.id = :clienteId AND c.horaInicio BETWEEN :ini AND :fin",
                    Carga.class).setParameter("clienteId", clienteId).setParameter("ini", ini).setParameter("fin", fin).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Carga cargaSinPagar(Long clienteId) {

        List<Carga> carga = em.createQuery(
                        "SELECT c FROM Carga_Pagos c WHERE c.cliente.id = :clienteId AND c.pagado = FALSE",
                        Carga.class)
                .setParameter("clienteId", clienteId)
                .setMaxResults(1)
                .getResultList();
        if (carga.isEmpty()) {
            return null;
        } else {
            return carga.get(0);

        }
    }
}
