package org.tallerjava.moduloClientes.dominio.repositorios;


import org.tallerjava.moduloClientes.dominio.Cliente;

import java.util.List;

public interface ClienteRepo {
    public void registarCliente(Cliente cliente);
    public Cliente buscaClientePorId(long id);
    public Cliente actualizarCliente(Cliente cliente);
    public List<Cliente> listarClientes();
}
