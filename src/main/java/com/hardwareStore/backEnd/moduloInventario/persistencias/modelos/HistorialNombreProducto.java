package com.hardwareStore.backEnd.moduloInventario.persistencias.modelos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.modelos.Usuario;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "\"ModuloInventario.HistorialNombreProducto\"")
public class HistorialNombreProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdHistorialNombreProducto")
    private int id;

    @Column(name = "IdNombreAntiguo")
    private String nombreAntiguo;

    @Column(name = "IdNombreActual")
    private String nombreActual;

    @Column(name = "FechaRegistro")
    private Date fechaRegistro;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdProducto")
    @JsonIgnore
    private Producto producto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdUsuarioRegistro")
    @JsonIgnore
    private Usuario usuario;

    public HistorialNombreProducto(){}

    public HistorialNombreProducto(int id, String nombreAntiguo, String nombreActual, Date fechaRegistro, Producto producto, Usuario usuario) {
        this.id = id;
        this.nombreAntiguo = nombreAntiguo;
        this.nombreActual = nombreActual;
        this.fechaRegistro = fechaRegistro;
        this.producto = producto;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreAntiguo() {
        return nombreAntiguo;
    }

    public void setNombreAntiguo(String nombreAntiguo) {
        this.nombreAntiguo = nombreAntiguo;
    }

    public String getNombreActual() {
        return nombreActual;
    }

    public void setNombreActual(String nombreActual) {
        this.nombreActual = nombreActual;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
