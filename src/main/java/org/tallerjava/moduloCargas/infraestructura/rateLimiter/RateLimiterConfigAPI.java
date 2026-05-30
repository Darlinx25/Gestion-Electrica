package org.tallerjava.moduloCargas.infraestructura.rateLimiter;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cargas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class RateLimiterConfigAPI {
    @Inject
    private RateLimiter rateLimiter;
    @GET
    @Path("/movil/config/rateLimiter")
    public Response activarRateLimiter(@QueryParam("activo") boolean nuevoEstado) {
        rateLimiter.activarRateLimiter(nuevoEstado);
        return Response.ok("RateLimiter " + (nuevoEstado ? "activado" : "desactivado")).build();
    }
}

/*
# Desactivar rate limiter
curl "http://localhost:8080/Gestion-Electrica/API/cargas/movil/config/rateLimiter?activo=false"
# Reactivar
curl "http://localhost:8080/Gestion-Electrica/API/cargas/movil/config/rateLimiter?activo=true"
 */