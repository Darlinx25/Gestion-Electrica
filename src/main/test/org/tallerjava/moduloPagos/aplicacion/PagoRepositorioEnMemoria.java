package org.tallerjava.moduloPagos.aplicacion;
import org.tallerjava.moduloPagos.dominio.Carga;
import org.tallerjava.moduloPagos.dominio.Cliente;
import org.tallerjava.moduloPagos.dominio.MedioPago;
import org.tallerjava.moduloPagos.dominio.repositorios.PagoRepo;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
public class PagoRepositorioEnMemoria implements PagoRepo {
    public Map<Long, Cliente> clientes = new HashMap<>();
    public Map<Long, MedioPago> mediosPago = new HashMap<>();
    public Map<Long, Carga> cargas = new HashMap<>();
    public void agregarCarga(Carga carga) {
        cargas.put(carga.getId(), carga);
    }
    public void agregarCliente(Cliente cliente) {
        clientes.put(cliente.getId(), cliente);
    }
    public void agregarMedioPago(MedioPago mp) {
        mediosPago.put(mp.getId(), mp);
    }
    @Override
    public void registrarCliente(Cliente cliente) {
        clientes.put(cliente.getId(), cliente);
    }
    @Override
    public Cliente buscaClientePorId(long id) {
        return clientes.get(id);
    }
    @Override
    public void altaMedioPago(MedioPago medioPago) {
        mediosPago.put(medioPago.getId(), medioPago);
    }
    @Override
    public MedioPago buscarMedioPagoPorId(long id) {
        return mediosPago.get(id);
    }
    @Override
    public List<Carga> consultarPagos(long clienteId, LocalDateTime ini, LocalDateTime fin) {
        return cargas.values().stream().filter(c -> c.getCliente().getId() == clienteId)
                .filter(c -> c.getHoraInicio() != null && !c.getHoraInicio().isBefore(ini) && !c.getHoraInicio()
                        .isAfter(fin)).collect(Collectors.toList());
    }
}