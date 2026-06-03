package org.tallerjava;

import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/API")
@DeclareRoles({"USER", "ADMIN"})
public class GestionElectricaApplication extends Application {
}
