package org.tallerjava.moduloCargas.infraestructura.rateLimiter;
import java.time.Duration;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
@ApplicationScoped
public class RateLimiter {
    private Bucket bucket;
    private volatile boolean activo;
    @PostConstruct
    public void inicializar() {
        activo = true;
        Bandwidth bucketConf = Bandwidth.builder()
                .capacity(10)
                .refillGreedy(5, Duration.ofSeconds(1))
                .build();
        bucket = Bucket.builder().addLimit(bucketConf).build();
    }
    public boolean consumir() {
        return bucket.tryConsume(1);
    }
    public void activarRateLimiter(boolean estado) {
        this.activo = estado;
    }
    public boolean isActivo() {
        return this.activo;
    }
    public void vaciarBucket() {
        this.bucket = Bucket.builder()
                .addLimit(Bandwidth.builder().capacity(1).refillGreedy(1, Duration.ofDays(1)).build())
                .build();
        bucket.tryConsume(1);
    }
}