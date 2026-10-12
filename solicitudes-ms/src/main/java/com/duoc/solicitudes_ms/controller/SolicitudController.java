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
 * Controlador REST para las solicitudes de retiro de reciclaje.
 * Expone los endpoints CRUD y búsquedas por estado, vecino y folio.
 */
@RestController
@RequestMapping("/solicitudes")
public class SolicitudController {

    private final SolicitudService service;

    /** Constructor con inyección del servicio. */
    public SolicitudController(SolicitudService service) {
        this.service = service;
    }

    /** Crea una nueva solicitud. Valida que dirección, tipoResiduo y vecinoId no sean nulos ni vacíos. */
    @PostMapping
    public ResponseEntity<Solicitud> guardar(@RequestBody Solicitud solicitud) {
        if (solicitud.getDireccion() == null || solicitud.getDireccion().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        if (solicitud.getTipoResiduo() == null || solicitud.getTipoResiduo().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        if (solicitud.getVecinoId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Solicitud guardada = service.guardar(solicitud);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    /** Devuelve todas las solicitudes. */
    @GetMapping
    public ResponseEntity<List<Solicitud>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    /** Busca una solicitud por su id. */
    @GetMapping("/{id}")
    public ResponseEntity<Solicitud> buscaPorId(@PathVariable Long id) {
        return service.buscaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** Actualiza una solicitud existente por id. */
    @PutMapping("/{id}")
    public ResponseEntity<Solicitud> actualizar(@PathVariable Long id, @RequestBody Solicitud datos) {
        return service.actualizar(id, datos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** Elimina una solicitud por id. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /** Busca solicitudes por estado. */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Solicitud>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(service.buscarPorEstado(estado));
    }

    /** Busca solicitudes por el id del vecino. */
    @GetMapping("/vecino/{vecinoId}")
    public ResponseEntity<List<Solicitud>> buscarPorVecino(@PathVariable Long vecinoId) {
        return ResponseEntity.ok(service.buscarPorVecino(vecinoId));
    }

    /** Busca una solicitud por su folio de recepción. */
    @GetMapping("/folio/{folio}")
    public ResponseEntity<Solicitud> buscaPorFolio(@PathVariable String folio) {
        return service.buscaPorFolio(folio)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
