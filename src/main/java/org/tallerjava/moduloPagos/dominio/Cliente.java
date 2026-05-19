package org.tallerjava.moduloPagos.dominio;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Cliente_Pagos")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_cliente", discriminatorType = DiscriminatorType.STRING)
@Table(name = "pagos_cliente")
public abstract class Cliente {
    @Id
    private long id;
    private String cedula;
    private String nombreCompleto;
    private String telefono;
    
    @JsonbTransient
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    @OrderBy ("id ASC")
    private List<MedioPago> mediosPago = new ArrayList<>();
    
    public void addMedioPago(MedioPago medioPago) {
        if (this instanceof ClienteComun cliente && medioPago instanceof CuentaUTE ute) {
            cliente.setCuentaUte(ute);
        }
        mediosPago.add(medioPago);
        medioPago.setCliente(this);
    }
}
