package org.tallerjava.moduloCargas.aplicacion;

import org.tallerjava.moduloCargas.dominio.EstacionCarga;
import org.tallerjava.moduloCargas.interfase.CargadorDTO;

public interface CargaServicios {
    public long altaEstacion(EstacionCarga estacion);
    public long altaCargador(CargadorDTO cargadorDTO);
}