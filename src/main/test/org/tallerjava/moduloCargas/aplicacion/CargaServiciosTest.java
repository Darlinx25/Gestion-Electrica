package org.tallerjava.moduloCargas.aplicacion;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.spi.Bean;
import org.jboss.weld.junit.MockBean;
import org.jboss.weld.junit5.EnableWeld;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.tallerjava.moduloCargas.dominio.*;
import org.tallerjava.moduloCargas.dominio.repositorios.CargaRepo;
import org.tallerjava.moduloCargas.interfase.*;
import org.tallerjava.moduloCargas.interfase.evento.out.PublicadorCarga;
import java.time.LocalDateTime;
import java.util.List;
@EnableWeld
class CargaServiciosTest {
    private CargaRepositorioEnMemoria repoMemoria;
    private PublicadorCarga mockPublicadorCarga;
    @WeldSetup
    public WeldInitiator weld = WeldInitiator.from(CargaServiciosImp.class)
            .addBeans(crearMockRepositorio(), crearMockPublicadorCarga()).build();

    private Bean<?> crearMockRepositorio() {
        repoMemoria = new CargaRepositorioEnMemoria();
        return MockBean.builder().types(CargaRepo.class).scope(ApplicationScoped.class).creating(repoMemoria).build();
    }

    private Bean<?> crearMockPublicadorCarga() {
        mockPublicadorCarga = Mockito.mock(PublicadorCarga.class);
        return MockBean.builder()
                .types(PublicadorCarga.class)
                .scope(ApplicationScoped.class)
                .creating(mockPublicadorCarga)
                .build();
    }

    @BeforeEach
    void setUp() {
        repoMemoria.estaciones.clear();
        repoMemoria.cargadores.clear();
        repoMemoria.cargas.clear();
        repoMemoria.clientes.clear();
        repoMemoria.mediosPago.clear();
    }

    @Test
    @DisplayName("altaEstacion crea estacion correctamente")
    void altaEstacion(CargaServiciosImp service) {
        EstacionDTO dto = new EstacionDTO();
        dto.setDescripcion("Estacion Centro");
        dto.setCalle("18 de Julio");
        dto.setDepartamento("Montevideo");
        dto.setLongitud(-56);
        dto.setLatitud(-34);
        long id = service.altaEstacion(dto);
        Assertions.assertTrue(id > 0);
        Assertions.assertNotNull(repoMemoria.buscaEstacionPorId(id));
    }

    @Test
    @DisplayName("altaCargador crea y asocia cargador a una estacion")
    void altaCargadorEstacionExistente(CargaServiciosImp service) {
        EstacionCarga estacion = new EstacionCarga();
        estacion.setId(1);
        estacion.setDescripcion("Estacion Centro");
        repoMemoria.altaEstacion(estacion);
        CargadorDTO dto = new CargadorDTO();
        dto.setTipo(TipoCargador.RAPIDA);
        dto.setTieneCable(true);
        dto.setTipoConector(TipoConector.TIPO2);
        dto.setEstado(EstadoCargador.DISPONIBLE);
        dto.setPotenciaMinima(150);
        dto.setEstacionId(1L);
        long id = service.altaCargador(dto);
        Cargador cargador = repoMemoria.cargadores.get(id);
        Assertions.assertNotNull(cargador);
        Assertions.assertEquals(1, cargador.getEstacionCarga().getId());
    }

    @Test
    @DisplayName("altaCargador sin estacionId lanza excepcion")
    void altaCargadorSinEstacionId(CargaServiciosImp service) {
        CargadorDTO dto = new CargadorDTO();
        dto.setTipo(TipoCargador.RAPIDA);
        dto.setEstacionId(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.altaCargador(dto));
    }

