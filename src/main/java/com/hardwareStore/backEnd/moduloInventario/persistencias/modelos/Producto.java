package com.hardwareStore.backEnd.moduloInventario.persistencias.modelos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.modelos.Usuario;

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

    @Column(name = "NombreProducto")
    private String nombre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdUsuarioRegistro")
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
    @JsonIgnore
    private List<HistorialNombreProducto> historialNombreProductos;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<MovimientoProducto> movimientosProducto;

    public Producto(){}

    public Producto(int id, String nombre, Usuario usuario, Date fechaRegistro, Date fechaModificacion, TipoProducto tipoProducto, List<HistorialNombreProducto> historialNombreProductos, List<MovimientoProducto> movimientosProducto) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.fechaRegistro = fechaRegistro;
        this.fechaModificacion = fechaModificacion;
        this.tipoProducto = tipoProducto;
        this.historialNombreProductos = historialNombreProductos;
        this.movimientosProducto = movimientosProducto;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public Usuario Usuario() {
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

    public List<MovimientoProducto> getMovimientosProducto() {
        return movimientosProducto;
    }

    public void setMovimientosProducto(List<MovimientoProducto> movimientosProducto) {
        this.movimientosProducto = movimientosProducto;
    }
}
