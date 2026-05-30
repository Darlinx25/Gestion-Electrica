package org.tallerjava.moduloCargas.infraestructura.rateLimiter;
import org.jboss.weld.junit5.EnableWeld;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
@EnableWeld
class RateLimiterTest {
    @WeldSetup
    public WeldInitiator weld = WeldInitiator.from(RateLimiter.class).build();
    @Test
    @DisplayName("al iniciar, el rate limiter esta activo por defecto")
    void inicializar_activoPorDefecto(RateLimiter rateLimiter) {
        assertTrue(rateLimiter.isActivo());
    }
    @Test
    @DisplayName("consumir retorna true mientras haya tokens disponibles")
    void consumir_retornaTrue_mientrasHayaTokens(RateLimiter rateLimiter) {
        for (int i = 0; i < 10; i++) {
            assertTrue(rateLimiter.consumir());
        }
    }
    @Test
    @DisplayName("consumir retorna false cuando se agotan los tokens")
    void consumir_retornaFalse_cuandoSeAgotanLosTokens(RateLimiter rateLimiter) {
        for (int i = 0; i < 10; i++) {
            rateLimiter.consumir();
        }
        assertFalse(rateLimiter.consumir());
    }
    @Test
    @DisplayName("despues de esperar el refill, vuelve a haber tokens")
    void consumir_retornaTrue_despuesDeRefill(RateLimiter rateLimiter) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            rateLimiter.consumir();
        }
        Thread.sleep(300);
        assertTrue(rateLimiter.consumir());
    }
    @Test
    @DisplayName("activarRateLimiter cambia el estado correctamente")
    void activarRateLimiter_cambiaEstado(RateLimiter rateLimiter) {
        rateLimiter.activarRateLimiter(false);
        assertFalse(rateLimiter.isActivo());
        rateLimiter.activarRateLimiter(true);
        assertTrue(rateLimiter.isActivo());
    }
}