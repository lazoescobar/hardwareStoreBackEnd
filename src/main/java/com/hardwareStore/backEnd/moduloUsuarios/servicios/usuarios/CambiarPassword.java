package com.hardwareStore.backEnd.moduloUsuarios.servicios.usuarios;

import com.hardwareStore.backEnd.moduleSystem.servicios.SalidaBaseService;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.modelos.Usuario;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.respositorios.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CambiarPassword {

    private final BuscarUsuarioPorId buscarUsuarioPorId;
    private final UsuarioRepository usuarioRepository;

    public CambiarPassword(BuscarUsuarioPorId buscarUsuarioPorId, UsuarioRepository usuarioRepository){
        this.buscarUsuarioPorId = buscarUsuarioPorId;
        this.usuarioRepository = usuarioRepository;
    }

    public static class EntradaCambiarPassword{
        public Integer idUsuario;
        public Integer idUsuarioACambiar;
        public String password;
    }

    public SalidaBaseService logica(EntradaCambiarPassword entrada){
        SalidaBaseService salida = new SalidaBaseService();
        BuscarUsuarioPorId.SalidaBuscarUsuarioPorId consultaUsuario = buscarUsuarioPorId.logica(entrada.idUsuario);
        if(consultaUsuario.getErrores() > 0){
            salida.setEstado(consultaUsuario.getEstado());
            salida.setMensaje(consultaUsuario.getMensaje() +  " modificador");
            salida.setErrores(consultaUsuario.getErrores());
            return salida;
        }

        BuscarUsuarioPorId.SalidaBuscarUsuarioPorId consultaUsuarioAModificar = buscarUsuarioPorId.logica(entrada.idUsuarioACambiar);
        if(consultaUsuario.getErrores() > 0){
            salida.setEstado(consultaUsuarioAModificar.getEstado());
            salida.setMensaje(consultaUsuarioAModificar.getMensaje() +  " a modificar");
            salida.setErrores(consultaUsuarioAModificar.getErrores());
            return salida;
        }

        if(entrada.password == null || entrada.password.isEmpty()){
            salida.setEstado(HttpStatus.BAD_REQUEST);
            salida.setMensaje("Passwpord no puede ser nula o vacia");
            salida.setErrores(+1);
            return salida;
        }

        Usuario usuario = usuarioRepository.findFirstById(entrada.idUsuarioACambiar);
        if(Objects.equals(entrada.password.trim(), usuario.getPass().trim())){
            salida.setEstado(HttpStatus.BAD_REQUEST);
            salida.setMensaje("Passwpord no puede ser igual a actual");
            salida.setErrores(+1);
            return salida;
        }

        try{
            usuario.setPass(entrada.password);
            usuarioRepository.saveAndFlush(usuario);
            salida.setEstado(HttpStatus.OK);
            salida.setMensaje("Passwpord actualizada correctamente");
        }catch (Exception e){
            salida.setEstado(HttpStatus.BAD_REQUEST);
            salida.setMensaje("Porblemas al actualizar passwpord ");
            salida.setErrores(+1);
        }

        return salida;
    }
}
