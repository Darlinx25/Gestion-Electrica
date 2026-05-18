package org.tallerjava.moduloCargas.aplicacion;

import org.tallerjava.moduloCargas.dominio.*;
import org.tallerjava.moduloCargas.interfase.CargadorDTO;
import org.tallerjava.moduloCargas.interfase.EstacionDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CargaServicios {
    public long altaEstacion(EstacionDTO estacionDTO);
    public long altaCargador(CargadorDTO cargadorDTO);
    public void iniciarCarga(Cliente cliente, MedioPago medioPago);
    public void verCargaActual(Cliente cliente);
    public void verHistorico(Cliente cliente, LocalDateTime ini, LocalDateTime fin);
    public void finalizarCarga(Cargador cargador, float carga, float recargo);
    public List<EstacionCarga> obtenerEstaciones();
}