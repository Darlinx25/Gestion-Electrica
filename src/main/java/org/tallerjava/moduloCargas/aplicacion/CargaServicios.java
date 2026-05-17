package org.tallerjava.moduloCargas.aplicacion;

import org.tallerjava.moduloCargas.dominio.*;
import org.tallerjava.moduloCargas.interfase.CargadorDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface CargaServicios {
    public long altaEstacion(EstacionCarga estacion);
    public long altaCargador(CargadorDTO cargadorDTO);
    public void iniciarCarga(Cliente cliente, MedioPago medioPago);
    public void verCargaActual(Cliente cliente);
    public void verHistorico(Cliente cliente, LocalDateTime ini, LocalDateTime fin);
    public void finalizarCarga(Cargador cargador, Carga carga,float recargo);
    public void obtenerEstaciones();
}