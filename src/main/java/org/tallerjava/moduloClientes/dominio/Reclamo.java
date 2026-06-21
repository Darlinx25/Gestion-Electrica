package org.tallerjava.moduloClientes.dominio;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cargas_reclamo")
public class Reclamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime fechaHora;
    private String informacion;
    private String etiqueta;

    @JsonbTransient
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}