package org.tallerjava.moduloClientes.interfase.remota.movil;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import org.tallerjava.moduloClientes.dominio.Cliente;
import java.util.List;

import org.tallerjava.moduloClientes.aplicacion.CuentaServicios;
import org.tallerjava.moduloClientes.dominio.MedioPago;
import org.tallerjava.moduloClientes.interfase.Dtos.ClienteDTO;
import org.tallerjava.moduloClientes.interfase.Dtos.MedioPagoDTO;
import org.tallerjava.moduloClientes.interfase.Dtos.ReclamoDTO;

@ApplicationScoped
@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteMovilAPI {

    @Inject
    private CuentaServicios clienteService;

    //Registrar ClienteComun
    //curl -X POST -v http://localhost:8080/Gestion-Electrica/API/clientes/movil/registrar -H "Content-Type: application/json" -d '{"cedula":"1234567890","nombreCompleto":"pablito guitiérrez","telefono":"091234567","password":"123","esProfesional":false}'
    //Registrar ClienteProfesional UBER
    //curl -X POST -v http://localhost:8080/Gestion-Electrica/API/clientes/movil/registrar -H "Content-Type: application/json" -d '{"cedula":"2234567890","nombreCompleto":"josefina rodríguez","telefono":"091234567","password":"123","esProfesional":true,"tipoProfesional":"UBER","porcentajeDescuento":20.5}'
    @POST
    @Path("/movil/registrar")
    @Consumes(MediaType.APPLICATION_JSON)
    public long registrarCliente(ClienteDTO clienteDTO) {
        Cliente cliente = clienteDTO.buildCliente();
        return clienteService.registarCliente(cliente);
    }

    //Alta cuentaUTE
    //curl -X POST -v http://localhost:8080/Gestion-Electrica/API/clientes/movil/medios-pago -H "Content-Type: application/json" -d '{"clienteId":"1","medio":"CUENTA_UTE","numeroCuenta":"1234"}'
    //Alta tarjeta débito
    //curl -X POST -v http://localhost:8080/Gestion-Electrica/API/clientes/movil/medios-pago -H "Content-Type: application/json" -d '{"clienteId":"1","medio":"TARJETA_DEBITO","numero":"1234","fechaVencimiento":"2028-10-23","digitoVerificacion":"123"}'
    //Alta tarjeta crédito (no se debe persistir, devuelve 500 internal server error, ver si se guarda en memoria de todas formas o hacer clase aparte sin @Entity)
    //curl -X POST -v http://localhost:8080/Gestion-Electrica/API/clientes/movil/medios-pago -H "Content-Type: application/json" -d '{"clienteId":"1","medio":"TARJETA_CREDITO","numero":"2234","fechaVencimiento":"2028-10-23","digitoVerificacion":"223"}'
    @POST
    @Path("/movil/medios-pago")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public boolean altaMedioPago(MedioPagoDTO medioPagoDTO,
            @Context SecurityContext securityContext) {

        String idUsuarioString = securityContext.getUserPrincipal().getName();

        long authenticatedClienteId = Long.parseLong(idUsuarioString);

        if (authenticatedClienteId != medioPagoDTO.getClienteId()) {
            throw new ForbiddenException();
        }

        MedioPago medioPago = medioPagoDTO.buildMedioPago();
        return clienteService.altaMedioPago(medioPagoDTO.getClienteId(), medioPago);
    }

    //Realizar reclamo
    //curl -X POST http://localhost:8080/Gestion-Electrica/API/clientes/movil/reclamo -H "Content-Type: application/json" -d '{"clienteId":1,"informacion":"El cargador no funciona correctamente"}'
    @POST
    @Path("/movil/reclamo")
    @RolesAllowed("USER")
    public long realizarReclamo(ReclamoDTO reclamoDTO,
            @Context SecurityContext securityContext) {
        
        String idUsuarioString = securityContext.getUserPrincipal().getName();

        long authenticatedClienteId = Long.parseLong(idUsuarioString);

        if (authenticatedClienteId != reclamoDTO.getClienteId()) {
            throw new ForbiddenException();
        }
        
        return clienteService.realizarReclamo(reclamoDTO.getClienteId(), reclamoDTO.getInformacion());
    }
}
