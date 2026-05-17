package org.tallerjava.moduloClientes.interfase.remota;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tallerjava.moduloClientes.dominio.Cliente;
import org.tallerjava.moduloClientes.dominio.ClienteComun;
import org.tallerjava.moduloClientes.dominio.ClienteProfesional;
import org.tallerjava.moduloClientes.dominio.TipoProfesional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    private String cedula;
    private String nombreCompleto;
    private String telefono;
    private String password;
    
    private boolean esProfesional;
    private TipoProfesional tipoProfesional;
    private float porcentajeDescuento;
    
    public Cliente buildCliente() {
        Cliente cliente;
        if (this.esProfesional) {
            cliente = new ClienteProfesional();
            cliente.setCedula(this.cedula);
            cliente.setNombreCompleto(this.nombreCompleto);
            cliente.setTelefono(this.telefono);
            cliente.setPassword(this.password);
            ((ClienteProfesional) cliente).setTipo(this.tipoProfesional);
            ((ClienteProfesional) cliente).setPorcentajeDescuento(this.porcentajeDescuento);
        } else {
            cliente = new ClienteComun();
            cliente.setCedula(this.cedula);
            cliente.setNombreCompleto(this.nombreCompleto);
            cliente.setTelefono(this.telefono);
            cliente.setPassword(this.password);
        }
        return cliente;
    }
}
