package org.tallerjava.moduloClientes.dominio.interfase;


import jakarta.servlet.http.PushBuilder;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.awt.*;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResourse {


    // http://localhost:8080/Gestion-Electrica/trafico/clientes Luego vemos de mejorar la URL
    @GET
    public String clinestes(){
        return "anduvo";
    }
}
