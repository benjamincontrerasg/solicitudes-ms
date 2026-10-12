package com.duoc.solicitudes_ms.service;

import com.duoc.solicitudes_ms.model.Solicitud;
import com.duoc.solicitudes_ms.repository.SolicitudRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class SolicitudService {

    private final SolicitudRepository repository;

    public SolicitudService(SolicitudRepository repository) {
        this.repository = repository;
    }

    public Solicitud guardar(Solicitud solicitud) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        solicitud.setFolio("RL-" + uuid.substring(0, 8).toUpperCase());
        solicitud.setEstado("PENDIENTE");
        solicitud.setFechaCreacion(LocalDate.now());
        return repository.save(solicitud);
    }

    public List<Solicitud> obtenerTodos() {
        return repository.findAll();
    }

    public Optional<Solicitud> buscaPorId(Long id) {
        return repository.findById(id);
    }

    public Optional<Solicitud> actualizar(Long id, Solicitud datos) {
        return repository.findById(id).map(solicitudExistente -> {
            solicitudExistente.setDireccion(datos.getDireccion());
            solicitudExistente.setTipoResiduo(datos.getTipoResiduo());
            solicitudExistente.setHorarioPreferido(datos.getHorarioPreferido());
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

    public List<Solicitud> buscarPorVecino(Long vecinoId) {
        return repository.findByVecinoId(vecinoId);
    }

    public Optional<Solicitud> buscaPorFolio(String folio) {
        return repository.findByFolio(folio);
    }
}
