package org.tallerjava.moduloClientes.dominio;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.tallerjava.moduloPagos.dominio.CuentaUTE;

@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("COMUN")
@Table (name = "clientes_clienteComun")
public class ClienteComun extends Cliente {
    @OneToOne
    private CuentaUTE cuentaUte;
}
