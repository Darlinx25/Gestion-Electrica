package org.tallerjava.moduloPagos.dominio.repositorios;

import org.tallerjava.moduloPagos.dominio.Cliente;
import org.tallerjava.moduloPagos.dominio.MedioPago;

public interface PagoRepo {
    public void registrarCliente(Cliente cliente);
    public Cliente buscaClientePorId(long id);
    public void altaMedioPago(MedioPago medioPago);
    public MedioPago buscarMedioPagoPorId(long id);
}
