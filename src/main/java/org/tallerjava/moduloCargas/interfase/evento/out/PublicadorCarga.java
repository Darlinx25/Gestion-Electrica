package org.tallerjava.moduloCargas.interfase.evento.out;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface PublicadorCarga {
    public void iniciarCarga(long cargaId,long clienteId);
    public void finalizarCarga(long cargaId, long clienteId, float carga, long medioPagoId, float importeTotal, float recargo, LocalDate fecha, LocalDateTime horaInicio, LocalDateTime horaFin);
}
