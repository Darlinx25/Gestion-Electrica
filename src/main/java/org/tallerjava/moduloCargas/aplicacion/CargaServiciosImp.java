package org.tallerjava.moduloCargas.aplicacion;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.tallerjava.moduloCargas.dominio.*;
import org.tallerjava.moduloCargas.interfase.*;
import org.tallerjava.moduloCargas.interfase.evento.out.PublicadorCarga;
import org.tallerjava.moduloCargas.dominio.repositorios.CargaRepo;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

        MedioPago medioPago = cargaRepo.buscarMedioPagoPorId(cargaDTO.getMedioPagoId());
        if (medioPago == null) {
            throw new IllegalArgumentException("Medio de pago no encontrado: " + cargaDTO.getMedioPagoId());
        }

        Carga carga = new Carga();
        carga.setHoraEstimadaFin(LocalDateTime.now().plusSeconds(30));
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
    public void actualizarEstadoCarga(EstadoCargaDTO estadoCargaDTO){
        Carga carga = cargaRepo.buscarCargaActivaPorCliente(estadoCargaDTO.getCargaId());
        carga.setPorcentajeAvance(estadoCargaDTO.getPorcentajeAvance());
        if(estadoCargaDTO.getPorcentajeAvance() == 100){
            carga.setHoraFin(LocalDateTime.now());
        }
        System.out.println("Porcentaje de avance: ");
        System.out.println(estadoCargaDTO.getPorcentajeAvance());
        cargaRepo.guardarCarga(carga);
    }


    @Override
    @Transactional
    public void finalizarCarga(FinalizarCargaRequestDTO cargaDTO){

        Carga carga = cargaRepo.buscarCargaActivaPorCliente(cargaDTO.getClienteId());
        LocalDateTime horaDesconexion;

        if(carga.getPorcentajeAvance() < 100){
            carga.setHoraFin(LocalDateTime.now());
            horaDesconexion = carga.getHoraFin();
        }else{
            horaDesconexion = LocalDateTime.now();
        }

        Duration tiempoExtra = Duration.between(carga.getHoraFin(),horaDesconexion);
        System.out.println("TIEMPO EXTRA");
        System.out.println(tiempoExtra);

        float recargo = 0;
        float importe = 10 * cargaDTO.getCarga();
        float segundos = tiempoExtra.toMillis() / 1000.0f;
        recargo = 3 * segundos;
        float importeTotal = recargo + importe;
        System.out.println("Importe");
        System.out.println(importe);
        System.out.println("Recargo");
        System.out.println(recargo);

        carga.setEstado(EstadoCarga.FINALIZADA);
        System.out.println("Porcentaje de avance: ");
        System.out.println(carga.getPorcentajeAvance());

        carga.setImporteTotal(importeTotal);
        carga.setRecargoPorDemora(recargo);
        cargaRepo.guardarCarga(carga);
        publicadorCarga.finalizarCarga(carga.getId(),carga.getCliente().getId(),cargaDTO.getCarga(),carga.getMedioPagoId(),importeTotal, recargo);


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
        dto.setImporteTotal(carga.getImporteTotal());
        return dto;
    }
    @Override
    public List<CargaDTO> verHistorico(Long clienteId, LocalDateTime ini, LocalDateTime fin){
        List<Carga> cargas = cargaRepo.verHistorico(clienteId, ini, fin);
        if(cargas == null || cargas.isEmpty()){
            throw new IllegalArgumentException("No hay una cargas para el cliente con id: " + clienteId + ", entre esas fechas.");
        }
        List<CargaDTO> cargasDTO = new ArrayList<>();
        for (Carga carga : cargas){
            CargaDTO dto = new CargaDTO();
            dto.setId(carga.getId());
            dto.setFecha(carga.getFecha());
            dto.setHoraInicio(carga.getHoraInicio());
            dto.setPorcentajeAvance(carga.getPorcentajeAvance());
            dto.setHoraEstimadaFin(carga.getHoraEstimadaFin());
            dto.setEstado(carga.getEstado().name());
            dto.setClienteId(clienteId);
            dto.setCargadorId(carga.getCargador() != null ? carga.getCargador().getId() : null);
            dto.setImporteTotal(carga.getImporteTotal());
            cargasDTO.add(dto);
        }
        return cargasDTO;
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