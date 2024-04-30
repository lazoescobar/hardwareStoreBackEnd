package com.hardwareStore.backEnd.moduloUsuarios.servicios.usuarios;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hardwareStore.backEnd.moduleSystem.servicios.SalidaBaseService;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.modelos.Usuario;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.respositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class BuscarUsuarioPorId {

    private final UsuarioRepository usuarioRepository;

    public BuscarUsuarioPorId(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }


    //Salida servicio
    public class SalidaBuscarUsuarioPorId  extends SalidaBaseService {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Usuario usuario;

        public Usuario getUsuario() {
            return usuario;
        }

        public void setUsuario(Usuario usuario) {
            this.usuario = usuario;
        }
    }


    //Logica
    public SalidaBuscarUsuarioPorId logica(Integer idUsuario){

        SalidaBuscarUsuarioPorId salida = new SalidaBuscarUsuarioPorId();

        String mensajeError = "usuario no encontrado";
        if(idUsuario == null || idUsuario == 0){
            salida.setEstado(HttpStatus.NOT_FOUND);
            salida.setMensaje(mensajeError);
            salida.setErrores(+1);
            return salida;
        }

        Usuario usuario = usuarioRepository.findFirstById(idUsuario);
        if(usuario == null){
            salida.setEstado(HttpStatus.NOT_FOUND);
            salida.setMensaje(mensajeError);
            salida.setErrores(+1);
            return salida;
        }

        salida.setEstado(HttpStatus.OK);
        salida.setMensaje("Usuario encontrado correctamente");
        salida.setUsuario(usuario);

        return salida;
    }

}
