package org.tallerjava.moduloCargas.interfase.remota;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.tallerjava.moduloCargas.aplicacion.CargaServicios;
import org.tallerjava.moduloCargas.dominio.EstacionCarga;
import org.tallerjava.moduloCargas.dominio.EstadoCargador;
import org.tallerjava.moduloCargas.dominio.TipoCargador;
import org.tallerjava.moduloCargas.dominio.TipoConector;
import org.tallerjava.moduloCargas.interfase.CargaDTO;
import org.tallerjava.moduloCargas.interfase.CargadorDTO;
import org.tallerjava.moduloCargas.interfase.EstacionDTO;
import org.tallerjava.moduloCargas.interfase.IniciarCargaRequestDTO;


import java.util.List;


@ApplicationScoped
@Path("/cargas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CargaResourseAPI {
    @Inject
    private CargaServicios cargaService;


    //curl -v http://localhost:8080/Gestion-Electrica/carga/cargas
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<EstacionCarga> obtenerEstaciones() {
        return cargaService.obtenerEstaciones();
    }

    //curl -X POST -v http://localhost:8080/Gestion-Electrica/carga/cargas/iniciar -H "Content-Type: application/json" -d' {"clienteId":1,"medioPagoId":1}'
    @POST
    @Path("/iniciar")
    public void iniciarCarga(IniciarCargaRequestDTO cargaDTO) {
        cargaService.iniciarCarga(cargaDTO.getClienteId(), cargaDTO.getMedioPagoId());
    }

    //registrar estacion
    //curl -X POST http://localhost:8080/Gestion-Electrica/carga/cargas/estacion \-H "Content-Type: application/json" \-d '{"descripcion":"prueba estacion","calle":"Lenguas de Diamante","departamento":"Maldonado","longitud":2,"latitud":3}'
    @POST
    @Path("/estacion")
    @Consumes(MediaType.APPLICATION_JSON)
    public long altaEstacion(EstacionDTO estacionDTO) {
        return cargaService.altaEstacion(estacionDTO);
    }

    //registrar cargador
    //curl -X POST -v http://localhost:8080/Gestion-Electrica/carga/cargas/cargador -H "Content-Type: application/json" -d '{"tipo": "RAPIDA", "tieneCable": true, "tipoConector": "TIPO2", "estado": "DISPONIBLE", "potenciaMinima": 150, "estacionId": 1}'
    @POST
    @Path("/cargador")
    @Consumes(MediaType.APPLICATION_JSON)
    public long altaCargador(CargadorDTO cargadorDTO) {
        return cargaService.altaCargador(cargadorDTO);
    }


    @GET
    @Path("/carga-actual/{clienteId}")
    public CargaDTO verCargaActual(@PathParam("clienteId") long clienteId) {
        return cargaService.verCargaActual(clienteId);
    }
}





