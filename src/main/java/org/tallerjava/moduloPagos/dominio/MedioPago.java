package org.tallerjava.moduloPagos.dominio;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance (strategy = InheritanceType.JOINED)
@Table(name = "pagos_medioPago")
public abstract class MedioPago {
    @Id
    private long id;
    
    @JsonbTransient
    @ManyToOne
    private Cliente cliente;
}
