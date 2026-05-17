/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.tallerjava.moduloPagos.aplicacion;

import java.time.LocalDateTime;
import java.util.List;
import org.tallerjava.moduloCargas.dominio.Carga;
import org.tallerjava.moduloPagos.dominio.Cliente;
import org.tallerjava.moduloPagos.dominio.MedioPago;


public interface PagoServicios {
    
    public void pagarCarga(Cliente cliente, float importe, MedioPago medioPago);
   
}
