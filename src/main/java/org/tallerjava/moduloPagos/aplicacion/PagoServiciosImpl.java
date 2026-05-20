package org.tallerjava.moduloPagos.aplicacion;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.tallerjava.moduloCargas.interfase.CargaDTO;
import org.tallerjava.moduloPagos.dominio.*;
import org.tallerjava.moduloPagos.dominio.repositorios.PagoRepo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PagoServiciosImpl implements PagoServicios {
    @Inject
    private PagoRepo pagoRepo;
    
    
    @Override
    public void pagarCarga(long clienteId, float importe, long medioPagoId){
        MedioPago medioPago = pagoRepo.buscarMedioPagoPorId(medioPagoId);
        if (medioPago == null) {
            throw new IllegalArgumentException("Medio de pago no encontrado: " + medioPagoId);
        }
        if (medioPago instanceof Tarjeta tarjeta) {
            System.out.println("Procesando pago con tarjeta " + tarjeta.getTipo()
                    + " por $" + importe);
        } else if (medioPago instanceof CuentaUTE cuenta) {
            System.out.println("Procesando pago con Cuenta UTE "
                    + cuenta.getNumeroCuenta() + " por $" + importe);
        }
    }
    
    @Override
    public void registrarCliente(Cliente cliente) {
        pagoRepo.registrarCliente(cliente);
    }
    
    @Override
    @Transactional
    public void altaMedioPago(long clienteId, MedioPago medioPago) {
        Cliente cliente = pagoRepo.buscaClientePorId(clienteId);
        if (cliente == null) {
            return;
        }
        cliente.addMedioPago(medioPago);
        pagoRepo.altaMedioPago(medioPago);
    }

    @Override
    public List<CargaDTO> consultarPagos(long clienteId, LocalDateTime ini, LocalDateTime fin) {
        List<Carga> cargas = pagoRepo.consultarPagos(clienteId, ini, fin);
        if(cargas == null || cargas.isEmpty()){
            throw new IllegalArgumentException("No hay cargas para el cliente con id: " + clienteId + ", entre esas fechas.");
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
            dto.setImporteTotal(carga.getImporteTotal());
            if (dto.getImporteTotal() != 0.0f){
                cargasDTO.add(dto);
            }

        }
        return cargasDTO;
    }
}
