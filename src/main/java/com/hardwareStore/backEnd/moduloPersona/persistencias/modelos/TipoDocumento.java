package com.hardwareStore.backEnd.moduloPersona.persistencias.modelos;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "\"ModuloPersona.TipoDocumento\"")
public class TipoDocumento {

    @Id
    @Column(name = "IdTipoDocumento")
    private String id;

    @Column(name = "TipoDocumento")
    private String tipoDocumento;

    @OneToMany(mappedBy = "tipoDocumento", cascade = CascadeType.ALL)
    private List<DocumentoIndentidad> documentosIdentidad;

    public TipoDocumento(){}

    public TipoDocumento(String id, String tipoDocumento, List<DocumentoIndentidad> documentosIdentidad) {
        this.id = id;
        this.tipoDocumento = tipoDocumento;
        this.documentosIdentidad = documentosIdentidad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public List<DocumentoIndentidad> getDocumentosIdentidad() {
        return documentosIdentidad;
    }

    public void setDocumentosIdentidad(List<DocumentoIndentidad> documentosIdentidad) {
        this.documentosIdentidad = documentosIdentidad;
    }
}
