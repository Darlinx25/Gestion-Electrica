package org.tallerjava.moduloClientes.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity(name = "CuentaUTE_Clientes") //no puedo tener dos entidades que se llamen igual
@Table (name = "clientes_pagoCuentaUTE")
public class CuentaUTE extends MedioPago {
    private String numeroCuenta;
}
