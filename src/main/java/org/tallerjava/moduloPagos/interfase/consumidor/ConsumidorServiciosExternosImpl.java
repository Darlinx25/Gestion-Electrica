package org.tallerjava.moduloPagos.interfase.consumidor;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
public class ConsumidorServiciosExternosImpl implements ConsumidorServiciosExternos {

    @Override
    public boolean pagarTarjeta(String nroTarjeta) {
        Client cliente = ClientBuilder.newClient();

        PagoRequest request = new PagoRequest();
        request.numeroTarjeta = nroTarjeta;

        Boolean respuesta = cliente
                .target("http://host.docker.internal:8180/ServicioMedioPagoMock/api/procesar-pago")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(request, MediaType.APPLICATION_JSON),
                        Boolean.class);

        return respuesta;
    }

    @Override
    public boolean pagarUTE(String nroCuenta) {
        Client cliente = ClientBuilder.newClient();

        NotificarPagoRequest request = new NotificarPagoRequest();
        request.nroFactura = nroCuenta;

        Boolean respuesta = cliente
                .target("http://host.docker.internal:8280/FacturaUTEMock/api/notificar-pago")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(request, MediaType.APPLICATION_JSON),
                        Boolean.class);

        return respuesta;
    }

}
