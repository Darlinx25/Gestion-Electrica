package org.tallerjava.moduloComun.eventosCarga;

import lombok.Data;
import org.tallerjava.moduloCargas.dominio.EstadoCarga;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CargaFinalizadaEvent {
    private long cargaId;
    private long clienteId;
    private long medioPagoId;
    private float importeTotal;
    private float recargo;
    private float carga;
    private LocalDate fecha;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
    public CargaFinalizadaEvent(long cargaId, long clienteId, float carga, long medioPagoId,float importeTotal,
                                float recargo, LocalDate fecha,LocalDateTime horaInicio,LocalDateTime horaFin ) {
        this.cargaId = cargaId;
        this.clienteId = clienteId;
        this.carga = carga;
        this.medioPagoId = medioPagoId;
        this.importeTotal = importeTotal;
        this.recargo = recargo;
        this.fecha = fecha;
        this.horaFin = horaFin;
        this.horaInicio = horaInicio;
    }
}