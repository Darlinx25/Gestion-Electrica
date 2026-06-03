package org.tallerjava.moduloCargas.dominio;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cargas_carga")
public class Carga {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate fecha;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
    private float importeTotal;
    private float recargoPorDemora;
    private int porcentajeAvance;
    private LocalDateTime horaEstimadaFin;
    private EstadoCarga estado;
    private long medioPagoId;
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean pagado;

    @JsonbTransient
    @ManyToOne
    @JoinColumn (name = "cliente_id")
    private Cliente cliente;
    
    @JsonbTransient
    @ManyToOne
    @JoinColumn (name = "cargador_id")
    private Cargador cargador;
    
    
   
}
