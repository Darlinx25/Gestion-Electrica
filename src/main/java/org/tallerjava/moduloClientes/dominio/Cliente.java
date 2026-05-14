package org.tallerjava.moduloClientes.dominio;

import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tallerjava.moduloCargas.dominio.Carga;
import org.tallerjava.moduloPagos.dominio.MedioPago;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance (strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_cliente", discriminatorType = DiscriminatorType.STRING)
@Table(name = "clientes_cliente")
public abstract class Cliente {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    private String cedula;
    private String nombreCompleto;
    private String telefono;
    private String password;
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    @OrderBy ("id ASC")
    private List<MedioPago> mediosPago;
    
    @OneToMany(mappedBy = "cliente")
    private List<Carga> cargas;
}
