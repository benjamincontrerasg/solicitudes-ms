package com.duoc.solicitudes_ms.service;

import com.duoc.solicitudes_ms.model.Solicitante;
import com.duoc.solicitudes_ms.repository.SolicitanteRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class SolicitanteService {

    private final SolicitanteRepository repository;

    public SolicitanteService(SolicitanteRepository repository) {
        this.repository = repository;
    }

    public Solicitante guardar(Solicitante solicitante) {
        return repository.save(solicitante);
    }

    public List<Solicitante> obtenerTodos() {
        return repository.findAll();
    }

    public Optional<Solicitante> buscaPorId(Long id) {
        return repository.findById(id);
    }

    public Optional<Solicitante> actualizar(Long id, Solicitante datos) {
        return repository.findById(id).map(solicitanteExistente -> {
            solicitanteExistente.setNombre(datos.getNombre());
            solicitanteExistente.setTipo(datos.getTipo());
            return repository.save(solicitanteExistente);
        });
    }

    public boolean eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
