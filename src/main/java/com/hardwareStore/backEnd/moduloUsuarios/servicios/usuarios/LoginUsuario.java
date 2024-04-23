package com.hardwareStore.backEnd.moduloUsuarios.servicios.usuarios;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hardwareStore.backEnd.moduleSystem.servicios.SalidaBaseService;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.modelos.Usuario;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.modelos.VisualizaEnFront;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.respositorios.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class LoginUsuario {

    private final UsuarioRepository usuarioRepository;

    public LoginUsuario(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    public static class EntradaLoginUsuario {
        public String uss;
        public String pass;
    }

    public class InfoAcceso {
        public String nombre;
        public String ruta;
    }


    public class SalidaLoginUsuario extends SalidaBaseService {
        @JsonInclude(JsonInclude.Include.NON_NULL)
        List<InfoAcceso> accesos;
        public void agregarAcceso(InfoAcceso acceso){
            if(accesos == null){
                accesos = new ArrayList<>();
            }
            accesos.add(acceso);
        }

        public List<InfoAcceso> getAccesos() {
            return accesos;
        }
    }


    public SalidaLoginUsuario Logica(EntradaLoginUsuario entradaLogin){

        SalidaLoginUsuario salida = new SalidaLoginUsuario();
        String nombre = entradaLogin.uss;
        String pass = entradaLogin.pass;

        String mensajeError = "usuario o pass no validos";
        if((nombre != null && nombre.isEmpty()) && (pass != null && !pass.isEmpty())){
            salida.setEstado(HttpStatus.UNAUTHORIZED);
            salida.setMensaje(mensajeError);
            return salida;
        }

        Usuario usuarioEntidad = usuarioRepository.findFirstByNombreAndPass(nombre, pass);
        if(usuarioEntidad == null){
            salida.setEstado(HttpStatus.UNAUTHORIZED);
            salida.setMensaje(mensajeError);
            return salida;
        }

        if(Objects.equals(usuarioEntidad.getEstadoUsuario().getEstado(), "BLO")){
            salida.setEstado(HttpStatus.UNAUTHORIZED);
            salida.setMensaje("usuario con estado BLOQUEADO");
            return salida;
        }

        if(usuarioEntidad.getTipoUsuario().getVisualizacionesEnFront().isEmpty()){
            salida.setEstado(HttpStatus.UNAUTHORIZED);
            salida.setMensaje("usuario sin PERMISOS");
            return salida;
        }

        List<VisualizaEnFront> visualizacionesEnFront = usuarioEntidad.getTipoUsuario().getVisualizacionesEnFront();
        for(VisualizaEnFront visualizaEnFront : visualizacionesEnFront){
            InfoAcceso infoAcceso = new InfoAcceso();
            infoAcceso.nombre = visualizaEnFront.getVisualiza();
            infoAcceso.ruta = "";
            salida.agregarAcceso(infoAcceso);
        }

        salida.setEstado(HttpStatus.OK);
        salida.setMensaje("Usuario autenticado correctamente");
        return salida;
    }
}
