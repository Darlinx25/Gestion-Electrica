package org.tallerjava.moduloCargas.aplicacion;

import org.tallerjava.moduloCargas.dominio.*;
import org.tallerjava.moduloCargas.interfase.CargaDTO;
import org.tallerjava.moduloCargas.interfase.CargadorDTO;
import org.tallerjava.moduloCargas.interfase.EstacionDTO;
import org.tallerjava.moduloCargas.interfase.IniciarCargaRequestDTO;


import java.time.LocalDateTime;
import java.util.List;

public interface CargaServicios {
    public long altaEstacion(EstacionDTO estacionDTO);
    public long altaCargador(CargadorDTO cargadorDTO);
    public void iniciarCarga(IniciarCargaRequestDTO cargaDTO);
    public CargaDTO verCargaActual(long clienteId);
    public void verHistorico(Cliente cliente, LocalDateTime ini, LocalDateTime fin);
    public void finalizarCarga(Cargador cargador, float carga, float recargo);
    public List<EstacionCarga> obtenerEstaciones();
    public void registrarCliente(Cliente cliente);
    public void altaMedioPago(long clienteId, MedioPago medioPago);
}