    @Test
    @DisplayName("altaCargador con estacion inexistente lanza excepcion")
    void altaCargadorEstacionInexistente(CargaServiciosImp service) {
        CargadorDTO dto = new CargadorDTO();
        dto.setTipo(TipoCargador.RAPIDA);
        dto.setEstacionId(99L);
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.altaCargador(dto));
    }

    @Test
    @DisplayName("verCargaActual con carga activa devuelve DTO")
    void verCargaActualConCargaActiva(CargaServiciosImp service) {
        Cliente cliente = new org.tallerjava.moduloCargas.dominio.ClienteComun();
        cliente.setId(1);
        repoMemoria.clientes.put(1L, cliente);
        Carga carga = new Carga();
        carga.setId(1);
        carga.setCliente(cliente);
        carga.setEstado(EstadoCarga.ACTIVA);
        repoMemoria.guardarCarga(carga);
        CargaDTO dto = service.verCargaActual(1);
        Assertions.assertNotNull(dto);
        Assertions.assertEquals("ACTIVA", dto.getEstado());
    }

    @Test
    @DisplayName("verCargaActual sin carga activa lanza excepcion")
    void verCargaActualSinCargaActiva(CargaServiciosImp service) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.verCargaActual(99));
    }

    @Test
    @DisplayName("iniciarCarga crea carga activa exitosamente")
    void iniciarCarga(CargaServiciosImp service) {
        Cliente cliente = new org.tallerjava.moduloCargas.dominio.ClienteComun();
        cliente.setId(1);
        repoMemoria.clientes.put(1L, cliente);
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setId(1);
        repoMemoria.mediosPago.put(1L, tarjeta);
        Cargador cargador = new Cargador();
        cargador.setId(1);
        cargador.setEstacionCarga(new EstacionCarga());
        repoMemoria.cargadores.put(1L, cargador);
        IniciarCargaRequestDTO dto = new IniciarCargaRequestDTO();
        dto.setClienteId(1);
        dto.setMedioPagoId(1);
        dto.setCargadorId(1);
        service.iniciarCarga(dto);
        Carga carga = repoMemoria.buscarCargaActivaPorCliente(1);
        Assertions.assertNotNull(carga);
        Assertions.assertEquals(EstadoCarga.ACTIVA, carga.getEstado());
        Assertions.assertEquals(1, carga.getCliente().getId());
    }

    @Test
    @DisplayName("iniciarCarga con cliente inexistente lanza excepcion")
    void iniciarCargaClienteInexistente(CargaServiciosImp service) {
        IniciarCargaRequestDTO dto = new IniciarCargaRequestDTO();
        dto.setClienteId(99);
        dto.setMedioPagoId(1);
        dto.setCargadorId(1);
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.iniciarCarga(dto));
    }

    @Test
    @DisplayName("iniciarCarga con medioPago inexistente lanza excepcion")
    void iniciarCargaMedioPagoInexistente(CargaServiciosImp service) {
        Cliente cliente = new org.tallerjava.moduloCargas.dominio.ClienteComun();
        cliente.setId(1);
        repoMemoria.clientes.put(1L, cliente);
        IniciarCargaRequestDTO dto = new IniciarCargaRequestDTO();
        dto.setClienteId(1);
        dto.setMedioPagoId(99);
        dto.setCargadorId(1);
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.iniciarCarga(dto));
    }

    @Test
    @DisplayName("verHistorico devuelve lista filtrada por cliente y fechas")
    void verHistoricoConDatos(CargaServiciosImp service) {
        Cliente cliente = new org.tallerjava.moduloCargas.dominio.ClienteComun();
        cliente.setId(1);
        repoMemoria.clientes.put(1L, cliente);
        Carga carga = new Carga();
        carga.setId(1);
        carga.setCliente(cliente);
        carga.setHoraInicio(LocalDateTime.now().minusHours(1));
        carga.setEstado(EstadoCarga.FINALIZADA);
        repoMemoria.guardarCarga(carga);
        LocalDateTime ini = LocalDateTime.now().minusHours(2);
        LocalDateTime fin = LocalDateTime.now();
        List<CargaDTO> resultado = service.verHistorico(1L, ini, fin);
        Assertions.assertFalse(resultado.isEmpty());
        Assertions.assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("verHistorico sin datos lanza excepcion")
    void verHistoricoSinDatos(CargaServiciosImp service) {
        LocalDateTime ini = LocalDateTime.now().minusHours(2);
        LocalDateTime fin = LocalDateTime.now();
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.verHistorico(1L, ini, fin));
    }

    @Test
    @DisplayName("finalizarCarga finaliza carga activa exitosamente")
    void finalizarCarga(CargaServiciosImp service) {
        Cliente cliente = new org.tallerjava.moduloCargas.dominio.ClienteComun();
        cliente.setId(1);
        repoMemoria.clientes.put(1L, cliente);
        Carga carga = new Carga();
        carga.setId(1);
        carga.setCliente(cliente);
        carga.setEstado(EstadoCarga.ACTIVA);
        carga.setPorcentajeAvance(50);
        repoMemoria.guardarCarga(carga);
        FinalizarCargaRequestDTO dto = new FinalizarCargaRequestDTO();
        dto.setClienteId(1);
        dto.setCarga(10);
        service.finalizarCarga(dto);
        Assertions.assertEquals(EstadoCarga.FINALIZADA, carga.getEstado());
        Assertions.assertNotNull(carga.getHoraFin());
    }

    @Test
    @DisplayName("obtenerEstaciones devuelve lista con estaciones registradas")
    void obtenerEstacionesConDatos(CargaServiciosImp service) {
        EstacionCarga estacion = new EstacionCarga();
        estacion.setId(1);
        estacion.setDescripcion("Estacion Centro");
        repoMemoria.altaEstacion(estacion);
        List<EstacionCarga> resultado = service.obtenerEstaciones();
        Assertions.assertFalse(resultado.isEmpty());
        Assertions.assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("obtenerEstaciones sin estaciones devuelve lista vacia")
    void obtenerEstacionesSinDatos(CargaServiciosImp service) {
        List<EstacionCarga> resultado = service.obtenerEstaciones();
        Assertions.assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("actualizarEstadoCarga actualiza porcentaje de avance")
    void actualizarEstadoCarga(CargaServiciosImp service) {
        Cliente cliente = new org.tallerjava.moduloCargas.dominio.ClienteComun();
        cliente.setId(1);
        repoMemoria.clientes.put(1L, cliente);
        Carga carga = new Carga();
        carga.setId(1);
        carga.setCliente(cliente);
        carga.setEstado(EstadoCarga.ACTIVA);
        repoMemoria.guardarCarga(carga);
        EstadoCargaDTO dto = new EstadoCargaDTO();
        dto.setCargaId(1);
        dto.setPorcentajeAvance(50);
        service.actualizarEstadoCarga(dto);
        Assertions.assertEquals(50, carga.getPorcentajeAvance());
    }

    @Test
    @DisplayName("actualizarEstadoCarga al 100% registra horaFin")
    void actualizarEstadoCargaCompleta(CargaServiciosImp service) {
        Cliente cliente = new org.tallerjava.moduloCargas.dominio.ClienteComun();
        cliente.setId(1);
        repoMemoria.clientes.put(1L, cliente);
        Carga carga = new Carga();
        carga.setId(1);
        carga.setCliente(cliente);
        carga.setEstado(EstadoCarga.ACTIVA);
        repoMemoria.guardarCarga(carga);
        EstadoCargaDTO dto = new EstadoCargaDTO();
        dto.setCargaId(1);
        dto.setPorcentajeAvance(100);
        service.actualizarEstadoCarga(dto);
        Assertions.assertEquals(100, carga.getPorcentajeAvance());
        Assertions.assertNotNull(carga.getHoraFin());
    }
}