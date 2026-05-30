package org.tallerjava.moduloCargas.infraestructura.rateLimiter;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Response;
import org.jboss.weld.junit5.EnableWeld;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
@EnableWeld
class RateLimiterFiltroTest {
    @WeldSetup
    public WeldInitiator weld = WeldInitiator.from(RateLimiter.class, RateLimiterFiltro.class).build();
    @Test
    @DisplayName("cuando esta activo y sin tokens, aborta con 429")
    void filter_cuandoActivoYsinTokens_abortaCon429(RateLimiterFiltro filtro) throws Exception {
        RateLimiter rateLimiter = filtro.getRateLimiter();
        rateLimiter.vaciarBucket();
        ContainerRequestContext ctx = Mockito.mock(ContainerRequestContext.class);
        filtro.filter(ctx);
        verify(ctx).abortWith(argThat(r -> r.getStatus() == 429));
    }
    @Test
    @DisplayName("cuando esta activo y con tokens, no aborta")
    void filter_cuandoActivoYconTokens_noAborta(RateLimiterFiltro filtro) throws Exception {
        ContainerRequestContext ctx = Mockito.mock(ContainerRequestContext.class);
        filtro.filter(ctx);
        verify(ctx, never()).abortWith(any());
    }
    @Test
    @DisplayName("cuando esta inactivo, no aborta aunque no haya tokens")
    void filter_cuandoInactivo_noAborta(RateLimiterFiltro filtro) throws Exception {
        RateLimiter rateLimiter = filtro.getRateLimiter();
        rateLimiter.activarRateLimiter(false);
        rateLimiter.vaciarBucket();
        ContainerRequestContext ctx = Mockito.mock(ContainerRequestContext.class);
        filtro.filter(ctx);
        verify(ctx, never()).abortWith(any());
    }
}