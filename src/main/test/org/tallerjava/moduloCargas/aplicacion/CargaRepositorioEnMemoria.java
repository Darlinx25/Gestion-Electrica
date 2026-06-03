package org.tallerjava.moduloCargas.aplicacion;
import org.tallerjava.moduloCargas.dominio.*;
import org.tallerjava.moduloCargas.dominio.repositorios.CargaRepo;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
public class CargaRepositorioEnMemoria implements CargaRepo {
    public Map<Long, EstacionCarga> estaciones = new HashMap<>();
    public Map<Long, Cargador> cargadores = new HashMap<>();
    public Map<Long, Carga> cargas = new HashMap<>();
    public Map<Long, Cliente> clientes = new HashMap<>();
    public Map<Long, MedioPago> mediosPago = new HashMap<>();
    private long nextEstacionId = 1;
    private long nextCargadorId = 1;
    private long nextCargaId = 1;
    @Override
    public long altaEstacion(EstacionCarga estacion) {
        estacion.setId(nextEstacionId++);
        estaciones.put(estacion.getId(), estacion);
        return estacion.getId();
    }
    @Override
    public long altaCargador(Cargador cargador) {
        cargador.setId(nextCargadorId++);
        cargadores.put(cargador.getId(), cargador);
        return cargador.getId();
    }
    @Override
    public long guardarCarga(Carga carga) {
        if (carga.getId() == 0) {
            carga.setId(nextCargaId++);
        }
        cargas.put(carga.getId(), carga);
        return carga.getId();
    }
    // resto de métodos sin cambios...
    @Override
    public EstacionCarga buscaEstacionPorId(long id) {
        return estaciones.get(id);
    }
    @Override
    public List<EstacionCarga> obtenerEstaciones() {
        return new ArrayList<>(estaciones.values());
    }
    @Override
    public Carga buscarCargaActivaPorCliente(long clienteId) {
        return cargas.values().stream().filter(c -> c.getCliente().getId() == clienteId)
                .filter(c -> c.getEstado() == EstadoCarga.ACTIVA).findFirst().orElse(null);
    }
    @Override
    public Carga buscarCargaPorId(long cargaId) {
        return cargas.get(cargaId);
    }
    @Override
    public Cliente buscarClientePorId(long id) {
        return clientes.get(id);
    }
    @Override
    public MedioPago buscarMedioPagoPorId(long id) {
        return mediosPago.get(id);
    }
    @Override
    public void registrarCliente(Cliente cliente) {
        clientes.put(cliente.getId(), cliente);
    }
    @Override
    public void altaMedioPago(MedioPago medioPago) {
        mediosPago.put(medioPago.getId(), medioPago);
    }
    @Override
    public Cargador buscaCargadorPorId(long id) {
        return cargadores.get(id);
    }
    @Override
    public List<Carga> verHistorico(Long clienteId, LocalDateTime ini, LocalDateTime fin) {
        return cargas.values().stream()
                .filter(c -> c.getCliente().getId() == clienteId).filter(c -> c.getHoraInicio() != null)
                .filter(c -> !c.getHoraInicio().isBefore(ini)).filter(c -> !c.getHoraInicio().isAfter(fin))
                .collect(Collectors.toList());
    }
    @Override
    public boolean cargaSinPagar(Long clienteId){
        return true;
    }
}