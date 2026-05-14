package org.tallerjava.moduloCargas.dominio;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cargas_estacionCarga")
public class EstacionCarga {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    
    private String descripcion;
    private String calle;
    private String departamento;
    private int longitud;
    private int latitud;
    
    @JsonbTransient
    @OneToMany(mappedBy = "estacionCarga", cascade = CascadeType.ALL)
    private List<Cargador> cargadores;
}
