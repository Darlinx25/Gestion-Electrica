/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.tallerjava.moduloPagos.aplicacion;

import jakarta.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.tallerjava.moduloCargas.dominio.Carga;
import org.tallerjava.moduloClientes.dominio.Cliente;
import org.tallerjava.moduloPagos.dominio.MedioPago;
import org.tallerjava.moduloPagos.dominio.repositorios.PagoRepo;

public class PagoServiciosImpl implements PagoServicios {
    @Inject
    private PagoRepo pagoRepo;
    
    
    @Override
    public void pagarCarga(Cliente cliente, float importe, MedioPago medioPago){
        
    }
    

    
    
}
