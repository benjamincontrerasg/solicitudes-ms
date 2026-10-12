package com.duoc.solicitudes_ms.service;

import com.duoc.solicitudes_ms.model.Solicitud;
import com.duoc.solicitudes_ms.repository.SolicitanteRepository;
import com.duoc.solicitudes_ms.repository.SolicitudRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class SolicitudService {

    private final SolicitudRepository repository;
    private final SolicitanteRepository solicitanteRepository;

    public SolicitudService(SolicitudRepository repository, SolicitanteRepository solicitanteRepository) {
        this.repository = repository;
        this.solicitanteRepository = solicitanteRepository;
    }

    public Optional<Solicitud> guardar(Long solicitanteId, Solicitud solicitud) {
        return solicitanteRepository.findById(solicitanteId).map(solicitante -> {
            solicitud.setSolicitante(solicitante);
            if (solicitud.getEstado() == null || solicitud.getEstado().trim().isEmpty()) {
                solicitud.setEstado("PENDIENTE");
            }
            if (solicitud.getFechaCreacion() == null) {
                solicitud.setFechaCreacion(LocalDate.now());
            }
            return repository.save(solicitud);
        });
    }

    public List<Solicitud> obtenerTodos() {
        return repository.findAll();
    }

    public Optional<Solicitud> buscaPorId(Long id) {
        return repository.findById(id);
    }

    public Optional<Solicitud> actualizar(Long id, Solicitud datos) {
        return repository.findById(id).map(solicitudExistente -> {
            solicitudExistente.setDescripcion(datos.getDescripcion());
            solicitudExistente.setDireccion(datos.getDireccion());
            solicitudExistente.setEstado(datos.getEstado());
            return repository.save(solicitudExistente);
        });
    }

    public boolean eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Solicitud> buscarPorEstado(String estado) {
        return repository.findByEstado(estado);
    }

    public List<Solicitud> buscarPorSolicitante(Long solicitanteId) {
        return repository.findBySolicitanteId(solicitanteId);
    }
}
