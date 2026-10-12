package com.duoc.solicitudes_ms.service;

import com.duoc.solicitudes_ms.model.Solicitud;
import com.duoc.solicitudes_ms.repository.SolicitanteRepository;
import com.duoc.solicitudes_ms.repository.SolicitudRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Servicio para gestionar la lógica de negocio de las Solicitudes.
 */
@Service
public class SolicitudService {

    private final SolicitudRepository repository;
    private final SolicitanteRepository solicitanteRepository;

    public SolicitudService(SolicitudRepository repository, SolicitanteRepository solicitanteRepository) {
        this.repository = repository;
        this.solicitanteRepository = solicitanteRepository;
    }

    /**
     * Guarda una nueva solicitud validando la existencia previa del solicitante.
     */
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

    /**
     * Obtiene la lista completa de solicitudes registradas.
     */
    public List<Solicitud> obtenerTodos() {
        return repository.findAll();
    }

    /**
     * Busca una solicitud por su identificador único.
     */
    public Optional<Solicitud> buscaPorId(Long id) {
        return repository.findById(id);
    }

    /**
     * Actualiza la descripción, dirección y estado de una solicitud existente sin alterar el solicitante ni la fecha.
     */
    public Optional<Solicitud> actualizar(Long id, Solicitud datos) {
        return repository.findById(id).map(solicitudExistente -> {
            solicitudExistente.setDescripcion(datos.getDescripcion());
            solicitudExistente.setDireccion(datos.getDireccion());
            solicitudExistente.setEstado(datos.getEstado());
            return repository.save(solicitudExistente);
        });
    }

    /**
     * Elimina una solicitud según su identificador si existe en la base de datos.
     */
    public boolean eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Busca solicitudes filtradas por su estado.
     */
    public List<Solicitud> buscarPorEstado(String estado) {
        return repository.findByEstado(estado);
    }

    /**
     * Busca las solicitudes asociadas a un solicitante por su identificador.
     */
    public List<Solicitud> buscarPorSolicitante(Long solicitanteId) {
        return repository.findBySolicitanteId(solicitanteId);
    }
}
