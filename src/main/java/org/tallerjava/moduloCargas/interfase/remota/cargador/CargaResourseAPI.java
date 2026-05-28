package org.tallerjava.moduloCargas.interfase.remota.cargador;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.tallerjava.moduloCargas.aplicacion.CargaServicios;
import org.tallerjava.moduloCargas.dominio.EstacionCarga;
import org.tallerjava.moduloCargas.interfase.Dtos.*;


import java.time.LocalDateTime;
import java.util.List;


@ApplicationScoped
@Path("/cargas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CargaResourseAPI {
    @Inject
    private CargaServicios cargaService;

    //Actualizar % avance de carga
    //curl -X POST -v "http://localhost:8080/Gestion-Electrica/API/cargas/actualizar" -H "Content-Type: application/json" -d '{"cargaId": 1,"porcentajeAvance":25}'
    @POST
    @Path("/actualizar")
    public void actualizarCarga(EstadoCargaDTO estadoCargaDTO){
        cargaService.actualizarEstadoCarga(estadoCargaDTO);
    }

    //Finalizar Carga
    //curl -X POST -v http://localhost:8080/Gestion-Electrica/API/cargas/finalizar -H "Content-Type: application/json" -d '{"clienteId":1, "carga":50.0}'
    @POST
    @Path("/finalizar")
    public void finalizarCarga(FinalizarCargaRequestDTO cargaDTO) {
        cargaService.finalizarCarga(cargaDTO);
    }


}





