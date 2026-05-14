/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.tallerjava.moduloPagos.interfase;

import java.time.LocalDate;
import org.tallerjava.moduloClientes.dominio.Cliente;
import org.tallerjava.moduloPagos.dominio.TipoTarjeta;


public class MedioPagoDTO {
    private long id;
    
    //datos de cuentaUTE
    private String numeroCuenta;
    
    //datos Tarjeta
    private String numero;
    private LocalDate fechaVencimiento;
    private String digitoVerificacion;
    private TipoTarjeta tipo;
    
}
