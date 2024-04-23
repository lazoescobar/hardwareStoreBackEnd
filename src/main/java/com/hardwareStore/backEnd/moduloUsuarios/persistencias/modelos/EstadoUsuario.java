package com.hardwareStore.backEnd.moduloUsuarios.persistencias.modelos;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "\"ModuloUsuario.EstadoUsuario\"")
public class EstadoUsuario {

    @Id
    @Column(name = "IdEstadoUsuario")
    private String id;

    @Column(name = "EstadoUsuario")
    private String estado;

    @OneToMany(mappedBy = "estadoUsuario", cascade = CascadeType.ALL)
    private List<Usuario> usuarios;

    public EstadoUsuario(){}

    public EstadoUsuario(String id, String estado, List<Usuario> usuarios) {
        this.id = id;
        this.estado = estado;
        this.usuarios = usuarios;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
