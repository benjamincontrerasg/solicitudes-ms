package com.duoc.solicitudes_ms.repository;

import com.duoc.solicitudes_ms.model.Solicitud;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad Solicitud.
 * Provee búsquedas por estado, vecinoId y folio.
 */
@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

    /** Busca solicitudes por su estado (PENDIENTE, ASIGNADA, REALIZADA, FALLIDA). */
    List<Solicitud> findByEstado(String estado);

    /** Busca solicitudes por el id del vecino. */
    List<Solicitud> findByVecinoId(Long vecinoId);

    /** Busca una solicitud por su folio de recepción. */
    Optional<Solicitud> findByFolio(String folio);
}
