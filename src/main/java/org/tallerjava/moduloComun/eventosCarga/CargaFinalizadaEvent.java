package org.tallerjava.moduloComun.eventosCarga;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CargaFinalizadaEvent {
    private long cargaId;
    private long clienteId;
    private long medioPagoId;
    private float importeTotal;
    private float recargo;
    private float carga;
    public CargaFinalizadaEvent(long cargaId, long clienteId, float carga, long medioPagoId,float importeTotal, float recargo) {
        this.cargaId = cargaId;
        this.clienteId = clienteId;
        this.carga = carga;
        this.medioPagoId = medioPagoId;
        this.importeTotal = importeTotal;
        this.recargo = recargo;
    }
}