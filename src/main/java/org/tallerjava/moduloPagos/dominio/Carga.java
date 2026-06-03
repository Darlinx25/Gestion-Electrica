package org.tallerjava.moduloPagos.dominio;

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
@Entity(name = "Carga_Pagos")
@Table(name = "pagos_carga")
public class Carga {
    @Id
    private long id;
    private LocalDate fecha;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
    private float importeTotal;
    private float recargoPorDemora;
    private int porcentajeAvance;
    private long medioPagoId;
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean pagado;

    @JsonbTransient
    @ManyToOne
    @JoinColumn (name = "cliente_id")
    private Cliente cliente;






}
