package org.tallerjava.moduloPagos.infraestructura.persistencia;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.tallerjava.moduloPagos.dominio.Cliente;
import org.tallerjava.moduloPagos.dominio.MedioPago;
import org.tallerjava.moduloPagos.dominio.repositorios.PagoRepo;

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
    public void altaMedioPago(MedioPago medioPago) {
        em.persist(medioPago);
    }

    @Override
    public MedioPago buscarMedioPagoPorId(long id) {
        return em.find(MedioPago.class, id);
    }
}
