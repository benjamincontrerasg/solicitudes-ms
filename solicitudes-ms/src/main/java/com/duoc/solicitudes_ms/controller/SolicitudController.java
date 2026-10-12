package com.duoc.solicitudes_ms.controller;

import com.duoc.solicitudes_ms.model.Solicitud;
import com.duoc.solicitudes_ms.service.SolicitudService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para gestionar las operaciones sobre Solicitudes.
 */
@RestController
@RequestMapping("/solicitudes")
public class SolicitudController {

    private final SolicitudService service;

    public SolicitudController(SolicitudService service) {
        this.service = service;
    }

    /**
     * Crea una solicitud asociada a un solicitante. Retorna 201 Created, 400 Bad Request o 404 Not Found.
     */
    @PostMapping("/solicitante/{solicitanteId}")
    public ResponseEntity<Solicitud> guardar(@PathVariable Long solicitanteId, @RequestBody Solicitud solicitud) {
        if (solicitud.getDescripcion() == null || solicitud.getDescripcion().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return service.guardar(solicitanteId, solicitud)
                .map(guardada -> ResponseEntity.status(HttpStatus.CREATED).body(guardada))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene la lista completa de solicitudes. Retorna 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<Solicitud>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    /**
     * Obtiene una solicitud por su ID. Retorna 200 OK o 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Solicitud> buscaPorId(@PathVariable Long id) {
        return service.buscaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Actualiza los datos de una solicitud existente. Retorna 200 OK o 404 Not Found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Solicitud> actualizar(@PathVariable Long id, @RequestBody Solicitud datos) {
        return service.actualizar(id, datos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Elimina una solicitud por su ID. Retorna 204 No Content o 404 Not Found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Busca solicitudes filtrando por estado. Retorna 200 OK con la lista.
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Solicitud>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(service.buscarPorEstado(estado));
    }

    /**
     * Busca solicitudes asociadas a un solicitante específico. Retorna 200 OK con la lista.
     */
    @GetMapping("/solicitante/{solicitanteId}")
    public ResponseEntity<List<Solicitud>> buscarPorSolicitante(@PathVariable Long solicitanteId) {
        return ResponseEntity.ok(service.buscarPorSolicitante(solicitanteId));
    }
}
