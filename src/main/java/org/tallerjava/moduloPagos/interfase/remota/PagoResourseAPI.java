package org.tallerjava.moduloPagos.interfase.remota;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.tallerjava.moduloCargas.interfase.CargaDTO;
import org.tallerjava.moduloPagos.aplicacion.PagoServicios;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
@Path("/pagos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PagoResourseAPI {

    @Inject
    private PagoServicios pagoService;

    //curl "http://localhost:8080/Gestion-Electrica/carga/pagos/pagosRealizados/1?ini=2026-05-19T20:00:00&fin=2026-05-19T20:10:00"
    @GET
    @Path("/pagosRealizados/{clienteId}")
    public List<CargaDTO> consultarPagos(@PathParam("clienteId") Long clienteId, @QueryParam("ini") String ini, @QueryParam("fin") String fin){
        LocalDateTime fechaIni = LocalDateTime.parse(ini);
        LocalDateTime fechaFin = LocalDateTime.parse(fin);
        return pagoService.consultarPagos(clienteId, fechaIni, fechaFin);
    }

}
