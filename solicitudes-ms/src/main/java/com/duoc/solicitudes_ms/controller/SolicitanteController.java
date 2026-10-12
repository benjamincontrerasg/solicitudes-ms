package com.duoc.solicitudes_ms.controller;

import com.duoc.solicitudes_ms.model.Solicitante;
import com.duoc.solicitudes_ms.service.SolicitanteService;
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
 * Controlador REST para gestionar las operaciones sobre Solicitantes.
 */
@RestController
@RequestMapping("/solicitantes")
public class SolicitanteController {

    private final SolicitanteService service;

    public SolicitanteController(SolicitanteService service) {
        this.service = service;
    }

    /**
     * Crea un nuevo solicitante. Retorna 201 Created.
     */
    @PostMapping
    public ResponseEntity<Solicitante> guardar(@RequestBody Solicitante solicitante) {
        Solicitante guardado = service.guardar(solicitante);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    /**
     * Obtiene todos los solicitantes registrados. Retorna 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<Solicitante>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    /**
     * Obtiene un solicitante por su ID. Retorna 200 OK o 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Solicitante> buscaPorId(@PathVariable Long id) {
        return service.buscaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Actualiza un solicitante existente. Retorna 200 OK o 404 Not Found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Solicitante> actualizar(@PathVariable Long id, @RequestBody Solicitante datos) {
        return service.actualizar(id, datos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Elimina un solicitante por su ID. Retorna 204 No Content o 404 Not Found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
