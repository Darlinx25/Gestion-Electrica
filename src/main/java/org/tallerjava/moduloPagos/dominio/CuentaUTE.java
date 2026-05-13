package org.tallerjava.moduloPagos.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "pagos_pagoCuentaUTE")
public class CuentaUTE extends MedioPago {
    private String numeroCuenta;
}
