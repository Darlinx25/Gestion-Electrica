package org.tallerjava.moduloCargas.dominio;

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
@Entity(name = "ClienteProfesional_Cargas")
@DiscriminatorValue("PROFESIONAL")
@Table (name = "cargas_clienteProfesional")
public class ClienteProfesional extends Cliente {
    private TipoProfesional tipo;
    private float porcentajeDescuento;
}
