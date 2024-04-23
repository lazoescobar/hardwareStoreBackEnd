package com.hardwareStore.backEnd.moduloPersona.persistencias.modelos;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "\"ModuloPersona.DocumentoIdentidad\"")
public class DocumentoIndentidad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdDocumentoIdentidad")
    private int id;

    @Column(name = "NumeroDocumento")
    private String numero;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdPersona")
    @JsonIgnore
    private Persona persona;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdTipoDocumento")
    @JsonIgnore
    private TipoDocumento tipoDocumento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdEstadoDocumento")
    @JsonIgnore
    private EstadoDocumento estadoDocumento;


    public DocumentoIndentidad(){}

    public DocumentoIndentidad(int id, String numero, Persona persona, TipoDocumento tipoDocumento, EstadoDocumento estadoDocumento) {
        this.id = id;
        this.numero = numero;
        this.persona = persona;
        this.tipoDocumento = tipoDocumento;
        this.estadoDocumento = estadoDocumento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public EstadoDocumento getEstadoDocumento() {
        return estadoDocumento;
    }

    public void setEstadoDocumento(EstadoDocumento estadoDocumento) {
        this.estadoDocumento = estadoDocumento;
    }
}
