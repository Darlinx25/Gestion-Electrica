package org.tallerjava.moduloCargas.infraestructura.rateLimiter;
import java.io.IOException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
@RateLimited
@Provider
@ApplicationScoped
public class RateLimiterFiltro implements ContainerRequestFilter {
    @Inject
    private RateLimiter rateLimiter;
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (rateLimiter.isActivo() && !rateLimiter.consumir()) {
            requestContext.abortWith(
                    Response.status(Response.Status.TOO_MANY_REQUESTS).entity("").build()
            );
        }
    }
    public RateLimiter getRateLimiter() {
        return rateLimiter;
    }
}