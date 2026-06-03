package org.tallerjava.moduloPagos.dominio.repositorios;

import org.tallerjava.moduloPagos.dominio.Carga;
import org.tallerjava.moduloPagos.dominio.Cliente;
import org.tallerjava.moduloPagos.dominio.MedioPago;

import java.time.LocalDateTime;
import java.util.List;

public interface PagoRepo {
    public void registrarCliente(Cliente cliente);
    public Cliente buscaClientePorId(long id);
    public void altaMedioPago(MedioPago medioPago);
    public MedioPago buscarMedioPagoPorId(long id);
    public Carga buscaCargaPorId(long id);
    public List<Carga> consultarPagos(long clienteId, LocalDateTime ini, LocalDateTime fin);
    public long guardarCarga(Carga carga);
    public Carga cargaSinPagar(Long clienteId);
}
