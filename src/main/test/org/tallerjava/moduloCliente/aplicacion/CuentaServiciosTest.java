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
@EnableWeld
class CuentaServiciosTest {
    private ClienteRepositorioEnMemoria repoMemoria;
    @WeldSetup
    public WeldInitiator weld = WeldInitiator.from(CuentaServiciosImpl.class, PublicadorEventoCliente.class).addBeans(crearMockRepositorio()).build();
    private Bean<?> crearMockRepositorio() {
        repoMemoria = new ClienteRepositorioEnMemoria();
        return MockBean.builder().types(ClienteRepo.class).scope(ApplicationScoped.class).creating(repoMemoria).build();
    }
    @BeforeEach
    void setUp() {
        repoMemoria.clientes.clear();
        repoMemoria.mediosPago.clear();
    }
    @Test
    @DisplayName("registrarCliente crea cliente exitosamente")
    void registrarCliente(CuentaServiciosImpl service) {
        Cliente cliente = new ClienteComun();
        cliente.setCedula("12345678");
        cliente.setNombreCompleto("Juan Perez");
        long id = service.registarCliente(cliente);
        Assertions.assertTrue(id > 0);
        Assertions.assertNotNull(repoMemoria.buscaClientePorId(id));
    }
    @Test
    @DisplayName("obtenerClientes devuelve lista con clientes registrados")
    void obtenerClientes(CuentaServiciosImpl service) {
        Cliente cliente = new ClienteComun();
        cliente.setId(1);
        repoMemoria.registarCliente(cliente);
        Assertions.assertFalse(service.obtenerClientes().isEmpty());
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
    }
    @Test
    @DisplayName("realizarReclamo en cliente inexistente lanza excepcion")
    void realizarReclamoClienteInexistente(CuentaServiciosImpl service) {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                service.realizarReclamo(99, "Reclamo invalido"));
    }
}