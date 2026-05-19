package org.tallerjava.moduloComun.eventosCliente;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientesAltaMedioPago {
    public enum TipoMedio {
        TARJETA_DEBITO,
        TARJETA_CREDITO,
        CUENTA_UTE
    }
    private TipoMedio medio;
    private long medioId;
    
    private long clienteId;
    
    //tarjeta
    private String numero;
    private LocalDate fechaVencimiento;
    private String digitoVerificacion;
    
    //ute
    private String numeroCuenta;
}
