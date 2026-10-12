package com.duoc.solicitudes_ms.service;

import com.duoc.solicitudes_ms.model.Solicitud;
import com.duoc.solicitudes_ms.repository.SolicitudRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

/**
 * Servicio de negocio para las solicitudes de retiro de reciclaje.
 * Genera el folio de recepción y gestiona el ciclo de vida de la solicitud.
 */
@Service
public class SolicitudService {

    private final SolicitudRepository repository;

    /** Constructor con inyección del repositorio. */
    public SolicitudService(SolicitudRepository repository) {
        this.repository = repository;
    }

    /**
     * Guarda una nueva solicitud.
     * Genera un folio con formato "RL-" + 8 caracteres en mayúscula desde UUID,
     * fuerza estado PENDIENTE y fecha de creación actual.
     */
    public Solicitud guardar(Solicitud solicitud) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        solicitud.setFolio("RL-" + uuid.substring(0, 8).toUpperCase());
        solicitud.setEstado("PENDIENTE");
        solicitud.setFechaCreacion(LocalDate.now());
        return repository.save(solicitud);
    }

    /** Devuelve todas las solicitudes. */
    public List<Solicitud> obtenerTodos() {
        return repository.findAll();
    }

    /** Busca una solicitud por su id. */
    public Optional<Solicitud> buscaPorId(Long id) {
        return repository.findById(id);
    }

    /**
     * Actualiza dirección, tipoResiduo, horarioPreferido y estado.
     * No modifica folio, vecinoId ni fechaCreacion.
     * Devuelve Optional.empty() si no existe.
     */
    public Optional<Solicitud> actualizar(Long id, Solicitud datos) {
        return repository.findById(id).map(solicitudExistente -> {
            solicitudExistente.setDireccion(datos.getDireccion());
            solicitudExistente.setTipoResiduo(datos.getTipoResiduo());
            solicitudExistente.setHorarioPreferido(datos.getHorarioPreferido());
            solicitudExistente.setEstado(datos.getEstado());
            return repository.save(solicitudExistente);
        });
    }

    /** Elimina una solicitud por id. Devuelve true si existía, false si no. */
    public boolean eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /** Busca solicitudes por estado (PENDIENTE, ASIGNADA, REALIZADA, FALLIDA). */
    public List<Solicitud> buscarPorEstado(String estado) {
        return repository.findByEstado(estado);
    }

    /** Busca solicitudes por el id del vecino. */
    public List<Solicitud> buscarPorVecino(Long vecinoId) {
        return repository.findByVecinoId(vecinoId);
    }

    /** Busca una solicitud por su folio de recepción. */
    public Optional<Solicitud> buscaPorFolio(String folio) {
        return repository.findByFolio(folio);
    }
}
