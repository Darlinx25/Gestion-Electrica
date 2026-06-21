package org.tallerjava.moduloClientes.aplicacion;

import org.tallerjava.moduloClientes.dominio.Cliente;
import org.tallerjava.moduloClientes.dominio.MedioPago;
import org.tallerjava.moduloClientes.interfase.Dtos.ClienteDTO;
import org.tallerjava.moduloClientes.interfase.Dtos.ReclamoDTO;

import java.util.List;

public interface CuentaServicios {
    public long registarCliente(Cliente cliente);
    public List<ClienteDTO> obtenerClientes();
    public boolean altaMedioPago(long clienteId, MedioPago medioPago); //Ver si lleva alguna exp como "throws SaldoInsuficienteException"
    public long realizarReclamo(long clienteId, String informacion);
    public List<ReclamoDTO> listarReclamosPorEtiqueta(String etiqueta);

}