package org.tallerjava.moduloComun.eventosCarga;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CargaFinalizadaEvent {
    private long cargaId;
    private long clienteId;
    private long medioPagoId;
    private double importeTotal;
    private double recargo;
    private LocalDateTime fechaFin;
    public CargaFinalizadaEvent(long cargaId, long clienteId, long medioPagoId,double importeTotal, double recargo, LocalDateTime fechaFin) {
        this.cargaId = cargaId;
        this.clienteId = clienteId;
        this.medioPagoId = medioPagoId;
        this.importeTotal = importeTotal;
        this.recargo = recargo;
        this.fechaFin = fechaFin;
    }
}