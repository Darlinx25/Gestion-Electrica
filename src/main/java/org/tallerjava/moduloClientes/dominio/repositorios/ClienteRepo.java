package org.tallerjava.moduloClientes.dominio.repositorios;


import org.tallerjava.moduloClientes.dominio.Cliente;

import java.util.List;

public interface ClienteRepo {
    public long registarCliente(Cliente cliente);
    public Cliente buscaClientePorId(long id);
    public List<Cliente> listarClientes();
}
