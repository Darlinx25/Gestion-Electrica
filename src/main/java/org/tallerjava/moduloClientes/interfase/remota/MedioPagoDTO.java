package org.tallerjava.moduloClientes.interfase.remota;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tallerjava.moduloClientes.dominio.CuentaUTE;
import org.tallerjava.moduloClientes.dominio.MedioPago;
import org.tallerjava.moduloClientes.dominio.Tarjeta;
import org.tallerjava.moduloClientes.dominio.TipoTarjeta;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedioPagoDTO {
    public enum TipoMedio {
        TARJETA_DEBITO,
        TARJETA_CREDITO,
        CUENTA_UTE
    }
    
    private TipoMedio medio;
    private long clienteId;
    
    //tarjeta
    private String numero;
    private LocalDate fechaVencimiento;
    private String digitoVerificacion;
    
    //ute
    private String numeroCuenta;
    
    public MedioPago buildMedioPago() {
        MedioPago medioPago;
        if (this.medio == TipoMedio.CUENTA_UTE) {
            medioPago = new CuentaUTE();
            ((CuentaUTE) medioPago).setNumeroCuenta(this.numeroCuenta);
        } else {
            medioPago = new Tarjeta();
            ((Tarjeta) medioPago).setNumero(this.numero);
            ((Tarjeta) medioPago).setFechaVencimiento(this.fechaVencimiento);
            ((Tarjeta) medioPago).setDigitoVerificacion(this.digitoVerificacion);
            if (this.medio == TipoMedio.TARJETA_DEBITO) {
                ((Tarjeta) medioPago).setTipo(TipoTarjeta.DEBITO);
            } else {
                ((Tarjeta) medioPago).setTipo(TipoTarjeta.CREDITO);
            }
        }
        
        return medioPago;
    }
}
