package org.tallerjava.moduloClientes.aplicacion;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.tallerjava.moduloClientes.dominio.Cliente;
import org.tallerjava.moduloClientes.dominio.repositorios.ClienteRepo;
import org.tallerjava.moduloClientes.dominio.MedioPago;

import java.util.List;

@ApplicationScoped
public class CuentaServiciosImpl implements CuentaServicios{

    @Inject
    private ClienteRepo clienteRepo;

    @Override
    public List<Cliente> obtenerClientes() {
        return clienteRepo.listarClientes();
    }

    @Override
    @Transactional
    public long registarCliente(Cliente cliente) {
        return clienteRepo.registarCliente(cliente);
    }

    @Override
    @Transactional
    public void altaMedioPago(long clienteId, MedioPago medioPago){
        
    }

    @Override
    public void realizarReclamo(){

    }
}
