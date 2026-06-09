package org.tallerjava.moduloMonitoreo.infraestructura;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.influx.InfluxConfig;
import io.micrometer.influx.InfluxMeterRegistry;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class RegistradorDeMetricas {

    public static final String CARGAS_ACTIVAS = "cargasActivas";
    public static final String CARGAS_REALIZADAS = "cargasRealizadas";
    public static final String PAGOS_UTE_REALIZADOS = "pagosUTERealizados";
    public static final String PAGOS_TARJETA_REALIZADOS = "pagosTarjetaRealizados";
    public static final String PAGOS_TARJETA_ERROR = "pagosTarjetaError";

    private InfluxConfig config;
    
    private MeterRegistry meterRegistry;
    private final Map<String, AtomicInteger> gaugeMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        config = new InfluxConfig() {
            @Override
            public String get(String s) {
                return null;
            }

            @Override
            public Duration step() {
                return Duration.ofSeconds(10);
            }

            @Override
            public String db() {
                return "metricasTallerJava";
            }
            
            @Override
            public String uri() {
                return "http://influxdb:8086";
            }
        };
        this.meterRegistry = new InfluxMeterRegistry(config, Clock.SYSTEM);
    }

    public void incrementarCounter(String nombreCounter) {
        meterRegistry.counter(nombreCounter).increment();
    }
    
    public void incrementarGauge(String nombreGauge) {
        gaugeMap.computeIfAbsent(nombreGauge, key -> {
            AtomicInteger val = new AtomicInteger(0);
            meterRegistry.gauge(key, val);
            return val;
        }).incrementAndGet();
    }

    public void decrementarGauge(String nombreGauge) {
        AtomicInteger val = gaugeMap.get(nombreGauge);
        if (val != null) {
            val.decrementAndGet();
        }
    }
}
