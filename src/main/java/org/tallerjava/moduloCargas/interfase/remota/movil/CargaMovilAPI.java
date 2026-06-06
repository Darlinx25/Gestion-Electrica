package org.tallerjava.moduloCargas.interfase.remota.movil;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import org.tallerjava.moduloCargas.infraestructura.rateLimiter.RateLimited;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import org.tallerjava.moduloCargas.aplicacion.CargaServicios;
import org.tallerjava.moduloCargas.dominio.EstacionCarga;
import org.tallerjava.moduloCargas.interfase.Dtos.*;

import java.time.LocalDateTime;
import java.util.List;
import org.tallerjava.moduloCargas.dominio.Cliente;

@ApplicationScoped
@Path("/cargas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CargaMovilAPI {

    @Inject
    private CargaServicios cargaService;

    //Iniciar Carga
    //curl -X POST -v "http://localhost:8080/Gestion-Electrica/API/cargas/movil/iniciar" -H "Content-Type: application/json" -d '{"clienteId": 1,"medioPagoId":1,"cargadorId":1}'
    @POST
    @Path("/movil/iniciar")
    @RolesAllowed("USER")
    public void iniciarCarga(IniciarCargaRequestDTO cargaDTO,
            @Context SecurityContext securityContext) {

        String idUsuarioString = securityContext.getUserPrincipal().getName();

        long authenticatedClienteId = Long.parseLong(idUsuarioString);

        Cliente cliente = cargaService.buscarClientePorId(authenticatedClienteId);
        
        boolean tieneMedioPago = cliente.tieneMedioPago(cargaDTO.getMedioPagoId());
        
        if (authenticatedClienteId != cargaDTO.getClienteId() || !tieneMedioPago) {
            throw new ForbiddenException();
        }

        cargaService.iniciarCarga(cargaDTO);
    }

    //Ver Carga Actual
    //curl -v http://localhost:8080/Gestion-Electrica/API/cargas/movil/carga-actual/1
    @GET
    @Path("/movil/carga-actual/{clienteId}")
    @RolesAllowed("USER")
    public CargaDTO verCargaActual(@PathParam("clienteId") long clienteId,
            @Context SecurityContext securityContext) {
        
        String idUsuarioString = securityContext.getUserPrincipal().getName();

        long authenticatedClienteId = Long.parseLong(idUsuarioString);
        
        if (authenticatedClienteId != clienteId) {
            throw new ForbiddenException();
        }
        
        return cargaService.verCargaActual(clienteId);
    }

    //Obtener Estaciones
    //curl -v http://localhost:8080/Gestion-Electrica/API/cargas/movil/estaciones
    @GET
    @Path("/movil/estaciones")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"USER", "ADMIN"})
    public List<EstacionCarga> obtenerEstaciones() {
        return cargaService.obtenerEstaciones();
    }

    //Consultar las cargas de un cliente entre 2 fechas/horas
    //curl "http://localhost:8080/Gestion-Electrica/API/cargas/movil/historico/1?ini=2026-05-19T20:00:00&fin=2026-05-19T20:10:00"
    @GET
    @Path("/movil/historico/{clienteId}")
    @RateLimited
    @RolesAllowed({"USER", "ADMIN"})
    public List<CargaDTO> verHistorico(@PathParam("clienteId") Long clienteId, @QueryParam("ini") String ini, @QueryParam("fin") String fin,
            @Context SecurityContext securityContext) {
        
        String idUsuarioString = securityContext.getUserPrincipal().getName();

        long authenticatedClienteId = Long.parseLong(idUsuarioString);
        
        if (securityContext.isUserInRole("USER") && authenticatedClienteId != clienteId) {
            throw new ForbiddenException();
        }
        
        LocalDateTime fechaIni = LocalDateTime.parse(ini);
        LocalDateTime fechaFin = LocalDateTime.parse(fin);
        return cargaService.verHistorico(clienteId, fechaIni, fechaFin);
    }
}
