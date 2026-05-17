package org.tallerjava.moduloClientes.aplicacion;

import org.tallerjava.moduloClientes.dominio.Cliente;
import org.tallerjava.moduloClientes.dominio.MedioPago;

import java.util.List;

public interface CuentaServicios {
    public long registarCliente(Cliente cliente);
    public List<Cliente> obtenerClientes();
    public boolean altaMedioPago(long clienteId, MedioPago medioPago); //Ver si lleva alguna exp como "throws SaldoInsuficienteException"
    public void realizarReclamo();

}