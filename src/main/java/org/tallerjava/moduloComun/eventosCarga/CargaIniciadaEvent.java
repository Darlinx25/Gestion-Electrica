package org.tallerjava.moduloComun.eventosCarga;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CargaIniciadaEvent {
    private long clienteId;
    private long cargaId;
    private LocalDateTime fechaInicio;
    public CargaIniciadaEvent(long cargaId, long clienteId, LocalDateTime fechaInicio) {
        this.cargaId = cargaId;
        this.clienteId = clienteId;
        this.fechaInicio = fechaInicio;
    }
}
