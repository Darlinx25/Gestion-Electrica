package org.tallerjava.moduloPagos.aplicacion;
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
import org.tallerjava.moduloCargas.interfase.Dtos.CargaDTO;
import org.tallerjava.moduloPagos.dominio.*;
import org.tallerjava.moduloPagos.dominio.repositorios.PagoRepo;
import java.time.LocalDateTime;
import java.util.List;
import org.mockito.Mockito;
import org.tallerjava.moduloPagos.interfase.consumidor.ConsumidorServiciosExternos;
import org.tallerjava.moduloPagos.interfase.evento.out.publicadorPagos;
@EnableWeld
class PagoServiciosTest {
    private PagoRepositorioEnMemoria repoMemoria;
    private ConsumidorServiciosExternos mockConsumidor;
    private publicadorPagos mockPublicadorPagos;
    @WeldSetup
    public WeldInitiator weld = WeldInitiator.from(PagoServiciosImpl.class)
            .addBeans(crearMockRepositorio(), crearMockConsumidor(), crearMockPublicadorPagos()).build();
    private Bean<?> crearMockRepositorio() {
        repoMemoria = new PagoRepositorioEnMemoria();
        return MockBean.builder().types(PagoRepo.class).scope(ApplicationScoped.class).creating(repoMemoria).build();
    }
    private Bean<?> crearMockConsumidor() {
        mockConsumidor = Mockito.mock(ConsumidorServiciosExternos.class);
        return MockBean.builder().types(ConsumidorServiciosExternos.class).scope(ApplicationScoped.class).creating(mockConsumidor).build();
    }
    private Bean<?> crearMockPublicadorPagos() {
        mockPublicadorPagos = Mockito.mock(publicadorPagos.class);
        return MockBean.builder().types(publicadorPagos.class).scope(ApplicationScoped.class).creating(mockPublicadorPagos).build();
    }
    @BeforeEach
    void setUp() {
        repoMemoria.clientes.clear();
        repoMemoria.mediosPago.clear();
        repoMemoria.cargas.clear();
        Mockito.reset(mockConsumidor, mockPublicadorPagos);
    }
    @Test
    @DisplayName("pagarCarga con Tarjeta autorizada marca la carga como pagada")
    void pagarCargaConTarjeta(PagoServiciosImpl service) {
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setId(1);
        tarjeta.setTipo(TipoTarjeta.DEBITO);
        tarjeta.setNumero("1234567890");
        repoMemoria.agregarMedioPago(tarjeta);
        Carga carga = new Carga();
        carga.setId(1);
        carga.setPagado(false);
        repoMemoria.agregarCarga(carga);
        Mockito.when(mockConsumidor.pagarTarjeta(Mockito.anyString())).thenReturn(true);
        service.pagarCarga(1, 500, 1, 1);
        Carga resultado = repoMemoria.buscaCargaPorId(1);
        Assertions.assertTrue(resultado.isPagado());
        Mockito.verify(mockPublicadorPagos).pagarCarga(1L);
    }
    @Test
    @DisplayName("pagarCarga con Tarjeta no autorizada no modifica la carga")
    void pagarCargaConTarjetaNoAutorizada(PagoServiciosImpl service) {
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setId(1);
        tarjeta.setTipo(TipoTarjeta.DEBITO);
        tarjeta.setNumero("1234567890");
        repoMemoria.agregarMedioPago(tarjeta);
        Carga carga = new Carga();
        carga.setId(1);
        carga.setPagado(false);
        repoMemoria.agregarCarga(carga);
        Mockito.when(mockConsumidor.pagarTarjeta(Mockito.anyString())).thenReturn(false);
        service.pagarCarga(1, 500, 1, 1);
        Carga resultado = repoMemoria.buscaCargaPorId(1);
        Assertions.assertFalse(resultado.isPagado());
        Mockito.verify(mockPublicadorPagos, Mockito.never()).pagarCarga(Mockito.anyLong());
    }
    @Test
    @DisplayName("pagarCarga con CuentaUTE autorizada marca la carga como pagada")
    void pagarCargaConCuentaUTE(PagoServiciosImpl service) {
        CuentaUTE cuenta = new CuentaUTE();
        cuenta.setId(1);
        cuenta.setNumeroCuenta("12345");
        repoMemoria.agregarMedioPago(cuenta);
        Carga carga = new Carga();
        carga.setId(1);
        carga.setPagado(false);
        repoMemoria.agregarCarga(carga);
        Mockito.when(mockConsumidor.pagarUTE(Mockito.anyString())).thenReturn(true);
        service.pagarCarga(1, 300, 1, 1);
        Carga resultado = repoMemoria.buscaCargaPorId(1);
        Assertions.assertTrue(resultado.isPagado());
        Mockito.verify(mockPublicadorPagos).pagarCarga(1L);
    }
    @Test
    @DisplayName("pagarCarga con medioPago inexistente lanza excepcion")
    void pagarCargaMedioPagoInexistente(PagoServiciosImpl service) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.pagarCarga(1, 500, 99, 1));
    }
    @Test
    @DisplayName("altaMedioPago agrega medio de pago a cliente existente")
    void altaMedioPagoClienteExistente(PagoServiciosImpl service) {
        Cliente cliente = new ClienteComun();
        cliente.setId(1);
        repoMemoria.agregarCliente(cliente);
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setId(2);
        tarjeta.setTipo(TipoTarjeta.DEBITO);
        service.altaMedioPago(1, tarjeta);
        Cliente resultado = repoMemoria.buscaClientePorId(1);
        Assertions.assertFalse(resultado.getMediosPago().isEmpty());
        Assertions.assertEquals(1, resultado.getMediosPago().size());
    }
    @Test
    @DisplayName("altaMedioPago cliente inexistente no hace nada")
    void altaMedioPagoClienteInexistente(PagoServiciosImpl service) {
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setId(1);
        service.altaMedioPago(99, tarjeta);
        Assertions.assertNull(repoMemoria.buscarMedioPagoPorId(1));
    }
    @Test
    @DisplayName("consultarPagos devuelve lista filtrada por cliente y fechas")
    void consultarPagosConDatos(PagoServiciosImpl service) {
        Cliente cliente = new ClienteComun();
        cliente.setId(1);
        repoMemoria.agregarCliente(cliente);
        Carga carga = new Carga();
        carga.setId(1);
        carga.setCliente(cliente);
        carga.setHoraInicio(LocalDateTime.of(2026, 5, 20, 19, 30));
        carga.setImporteTotal(500);
        repoMemoria.agregarCarga(carga);
        LocalDateTime ini = LocalDateTime.of(2026, 5, 20, 19, 0);
        LocalDateTime fin = LocalDateTime.of(2026, 5, 20, 20, 0);
        List<CargaDTO> resultado = service.consultarPagos(1, ini, fin);
        Assertions.assertEquals(1, resultado.size());
        Assertions.assertEquals(500, resultado.get(0).getImporteTotal());
        Assertions.assertEquals(1, resultado.get(0).getClienteId());
    }
    @Test
    @DisplayName("consultarPagos sin datos lanza excepcion")
    void consultarPagosSinDatos(PagoServiciosImpl service) {
        LocalDateTime ini = LocalDateTime.of(2026, 5, 20, 19, 0);
        LocalDateTime fin = LocalDateTime.of(2026, 5, 20, 20, 0);
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.consultarPagos(1, ini, fin));
    }
    @Test
    @DisplayName("cargaSinPagar devuelve la carga impaga del cliente")
    void cargaSinPagarDevuelveCargaImpaga(PagoServiciosImpl service) {
        Cliente cliente = new ClienteComun();
        cliente.setId(1);
        repoMemoria.agregarCliente(cliente);
        Carga carga = new Carga();
        carga.setId(1);
        carga.setCliente(cliente);
        carga.setPagado(false);
        repoMemoria.agregarCarga(carga);
        Carga resultado = service.cargaSinPagar(1L);
        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(1, resultado.getId());
        Assertions.assertFalse(resultado.isPagado());
    }
    @Test
    @DisplayName("cargaSinPagar devuelve null cuando todas las cargas estan pagas")
    void cargaSinPagarDevuelveNullSiTodasPagas(PagoServiciosImpl service) {
        Cliente cliente = new ClienteComun();
        cliente.setId(1);
        repoMemoria.agregarCliente(cliente);
        Carga carga = new Carga();
        carga.setId(1);
        carga.setCliente(cliente);
        carga.setPagado(true);
        repoMemoria.agregarCarga(carga);
        Carga resultado = service.cargaSinPagar(1L);
        Assertions.assertNull(resultado);
    }
    @Test
    @DisplayName("cargaSinPagar devuelve null cuando el cliente no tiene cargas")
    void cargaSinPagarDevuelveNullSinCargas(PagoServiciosImpl service) {
        Carga resultado = service.cargaSinPagar(99L);
        Assertions.assertNull(resultado);
    }
}