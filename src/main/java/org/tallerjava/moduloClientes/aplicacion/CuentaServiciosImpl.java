package org.tallerjava.moduloClientes.aplicacion;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.tallerjava.moduloClientes.dominio.Cliente;
import org.tallerjava.moduloClientes.dominio.repositorios.ClienteRepo;
import org.tallerjava.moduloPagos.dominio.MedioPago;

import java.util.List;

public class CuentaServiciosImpl implements CuentaServicios{

    @Inject
    private ClienteRepo clienteRepo;

    @Override
    public List<Cliente> obtenerClientes() {
        return clienteRepo.listarClientes();
    }

    @Override
    public void registarCliente(Cliente cliente) {
        clienteRepo.registarCliente(cliente);
    }

    @Override
    public void altaMedioPago(Cliente cliente, MedioPago medioPago){

    }

    @Override
    public void realizarReclamo(){

    }
}
