package org.tallerjava.moduloClientes.infraestructura.persistencia;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.tallerjava.moduloClientes.dominio.Cliente;
import org.tallerjava.moduloClientes.dominio.Reclamo;
import org.tallerjava.moduloClientes.dominio.repositorios.ClienteRepo;

import java.util.ArrayList;
import java.util.List;
import org.tallerjava.moduloClientes.dominio.MedioPago;

@ApplicationScoped
public class ClienteRepositorioImpl implements ClienteRepo {
    @PersistenceContext
    private EntityManager em;

    @Override
    public long registarCliente(Cliente cliente) {
        em.persist(cliente);
        return cliente.getId();
    }

    @Override
    public void altaMedioPago(MedioPago medioPago) {
        em.persist(medioPago);
    }
    
    @Override
    public Cliente buscaClientePorId(long id) {
        return em.find(Cliente.class,id);
    }
    
    @Override
    public List<Cliente> listarClientes(){
        return em.createQuery("SELECT c FROM Cliente_Clientes c",Cliente.class).getResultList();
    }

    @Override
    public long guardarReclamo(Reclamo reclamo) {
        em.persist(reclamo);
        return reclamo.getId();
    }

    @Override
    public Cliente buscaClientePorCedula(String cedula) {
        try {
            return em.createQuery("SELECT c FROM Cliente_Clientes c WHERE c.cedula = :cedula", Cliente.class).setParameter("cedula", cedula).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
