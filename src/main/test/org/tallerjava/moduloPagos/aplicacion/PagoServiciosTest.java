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
import org.tallerjava.moduloCargas.interfase.CargaDTO;
import org.tallerjava.moduloPagos.dominio.*;
import org.tallerjava.moduloPagos.dominio.repositorios.PagoRepo;
import java.time.LocalDateTime;
import java.util.List;
@EnableWeld
class PagoServiciosTest {
    private PagoRepositorioEnMemoria repoMemoria;
    @WeldSetup
    public WeldInitiator weld = WeldInitiator.from(PagoServiciosImpl.class).addBeans(crearMockRepositorio()).build();
    private Bean<?> crearMockRepositorio() {
        repoMemoria = new PagoRepositorioEnMemoria();
        return MockBean.builder().types(PagoRepo.class).scope(ApplicationScoped.class).creating(repoMemoria).build();
    }
    @BeforeEach
    void setUp() {
        repoMemoria.clientes.clear();
        repoMemoria.mediosPago.clear();
        repoMemoria.cargas.clear();
    }

    @Test
    @DisplayName("pagarCarga con Tarjeta no lanza excepcion")
    void pagarCargaConTarjeta(PagoServiciosImpl service) {
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setId(1);
        tarjeta.setTipo(TipoTarjeta.DEBITO);
        repoMemoria.agregarMedioPago(tarjeta);
        Assertions.assertDoesNotThrow(() -> service.pagarCarga(1, 500, 1));
    }
    @Test
    @DisplayName("pagarCarga con CuentaUTE no lanza excepcion")
    void pagarCargaConCuentaUTE(PagoServiciosImpl service) {
        CuentaUTE cuenta = new CuentaUTE();
        cuenta.setId(1);
        cuenta.setNumeroCuenta("12345");
        repoMemoria.agregarMedioPago(cuenta);
        Assertions.assertDoesNotThrow(() -> service.pagarCarga(1, 300, 1));
    }
    @Test
    @DisplayName("pagarCarga con medioPago inexistente lanza excepcion")
    void pagarCargaMedioPagoInexistente(PagoServiciosImpl service) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.pagarCarga(1, 500, 99));
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
        Assertions.assertFalse(resultado.isEmpty());
        Assertions.assertEquals(1, resultado.size());
    }
    @Test
    @DisplayName("consultarPagos sin datos lanza excepcion")
    void consultarPagosSinDatos(PagoServiciosImpl service) {
        LocalDateTime ini = LocalDateTime.of(2026, 5, 20, 19, 0);
        LocalDateTime fin = LocalDateTime.of(2026, 5, 20, 20, 0);
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.consultarPagos(1, ini, fin));
    }

    @Test
    @DisplayName("Carga guardada")
    void guardarCarga(){
        PagoRepositorioEnMemoria repo = new PagoRepositorioEnMemoria();
        Cliente cliente = new ClienteComun();
        cliente.setId(1);

        Carga carga = new Carga();
        carga.setId(1);
        carga.setCliente(cliente);

        long id = repo.guardarCarga(carga);

        Assertions.assertEquals(1, id);
        Assertions.assertEquals(carga, repo.cargas.get(id));
    }
}