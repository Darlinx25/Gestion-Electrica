package org.tallerjava.moduloClientes.aplicacion;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.tallerjava.moduloClientes.dominio.Cliente;
import org.tallerjava.moduloClientes.dominio.ClienteProfesional;
import org.tallerjava.moduloClientes.dominio.Reclamo;
import org.tallerjava.moduloClientes.dominio.repositorios.ClienteRepo;
import org.tallerjava.moduloClientes.dominio.MedioPago;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.tallerjava.moduloClientes.infraestructura.messaging.ProductorReclamo;
import org.tallerjava.moduloClientes.interfase.Dtos.ReclamoDTO;
import org.tallerjava.moduloClientes.interfase.evento.out.PublicadorEventoCliente;
import org.tallerjava.moduloClientes.interfase.Dtos.ClienteDTO;

@ApplicationScoped
public class CuentaServiciosImpl implements CuentaServicios{

    @Inject
    private ClienteRepo clienteRepo;
    
    @Inject
    private PublicadorEventoCliente evento;

    @Inject
    private ProductorReclamo productorReclamo;

    @Override
    public List<ClienteDTO> obtenerClientes() {
        List<Cliente> clientes = clienteRepo.listarClientes();
        List<ClienteDTO> clientesDTO = new ArrayList<>();

        for(Cliente cliente : clientes){
            ClienteDTO dto = new ClienteDTO();

            dto.setCedula(cliente.getCedula());
            dto.setNombreCompleto(cliente.getNombreCompleto());
            dto.setTelefono(cliente.getTelefono());
            dto.setPassword(cliente.getPassword());

            if (cliente instanceof ClienteProfesional profesional) {
                dto.setEsProfesional(true);
                dto.setTipoProfesional(profesional.getTipo());
                dto.setPorcentajeDescuento(profesional.getPorcentajeDescuento());
            } else {
                dto.setEsProfesional(false);
            }
            clientesDTO.add(dto);
        }

        return clientesDTO;
    }

    @Override
    @Transactional
    public long registarCliente(Cliente cliente) {
        long clienteId = clienteRepo.registarCliente(cliente);
        evento.publicarCliente(cliente);
        return clienteId;
    }

    @Override
    @Transactional
    public boolean altaMedioPago(long clienteId, MedioPago medioPago){
        Cliente cliente = clienteRepo.buscaClientePorId(clienteId);
        if (cliente == null) {
            return false;
        }
        cliente.addMedioPago(medioPago);
        clienteRepo.altaMedioPago(medioPago);
        evento.publicarNuevoMedioPago(medioPago);
        return true;
    }

    @Override
    @Transactional
    public long realizarReclamo(long clienteId, String informacion) {
        Cliente cliente = clienteRepo.buscaClientePorId(clienteId);
        if (cliente == null) {
            throw new IllegalArgumentException("No existe cliente con id: " + clienteId);
        }
        Reclamo reclamo = cliente.realizarReclamo(informacion);
        clienteRepo.guardarReclamo(reclamo);
        productorReclamo.enviarReclamo(reclamo.getId(), reclamo.getInformacion());
        return reclamo.getId();
    }

    @Override
    public List<ReclamoDTO> listarReclamosPorEtiqueta(String etiqueta) {
        return clienteRepo.buscarReclamosPorEtiqueta(etiqueta).stream()
            .map(r -> new ReclamoDTO(r.getCliente().getId(), r.getInformacion(), r.getEtiqueta()))
            .collect(Collectors.toList());
    }
    
}
