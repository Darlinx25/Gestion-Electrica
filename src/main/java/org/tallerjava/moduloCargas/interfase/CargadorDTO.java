package org.tallerjava.moduloCargas.interfase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tallerjava.moduloCargas.dominio.Cargador;
import org.tallerjava.moduloCargas.dominio.EstadoCargador;
import org.tallerjava.moduloCargas.dominio.TipoCargador;
import org.tallerjava.moduloCargas.dominio.TipoConector;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CargadorDTO {
    private TipoCargador tipo;
    private boolean tieneCable;
    private TipoConector tipoConector;
    private EstadoCargador estado;
    private int potenciaMinima;
    private long estacionId;
    public Cargador buildCargador() {
        Cargador cargador = new Cargador();
        cargador.setTipo(this.tipo);
        cargador.setTieneCable(this.tieneCable);
        cargador.setTipoConector(this.tipoConector);
        cargador.setEstado(this.estado);
        cargador.setPotenciaMinima(this.potenciaMinima);
        return cargador;
    }
}