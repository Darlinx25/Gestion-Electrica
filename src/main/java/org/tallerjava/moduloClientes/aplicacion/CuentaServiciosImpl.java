package org.tallerjava.moduloClientes.aplicacion;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.tallerjava.moduloClientes.dominio.Cliente;
import org.tallerjava.moduloClientes.dominio.Reclamo;
import org.tallerjava.moduloClientes.dominio.repositorios.ClienteRepo;
import org.tallerjava.moduloClientes.dominio.MedioPago;
import java.util.List;
import org.tallerjava.moduloClientes.interfase.evento.out.PublicadorEventoCliente;

@ApplicationScoped
public class CuentaServiciosImpl implements CuentaServicios{

    @Inject
    private ClienteRepo clienteRepo;
    
    @Inject
    private PublicadorEventoCliente evento;

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
    public boolean altaMedioPago(long clienteId, MedioPago medioPago){
        Cliente cliente = clienteRepo.buscaClientePorId(clienteId);
        if (cliente == null) {
            return false;
        }
        cliente.addMedioPago(medioPago);
        clienteRepo.altaMedioPago(medioPago);
        evento.publicarNuevoMedioPago(medioPago);
        return true;
    }

    @Override
    @Transactional
    public long realizarReclamo(long clienteId, String informacion) {
        Cliente cliente = clienteRepo.buscaClientePorId(clienteId);
        if (cliente == null) {
            throw new IllegalArgumentException("No existe cliente con id: " + clienteId);
        }
        Reclamo reclamo = cliente.realizarReclamo(informacion);
        clienteRepo.guardarReclamo(reclamo);
        return reclamo.getId();
    }
    
}
