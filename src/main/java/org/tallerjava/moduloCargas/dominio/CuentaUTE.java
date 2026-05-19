package org.tallerjava.moduloCargas.dominio;

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
@Entity(name = "CuentaUTE_Cargas")
@Table (name = "cargas_pagoCuentaUTE")
public class CuentaUTE extends MedioPago {
    private String numeroCuenta;
}
