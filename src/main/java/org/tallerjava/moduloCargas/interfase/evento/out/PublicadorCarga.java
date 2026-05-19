package org.tallerjava.moduloCargas.interfase.evento.out;

public interface PublicadorCarga {
    public void iniciarCarga(long cargaId,long clienteId);
    public void finalizarCarga(long cargaId, long clienteId, float carga, long medioPagoId,float importeTotal, float recargo);
}
