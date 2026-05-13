package org.tallerjava.moduloClientes.infraestructura.persistencia;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.tallerjava.moduloClientes.dominio.Cliente;
import org.tallerjava.moduloClientes.dominio.repositorios.ClienteRepo;

import java.util.List;

@ApplicationScoped
public class ClienteRepositorioImpl implements ClienteRepo {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void registarCliente(Cliente cliente) {
        em.persist(cliente);
    }

    @Override
    public Cliente buscaClientePorId(int id) {
        return em.find(Cliente.class,id);
    }

    @Override
    public Cliente actualizarCliente(Cliente cliente) {
        em.persist(cliente);
        return cliente;
    }

    public List<Cliente> listarClientes(){
        return em.createQuery("SELECT '*' FROM Cliente",Cliente.class).getResultList();
    }
}
