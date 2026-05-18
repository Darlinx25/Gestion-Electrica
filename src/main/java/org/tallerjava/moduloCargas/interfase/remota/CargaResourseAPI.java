package org.tallerjava.moduloCargas.interfase.remota;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.tallerjava.moduloCargas.aplicacion.CargaServicios;
import org.tallerjava.moduloCargas.dominio.EstacionCarga;
import org.tallerjava.moduloClientes.dominio.Cliente;

import java.util.List;


@ApplicationScoped
@Path("/cargas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CargaResourseAPI {
    @Inject
    private CargaServicios cargaService;


    //curl -v http://localhost:8080/Gestion-Electrica/carga/cargas
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<EstacionCarga> obtenerEstaciones(){
        return cargaService.obtenerEstaciones();
    }

}
