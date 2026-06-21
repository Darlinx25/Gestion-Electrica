package org.tallerjava.moduloClientes.interfase.Dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReclamoDTO {
    private long clienteId;
    private String informacion;
    private String etiqueta;
}