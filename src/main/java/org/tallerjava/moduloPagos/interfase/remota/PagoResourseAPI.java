package org.tallerjava.moduloPagos.interfase.remota;


import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.tallerjava.moduloCargas.interfase.Dtos.CargaDTO;
import org.tallerjava.moduloPagos.aplicacion.PagoServicios;
import org.tallerjava.moduloPagos.dominio.Carga;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
@Path("/pagos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PagoResourseAPI {

    @Inject
    private PagoServicios pagoService;

    //curl -v "http://localhost:8080/Gestion-Electrica/API/pagos/web/pagosRealizados/1?ini=2026-05-19T20:00:00&fin=2027-05-19T20:10:00"
    @GET
    @Path("/web/pagosRealizados/{clienteId}")
    @RolesAllowed("ADMIN")
    public List<CargaDTO> consultarPagos(@PathParam("clienteId") Long clienteId, @QueryParam("ini") String ini, @QueryParam("fin") String fin){
        LocalDateTime fechaIni = LocalDateTime.parse(ini);
        LocalDateTime fechaFin = LocalDateTime.parse(fin);
        return pagoService.consultarPagos(clienteId, fechaIni, fechaFin);
    }

    //curl -X POST -v "http://localhost:8080/Gestion-Electrica/API/pagos/web/pagarCarga/1?medioPagoId=2"
    @POST
    @Path("/web/pagarCarga/{clienteId}")
    public void pagarCarga(@PathParam("clienteId") Long clienteId, @QueryParam("medioPagoId") long medioPagoId){
    Carga pagar = pagoService.cargaSinPagar(clienteId);
        if(pagar == null){
            return;
        }else{
            pagoService.pagarCarga(clienteId, pagar.getImporteTotal(), medioPagoId,pagar.getId());
        }
    }

}
