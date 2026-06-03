package org.tallerjava.moduloPagos.aplicacion;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.tallerjava.moduloCargas.interfase.Dtos.CargaDTO;
import org.tallerjava.moduloPagos.dominio.*;
import org.tallerjava.moduloPagos.dominio.repositorios.PagoRepo;
import org.tallerjava.moduloPagos.interfase.evento.out.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.tallerjava.moduloPagos.interfase.consumidor.ConsumidorServiciosExternos;

@ApplicationScoped
public class PagoServiciosImpl implements PagoServicios {
    @Inject
    private PagoRepo pagoRepo;
    
    @Inject
    private ConsumidorServiciosExternos consumidor;

    @Inject
    private publicadorPagos publicadorPagos;

    @Override
    public void pagarCarga(long clienteId, float importe, long medioPagoId, long cargaId){
        MedioPago medioPago = pagoRepo.buscarMedioPagoPorId(medioPagoId);
        if (medioPago == null) {
            throw new IllegalArgumentException("Medio de pago no encontrado: " + medioPagoId);
        }
        if (medioPago instanceof Tarjeta tarjeta) {
            System.out.println("Procesando pago con tarjeta " + tarjeta.getTipo()
                    + " por $" + importe);
            boolean pagoAutorizado = consumidor.pagarTarjeta(tarjeta.getNumero());
            if (pagoAutorizado) {
                System.out.println("PAGO AUTORIZADO");
                publicadorPagos.pagarCarga(cargaId);
                Carga carga = pagoRepo.buscaCargaPorId(cargaId);
                carga.setPagado(true);
                pagoRepo.guardarCarga(carga);
            } else {
                System.out.println("PAGO NO AUTORIZADO");
            }
        } else if (medioPago instanceof CuentaUTE cuenta) {
            System.out.println("Procesando pago con Cuenta UTE "
                    + cuenta.getNumeroCuenta() + " por $" + importe);
            boolean pagoNotificado = consumidor.pagarUTE(cuenta.getNumeroCuenta());
            if (pagoNotificado) {
                System.out.println("PAGO NOTIFICADO CON ÉXITO");
                publicadorPagos.pagarCarga(cargaId);
                Carga carga = pagoRepo.buscaCargaPorId(cargaId);
                carga.setPagado(true);
                pagoRepo.guardarCarga(carga);
            } else {
                System.out.println("ERROR AL NOTIFICAR EL PAGO");
            }
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
        if(cargas.isEmpty()){
            throw new IllegalArgumentException("No hay cargas para el cliente con id: " + clienteId + ", entre esas fechas.");
        }
        List<CargaDTO> cargasDTO = new ArrayList<>();
        for (Carga carga : cargas){
            CargaDTO dto = new CargaDTO();
            dto.setId(carga.getId());
            dto.setFecha(carga.getFecha());
            dto.setHoraInicio(carga.getHoraInicio());
            dto.setPorcentajeAvance(carga.getPorcentajeAvance());
            dto.setClienteId(clienteId);
            dto.setImporteTotal(carga.getImporteTotal());
            cargasDTO.add(dto);
        }
        return cargasDTO;
    }
}
