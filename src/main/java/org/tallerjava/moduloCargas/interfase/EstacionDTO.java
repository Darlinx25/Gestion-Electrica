package org.tallerjava.moduloCargas.interfase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tallerjava.moduloCargas.dominio.EstacionCarga;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstacionDTO {
    private String descripcion;
    private String calle;
    private String departamento;
    private int longitud;
    private int latitud;
    public EstacionCarga buildEstacion() {
        EstacionCarga estacion = new EstacionCarga();
        estacion.setDescripcion(this.descripcion);
        estacion.setCalle(this.calle);
        estacion.setDepartamento(this.departamento);
        estacion.setLongitud(this.longitud);
        estacion.setLatitud(this.latitud);
        return estacion;
    }
}