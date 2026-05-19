package org.tallerjava.moduloCargas.dominio.repositorios;

import org.tallerjava.moduloCargas.dominio.*;

import java.util.List;

public interface CargaRepo {
    public long altaEstacion(EstacionCarga estacion);
    public long altaCargador(Cargador cargador);
    public long guardarCarga(Carga carga);
    public EstacionCarga buscaEstacionPorId(long id);
    public List<EstacionCarga> obtenerEstaciones();
    public Carga buscarCargaActivaPorCliente(long clienteId);
    public Cliente buscarClientePorId(long id);
    public MedioPago buscarMedioPagoPorId(long id);
    public void registrarCliente(Cliente cliente);
    public void altaMedioPago(MedioPago medioPago);
}
