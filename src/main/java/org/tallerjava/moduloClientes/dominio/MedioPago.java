package org.tallerjava.moduloClientes.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "MedioPago_Clientes") //no puedo tener dos entidades que se llamen igual
@Inheritance (strategy = InheritanceType.JOINED)
@Table(name = "clientes_medioPago")
public abstract class MedioPago {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
}