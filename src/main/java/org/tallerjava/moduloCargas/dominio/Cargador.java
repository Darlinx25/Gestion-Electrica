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
@Table(name = "cargas_cargador")
public class Cargador {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    private TipoCargador tipo;
    private boolean tieneCable;
    private TipoConector tipoConector;
    private EstadoCargador estado;
    private LocalDateTime tiempoEstimadoFinalizacion;
    private LocalDate fechaEstimadaReparacion;
    private int potenciaMinima;
}
