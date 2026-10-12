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

@RestController
@RequestMapping("/solicitudes")
public class SolicitudController {

    private final SolicitudService service;

    public SolicitudController(SolicitudService service) {
        this.service = service;
    }

    @PostMapping("/solicitante/{solicitanteId}")
    public ResponseEntity<Solicitud> guardar(@PathVariable Long solicitanteId, @RequestBody Solicitud solicitud) {
        if (solicitud.getDescripcion() == null || solicitud.getDescripcion().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return service.guardar(solicitanteId, solicitud)
                .map(guardada -> ResponseEntity.status(HttpStatus.CREATED).body(guardada))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Solicitud>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solicitud> buscaPorId(@PathVariable Long id) {
        return service.buscaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Solicitud> actualizar(@PathVariable Long id, @RequestBody Solicitud datos) {
        return service.actualizar(id, datos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Solicitud>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(service.buscarPorEstado(estado));
    }

    @GetMapping("/solicitante/{solicitanteId}")
    public ResponseEntity<List<Solicitud>> buscarPorSolicitante(@PathVariable Long solicitanteId) {
        return ResponseEntity.ok(service.buscarPorSolicitante(solicitanteId));
    }
}
