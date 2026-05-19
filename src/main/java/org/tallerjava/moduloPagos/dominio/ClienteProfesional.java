package org.tallerjava.moduloPagos.dominio;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity(name = "ClienteProfesional_Pagos")
@DiscriminatorValue("PROFESIONAL")
@Table (name = "pagos_clienteProfesional")
public class ClienteProfesional extends Cliente {
    private TipoProfesional tipo;
    private float porcentajeDescuento;
}
