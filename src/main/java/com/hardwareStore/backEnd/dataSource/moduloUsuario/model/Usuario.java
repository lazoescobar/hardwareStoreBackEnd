package com.hardwareStore.backEnd.dataSource.moduloUsuario.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hardwareStore.backEnd.dataSource.moduloInventario.model.HistorialNombreProducto;
import com.hardwareStore.backEnd.dataSource.moduloInventario.model.MovimientoProducto;
import com.hardwareStore.backEnd.dataSource.moduloInventario.model.Producto;
import com.hardwareStore.backEnd.dataSource.moduloPersona.model.Persona;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "\"ModuloUsuario.Usuario\"")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdUsuario")
    private int id;

    @Column(name = "NombreUsuario")
    private String nombre;

    @Column(name = "Pass")
    private String pass;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdTipoUsuario")
    @JsonIgnore
    private TipoUsuario tipoUsuario;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdPersona")
    @JsonIgnore
    private Persona persona;

    @Column(name = "FechaRegistro")
    private Date fechaRegistro;

    @Column(name = "FechaModificacion")
    private Date fechaModificacion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdEstadoUsuario")
    @JsonIgnore
    private TipoUsuario estadoUsuario;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<MovimientoProducto> movimientoProductos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Producto> productos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<HistorialNombreProducto> historialNombreProductos;

    public Usuario(){}

    public Usuario(int id, String nombre, String pass, TipoUsuario tipoUsuario, Persona persona, Date fechaRegistro, Date fechaModificacion, TipoUsuario estadoUsuario, List<MovimientoProducto> movimientoProductos, List<Producto> productos, List<HistorialNombreProducto> historialNombreProductos) {
        this.id = id;
        this.nombre = nombre;
        this.pass = pass;
        this.tipoUsuario = tipoUsuario;
        this.persona = persona;
        this.fechaRegistro = fechaRegistro;
        this.fechaModificacion = fechaModificacion;
        this.estadoUsuario = estadoUsuario;
        this.movimientoProductos = movimientoProductos;
        this.productos = productos;
        this.historialNombreProductos = historialNombreProductos;
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

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
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

    public TipoUsuario getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(TipoUsuario estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }

    public List<MovimientoProducto> getMovimientoProductos() {
        return movimientoProductos;
    }

    public void setMovimientoProductos(List<MovimientoProducto> movimientoProductos) {
        this.movimientoProductos = movimientoProductos;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public List<HistorialNombreProducto> getHistorialNombreProductos() {
        return historialNombreProductos;
    }

    public void setHistorialNombreProductos(List<HistorialNombreProducto> historialNombreProductos) {
        this.historialNombreProductos = historialNombreProductos;
    }
}
