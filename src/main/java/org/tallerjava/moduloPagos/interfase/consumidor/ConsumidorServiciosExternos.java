package org.tallerjava.moduloPagos.interfase.consumidor;

public interface ConsumidorServiciosExternos {
    public boolean pagarTarjeta(String nroTarjeta);
    public boolean pagarUTE(String nroCuenta);
}
