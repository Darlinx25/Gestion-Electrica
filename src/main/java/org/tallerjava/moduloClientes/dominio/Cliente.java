package org.tallerjava.moduloClientes.dominio;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Cliente_Clientes")
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
    
    @JsonbTransient
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    @OrderBy ("id ASC")
    private List<MedioPago> mediosPago = new ArrayList<>();
    
    @JsonbTransient
    @OneToMany(mappedBy = "cliente")
    private List<Carga> cargas = new ArrayList<>();

    @JsonbTransient
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Reclamo> reclamos = new ArrayList<>();
    
    public void addMedioPago(MedioPago medioPago) {
        mediosPago.add(medioPago);
        medioPago.setCliente(this);
    }
    public Reclamo realizarReclamo(String informacion) {
        Reclamo reclamo = new Reclamo();
        reclamo.setFechaHora(LocalDateTime.now());
        reclamo.setInformacion(informacion);
        reclamo.setCliente(this);
        reclamos.add(reclamo);
        return reclamo;
    }
}
