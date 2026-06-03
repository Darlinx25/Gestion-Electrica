package org.tallerjava.moduloCargas.interfase.remota.web;


import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.tallerjava.moduloCargas.aplicacion.CargaServicios;
import org.tallerjava.moduloCargas.interfase.Dtos.*;


@ApplicationScoped
@Path("/cargas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CargaWebAPI {
    @Inject
    private CargaServicios cargaService;

    //Registrar estacion
    //curl -X POST -v http://localhost:8080/Gestion-Electrica/API/cargas/web/estacion -H "Content-Type: application/json" -d '{"descripcion":"prueba estacion","calle":"Lenguas de Diamante","departamento":"Maldonado","longitud":2,"latitud":3}'
    @POST
    @Path("/web/estacion")
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    public long altaEstacion(EstacionDTO estacionDTO) {
        return cargaService.altaEstacion(estacionDTO);
    }

    //Registrar cargador
    //curl -X POST -v http://localhost:8080/Gestion-Electrica/API/cargas/web/cargador -H "Content-Type: application/json" -d '{"tipo": "RAPIDA", "tieneCable": true, "tipoConector": "TIPO2", "estado": "DISPONIBLE", "potenciaMinima": 150, "estacionId": 1}'
    @POST
    @Path("/web/cargador")
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    public long altaCargador(CargadorDTO cargadorDTO) {
        return cargaService.altaCargador(cargadorDTO);
    }


}





