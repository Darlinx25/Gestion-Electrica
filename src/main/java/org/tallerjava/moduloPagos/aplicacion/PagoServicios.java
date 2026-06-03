package org.tallerjava.moduloPagos.aplicacion;

import org.tallerjava.moduloCargas.interfase.Dtos.CargaDTO;
import org.tallerjava.moduloPagos.dominio.Carga;
import org.tallerjava.moduloPagos.dominio.Cliente;
import org.tallerjava.moduloPagos.dominio.MedioPago;

import java.time.LocalDateTime;
import java.util.List;


public interface PagoServicios {

    public void pagarCarga(long clienteId, float importe, long medioPagoId, long cargaId);
    public void registrarCliente(Cliente cliente);
    public void altaMedioPago(long clienteId, MedioPago medioPago);
    public List<CargaDTO> consultarPagos(long clienteId, LocalDateTime ini, LocalDateTime fin);
    public Carga cargaSinPagar(Long clienteId);
}
