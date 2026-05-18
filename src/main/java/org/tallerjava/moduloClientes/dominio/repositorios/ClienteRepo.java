package org.tallerjava.moduloClientes.dominio.repositorios;


import org.tallerjava.moduloClientes.dominio.Cliente;

import java.util.List;
import org.tallerjava.moduloClientes.dominio.MedioPago;
import org.tallerjava.moduloClientes.dominio.Reclamo;

public interface ClienteRepo {
    public long registarCliente(Cliente cliente);
    public void altaMedioPago(MedioPago medioPago);
    public Cliente buscaClientePorId(long id);
    public List<Cliente> listarClientes();
    public long guardarReclamo(Reclamo reclamo);
    
}
