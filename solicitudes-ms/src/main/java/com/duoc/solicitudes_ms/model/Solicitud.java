package com.duoc.solicitudes_ms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

/**
 * Entidad que representa una solicitud de retiro de reciclaje en RutaLimpia.
 * El vecinoId referencia al usuario del microservicio de Autenticación.
 */
@Entity
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String folio;

    private String direccion;

    private String tipoResiduo;

    private String horarioPreferido;

    private String estado;

    private LocalDate fechaCreacion;

    private Long vecinoId;

    /** Constructor vacío requerido por JPA. */
    public Solicitud() {
    }

    /** Constructor con los campos que envía el vecino al crear una solicitud. */
    public Solicitud(String direccion, String tipoResiduo, String horarioPreferido, Long vecinoId) {
        this.direccion = direccion;
        this.tipoResiduo = tipoResiduo;
        this.horarioPreferido = horarioPreferido;
        this.vecinoId = vecinoId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipoResiduo() {
        return tipoResiduo;
    }

    public void setTipoResiduo(String tipoResiduo) {
        this.tipoResiduo = tipoResiduo;
    }

    public String getHorarioPreferido() {
        return horarioPreferido;
    }

    public void setHorarioPreferido(String horarioPreferido) {
        this.horarioPreferido = horarioPreferido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Long getVecinoId() {
        return vecinoId;
    }

    public void setVecinoId(Long vecinoId) {
        this.vecinoId = vecinoId;
    }
}
