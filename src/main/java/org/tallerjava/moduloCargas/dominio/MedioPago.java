package org.tallerjava.moduloCargas.dominio;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tallerjava.moduloClientes.dominio.Cliente;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "MedioPago_Cargas")
@Inheritance (strategy = InheritanceType.JOINED)
@Table(name = "cargas_medioPago")
public abstract class MedioPago {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Cliente cliente;
}