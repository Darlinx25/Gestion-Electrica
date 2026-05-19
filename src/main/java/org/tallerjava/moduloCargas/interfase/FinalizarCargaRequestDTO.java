package org.tallerjava.moduloCargas.interfase;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FinalizarCargaRequestDTO {
    private long clienteId;
    private float carga;



}
