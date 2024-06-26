package com.hardwareStore.backEnd.moduloInventario.persistencias.modelos;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "\"ModuloInventario.TipoMovimientoProducto\"")
public class TipoMovimientoProducto {

    @Id
    @Column(name = "IdTipoMovimientoProducto")
    private String id;

    @Column(name = "TipoMovimientoProducto")
    private String tipo;

    @OneToMany(mappedBy = "tipoMovimientoProducto", cascade = CascadeType.ALL)
    private List<MovimientoProducto> movimientoProductos;

    public TipoMovimientoProducto(){}

    public TipoMovimientoProducto(String id, String tipo, List<MovimientoProducto> movimientoProductos) {
        this.id = id;
        this.tipo = tipo;
        this.movimientoProductos = movimientoProductos;
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

    public List<MovimientoProducto> getMovimientoProductos() {
        return movimientoProductos;
    }

    public void setMovimientoProductos(List<MovimientoProducto> movimientoProductos) {
        this.movimientoProductos = movimientoProductos;
    }
}
