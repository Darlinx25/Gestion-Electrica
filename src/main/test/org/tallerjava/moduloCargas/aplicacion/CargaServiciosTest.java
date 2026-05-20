
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
}