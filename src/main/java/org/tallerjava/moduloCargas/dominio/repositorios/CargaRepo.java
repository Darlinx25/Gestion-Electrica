package org.tallerjava.moduloCargas.dominio.repositorios;

import org.tallerjava.moduloCargas.dominio.Carga;
import org.tallerjava.moduloCargas.dominio.Cargador;
import org.tallerjava.moduloCargas.dominio.EstacionCarga;

import java.util.List;

public interface CargaRepo {
    public long altaEstacion(EstacionCarga estacion);
    public long altaCargador(Cargador cargador);
    public long guardarCarga(Carga carga);
    public EstacionCarga buscaEstacionPorId(long id);
    public List<EstacionCarga> obtenerEstaciones();
    public Carga buscarCargaActivaPorCliente(long clienteId);
}
