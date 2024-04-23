package com.hardwareStore.backEnd.moduloPersona.persistencias.modelos;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "\"ModuloPersona.EstadoDocumento\"")
public class EstadoDocumento {

    @Id
    @Column(name = "IdEstadoDocumento")
    private String id;

    @Column(name = "EstadoDocumento")
    private String estado;

    @OneToMany(mappedBy = "estadoDocumento", cascade = CascadeType.ALL)
    private List<DocumentoIndentidad> documentosIdentidad;


    public EstadoDocumento(){}

    public EstadoDocumento(String id, String estado, List<DocumentoIndentidad> documentosIdentidad) {
        this.id = id;
        this.estado = estado;
        this.documentosIdentidad = documentosIdentidad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getIdEstado() {
        return id;
    }

    public void setIdEstado(String id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<DocumentoIndentidad> getDocumentosIdentidad() {
        return documentosIdentidad;
    }

    public void setDocumentosIdentidad(List<DocumentoIndentidad> documentosIdentidad) {
        this.documentosIdentidad = documentosIdentidad;
    }
}
