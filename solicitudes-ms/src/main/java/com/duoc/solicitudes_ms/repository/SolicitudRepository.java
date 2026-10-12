package com.duoc.solicitudes_ms.repository;

import com.duoc.solicitudes_ms.model.Solicitud;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

    List<Solicitud> findByEstado(String estado);

    List<Solicitud> findBySolicitanteId(Long solicitanteId);
}
