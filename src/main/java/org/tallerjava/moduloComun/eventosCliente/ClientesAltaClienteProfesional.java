package org.tallerjava.moduloComun.eventosCliente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientesAltaClienteProfesional {

    private long id;
    private String cedula;
    private String nombreCompleto;
    private String telefono;

    public enum TipoProfesional {
        TAXI,
        UBER,
        CABIFY
    }

    private TipoProfesional tipo;
    private float porcentajeDescuento;
}
