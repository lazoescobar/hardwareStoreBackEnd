package com.hardwareStore.backEnd.dataSource.moduloUsuario.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "\"ModuloUsuario.TipoUsuario\"")
public class TipoUsuario {

    @Id
    @Column(name = "IdTipoUsuario")
    private String id;

    @Column(name = "TipoUsuario")
    private String tipo;

    @OneToMany(mappedBy = "tipoUsuario", cascade = CascadeType.ALL)
    private List<Usuario> usuarios;

    @OneToMany(mappedBy = "tipoUsuario", cascade = CascadeType.ALL)
    private List<VisualizaEnFront> visualizacionesEnFront;

    public TipoUsuario(){}

    public TipoUsuario(String id, String tipo, List<Usuario> usuarios, List<VisualizaEnFront> visualizacionesEnFront) {
        this.id = id;
        this.tipo = tipo;
        this.usuarios = usuarios;
        this.visualizacionesEnFront = visualizacionesEnFront;
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

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<VisualizaEnFront> getVisualizacionesEnFront() {
        return visualizacionesEnFront;
    }

    public void setVisualizacionesEnFront(List<VisualizaEnFront> visualizacionesEnFront) {
        this.visualizacionesEnFront = visualizacionesEnFront;
    }
}
