package org.tallerjava.moduloPagos.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "pagos_pagoTarjeta")
public class Tarjeta extends MedioPago {
    private String numero;
    private LocalDate fechaVencimiento;
    private String digitoVerificacion;
    private TipoTarjeta tipo;
}
