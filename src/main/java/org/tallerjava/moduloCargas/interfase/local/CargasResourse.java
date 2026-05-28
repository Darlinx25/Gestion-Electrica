package org.tallerjava.moduloCargas.interfase.local;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import org.tallerjava.moduloCargas.aplicacion.CargaServicios;
import org.tallerjava.moduloCargas.dominio.EstacionCarga;
import org.tallerjava.moduloCargas.interfase.Dtos.CargaDTO;
import org.tallerjava.moduloCargas.interfase.Dtos.CargadorDTO;
import org.tallerjava.moduloCargas.interfase.Dtos.EstacionDTO;
import org.tallerjava.moduloCargas.interfase.Dtos.EstadoCargaDTO;
import org.tallerjava.moduloCargas.interfase.Dtos.FinalizarCargaRequestDTO;
import org.tallerjava.moduloCargas.interfase.Dtos.IniciarCargaRequestDTO;

@ApplicationScoped
public class CargasResourse {

    @Inject
    private CargaServicios cargaServicios;
    
    public void iniciarCarga(IniciarCargaRequestDTO cargaDTO) {
        cargaServicios.iniciarCarga(cargaDTO);
    }
    
    public CargaDTO verCargaActual(long clienteId) {
        return cargaServicios.verCargaActual(clienteId);
    }
    
    public List<CargaDTO> verHistorico(Long clienteId, LocalDateTime ini, LocalDateTime fin) {
        return cargaServicios.verHistorico(clienteId, ini, fin);
    }
    
    public void finalizarCarga(FinalizarCargaRequestDTO cargaDTO) {
        cargaServicios.finalizarCarga(cargaDTO);
    }
    
    public void actualizarEstadoCarga(EstadoCargaDTO estadoCargaDTO) {
        cargaServicios.actualizarEstadoCarga(estadoCargaDTO);
    }
    
    public long altaEstacion(EstacionDTO estacionDTO) {
        return cargaServicios.altaEstacion(estacionDTO);
    }
    
    public long altaCargador(CargadorDTO cargadorDTO) {
        return cargaServicios.altaCargador(cargadorDTO);
    }
    
    public List<EstacionCarga> obtenerEstaciones() {
        return cargaServicios.obtenerEstaciones();
    }
}
