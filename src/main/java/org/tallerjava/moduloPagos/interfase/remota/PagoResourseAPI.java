package org.tallerjava.moduloPagos.interfase.remota;


import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import org.tallerjava.moduloCargas.interfase.Dtos.CargaDTO;
import org.tallerjava.moduloPagos.aplicacion.PagoServicios;
import org.tallerjava.moduloPagos.dominio.Carga;

import java.time.LocalDateTime;
import java.util.List;
import org.tallerjava.moduloPagos.dominio.Cliente;

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

    //curl -u 1234567890:123 -X POST -v "http://localhost:8080/Gestion-Electrica/API/pagos/web/pagarCarga/1?medioPagoId=2"
    @POST
    @Path("/web/pagarCarga/{clienteId}")
    @RolesAllowed("USER")
    public void pagarCarga(@PathParam("clienteId") Long clienteId, @QueryParam("medioPagoId") long medioPagoId,
            @Context SecurityContext securityContext){
        
         String idUsuarioString = securityContext.getUserPrincipal().getName();

        long authenticatedClienteId = Long.parseLong(idUsuarioString);

        Cliente cliente = pagoService.buscaClientePorId(authenticatedClienteId);
        
        boolean tieneMedioPago = cliente.tieneMedioPago(medioPagoId);
        
        if (authenticatedClienteId != clienteId || !tieneMedioPago) {
            throw new ForbiddenException();
        }
        
    Carga pagar = pagoService.cargaSinPagar(clienteId);
        if(pagar == null){
            return;
        }else{
            pagoService.pagarCarga(clienteId, pagar.getImporteTotal(), medioPagoId,pagar.getId());
        }
    }

}
