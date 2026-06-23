package org.tallerjava.moduloComun.eventosCliente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ReclamoEtiquetadoEvent {
    private long reclamoId;
    private String etiqueta;
}