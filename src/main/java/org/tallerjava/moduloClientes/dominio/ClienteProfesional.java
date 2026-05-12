package org.tallerjava.moduloClientes.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "clientes_clienteProfesional")
public class ClienteProfesional extends Cliente {
    private TipoProfesional tipo;
    private float porcentajeDescuento;
}
