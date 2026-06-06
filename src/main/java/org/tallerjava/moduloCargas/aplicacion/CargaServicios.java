package org.tallerjava.moduloCargas.aplicacion;

import org.tallerjava.moduloCargas.dominio.*;
import org.tallerjava.moduloCargas.interfase.Dtos.*;


import java.time.LocalDateTime;
import java.util.List;

public interface CargaServicios {
    public long altaEstacion(EstacionDTO estacionDTO);
    public long altaCargador(CargadorDTO cargadorDTO);
    public void iniciarCarga(IniciarCargaRequestDTO cargaDTO);
    public CargaDTO verCargaActual(long clienteId);
    public List<CargaDTO> verHistorico(Long clienteId, LocalDateTime ini, LocalDateTime fin);
    //public void finalizarCarga(Cargador cargador, float carga, float recargo);
    public void finalizarCarga(FinalizarCargaRequestDTO cargaDTO);
    public List<EstacionCarga> obtenerEstaciones();
    public void registrarCliente(Cliente cliente);
    public void altaMedioPago(long clienteId, MedioPago medioPago);
    public void actualizarEstadoCarga(EstadoCargaDTO estadoCargaDTO);
    public Cliente buscarClientePorId(long id);
}