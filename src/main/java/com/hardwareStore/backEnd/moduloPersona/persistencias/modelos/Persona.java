package com.hardwareStore.backEnd.moduloPersona.persistencias.modelos;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

import com.hardwareStore.backEnd.moduloUsuarios.persistencias.modelos.Usuario;

@Entity
@Table(name = "\"ModuloPersona.Persona\"")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPersona")
    private int id;

    @Column(name = "Nombres")
    private String nombres;

    @Column(name = "ApellidoPaterno")
    private String apellidoPaterno;

    @Column(name = "ApellidoMaterno")
    private String apellidoMaterno;

    @Column(name = "FechaRegistro")
    private Date fechaRegistro;

    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    private Usuario usuario;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL)
    private List<DocumentoIndentidad> documentosIdentidad;

    public Persona(){}

    public Persona(int id, String nombres, String apellidoPaterno, String apellidoMaterno, Date fechaRegistro, Usuario usuario, List<DocumentoIndentidad> documentosIdentidad) {
        this.id = id;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaRegistro = fechaRegistro;
        this.usuario = usuario;
        this.documentosIdentidad = documentosIdentidad;
    }

    public Persona(String nombres, String apellidoPaterno, String apellidoMaterno, Date fechaRegistro) {
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaRegistro = fechaRegistro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuarioEntidad) {
        this.usuario = usuario;
    }

    public List<DocumentoIndentidad> getDocumentosIdentidad() {
        return documentosIdentidad;
    }

    public void setDocumentosIdentidad(List<DocumentoIndentidad> documentosIdentidad) {
        this.documentosIdentidad = documentosIdentidad;
    }
}
