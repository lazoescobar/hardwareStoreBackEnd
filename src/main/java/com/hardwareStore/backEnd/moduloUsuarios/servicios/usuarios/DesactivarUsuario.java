package com.hardwareStore.backEnd.moduloUsuarios.servicios.usuarios;

import com.hardwareStore.backEnd.moduleSystem.servicios.SalidaBaseService;
import com.hardwareStore.backEnd.moduloPersona.persistencias.repositorios.EstadoDocumentoRepository;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.modelos.EstadoUsuario;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.modelos.Usuario;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.respositorios.EstadoUsuarioRepository;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.respositorios.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class DesactivarUsuario {

    private final UsuarioRepository usuarioRepository;
    private final BuscarUsuarioPorId buscarUsuarioPorId;
    private final EstadoUsuarioRepository estadoUsuarioRepository;

    public DesactivarUsuario(UsuarioRepository usuarioRepository,BuscarUsuarioPorId buscarUsuarioPorId,
                             EstadoUsuarioRepository estadoUsuarioRepository){
        this.usuarioRepository = usuarioRepository;
        this.buscarUsuarioPorId = buscarUsuarioPorId;
        this.estadoUsuarioRepository = estadoUsuarioRepository;
    }
    public static class EntradaDesactivarUsuario{
        public Integer idUsuarioDesactiva;
        public Integer idUsuario;
    }


    public SalidaBaseService logica(EntradaDesactivarUsuario entrada){
        SalidaBaseService salida = new SalidaBaseService();
        BuscarUsuarioPorId.SalidaBuscarUsuarioPorId consultaUsuarioDesactiva = buscarUsuarioPorId.logica(entrada.idUsuarioDesactiva);
        if(consultaUsuarioDesactiva.getErrores() > 0){
            salida.setEstado(consultaUsuarioDesactiva.getEstado());
            salida.setMensaje(consultaUsuarioDesactiva.getMensaje() +  " modificador");
            salida.setErrores(consultaUsuarioDesactiva.getErrores());
            return salida;
        }

        BuscarUsuarioPorId.SalidaBuscarUsuarioPorId consultaUsuario = buscarUsuarioPorId.logica(entrada.idUsuario);
        if(consultaUsuario.getErrores() > 0){
            salida.setEstado(consultaUsuario.getEstado());
            salida.setMensaje(consultaUsuario.getMensaje());
            salida.setErrores(consultaUsuario.getErrores());
            return salida;
        }
        Usuario usuario = consultaUsuario.getUsuario();
        this.transaccion(salida, entrada, usuario);

        return salida;
    }


    private void transaccion(SalidaBaseService salida, EntradaDesactivarUsuario entrada, Usuario usuario){
        try{
            EstadoUsuario estadoUsuario = estadoUsuarioRepository.findFirstById("BLO");
            usuario.setEstadoUsuario(estadoUsuario);
            usuario.setFechaModificacion(new Timestamp(System.currentTimeMillis()));
            Usuario usuarioInactivo = usuarioRepository.saveAndFlush(usuario);
            salida.setEstado(HttpStatus.OK);
            salida.setMensaje("Usuario desactivado correctamente");
        }catch (Exception e){
            salida.setEstado(HttpStatus.BAD_REQUEST);
            salida.setMensaje("Problemas al desactivar usuario");
            salida.setErrores(+1);
        }
    }

}
