package com.hardwareStore.backEnd.dataSource.moduloPersona.model;

import javax.persistence.*;
import java.util.*;

import com.hardwareStore.backEnd.dataSource.moduloUsuario.model.Usuario;

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
    private String fechaRegistro;

    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    private Usuario usuario;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL)
    private List<DocumentoIndentidad> documentosIdentidad;

    public Persona(){}

    public Persona(int id, String nombres, String apellidoPaterno, String apellidoMaterno, String fechaRegistro, Usuario usuario, List<DocumentoIndentidad> documentosIdentidad) {
        this.id = id;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaRegistro = fechaRegistro;
        this.usuario = usuario;
        this.documentosIdentidad = documentosIdentidad;
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

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<DocumentoIndentidad> getDocumentosIdentidad() {
        return documentosIdentidad;
    }

    public void setDocumentosIdentidad(List<DocumentoIndentidad> documentosIdentidad) {
        this.documentosIdentidad = documentosIdentidad;
    }
}
