package org.tallerjava.moduloCargas.interfase;

import lombok.Data;


@Data
public class IniciarCargaRequestDTO {
    private long clienteId;
    private long medioPagoId;
    private long cargadorId;
}
