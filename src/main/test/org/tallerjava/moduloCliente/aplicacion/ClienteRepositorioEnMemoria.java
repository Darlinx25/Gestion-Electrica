package org.tallerjava.moduloCliente.aplicacion;

import org.tallerjava.moduloClientes.dominio.Cliente;
import org.tallerjava.moduloClientes.dominio.MedioPago;
import org.tallerjava.moduloClientes.dominio.Reclamo;
import org.tallerjava.moduloClientes.dominio.repositorios.ClienteRepo;
import java.util.*;
import java.util.stream.Collectors;
public class ClienteRepositorioEnMemoria implements ClienteRepo {
    public Map<Long, Cliente> clientes = new HashMap<>();
    public Map<Long, MedioPago> mediosPago = new HashMap<>();   // ← cambiado de private a public
    public Map<Long, Reclamo> reclamos = new HashMap<>();
    private long nextClienteId = 1;
    private long nextReclamoId = 1;

    @Override
    public long registarCliente(Cliente cliente) {
        cliente.setId(nextClienteId++);
        clientes.put(cliente.getId(), cliente);
        return cliente.getId();
    }
    @Override
    public void altaMedioPago(MedioPago medioPago) {
        mediosPago.put(medioPago.getId(), medioPago);
    }
    @Override
    public Cliente buscaClientePorId(long id) {
        return clientes.get(id);
    }
    @Override
    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes.values());
    }
    @Override
    public long guardarReclamo(Reclamo reclamo) {
        reclamo.setId(nextReclamoId++);
        reclamos.put(reclamo.getId(), reclamo);
        return reclamo.getId();
    }
}