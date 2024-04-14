package com.hardwareStore.backEnd.jpaExample;

import javax.persistence.*;

@Entity
@Table(name = "\"ModuloPersona.Usuario\"")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdUsuario")
    private int id;

    @Column(name = "NombreUsuario")
    private String nombre;

    @Column(name = "Pass")
    private String pass;

    @Column(name = "IdTipoUsuario")
    private String idTipo;

    @Column(name = "IdPersona")
    private int idPersona;

    @Column(name = "FechaModificacion")
    private String fechaModificacion;

    @Column(name = "IdEstadoUsuario")
    private String estado;

    public UserEntity(){}

    public UserEntity(int id, String nombre, String pass, String idTipo, int idPersona, String fechaModificacion, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.pass = pass;
        this.idTipo = idTipo;
        this.idPersona = idPersona;
        this.fechaModificacion = fechaModificacion;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(String idTipo) {
        this.idTipo = idTipo;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
