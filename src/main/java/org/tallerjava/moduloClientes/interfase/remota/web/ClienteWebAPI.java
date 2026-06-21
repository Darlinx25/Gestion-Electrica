package org.tallerjava.moduloClientes.interfase.remota.web;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.tallerjava.moduloClientes.aplicacion.CuentaServicios;
import org.tallerjava.moduloClientes.dominio.Cliente;
import org.tallerjava.moduloClientes.dominio.MedioPago;
import org.tallerjava.moduloClientes.interfase.Dtos.ClienteDTO;
import org.tallerjava.moduloClientes.interfase.Dtos.MedioPagoDTO;
import org.tallerjava.moduloClientes.interfase.Dtos.ReclamoDTO;

import java.util.List;

@ApplicationScoped
@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteWebAPI {

    @Inject
    private CuentaServicios clienteService;

    //Obtener Clientes
    //curl -v http://localhost:8080/Gestion-Electrica/API/clientes/web/listar
    @GET
    @Path("/web/listar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<ClienteDTO> obtenerClientes(){
        return clienteService.obtenerClientes();
    }

    @GET
    @Path("/web/reclamos/negativos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ReclamoDTO> obtenerReclamosNegativos() {
        return clienteService.listarReclamosPorEtiqueta("NEGATIVO");
    }

}
