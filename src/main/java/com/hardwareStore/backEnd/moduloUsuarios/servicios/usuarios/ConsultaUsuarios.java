package com.hardwareStore.backEnd.moduloUsuarios.servicios.usuarios;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hardwareStore.backEnd.moduleSystem.servicios.SalidaBaseService;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.respositorios.UsuarioRepository;


import java.util.List;

public class ConsultaUsuarios {

    private UsuarioRepository usuarioRepository;

    public ConsultaUsuarios(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public static class EntradaConsultarUsuarios{
        public String nombre;
        public Boolean todos;
    }

    public class UsuarioConsulta {
        public Integer id;
        public String nombreUsuario;
        public String nombreCompleto;
        public String perfil;
        public String fechaRegistro;
    }


    public class SalidaConsultarUsuarios extends SalidaBaseService {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        public List<UsuarioConsulta> usuarios;

        public List<UsuarioConsulta> getUsuarios() {
            return usuarios;
        }

        public void setUsuarios(List<UsuarioConsulta> usuarios) {
            this.usuarios = usuarios;
        }
    }


    public SalidaConsultarUsuarios logica(EntradaConsultarUsuarios entrada){

        SalidaConsultarUsuarios salida = new SalidaConsultarUsuarios();


        return salida;
    }
}
