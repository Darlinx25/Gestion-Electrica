package org.tallerjava.moduloCargas.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
}
