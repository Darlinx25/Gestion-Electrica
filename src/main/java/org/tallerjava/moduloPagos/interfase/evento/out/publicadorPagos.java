package org.tallerjava.moduloPagos.interfase.evento.out;

public interface publicadorPagos {
    public void pagarCarga(long cargaId);
    public void pagoTarjetaRealizado();
    public void pagoTarjetaError();
    public void pagoUTERealizado();
}
