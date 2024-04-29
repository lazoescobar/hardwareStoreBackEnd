package com.hardwareStore.backEnd.moduloInventario.persistencias.modelos;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "\"ModuloInventario.TipoProducto\"")
public class TipoProducto {

    @Id
    @Column(name = "IdTipoProducto")
    private String id;

    @Column(name = "TipoProducto")
    private String tipo;

    @OneToMany(mappedBy = "tipoProducto", cascade = CascadeType.ALL)
    private List<Producto> productos;

    public TipoProducto(){}

    public TipoProducto(String id, String tipo, List<Producto> productos) {
        this.id = id;
        this.tipo = tipo;
        this.productos = productos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}
