package org.tallerjava.moduloCargas.interfase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CargaDTO {
    private long id;
    private LocalDate fecha;
    private LocalDateTime horaInicio;
    private int porcentajeAvance;
    private LocalDateTime horaEstimadaFin;
    private String estado;
    private long clienteId;
    private Long cargadorId;
}