package com.duoc.solicitudes_ms.repository;

import com.duoc.solicitudes_ms.model.Solicitante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad Solicitante.
 * Proporciona operaciones CRUD estándar para la gestión de solicitantes.
 */
@Repository
public interface SolicitanteRepository extends JpaRepository<Solicitante, Long> {
}
