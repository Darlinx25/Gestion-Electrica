package org.tallerjava.moduloCliente.aplicacion;
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
import org.tallerjava.moduloClientes.aplicacion.CuentaServiciosImpl;
import org.tallerjava.moduloClientes.dominio.*;
import org.tallerjava.moduloClientes.dominio.repositorios.ClienteRepo;
import org.tallerjava.moduloClientes.interfase.evento.out.PublicadorEventoCliente;
import org.tallerjava.moduloClientes.interfase.Dtos.ClienteDTO;
import java.util.List;
@EnableWeld
class CuentaServiciosTest {
    private ClienteRepositorioEnMemoria repoMemoria;
    @WeldSetup
    public WeldInitiator weld = WeldInitiator.from(CuentaServiciosImpl.class, PublicadorEventoCliente.class)
            .addBeans(crearMockRepositorio()).build();
    private Bean<?> crearMockRepositorio() {
        repoMemoria = new ClienteRepositorioEnMemoria();
        return MockBean.builder().types(ClienteRepo.class).scope(ApplicationScoped.class).creating(repoMemoria).build();
    }
    @BeforeEach
    void setUp() {
        repoMemoria.clientes.clear();
        repoMemoria.mediosPago.clear();
        repoMemoria.reclamos.clear();
    }
    @Test
    @DisplayName("registrarCliente crea cliente exitosamente")
    void registrarCliente(CuentaServiciosImpl service) {
        Cliente cliente = new ClienteComun();
        cliente.setCedula("12345678");
        cliente.setNombreCompleto("Juan Perez");
        long id = service.registarCliente(cliente);
        Assertions.assertTrue(id > 0);
        Cliente resultado = repoMemoria.buscaClientePorId(id);
        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("12345678", resultado.getCedula());
        Assertions.assertEquals("Juan Perez", resultado.getNombreCompleto());
    }
    @Test
    @DisplayName("obtenerClientes devuelve lista con datos correctos del cliente")
    void obtenerClientes(CuentaServiciosImpl service) {
        Cliente cliente = new ClienteComun();
        cliente.setCedula("87654321");
        cliente.setNombreCompleto("Maria Lopez");
        repoMemoria.registarCliente(cliente);
        List<ClienteDTO> resultado = service.obtenerClientes();
        Assertions.assertEquals(1, resultado.size());
        Assertions.assertEquals("87654321", resultado.get(0).getCedula());
        Assertions.assertEquals("Maria Lopez", resultado.get(0).getNombreCompleto());
        Assertions.assertFalse(resultado.get(0).isEsProfesional());
    }
    @Test
    @DisplayName("altaMedioPago en cliente existente retorna true")
    void altaMedioPagoClienteExistente(CuentaServiciosImpl service) {
        Cliente cliente = new ClienteComun();
        cliente.setId(1);
        repoMemoria.registarCliente(cliente);
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setId(2);
        tarjeta.setTipo(org.tallerjava.moduloClientes.dominio.TipoTarjeta.DEBITO);
        boolean resultado = service.altaMedioPago(1, tarjeta);
        Assertions.assertTrue(resultado);
        Cliente clienteActualizado = repoMemoria.buscaClientePorId(1);
        Assertions.assertEquals(1, clienteActualizado.getMediosPago().size());
    }
    @Test
    @DisplayName("altaMedioPago en cliente inexistente retorna false")
    void altaMedioPagoClienteInexistente(CuentaServiciosImpl service) {
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setId(1);
        boolean resultado = service.altaMedioPago(99, tarjeta);
        Assertions.assertFalse(resultado);
    }
    @Test
    @DisplayName("realizarReclamo crea reclamo para cliente existente")
    void realizarReclamoClienteExistente(CuentaServiciosImpl service) {
        Cliente cliente = new ClienteComun();
        cliente.setId(1);
        repoMemoria.registarCliente(cliente);
        long reclamoId = service.realizarReclamo(1, "Cargador fuera de servicio");
        Assertions.assertTrue(reclamoId > 0);
        Reclamo reclamo = repoMemoria.reclamos.get(reclamoId);
        Assertions.assertNotNull(reclamo);
        Assertions.assertEquals("Cargador fuera de servicio", reclamo.getInformacion());
        Assertions.assertEquals(1, reclamo.getCliente().getId());
    }
    @Test
    @DisplayName("realizarReclamo en cliente inexistente lanza excepcion")
    void realizarReclamoClienteInexistente(CuentaServiciosImpl service) {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                service.realizarReclamo(99, "Reclamo invalido"));
    }
}