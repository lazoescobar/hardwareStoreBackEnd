package com.hardwareStore.backEnd.dataSource.moduloInventario.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hardwareStore.backEnd.dataSource.moduloPersona.model.DocumentoIndentidad;
import com.hardwareStore.backEnd.dataSource.moduloUsuario.model.Usuario;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "\"ModuloInventario.Producto\"")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdProducto")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdUsuarioRgistro")
    @JsonIgnore
    private Usuario usuario;

    @Column(name = "FechaRegistro")
    private Date fechaRegistro;

    @Column(name = "FechaModificacion")
    private Date fechaModificacion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdTipoProducto")
    @JsonIgnore
    private TipoProducto tipoProducto;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<HistorialNombreProducto> historialNombreProductos;

    public Producto(){}

    public Producto(int id, Usuario usuario, Date fechaRegistro, Date fechaModificacion, TipoProducto tipoProducto, List<HistorialNombreProducto> historialNombreProductos) {
        this.id = id;
        this.usuario = usuario;
        this.fechaRegistro = fechaRegistro;
        this.fechaModificacion = fechaModificacion;
        this.tipoProducto = tipoProducto;
        this.historialNombreProductos = historialNombreProductos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public List<HistorialNombreProducto> getHistorialNombreProductos() {
        return historialNombreProductos;
    }

    public void setHistorialNombreProductos(List<HistorialNombreProducto> historialNombreProductos) {
        this.historialNombreProductos = historialNombreProductos;
    }
}
