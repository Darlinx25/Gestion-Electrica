package org.tallerjava.moduloClientes.infraestructura.persistencia;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.tallerjava.moduloClientes.dominio.Cliente;
import org.tallerjava.moduloClientes.dominio.repositorios.ClienteRepo;

import java.util.ArrayList;
import java.util.List;

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
    public Cliente buscaClientePorId(long id) {
        return em.find(Cliente.class,id);
    }
    
    @Override
    public List<Cliente> listarClientes(){
        return em.createQuery("SELECT c FROM Cliente c",Cliente.class).getResultList();
    }
}
