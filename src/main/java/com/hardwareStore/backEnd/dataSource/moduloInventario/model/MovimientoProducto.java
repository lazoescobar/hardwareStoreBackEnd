package com.hardwareStore.backEnd.dataSource.moduloInventario.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hardwareStore.backEnd.dataSource.moduloUsuario.model.Usuario;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "\"ModuloInventario.MovimientoProducto\"")
public class MovimientoProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdMovimientoProducto")
    private int id;

    @Column(name = "Cantidad")
    private Integer cantidad;

    @Column(name = "CantidadPreviaProducto")
    private Integer cantidadPrevia;

    @Column(name = "CantidadPosteriorProducto")
    private Integer cantidadPosterior;

    @Column(name = "FechaRegistro")
    private Date fechaRegistro;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdProducto")
    @JsonIgnore
    private Producto producto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdTipoMovimientoProducto")
    @JsonIgnore
    private TipoMovimientoProducto tipoMovimientoProducto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdUsuarioRegistro")
    @JsonIgnore
    private Usuario usuario;

    public MovimientoProducto(){}

    public MovimientoProducto(int id, Integer cantidad, Integer cantidadPrevia, Integer cantidadPosterior, Date fechaRegistro, Producto producto, TipoMovimientoProducto tipoMovimientoProducto, Usuario usuario) {
        this.id = id;
        this.cantidad = cantidad;
        this.cantidadPrevia = cantidadPrevia;
        this.cantidadPosterior = cantidadPosterior;
        this.fechaRegistro = fechaRegistro;
        this.producto = producto;
        this.tipoMovimientoProducto = tipoMovimientoProducto;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getCantidadPrevia() {
        return cantidadPrevia;
    }

    public void setCantidadPrevia(Integer cantidadPrevia) {
        this.cantidadPrevia = cantidadPrevia;
    }

    public Integer getCantidadPosterior() {
        return cantidadPosterior;
    }

    public void setCantidadPosterior(Integer cantidadPosterior) {
        this.cantidadPosterior = cantidadPosterior;
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

    public TipoMovimientoProducto getTipoMovimientoProducto() {
        return tipoMovimientoProducto;
    }

    public void setTipoMovimientoProducto(TipoMovimientoProducto tipoMovimientoProducto) {
        this.tipoMovimientoProducto = tipoMovimientoProducto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
