package org.tallerjava.moduloCargas.aplicacion;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.tallerjava.moduloCargas.dominio.*;
import org.tallerjava.moduloCargas.interfase.*;
import org.tallerjava.moduloCargas.interfase.evento.out.PublicadorCarga;
import org.tallerjava.moduloClientes.interfase.remota.MedioPagoDTO;
import org.tallerjava.moduloComun.eventosCarga.CargaIniciadaEvent;
import org.tallerjava.moduloCargas.dominio.repositorios.CargaRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped

public class CargaServiciosImp implements CargaServicios {

    @Inject
    private CargaRepo cargaRepo;

    @Inject
    private PublicadorCarga publicadorCarga;

    @Override
    @Transactional
    public long altaEstacion(EstacionDTO estacionDTO) {
        EstacionCarga estacion = estacionDTO.buildEstacion();
        return cargaRepo.altaEstacion(estacion);
    }

    @Override
    @Transactional
    public long altaCargador(CargadorDTO cargadorDTO) {
        Long estacionId = cargadorDTO.getEstacionId();
        if (estacionId == null) {
            throw new IllegalArgumentException("Debe especificar una estación para el cargador");
        }
        EstacionCarga estacion = cargaRepo.buscaEstacionPorId(estacionId);
        if (estacion == null) {
            throw new IllegalArgumentException(
                    "No existe estación con id: " + estacionId);
        }
        Cargador cargador = cargadorDTO.buildCargador(estacion);
        return cargaRepo.altaCargador(cargador);
    }

    @Override
    @Transactional
    public void iniciarCarga(IniciarCargaRequestDTO cargaDTO){

        Cliente cliente = cargaRepo.buscarClientePorId(cargaDTO.getClienteId());
        if (cliente == null){

            throw new IllegalArgumentException("Cliente no encontrado: " + cargaDTO.getClienteId());
        }

        MedioPago medioPago;
        if(cargaDTO.getTipoMedioDTO()  == IniciarCargaRequestDTO.TipoMedioDTO.CUENTA_UTE){
            medioPago = new CuentaUTE();
        } else if (cargaDTO.getTipoMedioDTO()  == IniciarCargaRequestDTO.TipoMedioDTO.TARJETA) {
            medioPago = new Tarjeta();
        }else{
            throw new IllegalArgumentException("No se pudo validar el medio de pago");
        }

        Carga carga = new Carga();
        carga.setCliente(cliente);
        carga.setMedioPagoId(medioPago.getId());
        carga.setFecha(LocalDate.now());
        carga.setHoraInicio(LocalDateTime.now());
        carga.setEstado(EstadoCarga.ACTIVA);

        Cargador cargador = cargaRepo.buscaCargadorPorId(cargaDTO.getCargadorId());
        carga.setCargador(cargador);
        cargador.getCargas().add(carga);

        long cargaId = cargaRepo.guardarCarga(carga);
        publicadorCarga.iniciarCarga(cargaId, cliente.getId());
    }

    @Override
    @Transactional
    public void finalizarCarga(FinalizarCargaRequestDTO cargaDTO){

        Carga carga = cargaRepo.buscarCargaActivaPorCliente(cargaDTO.getClienteId());
        float recargo = 10; //Calcular con la fecha inicio y la fecha fin y % de la carga PENDIENTE DE HACER
        float importe = 0;
        float importeTotal = recargo + importe;
        carga.setHoraFin(LocalDateTime.now());
        publicadorCarga.finalizarCarga(carga.getCargador().getId(),carga.getCliente().getId(),cargaDTO.getCarga(),carga.getMedioPagoId(),importeTotal, recargo);
        carga.setEstado(EstadoCarga.FINALIZADA);
        
    }

    @Override
    public CargaDTO verCargaActual(long clienteId) {
        Carga carga = cargaRepo.buscarCargaActivaPorCliente(clienteId);
        if (carga == null) {
            throw new IllegalArgumentException("No hay una carga activa para el cliente con id: " + clienteId);
        }
        CargaDTO dto = new CargaDTO();
        dto.setId(carga.getId());
        dto.setFecha(carga.getFecha());
        dto.setHoraInicio(carga.getHoraInicio());
        dto.setPorcentajeAvance(carga.getPorcentajeAvance());
        dto.setHoraEstimadaFin(carga.getHoraEstimadaFin());
        dto.setEstado(carga.getEstado().name());
        dto.setClienteId(clienteId);
        dto.setCargadorId(carga.getCargador() != null ? carga.getCargador().getId() : null);
        return dto;
    }
    @Override
    public void verHistorico(Cliente cliente, LocalDateTime ini, LocalDateTime fin){

    }

    @Override
    public List<EstacionCarga> obtenerEstaciones(){
        return cargaRepo.obtenerEstaciones();
    }
    @Override
    public void registrarCliente(Cliente cliente) {
        cargaRepo.registrarCliente(cliente);
    }
    @Override
    @Transactional
    public void altaMedioPago(long clienteId, MedioPago medioPago) {
        Cliente cliente = cargaRepo.buscarClientePorId(clienteId);
        if (cliente == null) {
            return;
        }
        cliente.addMedioPago(medioPago);
        cargaRepo.altaMedioPago(medioPago);
    }
}