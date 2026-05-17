package org.tallerjava.moduloClientes.interfase.local;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.tallerjava.moduloClientes.aplicacion.CuentaServicios;
import org.tallerjava.moduloClientes.dominio.Cliente;
import org.tallerjava.moduloClientes.interfase.remota.ClienteDTO;
import org.tallerjava.moduloClientes.dominio.MedioPago;

import java.util.List;

@ApplicationScoped
public class ClienteResourse {

    @Inject
    private CuentaServicios clienteService;

    public long registrarCliente(ClienteDTO clienteDTO) {
        Cliente cliente = clienteDTO.buildCliente();
        return clienteService.registarCliente(cliente);
    }

    public void altaMedioPago(Long clienteID, MedioPago medioPago){
        clienteService.altaMedioPago(clienteID,medioPago);
    }

    public List<Cliente> obtenerClientes(){
        return clienteService.obtenerClientes();
    }

    public void realizarReclamo(){
        clienteService.realizarReclamo();
    }

}
