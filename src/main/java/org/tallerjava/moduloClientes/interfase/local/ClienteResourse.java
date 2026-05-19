package org.tallerjava.moduloClientes.interfase.local;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.tallerjava.moduloClientes.interfase.remota.ClienteDTO;
import org.tallerjava.moduloComun.eventosCarga.CargaIniciadaEvent;
import org.tallerjava.moduloClientes.aplicacion.CuentaServicios;
import org.tallerjava.moduloClientes.dominio.Cliente;
import org.tallerjava.moduloClientes.dominio.MedioPago;

import java.util.List;

@ApplicationScoped
public class ClienteResourse {

    @Inject
    private CuentaServicios clienteService;

    public long registrarCliente(Cliente cliente) {
        return clienteService.registarCliente(cliente);
    }

    public void altaMedioPago(Long clienteID, MedioPago medioPago){
        clienteService.altaMedioPago(clienteID,medioPago);
    }

    public List<ClienteDTO> obtenerClientes(){
        return clienteService.obtenerClientes();
    }

    public long realizarReclamo(long clienteId, String informacion){
        return clienteService.realizarReclamo(clienteId, informacion);
    }


    public void onCargaIniciada(@Observes CargaIniciadaEvent event) {
        //aca la logica, hay que hacerlo, esta funcion por ejemplo escucha cuando disparo al iniciar una carga

        System.out.println("entro la publicacion");
        System.out.println(event.getClienteId());
        System.out.println(event.getCargaId());

    }


}
