package org.tallerjava.moduloClientes.infraestructura.seguridad;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;

@ApplicationScoped
@BasicAuthenticationMechanismDefinition(realmName = "GestionElectrica")
public class SecurityConfig {}
