/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.tallerjava.moduloPagos.infraestructura.persistencia;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.tallerjava.moduloPagos.dominio.Cliente;
import org.tallerjava.moduloPagos.dominio.repositorios.PagoRepo;

@ApplicationScoped
public class PagoRepositorioImpl implements PagoRepo {
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void registrarCliente(Cliente cliente) {
        em.persist(cliente);
    }
}
