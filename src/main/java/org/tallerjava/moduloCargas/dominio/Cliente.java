package org.tallerjava.moduloCargas.dominio;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Cliente_Cargas")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_cliente", discriminatorType = DiscriminatorType.STRING)
@Table(name = "cargas_cliente")
public abstract class Cliente {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    private String cedula;
    private String nombreCompleto;
    private String telefono;
    private String password;


}
