package org.tallerjava.moduloClientes.interfase;


import jakarta.inject.Inject;
import jakarta.servlet.http.PushBuilder;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.tallerjava.moduloClientes.dominio.Cliente;
import org.tallerjava.moduloClientes.dominio.repositorios.ClienteRepo;
import java.util.List;

import java.awt.*;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResourse {


    @Inject
    private ClienteRepo clienteRepo;

    //http://localhost:8080/Gestion-Electrica/carga/clientes Luego vemos de mejorar la URL, por ahora con "carga"
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cliente> clinestes(){
        return clienteRepo.listarClientes();
    }


}
