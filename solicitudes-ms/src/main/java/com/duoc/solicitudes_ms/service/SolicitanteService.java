package com.duoc.solicitudes_ms.service;

import com.duoc.solicitudes_ms.model.Solicitante;
import com.duoc.solicitudes_ms.repository.SolicitanteRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Servicio para gestionar la lógica de negocio de los Solicitantes.
 */
@Service
public class SolicitanteService {

    private final SolicitanteRepository repository;

    public SolicitanteService(SolicitanteRepository repository) {
        this.repository = repository;
    }

    /**
     * Guarda un nuevo solicitante en el repositorio.
     */
    public Solicitante guardar(Solicitante solicitante) {
        return repository.save(solicitante);
    }

    /**
     * Obtiene la lista completa de solicitantes registrados.
     */
    public List<Solicitante> obtenerTodos() {
        return repository.findAll();
    }

    /**
     * Busca un solicitante por su identificador único.
     */
    public Optional<Solicitante> buscaPorId(Long id) {
        return repository.findById(id);
    }

    /**
     * Actualiza los datos de un solicitante existente.
     */
    public Optional<Solicitante> actualizar(Long id, Solicitante datos) {
        return repository.findById(id).map(solicitanteExistente -> {
            solicitanteExistente.setNombre(datos.getNombre());
            solicitanteExistente.setTipo(datos.getTipo());
            return repository.save(solicitanteExistente);
        });
    }

    /**
     * Elimina un solicitante según su identificador si existe en la base de datos.
     */
    public boolean eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
