package com.hardwareStore.backEnd.moduloUsuarios.persistencias.modelos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

@Entity
@Table(name = "\"ModuloPersona.VisualizaEnFront\"")
public class VisualizaEnFront {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdVisualizaEnFront")
    private int id;

    @Column(name = "Visualiza")
    private String visualiza;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdTipoUsuario")
    @JsonIgnore
    private TipoUsuario tipoUsuario;

    public VisualizaEnFront(){}

    public VisualizaEnFront(int id, String visualiza, TipoUsuario tipoUsuario) {
        this.id = id;
        this.visualiza = visualiza;
        this.tipoUsuario = tipoUsuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVisualiza() {
        return visualiza;
    }

    public void setVisualiza(String visualiza) {
        this.visualiza = visualiza;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
