package org.tallerjava.moduloPagos.dominio;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "ClienteComun_Pagos")
@DiscriminatorValue("COMUN")
@Table (name = "pagos_clienteComun")
public class ClienteComun extends Cliente {
    
    @JsonbTransient
    @OneToOne
    private CuentaUTE cuentaUte;
}
