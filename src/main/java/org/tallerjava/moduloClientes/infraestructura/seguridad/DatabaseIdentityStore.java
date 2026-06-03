package org.tallerjava.moduloClientes.infraestructura.seguridad;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import org.tallerjava.moduloClientes.dominio.Cliente;
import org.tallerjava.moduloClientes.dominio.repositorios.ClienteRepo;
import java.util.Set;

@ApplicationScoped
public class DatabaseIdentityStore implements IdentityStore {

    @Inject
    private ClienteRepo clienteRepo;

    @Override
    public CredentialValidationResult validate(Credential credential) {
        UsernamePasswordCredential login = (UsernamePasswordCredential) credential;
        Cliente c = clienteRepo.buscaClientePorCedula(login.getCaller());
        if (c == null || !c.getPassword().equals(login.getPasswordAsString()))
            return CredentialValidationResult.INVALID_RESULT;

        return new CredentialValidationResult(
                String.valueOf(c.getId()),
                Set.of(c.getRol() != null ? c.getRol() : "USER")
        );
    }
}