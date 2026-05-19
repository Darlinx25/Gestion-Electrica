package org.tallerjava.moduloClientes.interfase.remota;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.tallerjava.moduloClientes.dominio.Cliente;
import java.util.List;

import org.tallerjava.moduloClientes.aplicacion.CuentaServicios;
import org.tallerjava.moduloClientes.dominio.MedioPago;

@ApplicationScoped
@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResourseAPI {


    @Inject
    private CuentaServicios clienteService;

    //http://localhost:8080/Gestion-Electrica/carga/clientes Luego vemos de mejorar la URL, por ahora con "carga"
    //curl -v http://localhost:8080/Gestion-Electrica/carga/clientes
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<ClienteDTO> obtenerClientes(){
        return clienteService.obtenerClientes();
    }
    
    //registrar ClienteComun
    //curl -X POST -v http://localhost:8080/Gestion-Electrica/carga/clientes -H "Content-Type: application/json" -d '{"cedula":"1234567890","nombreCompleto":"pablito guitiérrez","telefono":"091234567","password":"123","esProfesional":false}'
    //registrar ClienteProfesional UBER
    //curl -X POST -v http://localhost:8080/Gestion-Electrica/carga/clientes -H "Content-Type: application/json" -d '{"cedula":"2234567890","nombreCompleto":"josefina rodríguez","telefono":"091234567","password":"123","esProfesional":true,"tipoProfesional":"UBER","porcentajeDescuento":20.5}'
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public long registrarCliente(ClienteDTO clienteDTO) {
        Cliente cliente = clienteDTO.buildCliente();
        return clienteService.registarCliente(cliente);
    }
    
    //alta cuentaUTE
    //curl -X POST -v http://localhost:8080/Gestion-Electrica/carga/clientes/medios-pago -H "Content-Type: application/json" -d '{"clienteId":"1","medio":"CUENTA_UTE","numeroCuenta":"1234"}'
    //alta tarjeta débito
    //curl -X POST -v http://localhost:8080/Gestion-Electrica/carga/clientes/medios-pago -H "Content-Type: application/json" -d '{"clienteId":"1","medio":"TARJETA_DEBITO","numero":"1234","fechaVencimiento":"2028-10-23","digitoVerificacion":"123"}'
    //alta tarjeta crédito (no se debe persistir, devuelve 500 internal server error, ver si se guarda en memoria de todas formas o hacer clase aparte sin @Entity)
    //curl -X POST -v http://localhost:8080/Gestion-Electrica/carga/clientes/medios-pago -H "Content-Type: application/json" -d '{"clienteId":"1","medio":"TARJETA_CREDITO","numero":"2234","fechaVencimiento":"2028-10-23","digitoVerificacion":"223"}'
    @POST
    @Path("/medios-pago")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean altaMedioPago(MedioPagoDTO medioPagoDTO) {
        MedioPago medioPago = medioPagoDTO.buildMedioPago();
        return clienteService.altaMedioPago(medioPagoDTO.getClienteId(), medioPago);
    }
    
    //curl -X POST http://localhost:8080/Gestion-Electrica/carga/clientes/reclamo -H "Content-Type: application/json" -d '{"clienteId":1,"informacion":"El cargador no funciona correctamente"}'
    @POST
    @Path("/reclamo")
    public long realizarReclamo(ReclamoDTO reclamoDTO) {
        return clienteService.realizarReclamo(reclamoDTO.getClienteId(), reclamoDTO.getInformacion());
    }
}
