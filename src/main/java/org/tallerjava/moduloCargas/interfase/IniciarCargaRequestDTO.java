package org.tallerjava.moduloCargas.interfase;

import lombok.Data;


@Data
public class IniciarCargaRequestDTO {
    private long clienteId;
    private TipoMedioDTO tipoMedioDTO;
    private long cargadorId;

    public enum TipoMedioDTO {
        TARJETA,
        CUENTA_UTE
    }
}
